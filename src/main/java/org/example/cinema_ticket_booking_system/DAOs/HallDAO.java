package org.example.cinema_ticket_booking_system.DAOs;

import org.example.cinema_ticket_booking_system.Hall;
import org.example.cinema_ticket_booking_system.SessionFactoryProvider;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class HallDAO {

    private final SessionFactory sessionFactory;

    public HallDAO() {
        this.sessionFactory = SessionFactoryProvider.provideSessionFactory();
    }

    public HallDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // ✅ Find all halls
    public List<Hall> findAll() {
        Transaction tx = null;
        List<Hall> halls = null;

        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            halls = session.createQuery("from Hall", Hall.class).getResultList();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }

        return halls;
    }

    // ✅ Find hall by ID
    public Hall findById(int id) {
        Transaction tx = null;
        Hall hall = null;

        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            hall = session.get(Hall.class, id);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }

        return hall;
    }

    // ✅ Save new hall
    public void save(Hall hall) {
        Transaction tx = null;

        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.save(hall);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    // ✅ Update hall
    public void update(Hall hall) {
        Transaction tx = null;

        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.update(hall);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    // ✅ Delete hall
    public void delete(Hall hall) {
        Transaction tx = null;

        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.delete(hall);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }
}
