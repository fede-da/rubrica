package it.federicodarmini.rubrica.data;

import it.federicodarmini.rubrica.business.PersonaRepository;

import java.util.Vector;

public class RubricaModel {

    private final PersonaRepository repo;
    private final Vector<Persona> persone;

    public RubricaModel(PersonaRepository repo) {
        this.repo = repo;
        this.persone = new Vector<>(repo.findAll());
    }

    public Vector<Persona> getPersone() {
        return persone;
    }

    public void addPersona(Persona p) {
        repo.save(p);
        persone.add(p);
    }

    public void updatePersona(int index, Persona p) {
        // p deve avere id valorizzato (ce l’ha perché arriva da DB o da persist)
        repo.update(p);
        persone.set(index, p);
    }

    public void removePersona(int index) {
        Persona p = persone.get(index);
        repo.deleteById(p.getId());
        persone.remove(index);
    }
}