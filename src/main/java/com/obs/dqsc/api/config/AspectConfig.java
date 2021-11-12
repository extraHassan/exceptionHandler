package com.obs.dqsc.api.config;

import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AspectConfig {
    /**
     * A join point is in the web layer if the method is defined
     * in a type in the com.obs.dqsc.api.controller package or any sub-package
     * under that.
     */
    @Pointcut("within(com.obs.dqsc.api.controller..*)")
    public void initControllerLayer() {
    }

    /**
     * A join point is in the service layer if the method is defined
     * in a type in the com.obs.dqsc.api.service package or any sub-package
     * under that.
     */
    @Pointcut("within(com.obs.dqsc.api.service..*)")
    public void inServiceLayer() {
    }

    /**
     * A join point is in the data access layer if the method is defined
     * in a type in the com.obs.dqsc.api.repository package or any sub-package
     * under that.
     */
    @Pointcut("within(com.obs.dqsc.api.repository..*)")
    public void initRepositoryLayer() {
    }

    /**
     * A business service is the execution of any method defined on a service
     * interface. This definition assumes that interfaces are placed in the
     * "service" package, and that implementation types are in sub-packages.
     * <p>
     * If you group service interfaces by functional area (for example,
     * in packages com.obs.dqsc.api.repository or com.obs.dqsc.api.service) then
     * the pointcut expression "execution(* com.obs.dqsc.api.service.*.*(..))"
     * could be used instead.
     * <p>
     * Alternatively, you can write the expression using the 'bean'
     * PCD, like so "bean(*Service)". (This assumes that you have
     * named your Spring service beans in a consistent fashion.)
     */
    @Pointcut("execution(* com.obs.dqsc.api.service.*.*(..))")
    public void businessServiceOperations() {
    }

    /**
     * A data access operation is the execution of any method defined on a
     * dao interface. This definition assumes that interfaces are placed in the
     * "dao" package, and that implementation types are in sub-packages.
     */
    @Pointcut("execution(* com.obs.dqsc.api.repository.*.*(..))")
    public void repositoryOperations() {
    }

    @Pointcut("execution(* com.obs.dqsc.api.controller.*.*(..))")
    public void controllerOperations() {
    }

    @Pointcut("com.obs.dqsc.api.config.AspectConfig.initRepositoryLayer()" +
            "|| com.obs.dqsc.api.config.AspectConfig.inServiceLayer() " +
            "|| com.obs.dqsc.api.config.AspectConfig.initControllerLayer()" +
            "|| com.obs.dqsc.api.config.AspectConfig.repositoryOperations()" +
            "|| com.obs.dqsc.api.config.AspectConfig.businessServiceOperations()" +
            "|| com.obs.dqsc.api.config.AspectConfig.controllerOperations()")
    public void allLayers(){

    }
}
