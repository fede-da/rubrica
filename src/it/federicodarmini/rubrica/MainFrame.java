package it.federicodarmini.rubrica;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private final RubricaModel model;
    private final PersonaTableModel tableModel;
    private final JTable table;

    public MainFrame(RubricaModel model) {
        super("Rubrica");
        this.model = model;

        this.tableModel = new PersonaTableModel(model.getPersone());
        this.table = new JTable(tableModel);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);

        buildUi();
    }

    private void buildUi() {
        JScrollPane scrollPane = new JScrollPane(table);

        JButton nuovoBtn = new JButton("Nuovo");
        JButton modificaBtn = new JButton("Modifica");
        JButton eliminaBtn = new JButton("Elimina");

        nuovoBtn.addActionListener(e -> onNuovo());
        modificaBtn.addActionListener(e -> onModifica());
        eliminaBtn.addActionListener(e -> onElimina());

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttons.add(nuovoBtn);
        buttons.add(modificaBtn);
        buttons.add(eliminaBtn);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(buttons, BorderLayout.SOUTH);
    }

    private void onNuovo() {
        EditorPersonaDialog dlg = new EditorPersonaDialog(this, null);
        dlg.setVisible(true);

        if (dlg.isSaved()) {
            model.addPersona(dlg.getResult()); // salva anche su file (requisito)
            tableModel.refresh();
        }
    }

    private void onModifica() {
        int viewRow = table.getSelectedRow();
        if (viewRow < 0) {
            JOptionPane.showMessageDialog(this, "Seleziona una persona da modificare.");
            return;
        }

        int modelRow = table.convertRowIndexToModel(viewRow);
        Persona selected = model.getPersone().get(modelRow);

        EditorPersonaDialog dlg = new EditorPersonaDialog(this, selected);
        dlg.setVisible(true);

        if (dlg.isSaved()) {
            model.updatePersona(modelRow, dlg.getResult()); // salva su file
            tableModel.refresh();
        }
    }

    private void onElimina() {
        int viewRow = table.getSelectedRow();
        if (viewRow < 0) {
            JOptionPane.showMessageDialog(this, "Seleziona una persona da eliminare.");
            return;
        }

        int modelRow = table.convertRowIndexToModel(viewRow);
        Persona selected = model.getPersone().get(modelRow);

        int res = JOptionPane.showConfirmDialog(
                this,
                "Eliminare la persona " + selected.getNome() + " " + selected.getCognome() + "?",
                "Conferma eliminazione",
                JOptionPane.YES_NO_OPTION
        );

        if (res == JOptionPane.YES_OPTION) {
            model.removePersona(modelRow); // salva su file
            tableModel.refresh();
        }
    }
}