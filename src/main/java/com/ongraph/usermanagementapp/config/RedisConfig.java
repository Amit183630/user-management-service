package com.ongraph.usermanagementapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.ongraph.usermanagementapp.entity.User;

@Configuration
public class RedisConfig {

	@Value("${spring.data.redis.host}")
	private String hostName;

	@Value("${spring.data.redis.port}")
	private int port;
	
	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
		RedisStandaloneConfiguration redisStandaloneConfiguration=new RedisStandaloneConfiguration(this.hostName,this.port);
		return new JedisConnectionFactory(redisStandaloneConfiguration);
	}
	
	@Bean
	public RedisTemplate<String, User> redisTemplate(){
		RedisTemplate<String, User> template=new RedisTemplate<>();
		template.setConnectionFactory(jedisConnectionFactory());
		return template;
	}
}
