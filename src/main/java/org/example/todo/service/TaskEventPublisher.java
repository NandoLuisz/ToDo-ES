package org.example.todo.service;

import org.example.todo.domain.TaskEventDto;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;


@Component
public class TaskEventPublisher {

    private final Sinks.Many<TaskEventDto> sink;

    public TaskEventPublisher() {
        this.sink = Sinks.many().multicast().onBackpressureBuffer();
    }

    public void publish(TaskEventDto event) {
        sink.tryEmitNext(event);
    }

    public Flux<TaskEventDto> getEvents() {
        return sink.asFlux();
    }
}
