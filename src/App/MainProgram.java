package App;

import javax.swing.*;

public class MainProgram {
    public MainProgram() {
        SwingUtilities.invokeLater(() -> {
            try {
                new LoginPage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        new MainProgram();
    }
}
