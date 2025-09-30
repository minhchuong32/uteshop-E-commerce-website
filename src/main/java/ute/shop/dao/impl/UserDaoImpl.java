package ute.shop.dao.impl;

import jakarta.persistence.*;
import ute.shop.dao.IUserDao;
import ute.shop.entity.User;

import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements IUserDao {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("UteShop");

    @Override
    public List<User> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT u FROM User u", User.class).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public void insert(User user) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(user);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public boolean update(User user) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(user);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(int id) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            User u = em.find(User.class, id);
            if (u != null) em.remove(u);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<User> q = em.createQuery(
                    "SELECT u FROM User u WHERE u.email = :email", User.class);
            q.setParameter("email", email);
            return q.getResultStream().findFirst();
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<User> q = em.createQuery(
                    "SELECT u FROM User u WHERE u.username = :username", User.class);
            q.setParameter("username", username);
            return q.getResultStream().findFirst();
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<User> getUserById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return Optional.ofNullable(em.find(User.class, id));
        } finally {
            em.close();
        }
    }

    @Override
    public boolean updatePassword(String email, String newPassword) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Optional<User> opt = findByEmail(email);
            if (opt.isPresent()) {
                User u = opt.get();
                u.setPassword(newPassword);
                em.merge(u);
                tx.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    @Override
    public void updateStatus(int id, String status) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            User u = em.find(User.class, id);
            if (u != null) {
                u.setStatus(status);
                em.merge(u);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public boolean updatePwd(User user, boolean changePwd) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            User u = em.find(User.class, user.getUserId());
            if (u != null) {
                u.setUsername(user.getUsername());
                u.setEmail(user.getEmail());
                u.setRole(user.getRole());
                u.setStatus(user.getStatus());
                u.setAvatar(user.getAvatar());
                u.setPhone(user.getPhone());
                u.setName(user.getName());
                u.setAddress(user.getAddress());
                if (changePwd) {
                    u.setPassword(user.getPassword());
                }
                em.merge(u);
                tx.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }
}
