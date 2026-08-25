package VentanaEmergente.Maquinados;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Stack;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class elegirRevision extends javax.swing.JDialog implements KeyListener, FocusListener, ActionListener{

    private int currentButtonIndex;
    JButton botones[];
    JPanel panel[];
    Stack<String> pila;
    String elegir;
    
    public void setBoton(){
        botones = new JButton[pila.size()];
        panel = new JPanel[pila.size()];
        for (int i = 0; i < pila.size(); i++) {
            botones[i] = new JButton();
            panel[i] = new JPanel();
            panel[i].setLayout(new java.awt.BorderLayout());
            botones[i].setBackground(new java.awt.Color(51, 153, 255));
            botones[i].setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
            botones[i].setForeground(new java.awt.Color(255, 255, 255));
            botones[i].setText(pila.get(i));
            botones[i].setBorder(null);
            botones[i].addKeyListener(this);
            botones[i].addFocusListener(this);
            botones[i].addActionListener(this);
            panel[i].add(botones[i], BorderLayout.CENTER);
            java.awt.GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = i;
            gridBagConstraints.gridwidth = 3;
            gridBagConstraints.ipadx = 61;
            gridBagConstraints.ipady = 24;
            gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
            jPanel2.add(panel[i], gridBagConstraints);
            currentButtonIndex = 0;
            botones[currentButtonIndex].setFocusable(true);
            botones[currentButtonIndex].requestFocusInWindow();
        }
    }
    
    private void focusNextButton() {
        botones[currentButtonIndex].setFocusable(false);
        currentButtonIndex = (currentButtonIndex + 1) % botones.length;
        botones[currentButtonIndex].setFocusable(true);
        botones[currentButtonIndex].requestFocusInWindow();
    }

    private void focusPreviousButton() {
        botones[currentButtonIndex].setFocusable(false);
        currentButtonIndex = (currentButtonIndex - 1 + botones.length) % botones.length;
        botones[currentButtonIndex].setFocusable(true);
        botones[currentButtonIndex].requestFocusInWindow();
    }
    
    public String showDialog(Stack<String> pila){
        setLocationRelativeTo(getOwner()); // Centra el diálogo en relación con su propietario
        initComponents();
        this.pila = pila;
        setBoton();
        setVisible(true);
        return elegir;
    }
    
    public elegirRevision(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(218, 327));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBorder(null);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.GridBagLayout());
        jScrollPane1.setViewportView(jPanel2);

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jLabel5.setText("     ");
        jPanel1.add(jLabel5, java.awt.BorderLayout.EAST);

        jLabel6.setText("     ");
        jPanel1.add(jLabel6, java.awt.BorderLayout.WEST);

        jLabel7.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Revision");
        jPanel1.add(jLabel7, java.awt.BorderLayout.NORTH);

        jLabel8.setText("     ");
        jPanel1.add(jLabel8, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(elegirRevision.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(elegirRevision.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(elegirRevision.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(elegirRevision.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                elegirRevision dialog = new elegirRevision(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_DOWN) {
                focusNextButton();
            } else if (keyCode == KeyEvent.VK_UP) {
                focusPreviousButton();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }

    @Override
    public void focusGained(FocusEvent e) {
        for (int i = 0; i < pila.size(); i++) {
            if(e.getSource() == botones[i]){
                botones[i].setBackground(Color.green);
            }
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        for (int i = 0; i < pila.size(); i++) {
            if(e.getSource() == botones[i]){
                botones[i].setBackground(new java.awt.Color(51, 153, 255));
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < pila.size(); i++) {
            if(e.getSource() == botones[i]){
                elegir = pila.get(i);
                this.dispose();
            }
        }
    }
}
