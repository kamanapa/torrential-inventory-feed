# Architecture
Demonstration of [CQRS](https://martinfowler.com/bliki/CQRS.html) using Apache Kafka as an event-store (the C in CQRS), elasticsearch as an eventually consistent data store for the Q in CQRS, and Spring Boot/Cloud for convenience and ["12 factorness."](https://12factor.net/)

![High Level Architecture](torrential-feed/torrential.png "architecture")

# Elasticsearch document mapping and indexing
From wikipedia: 
> "Elasticsearch is distributed, which means that indices can be divided into shards and each shard can have zero or more replicas. Each node hosts one or more shards and acts as a coordinator to delegate operations to the correct shard(s). Rebalancing and routing are done automatically".[28] Related data is often stored in the same index, which consists of one or more primary shards, and zero or more replica shards. Once an index has been created, the number of primary shards cannot be changed."

You can find guidance on how `elasticsearch` stores and indexes documents [here.](https://www.elastic.co/guide/en/elasticsearch/reference/current/explicit-mapping.html) 

The official Java client for `elasticsearch` can be found [here.](https://github.com/elastic/elasticsearch-java/) The documentation for this client is hosted [here.](https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/current/index.html) 

# Apache Kafka
From the Apache Foundation:

> "Apache Kafka is an open-source distributed event streaming platform used by thousands of companies for high-performance data pipelines, streaming analytics, data integration, and mission-critical applications." 

The Kafka platform is built on the following core foundations: 

1. *High Throughput*:  Deliver messages at network limited throughput using a cluster of machines with latencies as low as 2ms.

2. *Scalable*: Scale production clusters up to a thousand brokers, trillions of messages per day, petabytes of data, hundreds of thousands of partitions. Elastically expand and contract storage and processing.
 
3. *Permanent Store*: Store streams of data safely in a distributed, durable, fault-tolerant cluster.

4. *High Availability*: Stretch clusters efficiently over availability zones or connect separate clusters across geographic regions.

We will use `docker-compose` file from *Confluent* Kafka. Find it [here.](https://github.com/confluentinc/cp-all-in-one/blob/7.3.0-post/cp-all-in-one/docker-compose.yml) 

Documentation for Kafka can be found [here.](https://kafka.apache.org/20/documentation.html) N.B. we will be using version <3.3.0 of Kafka i.e., pre KRaft.

# Build
You will need `docker-compose` and `make` to run the command to spin up instances of kafka and elasticsearch.
```
make local # build local target
```
