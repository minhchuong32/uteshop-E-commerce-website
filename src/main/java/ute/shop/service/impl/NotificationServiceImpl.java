package ute.shop.service.impl;

import ute.shop.dao.INotificationDao;
import ute.shop.dao.impl.NotificationDaoImpl;
import ute.shop.entity.Notification;
import ute.shop.service.INotificationService;

import java.util.List;

public class NotificationServiceImpl implements INotificationService {
	private final INotificationDao dao = new NotificationDaoImpl();

	public List<Notification> getUnreadByUserId(int userId) {
		return dao.findUnreadByUserId(userId);
	}

	public void insert(Notification noti) {
		dao.insert(noti);
	}

	public void update(Notification noti) {
		dao.update(noti);
	}

	public Notification findById(int id) {
		return dao.findById(id);
	}

	public List<Notification> getAllByUserId(int userId) {
		return dao.getAllByUserId(userId);
	}

	@Override
	public List<Notification> findByUserId(int userId) {
		return dao.findByUserId(userId);
	}

	@Override
	public List<Notification> findUnreadByUserId(int userId) {
		return dao.findUnreadByUserId(userId);
	}
}
