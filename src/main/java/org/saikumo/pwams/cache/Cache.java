package org.saikumo.pwams.cache;


import org.saikumo.pwams.constant.CacheName;

/**
 * Cache接口
 */
public interface Cache {

    <T> T get(CacheName cacheName, String key, Class<T> clazz);

    void put(CacheName cacheName, String key, Object value);

    void remove(CacheName cacheName, String key);
}
