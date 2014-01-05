package org.pac4j.play;

import org.junit.Assert;
import org.junit.Test;

/**
 * This class tests the @{link StorageHelper} class
 *
 * @author Ahsan Rabbani
 * @since 1.1.2
 */
public class CacheStorageHelperTests {

    private static final String CACHE_KEY = "cacheKey";

    @Test
    public void getCacheKey_whenNullCacheKeyPrefix_returnsCacheKey() {
        CacheStorageHelper cacheStorageHelper = new CacheStorageHelper();
        Config.setCacheKeyPrefix(null);
        Assert.assertEquals(CACHE_KEY, cacheStorageHelper.getCacheKey(CACHE_KEY));
    }

    @Test
    public void getCacheKey_whenBlankCacheKeyPrefix_returnsCacheKey() {
        CacheStorageHelper cacheStorageHelper = new CacheStorageHelper();
        Config.setCacheKeyPrefix("  ");
        Assert.assertEquals(CACHE_KEY, cacheStorageHelper.getCacheKey(CACHE_KEY));
    }

    @Test
    public void getCacheKey_whenNonBlankCacheKeyPrefix_returnsCacheKeyWithPrefix() {
        CacheStorageHelper cacheStorageHelper = new CacheStorageHelper();
        String keyPrefix = "keyPrefix";
        Config.setCacheKeyPrefix(keyPrefix);
        Assert.assertEquals(keyPrefix + ":" + CACHE_KEY, cacheStorageHelper.getCacheKey(CACHE_KEY));
    }

}
