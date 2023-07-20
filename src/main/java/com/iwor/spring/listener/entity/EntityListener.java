package com.iwor.spring.listener.entity;

import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class EntityListener {

    @EventListener(condition = "#root.args[0].accessType.name() == 'READ'")
    @Order(10)
    public void acceptEntityRead(EntityEvent event) {
        System.out.println("Entity: " + event);
    }
}
