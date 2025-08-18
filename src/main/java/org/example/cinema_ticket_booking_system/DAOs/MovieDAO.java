package org.example.cinema_ticket_booking_system.DAOs;

import org.example.cinema_ticket_booking_system.Movie;
import org.example.cinema_ticket_booking_system.MovieCrew;
import org.example.cinema_ticket_booking_system.SessionFactoryProvider;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class MovieDAO {
    private SessionFactory sessionFactory;

    public MovieDAO()
    {
        sessionFactory = SessionFactoryProvider.provideSessionFactory();
    }

    public List<Movie> findAll()
    {
        try(Session session = sessionFactory.openSession())
        {
            return session.createQuery("FROM Movie", Movie.class)
                    .getResultList();
        }
    }

    public Movie findById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Movie.class, id);
        }
    }

    public void saveOrUpdate(Movie movie){

        Transaction tx = null;

        try{
            Session session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.saveOrUpdate(movie);
            tx.commit();
        }catch (Exception e)
        {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public void delete(Movie movie) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.delete(movie);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

}
