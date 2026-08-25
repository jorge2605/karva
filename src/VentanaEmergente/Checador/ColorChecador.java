package VentanaEmergente.Checador;

import java.awt.Color;
import java.awt.Component;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.TableCellRenderer;

public class ColorChecador extends rojerusan.RSTableMetro {

    public confEmpleado conf[];
    public Object matriz[][][];

    public void setMatriz(Object[][][] matriz) {
        this.matriz = matriz;
    }

    public void setConfig(confEmpleado[] conf) {
        this.conf = conf;
    }

    public int buscarNumero(String numero) {
        int num = 0;
        for (int i = 0; i < conf.length; i++) {
            if (conf[i].getNumEmpleado().equals(numero)) {
                num = i;
            }
        }
        return num;
    }

    public String restarHoras(String f1, String f2, String f3, String f4, String f5, String f6, String f7) {
        long hor = 0, min = 0, seg = 0;
        String hora = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            Date h1, h2, h3, h4, h5, h6, h7;
            long m1, m2, m3, m4, m5, m6, m7;
            if (f1.equals("")) {
                m1 = 0;
            } else {
                m1 = sdf.parse(f1).getTime() - 25200000;
            }
            if (f2.equals("")) {
                m2 = 0;
            } else {
                m2 = sdf.parse(f2).getTime() - 25200000;
            }
            if (f3.equals("")) {
                m3 = 0;
            } else {
                m3 = sdf.parse(f3).getTime() - 25200000;
            }
            if (f4.equals("")) {
                m4 = 0;
            } else {
                m4 = sdf.parse(f4).getTime() - 25200000;
            }
            if (f5.equals("")) {
                m5 = 0;
            } else {
                m5 = sdf.parse(f5).getTime() - 25200000;
            }
            if (f6.equals("")) {
                m6 = 0;
            } else {
                m6 = sdf.parse(f6).getTime() - 25200000;
            }
            if (f7.equals("")) {
                m7 = 0;
            } else {
                m7 = sdf.parse(f7).getTime() - 25200000;
            }

            long timeDiff = (long) Math.floor((m1) + m2 + m3 + m4 + m5 + m6 + m7);
            long horas = TimeUnit.MINUTES.convert(timeDiff, TimeUnit.MILLISECONDS);
            hor = (horas / 60);
            min = (horas % 60);
            String j = String.valueOf(hor), k = String.valueOf(min);
            if (String.valueOf(hor).length() == 1) {
                j = "0" + hor;
            }
            if (String.valueOf(min).length() == 1) {
                k = "0" + min;
            }
            hora = j + ":" + k + "";
        } catch (ParseException ex) {
            Logger.getLogger(Calendar.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hora;
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int rowIndex, int ColumnIndex) {
        Component componente = super.prepareRenderer(renderer, rowIndex, ColumnIndex);
        //--------------------------------------------------HORAS---------------------------------------------
        //--------------------------------------------------HORAS---------------------------------------------
        //--------------------------------------------------HORAS---------------------------------------------
        if (getColumnName(ColumnIndex).equals("horas")) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            if (getValueAt(rowIndex, ColumnIndex - 1) != null && getValueAt(rowIndex, ColumnIndex - 2) != null) {
                if (!getValueAt(rowIndex, ColumnIndex - 1).toString().equals("") && !getValueAt(rowIndex, ColumnIndex - 2).toString().equals("")) {
                    Date h1, h2;
                    try {
                        if (getValueAt(rowIndex, ColumnIndex - 2).toString().equals("")
                                || getValueAt(rowIndex, ColumnIndex - 1).toString().equals("")) {
                            setValueAt("", rowIndex, ColumnIndex);
                            componente.setBackground(new Color(209, 108, 0));
                        } else {
                            String val1;
                            String val2;
                            if (this.getValueAt(rowIndex, ColumnIndex - 2).toString().equals("")) {
                                val1 = "00:00";
                            } else {
                                val1 = (this.getValueAt(rowIndex, ColumnIndex - 2).toString());
                            }
                            if (this.getValueAt(rowIndex, ColumnIndex - 1).toString().equals("")) {
                                val2 = "00:00";
                            } else {
                                val2 = (this.getValueAt(rowIndex, ColumnIndex - 1).toString());
                            }
                            h1 = sdf.parse(val1);
                            h2 = sdf.parse(val2);
                            Calendar fecha;
                            long timeDiff;
                            if (conf[buscarNumero(getValueAt(rowIndex, 0).toString())].getTurno().equals("VESPERTINO")) {
                                Date doce = sdf.parse("24:00");
                                Date doce2 = sdf.parse("00:00");
                                timeDiff = Math.abs((doce.getTime() - h1.getTime()) + ((h2.getTime() - doce2.getTime())));
                            } else {
                                timeDiff = Math.abs(h1.getTime() - h2.getTime());
                            }
                            long horas = TimeUnit.MINUTES.convert(timeDiff, TimeUnit.MILLISECONDS);
                            int hours = (int) (horas / 60);
                            int minutes = (int) (horas % 60);
                            String h = "", m = "";
                            if (String.valueOf(hours).length() == 1) {
                                h = "0" + hours;
                            } else {
                                h = "" + hours;
                            }
                            if (String.valueOf(minutes).length() == 1) {
                                m = "0" + minutes;
                            } else {
                                m = "" + minutes;
                            }
                            setValueAt(h + ":" + m + "", rowIndex, ColumnIndex);
                        }
                    } catch (ParseException ex) {
                        Logger.getLogger(ColorChecador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    setValueAt("", rowIndex, ColumnIndex);
                }
            } else {
                setValueAt("", rowIndex, ColumnIndex);
            }
        }

        //-------------------------------------------------------TOTAL HORAS-----------------------------------------------------------
        //-------------------------------------------------------TOTAL HORAS-----------------------------------------------------------
        //-------------------------------------------------------TOTAL HORAS-----------------------------------------------------------
        if (getColumnName(ColumnIndex).equals("total")) {
            String d1 = "00:00", d2 = "00:00", d3 = "00:00", d4 = "00:00", d5 = "00:00", d6 = "00:00", d7 = "00:00";
            if (getValueAt(rowIndex, 4) != null) {
                if (!getValueAt(rowIndex, 4).toString().equals("")) {
                    d1 = getValueAt(rowIndex, 4).toString();
                } else {
                    d1 = "00:00";
                }
            }
            if (getValueAt(rowIndex, 7) != null) {
                if (!getValueAt(rowIndex, 7).toString().equals("")) {
                    d2 = getValueAt(rowIndex, 7).toString();
                } else {
                    d2 = "00:00";
                }
            }
            if (getValueAt(rowIndex, 10) != null) {
                if (!getValueAt(rowIndex, 10).toString().equals("")) {
                    d3 = getValueAt(rowIndex, 10).toString();
                } else {
                    d3 = "00:00";
                }
            }
            if (getValueAt(rowIndex, 13) != null) {
                if (!getValueAt(rowIndex, 13).toString().equals("")) {
                    d4 = getValueAt(rowIndex, 13).toString();
                } else {
                    d4 = "00:00";
                }
            }
            if (getValueAt(rowIndex, 16) != null) {
                if (!getValueAt(rowIndex, 16).toString().equals("")) {
                    d5 = getValueAt(rowIndex, 16).toString();
                } else {
                    d5 = "00:00";
                }
            }
            if (getValueAt(rowIndex, 19) != null) {
                if (!getValueAt(rowIndex, 19).toString().equals("")) {
                    d6 = getValueAt(rowIndex, 19).toString();
                } else {
                    d6 = "00:00";
                }
            }
            if (getValueAt(rowIndex, 22) != null) {
                if (!getValueAt(rowIndex, 22).toString().equals("")) {
                    d7 = getValueAt(rowIndex, 22).toString();
                } else {
                    d7 = "00:00";
                }
            }
            setValueAt(restarHoras(d1, d2, d3, d4, d5, d6, d7), rowIndex, 23);

        }
        //-----------------------------------------------------RETRASO--------------------------------------------
        //-----------------------------------------------------RETRASO--------------------------------------------
        //-----------------------------------------------------RETRASO--------------------------------------------
        if (getColumnName(ColumnIndex).equals("retraso")) {
            Date lunes = null;
            Date martes = null;
            Date miercoles = null;
            Date jueves = null;
            Date viernes = null;
            Date sabado = null;
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            try {
                if (getValueAt(rowIndex, 2) != null) {
                    if (!getValueAt(rowIndex, 2).toString().equals("")) {
                        lunes = sdf.parse(getValueAt(rowIndex, 2).toString());
                    }
                }
                if (getValueAt(rowIndex, 5) != null) {
                    if (!getValueAt(rowIndex, 5).toString().equals("")) {
                        martes = sdf.parse(getValueAt(rowIndex, 5).toString());
                    }
                }
                if (getValueAt(rowIndex, 8) != null) {
                    if (!getValueAt(rowIndex, 8).toString().equals("")) {
                        miercoles = sdf.parse(getValueAt(rowIndex, 8).toString());
                    }
                }
                if (getValueAt(rowIndex, 11) != null) {
                    if (!getValueAt(rowIndex, 11).toString().equals("")) {
                        jueves = sdf.parse(getValueAt(rowIndex, 11).toString());
                    }
                }
                if (getValueAt(rowIndex, 14) != null) {
                    if (!getValueAt(rowIndex, 14).toString().equals("")) {
                        viernes = sdf.parse(getValueAt(rowIndex, 14).toString());
                    }
                }
                if (getValueAt(rowIndex, 17) != null) {
                    if (!getValueAt(rowIndex, 17).toString().equals("")) {
                        sabado = sdf.parse(getValueAt(rowIndex, 17).toString());
                    }
                }

                long tot = 0;

                if (lunes != null) {
                    if (lunes.getTime() > sdf.parse(conf[buscarNumero(getValueAt(rowIndex, 0).toString())].getEntrada()).getTime()) {
                        tot += lunes.getTime() - sdf.parse(conf[buscarNumero(getValueAt(rowIndex, 0).toString())].getEntrada()).getTime();
                    }
                }
                if (martes != null) {
                    if (martes.getTime() > sdf.parse(conf[buscarNumero(getValueAt(rowIndex, 0).toString())].getEntrada()).getTime()) {
                        tot += martes.getTime() - sdf.parse(conf[buscarNumero(getValueAt(rowIndex, 0).toString())].getEntrada()).getTime();
                    }
                }
                if (miercoles != null) {
                    if (miercoles.getTime() > sdf.parse(conf[buscarNumero(getValueAt(rowIndex, 0).toString())].getEntrada()).getTime()) {
                        tot += miercoles.getTime() - sdf.parse(conf[buscarNumero(getValueAt(rowIndex, 0).toString())].getEntrada()).getTime();
                    }
                }
                if (jueves != null) {
                    if (jueves.getTime() > sdf.parse(conf[buscarNumero(getValueAt(rowIndex, 0).toString())].getEntrada()).getTime()) {
                        tot += jueves.getTime() - sdf.parse(conf[buscarNumero(getValueAt(rowIndex, 0).toString())].getEntrada()).getTime();
                    }
                }
                if (viernes != null) {
                    if (viernes.getTime() > sdf.parse(conf[buscarNumero(getValueAt(rowIndex, 0).toString())].getEntrada()).getTime()) {
                        tot += viernes.getTime() - sdf.parse(conf[buscarNumero(getValueAt(rowIndex, 0).toString())].getEntrada()).getTime();
                    }
                }
                if (sabado != null) {
                    if (sabado.getTime() > sdf.parse(conf[buscarNumero(getValueAt(rowIndex, 0).toString())].getEntradaSabado()).getTime()) {
                        tot += sabado.getTime() - sdf.parse(conf[buscarNumero(getValueAt(rowIndex, 0).toString())].getEntradaSabado()).getTime();
                    }
                }

                long timeDiff = tot;
                long horas = TimeUnit.MINUTES.convert(timeDiff, TimeUnit.MILLISECONDS);
                long hor = (horas / 60);
                long min = (horas % 60);
                String j = String.valueOf(hor), k = String.valueOf(min);
                if (String.valueOf(hor).length() == 1) {
                    j = "0" + hor;
                }
                if (String.valueOf(min).length() == 1) {
                    k = "0" + min;
                }
                String hora = j + ":" + k + "";

                setValueAt(hora, rowIndex, ColumnIndex);
                if (!hora.equals("00:00")) {
                    componente.setForeground(Color.red);
                }
            } catch (ParseException ex) {
                Logger.getLogger(ColorChecador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //-----------------------------------------------------SOBRETRABAJO-------------------------------------------------
        //-----------------------------------------------------SOBRETRABAJO-------------------------------------------------
        //-----------------------------------------------------SOBRETRABAJO-------------------------------------------------
        if (getColumnName(ColumnIndex).equals("sobretrabajo")) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            try {
                Date d1 = sdf.parse(getValueAt(rowIndex, 23).toString());
                Date d2;
                String cantH = conf[buscarNumero(getValueAt(rowIndex, 0).toString())].getTotalHoras() + ":00";
                try {
                    d2 = sdf.parse(cantH);
                } catch (Exception e) {
                    d2 = sdf.parse("49:00");
                }

                long tot = 0;
                tot = d1.getTime() - d2.getTime();
                long timeDiff = tot;
                long horas = TimeUnit.MINUTES.convert(timeDiff, TimeUnit.MILLISECONDS);
                long hor = (horas / 60);
                long min = (horas % 60);
                String j = String.valueOf(hor), k = String.valueOf(min);
                if (String.valueOf(hor).length() == 1) {
                    j = "0" + hor;
                }
                if (String.valueOf(min).length() == 1) {
                    k = "0" + min;
                }
                String hora;
                if (d1.getTime() < d2.getTime()) {
                    hora = "00:00";
                } else {
                    hora = j + ":" + k + "";
                }
                setValueAt(hora, rowIndex, ColumnIndex);

            } catch (ParseException ex) {
                Logger.getLogger(ColorChecador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //-----------------------------------------COLORES---------------------------------------------------------
        //-----------------------------------------COLORES---------------------------------------------------------
        //-----------------------------------------COLORES---------------------------------------------------------
        if (ColumnIndex != 0 && ColumnIndex != 1 && ColumnIndex != 23 && ColumnIndex != 24 && ColumnIndex != 25) {
            if (getValueAt(rowIndex, ColumnIndex) != null) {
                if (getValueAt(rowIndex, ColumnIndex).getClass().equals(String.class)) {
                    try {
                        String valor = (this.getValueAt(rowIndex, ColumnIndex).toString());
                        Date hora1;
                        Date hora2;

                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");

                        if (getColumnName(ColumnIndex).equals("entrada")) {

                            if (valor.equals("")) {
                                componente.setBackground(new Color(209, 108, 0));
                            } else {

                                String val = valor;
                                if (this.getValueAt(rowIndex, ColumnIndex).toString().equals("")) {
                                    val = "";
                                }
                                String h2 = conf[buscarNumero(getValueAt(rowIndex, 0).toString())].getEntrada();
                                hora1 = sdf.parse(val);
                                try {
                                    hora2 = sdf.parse(h2);
                                } catch (Exception ex) {
                                    hora2 = sdf.parse("00:00");
                                }
                                if (ColumnIndex == 17) {
                                    h2 = conf[buscarNumero(getValueAt(rowIndex, 0).toString())].getEntradaSabado();
                                    hora2 = sdf.parse(h2);
                                }
                                if ((hora1.getTime()) > (hora2.getTime())) {
                                    componente.setForeground(new Color(105, 0, 0));
                                }
                            }
                        } else if (getColumnName(ColumnIndex).equals("salida")) {
                            if (valor.equals("")) {
                                componente.setBackground(new Color(209, 108, 0));
                            } else {
                                String val = valor;
                                if (this.getValueAt(rowIndex, ColumnIndex).toString().equals("")) {
                                    val = "00:00";
                                }
                                String h2 = conf[buscarNumero(getValueAt(rowIndex, 0).toString())].getSalida();
                                try {
                                    hora2 = sdf.parse(h2);
                                } catch (Exception ex) {
                                    hora2 = sdf.parse("00:00");
                                }
                                hora1 = sdf.parse(val);
                                if (ColumnIndex == 18) {
                                    h2 = conf[buscarNumero(getValueAt(rowIndex, 0).toString())].getSalidaSabado();
                                    hora2 = sdf.parse(h2);
                                }
                                if (hora1.getTime() < hora2.getTime()) {
                                    componente.setForeground(new Color(105, 0, 0));
                                }
                            }
                        } else if (getColumnName(ColumnIndex).equals("horas")) {
                            String h2 = conf[buscarNumero(getValueAt(rowIndex, 0).toString())].getHoraDiaria();
                            hora2 = sdf.parse(h2);
                            if (ColumnIndex == 19) {
                                h2 = conf[buscarNumero(getValueAt(rowIndex, 0).toString())].getHoraSabado();
                                hora2 = sdf.parse(h2);
                            }
                            if (!valor.equals("")) {
                                hora1 = sdf.parse(valor);
                            } else {
                                hora1 = sdf.parse("00:00");
                            }
                            componente.setBackground(new Color(158, 202, 240));
                            if (hora1.getTime() < hora2.getTime()) {
                                componente.setForeground(new Color(105, 0, 0));
                            }
                        } else if (getColumnName(ColumnIndex).equals("total")) {

                        }
                        if(matriz != null){
                            if(matriz[rowIndex][ColumnIndex][0] != null){
                                componente.setBackground(new Color(230,183,247));
                            }
                        }
                    } catch (ParseException ex) {
                        Logger.getLogger(ColorChecador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return componente;
    }
}
