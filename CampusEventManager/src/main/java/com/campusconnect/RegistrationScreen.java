
package com.campusconnect;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class RegistrationScreen extends VBox {

    private RegistrationManager manager;
    private FileManager fileManager;

    private TextField id;
    private TextField name;
    private TextField email;
    private TextField department;
    private TextField eventId;

    private Label result;
    private Label slots;
    private TextArea participants;

    private final String cardStyle =
            "-fx-background-color: #201A35;" +
            "-fx-background-radius: 15;" +
            "-fx-border-color: #393052;" +
            "-fx-border-radius: 15;" +
            "-fx-border-width: 1;";

    private final String fieldStyle =
            "-fx-background-color: #2B2442;" +
            "-fx-text-fill: white;" +
            "-fx-prompt-text-fill: #A99DBF;" +
            "-fx-background-radius: 8;" +
            "-fx-border-color: #46385F;" +
            "-fx-border-radius: 8;" +
            "-fx-padding: 11;";

    public RegistrationScreen() {

        manager = new RegistrationManager();
        fileManager = new FileManager();

        setSpacing(20);
        setPadding(new Insets(28));
        setStyle("-fx-background-color: #171326;");
        setFillWidth(true);

        // Page heading
        Label title = new Label("Student Registration");
        title.setStyle(
                "-fx-font-size: 27px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: white;"
        );

        Label subtitle = new Label(
                "Register students for campus events and manage participants."
        );
        subtitle.setStyle(
                "-fx-font-size: 13px;" +
                "-fx-text-fill: #B4A9C9;"
        );

        VBox heading = new VBox(6, title, subtitle);

        // Registration form card
        Label formTitle = new Label("Register a Student");
        formTitle.setStyle(
                "-fx-font-size: 18px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: white;"
        );

        id = createField("Enter Student ID");
        name = createField("Enter Student Name");
        email = createField("Enter Email Address");
        department = createField("Enter Department");
        eventId = createField("Enter Event ID");

        GridPane formGrid = new GridPane();
        formGrid.setHgap(18);
        formGrid.setVgap(15);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);

        formGrid.getColumnConstraints().addAll(col1, col2);

        formGrid.add(createFieldBox("Student ID", id), 0, 0);
        formGrid.add(createFieldBox("Student Name", name), 1, 0);
        formGrid.add(createFieldBox("Email Address", email), 0, 1);
        formGrid.add(createFieldBox("Department", department), 1, 1);
        formGrid.add(createFieldBox("Event ID", eventId), 0, 2);

        Button register = new Button("Register Student");
        register.setStyle(
                "-fx-background-color: #A855F7;" +
                "-fx-text-fill: white;" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 13px;" +
                "-fx-background-radius: 8;" +
                "-fx-padding: 12 22;"
        );

        Button viewParticipants = new Button("View Participants");
        viewParticipants.setStyle(
                "-fx-background-color: #35294D;" +
                "-fx-text-fill: white;" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 13px;" +
                "-fx-background-radius: 8;" +
                "-fx-padding: 12 22;" +
                "-fx-border-color: #6D4B9A;" +
                "-fx-border-radius: 8;"
        );

        HBox buttonRow = new HBox(12, register, viewParticipants);
        buttonRow.setAlignment(Pos.CENTER_LEFT);

        result = new Label("Enter student details to begin.");
        result.setWrapText(true);
        result.setStyle(
                "-fx-text-fill: #C4B5FD;" +
                "-fx-font-size: 13px;"
        );

        VBox formCard = new VBox(
                18,
                formTitle,
                formGrid,
                buttonRow,
                result
        );

        formCard.setPadding(new Insets(22));
        formCard.setStyle(cardStyle);

        // Participants section
        Label participantsTitle = new Label("Registered Participants");
        participantsTitle.setStyle(
                "-fx-font-size: 18px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: white;"
        );

        Label participantsSubtitle = new Label(
                "Enter an Event ID above to view its registered students."
        );
        participantsSubtitle.setStyle(
                "-fx-text-fill: #B4A9C9;" +
                "-fx-font-size: 12px;"
        );

        slots = new Label("Available Slots: --");
        slots.setStyle(
                "-fx-text-fill: #D8B4FE;" +
                "-fx-font-size: 14px;" +
                "-fx-font-weight: bold;"
        );

        participants = new TextArea();
        participants.setPromptText(
                "Participant details will appear here..."
        );
        participants.setEditable(false);
        participants.setWrapText(true);
        participants.setPrefRowCount(9);
        participants.setStyle(
                "-fx-control-inner-background: #191428;" +
                "-fx-text-fill: white;" +
                "-fx-prompt-text-fill: #A99DBF;" +
                "-fx-background-radius: 8;" +
                "-fx-border-color: #393052;" +
                "-fx-border-radius: 8;"
        );

        VBox participantsCard = new VBox(
                12,
                participantsTitle,
                participantsSubtitle,
                slots,
                participants
        );

        participantsCard.setPadding(new Insets(22));
        participantsCard.setStyle(cardStyle);

        // Registration action
        register.setOnAction(e -> {

            if (id.getText().trim().isEmpty()
                    || name.getText().trim().isEmpty()
                    || email.getText().trim().isEmpty()
                    || department.getText().trim().isEmpty()
                    || eventId.getText().trim().isEmpty()) {

                result.setText("Please fill all fields!");
                return;
            }

            Event selectedEvent = findEvent(eventId.getText().trim());

            if (selectedEvent == null) {
                result.setText("Event ID not found!");
                return;
            }

            Student student = new Student(
                    id.getText().trim(),
                    name.getText().trim(),
                    email.getText().trim(),
                    department.getText().trim()
            );

            String message = manager.registerStudent(
                    student,
                    selectedEvent.getEventId(),
                    selectedEvent.getCapacity(),
                    selectedEvent.getEventDate()
            );

            result.setText(message);

            refreshParticipants();
        });

        viewParticipants.setOnAction(e -> refreshParticipants());

        getChildren().addAll(
                heading,
                formCard,
                participantsCard
        );
    }

    private TextField createField(String prompt) {

        TextField field = new TextField();
        field.setPromptText(prompt);
        field.setStyle(fieldStyle);
        field.setMaxWidth(Double.MAX_VALUE);

        return field;
    }

    private VBox createFieldBox(String labelText, TextField field) {

        Label label = new Label(labelText);
        label.setStyle(
                "-fx-text-fill: #D8D0E8;" +
                "-fx-font-size: 12px;" +
                "-fx-font-weight: bold;"
        );

        VBox box = new VBox(7, label, field);
        box.setMaxWidth(Double.MAX_VALUE);

        return box;
    }

    private Event findEvent(String selectedId) {

        for (Event event : fileManager.loadEvents()) {

            if (event.getEventId().equals(selectedId)) {
                return event;
            }
        }

        return null;
    }

    private void refreshParticipants() {

        String selectedEventId = eventId.getText().trim();

        if (selectedEventId.isEmpty()) {
            result.setText("Enter Event ID first!");
            return;
        }

        Event selectedEvent = findEvent(selectedEventId);

        if (selectedEvent == null) {
            result.setText("Event ID not found!");
            return;
        }

        participants.clear();

        for (Student s : manager.getParticipants(selectedEventId)) {

            participants.appendText(
                    "Student Name: " + s.getStudentName()
                    + "  |  Department: " + s.getDepartment()
                    + "\n"
            );
        }

        slots.setText(
                "Available Slots: "
                + manager.getAvailableSlots(
                        selectedEventId,
                        selectedEvent.getCapacity()
                )
        );
    }
}
