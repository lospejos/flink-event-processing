# Flink for Event-driven Systems (Batch/Stream processing)

This is just a pet project of mine to play with Flink Batch/Streaming APIs

The goal in here is to:
  - Batch Process huge text files of Sales Orders (found on the `datasets` folder) with Flink Table API,
  - Filter them (by region/country) and/or reduce them with the DataSet API 
  - And finally Stream a given DataSet to Kafka using the DataStream API with Flink/Kafka Connector
 
Later integrations with ElasticSearch and Cassandra will be added

## Up and Running

**Requirements**
- JDK 10

**Building**
```
./gradlew build
```

**Testing**
```
./gradlew test
```

## Usage 

**Flink-Kafka Producer**
```
var file = // some SALES_RECORDS_FILE.csv 
var repo = new OrderRepository();
repo.process(file, ",");

var ordersFromEurope = repo.findOrdersByRegion("europe");
var kafkaService = new KafkaService<>(Order.class);

kafkaService.publish("orders-topic-on-kafka", ordersFromEurope)
``` 

**Flink-Kafka Consumer**
```
var kafkaService = new KafkaService<>(Order.class);
var orders = kafkaService.subscribe("orders-topic-on-kafka")
```

## TODOs

**Batch API**
- [x] DataSet API
- [ ] MapReduce on DataSets
- [x] Batch Table API (CSV Source)

**Streaming API**
- [x] DataStream API
- [x] Kafka Connector 
- [ ] Cassandra Connector
- [ ] ElasticSearch Connector

**Building and Shipping**
- [ ] Automate the build with CI
- [ ] Automate Code Coverage assertion
- [ ] Automate high maintainability score 
- [ ] Automate vulnerabilities check 
- [ ] Build and ship with Graal VM


@iobruno
