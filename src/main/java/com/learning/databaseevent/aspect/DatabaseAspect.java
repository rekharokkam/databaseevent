package com.learning.databaseevent.aspect;

import com.learning.databaseevent.events.Event;
import com.learning.databaseevent.events.EventPublisher;
import com.learning.databaseevent.events.EventSubscriber;
import com.learning.databaseevent.repository.entity.OrderEntity;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Aspect
@Service
@Slf4j
public class DatabaseAspect implements EventPublisher {

    private List<EventSubscriber> eventSubscribers = new ArrayList<>();

    @Override
    public void addSubscriber(EventSubscriber eventSubscriber) {
        eventSubscribers.add(eventSubscriber);
    }

    @Override
    public void removeSubscriber(EventSubscriber eventSubscriber) {
        eventSubscribers.remove(eventSubscriber);
    }

    @Override
    public void publishEvent(Event orderEvent) {
        for (EventSubscriber eachSubscriber: eventSubscribers) {
            eachSubscriber.processEvent(orderEvent);
        }
    }

    //only after successfully executing
    @AfterReturning("execution(* com.learning.databaseevent.repository.OrderRepo.save(..))")
    public void printThisNow (JoinPoint joinPoint){
        log.info("total number of parameters to this method : {}", joinPoint.getArgs().length);
        log.info("parameter argument type : {}", joinPoint.getArgs()[0].getClass().getName());

        OrderEntity orderEntity = (OrderEntity) joinPoint.getArgs()[0];
        Event<OrderEntity> orderSavedEvent = new Event<>(orderEntity);
        publishEvent (orderSavedEvent);
    }
}
