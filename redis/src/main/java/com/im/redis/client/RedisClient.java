package com.im.redis.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * @author viruser
 * @create 2018/12/19
 * @since 1.0.0
 */
@Component
public class RedisClient {
    @Autowired
    private JedisPool jedisPool;

    public Jedis getJedis() {
        Jedis jedis = jedisPool.getResource();
        return jedis;
    }

    /**
     * <p>
     * 通过key获取储存在redis中的value
     * </p>
     * <p>
     * 并释放连接
     * </p>
     *
     * @param key
     * @return 成功返回value 失败返回null
     */
    public String get(String key) {
        Jedis jedis = null;
        String value = null;
        try {
            jedis = jedisPool.getResource();
            value = jedis.get(key);
        } catch (Exception e) {
            return null;
        } finally {
            returnResource(jedisPool, jedis);
        }
        return value;
    }

    /**
     * <p>
     * 通过key插入该list集合里面,存到list的最后一个
     * </p>
     * <p>
     * 并释放连接
     * </p>
     *
     * @param key
     * @return 成功返回插入value的个数 失败返回0
     */
    public Long rpush(String key, String... value) {
        Jedis jedis = null;
        Long num;
        try {
            jedis = jedisPool.getResource();
            num = jedis.rpush(key, value);
        } catch (Exception e) {
            return 0L;
        } finally {
            returnResource(jedisPool, jedis);
        }
        return num;
    }

    /**
     * <p>
     * 通过key获取该list集合里面,到list的第一个
     * </p>
     * <p>
     * 并释放连接
     * </p>
     *
     * @param key
     * @return 成功返回插入value 失败返回null
     */
    public String lpop(String key) {
        Jedis jedis = null;
        String value;
        try {
            jedis = jedisPool.getResource();
            value = jedis.lpop(key);
        } catch (Exception e) {
            return null;
        } finally {
            returnResource(jedisPool, jedis);
        }
        return value;
    }
    /**
     * <p>
     * 通过key  del在redis中的value
     * </p>
     * <p>
     * 并释放连接
     * </p>
     *
     * @param key
     * @return 成功返回value 失败返回0
     */
    public Long del(String key) {
        Jedis jedis = null;
        Long value = null;
        try {
            jedis = jedisPool.getResource();
            value = jedis.del(key);
        } catch (Exception e) {
            return 0L;
        } finally {
            returnResource(jedisPool, jedis);
        }
        return value;
    }

    /**
     * <p>
     * 通过key获取储存在redis中的value
     * </p>
     * <p>
     * 并释放连接
     * </p>
     *
     * @param key
     * @return 成功返回value 失败返回null
     */
    public byte[] get(byte[] key) {
        Jedis jedis = null;
        byte[] value = null;
        try {
            jedis = jedisPool.getResource();
            value = jedis.get(key);
        } catch (Exception e) {

        } finally {
            returnResource(jedisPool, jedis);
        }
        return value;
    }

    /**
     * <p>
     * 向redis存入key和value,并释放连接资源
     * </p>
     * <p>
     * 如果key已经存在 则覆盖
     * </p>
     *
     * @param key
     * @param value
     * @return 成功 返回OK 失败返回 0
     */
    public String set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.set(key, value);
        } catch (Exception e) {

            return "0";
        } finally {
            returnResource(jedisPool, jedis);
        }
    }

    /**
     * <p>
     * 向redis存入key和value,并释放连接资源
     * </p>
     * <p>
     * 如果key已经存在 则覆盖
     * </p>
     *
     * @param key
     * @param value
     * @return 成功 返回OK 失败返回 0
     */
    public String set(byte[] key, byte[] value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.set(key, value);
        } catch (Exception e) {

            return "0";
        } finally {
            returnResource(jedisPool, jedis);
        }
    }

    /**
     * <p>
     * 删除指定的key,也可以传入一个包含key的数组
     * </p>
     *
     * @param keys 一个key 也可以使 string 数组
     * @return 返回删除成功的个数
     */
    public Long del(String... keys) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.del(keys);
        } catch (Exception e) {

            return 0L;
        } finally {
            returnResource(jedisPool, jedis);
        }
    }

    /**
     * <p>
     * 删除指定的key,也可以传入一个包含key的数组
     * </p>
     *
     * @param keys 一个key 也可以使 string 数组
     * @return 返回删除成功的个数
     */
    public Long del(byte[]... keys) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.del(keys);
        } catch (Exception e) {

            return 0L;
        } finally {
            returnResource(jedisPool, jedis);
        }
    }

    /**
     * <p>
     * 判断key是否存在
     * </p>
     *
     * @param key
     * @return true OR false
     */
    public Boolean exists(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.exists(key);
        } catch (Exception e) {

            return false;
        } finally {
            returnResource(jedisPool, jedis);
        }
    }

    /**
     * <p>
     * 为给定 key 设置生存时间，当 key 过期时(生存时间为 0 )，它会被自动删除。
     * </p>
     *
     * @param key
     * @param value 过期时间，单位：秒
     * @return 成功返回1 如果存在 和 发生异常 返回 0
     */
    public Long expire(String key, int value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.expire(key, value);
        } catch (Exception e) {
            return 0L;
        } finally {
            returnResource(jedisPool, jedis);
        }
    }

    /**
     * <p>
     * 以秒为单位，返回给定 key 的剩余生存时间
     * </p>
     *
     * @param key
     * @return 当 key 不存在时，返回 -2 。当 key 存在但没有设置剩余生存时间时，返回 -1 。否则，以秒为单位，返回 key
     * 的剩余生存时间。 发生异常 返回 0
     */
    public Long ttl(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.ttl(key);
        } catch (Exception e) {

            return 0L;
        } finally {
            returnResource(jedisPool, jedis);
        }
    }

    /**
     * <p>
     * 设置key value并制定这个键值的有效期
     * </p>
     *
     * @param key
     * @param value
     * @param seconds 单位:秒
     * @return 成功返回OK 失败和异常返回null
     */
    public String setex(String key, String value, int seconds) {
        Jedis jedis = null;
        String res = null;
        try {
            jedis = jedisPool.getResource();
            res = jedis.setex(key, seconds, value);
        } catch (Exception e) {

        } finally {
            returnResource(jedisPool, jedis);
        }
        return res;
    }

    /**
     * 返还到连接池
     *
     * @param jedisPool
     * @param jedis
     */
    public static void returnResource(JedisPool jedisPool, Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnResource(jedis);
        }
    }
}
