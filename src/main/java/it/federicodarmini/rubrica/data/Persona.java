package it.federicodarmini.rubrica.data;
import jakarta.persistence.*;

@Entity
@Table(name = "persona")
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 100)
    private String cognome;

    @Column(length = 255)
    private String indirizzo;

    @Column(length = 50)
    private String telefono;

    @Column(nullable = false)
    private int eta;

    // richiesto da JPA
    protected Persona() {}

    public Persona(Integer id, String nome, String cognome, String indirizzo, String telefono, int eta) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.indirizzo = indirizzo;
        this.telefono = telefono;
        this.eta = eta;
    }

    public Persona(String nome, String cognome, String indirizzo, String telefono, int eta) {
        this((Integer) null, nome, cognome, indirizzo, telefono, eta);
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }

    public String getIndirizzo() { return indirizzo; }
    public void setIndirizzo(String indirizzo) { this.indirizzo = indirizzo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public int getEta() { return eta; }
    public void setEta(int eta) { this.eta = eta; }
}