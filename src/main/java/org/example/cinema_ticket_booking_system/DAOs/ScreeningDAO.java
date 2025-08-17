package org.example.cinema_ticket_booking_system.DAOs;

import org.example.cinema_ticket_booking_system.Screening;
import org.example.cinema_ticket_booking_system.SessionFactoryProvider;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.List;

public class ScreeningDAO {

    private SessionFactory sessionFactory;

    public ScreeningDAO()
    {
        this.sessionFactory = SessionFactoryProvider.provideSessionFactory();
    }

    public ScreeningDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Screening> findAll() {
         Session session = sessionFactory.openSession();
        Transaction tx = null;
        List<Screening> screenings = null;

        try {
            tx = session.beginTransaction();
            screenings = session.createQuery("from Screening", Screening.class).getResultList();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return screenings;
    }

}
