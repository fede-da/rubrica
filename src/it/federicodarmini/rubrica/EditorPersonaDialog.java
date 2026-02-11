package it.federicodarmini.rubrica;

import javax.swing.*;
import java.awt.*;

public class EditorPersonaDialog extends JDialog {

    private final JTextField nomeField = new JTextField(20);
    private final JTextField cognomeField = new JTextField(20);
    private final JTextField indirizzoField = new JTextField(20);
    private final JTextField telefonoField = new JTextField(20);
    private final JTextField etaField = new JTextField(20);

    private boolean saved = false;
    private Persona result = null;

    // persona == null => modalità "Nuovo"
    // persona != null => modalità "Modifica"
    public EditorPersonaDialog(Frame owner, Persona persona) {
        super(owner, true); // modale
        setTitle(persona == null ? "Nuova Persona" : "Modifica Persona");

        buildUi();

        if (persona != null) {
            nomeField.setText(persona.getNome());
            cognomeField.setText(persona.getCognome());
            indirizzoField.setText(persona.getIndirizzo());
            telefonoField.setText(persona.getTelefono());
            etaField.setText(String.valueOf(persona.getEta()));
        }

        pack();
        setLocationRelativeTo(owner);
    }

    private void buildUi() {
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addRow(form, gbc, 0, "Nome", nomeField);
        addRow(form, gbc, 1, "Cognome", cognomeField);
        addRow(form, gbc, 2, "Indirizzo", indirizzoField);
        addRow(form, gbc, 3, "Telefono", telefonoField);
        addRow(form, gbc, 4, "Età", etaField);

        JButton salvaBtn = new JButton("Salva");
        JButton annullaBtn = new JButton("Annulla");

        salvaBtn.addActionListener(e -> onSave());
        annullaBtn.addActionListener(e -> onCancel());

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.add(salvaBtn);
        buttons.add(annullaBtn);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(form, BorderLayout.CENTER);
        getContentPane().add(buttons, BorderLayout.SOUTH);
    }

    private void addRow(JPanel panel, GridBagConstraints gbc, int row, String label, JTextField field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        panel.add(new JLabel(label + ":"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(field, gbc);
    }

    private void onSave() {
        String nome = nomeField.getText().trim();
        String cognome = cognomeField.getText().trim();
        String indirizzo = indirizzoField.getText().trim();
        String telefono = telefonoField.getText().trim();
        String etaTxt = etaField.getText().trim();

        int eta;
        try {
            eta = Integer.parseInt(etaTxt);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Età non valida: inserisci un numero intero.");
            return;
        }

        result = new Persona(nome, cognome, indirizzo, telefono, eta);
        saved = true;
        dispose();
    }

    private void onCancel() {
        saved = false;
        result = null;
        dispose();
    }

    public boolean isSaved() {
        return saved;
    }

    public Persona getResult() {
        return result;
    }
}