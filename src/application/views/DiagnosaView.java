/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package application.views;

import application.dao.AlternatifDao;
import application.dao.CriteriaDao;
import application.dao.DiagnosaDao;
import application.dao.GejalaDao;
import application.dao.KaryawanDao;
import application.dao.SubCriteriaDao;
import application.daoimpl.AlternatifDaoImpl;
import application.daoimpl.CriteriaDaoImpl;
import application.daoimpl.DiagnosaDaoImpl;
import application.daoimpl.GejalaDaoImpl;
import application.daoimpl.KaryawanDaoImpl;
import application.daoimpl.SubCriteriaDaoImpl;
import application.models.AlternatifModel;
import application.models.CriteriaModel;
import application.models.DiagnosaModel;
import application.models.GejalaModel;
import application.models.KaryawanModel;
import application.models.SubCriteriaModel;
import application.utils.ComboBoxItem;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author mhdja
 */
public class DiagnosaView extends javax.swing.JPanel {
    public final GejalaDao gejalaDao;
    public final DiagnosaDao diagnosaDao;
    private JLabel labelContent;
    private Map<Integer, JRadioButton> selectedGejala = new HashMap<>();
    
//    public void getAllData() {
//        List<AlternatifModel> alternatifList = alternatifDao.findAll(); // Mengambil data dari metode findAll()
//
//        // Membuat model untuk jTable1 dengan kolom-kolom yang sesuai
//        DefaultTableModel model = new DefaultTableModel();
//        model.setColumnIdentifiers(new Object[]{"Nama Supllier", "Nama Kriteria", "Bobot Alternatif"}); // Menentukan nama kolom
//
//        // Mengisi model dengan data dari alternatifList
//        for (AlternatifModel alternatif : alternatifList) {
//            model.addRow(new Object[]{
//                alternatif.getNameAlternatif(), // Nama Pelanggan
//                alternatif.getNameKriteria(),   // Nama Kriteria
//                alternatif.getBobotAlternatif(), // Bobot Alternatif
//            });
//        }
//
//        // Set model ke jTable1
//        jTable1.setModel(model);
//    }
    
