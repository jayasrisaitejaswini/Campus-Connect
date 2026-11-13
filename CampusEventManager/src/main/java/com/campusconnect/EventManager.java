package com.campusconnect;

import java.util.ArrayList;

public class EventManager {

    private ArrayList<Event> events;

    public EventManager() {
        events = new ArrayList<>();
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public Event findEvent(String eventId) {
        for (Event event : events) {
            if (event.getEventId().equals(eventId)) {
                return event;
            }
        }
        return null;
    }

    public boolean deleteEvent(String eventId) {
        Event event = findEvent(eventId);

        if (event != null) {
            events.remove(event);
            return true;
        }

        return false;
    }

    public int getTotalEvents() {
        return events.size();
    }
}