package com.nocarpe.openutil.feign.config;


import feign.Client;
import java.util.concurrent.TimeUnit;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : yaoximing
 * @date : 2020/8/21
 * @description :
 **/
@EnableConfigurationProperties(FeignUtilClientConf.class)
@Configuration
public class FeignUtilClientAutoConfiguration {


    private FeignUtilClientConf feignUtilClientConf;


    public FeignUtilClientAutoConfiguration(FeignUtilClientConf feignUtilClientConf) {
        this.feignUtilClientConf = feignUtilClientConf;
    }

    @Bean
    public ConnectionPool connectionPool() {
        return new ConnectionPool(feignUtilClientConf.getMaxIdleConnections(),
            feignUtilClientConf.getKeepAliveDuration(), TimeUnit.MINUTES);
    }


    @Bean(value = "client")
    @ConditionalOnExpression("'okhttp3'.equals('${feign.httpclient:okhttp3}')")
    @ConditionalOnClass(Client.class)
    public Client okHttpClient(ConnectionPool connectionPool) {
        OkHttpClient delegate = new OkHttpClient().newBuilder()
            .connectionPool(connectionPool)
            .connectTimeout(feignUtilClientConf.getConnectTimeout(), TimeUnit.MILLISECONDS)
            .readTimeout(feignUtilClientConf.getReadTimeout(), TimeUnit.MILLISECONDS)
            .writeTimeout(feignUtilClientConf.getWriteTimeout(), TimeUnit.MILLISECONDS)
            .build();
        return new feign.okhttp.OkHttpClient(delegate);
    }


}
