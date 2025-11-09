import java.io.*;
import java.net.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.json.simple.*;
import org.json.simple.parser.*;
import java.util.ArrayList;

public class FrmCekCuaca extends javax.swing.JFrame {

    private final String API_KEY = "85971d52a7e63b21f82db242b900105c";
    private DefaultTableModel model;
    private ArrayList<String> favorit = new ArrayList<>();

    public FrmCekCuaca() {
        initComponents();
        setTitle("Aplikasi Cek Cuaca Sederhana");
        setLocationRelativeTo(null);
        model = new DefaultTableModel(new String[]{"Kota", "Kondisi", "Suhu (°C)"}, 0);
        tblData.setModel(model);
    }
   
    public JSONObject getWeatherData(String city) {
    try {
        String urlStr = "https://api.openweathermap.org/data/2.5/weather?q=" 
                + city + "&appid=" + API_KEY + "&units=metric&lang=id";
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        conn.disconnect();

        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(content.toString());
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal mengambil data cuaca: " + e.getMessage());
        return null;
    }
}

    public void tampilkanCuaca(String city) {
    JSONObject data = getWeatherData(city);
    if (data == null) return;

    JSONObject main = (JSONObject) data.get("main");
    JSONArray weatherArray = (JSONArray) data.get("weather");
    JSONObject weather = (JSONObject) weatherArray.get(0);

    String kondisi = (String) weather.get("description");
    double suhu = (double) main.get("temp");

    lblHasil.setText("Kondisi: " + kondisi + ", Suhu: " + suhu + "°C");

    // Pilih ikon berdasarkan kondisi
   String iconPath = "/images/cloudy.png"; // default

if (kondisi.contains("cerah"))
    iconPath = "/images/sun.png";
else if (kondisi.contains("hujan"))
    iconPath = "/images/rainy-day.png";
else if (kondisi.contains("awan"))
    iconPath = "/images/cloudy.png";

lblIkon.setIcon(new ImageIcon(getClass().getResource(iconPath)));

    // Tambah ke tabel
    model.addRow(new Object[]{city, kondisi, suhu});
}

    private void btnCekActionPerformed(java.awt.event.ActionEvent evt) {                                        
    String city = txtKota.getText().trim();
    if (city.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Masukkan nama kota terlebih dahulu!");
        return;
    }
    tampilkanCuaca(city);
}                                       

private void btnSimpanFavoritActionPerformed(java.awt.event.ActionEvent evt) {                                                
    String city = txtKota.getText().trim();
    if (!city.isEmpty() && !favorit.contains(city)) {
        favorit.add(city);
        cmbFavorit.addItem(city);
        JOptionPane.showMessageDialog(this, "Kota ditambahkan ke favorit!");
    }
}                                               

private void cmbFavoritItemStateChanged(java.awt.event.ItemEvent evt) {                                            
    if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
        String selectedCity = cmbFavorit.getSelectedItem().toString();
        tampilkanCuaca(selectedCity);
    }
}                                           

private void btnSimpanCSVActionPerformed(java.awt.event.ActionEvent evt) {                                            
    try (PrintWriter writer = new PrintWriter(new File("data_cuaca.csv"))) {
        for (int i = 0; i < model.getRowCount(); i++) {
            writer.println(model.getValueAt(i, 0) + "," +
                           model.getValueAt(i, 1) + "," +
                           model.getValueAt(i, 2));
        }
        JOptionPane.showMessageDialog(this, "Data cuaca disimpan ke data_cuaca.csv");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal menyimpan file: " + e.getMessage());
    }
}                                           

private void btnMuatCSVActionPerformed(java.awt.event.ActionEvent evt) {                                          
    try (BufferedReader reader = new BufferedReader(new FileReader("data_cuaca.csv"))) {
        model.setRowCount(0);
        String line;
        while ((line = reader.readLine()) != null) {
            model.addRow(line.split(","));
        }
        JOptionPane.showMessageDialog(this, "Data cuaca dimuat kembali!");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal memuat file: " + e.getMessage());
    }
}                                         

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FrmCekCuaca.class.getName());

    /**
     * Creates new form FrmCekCuaca
     */
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblJudul = new javax.swing.JLabel();
        lblKota = new javax.swing.JLabel();
        txtKota = new javax.swing.JTextField();
        btnCek = new javax.swing.JButton();
        cmbFavorit = new javax.swing.JComboBox<>();
        btnSimpanFavorit = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblData = new javax.swing.JTable();
        btnSimpanCSV = new javax.swing.JButton();
        btnMuatCSV = new javax.swing.JButton();
        lblIkon = new javax.swing.JLabel();
        lblHasil = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblJudul.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblJudul.setText("Aplikasi Cek Cuaca Sederhana");

        lblKota.setText("Masukkan Nama Kota");

        btnCek.setText("Cek Cuaca");

        cmbFavorit.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Banjarmasin", "Bandung", "Jakarta" }));

        btnSimpanFavorit.setText("Simpan Favorit");

        tblData.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblData);

        btnSimpanCSV.setText("Simpan CSV");

        btnMuatCSV.setText("Muat CSV");

        lblHasil.setText("Kondisi Cuaca:");

        jLabel1.setText("Pilih dari Favorit");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSimpanCSV)
                .addGap(85, 85, 85)
                .addComponent(btnMuatCSV)
                .addGap(101, 101, 101))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(lblKota))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtKota, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnCek))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(cmbFavorit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnSimpanFavorit))))
                            .addComponent(lblHasil, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addComponent(lblJudul))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(131, 131, 131)
                        .addComponent(lblIkon, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(lblJudul)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblKota)
                    .addComponent(txtKota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCek))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbFavorit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(btnSimpanFavorit))
                .addGap(18, 18, 18)
                .addComponent(lblHasil)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblIkon, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnMuatCSV)
                    .addComponent(btnSimpanCSV))
                .addGap(27, 27, 27))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new FrmCekCuaca().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCek;
    private javax.swing.JButton btnMuatCSV;
    private javax.swing.JButton btnSimpanCSV;
    private javax.swing.JButton btnSimpanFavorit;
    private javax.swing.JComboBox<String> cmbFavorit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblHasil;
    private javax.swing.JLabel lblIkon;
    private javax.swing.JLabel lblJudul;
    private javax.swing.JLabel lblKota;
    private javax.swing.JTable tblData;
    private javax.swing.JTextField txtKota;
    // End of variables declaration//GEN-END:variables
}
