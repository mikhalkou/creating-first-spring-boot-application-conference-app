package com.mikhalkou.learn.springboot1.conferencespringboot.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public interface ObjectHelper {
    static Set<String> getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }

        return emptyNames;
    }

    static Set<String> getNullPropertyNames (Object source, String ... fieldsToIgnore) {

        Set<String> emptyNames = getNullPropertyNames(source);
        Arrays.stream(fieldsToIgnore).forEach(emptyNames::remove);

        return emptyNames;
    }
}