    public void questionGejala() {
        jPanel3.removeAll();

        jPanel3.setLayout(new BoxLayout(jPanel3, BoxLayout.Y_AXIS));
        jPanel3.setBackground(new Color(245, 245, 245));

        List<GejalaModel> listGejala = gejalaDao.findAll();

        int nomor = 1;

        for (GejalaModel gejala : listGejala) {

            // =========================
            // CARD PANEL
            // =========================
            JPanel card = new JPanel(new BorderLayout());
            card.setBackground(Color.WHITE);
            card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
            card.setPreferredSize(new Dimension(0, 70));

            card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(220, 220, 220)),
                    BorderFactory.createEmptyBorder(10, 15, 10, 15)
            ));

            // =========================
            // PANEL KIRI (NOMOR)
            // =========================
            JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            leftPanel.setOpaque(false);

            JLabel lblNo = new JLabel(String.valueOf(nomor));
            lblNo.setFont(new Font("Segoe UI", Font.BOLD, 16));

            lblNo.setForeground(Color.WHITE);
            lblNo.setOpaque(true);
            lblNo.setBackground(new Color(33, 150, 243));
            lblNo.setHorizontalAlignment(SwingConstants.CENTER);

            lblNo.setPreferredSize(new Dimension(35, 35));

            leftPanel.add(lblNo);

            // =========================
            // PANEL TENGAH (PERTANYAAN)
            // =========================
            JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            centerPanel.setOpaque(false);

            JLabel lblQuestion = new JLabel(
                    "Apakah " + gejala.getName().toLowerCase() + "?"
            );

            lblQuestion.setFont(new Font("Segoe UI", Font.PLAIN, 15));

            centerPanel.add(lblQuestion);

            // =========================
            // PANEL KANAN (JAWABAN)
            // =========================
            JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0));
            rightPanel.setOpaque(false);

            JRadioButton rbYa = new JRadioButton("Ya");
            JRadioButton rbTidak = new JRadioButton("Tidak");

            rbYa.setOpaque(false);
            rbTidak.setOpaque(false);

            rbYa.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            rbTidak.setFont(new Font("Segoe UI", Font.PLAIN, 14));

            ButtonGroup group = new ButtonGroup();
            group.add(rbYa);
            group.add(rbTidak);
            
            // simpan gejala id -> radio ya
            selectedGejala.put(gejala.getId(), rbYa);

            rightPanel.add(rbYa);
            rightPanel.add(rbTidak);

            // =========================
            // ADD COMPONENT
            // =========================
            card.add(leftPanel, BorderLayout.WEST);
            card.add(centerPanel, BorderLayout.CENTER);
            card.add(rightPanel, BorderLayout.EAST);

            // JARAK ANTAR CARD
            jPanel3.add(card);
            jPanel3.add(Box.createVerticalStrut(8));

            nomor++;
        }

        jPanel3.revalidate();
        jPanel3.repaint();
    }
    
    private void showDiagnosa(DiagnosaModel result) {

        jPanelHasil.removeAll();
        jPanelHasil.setLayout(new BorderLayout());
        jPanelHasil.setBackground(new Color(245, 245, 245));

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);

        card.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                    new Color(220, 220, 220)
                ),
                BorderFactory.createEmptyBorder(
                    20, 20, 20, 20
                )
            )
        );

        JLabel lblTitle =
                new JLabel("HASIL DIAGNOSA");

        lblTitle.setFont(
                new Font("Segoe UI", Font.BOLD, 22)
        );

        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblDisease =
                new JLabel(result.getDiseaseName());

        lblDisease.setFont(
                new Font("Segoe UI", Font.BOLD, 28)
        );

        lblDisease.setForeground(
                new Color(33, 150, 243)
        );

        lblDisease.setAlignmentX(Component.LEFT_ALIGNMENT);

        double percentage = result.getPercentage();

        JLabel lblPercentage =
                new JLabel(
                        String.format(
                                "Tingkat Kecocokan : %.2f%%",
                                percentage
                        )
                );

        lblPercentage.setFont(
                new Font("Segoe UI", Font.BOLD, 15)
        );

        if (percentage >= 80) {

            lblPercentage.setForeground(
                    new Color(46, 125, 50)
            );

        } else if (percentage >= 50) {

            lblPercentage.setForeground(
                    new Color(255, 143, 0)
            );

        } else {

            lblPercentage.setForeground(
                    new Color(198, 40, 40)
            );
        }

        lblPercentage.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblDescTitle =
                new JLabel("Deskripsi");

        lblDescTitle.setFont(
                new Font("Segoe UI", Font.BOLD, 16)
        );

        lblDescTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextArea txtDeskripsi =
                new JTextArea(result.getDescription());

        txtDeskripsi.setEditable(false);
        txtDeskripsi.setLineWrap(true);
        txtDeskripsi.setWrapStyleWord(true);
        txtDeskripsi.setOpaque(false);
        txtDeskripsi.setFocusable(false);

        txtDeskripsi.setFont(
                new Font("Segoe UI", Font.PLAIN, 14)
        );

        txtDeskripsi.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblSolutionTitle =
                new JLabel("Solusi");

        lblSolutionTitle.setFont(
                new Font("Segoe UI", Font.BOLD, 16)
        );

        lblSolutionTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextArea txtSolusi =
                new JTextArea(result.getSolution());

        txtSolusi.setEditable(false);
        txtSolusi.setLineWrap(true);
        txtSolusi.setWrapStyleWord(true);
        txtSolusi.setOpaque(false);
        txtSolusi.setFocusable(false);

        txtSolusi.setFont(
                new Font("Segoe UI", Font.PLAIN, 14)
        );

        txtSolusi.setAlignmentX(Component.LEFT_ALIGNMENT);

        JSeparator separator1 = new JSeparator();
        separator1.setAlignmentX(Component.LEFT_ALIGNMENT);

        JSeparator separator2 = new JSeparator();
        separator2.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(lblTitle);
        card.add(Box.createVerticalStrut(15));

        card.add(lblDisease);
        card.add(Box.createVerticalStrut(5));

        card.add(lblPercentage);
        card.add(Box.createVerticalStrut(20));

        card.add(separator1);
        card.add(Box.createVerticalStrut(15));

        card.add(lblDescTitle);
        card.add(Box.createVerticalStrut(8));

        card.add(txtDeskripsi);
        card.add(Box.createVerticalStrut(20));

        card.add(separator2);
        card.add(Box.createVerticalStrut(15));

        card.add(lblSolutionTitle);
        card.add(Box.createVerticalStrut(8));

        card.add(txtSolusi);

        jPanelHasil.add(card, BorderLayout.CENTER);

        jPanelHasil.revalidate();
        jPanelHasil.repaint();
    }
    /**
     * Creates new form AlternatifView
     */
    public DiagnosaView() {
      this.gejalaDao = new GejalaDaoImpl();
      this.diagnosaDao = new DiagnosaDaoImpl();

      initComponents();
      
      jSplitPane1.setResizeWeight(0.8);
      
      questionGejala();
  }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jPanelHasil = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        jPanelHasil.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanelHasilLayout = new javax.swing.GroupLayout(jPanelHasil);
        jPanelHasil.setLayout(jPanelHasilLayout);
        jPanelHasilLayout.setHorizontalGroup(
            jPanelHasilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 556, Short.MAX_VALUE)
        );
        jPanelHasilLayout.setVerticalGroup(
            jPanelHasilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 535, Short.MAX_VALUE)
        );

        jSplitPane1.setRightComponent(jPanelHasil);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 735, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 457, Short.MAX_VALUE)
        );

        jScrollPane2.setViewportView(jPanel3);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("DIAGNOSA PENYAKIT");

        jButton1.setText("CEK PENYAKIT");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1)
                            .addComponent(jLabel1))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );

        jSplitPane1.setLeftComponent(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jSplitPane1)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        List<Integer> symptomIds = new ArrayList<>();

        for (Map.Entry<Integer, JRadioButton> entry : selectedGejala.entrySet()) {

            Integer gejalaId = entry.getKey();
            JRadioButton rbYa = entry.getValue();

            if (rbYa.isSelected()) {
                symptomIds.add(gejalaId);
            }
        }
        
        List<DiagnosaModel> results =
        diagnosaDao.diagnose(symptomIds);
        
        jPanelHasil.setBackground(new Color(245, 245, 245));
        
        showDiagnosa(results.get(0));
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanelHasil;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    // End of variables declaration//GEN-END:variables
}
