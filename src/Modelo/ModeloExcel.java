package Modelo;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ModeloExcel {
    Workbook book;
    
    public String Importar(File archivo, JTable tabla){
        String mensaje="Error en la Importacion";
        DefaultTableModel modelo=new DefaultTableModel();
        tabla.setModel(modelo);
        
        try {
            book=WorkbookFactory.create(new FileInputStream(archivo));
            Sheet hoja=book.getSheetAt(0);
            Iterator FilaIterator=hoja.rowIterator();
            int IndiceFila=-1;
            
            while (FilaIterator.hasNext()) {                
                IndiceFila++;
                Row fila=(Row)FilaIterator.next();
                Iterator ColumnaIterator=fila.cellIterator();
                Object[]ListaColumna=new Object[9999];
                int IndiceColumna=-1;
                while (ColumnaIterator.hasNext()) {                
                    IndiceColumna++;
                    Cell celda=(Cell)ColumnaIterator.next();
                    if(IndiceFila==0){
                        modelo.addColumn(celda.getStringCellValue());
                    }else{
                        if(celda!=null){
                           switch (celda.getCellType()){
                                case NUMERIC:
                                    ListaColumna[IndiceColumna]=(int)Math.round(celda.getNumericCellValue());
                                    break;
                                case STRING:
                                    ListaColumna[IndiceColumna]=celda.getStringCellValue();
                                    break;
                                case BOOLEAN:
                                    ListaColumna[IndiceColumna]=celda.getBooleanCellValue();
                                    break;
                                default:
                                     ListaColumna[IndiceColumna]=celda.getDateCellValue();
                                     break;
                            }
                        }
                    }
                }
                
                if(IndiceFila!=0)modelo.addRow(ListaColumna);
            }
            mensaje="Importacion Exitosa";
            
        } catch (Exception e) {
        }
        return mensaje;
    }
    
    public String Exportar(File archivo, JTable tabla){
        String mensaje="Error en la Exportacion";
        int NumeroFila=tabla.getRowCount(),NumeroColumna=tabla.getColumnCount();
        if(archivo.getName().endsWith("xls")){
            book=new HSSFWorkbook();
        }else{
            book=new XSSFWorkbook();
        }
        Sheet hoja=book.createSheet("Hoja1");
        
        try {
            for (int i = -1; i < NumeroFila; i++) {
                Row fila=hoja.createRow(i+1);
                for (int j = 0; j <NumeroColumna; j++) {
                    Cell celda=fila.createCell(j);
                    if(i==-1){
                        celda.setCellValue(String.valueOf(tabla.getColumnName(j)));
                    }else{
                        celda.setCellValue(String.valueOf(tabla.getValueAt(i, j)));
                    }
                    book.write(new FileOutputStream(archivo));
                }
            }
            mensaje="Exportacion Exitosa";
        } catch (Exception e) {
        }
        return mensaje;
    }
    
}
