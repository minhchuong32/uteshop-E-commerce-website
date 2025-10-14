package ute.shop.service;

import java.util.List;

import ute.shop.entity.Notification;

public interface INotificationService {
	List<Notification> getAllByUserId(int userId);

	List<Notification> findByUserId(int userId);

	Notification findById(int id);

	void update(Notification noti);

	List<Notification> findUnreadByUserId(int userId);

	void insert(Notification n);
}
