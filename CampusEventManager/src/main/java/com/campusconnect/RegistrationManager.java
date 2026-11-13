package com.campusconnect;

import java.util.ArrayList;
import java.io.*;

public class RegistrationManager {

    private ArrayList<Registration> registrations;
    private static final String FILE_NAME = "registrations.txt";

    public RegistrationManager() {
        registrations = new ArrayList<>();
        loadRegistrations();
    }

    public String registerStudent(Student student,
                                  String eventId,
                                  int capacity,
                                  String date) {

        for (Registration r : registrations) {
            if (r.getStudent().getStudentId().equals(student.getStudentId())
                    && r.getEventId().equals(eventId)) {
                return "Student is already registered!";
            }
        }

        if (getRegisteredCount(eventId) >= capacity) {
            return "Registration is Full!";
        }

        String registrationId = "R" + (registrations.size() + 1);

        Registration registration = new Registration(
                registrationId,
                student,
                eventId,
                date
        );

        registrations.add(registration);

        saveRegistrations();

        return "Registration Successful!";
    }

    public int getRegisteredCount(String eventId) {
        int count = 0;

        for (Registration r : registrations) {
            if (r.getEventId().equals(eventId)) {
                count++;
            }
        }

        return count;
    }

    public int getAvailableSlots(String eventId, int capacity) {
        return Math.max(0, capacity - getRegisteredCount(eventId));
    }

    public ArrayList<Registration> getRegistrations() {
        return registrations;
    }

    public ArrayList<Student> getParticipants(String eventId) {
        ArrayList<Student> participants = new ArrayList<>();

        for (Registration r : registrations) {
            if (r.getEventId().equals(eventId)) {
                participants.add(r.getStudent());
            }
        }

        return participants;
    }

    private void saveRegistrations() {

        try (PrintWriter writer = new PrintWriter(
                new FileWriter(FILE_NAME))) {

            for (Registration r : registrations) {

                Student s = r.getStudent();

                writer.println(
                        s.getStudentId() + "," +
                        s.getStudentName() + "," +
                        s.getEmail() + "," +
                        s.getDepartment() + "," +
                        r.getEventId()
                );
            }

        } catch (IOException e) {
            System.out.println("Error saving registrations: "
                    + e.getMessage());
        }
    }

    private void loadRegistrations() {

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(
                new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",");

                if (data.length == 5) {

                    Student student = new Student(
                            data[0],
                            data[1],
                            data[2],
                            data[3]
                    );

                    Registration registration = new Registration(
                            "R" + (registrations.size() + 1),
                            student,
                            data[4],
                            "09-11-2026"
                    );

                    registrations.add(registration);
                }
            }

        } catch (IOException e) {
            System.out.println("Error loading registrations: "
                    + e.getMessage());
        }
    }
}