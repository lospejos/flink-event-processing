# Flink for Event-driven Systems (Batch/Stream processing)

[![Travis CI](https://travis-ci.org/iobruno/flink-event-processing.svg?branch=master)](https://travis-ci.org/iobruno/flink-event-processing)
[![codecov](https://codecov.io/gh/iobruno/flink-event-processing/branch/master/graph/badge.svg)](https://codecov.io/gh/iobruno/flink-event-processing)
[![Maintainability](https://api.codeclimate.com/v1/badges/e7c15ab052caecafead0/maintainability)](https://codeclimate.com/github/iobruno/flink-event-processing/maintainability)

This is just a pet project of mine to play with Flink Batch/Streaming APIs

The goal in here is to:
  - Batch Process huge text files of Sales Orders (found in the `datasets` folder) with Flink Table API,
  - Filter them (by region/country) and/or reduce them with the DataSet API 
  - And finally Stream a given DataSet to Kafka using the DataStream API with Flink/Kafka Connector
 
Later integrations with ElasticSearch and Cassandra will be added

## Up and Running

**Requirements**
- JDK 10+ (also tested with OpenJDK11 :heavy_check_mark:)
- Gradle

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

var kafkaConfig = new Properties();
kafkaConfig.setProperty("kafka.producer.bootstrap-server", "localhost:9092");

var kafkaService = new KafkaService<>(Order.class, kafkaConfig);
kafkaService.publish("kafka-topic", ordersFromEurope)
``` 

**Flink-Kafka Consumer**
```
var kafkaConfig = new Properties();
kafkaConfig.setProperty("kafka.consumer.bootstrap-server", "localhost:9092");
kafkaConfig.setProperty("kafka.consumer.zookeeper-server", "localhost:2181");
kafkaConfig.setProperty("kafka.consumer.group-id", "test-consumer-group");

var kafkaService = new KafkaService<>(Order.class, kafkaConfig);
var orders = kafkaService.subscribe("kafka-topic")
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
- [x] Automate the build with CI
- [x] Automate Code Coverage assertion
- [x] Automate high maintainability score 
- [ ] Automate vulnerabilities check 
- [ ] Build and ship with Graal VM


@iobruno
