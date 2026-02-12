package it.federicodarmini.rubrica.data;

import jakarta.persistence.*;

@Entity
@Table(name = "utente")
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    protected Utente() {}

    public Utente(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
    }

    public Integer getId() { return id; }
    public String getUsername() { return username; }
    public String getPasswordHash() { return passwordHash; }

    public void setUsername(String username) { this.username = username; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
}
