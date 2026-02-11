package it.federicodarmini.rubrica;

import java.util.Vector;

public class RubricaModel {

    private final RubricaFileRepository repository;
    private final Vector<Persona> persone;

    public RubricaModel(RubricaFileRepository repository) {
        this.repository = repository;
        this.persone = repository.load();
    }

    public Vector<Persona> getPersone() {
        return persone;
    }

    public void addPersona(Persona p) {
        persone.add(p);
        repository.saveAll(persone);
    }

    public void updatePersona(int index, Persona p) {
        persone.set(index, p);
        repository.saveAll(persone);
    }

    public void removePersona(int index) {
        persone.remove(index);
        repository.saveAll(persone);
    }
}