package org.saikumo.pwams.cache;

import lombok.extern.slf4j.Slf4j;
import org.saikumo.pwams.constant.CacheName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class CaffeineCache implements Cache{
	@Autowired
	private CacheManager caffeineCacheManager;


	public <T> T get(CacheName cacheName, String key, Class<T> clazz) {
		log.debug("{} get -> cacheName [{}], key [{}], class type [{}]", this.getClass().getName(), cacheName, key, clazz.getName());
		return Objects.requireNonNull(caffeineCacheManager.getCache(cacheName.getCacheName())).get(key, clazz);
	}

	public void put(CacheName cacheName, String key, Object value) {
		log.debug("{} put -> cacheName [{}], key [{}], value [{}]", this.getClass().getName(), cacheName, key, value);
		Objects.requireNonNull(caffeineCacheManager.getCache(cacheName.getCacheName())).put(key, value);
	}

	public void remove(CacheName cacheName, String key) {
		log.debug("{} remove -> cacheName [{}], key [{}]", this.getClass().getName(), cacheName, key);
		Objects.requireNonNull(caffeineCacheManager.getCache(cacheName.getCacheName())).evict(key);
	}
}
