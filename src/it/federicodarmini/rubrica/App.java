package it.federicodarmini.rubrica;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RubricaFileRepository repo = new RubricaFileRepository();
            RubricaModel model = new RubricaModel(repo);

            MainFrame frame = new MainFrame(model);
            frame.setVisible(true);
        });
    }
}