package app.slicequeue.event.domain;

import app.slicequeue.event.util.DataSerializer;
import lombok.Getter;

/**
 * 이벤트 통신을 위한 클래스
 */
@Getter
public class Event {
    private Long eventId;
    private EventDescriptor type;
    private Object payload;

    public static Event of(Long eventId, EventDescriptor type, EventPayload payload) {
        Event event = new Event();
        event.eventId = eventId;
        event.type = type;
        event.payload = payload;
        return event;
    }

    public String toJson() {
        return DataSerializer.serialize(this);
    }

    public static Event fromJson(
            String json,
            Class<? extends EventDescriptor> eventTypeClazz) {
        EventRaw eventRaw = DataSerializer.deserialize(json, EventRaw.class);
        if (eventRaw == null) return null;

        EventDescriptor type = DataSerializer.deserialize(eventRaw.getType(), eventTypeClazz);
        Event event = new Event();
        event.eventId = eventRaw.getEventId();
        event.type = type;
        event.payload = eventRaw.getPayload();
        return event;
    }

    public <T extends EventPayload> T extractPayload(Class<T> payloadClazz) {
        return DataSerializer.deserialize(payload, payloadClazz);
    }

    @Getter
    public static class EventRaw {
        private Long eventId;
        private Object type;
        private Object payload;

        public record EventType(String code, String topic) { }

    }

}
