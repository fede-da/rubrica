package it.federicodarmini.rubrica.business;

import it.federicodarmini.rubrica.data.HibernateUtil;
import it.federicodarmini.rubrica.data.Persona;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PersonaRepository {

    public List<Persona> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Persona", Persona.class).list();
        }
    }

    public Persona save(Persona p) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(p); // INSERT
            tx.commit();
            return p;
        } catch (RuntimeException ex) {
            if (tx != null) tx.rollback();
            throw ex;
        }
    }

    public void update(Persona p) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(p); // UPDATE
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx != null) tx.rollback();
            throw ex;
        }
    }

    public void deleteById(int id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Persona p = session.get(Persona.class, id);
            if (p != null) session.remove(p); // DELETE
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx != null) tx.rollback();
            throw ex;
        }
    }
}