package it.federicodarmini.rubrica;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import it.federicodarmini.rubrica.business.AuthService;
import it.federicodarmini.rubrica.business.PersonaRepository;
import it.federicodarmini.rubrica.business.UtenteRepository;
import it.federicodarmini.rubrica.ui.LoginDialog;
import it.federicodarmini.rubrica.util.HibernateUtil;
import it.federicodarmini.rubrica.data.RubricaModel;
import it.federicodarmini.rubrica.ui.MainFrame;

public class App {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            PersonaRepository repo = new PersonaRepository();
            RubricaModel model = new RubricaModel(repo);

            AuthService auth = new AuthService(new UtenteRepository());

            LoginDialog login = new LoginDialog(null, auth);
            login.setVisible(true);

            if (!login.isAuthenticated()) {
                System.exit(0);
                return;
            }

            MainFrame frame = new MainFrame(model);
            frame.setVisible(true);
        });

        // opzionale: shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(HibernateUtil::shutdown));
    }
}