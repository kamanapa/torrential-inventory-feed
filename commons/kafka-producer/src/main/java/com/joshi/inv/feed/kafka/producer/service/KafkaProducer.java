package com.joshi.inv.feed.kafka.producer.service;

import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.PreDestroy;
import java.io.Serializable;

public interface KafkaProducer<K extends Serializable, V extends SpecificRecordBase>{
  void dispatch (String topicName, K key, V value, ListenableFutureCallback<SendResult<K,V>> cb);
}
