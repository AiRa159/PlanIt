package cz.cvut.fel.ear.planIt.dao;

import cz.cvut.fel.ear.planIt.model.User;
import org.springframework.stereotype.Repository;
import javax.persistence.NoResultException;

@Repository
public class UserDao extends BaseDao<User> {

    public UserDao() {
        super(User.class);
    }

    public User findByName(String name, String surname) {
        try {
            return em.createNamedQuery("User.findByName", User.class)
                    .setParameter("name", name).setParameter("surname", surname)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public User findByUsername(String username) {
        try {
            return em.createNamedQuery("User.findByUsername", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }


}
