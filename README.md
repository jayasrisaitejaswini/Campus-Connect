# Campus Connect – Campus Event Management System

## Project Overview
Campus Connect is a JavaFX-based desktop application developed to simplify campus event management and student registration.

The application allows administrators to create, manage, view, and delete campus events. It also provides student registration, participant viewing, available slot checking, and event reports.

## Technologies Used
- **Programming Language:** Java
- **GUI Framework:** JavaFX
- **Build Tool:** Maven
- **IDE:** Eclipse
- **Data Storage:** Text File Handling
- **Version Control:** Git and GitHub

## Main Features

### 1. Dashboard
- Displays total number of events.
- Displays total event capacity.
- Shows system status.
- Provides access to all modules.

### 2. Event Management
- Add new campus events.
- View existing events.
- Delete events.
- Save event details.

### 3. Student Registration
- Enter student details.
- Register students for campus events.
- Store registration information.

### 4. Participants
- View registered student details.

### 5. Available Slots
- Check available event capacity.

### 6. Reports
- View total events.
- View total capacity.
- Display individual event details.

## Data Storage
The application uses local text file handling.

- `events.txt` – Stores event information.
- `registrations.txt` – Stores student registration information.

## Project Structure

```text
CampusEventManager/
│
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── campusconnect/
│                   ├── Dashboard.java
│                   ├── Event.java
│                   ├── Student.java
│                   ├── RegistrationScreen.java
│                   ├── RegistrationManager.java
│                   ├── Registration.java
│                   └── ReportManager.java
│
├── events.txt
├── registrations.txt
├── pom.xml
└── README.md
```

## How to Run the Project

1. Install Java JDK 21.
2. Install Eclipse IDE.
3. Import the project into Eclipse as an existing Maven project.
4. Allow Maven to download the required dependencies.
5. Right-click the project.
6. Select **Run As → Maven Build**.
7. Enter the Maven goal:

```bash
clean javafx:run
```

8. Click Run.

## Future Enhancements
- Separate Admin and Student panels.
- Student login and authentication.
- Database integration using JDBC and MySQL.
- Event start and end timings.
- Registration deadlines.
- Automatic event status updates.
- Individual event-wise registration tracking.

## Project Purpose
This project aims to reduce manual work in campus event organization and provide a centralized platform for managing campus events and student registrations.

## Developed By
Campus Connect Project Team

## License
This project is developed for academic and educational purposes.
