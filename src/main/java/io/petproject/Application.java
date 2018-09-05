package io.petproject;

import io.petproject.model.Order;
import io.petproject.repository.OrderRepository;

import java.io.File;
import java.util.List;

public class Application {

   public static void main(String[] args) throws Exception {
      File file = new File(args[0]);

      OrderRepository repo = new OrderRepository();
      repo.process(file, ",");
      List<Order> ordersFromEurope = repo.findOrdersByRegion("europe");

      // TODO: event stream to Kafka
      // TODO: implement reduce function to consolidate overall OrdersProfit from a region/country
   }

}
