package it.federicodarmini.rubrica.business;

import it.federicodarmini.rubrica.data.Utente;
import it.federicodarmini.rubrica.util.HibernateUtil;
import org.hibernate.Session;

public class UtenteRepository {

    public Utente findByUsername(String username) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "from Utente u where u.username = :u",
                            Utente.class
                    ).setParameter("u", username)
                    .uniqueResult();
        }
    }

    public void save(Utente u) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            var tx = session.beginTransaction();
            session.persist(u);
            tx.commit();
        }
    }

    public long count() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("select count(u) from Utente u", Long.class)
                    .getSingleResult();
        }
    }
}
