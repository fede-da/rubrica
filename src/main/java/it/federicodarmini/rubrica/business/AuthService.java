package it.federicodarmini.rubrica.business;

import it.federicodarmini.rubrica.data.Utente;
import it.federicodarmini.rubrica.util.PasswordUtil;

public class AuthService {

    private final UtenteRepository repo;

    public AuthService(UtenteRepository repo) {
        this.repo = repo;
    }

    public boolean loginOk(String username, String passwordPlain) {
        Utente u = repo.findByUsername(username);
        if (u == null) return false;

        String hash = PasswordUtil.sha256(passwordPlain);
        return hash.equals(u.getPasswordHash());
    }
}
