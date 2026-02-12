package it.federicodarmini.rubrica.ui;

import it.federicodarmini.rubrica.data.Persona;
import it.federicodarmini.rubrica.data.PersonaTableModel;
import it.federicodarmini.rubrica.data.RubricaModel;
import it.federicodarmini.rubrica.util.BackgroundMusic;

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
        BackgroundMusic.playLoop("/assets/background.wav");
    }

    private void buildUi() {
        // Content pane con background immagine
        BackgroundPanel content = new BackgroundPanel("/assets/user_list_bg.jpg");
        setContentPane(content);
        // Header (titolo + toolbar)
        JLabel title = new JLabel("Rubrica");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 22f));
        title.setBorder(BorderFactory.createEmptyBorder(16, 16, 8, 16));

        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setOpaque(false);
        toolBar.setBorder(BorderFactory.createEmptyBorder(0, 12, 10, 12));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.add(title, BorderLayout.WEST);
        header.add(toolBar, BorderLayout.EAST);

        content.add(header, BorderLayout.NORTH);

        // Tabella + scroll
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));

        // Effetto "glass": tabella e viewport trasparenti per far vedere lo sfondo
        table.setOpaque(false);
        table.setFillsViewportHeight(true);
        table.setRowHeight(28);
        table.setShowHorizontalLines(false);
        table.setShowVerticalLines(false);

        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        // Zebra rows + selezione più leggibile (semi-trasparente)
        table.setDefaultRenderer(Object.class, (tbl, value, isSelected, hasFocus, row, col) -> {
            javax.swing.table.DefaultTableCellRenderer r = new javax.swing.table.DefaultTableCellRenderer();
            Component c = r.getTableCellRendererComponent(tbl, value, isSelected, hasFocus, row, col);

            c.setFont(c.getFont().deriveFont(Font.PLAIN, 13f));
            c.setForeground(Color.DARK_GRAY);

            if (isSelected) {
                c.setBackground(new Color(60, 120, 220, 90));
            } else {
                c.setBackground(row % 2 == 0
                        ? new Color(255, 255, 255, 140)
                        : new Color(245, 245, 245, 110));
            }
            ((JComponent) c).setOpaque(true);
            return c;
        });

        content.add(scrollPane, BorderLayout.CENTER);

        // Bottoni nella toolbar (in alto)
        JButton nuovoBtn = new JButton("➕ Nuovo");
        JButton modificaBtn = new JButton("✏️ Modifica");
        JButton eliminaBtn = new JButton("🗑 Elimina");

        for (JButton b : new JButton[]{nuovoBtn, modificaBtn, eliminaBtn}) {
            b.setFocusPainted(false);
            b.setContentAreaFilled(true);
            b.setOpaque(true);
            b.setForeground(Color.WHITE);
            b.setFont(b.getFont().deriveFont(Font.BOLD, 13f));

            // Sfondo blu moderno
            b.setBackground(new Color(33, 150, 243));

            // Bordo con effetto rilievo
            b.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(25, 118, 210), 2, true),
                    BorderFactory.createEmptyBorder(8, 14, 8, 14)
            ));

            // Effetto hover semplice
            b.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    b.setBackground(new Color(30, 136, 229));
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    b.setBackground(new Color(33, 150, 243));
                }
            });
        }

        nuovoBtn.addActionListener(e -> onNuovo());
        modificaBtn.addActionListener(e -> onModifica());
        eliminaBtn.addActionListener(e -> onElimina());

        toolBar.add(nuovoBtn);
        toolBar.addSeparator(new Dimension(10, 0));
        toolBar.add(modificaBtn);
        toolBar.addSeparator(new Dimension(10, 0));
        toolBar.add(eliminaBtn);
    }

    private void onNuovo() {
        EditorPersonaDialog dlg = new EditorPersonaDialog(this, null);
        dlg.setVisible(true);

        if (dlg.isSaved()) {
            model.addPersona(dlg.getResult());
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
            model.updatePersona(modelRow, dlg.getResult());
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
            model.removePersona(modelRow);
            tableModel.refresh();
        }
    }
}