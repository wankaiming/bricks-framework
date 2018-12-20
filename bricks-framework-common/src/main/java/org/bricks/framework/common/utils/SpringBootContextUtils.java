package org.bricks.framework.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringBootContextUtils implements ApplicationContextAware{
    // 上下文对象实例
    private static ApplicationContext applicationContext;
 
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringBootContextUtils.applicationContext = applicationContext;
    }
 
    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }
 
    public static <T> T getBean(Class<T> clazz){
        return applicationContext.getBean(clazz);
    }
 
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name){
        return (T)applicationContext.getBean(name);
    }
 
    public static <T> T getBean(String name, Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }
}