package com.py.util;

import java.util.HashMap;
import java.util.Map;

public class MyCacheUtil {

    private static MyCacheUtil myCacheUtil;
    private static Map<String,Object> cacheMap;

    private MyCacheUtil(){
        cacheMap = new HashMap<String, Object>();
    }

    public static MyCacheUtil getInstance(){
        if (myCacheUtil == null){
            myCacheUtil = new MyCacheUtil();
        }
        return myCacheUtil;
    }

    /**
     * 添加缓存
     * @param key
     * @param obj
     */
    public void addCacheData(String key,Object obj){
        cacheMap.put(key,obj);
    }

    /**
     * 取出缓存
     * @param key
     * @return
     */
    public Object getCacheData(String key){
        return cacheMap.get(key);
    }

    /**
     * 清楚缓存
     * @param key
     */
    public void removeCacheData(String key){
        cacheMap.remove(key);
    }

}
