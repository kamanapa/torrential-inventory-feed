package com.joshi.inv.feed.kafka.producer.service.impl;

import com.joshi.inv.feed.kafka.producer.service.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.PreDestroy;
import java.io.Serializable;

@Slf4j
public class KafkaProducerImpl <K extends Serializable, V extends SpecificRecordBase> implements KafkaProducer<K,V> {
  private final KafkaTemplate<K,V> kafkaTemplate;

  public KafkaProducerImpl(KafkaTemplate<K, V> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  @Override
  public void dispatch(String topicName, K key, V value, ListenableFutureCallback<SendResult<K,V>> cb) {
    log.info("");
    ListenableFuture<SendResult<K,V>> future  = kafkaTemplate.send(topicName, key, value);
    future.addCallback(cb, ex -> {
        log.error("Error dispatching message for topic: {} key {} value {}", topicName, key, value);
    });
  }


  @PreDestroy
  public void close() {
    if (kafkaTemplate !=null) {
      kafkaTemplate.destroy();
      log.info("Destroying kafka producer");
    }
  }
}
