/*
  Copyright 2012 - 2013 Jerome Leleu

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package org.pac4j.play;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.cache.Cache;

/**
 * This class is an helper to store/retrieve objects (from cache).
 * 
 * @author Jerome Leleu
 * @since 1.1.0
 */
public final class CacheStorageHelper extends StorageHelper {
    
    private static final Logger logger = LoggerFactory.getLogger(CacheStorageHelper.class);


    /**
     * Get an object from storage.
     * 
     * @param sessionId
     * @param key
     * @return the object
     */
    @Override
    public Object get(final String sessionId, final String key) {
        if (sessionId != null) {
            return get(sessionId + Constants.SEPARATOR + key);
        }
        return null;
    }
    
    /**
     * Save an object in storage.
     * 
     * @param sessionId
     * @param key
     * @param value
     */
    @Override
    public void save(final String sessionId, final String key, final Object value) {
        if (sessionId != null) {
            save(sessionId + Constants.SEPARATOR + key, value, Config.getSessionTimeout());
        }
    }
    
    /**
     * Remove an object in storage.
     * 
     * @param sessionId
     * @param key
     */
    @Override
    public void remove(final String sessionId, final String key) {
        remove(sessionId + Constants.SEPARATOR + key);
    }
    
    /**
     * Get an object from storage.
     * 
     * @param key
     * @return the object
     */
    @Override
    public Object get(final String key) {
        return Cache.get(getCacheKey(key));
    }
    
    /**
     * Save an object in storage.
     * 
     * @param key
     * @param value
     * @param timeout
     */
    @Override
    public void save(final String key, final Object value, final int timeout) {
        Cache.set(getCacheKey(key), value, timeout);
    }
    
    /**
     * Remove an object from storage.
     * 
     * @param key
     */
    @Override
    public void remove(final String key) {
        save(key, null, 0);
    }

    String getCacheKey(final String key) {
        return (StringUtils.isNotBlank(Config.getCacheKeyPrefix()))
                ? Config.getCacheKeyPrefix() + ":" + key
                : key;
    }
}
