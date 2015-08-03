
package excelReports;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Altynbek Shaidyldaev
 */

public class ExecuteCreateJournal {
    
    public void createJournalExcel(String grCode, String semestr){
        
        CreateJournal tr = new CreateJournal();
        try {
            tr.createBook(grCode);
        } catch (IOException ex) {
//            JOptionPane.showMessageDialog(null, " createJournalExcel "+ex);
            ex.printStackTrace();
        }
        
        String path = System.getProperty("user.home") + "/Desktop/"+grCode+".xls";
        studentHibClass vb = new studentHibClass();
        List<Integer> studPoi = vb.studIdArr(grCode);
        List<String> uchplan = vb.getgrUchPlan(grCode, semestr);
        List<String> studMarks = new ArrayList<>();
        

        int row = 1;
        int column = 1;
        
        //Insert StudentId into excel iterator
        for (Integer studPoi1 : studPoi) {
            row++;
            tr.writeJournal(row, 1, studPoi1.toString(), path);
        }

        //Insert discCodes into iterator
        for (String uchplan1 : uchplan) {
            column++;
            tr.writeJournal(1, column, uchplan1, path);
        }

        //Insert marks for Selecting by student_id, disc_code, uchplan_semestr
        for(int rowID=2;rowID<studPoi.size()+2;rowID++){
            for(int i=2;i<uchplan.size()+2;i++){

               String studIdent = tr.getStringCellValue(rowID,1, path);
               String disc_code = tr.getStringCellValue(1, i, path);
               String markSt = vb.getStudMarks(semestr, studIdent, disc_code);
               String markAfterConvert="нет";
           
                if(markSt.equals("-1")){
                    markAfterConvert="0";
                    tr.writeJournal(2, i, markAfterConvert, path);
                }else{
                    if(markSt.equals("Отлично")){
                        markAfterConvert="5";
                        tr.writeJournal(rowID, i, markAfterConvert, path);
                    }else if(markSt.equals("Хорошо")){
                        markAfterConvert="4";
                        tr.writeJournal(rowID, i, markAfterConvert, path);
                    }else if(markSt.equals("Удов.")){
                        markAfterConvert="3";
                        tr.writeJournal(rowID, i, markAfterConvert, path);
                    }else if(markSt.equals("Не удов.")){
                        markAfterConvert="";
                        tr.writeJournal(rowID, i, markAfterConvert, path);
                    }else if(markSt.equals("Не зачтено")){
                        markAfterConvert=null;
                        tr.writeJournal(rowID, i, markAfterConvert, path);
                    }else if(markSt == null){
                        markAfterConvert=null;
                        tr.writeJournal(rowID, i, markAfterConvert, path);
                    }else{
                        markAfterConvert=markSt;
                        tr.writeJournal(rowID, i, markAfterConvert, path);
                    }
                }
            }
        }
        
        int e =1;
        for(Integer studPoi1 : studPoi){
            e++;
            String studName = vb.getStudentNamesById(studPoi1.toString());
            tr.writeJournal(e, 1, studName, path);
        }
        
        int c =1;
        for (String uchplan1 : uchplan) {
            String discName  = vb.getDisciplineNamesByCode(uchplan1);
            c++;
            tr.writeJournal(1, c, discName, path);
        }
    }
}
