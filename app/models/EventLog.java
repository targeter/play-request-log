package models;

import play.libs.F;

import java.util.List;

public class EventLog {

    static EventLog instance;

    final F.ArchivedEventStream<MessageEntry> chatEvents = new F.ArchivedEventStream<MessageEntry>(100);

    public List<MessageEntry> getArchive() {
        return chatEvents.archive();
    }
    public F.EventStream<MessageEntry> getStream() {
        return chatEvents.eventStream();
    }

    public void put(MessageEntry entry) {
        chatEvents.publish(entry);
    }

    public static EventLog get() {
        if(instance == null) {
            instance = new EventLog();
        }

        return instance;
    }
}
