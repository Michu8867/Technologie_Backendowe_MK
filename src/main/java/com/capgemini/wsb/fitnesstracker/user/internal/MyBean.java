package com.capgemini.wsb.fitnesstracker.user.internal;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * A Spring bean demonstrating the lifecycle callbacks: instantiation, initialization, and destruction.
 */
@Service
@Profile("BeanCycle")
public class MyBean {

    /**
     * Constructs a new instance of {@code MyBean}.
     * This constructor prints "Instantiation" to indicate the creation of the bean.
     */
    public MyBean() {
        System.out.println("Instantiation");
    }

    /**
     * Initializes the bean.
     * This method is annotated with {@link PostConstruct} and prints "Initializing.." to indicate the initialization phase.
     */
    @PostConstruct
    public void init() {
        System.out.println("Initializing..");
    }

    /**
     * Destroys the bean.
     * This method is annotated with {@link PreDestroy} and prints "Destroying.." to indicate the destruction phase.
     */
    @PreDestroy
    public void destroy() {
        System.out.println("Destroying..");
    }
}
