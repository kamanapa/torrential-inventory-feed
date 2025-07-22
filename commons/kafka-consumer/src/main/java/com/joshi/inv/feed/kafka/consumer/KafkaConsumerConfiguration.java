package com.joshi.inv.feed.kafka.consumer;

import com.joshi.inv.feed.kafka.config.KafkaConfigProperties;
import com.joshi.inv.feed.kafka.config.KafkaConsumerConfigProperties;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfiguration<K extends Serializable, V extends SpecificRecordBase> {
  private final KafkaConfigProperties kafkaConfigProperties;
  private final KafkaConsumerConfigProperties kafkaConsumerConfigProperties;

  public KafkaConsumerConfiguration(KafkaConfigProperties kafkaConfigProperties, KafkaConsumerConfigProperties kafkaConsumerConfigProperties) {
    this.kafkaConfigProperties = kafkaConfigProperties;
    this.kafkaConsumerConfigProperties = kafkaConsumerConfigProperties;
  }

  public Map<String, Object> consumerConfig() {
    HashMap<String, Object> properties = new HashMap<>();

    properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfigProperties.getBootstrapServers());
    properties.put(kafkaConfigProperties.getSchemaRegistryUrlKey(), kafkaConfigProperties.getSchemaRegistryUrl());

    properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, kafkaConsumerConfigProperties.getKeyDeserializer());
    properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,kafkaConsumerConfigProperties.getValueDeserializer());

    properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG,kafkaConsumerConfigProperties.getMaxPollRecords());
    properties.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, kafkaConsumerConfigProperties.getMaxPollIntervalMs());
    properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG,kafkaConsumerConfigProperties.getSessionTimeoutMs());
    properties.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, kafkaConsumerConfigProperties.getHeartBeatIntervalMs());
    properties.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, kafkaConsumerConfigProperties.getMaxPartitionFetchBytesDefault() * kafkaConsumerConfigProperties.getMaxPartitionFetchBytesBoostFactor());

    return properties;
  }

  @Bean
  public ConsumerFactory<K,V> consumerFactory() {
    return new DefaultKafkaConsumerFactory<>(consumerConfig());
  }

  @Bean
  public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<K,V>> kafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<K,V> concurrentKafkaListenerContainerFactory
      = new ConcurrentKafkaListenerContainerFactory<>();
    concurrentKafkaListenerContainerFactory.setConsumerFactory(consumerFactory());
    concurrentKafkaListenerContainerFactory.setBatchListener(kafkaConsumerConfigProperties.getBatchListener());
    concurrentKafkaListenerContainerFactory.setAutoStartup(kafkaConsumerConfigProperties.getAutoStartUp());
    concurrentKafkaListenerContainerFactory.getContainerProperties().setPollTimeout(kafkaConsumerConfigProperties.getPollTimeoutMs());
    return concurrentKafkaListenerContainerFactory;
  }
}
