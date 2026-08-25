package Controlador;

import javax.smartcardio.*;
import java.util.List;

public class LectorNFC {

    private static final int IOCTL_CCID_ESCAPE = 0x003136B0;

    private CardTerminal lector;

    private volatile boolean ejecutando = false;
    private volatile boolean pausado = false;

    private Thread hiloLector;

    private ListenerNFC listener;

    public interface ListenerNFC {
        void tarjetaDetectada(String uid);
        void error(String mensaje, Exception e);
    }

    public LectorNFC() throws CardException {

        TerminalFactory factory = TerminalFactory.getDefault();

        List<CardTerminal> terminals = factory.terminals().list();

        if (terminals.isEmpty()) {
            throw new CardException(
                    "No se encontró ningún lector NFC."
            );
        }

        for (CardTerminal terminal : terminals) {

            System.out.println(
                    "Lector encontrado: " + terminal.getName()
            );

            if (terminal.getName()
                    .toUpperCase()
                    .contains("ACR122")) {

                lector = terminal;
                break;
            }
        }

        if (lector == null) {
            throw new CardException(
                    "No se encontró el lector ACR122U."
            );
        }

        System.out.println(
                "Lector seleccionado: " + lector.getName()
        );
    }

    // ---------------------------------------------------------
    // ASIGNAR LISTENER
    // ---------------------------------------------------------

    public void setListener(ListenerNFC listener) {
        this.listener = listener;
    }

    // ---------------------------------------------------------
    // INICIAR LECTOR
    // ---------------------------------------------------------

    public void iniciar() {

        if (ejecutando) {
            return;
        }

        ejecutando = true;
        pausado = false;

        hiloLector = new Thread(() -> {

            System.out.println("Lector NFC iniciado.");

            while (ejecutando) {

                try {

                    // -------------------------------------------------
                    // SI ESTÁ PAUSADO
                    // -------------------------------------------------

                    if (pausado) {

                        Thread.sleep(100);

                        continue;
                    }

                    // -------------------------------------------------
                    // ESPERAR TARJETA
                    // -------------------------------------------------

                    /*
                     * Usamos 200 ms en lugar de 0.
                     *
                     * Esto permite comprobar constantemente si el
                     * lector fue pausado o detenido.
                     */

                    boolean tarjetaPresente =
                            lector.waitForCardPresent(200);

                    if (!tarjetaPresente) {
                        continue;
                    }

                    // Si se pausó justo cuando apareció la tarjeta
                    if (pausado || !ejecutando) {
                        continue;
                    }

                    System.out.println(
                            "Tarjeta detectada."
                    );

                    // -------------------------------------------------
                    // OBTENER UID
                    // -------------------------------------------------

                    String uid = obtenerUID();

                    if (uid != null && !uid.isEmpty()) {

                        System.out.println(
                                "UID: " + uid
                        );

                        if (listener != null) {

                            try {

                                listener.tarjetaDetectada(uid);

                            } catch (Exception e) {

                                System.out.println(
                                        "Error dentro del listener:"
                                );

                                e.printStackTrace();
                            }
                        }
                    }

                    // -------------------------------------------------
                    // ESPERAR A QUE RETIREN LA TARJETA
                    // -------------------------------------------------

                    while (
                            ejecutando &&
                            lector.isCardPresent()
                    ) {

                        Thread.sleep(100);
                    }

                    System.out.println(
                            "Tarjeta retirada."
                    );

                } catch (InterruptedException e) {

                    Thread.currentThread().interrupt();

                } catch (CardException e) {

                    if (ejecutando && listener != null) {

                        listener.error(
                                "Error comunicándose con el lector.",
                                e
                        );
                    }

                    // Evitar un ciclo demasiado rápido
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
            }

            System.out.println(
                    "Lector NFC detenido."
            );

        });

        hiloLector.setName("Hilo-Lector-NFC");

        hiloLector.start();
    }

    // ---------------------------------------------------------
    // OBTENER UID
    // ---------------------------------------------------------

    private String obtenerUID() throws CardException {

        Card tarjeta = null;

        try {

            tarjeta = lector.connect("direct");

            byte[] comando = {
                    (byte) 0xFF,
                    (byte) 0xCA,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00
            };

            byte[] respuesta =
                    tarjeta.transmitControlCommand(
                            IOCTL_CCID_ESCAPE,
                            comando
                    );

            if (respuesta.length < 2) {

                throw new CardException(
                        "Respuesta inválida del lector."
                );
            }

            int sw1 =
                    respuesta[respuesta.length - 2] & 0xFF;

            int sw2 =
                    respuesta[respuesta.length - 1] & 0xFF;

            if (sw1 != 0x90 || sw2 != 0x00) {

                throw new CardException(
                        String.format(
                                "Error del lector: %02X %02X",
                                sw1,
                                sw2
                        )
                );
            }

            StringBuilder uid =
                    new StringBuilder();

            for (int i = 0;
                 i < respuesta.length - 2;
                 i++) {

                uid.append(
                        String.format(
                                "%02X",
                                respuesta[i] & 0xFF
                        )
                );
            }

            return uid.toString();

        } finally {

            if (tarjeta != null) {

                try {
                    tarjeta.disconnect(false);
                } catch (Exception ignored) {
                }
            }
        }
    }

    // ---------------------------------------------------------
    // PAUSAR
    // ---------------------------------------------------------

    public void pausar() {

        pausado = true;

        System.out.println(
                "Lector NFC pausado."
        );
    }

    // ---------------------------------------------------------
    // REANUDAR
    // ---------------------------------------------------------

    public void reanudar() {

        pausado = false;

        System.out.println(
                "Lector NFC reanudado."
        );
    }

    // ---------------------------------------------------------
    // DETENER
    // ---------------------------------------------------------

    public void detener() {

        ejecutando = false;
        pausado = false;

        if (hiloLector != null) {
            hiloLector.interrupt();
        }

        System.out.println(
                "Deteniendo lector NFC..."
        );
    }

    // ---------------------------------------------------------
    // ESTADO
    // ---------------------------------------------------------

    public boolean estaPausado() {
        return pausado;
    }

    public boolean estaEjecutando() {
        return ejecutando;
    }
}