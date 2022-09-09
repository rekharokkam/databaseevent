package com.learning.databaseevent;

import com.learning.databaseevent.api.dataobject.Order;
import com.learning.databaseevent.catalogreplacer.ProductDataLoadService;
import com.learning.databaseevent.repository.entity.OrderEntity;
import com.learning.databaseevent.service.OrderService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class DatabaseEventApplication implements CommandLineRunner {

	private OrderService orderService;
	private ProductDataLoadService productDataLoadService;

	public DatabaseEventApplication (OrderService orderService, ProductDataLoadService productDataLoadService) {
		this.orderService = orderService;
		this.productDataLoadService = productDataLoadService;
	}

	@Override
	public void run(String... args) throws Exception {
		Order order = new Order();
		order.setOrderType("purchase");
		order.setOrderStatus("completed");

		orderService.createOrder(order);

		productDataLoadService.loadDatabase();
	}

	public static void main(String[] args) {
		SpringApplication.run(DatabaseEventApplication.class, args);
	}
}
