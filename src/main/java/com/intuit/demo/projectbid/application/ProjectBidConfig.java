package com.intuit.demo.projectbid.application;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.wadl.internal.WadlResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.intuit.demo.projectbid.domain.core.Bid;
import com.intuit.demo.projectbid.domain.core.Project;
import com.intuit.demo.projectbid.domain.errorhandling.ProjectBidExceptionMapper;
import com.intuit.demo.projectbid.rest.resource.impl.ProjectResourceImpl;
import com.intuit.demo.projectbid.service.BidService;
import com.intuit.demo.projectbid.service.ProjectService;
import com.intuit.demo.projectbid.service.impl.v2.BidServiceImplV2;
import com.intuit.demo.projectbid.service.impl.v2.ProjectServiceImplV2;

import redis.clients.jedis.JedisPoolConfig;
import redis.embedded.RedisServer;


/**
 * ProjectBidConfig
 * @author Jeyavelu Pillay
 */
@Configuration
public class ProjectBidConfig extends ResourceConfig {
	
	RedisServer redisServer;
	
    public ProjectBidConfig()
    {    	
    	register(new AbstractBinder() {
            @Override
            protected void configure() {
            	bind(ProjectServiceImplV2.class).to(ProjectService.class);
            	bind(BidServiceImplV2.class).to(BidService.class);
            }
        });

         register(ProjectResourceImpl.class); 

    	 register(Project.class);
    	 register(Bid.class);
          
         register(ProjectBidExceptionMapper.class);
         
         this.register(WadlResource.class);
     
    }
    
   @PostConstruct
    public void startRedisServer() {    	
		try {
			redisServer = new RedisServer(6379);
	    	redisServer.start();
		} catch (IOException ex) {
			System.out.println("FATAL: Exception starting Embedded Redis Server : " + ex);
		}
    }
    
    @PreDestroy
    public void stopRedisServer() {
		redisServer.stop();
    }
    
    @Bean
	public RedisConnectionFactory redisConnectionFactory() {
		//JedisPoolConfig poolConfig = new JedisPoolConfig();
		//poolConfig.setMaxTotal(5);
		//poolConfig.setTestOnBorrow(true);
		//poolConfig.setTestOnReturn(true);		
		//JedisConnectionFactory connectionFactory = new JedisConnectionFactory(poolConfig);
		
		JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
		//connectionFactory.setUsePool(true);
		connectionFactory.setHostName("localhost");
		connectionFactory.setPort(6379);
		
		return connectionFactory;
	}
    
	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		System.out.println("GET REDIS TEMPLATE");
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		redisTemplate.setEnableTransactionSupport(true);
		return redisTemplate;
	}
    
    
    
}