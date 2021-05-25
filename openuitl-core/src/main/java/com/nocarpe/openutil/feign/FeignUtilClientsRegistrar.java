package com.nocarpe.openutil.feign;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.Assert;

/**
 * @author : yaoximing
 * @date : 2020/8/21
 * @description :
 **/
public class FeignUtilClientsRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, EnvironmentAware {

    private ResourceLoader resourceLoader;

    private Environment environment;


    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }


    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes attributes = AnnotationAttributes
            .fromMap(importingClassMetadata.getAnnotationAttributes(EnableFeignUtilClients.class.getName()));
        Set<String> basePackages = new HashSet<>();
        basePackages.addAll(Arrays.asList(attributes.getStringArray("basePackages")));
        ClassPathScanningCandidateComponentProvider scanner = getScanner();
        AnnotationTypeFilter annotationTypeFilter = new AnnotationTypeFilter(
            FeignUtilClient.class);

        Set<BeanDefinition> beanDefinitions = new HashSet<>();
        scanner.setResourceLoader(this.resourceLoader);
        scanner.addIncludeFilter(annotationTypeFilter);

        for (String basePackage : attributes.getStringArray("basePackages")) {
            beanDefinitions.addAll(scanner.findCandidateComponents(basePackage));
        }
        for (BeanDefinition beanDefinition : beanDefinitions) {
            if (beanDefinition instanceof AnnotatedBeanDefinition) {
                //verify annotaed class is an interface
                AnnotatedBeanDefinition annotatedBeanDefinition = (AnnotatedBeanDefinition) beanDefinition;
                AnnotationMetadata annotationMetadata = annotatedBeanDefinition.getMetadata();
                Assert.isTrue(annotationMetadata.isInterface(),
                    "@FeignUtilClient can only be specified on an interface");
                Map<String, Object> feignUtilClientMap = annotationMetadata
                    .getAnnotationAttributes(FeignUtilClient.class.getCanonicalName());

                registerFeignUtilClient(beanDefinition, feignUtilClientMap, registry);
            }
        }

    }

    public void registerFeignUtilClient(BeanDefinition beanDefinition, Map<String, Object> feignUtilClientMap,
        BeanDefinitionRegistry registry) {
        //代理
        BeanDefinitionBuilder definition = BeanDefinitionBuilder
            .genericBeanDefinition(FeignUtilClientFactoryBean.class);
        definition.addPropertyValue("url", feignUtilClientMap.get("url"));
        definition.addPropertyValue("proxyInterface", beanDefinition.getBeanClassName());
        BeanDefinitionHolder holder = new BeanDefinitionHolder(definition.getBeanDefinition(),
            beanDefinition.getBeanClassName(),
            null);
        BeanDefinitionReaderUtils.registerBeanDefinition(holder, registry);

    }


    protected ClassPathScanningCandidateComponentProvider getScanner() {
        return new ClassPathScanningCandidateComponentProvider(false, this.environment) {
            @Override
            protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
                boolean isCandidate = false;
                if (beanDefinition.getMetadata().isIndependent()) {
                    if (!beanDefinition.getMetadata().isAnnotation()) {
                        isCandidate = true;
                    }
                }
                return isCandidate;
            }
        };

    }

}
