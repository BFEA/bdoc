package bup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import bdoc.dbconnector;
import javax.swing.JOptionPane;

/**
 *
 * @author Altynbek
 * 
 */

public class AddBup {
    
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    
    public AddBup(){
    
        conn = dbconnector.getConnection();
    }
    
    public Boolean isBupExists(){
        
        Boolean check = false;
    
        
        return check;
    }
    
    public Boolean addBup(String bup_years_id,
                          String bup_disc_code,
                          String bup_vsego_chas,
                          String bup_credit_vsego,
                          String bup_aud_chas,
                          String bup_lec_chas,
                          String bup_prac_chas,
                          String bup_ind_chas,
                          String bup_srs_chas,
                          String bup_zachet,
                          String bup_ekzamen,
                          String bup_kursov_rabota,
                          String bup_kontr_rabota,
                          String bup_nir,
                          String bup_semestr){
        
        Boolean bupAdded = false;
        
        try {
            
        final String sql = "INSERT INTO bup_year (bup_year_id_auto, "
                                                + "bup_years_id, "
                                                + "bup_dis_code, "
                                                + "bup_vsego_chas, "
                                                + "bup_kredit_vsego, "
                                                + "bup_aud_chas, "
                                                + "bup_lec_chas, "
                                                + "bup_prac_chas, "
                                                + "bup_ind_chas, "
                                                + "bup_srs_chas, "
                                                + "bup_zachet, "
                                                + "bup_ekzamen, "
                                                + "bup_kur_rabota, "
                                                + "bup_kontr_rab, "
                                                + "bup_nir, "
                                                + "bup_semestr) "
                + "VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                
            pst = conn.prepareStatement(sql);
                 
                pst.setString(1, bup_years_id);
                pst.setString(2, bup_disc_code);
                pst.setString(3, bup_vsego_chas);
                pst.setString(4, bup_credit_vsego);
                pst.setString(5, bup_aud_chas);
                pst.setString(6, bup_lec_chas);
                pst.setString(7, bup_prac_chas);
                pst.setString(8, bup_ind_chas);
                pst.setString(9, bup_srs_chas);
                pst.setString(10, bup_zachet);
                pst.setString(11, bup_ekzamen);
                pst.setString(12, bup_kursov_rabota);
                pst.setString(13, bup_kontr_rabota);
                pst.setString(14, bup_nir);
                pst.setString(15, bup_semestr);
                
                int check = pst.executeUpdate();
                
                if(check > 0){
                   bupAdded = true;
                }else{
                    bupAdded = false;
                }
                    
            pst.close();
//            rs.close(); 
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        return bupAdded;
        
    }
    
}
