package com.github.jbharter.delegates;

import com.github.jbharter.delegates.services.BaseService;

public interface BaseDelegate<T extends BaseService> {
    T getService();
}
