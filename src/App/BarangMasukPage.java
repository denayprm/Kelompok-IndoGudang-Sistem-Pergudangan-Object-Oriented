package App;

import Barang.*;
import Supplier.Supplier;
import Warehouse.Inventory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class BarangMasukPage extends JFrame {
    private Barang barang;
    private Supplier supplier;
    private LoginPage database;
    private JPanel barangMasukPanel;
    private JLabel logo;
    private JButton berandaNav;
    private JButton barangMasukNav;
    private JButton barangKeluarNav;
    private JButton dataBarangNav;
    private JButton informasiSupplierNav;
    private JButton logoutButton;
    private JTextField idSuppField;
    private JTextField namaPtField;
    private JTextField noTelpField;
    private JTextField namaBarangField;
    private JTextField jumlahField;
    private JRadioButton pakaianRadioButton;
    private JRadioButton elektronikRadioButton;
    private JRadioButton bahanMakananRadioButton;
    private JRadioButton dokumenRadioButton;
    private JButton saveButton;
    private JButton cancelButton;
    private JTextField alamatField;
    private JTextField idBarangField;
    private Connection connection;
    private int idPK = 0, idEL = 0, idBM = 0, idDK = 0;
    Inventory inventory = new Inventory();
    public BarangMasukPage() {
        setContentPane(barangMasukPanel);
        setTitle("IndoGudang.com/barang-masuk");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveData();
                HomePage homePage = new HomePage();
                SwingUtilities.getWindowAncestor(barangMasukPanel).dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancel();
            }
        });

        berandaNav.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HomePage homePage = new HomePage();
                SwingUtilities.getWindowAncestor(barangMasukPanel).dispose();
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showOptionDialog(
                        BarangMasukPage.this,
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
                    SwingUtilities.getWindowAncestor(barangMasukPanel).dispose();
                }
            }
        });

        getConnection();
        barangKeluarNav.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BarangKeluarPage barangKeluarPage = new BarangKeluarPage();
                SwingUtilities.getWindowAncestor(barangMasukPanel).dispose();
            }
        });
        dataBarangNav.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DataBarangPage dataBarangPage = new DataBarangPage();
                SwingUtilities.getWindowAncestor(barangMasukPanel).dispose();
            }
        });
        informasiSupplierNav.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InformasiSupplierPage informasiSupplierPage = new InformasiSupplierPage();
                SwingUtilities.getWindowAncestor(barangMasukPanel).dispose();
            }
        });
    }

    private void createUIComponents() {
        ImageIcon imageLogo = new ImageIcon("logo-indogudang.png");
        Image setLogo = imageLogo.getImage().getScaledInstance(100, 50, Image.SCALE_DEFAULT);
        logo = new JLabel(new ImageIcon(setLogo));
    }

    private void getConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/indogudang";
            String username = "admin";
            String password = "admin";

            connection = DriverManager.getConnection(url, username, password);
            createTableSupplier();
            createTableBarang();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTableSupplier() {
        try (Statement statement = connection.createStatement()) {
            String createTable = "CREATE TABLE IF NOT EXISTS supplier (" +
                    "nomor int NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                    "id_supplier CHAR(10)," +
                    "nama_pt VARCHAR(50)," +
                    "no_telp CHAR(15)," +
                    "alamat VARCHAR(50));";
            statement.executeUpdate(createTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTableBarang() {
        try (Statement statement = connection.createStatement()) {
            String createTable = "CREATE TABLE IF NOT EXISTS barang (" +
                    "nomor int NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                    "kode_barang CHAR(10)," +
                    "nama_barang VARCHAR(50)," +
                    "stok_barang int," +
                    "kategori_barang VARCHAR(25));";
            statement.executeUpdate(createTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertSupplier(String idSupplier, String namaPT, String noTelp, String alamatPT) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO supplier (id_supplier, nama_pt, no_telp, alamat) VALUES (?, ?, ?, ?);")) {
            statement.setString(1, idSupplier);
            statement.setString(2, namaPT);
            statement.setString(3, noTelp);
            statement.setString(4, alamatPT);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertBarang(String kodeBarang, String namaBarang, int stok, String kategori) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO barang (kode_barang, nama_barang, stok_barang, kategori_barang) VALUES (?, ?, ?, ?);")) {
            statement.setString(1, kodeBarang);
            statement.setString(2, namaBarang);
            statement.setInt(3, stok);
            statement.setString(4, kategori);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dataBarang() {
        try (Statement statement = connection.createStatement()) {
            String createView = "CREATE VIEW data_barang AS" +
                    "SELECT s.id_supplier, b.kode_barang, s.nama_pt, b.nama_barang, b.stok_barang" +
                    "FROM supplier s" +
                    "JOIN barang b ON s.nomor = b.nomor;";
            statement.executeUpdate(createView);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void dataSupplierBarang() {
        inventory = new Inventory();

        String idBarang = idBarangField.getText();
        String idSupplier = idSuppField.getText();
        String namaPT = namaPtField.getText();
        String noTelp = noTelpField.getText();
        String alamat = alamatField.getText();
        String namaBarang = namaBarangField.getText();
        int stok = Integer.parseInt(jumlahField.getText());

        supplier = new Supplier(idSupplier, namaPT, noTelp, alamat);
        insertSupplier(supplier.getIdSupplier(), namaPT, supplier.getNoTelp(), alamat);

        if (pakaianRadioButton.isSelected()) {
            idPK += 1;
            String kodeBarang = "0" + idPK;
            barang = new Pakaian(kodeBarang, namaBarang, stok);
            insertBarang(idBarang, namaBarang, stok, "Pakaian");
        } else if (elektronikRadioButton.isSelected()) {
            idEL += 1;
            String kodeBarang = "0" + idEL;
            barang = new Elektronik(kodeBarang, namaBarang, stok);
            insertBarang(idBarang, namaBarang, stok, "Elektronik");
        } else if (bahanMakananRadioButton.isSelected()) {
            idBM += 1;
            String kodeBarang = "0" + idBM;
            barang = new BahanMakanan(kodeBarang, namaBarang, stok);
            insertBarang(idBarang, namaBarang, stok, "Bahan Makanan");
        } else {
            idDK += 1;
            String kodeBarang = "0" + idDK;
            barang = new Dokumen(kodeBarang, namaBarang, stok);
            insertBarang(idBarang, namaBarang, stok, "Dokumen");
        }
        inventory.terimaBarang(supplier, barang);
    }

    private void saveData() {
        dataSupplierBarang();
        inventory.tampilkanData();
        JOptionPane.showMessageDialog(
                this,
                "Data Berhasil Disimpan",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void cancel() {
        int result = JOptionPane.showOptionDialog(
                this,
                "Yakin ingin membatalkan penyimpanan?" ,
                "Cancel",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new Object[]{"OK", "CANCEL"},
                "OK"
        );
        if (result == JOptionPane.OK_OPTION) {
            HomePage homePage = new HomePage();
            SwingUtilities.getWindowAncestor(barangMasukPanel).dispose();
        }
    }
}
