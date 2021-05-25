package com.nocarpe.openutil.feign.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author : yaoximing
 * @date : 2020/8/21
 * @description :
 **/
@ConfigurationProperties(prefix = "feign.util", ignoreInvalidFields = true, ignoreUnknownFields = true)
public class FeignUtilClientConf {

    private int maxIdleConnections = 200;

    /**
     * The default is 5 minutes.
     */
    private long keepAliveDuration = 5;

    /** Default connection timeout (in milliseconds). The default is 10 seconds. */
    private int connectTimeout = 10 * 1000;
    private int readTimeout = 10 * 1000;
    private int writeTimeout = 10 * 1000;

    public int getMaxIdleConnections() {
        return maxIdleConnections;
    }

    public void setMaxIdleConnections(int maxIdleConnections) {
        this.maxIdleConnections = maxIdleConnections;
    }

    public long getKeepAliveDuration() {
        return keepAliveDuration;
    }

    public void setKeepAliveDuration(long keepAliveDuration) {
        this.keepAliveDuration = keepAliveDuration;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public int getWriteTimeout() {
        return writeTimeout;
    }

    public void setWriteTimeout(int writeTimeout) {
        this.writeTimeout = writeTimeout;
    }
}
