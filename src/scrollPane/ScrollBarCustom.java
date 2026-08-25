package scrollPane;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JScrollBar;

public class ScrollBarCustom extends JScrollBar{
    
    public ScrollBarCustom(Color color){
        setUI(new scrollModerno());
        setPreferredSize(new Dimension(8,8));
        setForeground(color);
        setBackground(Color.white);
        this.setUnitIncrement(30);
    }
    
}
