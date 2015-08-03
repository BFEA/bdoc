
package bdoc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class SettingsKG extends javax.swing.JFrame {
            Connection conn;
            ResultSet rs;
            PreparedStatement pst;

    public SettingsKG() {
        initComponents();
        conn = dbconnector.ConnectorDb();
        FillAll();
    }
    
    private void FillAll(){
        
         
        try{
             String sql = "SELECT * FROM spr_settingskg";
                
                    pst = conn.prepareStatement(sql);
                        rs=pst.executeQuery();
                    
                   if(rs.next()){  
                String short_name = rs.getString("A_NAMES");
                    textFieldShortName.setText(short_name);
                String long_name = rs.getString("A_NAMEL");
                    textFieldLongName.setText(long_name);
                String adr_name = rs.getString("A_ADRES");
                    textFieldAdress.setText(adr_name);
                String phone_name = rs.getString("A_PHONE");
                    textFieldPhoneNumber.setText(phone_name);
                String phonefax_name = rs.getString("A_FAX");
                    textFieldFaxNumber.setText(phonefax_name);
                String okpo_name = rs.getString("A_OKPO");
                    textFieldCodeOkpo.setText(okpo_name);
                String inn_name = rs.getString("A_INN");
                    textFieldCodeInn.setText(inn_name);
                String rector_name = rs.getString("A_RECTOR");
                    textFieldNameRector.setText(rector_name);
                String glbuh_name = rs.getString("A_GLBUH");
                    textFieldNameBuchgalter.setText(glbuh_name);
                String uotdel_name = rs.getString("A_UOTDEL");
                    textFieldNameNachalnik.setText(uotdel_name);
                String nachUchG = rs.getString("A_YEARS");
                    tekUchgS.setText(nachUchG);
                String konUchG = rs.getString("A_YEARE");
                    tekUchgF.setText(konUchG);
                String editFrom = rs.getString("A_DATES");
                    ((JTextField)fromEdit.getDateEditor().getUiComponent()).setText(editFrom);
                String editTo = rs.getString("A_DATEE");
                    ((JTextField)toEdit.getDateEditor().getUiComponent()).setText(editFrom);
                String otlS = rs.getString("A_1");
                    otlichnoS.setText(otlS);
                String otlE = rs.getString("A_2");
                    otlichnoE.setText(otlE);
                String horS = rs.getString("A_3");
                    horoshoS.setText(horS);
                String horE = rs.getString("A_4");
                    horoshoE.setText(horE);
                String udvS = rs.getString("A_5");
                    udovletS.setText(udvS);
                String udvE = rs.getString("A_6");
                    udovletE.setText(udvE);
                String recStatus = rs.getString("A_PRIM");
                    textFieldNameRectorStatus.setText(recStatus);
                   
                     Integer markAllow = rs.getInt("A_VED");
                    if(markAllow==1){
                        checkAllowPoints.setSelected(true);
                    }else{
                        checkAllowPoints.setSelected(false);
                    }
                   
                   }
                
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,"FillAll action "+e);
            }
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        rekvizityVUZA = new javax.swing.JPanel();
        panelSettingsInternal = new javax.swing.JPanel();
        longName = new javax.swing.JLabel();
        textFieldAdress = new javax.swing.JTextField();
        phoneNumber = new javax.swing.JLabel();
        textFieldFaxNumber = new javax.swing.JTextField();
        nameBuchgalter = new javax.swing.JLabel();
        textFieldShortName = new javax.swing.JTextField();
        nameRector = new javax.swing.JLabel();
        textFieldNameNachalnik = new javax.swing.JTextField();
        nameNachalnikuch = new javax.swing.JLabel();
        shortName = new javax.swing.JLabel();
        textFieldPhoneNumber = new javax.swing.JTextField();
        adress = new javax.swing.JLabel();
        textFieldCodeOkpo = new javax.swing.JTextField();
        textFieldLongName = new javax.swing.JTextField();
        codeInn = new javax.swing.JLabel();
        textFieldCodeInn = new javax.swing.JTextField();
        textFieldNameRector = new javax.swing.JTextField();
        faxNumber = new javax.swing.JLabel();
        codeOKPO = new javax.swing.JLabel();
        textFieldNameBuchgalter = new javax.swing.JTextField();
        textFieldNameRectorStatus = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        checkAllowPoints = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        fromEdit = new com.toedter.calendar.JDateChooser();
        toEdit = new com.toedter.calendar.JDateChooser();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tekUchgS = new javax.swing.JTextField();
        tekUchgF = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        otlichnoS = new javax.swing.JTextField();
        otlichnoE = new javax.swing.JTextField();
        horoshoE = new javax.swing.JTextField();
        horoshoS = new javax.swing.JTextField();
        udovletS = new javax.swing.JTextField();
        udovletE = new javax.swing.JTextField();
        panelSettingsDown = new javax.swing.JPanel();
        btnAcceptSettings = new javax.swing.JButton();
        btnCancelSettings = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(791, 513));

        rekvizityVUZA.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        longName.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        longName.setText("Наименование полное . . . . . . . .");

        phoneNumber.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        phoneNumber.setText("Телефон . . . . . . . . . . . . . . . . . . . . .");

        nameBuchgalter.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        nameBuchgalter.setText("Главный бухгалтер . . . . . . . . . . . . ");

        nameRector.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        nameRector.setText("Ректор . . . . . . . . . . . . . . . . . . . . . . .");

        nameNachalnikuch.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        nameNachalnikuch.setText("Начальник учебного отдела . . . ");

        shortName.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        shortName.setText("Наименование короткое . . . . . . ");

        adress.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        adress.setText("Адрес . . . . . . . . . . . . . . . . . . . . . . . .");

        codeInn.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        codeInn.setText("И Н Н . . . . . . . . . . . . . . . . . . . . . . . .");

        faxNumber.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        faxNumber.setText("Факс . . . . . . . . . . . . . . . . . . . . . . . . .");

        codeOKPO.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        codeOKPO.setText("Код ОКПО . . . . . . . . . . . . . . . . . . . .");

        javax.swing.GroupLayout panelSettingsInternalLayout = new javax.swing.GroupLayout(panelSettingsInternal);
        panelSettingsInternal.setLayout(panelSettingsInternalLayout);
        panelSettingsInternalLayout.setHorizontalGroup(
            panelSettingsInternalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSettingsInternalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelSettingsInternalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelSettingsInternalLayout.createSequentialGroup()
                        .addGroup(panelSettingsInternalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(shortName)
                            .addComponent(longName)
                            .addComponent(adress)
                            .addComponent(phoneNumber)
                            .addComponent(faxNumber)
                            .addComponent(codeOKPO)
                            .addComponent(codeInn)
                            .addComponent(nameRector))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelSettingsInternalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textFieldCodeInn, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textFieldCodeOkpo, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textFieldFaxNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textFieldPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textFieldAdress, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textFieldLongName, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textFieldShortName, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelSettingsInternalLayout.createSequentialGroup()
                                .addComponent(textFieldNameRector, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(textFieldNameRectorStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(panelSettingsInternalLayout.createSequentialGroup()
                        .addGroup(panelSettingsInternalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nameBuchgalter)
                            .addComponent(nameNachalnikuch))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelSettingsInternalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textFieldNameNachalnik, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textFieldNameBuchgalter, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(66, Short.MAX_VALUE))
        );
        panelSettingsInternalLayout.setVerticalGroup(
            panelSettingsInternalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSettingsInternalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelSettingsInternalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(shortName)
                    .addGroup(panelSettingsInternalLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(textFieldShortName, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelSettingsInternalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(longName)
                    .addComponent(textFieldLongName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelSettingsInternalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(adress)
                    .addComponent(textFieldAdress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelSettingsInternalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(phoneNumber)
                    .addComponent(textFieldPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelSettingsInternalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(faxNumber)
                    .addComponent(textFieldFaxNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelSettingsInternalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(codeOKPO)
                    .addComponent(textFieldCodeOkpo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelSettingsInternalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(codeInn)
                    .addComponent(textFieldCodeInn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelSettingsInternalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameRector)
                    .addComponent(textFieldNameRector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textFieldNameRectorStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelSettingsInternalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameBuchgalter)
                    .addComponent(textFieldNameBuchgalter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelSettingsInternalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameNachalnikuch)
                    .addComponent(textFieldNameNachalnik, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout rekvizityVUZALayout = new javax.swing.GroupLayout(rekvizityVUZA);
        rekvizityVUZA.setLayout(rekvizityVUZALayout);
        rekvizityVUZALayout.setHorizontalGroup(
            rekvizityVUZALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelSettingsInternal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        rekvizityVUZALayout.setVerticalGroup(
            rekvizityVUZALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rekvizityVUZALayout.createSequentialGroup()
                .addComponent(panelSettingsInternal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 32, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Общие", rekvizityVUZA);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Работа с ведомостями"));

        checkAllowPoints.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        checkAllowPoints.setText("Разрешить преподователям выставлять оценки с ");

        jLabel3.setText("по");

        fromEdit.setDateFormatString("yyyy-MM-dd");

        toEdit.setDateFormatString("yyyy-MM-dd");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(checkAllowPoints)
                .addGap(14, 14, 14)
                .addComponent(fromEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(22, 22, 22)
                .addComponent(toEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(toEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkAllowPoints)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel3)
                        .addComponent(fromEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Учебный год"));
        jPanel2.setToolTipText("");
        jPanel2.setName(""); // NOI18N

        jLabel1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel1.setText("Текущий учебный год . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .");

        jLabel2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel2.setText(" . . . . . . . . ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tekUchgS, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tekUchgF, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(90, 90, 90))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tekUchgS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(tekUchgF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Шкала оценок"));

        jLabel4.setText("Отлично . . . . . . . . . . . . . . . . . ");

        jLabel5.setText("Хорошо . . . . . . . . . . . . . . . . . .");

        jLabel6.setText("Удовлетворительно. . . . . . . . .");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(171, 171, 171)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(otlichnoS, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(udovletS, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(horoshoS, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(31, 31, 31)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(udovletE, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(otlichnoE, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(horoshoE, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(115, 115, 115))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(otlichnoS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(otlichnoE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(horoshoS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(horoshoE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(udovletE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(udovletS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Специальные", jPanel1);

        btnAcceptSettings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/accept.png"))); // NOI18N
        btnAcceptSettings.setText("Применить");
        btnAcceptSettings.setToolTipText("Применить настройки");
        btnAcceptSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAcceptSettingsActionPerformed(evt);
            }
        });

        btnCancelSettings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cancel.png"))); // NOI18N
        btnCancelSettings.setText("Отмена");
        btnCancelSettings.setToolTipText("Отмена настроек");
        btnCancelSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelSettingsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelSettingsDownLayout = new javax.swing.GroupLayout(panelSettingsDown);
        panelSettingsDown.setLayout(panelSettingsDownLayout);
        panelSettingsDownLayout.setHorizontalGroup(
            panelSettingsDownLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSettingsDownLayout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addComponent(btnAcceptSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 212, Short.MAX_VALUE)
                .addComponent(btnCancelSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(130, 130, 130))
        );
        panelSettingsDownLayout.setVerticalGroup(
            panelSettingsDownLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSettingsDownLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnAcceptSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnCancelSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(panelSettingsDown, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 83, Short.MAX_VALUE)
                .addComponent(panelSettingsDown, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        setSize(new java.awt.Dimension(799, 546));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAcceptSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAcceptSettingsActionPerformed

        try {
            Integer razrVystOc = 0;
        
            if(checkAllowPoints.isSelected()){
                razrVystOc = 1;
            }else{
                razrVystOc = 0;
            }
            
            String value1 = textFieldShortName.getText();
            String value2 = textFieldLongName.getText();
            String value3 = textFieldAdress.getText();
            String value4 = textFieldPhoneNumber.getText();
            String value5 = textFieldFaxNumber.getText();
            String value6 = textFieldCodeOkpo.getText();
            String value7 = textFieldCodeInn.getText();
            String value8 = textFieldNameRector.getText();
            String value9 = textFieldNameBuchgalter.getText();
            String value10 = textFieldNameNachalnik.getText();
            String value11 = tekUchgS.getText();
            String value12 = tekUchgF.getText();
            Integer value13 = razrVystOc;
            String value14 = ((JTextField)fromEdit.getDateEditor().getUiComponent()).getText();
            String value15 = ((JTextField)toEdit.getDateEditor().getUiComponent()).getText();
            String value16 = otlichnoS.getText();
            String value17 = otlichnoE.getText();
            String value18 = horoshoS.getText();
            String value19 = horoshoE.getText();
            String value20 = udovletS.getText();
            String value21 = udovletE.getText();
            String value22 = textFieldNameRectorStatus.getText();

            String sql = "UPDATE spr_settingskg SET A_NAMES='"+value1+"',A_NAMEL='"+value2+"',A_ADRES='"+value3
            +"',A_PHONE='"+value4
            +"',A_PRIM='"+value22
            +"',A_FAX='"+value5
            +"',A_OKPO='"+value6
            +"',A_INN='"+value7
            +"',A_RECTOR='"+value8
            +"',A_GLBUH='"+value9
            +"',A_UOTDEL='"+value10
            +"',A_YEARS='"+value11
            +"',A_YEARE='"+value12
            +"',A_VED='"+value13
            +"',A_DATES='"+value14
            +"',A_DATEE='"+value15
            +"',A_1='"+value16+"',A_2='"+value17+"',A_3='"+value18+"',A_4='"+value19+"',A_5='"+value20+"',A_6='"+value21
            +"' WHERE A_ID=1";
            pst = conn.prepareStatement(sql);
            pst.execute();

            JOptionPane.showMessageDialog(null,"Данные успешно обновлены");

        }catch(Exception e){

            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_btnAcceptSettingsActionPerformed

    private void btnCancelSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelSettingsActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelSettingsActionPerformed


    public static void main(String args[]) {
       
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
            java.util.logging.Logger.getLogger(SettingsKG.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SettingsKG.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SettingsKG.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SettingsKG.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

      
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SettingsKG().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel adress;
    private javax.swing.JButton btnAcceptSettings;
    private javax.swing.JButton btnCancelSettings;
    private javax.swing.JCheckBox checkAllowPoints;
    private javax.swing.JLabel codeInn;
    private javax.swing.JLabel codeOKPO;
    private javax.swing.JLabel faxNumber;
    private com.toedter.calendar.JDateChooser fromEdit;
    private javax.swing.JTextField horoshoE;
    private javax.swing.JTextField horoshoS;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel longName;
    private javax.swing.JLabel nameBuchgalter;
    private javax.swing.JLabel nameNachalnikuch;
    private javax.swing.JLabel nameRector;
    private javax.swing.JTextField otlichnoE;
    private javax.swing.JTextField otlichnoS;
    private javax.swing.JPanel panelSettingsDown;
    private javax.swing.JPanel panelSettingsInternal;
    private javax.swing.JLabel phoneNumber;
    private javax.swing.JPanel rekvizityVUZA;
    private javax.swing.JLabel shortName;
    private javax.swing.JTextField tekUchgF;
    private javax.swing.JTextField tekUchgS;
    private javax.swing.JTextField textFieldAdress;
    private javax.swing.JTextField textFieldCodeInn;
    private javax.swing.JTextField textFieldCodeOkpo;
    private javax.swing.JTextField textFieldFaxNumber;
    private javax.swing.JTextField textFieldLongName;
    private javax.swing.JTextField textFieldNameBuchgalter;
    private javax.swing.JTextField textFieldNameNachalnik;
    private javax.swing.JTextField textFieldNameRector;
    private javax.swing.JTextField textFieldNameRectorStatus;
    private javax.swing.JTextField textFieldPhoneNumber;
    private javax.swing.JTextField textFieldShortName;
    private com.toedter.calendar.JDateChooser toEdit;
    private javax.swing.JTextField udovletE;
    private javax.swing.JTextField udovletS;
    // End of variables declaration//GEN-END:variables
}
