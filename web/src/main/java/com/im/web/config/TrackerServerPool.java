package com.im.web.config;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author viruser
 * @create 2019/1/12
 * @since 1.0.0
 */
@Component
public class TrackerServerPool {
    /***
     * 配置文件路径
     */
    private static final String FASTDFS_CONFIG_PATH = "application.properties";
    /***
     * 最大连接数
     */
    @Value("${max_storage_connection}")
    private static int maxStorageConnection ;
    /***
     * TrackerServer 对象池
     */
    private static GenericObjectPool<TrackerServer> pool ;
    /***
     * 无参构造函数
     */
    private TrackerServerPool(){}
    /***
     * 获取TrackerServer连接池
     * @return
     * @throws Exception
     */
    public static synchronized GenericObjectPool<TrackerServer> getObjectPool() throws  Exception{
        if (pool ==null){
            //加载配置文件
            ClientGlobal.initByProperties(FASTDFS_CONFIG_PATH);
            //pool配置,设置最大值和最小值
            GenericObjectPoolConfig config = new GenericObjectPoolConfig();
            config.setMinIdle(2);
            if (maxStorageConnection>0){
                config.setMaxIdle(maxStorageConnection);
            }

            pool = new GenericObjectPool<>(new TrackerServerFactory(),config) ;
        }
        return pool ;
    }
    /***
     * 获取TrackerServer
     * @return
     * @throws Exception
     */
    public static TrackerServer borrowObject()throws Exception{
        TrackerServer trackerServer = null;
        trackerServer = getObjectPool().borrowObject() ;
        return  trackerServer ;
    }
    /***
     * 回收 TrackerServer
     * @param server
     * @throws Exception
     */
    public static void recycleObject(TrackerServer server) throws Exception{
        getObjectPool().returnObject(server);
    }

}
