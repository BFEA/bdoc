/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reschetChasov;

import bdoc.dbconnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JTable;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Altynbek
 */
public class AddRaschetChasov {
    
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    
    public AddRaschetChasov(){
        conn = dbconnector.getConnection();
    }
        
    /**
     * 28 parameters
     * @param disc_code,
     * @param group_code,
     * @param students_kolvo,
     * @param semestr,
     * @param group_course,
     * @param bup_lecture,
     * @param lecture,
     * @param bup_seminar,
     * @param seminar
     * @param bup_kurs_rab,
     * @param kurs_rab,
     * @param bupkontrRabota,
     * @param bup_nir,
     * @param nir,
     * @param individual,
     * @param konsult,
     * @param konsult_ind,
     * @param srs,
     * @param practica,
     * @param zachet,
     * @param ekzamen,
     * @param preddipl_prac,
     * @param rukovodstvo_vkr,
     * @param recenziya,
     * @param uchastie_v_gak,
     * @param vsego_chasov,
     * @param kreditsECTS,
     * @param teacher_code
     * @return boolean
     */
    
    public Boolean addRaschetChasov(String disc_code,
                                    String group_code,
                                    String students_kolvo,
                                    String semestr,
                                    String group_course,
                                    String bup_lecture,
                                    String lecture,
                                    String bup_seminar,
                                    String seminar,
                                    String bup_kurs_rab,
                                    String kurs_rab,
                                    String bupkontrRabota,
                                    String bup_nir,
                                    String nir,
                                    String individual,
                                    String konsult,
                                    String konsult_ind,
                                    String srs,
                                    String practica,
                                    String zachet,
                                    String ekzamen,
                                    String preddipl_prac,
                                    String rukovodstvo_vkr,
                                    String recenziya,
                                    String uchastie_v_gak,
                                    String vsego_chasov,
                                    String kreditsECTS,
                                    String teacher_code){
        Boolean rachetChasovAdded = false;
                
        try {
            
        final String sql = "INSERT INTO bup_raschet_chasov(disc_code, gr_code, "
                                                + "students_kolvo, semestr, group_course, "
                                                + "bup_lecture, lecture, "
                                                + "bup_seminar, seminar, "
                                                + "bup_kurs_rab, kurs_rab, bup_kontr_rabota,"
                                                + "bup_nir, nir, "
                                                + "individual, konsult, konsult_individ, "
                                                + "srs, practica, "
                                                + "zachet, ekzamen, "
                                                + "preddipl_pract, rukovodstvo_vkr, recenziya, uchastie_v_gak, "
                                                + "vsego_chasov,bup_kredits, teacher_code) "
                                                + "VALUES  (?, ?, ?, ?, ?, ?, ?, ?, "
                                                            + "?, ?, ?, ?, ?, "
                                                            + "?, ?, ?, ?, ?, "
                                                            + "?, ?, ?, ?, ?, "
                                                            + "?, ?, ?, ?, ?)";
                
            pst = conn.prepareStatement(sql);
                 
                pst.setString(1, disc_code);
                pst.setString(2, group_code);
                pst.setString(3, students_kolvo);
                pst.setString(4, semestr);
                pst.setString(5, group_course);
                pst.setString(6, bup_lecture);
                pst.setString(7, lecture);
                pst.setString(8, bup_seminar);
                pst.setString(9, seminar);
                pst.setString(10, bup_kurs_rab);
                pst.setString(11, kurs_rab);
                pst.setString(12, bupkontrRabota);
                pst.setString(13, bup_nir);
                pst.setString(14, nir);
                pst.setString(15, individual);
                pst.setString(16, konsult);
                pst.setString(17, konsult_ind);
                pst.setString(18, srs);
                pst.setString(19, practica);
                pst.setString(20, zachet);
                pst.setString(21, ekzamen);
                pst.setString(22, preddipl_prac);
                pst.setString(23, rukovodstvo_vkr);
                pst.setString(24, recenziya);
                pst.setString(25, uchastie_v_gak);
                pst.setString(26, vsego_chasov);
                pst.setString(27, kreditsECTS);
                pst.setString(28, teacher_code);
                
                int check = pst.executeUpdate();
                
                if(check > 0){
                   rachetChasovAdded= true;
                }else{
                    rachetChasovAdded = false;
                }
                    
            pst.close();
//            rs.close(); 
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return rachetChasovAdded;
    }
    
    public void updateRaschetChasovTable( JTable table ){

        try {
            String sql = "SELECT "
                            + "disc.disc_name AS 'Наименование дисциплины',"
                            + "rs.group_course AS 'Курс',"
                            + "rs.gr_code AS 'Группа',"
                            + "rs.students_kolvo AS 'Количество студентов в гр',"
                            + "rs.semestr AS 'Семестр',"
                            + "rs.bup_lecture AS 'Лекции по БУП',"
                            + "rs.lecture AS 'Лекции',"
                            + "rs.bup_seminar AS 'Семинары по БУП',"
                            + "rs.seminar AS 'Семинары',"
                            + "rs.bup_kurs_rab AS 'Курс. раб. по БУП',"
                            + "rs.kurs_rab AS 'Курсовая работа',"
                            + "rs.bup_nir AS 'НИР по БУП',"
                            + "rs.nir AS 'НИР',"
                            + "rs.individual AS 'Индивидуальные',"
                            + "rs.konsult AS 'Консультации по БУП',"
                            + "rs.konsult_individ AS 'Консультации',"
                            + "rs.srs AS 'СРС',"
                            + "rs.practica AS 'Практика',"
                            + "rs.zachet AS 'Зачет',"
                            + "rs.ekzamen AS 'Экзамен',"
                            + "rs.preddipl_pract AS 'Преддипломная практика',"
                            + "rs.rukovodstvo_vkr AS 'Руководство ВКР',"
                            + "rs.recenziya AS 'Рецензия',"
                            + "rs.uchastie_v_gak AS 'Участие в ГАК',"
                            + "rs.vsego_chasov AS 'Всего часов',"
                            + "rs.bup_kredits AS 'Кредиты', " 
                            + "t.teacher_lastname AS 'Фамилия преп.' "
                        + "FROM bup_raschet_chasov as rs "
                        + "LEFT JOIN spr_discipline as disc "
                        + "ON rs.disc_code=disc.disc_code "
                        + "LEFT JOIN teacher as t "
                        + "ON rs.teacher_code=t.teacher_code "
                        + "ORDER BY disc.disc_name ASC";
            
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            
            table.setModel(DbUtils.resultSetToTableModel(rs));
            
            }catch(Exception e){
               // JOptionPane.showMessageDialog(null, e);
                e.printStackTrace();
            }
        }
}
