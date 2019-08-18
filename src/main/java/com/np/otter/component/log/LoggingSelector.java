package com.np.otter.component.log;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author np
 * @date 2019/8/18
 */
public class LoggingSelector implements ImportSelector, BeanFactoryAware {

    private BeanFactory beanFactory;

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public String[] selectImports(AnnotationMetadata annotationMetadata) {

        // TODO
        return new String[] {ProxyLogConfiguration.class.getName()};
    }
}
