package ute.shop.dao;

import java.util.List;

import ute.shop.entity.Notification;

public interface INotificationDao {
	List<Notification> getAllByUserId(int userId);

	List<Notification> findByUserId(int userId);

	Notification findById(int id);

	void update(Notification noti);

	List<Notification> findUnreadByUserId(int userId);

	void insert(Notification n);
}
