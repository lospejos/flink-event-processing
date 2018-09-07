package io.petproject.service;

import io.petproject.model.Order;
import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.api.common.serialization.TypeInformationSerializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011;

import java.util.Collection;

public class KafkaService<T> {

   private final static String KAFKA_SERVER = "localhost:9092";
   private StreamExecutionEnvironment sEnv;
   private Class<T> tClass;

   public KafkaService() {
      this.sEnv = StreamExecutionEnvironment.getExecutionEnvironment();
      // TODO: Get Parametrized class somehow for tClass
      this.tClass = (Class<T>) Order.class;
   }

   public void publish(String kafkaTopic, Collection<T> collection) throws Exception {
      DataStream<T> dataStream = sEnv.fromCollection(collection);
      dataStream.addSink(getKafkaProducer(kafkaTopic));
      sEnv.execute();
   }

   private FlinkKafkaProducer011<T> getKafkaProducer(String kafkaTopic) {
      TypeInformationSerializationSchema<T> serializationSchema = new TypeInformationSerializationSchema<>(
         TypeInformation.of(tClass),
         new ExecutionConfig()
      );
      return new FlinkKafkaProducer011<>(KAFKA_SERVER, kafkaTopic, serializationSchema);
   }

}
