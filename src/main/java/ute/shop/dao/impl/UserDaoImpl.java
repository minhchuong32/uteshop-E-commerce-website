package ute.shop.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import ute.shop.connection.DBConnectSQLServer;
import ute.shop.dao.IUserDao;
import ute.shop.models.User;

public class UserDaoImpl extends DBConnectSQLServer implements IUserDao {

	@Override
	public List<User> findAll() {
		String sql = "SELECT * FROM Users";
		List<User> list = new ArrayList<>();

		try (Connection conn = super.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				User user = new User(rs.getInt("user_id"), rs.getString("username"), rs.getString("password"),
						rs.getString("email"), rs.getString("role"), rs.getString("status"), rs.getDate("createDate"));
				list.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public void Insert(User user) {
	    String sql = "INSERT INTO Users(username, password, email, role, status, createDate) VALUES(?,?,?,?,?,?)";
	    try (Connection conn = super.getConnection(); 
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setString(1, user.getUsername());
	        ps.setString(2, user.getPassword()); // password đã hash ở Service
	        ps.setString(3, user.getEmail());
	        ps.setString(4, user.getRole());
	        ps.setString(5, user.getStatus());

	        // Lấy ngày hiện tại
	        java.sql.Timestamp now = new java.sql.Timestamp(System.currentTimeMillis());
	        ps.setTimestamp(6, now);

	        ps.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	@Override
	public void Update(User user) {
		String sql = "UPDATE Users SET username=?, password=?, email=?, role=?, status=? WHERE user_id=?";
		try (Connection conn = super.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword()); // password đã hash ở Service
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getRole());
			ps.setString(5, user.getStatus());
			ps.setInt(6, user.getUser_id());

			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void Delete(int id) {
		String sql = "DELETE FROM Users WHERE user_id=?";
		try (Connection conn = super.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public User findByEmail(String email) {
		String sql = "SELECT * FROM Users WHERE email=?";
		try (Connection conn = super.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, email);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return new User(rs.getInt("user_id"), rs.getString("username"), rs.getString("password"),
							rs.getString("email"), rs.getString("role"), rs.getString("status"), rs.getDate("createDate"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public User findByUsername(String username) {
		String sql = "SELECT * FROM Users WHERE username=?";
		try (Connection conn = super.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, username);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return new User(rs.getInt("user_id"), rs.getString("username"), rs.getString("password"),
							rs.getString("email"), rs.getString("role"), rs.getString("status"), rs.getDate("createDate"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updatePassword(String email, String newPassword) {
		String sql = "UPDATE Users SET password=? WHERE email=?";
		try (Connection conn = super.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, newPassword); // password đã hash ở Service
			ps.setString(2, email);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public User getUserById(int id) {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM Users WHERE user_id=?";
		try (Connection conn = super.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return new User(rs.getInt("user_id"), rs.getString("username"), rs.getString("password"),
							rs.getString("email"), rs.getString("role"), rs.getString("status"), rs.getDate("createDate"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}

	@Override
	public void updateStatus(int id, String status) {
	    String sql = "UPDATE users SET status = ? WHERE user_id = ?";
	    try (Connection conn = super.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setString(1, status);   // truyền status mới (active/blocked...)
	        ps.setInt(2, id);          // id user cần cập nhật
	        ps.executeUpdate();

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

}
