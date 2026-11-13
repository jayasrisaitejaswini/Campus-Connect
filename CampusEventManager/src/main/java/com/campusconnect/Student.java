
package com.campusconnect;

public class Student {

    private String studentId;
    private String studentName;
    private String email;
    private String department;

    public Student(String studentId, String studentName,
                   String email, String department) {

        this.studentId = studentId;
        this.studentName = studentName;
        this.email = email;
        this.department = department;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getEmail() {
        return email;
    }

    public String getDepartment() {
        return department;
    }
}

