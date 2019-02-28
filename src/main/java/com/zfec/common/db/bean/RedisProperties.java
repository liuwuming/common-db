package com.zfec.common.db.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "zfec.redis")
public class RedisProperties {
	
	/*
		zfec.redis.nodes = 192.168.47.103:6381,192.168.47.103:6382,192.168.47.103:6383
		zfec.redis.password = 123456
		# 连接池最大连接数（使用负值表示没有限制）
		zfec.redis.maxActive = 8  
		# 连接池最大阻塞等待时间（使用负值表示没有限制）
		zfec.redis.maxWait = 4000  
		# 连接池中的最大空闲连接
		zfec.redis.maxIdle = 8  
		# 连接池中的最小空闲连接
		zfec.redis.minIdle = 0  
		# 连接超时时间（毫秒）
		zfec.redis.timeout = 5000
		# 是否在从池中取出连接前进行检验,如果检验失败,则从池中去除连接并尝试取出另一个
		zfec.redis.testOnBorrow = true
		# 执行命令超时时间
		zfec.redis.soTimeout = 15000
		# 重试次数
		zfec.redis.maxAttempts = 5
	 */
	
	String nodes;
	String password;
//	Integer maxActive;
	Integer maxWait;
	Integer maxIdle;
	Integer minIdle;
	Integer timeout;
	boolean testOnBorrow;
	Integer maxAttempts;
	Integer soTimeout;
	
	public String getNodes() {
		return nodes;
	}
	public void setNodes(String nodes) {
		this.nodes = nodes;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
//	public Integer getMaxActive() {
//		return maxActive;
//	}
//	public void setMaxActive(Integer maxActive) {
//		this.maxActive = maxActive;
//	}
	public Integer getMaxWait() {
		return maxWait;
	}
	public void setMaxWait(Integer maxWait) {
		this.maxWait = maxWait;
	}
	public Integer getMaxIdle() {
		return maxIdle;
	}
	public void setMaxIdle(Integer maxIdle) {
		this.maxIdle = maxIdle;
	}
	public Integer getMinIdle() {
		return minIdle;
	}
	public void setMinIdle(Integer minIdle) {
		this.minIdle = minIdle;
	}
	public Integer getTimeout() {
		return timeout;
	}
	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}
	public boolean isTestOnBorrow() {
		return testOnBorrow;
	}
	public void setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}
	public Integer getSoTimeout() {
		return soTimeout;
	}
	public void setSoTimeout(Integer soTimeout) {
		this.soTimeout = soTimeout;
	}
	public Integer getMaxAttempts() {
		return maxAttempts;
	}
	public void setMaxAttempts(Integer maxAttempts) {
		this.maxAttempts = maxAttempts;
	}
}
