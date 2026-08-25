package VentanaEmergente.ProyectoManager;

import Conexiones.Conexion;
import VentanaEmergente.Ventas.ColorVentas;
import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableModel;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ComparisonOperator;
import org.apache.poi.ss.usermodel.ConditionalFormattingRule;
import org.apache.poi.ss.usermodel.CreationHelper;
import static org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PatternFormatting;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.SheetConditionalFormatting;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class pruebaExcel {

    public static JTable Tabla1;

    public static XSSFColor colorCol(int col) {
        switch (col) {
            case 0:
            case 1:
                return new XSSFColor(new Color(237, 200, 149), null);
            case 2:
            case 3:
            case 4:
                return new XSSFColor(new Color(198, 224, 180), null);
            case 5:
            case 6:
            case 7:
                return new XSSFColor(new Color(189, 215, 238), null);
            case 8:
            case 9:
            case 10:
                return new XSSFColor(new Color(225, 230, 153), null);
            case 11:
            case 12:
            case 13:
                return new XSSFColor(new Color(237, 237, 237), null);
            default:
                return null;
        }
    }

    public static LinkedHashMap extraerInfo(String limit) {
        try {
            Connection con = new Conexion().getConnection();
            String sql = "SELECT p.proyecto, p.descripcion, p.Estatus,\n"
                    + "\n"
                    + "    MAX(CASE WHEN a.departamento='Diseño'\n"
                    + "        THEN a.fecha END) AS fechaD,\n"
                    + "\n"
                    + "    MAX(CASE WHEN a.departamento='Diseño'\n"
                    + "        THEN a.fechaFin END) AS finD,\n"
                    + "\n"
                    + "    MAX(CASE WHEN a.departamento='Diseño'\n"
                    + "        THEN a.FechaTermino END) AS terD,\n"
                    + "\n"
                    + "    MAX(CASE WHEN a.departamento='Maquinados'\n"
                    + "        THEN a.fecha END) AS fechaM,\n"
                    + "\n"
                    + "    MAX(CASE WHEN a.departamento='Maquinados'\n"
                    + "        THEN a.fechaFin END) AS finM,\n"
                    + "\n"
                    + "    MAX(CASE WHEN a.departamento='Maquinados'\n"
                    + "        THEN a.FechaTermino END) AS terM,\n"
                    + "\n"
                    + "    MAX(CASE WHEN a.departamento='Integracion'\n"
                    + "        THEN a.fecha END) AS fechaI,\n"
                    + "\n"
                    + "    MAX(CASE WHEN a.departamento='Integracion'\n"
                    + "        THEN a.fechaFin END) AS finI,\n"
                    + "\n"
                    + "    MAX(CASE WHEN a.departamento='Integracion'\n"
                    + "        THEN a.FechaTermino END) AS terI,\n"
                    + "\n"
                    + "    MAX(CASE WHEN a.departamento='Compras'\n"
                    + "        THEN a.fecha END) AS fechaC,\n"
                    + "\n"
                    + "    MAX(CASE WHEN a.departamento='Compras'\n"
                    + "        THEN a.fechaFin END) AS finC,\n"
                    + "\n"
                    + "    MAX(CASE WHEN a.departamento='Compras'\n"
                    + "        THEN a.FechaTermino END) as terC\n"
                    + "\n"
                    + "FROM proyectos p\n"
                    + "LEFT JOIN agenda a ON p.Proyecto = a.Proyecto\n"
                    + "where p.Estatus like 'EN PROCESO' or p.estatus like 'CERRADO'\n"
                    + "GROUP BY p.id order by id desc limit " + limit;
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            LinkedHashMap<String, InfoProyecto> proyectos = new LinkedHashMap<>();
            while (rs.next()) {
                String pr = rs.getString("proyecto");
                String des = rs.getString("descripcion");
                String ccd = rs.getString("finD");
                String crd = rs.getString("terD");
                String ccc = rs.getString("finC");
                String crc = rs.getString("terD");
                String ccm = rs.getString("finM");
                String crm = rs.getString("terM");
                String cci = rs.getString("finI");
                String cri = rs.getString("terI");
                InfoProyecto inf = new InfoProyecto(pr, des, ccd, crd, ccc, crc, ccm, crm, cci, cri);
                proyectos.put(pr, inf);
            }
            return proyectos;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error" + e, "Error ", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    public static void formatoCondicional(Sheet sheet) {
        SheetConditionalFormatting sheetCF = sheet.getSheetConditionalFormatting();

        ConditionalFormattingRule rule = sheetCF.createConditionalFormattingRule(ComparisonOperator.LT, "0");

        PatternFormatting fill = rule.createPatternFormatting();
        fill.setFillBackgroundColor(IndexedColors.RED.getIndex());
        fill.setFillForegroundColor(IndexedColors.RED.getIndex());
        fill.setFillPattern(PatternFormatting.SOLID_FOREGROUND);

        CellRangeAddress[] regions = {
            CellRangeAddress.valueOf("E3:E100")
        };

        sheetCF.addConditionalFormatting(regions, rule);
    }

    public void crearReporteFechas(String url, String limit) {
        try {
            Workbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet("Proyectos");

            CellStyle headerStyle = wb.createCellStyle();
            Font fontHeader = wb.createFont();
            fontHeader.setBold(true);
            headerStyle.setFont(fontHeader);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderBottom(BorderStyle.THIN);
//            headerStyle.setBorderLeft(BorderStyle.THIN);
//            headerStyle.setBorderRight(BorderStyle.THIN);

            CellStyle normalStyle = wb.createCellStyle();
//            normalStyle.setBorderTop(BorderStyle.THIN);
            normalStyle.setBorderBottom(BorderStyle.THIN);
//            normalStyle.setBorderLeft(BorderStyle.THIN);
//            normalStyle.setBorderRight(BorderStyle.THIN);

            CellStyle fechaStyle = wb.createCellStyle();
            fechaStyle.cloneStyleFrom(normalStyle);

            CreationHelper createHelper = wb.getCreationHelper();
            fechaStyle.setDataFormat(
                    createHelper.createDataFormat().getFormat("dd/MM/yyyy")
            );

            Row row0 = sheet.createRow(0);
            Row row1 = sheet.createRow(1);

            String[] headers = {
                "Proyecto",
                "Descripcion",
                "Cierre compromiso",
                "Cierre real",
                "Duracion",
                "Cierre compromiso",
                "Cierre real",
                "Duracion",
                "Cierre compromiso",
                "Cierre real",
                "Duracion",
                "Cierre compromiso",
                "Cierre real",
                "Duracion"
            };

            sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 1, 1));

            sheet.setColumnWidth(0, 3000);
            sheet.setColumnWidth(1, 8000);
            formatoCondicional(sheet);
            for (int i = 0; i < headers.length; i++) {

                XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
                if (i > 1) {
                    sheet.setColumnWidth(i, 3500);
                } else {
                    style.setFont(fontHeader);
                }
                style.setFillForegroundColor(colorCol(i));
                style.setFillPattern(SOLID_FOREGROUND);
                style.setAlignment(HorizontalAlignment.CENTER);
                style.setVerticalAlignment(VerticalAlignment.CENTER);
                style.setVerticalAlignment(VerticalAlignment.CENTER);
                style.setAlignment(HorizontalAlignment.CENTER);
                style.setWrapText(true);
                Cell cell = row0.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(style);
            }

            String depas[] = {"Diseño", "Compras", "Maquinados", "Integracion"};
            XSSFColor col[] = {
                new XSSFColor(new Color(198, 224, 180), null),
                new XSSFColor(new Color(189, 215, 238), null),
                new XSSFColor(new Color(225, 230, 153), null),
                new XSSFColor(new Color(237, 237, 237), null)};

            for (int i = 0; i < depas.length; i++) {
                XSSFCellStyle styleFondo = (XSSFCellStyle) wb.createCellStyle();
                styleFondo.setFillForegroundColor(col[i]);
                styleFondo.setFillPattern(SOLID_FOREGROUND);
                styleFondo.setVerticalAlignment(VerticalAlignment.BOTTOM);
                styleFondo.setAlignment(HorizontalAlignment.CENTER);
                styleFondo.setWrapText(true);
                styleFondo.setFont(fontHeader);
                Cell cell = row1.createCell(i * 3 + 2);
                cell.setCellValue(depas[i]);
                cell.setCellStyle(styleFondo);
                sheet.addMergedRegion(new CellRangeAddress(1, 1, i * 3 + 2, i * 3 + 4));
            }

            int filaExcel = 2;

            LinkedHashMap<String, InfoProyecto> info = extraerInfo(limit);

            for (InfoProyecto inf : info.values()) {
                Row row = sheet.createRow(filaExcel);

                // PROYECTO
                Cell c0 = row.createCell(0);
                c0.setCellValue(inf.getProyecto());
                c0.setCellStyle(normalStyle);

                // DESCRIPCION
                Cell c1 = row.createCell(1);
                c1.setCellValue(inf.getDescr());
                c1.setCellStyle(normalStyle);

                String matriz[][] = {
                    {inf.getCcd(), inf.getCrd()},
                    {inf.getCcc(), inf.getCrc()},
                    {inf.getCcm(), inf.getCrm()},
                    {inf.getCci(), inf.getCri()}
                };
                for (int j = 0; j < matriz.length; j++) {
                    XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
                    style.setFillPattern(SOLID_FOREGROUND);
                    style.setAlignment(HorizontalAlignment.CENTER);
                    style.setBorderBottom(BorderStyle.THIN);
                    style.setVerticalAlignment(VerticalAlignment.CENTER);
                    style.setVerticalAlignment(VerticalAlignment.CENTER);
                    style.setAlignment(HorizontalAlignment.CENTER);
                    style.setWrapText(true);
                    style.setFillPattern(SOLID_FOREGROUND);
                    style.setFillForegroundColor(colorCol(j * 3 + 2));
                    Cell c2 = row.createCell(j * 3 + 2);

                    String fechaCompromiso = matriz[j][0];

                    // CIERRE COMPROMISO
                    c2.setCellValue(fechaCompromiso);
                    c2.setCellStyle(style);

                    // CIERRE REAL
                    Cell c3 = row.createCell(j * 3 + 3);
                    String fechaReal = matriz[j][1];

                    c3.setCellValue(fechaReal);
                    c3.setCellStyle(style);

                    Cell c4 = row.createCell(j * 3 + 4);

                    int excelRow = filaExcel + 1;

                    String letraCompromiso = CellReference.convertNumToColString(j * 3 + 2);
                    String letraReal = CellReference.convertNumToColString(j * 3 + 3);
                    String formula
                            = "IF(" + letraReal + excelRow + "=\"\","
                            + "IF(" + letraCompromiso + excelRow + "=\"\",\"\","
                            + "" + letraCompromiso + excelRow + ""
                            + "-TODAY()),IF(" + letraCompromiso + excelRow + "=\"\",\"\","
                            + "" + letraCompromiso + excelRow + "-" + letraReal + excelRow + "))";
                    c4.setCellFormula(formula);
                    c4.setCellStyle(style);
                }

                filaExcel++;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy HH-ss");
            File file = new File(url + "\\Reporte " + sdf.format(new Date()) + ".xlsx");
            try (FileOutputStream fos = new FileOutputStream(file)) {
                wb.write(fos);
            }

            wb.close();

            Desktop.getDesktop().open(file);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
