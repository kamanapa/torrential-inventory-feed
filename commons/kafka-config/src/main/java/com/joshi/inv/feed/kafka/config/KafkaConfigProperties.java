package com.joshi.inv.feed.kafka.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix="kafka-config")
public class KafkaConfigProperties {
  private String bootstrapServers;
  private String schemaRegistryUrlKey;
  private String schemaRegistryUrl;
  private Integer numOfPartitions;
  private Short replicationFactor;
}
