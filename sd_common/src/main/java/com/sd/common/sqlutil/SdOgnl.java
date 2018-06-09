package com.sd.common.sqlutil;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

public class SdOgnl {

    public static boolean isEmpty(Object objectect) throws IllegalArgumentException {
        if (objectect == null) {
            return true;
        }
        if (objectect instanceof String) {
            if (((String) objectect).length() == 0) {
                return true;
            }
        } else if (objectect instanceof Collection) {
            if (((Collection) objectect).isEmpty()) {
                return true;
            }
        } else if (objectect.getClass().isArray()) {
            if (Array.getLength(objectect) == 0) {
                return true;
            }
        } else if (objectect instanceof Map) {
            if (((Map) objectect).isEmpty()) {
                return true;
            }
        } else {
            return false;
        }
        
        return false;
    }
    
    
    public static boolean isNotEmpty(Object object) {
        return !isEmpty(object);
    }

}
