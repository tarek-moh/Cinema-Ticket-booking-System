package org.example.cinema_ticket_booking_system.DAOs;

import org.example.cinema_ticket_booking_system.Screening;
import org.example.cinema_ticket_booking_system.SessionFactoryProvider;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class ScreeningDAO {

    private final SessionFactory sessionFactory;

    public ScreeningDAO() {
        this.sessionFactory = SessionFactoryProvider.provideSessionFactory();
    }

    public ScreeningDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // -------------------- CREATE --------------------
    public void save(Screening screening) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.save(screening);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }
    // -------------------- Save or Update --------------------

    public void saveOrUpdate(Screening screening) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.saveOrUpdate(screening);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    // -------------------- READ --------------------
    public Screening findById(int id) {
        Screening screening = null;
        try (Session session = sessionFactory.openSession()) {
            screening = session.get(Screening.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return screening;
    }

    public List<Screening> findAll() {
        List<Screening> screenings = null;
        try (Session session = sessionFactory.openSession()) {
            screenings = session.createQuery("from Screening", Screening.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return screenings;
    }

    // -------------------- UPDATE --------------------
    public void update(Screening screening) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.update(screening);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    // -------------------- DELETE --------------------
    public void delete(Screening screening) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.delete(screening);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }
}
