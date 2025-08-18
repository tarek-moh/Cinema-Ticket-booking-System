package org.example.cinema_ticket_booking_system.DAOs;

import org.example.cinema_ticket_booking_system.MovieCrew;
import org.example.cinema_ticket_booking_system.SessionFactoryProvider;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class MovieCrewDAO {
    private final SessionFactory sessionFactory;

    public MovieCrewDAO() {
        this.sessionFactory = SessionFactoryProvider.provideSessionFactory();
    }

    public MovieCrewDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Fetch all MovieCrew members regardless of type
     */
    public List<MovieCrew> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM MovieCrew", MovieCrew.class)
                    .getResultList();
        }
    }

    /**
     * Fetch MovieCrew members filtered by type (e.g., DIRECTOR or ACTOR)
     */
    public List<MovieCrew> findByType(String memberType) {
        List<MovieCrew> crewList = null;
        Transaction tx = null;

        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            crewList = session.createQuery("FROM MovieCrew WHERE memberType = :type", MovieCrew.class)
                    .setParameter("type", memberType.toUpperCase())
                    .getResultList();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }

        return crewList;
    }

    /**
     * Find a MovieCrew member by their ID
     */
    public MovieCrew findById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(MovieCrew.class, id);
        }
    }

    /**
     * Save or update a MovieCrew member
     */
    public void saveOrUpdate(MovieCrew member) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.saveOrUpdate(member);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    /**
     * Delete a MovieCrew member
     */
    public void delete(MovieCrew member) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.delete(member);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }
}
