package App;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.*;

public class BarangKeluarPage extends JFrame {
    private JLabel logo;
    private JPanel barangKeluarPanel;
    private JButton berandaNav;
    private JButton barangMasukNav;
    private JButton logoutButton;
    private JButton barangKeluarNav;
    private JButton dataBarangNav;
    private JButton informasiSupplierNav;
    private JTextField searchField;
    private JButton searchButton;
    private JTable dataBarangTable;
    private JTextField jumlahField;
    private JButton saveButton;
    private JButton cancelButton;
    private JRadioButton kurangiStokRadioButton;
    private JRadioButton keluarkanBarangRadioButton;
    private JComboBox opsiComboBox;
    private Connection connection;

    public BarangKeluarPage() {
        setContentPane(barangKeluarPanel);
        setTitle("IndoGudang.com/barang-keluar");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);

        DefaultTableModel model = new DefaultTableModel();
        getConnection();
        displayDataBarang(model);

        addPlaceHolder("Masukkan kode barang...", searchField);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchBarang(model);
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveData();
            }
        });

        berandaNav.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HomePage homePage = new HomePage();
                SwingUtilities.getWindowAncestor(barangKeluarPanel).dispose();
            }
        });
        barangMasukNav.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BarangMasukPage barangMasukPage = new BarangMasukPage();
                SwingUtilities.getWindowAncestor(barangKeluarPanel).dispose();
            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showOptionDialog(
                        BarangKeluarPage.this,
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
                    SwingUtilities.getWindowAncestor(barangKeluarPanel).dispose();
                }
            }
        });

        searchField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchBarang(model);
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancel();
            }
        });

        dataBarangNav.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DataBarangPage dataBarangPage = new DataBarangPage();
                SwingUtilities.getWindowAncestor(barangKeluarPanel).dispose();
            }
        });
        informasiSupplierNav.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InformasiSupplierPage informasiSupplierPage = new InformasiSupplierPage();
                SwingUtilities.getWindowAncestor(barangKeluarPanel).dispose();
            }
        });
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (kurangiStokRadioButton.isSelected()) {
                    jumlahField.setEnabled(true);
                    jumlahField.setEditable(true);
                } else {
                    jumlahField.setEnabled(false);
                    jumlahField.setEditable(false);
                }
            }
        };
        kurangiStokRadioButton.addActionListener(listener);
        keluarkanBarangRadioButton.addActionListener(listener);
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

        opsiComboBox = new JComboBox<>();
        opsiComboBox.addItem("Kurangi Stok");
        opsiComboBox.addItem("Keluarkan Barang");
    }

    private void searchBarang(DefaultTableModel model) {
        String kodeBarang = searchField.getText();

        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM barang WHERE kode_barang = ?")) {
            statement.setString(1, kodeBarang);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                int nomor = result.getInt("nomor");
                String kodeBarangResult = result.getString("kode_barang");
                String namaBarangResult = result.getString("nama_barang");
                int stokResult = result.getInt("stok_barang");
                String kategoriResult = result.getString("kategori_barang");

                model.addRow(new Object[]{nomor, kodeBarangResult, namaBarangResult, stokResult, kategoriResult});
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Barang tidak ditemukan",
                        "Not Found",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void displayDataBarang(DefaultTableModel model) {
        dataBarangTable.setModel(model);

        model.addColumn("nomor");
        model.addColumn("kode_barang");
        model.addColumn("nama_barang");
        model.addColumn("stok_barang");
        model.addColumn("kategori_barang");
    }

    private void saveData() {
        if (kurangiStokRadioButton.isSelected()) {
            System.out.println("Pilihan: " +kurangiStokRadioButton.getText());
            jumlahField.setEditable(true);
            kurangiStok();
        } else {
            System.out.println("Pilihan: " +kurangiStokRadioButton.getText());
            jumlahField.setEditable(false);
            keluarkanBarang();
        }
    }

    private void kurangiStok() {
        String kodeBarang = searchField.getText();
        int jumlah = Integer.parseInt(jumlahField.getText());

        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE barang SET stok_barang = stok_barang - ? WHERE kode_barang = ?;")) {
            statement.setInt(1, jumlah);
            statement.setString(2, kodeBarang);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "Stok barang dengan kode " +kodeBarang+ " telah dikurangi sebanyak " +jumlah,
                        "Kurangi Stok Success.",
                        JOptionPane.INFORMATION_MESSAGE
                );
                HomePage homePage = new HomePage();
                SwingUtilities.getWindowAncestor(barangKeluarPanel).dispose();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Barang tidak ditemukan and/or Stok habis.",
                        "Kurangi Stok Failed",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void keluarkanBarang() {
        String kodeBarang = searchField.getText();

        try (PreparedStatement removeSupplierStatement = connection.prepareStatement(
                "DELETE FROM supplier WHERE nomor =" +
                        "(SELECT nomor FROM barang WHERE kode_barang = ?);");
                PreparedStatement removeBarangStatement = connection.prepareStatement(
                        "DELETE FROM barang WHERE kode_barang = ?;")) {

            connection.setAutoCommit(false);

            removeSupplierStatement.setString(1, kodeBarang);
            removeBarangStatement.setString(1, kodeBarang);

            removeSupplierStatement.executeUpdate();
            removeBarangStatement.executeUpdate();

            connection.commit();
            connection.setAutoCommit(true);

            int result = JOptionPane.showOptionDialog(
                    BarangKeluarPage.this,
                    "Apakah anda yakin ingin berhenti menyimpan?" ,
                    "Keluar",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new Object[]{"YAKIN", "CANCEL"},
                    "YAKIN"
            );
            if (result == JOptionPane.OK_OPTION) {
                JOptionPane.showMessageDialog(
                        this,
                        "Barang dengan kode " +kodeBarang+ " telah berhenti menyimpan.",
                        "Keluarkan Barang Success.",
                        JOptionPane.INFORMATION_MESSAGE
                );
                HomePage homePage = new HomePage();
                SwingUtilities.getWindowAncestor(barangKeluarPanel).dispose();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
            SwingUtilities.getWindowAncestor(barangKeluarPanel).dispose();
        }
    }


    public void addPlaceHolder(String placeholder, JTextField textField) {
        textField.setText(placeholder);
        textField.setForeground(Color.GRAY);

        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(Color.GRAY);
                }
            }
        });
    }
}
