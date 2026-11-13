package com.campusconnect;

public class RegistrationTest {

    public static void main(String[] args) {

        RegistrationManager manager = new RegistrationManager();

        Student s1 = new Student("101", "Ravi", "ravi@gmail.com", "IT");
        Student s2 = new Student("102", "Priya", "priya@gmail.com", "CSE");

        System.out.println(manager.registerStudent(s1, "E001", 2, "09-11-2026"));
        System.out.println(manager.registerStudent(s2, "E001", 2, "09-11-2026"));
        System.out.println(manager.registerStudent(s1, "E001", 2, "09-11-2026"));

        System.out.println("Registered: " + manager.getRegisteredCount("E001"));
        System.out.println("Available slots: " + manager.getAvailableSlots("E001", 2));
        System.out.println("\nParticipants:");

        for (Student s : manager.getParticipants("E001")) {
            System.out.println(s.getStudentName() + " - " + s.getDepartment());
        }
    }
    
}