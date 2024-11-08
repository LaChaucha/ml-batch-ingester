package com.waterworks.mlqs.batch.ingester.infra.redisout.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.Kryo5Codec;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.TransportMode;
import org.redisson.connection.balancer.RoundRobinLoadBalancer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Configuration class for Redisson caching. Enables caching and configures the Redisson client for
 * caching purposes.
 *
 * @author Gonzalo Zarazaga
 * @version 1.0
 */
@Configuration
@EnableCaching
@Profile({"dev", "staging", "prod"})
public class RedissonConfig {

  public static final int THREADS = 16;
  public static final int NETTY_THREADS = 32;
  public static final int IDLE_CONNECTION_TIMEOUT = 10000;
  public static final int CONNECT_TIMEOUT = 10000;
  public static final int TIMEOUT = 3000;
  public static final int RETRY_ATTEMPTS = 3;
  public static final int RETRY_INTERVAL = 1500;
  public static final int FAILED_SLAVES_RECONNECTION_TIMEOUT = 30000;
  public static final int SLAVE_FAILS_INTERVAL = 60000;
  public static final int SUBSCRIPTIONS_PER_CONNECTION = 5;
  public static final int SUBSCRIPTION_CONNECTION_MINIMUM_IDLE_SIZE = 1;
  public static final int SUBSCRIPTION_CONNECTION_POOL_SIZE = 50;
  public static final int SLAVE_CONNECTION_MINIMUM_IDLE_SIZE = 24;
  public static final int SLAVE_CONNECTION_POOL_SIZE = 64;
  public static final int MASTER_CONNECTION_MINIMUM_IDLE_SIZE = 24;
  public static final int MASTER_CONNECTION_POOL_SIZE = 63;
  public static final int SCAN_INTERVAL = 10000;


  @Value("#{'${spring.redis.replicated.nodes}'.split(',')}")
  private String[] redisNodes;

  /**
   * Creates a RedissonClient bean with the specified configuration for caching purposes.
   *
   * @return A RedissonClient instance configured for caching.
   */
  @Bean(destroyMethod = "shutdown")
  public RedissonClient redissonClient() {
    Config config = new Config();

    config.setThreads(THREADS);
    config.setNettyThreads(NETTY_THREADS);
    config.setTransportMode(TransportMode.NIO);
    config.setCodec(new Kryo5Codec());

    ClusterServersConfig clusterConfig = config.useClusterServers();

    clusterConfig.addNodeAddress(redisNodes);
    clusterConfig.setIdleConnectionTimeout(IDLE_CONNECTION_TIMEOUT);
    clusterConfig.setConnectTimeout(CONNECT_TIMEOUT);
    clusterConfig.setTimeout(TIMEOUT);
    clusterConfig.setRetryAttempts(RETRY_ATTEMPTS);
    clusterConfig.setRetryInterval(RETRY_INTERVAL);
    clusterConfig.setFailedSlaveReconnectionInterval(FAILED_SLAVES_RECONNECTION_TIMEOUT);
    clusterConfig.setFailedSlaveCheckInterval(SLAVE_FAILS_INTERVAL);
    clusterConfig.setSubscriptionsPerConnection(SUBSCRIPTIONS_PER_CONNECTION);
    clusterConfig.setLoadBalancer(new RoundRobinLoadBalancer());
    clusterConfig.setSubscriptionConnectionMinimumIdleSize(
        SUBSCRIPTION_CONNECTION_MINIMUM_IDLE_SIZE);
    clusterConfig.setSubscriptionConnectionPoolSize(SUBSCRIPTION_CONNECTION_POOL_SIZE);
    clusterConfig.setSlaveConnectionMinimumIdleSize(SLAVE_CONNECTION_MINIMUM_IDLE_SIZE);
    clusterConfig.setSlaveConnectionPoolSize(SLAVE_CONNECTION_POOL_SIZE);
    clusterConfig.setMasterConnectionMinimumIdleSize(MASTER_CONNECTION_MINIMUM_IDLE_SIZE);
    clusterConfig.setMasterConnectionPoolSize(MASTER_CONNECTION_POOL_SIZE);
    clusterConfig.setScanInterval(SCAN_INTERVAL);

    return Redisson.create(config);
  }
}
