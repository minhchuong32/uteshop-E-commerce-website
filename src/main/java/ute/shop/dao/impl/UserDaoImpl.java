package ute.shop.dao.impl;

import jakarta.persistence.*;
import ute.shop.config.JPAConfig;
import ute.shop.dao.IUserDao;
import ute.shop.entity.User;

import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements IUserDao {

	@Override
	public List<User> findAll() {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			return em.createQuery("SELECT u FROM User u", User.class).getResultList();
		} finally {
			em.close();
		}
	}

	@Override
	public User insert(User user) { // Thay đổi kiểu trả về
		EntityManager em = JPAConfig.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			em.persist(user);
			tx.commit();
			return user; // Trả về đối tượng user đã có ID
		} catch (Exception e) {
			if (tx.isActive())
				tx.rollback();
			e.printStackTrace();
			return null; // Trả về null nếu có lỗi
		} finally {
			em.close();
		}
	}

	@Override
	public boolean update(User user) {
		EntityManager em = JPAConfig.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			em.merge(user);
			tx.commit();
			return true;
		} catch (Exception e) {
			if (tx.isActive())
				tx.rollback();
			e.printStackTrace();
			return false;
		} finally {
			em.close();
		}
	}

	@Override
	public void delete(int id) {
		EntityManager em = JPAConfig.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			User u = em.find(User.class, id);
			if (u != null)
				em.remove(u);
			tx.commit();
		} catch (Exception e) {
			if (tx.isActive())
				tx.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
	}

	@Override
	public Optional<User> findByEmail(String email) {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			TypedQuery<User> q = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
			q.setParameter("email", email);
			return q.getResultStream().findFirst();
		} finally {
			em.close();
		}
	}

	@Override
	public Optional<User> findByUsername(String username) {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			TypedQuery<User> q = em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
			q.setParameter("username", username);
			return q.getResultStream().findFirst();
		} finally {
			em.close();
		}
	}

	@Override
	public Optional<User> getUserById(int id) {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			return Optional.ofNullable(em.find(User.class, id));
		} finally {
			em.close();
		}
	}

	@Override
	public boolean updatePassword(String email, String newPassword) {
		EntityManager em = JPAConfig.getEntityManager();
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
			if (tx.isActive())
				tx.rollback();
			e.printStackTrace();
			return false;
		} finally {
			em.close();
		}
	}

	@Override
	public void updateStatus(int id, String status) {
		EntityManager em = JPAConfig.getEntityManager();
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
			if (tx.isActive())
				tx.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
	}

	@Override
	public boolean updatePwd(User user, boolean changePwd) {
		EntityManager em = JPAConfig.getEntityManager();
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
			if (tx.isActive())
				tx.rollback();
			e.printStackTrace();
			return false;
		} finally {
			em.close();
		}
	}

	@Override
	public List<User> getUsersByRole(String role) {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			// Truy vấn tất cả user có role trùng khớp (ví dụ: "Vendor")
			TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.role = :role", User.class);
			query.setParameter("role", role);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return List.of(); // trả về danh sách rỗng nếu lỗi
		} finally {
			em.close();
		}
	}

	@Override
	public long countAllUsers() {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			return em.createQuery("SELECT COUNT(u) FROM User u", Long.class).getSingleResult();
		} finally {
			em.close();
		}
	}

	@Override
	public Optional<User> findByGoogleId(String googleId) {
		EntityManager em = JPAConfig.getEntityManager();
		try {
			TypedQuery<User> q = em.createQuery("SELECT u FROM User u WHERE u.googleId = :googleId", User.class);
			q.setParameter("googleId", googleId);
			// Dùng getResultStream().findFirst() để tránh lỗi NoResultException
			return q.getResultStream().findFirst();
		} finally {
			em.close();
		}
	}
	
}
