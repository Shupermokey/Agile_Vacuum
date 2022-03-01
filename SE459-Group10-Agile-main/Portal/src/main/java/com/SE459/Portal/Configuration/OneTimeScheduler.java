package com.SE459.Portal.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.Trigger;


@Configuration
@EnableScheduling
@ComponentScan
@Component
public class OneTimeScheduler {

    private static AnnotationConfigApplicationContext CONTEXT = null;

    @Autowired
    private ThreadPoolTaskScheduler scheduler;

    public static OneTimeScheduler getInstance() {
        if (!isValidBean()) {
            CONTEXT = new AnnotationConfigApplicationContext(OneTimeScheduler.class);
        }

        return CONTEXT.getBean(OneTimeScheduler.class);
    }

    @Bean
    public ThreadPoolTaskScheduler oneTimeTaskScheduler() {
        return new ThreadPoolTaskScheduler();
    }

    public void start(Runnable task, Trigger cronTrigger) throws Exception {
        scheduler.schedule(task, cronTrigger);
    }

    public void stopAll() {
        scheduler.shutdown();
        CONTEXT.close();
    }

    private static boolean isValidBean() {
        if (CONTEXT == null || !CONTEXT.isActive()) {
            return false;
        }

        try {
            CONTEXT.getBean(OneTimeScheduler.class);
        } catch (NoSuchBeanDefinitionException ex) {
            return false;
        }

        return true;
    }
}
