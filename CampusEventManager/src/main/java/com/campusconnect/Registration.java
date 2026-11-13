
package com.campusconnect;

public class Registration {

    private String registrationId;
    private Student student;
    private String eventId;
    private String registrationDate;

    public Registration(String registrationId, Student student,
                        String eventId, String registrationDate) {

        this.registrationId = registrationId;
        this.student = student;
        this.eventId = eventId;
        this.registrationDate = registrationDate;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public Student getStudent() {
        return student;
    }

    public String getEventId() {
        return eventId;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }
}
