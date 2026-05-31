/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package application.views;

import application.dao.AturanDao;
import application.dao.AturanDetailDao;
import application.dao.CriteriaDao;
import application.dao.GejalaDao;
import application.dao.PenyakitDao;
import application.dao.SubCriteriaDao;
import application.daoimpl.AturanDaoImpl;
import application.daoimpl.AturanDetailDaoImpl;
import application.daoimpl.CriteriaDaoImpl;
import application.daoimpl.GejalaDaoImpl;
import application.daoimpl.PenyakitDaoImpl;
import application.daoimpl.SubCriteriaDaoImpl;
import application.models.AturanDetailModel;
import application.models.AturanModel;
import application.models.CriteriaModel;
import application.models.GejalaModel;
import application.models.PenyakitModel;
import application.models.SubCriteriaModel;
import application.utils.DatabaseUtil;
import java.awt.Component;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author mhdja
 */
public class AturanView extends javax.swing.JPanel {
    public final PenyakitDao penyakitDao;
    public final AturanDao aturanDao;
    public final AturanDetailDao aturanDetailDao;
    private final Map<String, Integer> penyakitMap = new HashMap<>();
    
    public void getAllData() {
        List<AturanModel> aturanList = aturanDao.findAll();
        
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"Id", "Nama Aturan", "Id Penyakit", "Penyakit"}); // Adjust column names as needed

        // Populate the model with data from spareparts
        for (AturanModel aturan : aturanList) {
            model.addRow(new Object[]{
                aturan.getId(), 
                aturan.getRuleName(),
                aturan.getDiseaseId(),
                aturan.getDiseaseName()
            }); // Add more attributes as needed
        }
        
        // Set the table model to jTable1
        jTable1.setModel(model);
    }
    
    public void getPenyakitComboBox() {
        List<PenyakitModel> penyakitList = penyakitDao.findAll();
        
        jComboBoxPenyakit.removeAllItems(); // Clear previous items from combo box
        penyakitMap.clear(); // Clear previous mappings

        // Add new items to the combo box from the spareparts list
        for (PenyakitModel penyakit : penyakitList) {
            jComboBoxPenyakit.addItem(penyakit.getName()); // Add name to combo box
            penyakitMap.put(penyakit.getName(), penyakit.getId()); // Map name to ID
        }
    }
    
    public void resetForm() {
//        jTextFieldDeskripsi.setText(""); // kosongkan deskripsi
//        jTextFieldBobot.setText("");     // kosongkan bobot
//        jComboBox1.setSelectedIndex(0); // set ke item pertama
//        jTable1.clearSelection(); // hapus seleksi di tabel
    }
    
    private void loadGejala() {
        GejalaDao gejalaDao = new GejalaDaoImpl();

        jPanel1.removeAll();

        try {

            List<GejalaModel> gejalaList = gejalaDao.findAll();

            for (GejalaModel gejala : gejalaList) {

                JCheckBox cb = new JCheckBox(
                    gejala.getCode() + " - " + gejala.getName()
                );

                // Simpan ID gejala di checkbox
                cb.putClientProperty(
                    "symptomId",
                    gejala.getId()
                );

                jPanel1.add(cb);
            }

            jPanel1.revalidate();
            jPanel1.repaint();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                this,
                e.getMessage()
            );
        }
    }
    
    private void loadSelectedSymptoms(int ruleId) {

        try {

            // reset semua checkbox
            for (Component component : jPanel1.getComponents()) {

                if (component instanceof JCheckBox) {

                    JCheckBox cb = (JCheckBox) component;
                    cb.setSelected(false);
                }
            }

            List<AturanDetailModel> details =
                    aturanDetailDao.findByRuleId(ruleId);

            for (AturanDetailModel detail : details) {

                Integer symptomId = detail.getSymptomId();

                for (Component component : jPanel1.getComponents()) {

                    if (component instanceof JCheckBox) {

                        JCheckBox cb = (JCheckBox) component;

                        Integer checkboxSymptomId =
                            (Integer) cb.getClientProperty("symptomId");

                        if (checkboxSymptomId != null
                                && checkboxSymptomId.equals(symptomId)) {

                            cb.setSelected(true);
                            break;
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    

    /**
     * Creates new form SubCriteriaView
     */
    public AturanView() {
        initComponents();
        
        this.penyakitDao = new PenyakitDaoImpl();
        this.aturanDao = new AturanDaoImpl();
        this.aturanDetailDao = new AturanDetailDaoImpl();
        
        jPanel1.setLayout(new javax.swing.BoxLayout(
                jPanel1,
                javax.swing.BoxLayout.Y_AXIS
        ));
        
        loadGejala();
        
        getAllData();
        getPenyakitComboBox();
        
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int selectedRow = jTable1.getSelectedRow();
                if (selectedRow != -1) {
                    // Ambil nilai dari kolom tabel
                    String namaAturan = jTable1.getValueAt(selectedRow, 1).toString();
                    String idPenyakit = jTable1.getValueAt(selectedRow, 2).toString();
                    String namaPenyakit = jTable1.getValueAt(selectedRow, 3).toString();
                    
                    txtNama.setText(namaAturan);
                    // Set ke combobox
                    for (int i = 0; i < jComboBoxPenyakit.getItemCount(); i++) {
                        String item = jComboBoxPenyakit.getItemAt(i);
                        if (item.equals(namaPenyakit)) {
                            jComboBoxPenyakit.setSelectedIndex(i);
                            break;
                        }
                    }
                    
                    loadSelectedSymptoms(Integer.parseInt(idPenyakit));
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtNama = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jComboBoxPenyakit = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jButton2.setText("Simpan");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton4.setText("Hapus");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton1.setText("CETAK LAPORAN");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Nama Aturan");

        jLabel2.setText("Pilih Penyakit");

        jComboBoxPenyakit.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 214, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 141, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(jPanel1);

        jLabel3.setText("Pilih Gejala");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1)
                            .addComponent(txtNama)
                            .addComponent(jComboBoxPenyakit, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBoxPenyakit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 609, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(128, 128, 128))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 325, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        
        String nama = txtNama.getText();
        String selectedName = (String) jComboBoxPenyakit.getSelectedItem();
        
        Integer selectedId = null;
        if (selectedName != null) {
            selectedId = penyakitMap.get(selectedName); // Get ID based on name
        }
        
        System.out.println("Nama" + nama);
        System.out.println("Penyakit" + selectedName + "ID" + selectedId);
        
        AturanModel aturan = new AturanModel();
        aturan.setDiseaseId(selectedId);
        aturan.setRuleName(nama);
        
        int ruleId = aturanDao.create(aturan);
        
        for(Component comp : jPanel1.getComponents()) {

        if(comp instanceof JCheckBox) {

            JCheckBox cb = (JCheckBox) comp;

            if(cb.isSelected()) {

                Integer symptomId =
                    (Integer) cb.getClientProperty("symptomId");

                AturanDetailModel detail =
                    new AturanDetailModel();

                detail.setRuleId(ruleId);
                detail.setSymptomId(symptomId);

                aturanDetailDao.create(detail);
            }
        }
    }
//        String description = jTextFieldDeskripsi.getText();
//        int bobot = Integer.parseInt(jTextFieldBobot.getText());
//        String selectedName = (String) jComboBox1.getSelectedItem();
//        Integer selectedId = null;
//        if (selectedName != null) {
//            selectedId = criteriaMap.get(selectedName); // Get ID based on name
//        }
//        
//        SubCriteriaModel subCriteria = new SubCriteriaModel();
//        subCriteria.setIdCriteria(selectedId);
////        subCriteria.setDeskripsi(description);
////        subCriteria.setBobot(bobot);
////        
//        int result = subCriteriaDao.create(subCriteria);
//        
//        if (result > 0) {
//            JOptionPane.showMessageDialog(this, "Data added successfully!");
//            
//            getAllData();
//            resetForm();
//        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
//        int selectedRow = jTable1.getSelectedRow();
//    
//        if (selectedRow >= 0) {
//            int idSubCriteria = (int) jTable1.getValueAt(selectedRow, 0); // kolom 0 diasumsikan kolom ID
//            SubCriteriaModel subCriteria = new SubCriteriaModel();
//            subCriteria.setId(idSubCriteria);
//
//            int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
//            if (confirm == JOptionPane.YES_OPTION) {
//                subCriteriaDao.delete(subCriteria);
//                JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
//                getAllData();
//                resetForm();
//            }
//        } else {
//            JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus!");
//        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
         try {
            String templateName = "LaporanSubKriteriaBiznet.jrxml";
            InputStream reportStream = ReportView.class.getResourceAsStream("/resources/reports/" + templateName);
            JasperDesign jd = JRXmlLoader.load(reportStream);

            Connection dbConnection = DatabaseUtil.getInstance().getConnection();

            JasperReport jr = JasperCompileManager.compileReport(jd);

            HashMap parameter = new HashMap();
            parameter.put("PATH","src/resources/images/");
            
            JasperPrint jp = JasperFillManager.fillReport(jr,parameter, dbConnection);
            JasperViewer.viewReport(jp, false);
        } catch (JRException ex) {
            Logger.getLogger(ReportView.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBoxPenyakit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtNama;
    // End of variables declaration//GEN-END:variables
}
