package com.datastax.pulsar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

public class Configuration {

    private static final Logger LOG = LoggerFactory.getLogger(Configuration.class);

    private static Configuration _instance;

    private final String serviceUrl;
    private final String tenantName;
    private final String namespace;
    private final String topicName;
    private final String authenticationToken;

    private int waitPeriod = 1000;

    public static synchronized Configuration getInstance() {
        if (null == _instance) {
            _instance = new Configuration();
        }
        return _instance;
    }

    private Configuration() {
        try {
            Properties properties = new Properties();
            properties.load(Thread
                    .currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream("application.properties"));

            this.serviceUrl = properties.getProperty("service_url");
            if (null == serviceUrl) {
                throw new IllegalArgumentException("Cannot read serviceUrl in conf file");
            }

            this.namespace  = properties.getProperty("namespace");
            if (null == namespace) {
                throw new IllegalArgumentException("Cannot read namespace in conf file");
            }

            this.tenantName  = properties.getProperty("tenant_name");
            if (null == tenantName) {
                throw new IllegalArgumentException("Cannot read tenant_name in conf file");
            }

            this.topicName  = properties.getProperty("topic_name");
            if (null == topicName) {
                throw new IllegalArgumentException("Cannot read topic_name in conf file");
            }

            this.authenticationToken  = properties.getProperty("authentication_token");
            if (null == authenticationToken) {
                throw new IllegalArgumentException("Cannot read authentication_token in conf file");
            }

            String newPeriod = properties.getProperty("demo.wait_between_message");
            if (null != newPeriod) {
                waitPeriod = Integer.parseInt(newPeriod);
            }

            LOG.info("Configuration has been loaded successfully");
        } catch (IOException ioe) {
            throw new IllegalStateException(ioe);
        }
    }

    public static void main(String[] args) {
        new Configuration();
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public String getTenantName() {
        return tenantName;
    }

    public String getNamespace() {
        return namespace;
    }

    public String getTopicName() {
        return topicName;
    }

    public String getAuthenticationToken() {
        return authenticationToken;
    }

    public int getWaitPeriod() {
        return waitPeriod;
    }

}