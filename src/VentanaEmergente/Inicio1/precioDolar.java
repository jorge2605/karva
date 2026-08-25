package VentanaEmergente.Inicio1;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;
import javax.swing.JOptionPane;
import org.jsoup.select.Elements;
public class precioDolar {
    
    public double getPrecio(){
        String url = "https://www.cotizacion.co/mexico/precio-del-dolar-en-ciudad-juarez-chihuahua.php/";
        try {
            Document document = Jsoup.connect(url).get();
            Elements elements = document.getElementsByClass("pricetxt");
            for (Element element : elements) {
                String dato = element.text();
                try{
                    double precio = Double.parseDouble(dato.replaceAll("[^0-9,\\.]", ""));
                    return precio;
                }catch(NumberFormatException e){
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "ERROR AL VER PRECIO DEL DOLAR: "+e,"error",JOptionPane.ERROR_MESSAGE);
        }
        return 0;
    }
}

