package ute.shop.service.impl;

import ute.shop.dao.impl.NotificationDaoImpl;
import ute.shop.entity.Notification;
import java.util.List;

public class NotificationServiceImpl {
    private final NotificationDaoImpl dao = new NotificationDaoImpl();

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
}
