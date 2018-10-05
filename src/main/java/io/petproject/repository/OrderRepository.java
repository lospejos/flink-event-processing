package io.petproject.repository;

import io.petproject.model.Order;
import io.petproject.model.Priority;
import io.petproject.model.SalesOrder;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.Types;
import org.apache.flink.table.api.java.BatchTableEnvironment;
import org.apache.flink.table.sources.CsvTableSource;
import org.apache.flink.types.Row;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

public class OrderRepository {

   private ExecutionEnvironment batchEnv;
   private BatchTableEnvironment batchTableEnv;
   private final static String ORDER_TABLE = "orders";

   public OrderRepository() {
      batchEnv = ExecutionEnvironment.getExecutionEnvironment();
      batchTableEnv = TableEnvironment.getTableEnvironment(batchEnv);
   }

   public List<Order> findOrdersByRegion(String regionName) throws Exception {
      Table tOrders = batchTableEnv.scan(ORDER_TABLE)
         .filter(String.format("region.lowerCase() === '%s'", regionName.toLowerCase()))
         .select("order_id, category, priority, units_sold, unit_price, unit_cost, region, country");

      return batchTableEnv
         .toDataSet(tOrders, Row.class)
         .map((MapFunction<Row, Order>) row -> {
            Long orderId = (Long) row.getField(0);
            String category = row.getField(1).toString();
            Priority priority = Priority.of(row.getField(2).toString());
            Integer unitsSold = (Integer) row.getField(3);
            BigDecimal unitPrice = new BigDecimal(row.getField(4).toString());
            BigDecimal unitCost = new BigDecimal(row.getField(5).toString());
            String region = row.getField(6).toString();
            String country = row.getField(7).toString();

            SalesOrder salesOrder = new SalesOrder(unitsSold, unitPrice, unitCost);
            return new Order(orderId, category, priority, salesOrder);
         }).collect();
   }

   public void process(File csvFile, String delimiter) {
      batchTableEnv.registerTableSource(ORDER_TABLE, CsvTableSource.builder()
         .path(csvFile.getPath())
         .ignoreFirstLine()
         .fieldDelimiter(delimiter)
         .field("region", Types.STRING())
         .field("country", Types.STRING())
         .field("category", Types.STRING())
         .field("sales_channel", Types.STRING())
         .field("priority", Types.STRING())
         .field("order_date", Types.STRING())
         .field("order_id", Types.LONG())
         .field("shipDate", Types.STRING())
         .field("units_sold", Types.INT())
         .field("unit_price", Types.DOUBLE())
         .field("unit_cost", Types.DOUBLE())
         .field("total_revenue", Types.DOUBLE())
         .field("total_cost", Types.DOUBLE())
         .field("total_profit", Types.DOUBLE())
         .build()
      );
   }

}
