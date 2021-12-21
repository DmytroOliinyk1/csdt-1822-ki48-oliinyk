package com.lpnu.virtual.library.util;

import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.Collections;

@UtilityClass
public class ValuesUtils {

    public static Boolean hasElements(Collection<?> collection) {
        return collection != null && !collection.isEmpty();
    }

    public static Object emptyValue() {
        return null;
    }

    public static Boolean containsAny(Collection<?> a, Collection<?> b) {
        return hasElements(a) && hasElements(b) ? !Collections.disjoint(a, b) : Boolean.FALSE;
    }
}
