package App;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class InformasiSupplierPage extends JFrame {
    private JPanel infoSupplierPanel;
    private JLabel logo;
    private JScrollPane dataSupplierScrollPanel;
    private JButton berandaNav;
    private JButton barangMasukNav;
    private JButton barangKeluarNav;
    private JButton dataBarangNav;
    private JButton logoutButton;
    private JButton informasiSupplierNav;
    private JTable dataSupplierTable;
    private Connection connection;

    public InformasiSupplierPage() {
        setContentPane(infoSupplierPanel);
        setTitle("IndoGudang.com/info-supplier");
        setSize(700,500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);

        getConnection();

        displayDataSupplier();
        berandaNav.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HomePage homePage = new HomePage();
                SwingUtilities.getWindowAncestor(infoSupplierPanel).dispose();
            }
        });
        barangMasukNav.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BarangMasukPage barangMasukPage = new BarangMasukPage();
                SwingUtilities.getWindowAncestor(infoSupplierPanel).dispose();
            }
        });
        barangKeluarNav.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BarangKeluarPage barangKeluarPage = new BarangKeluarPage();
                SwingUtilities.getWindowAncestor(infoSupplierPanel).dispose();
            }
        });
        dataBarangNav.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DataBarangPage dataBarangPage = new DataBarangPage();
                SwingUtilities.getWindowAncestor(infoSupplierPanel).dispose();
            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showOptionDialog(
                        InformasiSupplierPage.this,
                        "Apakah anda yakin ingin keluar?" ,
                        "Keluar",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new Object[]{"OK", "CANCEL"},
                        "OK"
                );
                if (result == JOptionPane.OK_OPTION) {
                    LoginPage loginPage = new LoginPage();
                    SwingUtilities.getWindowAncestor(infoSupplierPanel).dispose();
                }
            }
        });
    }

    private void createUIComponents() {
        ImageIcon imageLogo = new ImageIcon("logo-indogudang.png");
        Image setLogo = imageLogo.getImage().getScaledInstance(300, 150, Image.SCALE_DEFAULT);
        logo = new JLabel(new ImageIcon(setLogo));
    }

    private void getConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/indogudang";
            String username = "admin";
            String password = "admin";

            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void displayDataSupplier() {
        DefaultTableModel model = new DefaultTableModel();
        dataSupplierTable.setModel(model);

        model.addColumn("nomor");
        model.addColumn("id_supplier");
        model.addColumn("nama_pt");
        model.addColumn("no_telp");
        model.addColumn("alamat");

        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM supplier;")) {
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                int nomor = result.getInt("nomor");
                String idSupplier = result.getString("id_supplier");
                String namaPt = result.getString("nama_pt");
                String noTelp = result.getString("no_telp");
                String alamat = result.getString("alamat");

                model.addRow(new Object[]{nomor, idSupplier, namaPt, noTelp, alamat});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
