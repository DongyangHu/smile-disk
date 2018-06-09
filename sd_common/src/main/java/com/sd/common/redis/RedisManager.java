package com.sd.common.redis;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;


import redis.clients.jedis.Jedis;

public class RedisManager {
    
    private static Log logger = LogFactory.getLog(RedisManager.class);
    private JedisConnectionFactory jedisConnectionFactory = null;
    private Jedis jedis = null;
    
    public JedisConnectionFactory getJedisConnectionFactory() {
        return jedisConnectionFactory;
    }

    public void setJedisConnectionFactory(JedisConnectionFactory jedisConnectionFactory) {
        this.jedisConnectionFactory = jedisConnectionFactory;
    }

    
    
    /**
     * set redis
     * @param namespace
     * @param key
     * @param value
     */
    public void put(String namespace, Serializable key, Serializable value) {
        this.jedis = jedisConnectionFactory.getShardInfo().createResource();
        this.jedis.set((String)namespace + ":" + (String)key, (String)value);
    }
    
    /**
     * 获取单个key的value
     * @param key
     * @return
     */
    public String get(Serializable key) {
        this.jedis = jedisConnectionFactory.getShardInfo().createResource();
        return this.jedis.get((String) key);
    }
    
    public Set<Entry<String, String>> get(String[] keys) {
        HashMap<String, String> map = new HashMap<String, String>();
        this.jedis = jedisConnectionFactory.getShardInfo().createResource();
        for (String key : keys) {
            String value = get((Serializable)key);
            map.put(key, value);
        }
        Set<Entry<String, String>> resultSet = map.entrySet();
        return resultSet;
    }
    
    /**
     * 获取序列
     * @param key
     * @return
     */
    public long getSequence (Serializable key) {
        this.jedis = jedisConnectionFactory.getShardInfo().createResource();
        return jedis.incr((String) key);
    }
    
    /**
     * 获取命名空间下的所有key value
     * @param namespace
     * @return
     */
    public Set<Entry<String, String>> getSetByNamespace(Serializable namespace){
        HashMap<String, String> map = new HashMap<String, String>();
        this.jedis = jedisConnectionFactory.getShardInfo().createResource();
        logger.info("redis get " + namespace + "...");
        Set<String> keys = jedis.keys(namespace + "*");
        Iterator<String> iterator = keys.iterator();
        while(iterator.hasNext()) {
            String key = iterator.next();
            String value = get((Serializable)key);
            map.put(key, value);
        }
        Set<Entry<String, String>> resultSet = map.entrySet();
        return resultSet;
    }
    
}
