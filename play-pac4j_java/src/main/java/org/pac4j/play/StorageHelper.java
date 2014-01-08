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

import org.pac4j.core.profile.CommonProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.mvc.Http;
import play.mvc.Http.Session;

/**
 * This class is an helper to store/retrieve objects (from cache).
 * 
 * @author Jerome Leleu
 * @since 1.1.0
 */
public abstract class StorageHelper {
    
    private static final Logger logger = LoggerFactory.getLogger(StorageHelper.class);

    private static volatile StorageHelper instance;

    public static StorageHelper getInstance() {
        if (instance == null) {
            synchronized (StorageHelper.class) {
                if (instance == null) {
                    instance = new CacheStorageHelper();
                }
            }
        }
        return instance;
    }

    public static void setInstance(StorageHelper _instance) {
        synchronized (StorageHelper.class) {
            instance = _instance;
        }
    }

    /**
     * Get a session identifier and generates it if no session exists.
     * 
     * @param session
     * @return the session identifier
     */
    public String getOrCreationSessionId(final Session session) {
        // get current sessionId
        String sessionId = session.get(Constants.SESSION_ID);
        logger.debug("retrieved sessionId : {}", sessionId);
        // if null, generate a new one
        if (sessionId == null) {
            // generate id for session
            sessionId = generateSessionId();
            logger.debug("generated sessionId : {}", sessionId);
            // and save it to session
            session.put(Constants.SESSION_ID, sessionId);
        }
        return sessionId;
    }
    
    /**
     * Generate a session identifier.
     * 
     * @return a session identifier
     */
    public String generateSessionId() {
        return java.util.UUID.randomUUID().toString();
    }
    
    /**
     * Get the profile from storage.
     * 
     * @param sessionId
     * @return the user profile
     */
    public CommonProfile getProfile(final Http.Request request, final Session session, final String sessionId) {
        if (sessionId != null) {
            return (CommonProfile) get(sessionId);
        }
        return null;
    }
    
    /**
     * Save a user profile in storage.
     * 
     * @param sessionId
     * @param profile
     */
    public void saveProfile(final Http.Request request, final Http.Response response, final Session session, final String sessionId, final CommonProfile profile) {
        if (sessionId != null) {
            save(sessionId, profile, Config.getProfileTimeout());
        }
    }
    
    /**
     * Remove a user profile from storage.
     * 
     * @param sessionId
     */
    public void removeProfile(final String sessionId) {
        if (sessionId != null) {
            remove(sessionId);
        }
    }
    
    /**
     * Get a requested url from storage.
     * 
     * @param sessionId
     * @param clientName
     * @return the requested url
     */
    public String getRequestedUrl(final String sessionId, final String clientName) {
        return (String) get(sessionId, clientName + Constants.SEPARATOR + Constants.REQUESTED_URL);
    }
    
    /**
     * Save a requested url to storage.
     * 
     * @param sessionId
     * @param clientName
     * @param requestedUrl
     */
    public void saveRequestedUrl(final String sessionId, final String clientName, final String requestedUrl) {
        save(sessionId, clientName + Constants.SEPARATOR + Constants.REQUESTED_URL, requestedUrl);
    }
    
    /**
     * Get an object from storage.
     * 
     * @param sessionId
     * @param key
     * @return the object
     */
    public abstract Object get(final String sessionId, final String key);

    /**
     * Save an object in storage.
     * 
     * @param sessionId
     * @param key
     * @param value
     */
    public abstract void save(final String sessionId, final String key, final Object value);

    /**
     * Remove an object in storage.
     * 
     * @param sessionId
     * @param key
     */
    public abstract void remove(final String sessionId, final String key);

    /**
     * Get an object from storage.
     * 
     * @param key
     * @return the object
     */
    public abstract Object get(final String key);

    /**
     * Save an object in storage.
     * 
     * @param key
     * @param value
     * @param timeout
     */
    public abstract void save(final String key, final Object value, final int timeout);

    /**
     * Remove an object from storage.
     * 
     * @param key
     */
    public abstract void remove(final String key);

}
