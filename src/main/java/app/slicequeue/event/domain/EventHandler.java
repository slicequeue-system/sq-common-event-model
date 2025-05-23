package app.slicequeue.event.domain;

public interface EventHandler<T extends EventPayload> {
    void handle(Event event);
    boolean supports(Event event);
}
