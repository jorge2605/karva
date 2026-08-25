package VentanaEmergente.Maquinados;

import javax.swing.JOptionPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class CustomDocumentFilter extends DocumentFilter {
    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        // Permite solo caracteres numéricos, puntos y 'x'
        if (string != null && string.matches("[0-9.]*")) {
            String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        // Permite solo caracteres numéricos, puntos y 'x'
        if(text.toLowerCase().equals("x")){
            
        }
        if (text != null && text.matches("[0-9.]*")) {
            String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
            try{
                double parseDouble = Double.parseDouble(currentText + text);
                super.replace(fb, offset, length, text, attrs);
            }catch(Exception e){
            }
            
        }
    }

    @Override
    public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
        super.remove(fb, offset, length);
    }
}