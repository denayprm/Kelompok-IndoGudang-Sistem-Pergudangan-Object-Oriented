package App;

import javax.swing.*;
import javax.swing.plaf.nimbus.State;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Stack;

public class DataBarangPage extends JFrame {
    private JPanel dataBarangPanel;
    private JLabel logo;
    private JButton berandaNav;
    private JButton barangMasukNav;
    private JButton barangKeluarNav;
    private JButton dataBarangNav;
    private JButton informasiSupplierNav;
    private JButton logoutButton;
    private JTable dataBarangTable;
    private JScrollPane dataBarangScrollPanel;
    private Connection connection;

    public DataBarangPage() {
        setContentPane(dataBarangPanel);
        setTitle("IndoGudang.com/data-barang");
        setSize(700,500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);

        getConnection();
        displayDataBarang();

        berandaNav.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HomePage homePage = new HomePage();
                SwingUtilities.getWindowAncestor(dataBarangPanel).dispose();
            }
        });
        barangMasukNav.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BarangMasukPage barangMasukPage = new BarangMasukPage();
                SwingUtilities.getWindowAncestor(dataBarangPanel).dispose();
            }
        });
        barangKeluarNav.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BarangKeluarPage barangKeluarPage = new BarangKeluarPage();
                SwingUtilities.getWindowAncestor(dataBarangPanel).dispose();
            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showOptionDialog(
                        DataBarangPage.this,
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
                    SwingUtilities.getWindowAncestor(dataBarangPanel).dispose();
                }
            }
        });
        informasiSupplierNav.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InformasiSupplierPage informasiSupplierPage = new InformasiSupplierPage();
                SwingUtilities.getWindowAncestor(dataBarangPanel).dispose();
            }
        });
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

    private void createUIComponents() {
        ImageIcon imageLogo = new ImageIcon("logo-indogudang.png");
        Image setLogo = imageLogo.getImage().getScaledInstance(100, 50, Image.SCALE_DEFAULT);
        logo = new JLabel(new ImageIcon(setLogo));
    }

    private void displayDataBarang() {
        DefaultTableModel model = new DefaultTableModel();
        dataBarangTable.setModel(model);

        model.addColumn("nomor");
        model.addColumn("kode_barang");
        model.addColumn("nama_barang");
        model.addColumn("stok_barang");
        model.addColumn("kategori_barang");

        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM barang;")) {
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                int nomor = result.getInt("nomor");
                String kodeBarang = result.getString("kode_barang");
                String namaBarang = result.getString("nama_barang");
                int stok = result.getInt("stok_barang");
                String kategori = result.getString("kategori_barang");

                model.addRow(new Object[]{nomor, kodeBarang, namaBarang, stok, kategori});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
