package com.learning.databaseevent;

import com.learning.databaseevent.dataobject.Order;
import com.learning.databaseevent.service.ProductDataLoadService;
import com.learning.databaseevent.service.OrderService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableCaching
public class DatabaseEventApplication implements CommandLineRunner {

	private OrderService orderService;
	private ProductDataLoadService productDataLoadService;

	public DatabaseEventApplication (OrderService orderService, ProductDataLoadService productDataLoadService) {
		this.orderService = orderService;
		this.productDataLoadService = productDataLoadService;
	}

	@Override
	public void run(String... args) throws Exception {
		//load the products table
		productDataLoadService.loadDatabase();

//		Order order = new Order();
//		order.setOrderType("purchase");
//		order.setOrderStatus("completed");
//		order.setPrId("42671");
//
//		orderService.createOrder(order);
	}

	public static void main(String[] args) {
		SpringApplication.run(DatabaseEventApplication.class, args);
	}
}
