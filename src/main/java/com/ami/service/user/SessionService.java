package com.ami.service.user;

import com.ami.entities.Session;

/**
 * @author: Amit Khandelwal
 * Date: 24/09/16
 */

public interface SessionService {
    String save(Session session);
    String get(String key);
    boolean invalidate(String key);

}
