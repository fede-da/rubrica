package it.federicodarmini.rubrica;

import javax.swing.table.AbstractTableModel;
import java.util.Vector;

public class PersonaTableModel extends AbstractTableModel {

    private final Vector<Persona> persone;
    private final String[] columns = {"Nome", "Cognome", "Telefono"};

    public PersonaTableModel(Vector<Persona> persone) {
        this.persone = persone;
    }

    @Override
    public int getRowCount() {
        return persone.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Persona p = persone.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> p.getNome();
            case 1 -> p.getCognome();
            case 2 -> p.getTelefono();
            default -> "";
        };
    }

    public void refresh() {
        fireTableDataChanged();
    }
}
