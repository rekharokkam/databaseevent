package com.learning.databaseevent.events;

public interface EventSubscriber<T> {
    void processEvent(Event<T> orderSavedEvent);
}
