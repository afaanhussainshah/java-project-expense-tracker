import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;
import java.util.List;

public class LoginWindow {
    private Frame loginFrame;
    private TextField userField;
    private Button loginBtn;
    private final String USERS_FILE = "users.txt";

    public LoginWindow() {
        loginFrame = new Frame("Login - Expense Tracker");
        loginFrame.setSize(300, 150);
        loginFrame.setLayout(new FlowLayout());
        loginFrame.setLocationRelativeTo(null);

        Label userLabel = new Label("Enter Username:");
        userField = new TextField(20);
        loginBtn = new Button("Login");

        loginBtn.addActionListener(e -> {
            String username = userField.getText().trim();
            if (!username.isEmpty()) {
                try {
                    boolean isExisting = userExists(username);
                    if (!isExisting) {
                        addUser(username);
                        launchTracker(username);
                    } else {
                        askToProceed(username);
                    }
                } catch (IOException ex) {
                    showMessage("Error: " + ex.getMessage());
                }
            } else {
                showMessage("Username cannot be empty.");
            }
        });

        loginFrame.add(userLabel);
        loginFrame.add(userField);
        loginFrame.add(loginBtn);

        loginFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                loginFrame.dispose();
            }
        });

        loginFrame.setVisible(true);
    }

    private boolean userExists(String username) throws IOException {
        if (!Files.exists(Paths.get(USERS_FILE))) return false;
        List<String> users = Files.readAllLines(Paths.get(USERS_FILE));
        return users.contains(username);
    }

    private void addUser(String username) throws IOException {
        Files.write(Paths.get(USERS_FILE), (username + "\n").getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    private void askToProceed(String username) {
        Dialog dialog = new Dialog(loginFrame, "User Exists", true);
        dialog.setLayout(new FlowLayout());
        dialog.add(new Label("User exists. Continue?"));

        Button yesBtn = new Button("Yes");
        yesBtn.addActionListener(e -> {
            dialog.setVisible(false);
            launchTracker(username);
        });

        Button noBtn = new Button("No");
        noBtn.addActionListener(e -> dialog.setVisible(false));

        dialog.add(yesBtn);
        dialog.add(noBtn);
        dialog.setSize(250, 100);
        dialog.setLocationRelativeTo(loginFrame);
        dialog.setVisible(true);
    }

    private void launchTracker(String username) {
        loginFrame.dispose();
        new ExpenseController(username);
    }

    private void showMessage(String msg) {
        Dialog dialog = new Dialog(loginFrame, "Alert", true);
        dialog.setLayout(new FlowLayout());
        dialog.add(new Label(msg));
        Button ok = new Button("OK");
        ok.addActionListener(e -> dialog.setVisible(false));
        dialog.add(ok);
        dialog.setSize(250, 100);
        dialog.setLocationRelativeTo(loginFrame);
        dialog.setVisible(true);
    }
}
