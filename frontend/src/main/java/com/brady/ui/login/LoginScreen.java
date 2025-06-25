package com.brady.ui.login;

import com.brady.service.user.UserServiceRemote;
import com.brady.ui.dashboard.DashboardScreen;
import com.brady.utils.JNDIUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginScreen extends JFrame {

    private JTextField tfUsername;
    private JPasswordField tfPassword;
    private JButton btnLogin;

    public LoginScreen() {
        setTitle("Brady Pet Assignment - Login");
        setSize(400, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initializeUserInterface();
        setActionListeners();
    }

    private void initializeUserInterface() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        JLabel userLabel = new JLabel("Enter Username");
        JLabel passLabel = new JLabel("Enter Password");

        this.tfUsername = new JTextField();
        this.tfPassword = new JPasswordField();

        formPanel.add(userLabel);
        formPanel.add(this.tfUsername);
        formPanel.add(passLabel);
        formPanel.add(this.tfPassword);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        this.btnLogin = new JButton("Login");
        buttonPanel.add(this.btnLogin);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void setActionListeners() {
        btnLogin.addActionListener((ActionEvent e) -> loginAction());
    }

    private void loginAction() {
        try {
            UserServiceRemote service = JNDIUtil.lookupUserService();
            String username = this.tfUsername.getText();
            String password = new String(this.tfPassword.getPassword());

            boolean authenticated = service.authenticate(username, password);
            if (authenticated) {
                this.dispose();
                SwingUtilities.invokeLater(() -> new DashboardScreen().setVisible(true));
            } else {
                JOptionPane.showMessageDialog(this,
                        "Invalid username or password.",
                        "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error connecting to server",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginScreen().setVisible(true));
    }
}
