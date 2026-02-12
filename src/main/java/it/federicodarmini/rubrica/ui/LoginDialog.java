package it.federicodarmini.rubrica.ui;

import it.federicodarmini.rubrica.business.AuthService;

import javax.swing.*;
import java.awt.*;

public class LoginDialog extends JDialog {

    private final JTextField usernameField = new JTextField(18);
    private final JPasswordField passwordField = new JPasswordField(18);
    private boolean authenticated = false;

    public LoginDialog(Frame owner, AuthService authService) {
        super(owner, "Login", true);
        buildUi(authService);
        pack();
        setLocationRelativeTo(owner);
        setResizable(false);
    }

    private void buildUi(AuthService authService) {
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(8, 8, 8, 8);
        gc.anchor = GridBagConstraints.WEST;

        gc.gridx = 0; gc.gridy = 0;
        form.add(new JLabel("Username:"), gc);

        gc.gridx = 1;
        form.add(usernameField, gc);

        gc.gridx = 0; gc.gridy = 1;
        form.add(new JLabel("Password:"), gc);

        gc.gridx = 1;
        form.add(passwordField, gc);

        JButton loginBtn = new JButton("LOGIN");
        loginBtn.addActionListener(e -> {
            String user = usernameField.getText().trim();
            String pass = new String(passwordField.getPassword());

            if (authService.loginOk(user, pass)) {
                authenticated = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Login errato",
                        "Errore",
                        JOptionPane.ERROR_MESSAGE
                );
                passwordField.setText("");
                passwordField.requestFocusInWindow();
            }
        });

        getRootPane().setDefaultButton(loginBtn);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(loginBtn);

        JPanel root = new JPanel(new BorderLayout());
        root.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        root.add(form, BorderLayout.CENTER);
        root.add(bottom, BorderLayout.SOUTH);

        setContentPane(root);
    }

    public boolean isAuthenticated() {
        return authenticated;
    }
}
