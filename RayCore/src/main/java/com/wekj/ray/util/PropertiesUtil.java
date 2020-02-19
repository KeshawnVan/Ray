package com.wekj.ray.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Properties;

/**
 * @author liuna
 */
public final class PropertiesUtil {

    private PropertiesUtil() {
    }

    public static Properties loadProperties(String filePath) {
        try (InputStream inputStream = getClassLoader().getResourceAsStream(filePath);) {
            if (inputStream == null) {
                throw new FileNotFoundException(filePath + "file not found");
            }
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static ClassLoader getClassLoader() {
        ClassLoader classLoader = System.getSecurityManager() == null
                ? Thread.currentThread().getContextClassLoader()
                : AccessController.doPrivileged((PrivilegedAction<ClassLoader>) () -> Thread.currentThread().getContextClassLoader());

        if (classLoader == null) {
            classLoader = PropertiesUtil.class.getClassLoader();
        }

        return classLoader;
    }

}
