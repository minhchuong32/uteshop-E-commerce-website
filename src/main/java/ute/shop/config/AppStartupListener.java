package ute.shop.config;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ute.shop.factory.PaymentFactoryBootstrap;
import ute.shop.observer.NotificationObserver;
import ute.shop.observer.OrderEventPublisher;


@WebListener
public class AppStartupListener implements ServletContextListener {

	// AppStartupListener.java — thêm vào contextInitialized()
	@Override
	public void contextInitialized(ServletContextEvent sce) {
	    try {
	        Class.forName(PaymentFactoryBootstrap.class.getName());
	    } catch (ClassNotFoundException e) {
	        throw new RuntimeException(e);
	    }

	    // Đăng ký Observer một lần duy nhất khi server khởi động
	    OrderEventPublisher publisher = OrderEventPublisher.getInstance();
	    publisher.subscribe(new NotificationObserver());
	    // publisher.subscribe(new EmailObserver()); // Dễ mở rộng sau
	}
}