package org.saikumo.pwams.config;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.saikumo.pwams.constant.CacheName;
import org.saikumo.pwams.properties.JwtProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {
	@Autowired
	private JwtProperties jwtProperties;

	/**
	 * 创建基于Caffeine的Cache Manager
	 *
	 * @return
	 */
	@Bean("caffeineCacheManager")
	public CacheManager caffeineCacheManager() {
		CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
		Caffeine<Object, Object> caffeine = Caffeine.newBuilder()
				// 设置初始容量
				.initialCapacity(5)
				// 失效策略 写入后按时间失效
				.refreshAfterWrite(jwtProperties.getExpirationTime(), TimeUnit.SECONDS);
		caffeineCacheManager.setCaffeine(caffeine);
		caffeineCacheManager.setCacheLoader(cacheLoader());
		caffeineCacheManager.setCacheNames(CacheName.getCacheNames());
		return caffeineCacheManager;
	}

	@Bean
	public CacheLoader<Object, Object> cacheLoader() {
		return new CacheLoader<Object, Object>() {
			@Override
			public Object load(Object key) throws Exception {
				return null;
			}

			// 达到配置文件中的refreshAfterWrite所指定的时候回处罚这个事件方法
			@Override
			public Object reload(Object key, Object oldValue) throws Exception {
				return oldValue;
			}
		};
	}
}
