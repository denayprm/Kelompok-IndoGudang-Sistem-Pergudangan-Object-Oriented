package Admin;

public class Admin {
    final protected String username = "admin";
    final protected String password = "12345678";

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean login(String username, String password) {
        if (getUsername().equals(username)) {
            if (getPassword().equals(password)) {
                return true;
            } else {
                System.out.println("Password salah.");
            }
        } else {
            System.out.println("Username salah.");
        }
        return false;
    }
}
