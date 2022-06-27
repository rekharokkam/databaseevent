package com.learning.databaseevent.events;

import java.util.List;

public interface EventPublisher<T> {

    void addSubscriber(EventSubscriber eventSubscriber);

    void removeSubscriber(EventSubscriber eventSubscriber);

    void publishEvent (Event<T> event);
}
