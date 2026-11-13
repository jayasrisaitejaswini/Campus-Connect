
package com.campusconnect;

public class Event {

    private String eventId;
    private String eventName;
    private String eventDate;
    private String eventVenue;
    private int capacity;

    public Event(String eventId, String eventName,
                 String eventDate, String eventVenue,
                 int capacity) {

        this.eventId = eventId;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventVenue = eventVenue;
        this.capacity = capacity;
    }

    public String getEventId() {
        return eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getEventVenue() {
        return eventVenue;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public void setEventVenue(String eventVenue) {
        this.eventVenue = eventVenue;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
