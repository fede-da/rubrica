package it.federicodarmini.rubrica.data;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration cfg = new Configuration();
            cfg.addAnnotatedClass(Persona.class);
            // Legge hibernate.properties da resources automaticamente
            return cfg.buildSessionFactory();
        } catch (Exception ex) {
            throw new ExceptionInInitializerError("Errore init Hibernate: " + ex.getMessage());
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        sessionFactory.close();
    }
}
