package models;

import org.joda.time.DateTime;

public class MessageEntry implements Comparable<MessageEntry>{

    public final String message;
    public final DateTime dateTime;

    public MessageEntry(String message) {
        this.dateTime = new DateTime();
        this.message = message;
    }

    @Override
    public int compareTo(MessageEntry o) {
        if(o == null) return -1;
        return dateTime.compareTo(o.dateTime);
    }
}
