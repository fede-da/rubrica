package it.federicodarmini.rubrica.business;

import it.federicodarmini.rubrica.data.Utente;
import it.federicodarmini.rubrica.util.PasswordUtil;

public class AuthService {

    private final UtenteRepository utenteRepository;

    public AuthService(UtenteRepository utenteRepository) {
        this.utenteRepository = utenteRepository;
    }

    public boolean loginOk(String username, String passwordPlain) {
        Utente u = utenteRepository.findByUsername(username);
        if (u == null) return false;

        String hash = PasswordUtil.sha256(passwordPlain);
        return hash.equals(u.getPasswordHash());
    }

    public void ensureAdminUser() {
        if (utenteRepository.count() == 0) {
            String defaultUser = "admin";
            String defaultPassword = "admin";

            String hash = PasswordUtil.sha256(defaultPassword);;

            Utente admin = new Utente(defaultUser, hash);
            utenteRepository.save(admin);

            System.out.println("Creato utente di default: admin/admin");
        }
    }
}
