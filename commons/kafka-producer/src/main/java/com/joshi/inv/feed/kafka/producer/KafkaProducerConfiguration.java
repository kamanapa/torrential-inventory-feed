package com.joshi.inv.feed.kafka.producer;

import com.joshi.inv.feed.kafka.config.KafkaConfigProperties;
import com.joshi.inv.feed.kafka.config.KafkaProducerConfigProperties;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfiguration<K extends Serializable,V extends SpecificRecordBase> {
  private final KafkaConfigProperties kafkaConfigProperties;
  private final KafkaProducerConfigProperties kafkaProducerConfigProperties;

  public KafkaProducerConfiguration(KafkaConfigProperties kafkaConfigProperties, KafkaProducerConfigProperties kafkaProducerConfigProperties) {
    this.kafkaConfigProperties = kafkaConfigProperties;
    this.kafkaProducerConfigProperties = kafkaProducerConfigProperties;
  }

  public ProducerFactory<K,V> kafkaProducer() {
    Map<String, Object> properties = new HashMap<>();

    properties.put(kafkaConfigProperties.getSchemaRegistryUrlKey(), kafkaConfigProperties.getSchemaRegistryUrl());
    properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfigProperties.getBootstrapServers());

    properties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, kafkaProducerConfigProperties.getCompressionType());
    properties.put(ProducerConfig.BATCH_SIZE_CONFIG, kafkaProducerConfigProperties.getBatchSize() * kafkaProducerConfigProperties.getBatchSizeBoostFactor());

    properties.put(ProducerConfig.LINGER_MS_CONFIG, kafkaProducerConfigProperties.getLingerMs());
    properties.put(ProducerConfig.ACKS_CONFIG,kafkaProducerConfigProperties.getAcks());
    properties.put(ProducerConfig.RETRIES_CONFIG, kafkaProducerConfigProperties.getRetryCount());
    properties.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG,kafkaProducerConfigProperties.getRequestTimeoutMs());

    properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, kafkaProducerConfigProperties.getKeySerializerClass());
    properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,kafkaProducerConfigProperties.getValueSerializerClass());

    return new DefaultKafkaProducerFactory<>(properties);
  }

  @Bean
  public KafkaTemplate<K,V> kafkaTemplate() {
    return new KafkaTemplate<>(kafkaProducer());
  }
}
