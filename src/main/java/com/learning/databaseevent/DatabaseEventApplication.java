package com.learning.databaseevent;

import com.learning.databaseevent.api.dataobject.Order;
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

	public DatabaseEventApplication (OrderService orderService){
		this.orderService = orderService;
	}

	@Override
	public void run(String... args) throws Exception {
		Order order = new Order();
		order.setOrderType("purchase");
		order.setOrderStatus("completed");

		orderService.createOrder(order);
	}

	public static void main(String[] args) {
		SpringApplication.run(DatabaseEventApplication.class, args);
	}
}
