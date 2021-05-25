package com.nocarpe.openutil.feign;

import static java.util.concurrent.TimeUnit.SECONDS;

import com.nocarpe.openutil.feign.config.FeignUtilClientConf;
import feign.Client;
import feign.Feign;
import feign.Request.Options;
import feign.Retryer;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author : yaoximing
 * @date : 2020/8/21
 * @description :
 **/
public class FeignUtilClientFactoryBean implements FactoryBean<Object>, ApplicationContextAware {

    private Class<?> proxyInterface;

    private String url;

    private ApplicationContext applicationContext;


    @Override
    public Object getObject() throws Exception {
        return getTarget();
    }

    <T> T getTarget() {
        FeignUtilClientConf conf = applicationContext.getBean(FeignUtilClientConf.class);
        Client client;
        try {
            client = applicationContext.getBean("client", Client.class);
        } catch (NoSuchBeanDefinitionException e) {
            throw new NullPointerException("Without one of [okhttp3, Http2Client] client.");
        }
        return (T) Feign.builder()
            .client(client)
            .encoder(new GsonEncoder())
            .decoder(new GsonDecoder())
            .retryer(new Retryer.Default(100, SECONDS.toMillis(1), 0))
            .options(new Options(conf.getConnectTimeout(), conf.getReadTimeout(), true))
            .target(proxyInterface, url);
    }


    @Override
    public Class<?> getObjectType() {
        return proxyInterface;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public Class<?> getProxyInterface() {
        return proxyInterface;
    }

    public void setProxyInterface(Class<?> proxyInterface) {
        this.proxyInterface = proxyInterface;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
