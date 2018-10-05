package io.petproject.service;

import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.api.common.serialization.TypeInformationSerializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011;

import java.util.Collection;
import java.util.Properties;

public class KafkaService<T> {

   private StreamExecutionEnvironment streamEnv;
   private Properties kafkaConfig;
   private Class<T> tClass;

   public KafkaService(Class<T> tClass, Properties kafkaConfig) {
      this.streamEnv = StreamExecutionEnvironment.getExecutionEnvironment();
      this.kafkaConfig = kafkaConfig;
      this.tClass = tClass; // TODO: Workaround to deal with generics type erasure
   }

   public void publish(String kafkaTopic, Collection<T> collection) throws Exception {
      var dataStream = streamEnv.fromCollection(collection);
      dataStream.addSink(getKafkaProducer(kafkaTopic));
      streamEnv.execute();
   }

   public DataStream<T> subscribe(String kafkaTopic) {
      return streamEnv.addSource(getKafkaConsumer(kafkaTopic));
   }

   private FlinkKafkaProducer011<T> getKafkaProducer(String kafkaTopic) {
      var kafkaProducer = new FlinkKafkaProducer011<>(
         kafkaConfig.getProperty("kafka.producer.bootstrap-server"),
         kafkaTopic,
         getSerializationSchema()
      );
      kafkaProducer.setWriteTimestampToKafka(true);
      return kafkaProducer;
   }

   private FlinkKafkaConsumer011<T> getKafkaConsumer(String kafkaTopic) {
      var kafkaConsumer = new FlinkKafkaConsumer011<>(
         kafkaTopic,
         getSerializationSchema(),
         getKafkaConsumerProperties()
      );
      kafkaConsumer.setStartFromGroupOffsets();
      return kafkaConsumer;
   }

   private TypeInformationSerializationSchema<T> getSerializationSchema() {
      return new TypeInformationSerializationSchema<>(TypeInformation.of(tClass), new ExecutionConfig());
   }

   private Properties getKafkaConsumerProperties() {
      Properties kafkaConsumer = new Properties();
      kafkaConsumer.setProperty("bootstrap.servers", kafkaConfig.getProperty("kafka.consumer.bootstrap-server"));
      kafkaConsumer.setProperty("zookeeper.connect", kafkaConfig.getProperty("kafka.consumer.zookeeper-server"));
      kafkaConsumer.setProperty("group.id", kafkaConfig.getProperty("kafka.consumer.group-id"));
      return kafkaConsumer;
   }

   protected StreamExecutionEnvironment getStreamEnv() {
      return streamEnv;
   }

}
