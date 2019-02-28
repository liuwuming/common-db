package com.zfec.common.db.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import com.zfec.common.db.bean.RedisProperties;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;

@Configuration
@AutoConfigureAfter({ RedisProperties.class })
public class RedisConfig {

	@Resource
	private RedisProperties redisProperties;

	@Bean(name = "jedisPoolConfig")
	public JedisPoolConfig getJedisPoolConfig() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxIdle(redisProperties.getMaxIdle());
		jedisPoolConfig.setMaxWaitMillis(redisProperties.getMaxWait());
		jedisPoolConfig.setTestOnBorrow(redisProperties.isTestOnBorrow());
		jedisPoolConfig.setMinIdle(redisProperties.getMinIdle());

		return jedisPoolConfig;
	}

	@Bean
	public RedisClusterConfiguration getRedisClusterConfiguration() {
		RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
//		redisClusterConfiguration.setPassword(RedisPassword.of(redisProperties.getPassword()));
		redisClusterConfiguration.setMaxRedirects(2);

		List<RedisNode> nodeList = new ArrayList<RedisNode>();

		String[] cNodes = redisProperties.getNodes().split(",");
		for (String node : cNodes) {
			String[] hp = node.split(":");
			nodeList.add(new RedisNode(hp[0], Integer.parseInt(hp[1])));
		}
		redisClusterConfiguration.setClusterNodes(nodeList);
		
		return redisClusterConfiguration;
	}

	@Bean
	public JedisConnectionFactory getJedisConnectionFactory(RedisClusterConfiguration redisClusterConfiguration,
			JedisPoolConfig jedisPoolConfig) {
		JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory(redisClusterConfiguration,
				jedisPoolConfig);
		
		return redisConnectionFactory;
	}

	@Bean(name = "jedisCluster")
	public JedisCluster getJedisCluster(JedisPoolConfig jedisPoolConfig) {
		Set<HostAndPort> hostAndPortsSet = new HashSet<HostAndPort>();
		String[] cNodes = redisProperties.getNodes().split(",");
		for (String node : cNodes) {
			String[] hp = node.split(":");
			hostAndPortsSet.add(new HostAndPort(hp[0], Integer.parseInt(hp[1])));
		}

		JedisCluster jedisCluster = new JedisCluster(hostAndPortsSet, redisProperties.getTimeout(),
				redisProperties.getSoTimeout(), redisProperties.getMaxAttempts(), jedisPoolConfig);
		return jedisCluster;
	}

	@Bean(name = "redisTemplate")
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		StringRedisSerializer stringSerializer = new StringRedisSerializer();
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		redisTemplate.setKeySerializer(stringSerializer);
		redisTemplate.setHashKeySerializer(stringSerializer);
		redisTemplate.afterPropertiesSet();

		return redisTemplate;
	}
	
/*
 * 不使用
 */
//	@Bean
//	@SuppressWarnings("unchecked")
//	public SimpleCacheManager getRedisCacheManager(JedisConnectionFactory redisConnectionFactory) {
//		List<Cache>lists = new ArrayList<Cache>();
//		lists.add(new RedisCache());
//		SimpleCacheManager redisCacheManager = new SimpleCacheManager();
//		redisCacheManager.setCaches(lists);
//		return redisCacheManager;
//	}
}
