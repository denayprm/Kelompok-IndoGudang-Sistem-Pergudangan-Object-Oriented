package App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class LoginPage extends JFrame {
    private JPanel LoginPanel;
    private JLabel logo;
    private JTextField usernameField;
    private JPanel spacer3;
    private JPasswordField passwordField;
    private JPanel spacer4;
    private JLabel labelKosong;
    private JButton loginButton;
    private JButton signupButton;
    private Connection connection;
    private Map<String, String> users = new HashMap<>();

    public LoginPage() {
        setContentPane(LoginPanel);
        setTitle("IndoGudang.com/login");
        setSize(700,500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signup();
            }
        });
        usernameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                passwordField.requestFocus();
            }
        });
        passwordField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        getConnection();
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
            createTableLogin();
            selectTableLogin();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTableLogin() {
        try (Statement statement = connection.createStatement()) {
            String createTable = "CREATE TABLE IF NOT EXISTS login (" +
                    "id int NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                    "username VARCHAR(50) UNIQUE," +
                    "password VARCHAR(50));";
            statement.executeUpdate(createTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertUser(String username, String password) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO login (username, password) VALUES (?, ?)")) {
            statement.setString(1, username);
            statement.setString(2, password);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void selectTableLogin() {
        try (Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery(
                    "SELECT * FROM login;"
            );
            while (result.next()) {
                String username = result.getString("username");
                String password = result.getString("password");
                users.put(username, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (users.containsKey(username) && users.get(username).equals(password)) {
            SwingUtilities.getWindowAncestor(LoginPanel).dispose();
            HomePage homePage = new HomePage();
            homePage.loginSuccessNotif();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Incorrect username or password",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void signup() {
        String newUsername = usernameField.getText();
        String newPassword = new String(passwordField.getPassword());

        if (!users.containsKey(newUsername)) {
            insertUser(newUsername, newPassword);
            users.put(newUsername, newPassword);
            JOptionPane.showMessageDialog(
                    this,
                    "SIGN UP SUCCESFULL!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "SIGN UP FAILED",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
