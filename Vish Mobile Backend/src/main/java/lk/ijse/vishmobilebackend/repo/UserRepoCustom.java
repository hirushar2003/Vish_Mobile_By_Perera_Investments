package lk.ijse.vishmobilebackend.repo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import lk.ijse.vishmobilebackend.entity.User;

@Repository
public class UserRepoCustom {

    @Autowired
    private EntityManager entityManager;

    public User findUserByEmail(String email) {
        try {
            Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email");
            query.setParameter("email", email);
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;  // Return null if no user is found
        }
    }
}
