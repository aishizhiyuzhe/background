package com.ming.common.security.cache;

import com.ming.common.utils.JedisUtils;
import com.ming.common.utils.ObjectUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import redis.clients.jedis.Jedis;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author: Ming
 * @Description:
 * @Date: Created in 2021/12/19
 * @Modified By:
 */
public class JedisCacheManager implements CacheManager {
    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return new JedisCache<>(s);
    }
    /**
     * 自定义授权缓存管理类
     * @author ThinkGem
     * @version 2014-7-20
     */
    public class JedisCache<K, V> implements Cache<K, V> {
        private String key;

        public JedisCache(String key){
            this.key=key;
        }

        @Override
        public V get(K k) throws CacheException {
            Jedis jedis= JedisUtils.getResource();
            V value= (V) ObjectUtils.unserialize(jedis.hget(JedisUtils.getBytesKey(key),JedisUtils.getBytesKey(k)));
            JedisUtils.clone(jedis);
            return value;
        }

        @Override
        public V put(K k, V v) throws CacheException {
            Jedis jedis= JedisUtils.getResource();
            jedis.hset(JedisUtils.getBytesKey(key),JedisUtils.getBytesKey(k),JedisUtils.getBytesKey(v));
            JedisUtils.clone(jedis);
            return v;
        }

        @Override
        public V remove(K k) throws CacheException {
            Jedis jedis= JedisUtils.getResource();
            V value= (V) ObjectUtils.unserialize(jedis.hget(JedisUtils.getBytesKey(key),JedisUtils.getBytesKey(k)));
            jedis.hget(JedisUtils.getBytesKey(key),JedisUtils.getBytesKey(k));
            JedisUtils.clone(jedis);
            return null;
        }

        @Override
        public void clear() throws CacheException {
            Jedis jedis= JedisUtils.getResource();
            jedis.hdel(JedisUtils.getBytesKey(key));
            JedisUtils.clone(jedis);
        }

        @Override
        public int size() {
            Jedis jedis= JedisUtils.getResource();
            int size= Math.toIntExact(jedis.hlen(JedisUtils.getBytesKey(key)));
            JedisUtils.clone(jedis);
            return size;
        }

        @Override
        public Set<K> keys() {
            Jedis jedis= JedisUtils.getResource();
            Set<byte[]> set=jedis.hkeys(JedisUtils.getBytesKey(key));
            Set<K> keys=new HashSet<>();
            for(byte[] key : set){
                Object obj = (K)ObjectUtils.unserialize(key);
                if (obj != null){
                    keys.add((K) obj);
                }
            }
            JedisUtils.clone(jedis);
            return keys;
        }

        @Override
        public Collection<V> values() {
            Jedis jedis= JedisUtils.getResource();
            List<byte[]> hvals=jedis.hvals(JedisUtils.getBytesKey(key));
            Collection<V> value=new HashSet<>();
            for (byte[] i:hvals){
                value.add((V) ObjectUtils.unserialize(i));
            }
            JedisUtils.clone(jedis);
            return null;
        }
    }


}
