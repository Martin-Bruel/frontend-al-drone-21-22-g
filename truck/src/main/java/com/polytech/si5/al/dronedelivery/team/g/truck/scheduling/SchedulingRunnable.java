package com.polytech.si5.al.dronedelivery.team.g.truck.scheduling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ReflectionUtils;

import javax.persistence.Embeddable;
import java.lang.reflect.Method;
import java.util.Objects;

@Embeddable
public class SchedulingRunnable implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(SchedulingRunnable.class);

    private ApplicationContext applicationContext;

    private String beanName;

    private String methodName;

    private Class[] paramsTypes;

    private Object[] params;

    public SchedulingRunnable(ApplicationContext context,String beanName, String methodName) {
        this(context,beanName, methodName, null,null);
    }

    public SchedulingRunnable(ApplicationContext context,String beanName, String methodName, Class[] paramsTypes,Object[] params) {
        this.applicationContext=context;
        this.beanName = beanName;
        this.methodName = methodName;
        this.paramsTypes=paramsTypes;
        this.params = params;
    }

    public SchedulingRunnable() {

    }

    @Override
    public void run() {
        //logger.info("Timed Task Start Execution - bean: {}，Method:{}，Parameters:{}", beanName, methodName, params);
        long startTime = System.currentTimeMillis();

        try {
            Object target = applicationContext.getBean(beanName);

            Method method = null;
            if (params!=null && paramsTypes!=null&&params.length!=0) {
                method = target.getClass().getDeclaredMethod(methodName, paramsTypes);
            } else {
                method = target.getClass().getDeclaredMethod(methodName);
            }

            ReflectionUtils.makeAccessible(method);
            if (params!=null && paramsTypes!=null&&params.length!=0) {
                method.invoke(target, params);
            } else {
                method.invoke(target);
            }
        } catch (Exception ex) {
            logger.error(String.format("Timed Task Execution Exception - bean: %s，Method:%s，Parameters:%s ", beanName, methodName, params), ex);
        }

        long times = System.currentTimeMillis() - startTime;
        //logger.info("Timed Task Execution End - bean: {}，Method:{}，Parameters:{}，Time consuming:{} Millisecond", beanName, methodName, params, times);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SchedulingRunnable that = (SchedulingRunnable) o;
        if (params == null) {
            return beanName.equals(that.beanName) &&
                    methodName.equals(that.methodName) &&
                    that.params == null;
        }

        return beanName.equals(that.beanName) &&
                methodName.equals(that.methodName) &&
                params.equals(that.params);
    }

    @Override
    public int hashCode() {
        if (params == null) {
            return Objects.hash(beanName, methodName);
        }

        return Objects.hash(beanName, methodName, params);
    }
}
