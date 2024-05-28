package App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HomePage extends JFrame {
    private JPanel HomePanel;
    private JLabel logo;
    private JButton berandaNav;
    private JButton barangMasukButton;
    private JButton barangKeluarButton;
    private JButton dataBarangButton;
    private JButton infoSupplierButton;
    private JButton barangMasukNav;
    private JButton barangKeluarNav;
    private JButton dataBarangNav;
    private JButton informasiSupplierNav;
    private JButton logoutButton;
    private JPanel barangMasukPanel;

    public HomePage() {
        setContentPane(HomePanel);
        setTitle("IndoGudang.com");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showOptionDialog(
                        HomePage.this,
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
                    SwingUtilities.getWindowAncestor(HomePanel).dispose();
                }
            }
        });

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BarangMasukPage barangMasukPage = new BarangMasukPage();
                SwingUtilities.getWindowAncestor(HomePanel).dispose();
            }
        };
        barangMasukNav.addActionListener(listener);
        barangMasukButton.addActionListener(listener);
        ActionListener listener1 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BarangKeluarPage barangKeluarPage = new BarangKeluarPage();
                SwingUtilities.getWindowAncestor(HomePanel).dispose();
            }
        };
        barangKeluarNav.addActionListener(listener1);
        barangKeluarButton.addActionListener(listener1);

        ActionListener listener2 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DataBarangPage dataBarangPage = new DataBarangPage();
                SwingUtilities.getWindowAncestor(HomePanel).dispose();
            }
        };
        dataBarangNav.addActionListener(listener2);
        dataBarangButton.addActionListener(listener2);
        ActionListener listener3 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InformasiSupplierPage informasiSupplierPage = new InformasiSupplierPage();
                SwingUtilities.getWindowAncestor(HomePanel).dispose();
            }
        };
        informasiSupplierNav.addActionListener(listener3);
        infoSupplierButton.addActionListener(listener3);
    }

    private void createUIComponents() {
        ImageIcon imageLogo = new ImageIcon("logo-indogudang.png");
        Image setLogo = imageLogo.getImage().getScaledInstance(100, 50, Image.SCALE_DEFAULT);
        logo = new JLabel(new ImageIcon(setLogo));
    }

    public void loginSuccessNotif() {
        UIManager.put("OptionPane.background", Color.decode("#04BF79"));
        UIManager.put("OptionPane.messageForeground", Color.BLACK);
        UIManager.put("Panel.background", Color.decode("#04BF79"));
        JOptionPane.showMessageDialog(
                this,
                "Login Success",
                "Login",
                JOptionPane.INFORMATION_MESSAGE
        );
        UIManager.put("OptionPane.background", UIManager.getColor("control"));
        UIManager.put("OptionPane.messageForeground", UIManager.getColor("OptionPane.messageForeground"));
        UIManager.put("Panel.background", UIManager.getColor("OptionPane.background"));
    }
}

