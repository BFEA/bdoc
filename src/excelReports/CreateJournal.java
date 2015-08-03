package excelReports;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.WorkbookUtil;

/**
 *
 * @author Altynbek Shaidyldaev
 * 
 */

public class CreateJournal {

    public void createBook(String name) throws IOException {
        
        String path = System.getProperty("user.home") + "/Desktop/"+name+".xls";
        
        Workbook wb = new HSSFWorkbook();
        
        String safeSheetName = WorkbookUtil.createSafeSheetName(name);
        Sheet sheet = wb.createSheet(safeSheetName);
        
        Row row = sheet.createRow((short)0);
        
        Cell cell = row.createCell(0) ;
        cell.setCellValue("№");
        CellRangeAddress regionNumber = CellRangeAddress.valueOf("A1:A2");
        sheet.addMergedRegion(regionNumber);
        
        row.createCell(1).setCellValue("ФИО");
        CellRangeAddress regionName = CellRangeAddress.valueOf("B1:B2");
        sheet.addMergedRegion(regionName);
        
        row.createCell(2).setCellValue("Зачеты");
        CellRangeAddress regionTerms = CellRangeAddress.valueOf("C1:M1");
        sheet.addMergedRegion(regionTerms);
        
        
        
        FileOutputStream fileOut = new FileOutputStream(path);
        wb.write(fileOut);
        fileOut.close();
    }
    
    public void writeJournal(int row, int column, String label, String path) {
        
        try {
            InputStream inp = new FileInputStream(new File(path));

            Workbook wb = WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheetAt(0); //Access the worksheet, so that we can update / modify it.
            Row sheetRow = sheet.getRow(row);

                if (sheetRow == null) {
                    HSSFRow newRow = (HSSFRow) sheet.createRow(row);
                    newRow.createCell(column).setCellValue(label);
                }else{
                    sheetRow.createCell(column).setCellValue(label);
                }
                
            try (FileOutputStream fileOut = new FileOutputStream(path)) {
                wb.write(fileOut);
            }
        } catch (IOException | InvalidFormatException e) {
//            JOptionPane.showMessageDialog(null, " writeJournal "+e);
                        e.printStackTrace();
        }
        
    }
   
    public String getStringCellValue(int row, int column, String path){
        
        String value=null;
        
        try {
            InputStream inp = new FileInputStream(new File(path));
            Workbook wb = WorkbookFactory.create(inp);
            
            Sheet sheet = wb.getSheetAt(0);
            Cell cell = sheet.getRow(row).getCell(column);
            value=cell.getStringCellValue();
            
        } catch(IOException ex){
//            JOptionPane.showMessageDialog(null, " getStringCellValue "+ex);
                        ex.printStackTrace();
        } catch(InvalidFormatException invfex){
//            JOptionPane.showMessageDialog(null, " getStringCellValue "+invfex);
                       invfex.printStackTrace();
        }
        return value;
    }
   
}