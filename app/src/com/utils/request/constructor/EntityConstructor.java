package com.utils.request.constructor;

import java.util.Map;

public interface EntityConstructor<T> {
    T construct(Map<String, Object> values);
}
