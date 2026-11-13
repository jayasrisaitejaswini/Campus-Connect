
package com.campusconnect;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Dashboard extends VBox {

    private EventManager eventManager;
    private FileManager fileManager;
    private ReportManager reportManager;

    private TextField eventId;
    private TextField eventName;
    private TextField eventDate;
    private TextField eventVenue;
    private TextField capacity;

    private TextArea displayArea;
    private VBox eventCards;

    private Label totalEventsLabel;
    private Label totalCapacityLabel;

    private static final String BG = "#100D1F";
    private static final String PANEL = "#1D1830";
    private static final String PURPLE = "#9B5CFF";
    private static final String PINK = "#E34DCC";
    private static final String TEXT = "#F5F3FF";
    private static final String MUTED = "#AAA3C2";

    public Dashboard() {

        eventManager = new EventManager();
        fileManager = new FileManager();
        reportManager = new ReportManager();

        eventManager.getEvents().addAll(fileManager.loadEvents());

        setStyle("-fx-background-color: " + BG + ";");

        BorderPane mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-background-color: " + BG + ";");

        mainLayout.setLeft(createSidebar());
        mainLayout.setCenter(createMainContent());

        getChildren().add(mainLayout);

        FadeTransition fade =
                new FadeTransition(Duration.millis(900), mainLayout);

        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();

        refreshStats();
        refreshEventCards();
    }

    private VBox createSidebar() {

        VBox sidebar = new VBox(22);
        sidebar.setPadding(new Insets(30, 18, 25, 18));
        sidebar.setPrefWidth(205);

        sidebar.setStyle(
                "-fx-background-color: #171226;" +
                "-fx-border-color: #302544;" +
                "-fx-border-width: 0 1 0 0;"
        );

        Label brand = new Label("✦ CAMPUS CONNECT");

        brand.setStyle(
                "-fx-text-fill: " + PINK + ";" +
                "-fx-font-size: 18px;" +
                "-fx-font-weight: bold;"
        );

        Label menuTitle = new Label("MAIN MENU");

        menuTitle.setStyle(
                "-fx-text-fill: " + MUTED + ";" +
                "-fx-font-size: 10px;" +
                "-fx-font-weight: bold;"
        );

        VBox menu = new VBox(10);

        Button dashboardButton = sideButton("▦  Dashboard");
        Button eventsButton = sideButton("◈  Events");
        Button registrationButton = sideButton("♙  Registration");
        Button participantsButton = sideButton("♧  Participants");
        Button reportsButton = sideButton("▤  Reports");

        dashboardButton.setStyle(activeButtonStyle());

        eventsButton.setOnAction(e -> viewEvents());
        registrationButton.setOnAction(e -> openRegistration());
        participantsButton.setOnAction(e -> viewParticipants());
        reportsButton.setOnAction(e -> generateReport());

        menu.getChildren().addAll(
                dashboardButton,
                eventsButton,
                registrationButton,
                participantsButton,
                reportsButton
        );

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        Label footer = new Label(
                "Campus Event Manager\nJavaFX Edition"
        );

        footer.setStyle(
                "-fx-text-fill: " + MUTED + ";" +
                "-fx-font-size: 11px;"
        );

        sidebar.getChildren().addAll(
                brand, menuTitle, menu, spacer, footer
        );

        return sidebar;
    }

    private VBox createMainContent() {

        VBox content = new VBox(22);
        content.setPadding(new Insets(28, 30, 28, 30));

        Label welcome = new Label("Welcome to Campus Connect");

        welcome.setStyle(
                "-fx-text-fill: " + TEXT + ";" +
                "-fx-font-size: 26px;" +
                "-fx-font-weight: bold;"
        );

        Label subtitle = new Label(
                "Manage campus events and student registrations"
        );

        subtitle.setStyle("-fx-text-fill: " + MUTED + ";");

        VBox heading = new VBox(6);
        heading.getChildren().addAll(welcome, subtitle);

        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);

        Region headerSpacer = new Region();
        HBox.setHgrow(headerSpacer, Priority.ALWAYS);

        Label adminBadge = new Label("●  ADMIN PANEL");

        adminBadge.setPadding(new Insets(10, 15, 10, 15));

        adminBadge.setStyle(
                "-fx-background-color: #302044;" +
                "-fx-text-fill: " + PINK + ";" +
                "-fx-background-radius: 20;" +
                "-fx-font-weight: bold;"
        );

        header.getChildren().addAll(
                heading, headerSpacer, adminBadge
        );

        HBox stats = new HBox(16);

        totalEventsLabel = new Label("0");
        totalCapacityLabel = new Label("0");

        stats.getChildren().addAll(
                createStatCard("TOTAL EVENTS", totalEventsLabel, "◈"),
                createStatCard("TOTAL CAPACITY", totalCapacityLabel, "♧"),
                createStatCard(
                        "SYSTEM STATUS",
                        new Label("ACTIVE"),
                        "✦"
                )
        );

        for (javafx.scene.Node node : stats.getChildren()) {
            HBox.setHgrow(node, Priority.ALWAYS);
        }

        HBox workArea = new HBox(20);
        workArea.setAlignment(Pos.TOP_LEFT);

        VBox formCard = createEventForm();
        VBox outputCard = createOutputPanel();

        HBox.setHgrow(outputCard, Priority.ALWAYS);

        workArea.getChildren().addAll(formCard, outputCard);

        content.getChildren().addAll(header, stats, workArea);

        return content;
    }

    private VBox createStatCard(
            String title, Label value, String iconText) {

        VBox card = new VBox(12);
        card.setPadding(new Insets(20));
        card.setMinWidth(150);

        card.setStyle(
                "-fx-background-color: " + PANEL + ";" +
                "-fx-background-radius: 14;" +
                "-fx-border-color: #39284F;" +
                "-fx-border-radius: 14;"
        );

        Label icon = new Label(iconText);

        icon.setStyle(
                "-fx-text-fill: " + PINK + ";" +
                "-fx-font-size: 22px;"
        );

        Label titleLabel = new Label(title);

        titleLabel.setStyle(
                "-fx-text-fill: " + MUTED + ";" +
                "-fx-font-size: 11px;" +
                "-fx-font-weight: bold;"
        );

        value.setStyle(
                "-fx-text-fill: " + TEXT + ";" +
                "-fx-font-size: 25px;" +
                "-fx-font-weight: bold;"
        );

        card.getChildren().addAll(icon, titleLabel, value);

        addHoverAnimation(card);

        return card;
    }

    private VBox createEventForm() {

        VBox card = new VBox(14);
        card.setPadding(new Insets(22));
        card.setPrefWidth(310);

        card.setStyle(
                "-fx-background-color: " + PANEL + ";" +
                "-fx-background-radius: 14;" +
                "-fx-border-color: #39284F;" +
                "-fx-border-radius: 14;"
        );

        Label heading = new Label("Create New Event");

        heading.setStyle(
                "-fx-text-fill: " + TEXT + ";" +
                "-fx-font-size: 18px;" +
                "-fx-font-weight: bold;"
        );

        Label subtitle = new Label("Enter event details below");
        subtitle.setStyle("-fx-text-fill: " + MUTED + ";");

        eventId = createField("Event ID");
        eventName = createField("Event Name");
        eventDate = createField("Date (DD-MM-YYYY)");
        eventVenue = createField("Venue");
        capacity = createField("Capacity");

        Button addButton = primaryButton("＋  Add Event");
        Button viewButton = secondaryButton("View Events");
        Button saveButton = secondaryButton("Save Events");
        Button deleteButton = secondaryButton("Delete Event");
        Button reportButton = secondaryButton("Generate Report");

        Button registrationButton =
                secondaryButton("Student Registration");

        addButton.setOnAction(e -> addEvent());
        viewButton.setOnAction(e -> viewEvents());

        saveButton.setOnAction(e -> {
            fileManager.saveEvents(eventManager.getEvents());
            displayArea.setText("Events saved successfully!");
            refreshStats();
        });

        deleteButton.setOnAction(e -> deleteEvent());
        reportButton.setOnAction(e -> generateReport());
        registrationButton.setOnAction(e -> openRegistration());

        card.getChildren().addAll(
                heading,
                subtitle,
                eventId,
                eventName,
                eventDate,
                eventVenue,
                capacity,
                addButton,
                viewButton,
                saveButton,
                deleteButton,
                reportButton,
                registrationButton
        );

        return card;
    }

    private VBox createOutputPanel() {

        VBox card = new VBox(14);
        card.setPadding(new Insets(22));

        card.setStyle(
                "-fx-background-color: " + PANEL + ";" +
                "-fx-background-radius: 14;" +
                "-fx-border-color: #39284F;" +
                "-fx-border-radius: 14;"
        );

        Label heading = new Label("Upcoming Events");

        heading.setStyle(
                "-fx-text-fill: " + TEXT + ";" +
                "-fx-font-size: 20px;" +
                "-fx-font-weight: bold;"
        );

        Label subtitle = new Label(
                "Explore your campus events"
        );

        subtitle.setStyle("-fx-text-fill: " + MUTED + ";");

        eventCards = new VBox(14);

        ScrollPane scrollPane = new ScrollPane(eventCards);

        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(300);

        scrollPane.setStyle(
                "-fx-background: transparent;" +
                "-fx-background-color: transparent;"
        );

        displayArea = new TextArea();

        displayArea.setEditable(false);
        displayArea.setWrapText(true);
        displayArea.setPromptText(
                "Reports and messages will appear here..."
        );

        displayArea.setPrefRowCount(5);

        displayArea.setStyle(
                "-fx-control-inner-background: #151022;" +
                "-fx-text-fill: #EDE7FF;" +
                "-fx-font-family: 'Consolas';" +
                "-fx-font-size: 12px;"
        );

        card.getChildren().addAll(
                heading, subtitle, scrollPane, displayArea
        );

        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        return card;
    }

    private void refreshEventCards() {

        if (eventCards == null) {
            return;
        }

        eventCards.getChildren().clear();

        if (eventManager.getEvents().isEmpty()) {

            Label empty = new Label(
                    "No events available. Create your first event!"
            );

            empty.setStyle("-fx-text-fill: " + MUTED + ";");

            eventCards.getChildren().add(empty);

            return;
        }

        for (Event event : eventManager.getEvents()) {

            VBox card = new VBox(10);

            card.setPadding(new Insets(18));

            card.setStyle(
                    "-fx-background-color: #251B3A;" +
                    "-fx-background-radius: 12;" +
                    "-fx-border-color: #563477;" +
                    "-fx-border-radius: 12;"
            );

            Label name = new Label(event.getEventName());

            name.setStyle(
                    "-fx-text-fill: " + TEXT + ";" +
                    "-fx-font-size: 18px;" +
                    "-fx-font-weight: bold;"
            );

            Label id = new Label(
                    "EVENT ID: " + event.getEventId()
            );

            id.setStyle(
                    "-fx-text-fill: " + PINK + ";" +
                    "-fx-font-size: 11px;" +
                    "-fx-font-weight: bold;"
            );

            Label date = new Label(
                    "◷  " + event.getEventDate()
            );

            Label venue = new Label(
                    "⌖  " + event.getEventVenue()
            );

            Label seats = new Label(
                    "♧  Capacity: " + event.getCapacity()
            );

            date.setStyle("-fx-text-fill: " + MUTED + ";");
            venue.setStyle("-fx-text-fill: " + MUTED + ";");
            seats.setStyle("-fx-text-fill: " + MUTED + ";");

            Region spacer = new Region();

            VBox details = new VBox(8);

            details.getChildren().addAll(
                    id, name, date, venue, seats
            );

            card.getChildren().addAll(details, spacer);

            addHoverAnimation(card);

            FadeTransition fade =
                    new FadeTransition(Duration.millis(450), card);

            fade.setFromValue(0);
            fade.setToValue(1);
            fade.play();

            eventCards.getChildren().add(card);
        }
    }

    private TextField createField(String prompt) {

        TextField field = new TextField();

        field.setPromptText(prompt);
        field.setPrefHeight(38);

        field.setStyle(
                "-fx-background-color: #151022;" +
                "-fx-text-fill: " + TEXT + ";" +
                "-fx-prompt-text-fill: #817798;" +
                "-fx-background-radius: 8;" +
                "-fx-border-color: #39284F;" +
                "-fx-border-radius: 8;" +
                "-fx-padding: 9;"
        );

        return field;
    }

    private Button primaryButton(String text) {

        Button button = new Button(text);

        button.setMaxWidth(Double.MAX_VALUE);
        button.setPrefHeight(42);

        button.setStyle(
                "-fx-background-color: linear-gradient(to right, "
                + PURPLE + ", " + PINK + ");" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 13px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 9;" +
                "-fx-cursor: hand;"
        );

        animateButton(button);

        return button;
    }

    private Button secondaryButton(String text) {

        Button button = new Button(text);

        button.setMaxWidth(Double.MAX_VALUE);
        button.setPrefHeight(38);

        button.setStyle(
                "-fx-background-color: #302044;" +
                "-fx-text-fill: #E9D5FF;" +
                "-fx-font-size: 12px;" +
                "-fx-background-radius: 8;" +
                "-fx-border-color: #49305F;" +
                "-fx-border-radius: 8;" +
                "-fx-cursor: hand;"
        );

        animateButton(button);

        return button;
    }

    private Button sideButton(String text) {

        Button button = new Button(text);

        button.setMaxWidth(Double.MAX_VALUE);
        button.setAlignment(Pos.CENTER_LEFT);
        button.setPrefHeight(42);

        button.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-text-fill: " + MUTED + ";" +
                "-fx-font-size: 12px;" +
                "-fx-background-radius: 8;" +
                "-fx-cursor: hand;"
        );

        button.setOnMouseEntered(e ->
                button.setStyle(
                        "-fx-background-color: #302044;" +
                        "-fx-text-fill: " + TEXT + ";" +
                        "-fx-background-radius: 8;"
                )
        );

        button.setOnMouseExited(e -> {
            if (!button.getText().contains("Dashboard")) {
                button.setStyle(
                        "-fx-background-color: transparent;" +
                        "-fx-text-fill: " + MUTED + ";" +
                        "-fx-background-radius: 8;"
                );
            }
        });

        return button;
    }

    private String activeButtonStyle() {

        return "-fx-background-color: linear-gradient(to right, "
                + PURPLE + ", " + PINK + ");"
                + "-fx-text-fill: white;"
                + "-fx-font-weight: bold;"
                + "-fx-background-radius: 8;";
    }

    private void animateButton(Button button) {

        button.setOnMouseEntered(e -> {

            ScaleTransition scale =
                    new ScaleTransition(Duration.millis(130), button);

            scale.setToX(1.03);
            scale.setToY(1.03);

            scale.play();
        });

        button.setOnMouseExited(e -> {

            ScaleTransition scale =
                    new ScaleTransition(Duration.millis(130), button);

            scale.setToX(1);
            scale.setToY(1);

            scale.play();
        });
    }

    private void addHoverAnimation(VBox card) {

        card.setOnMouseEntered(e -> {

            ScaleTransition scale =
                    new ScaleTransition(Duration.millis(150), card);

            scale.setToX(1.025);
            scale.setToY(1.025);

            scale.play();
        });

        card.setOnMouseExited(e -> {

            ScaleTransition scale =
                    new ScaleTransition(Duration.millis(150), card);

            scale.setToX(1);
            scale.setToY(1);

            scale.play();
        });
    }

    private void addEvent() {

        try {

            if (eventId.getText().isEmpty()
                    || eventName.getText().isEmpty()
                    || eventDate.getText().isEmpty()
                    || eventVenue.getText().isEmpty()
                    || capacity.getText().isEmpty()) {

                displayArea.setText("Please fill all fields!");

                return;
            }

            if (eventManager.findEvent(eventId.getText()) != null) {

                displayArea.setText("Event ID already exists!");

                return;
            }

            int eventCapacity =
                    Integer.parseInt(capacity.getText());

            if (eventCapacity <= 0) {

                displayArea.setText(
                        "Capacity must be greater than zero!");

                return;
            }

            Event event = new Event(
                    eventId.getText(),
                    eventName.getText(),
                    eventDate.getText(),
                    eventVenue.getText(),
                    eventCapacity
            );

            eventManager.addEvent(event);

            displayArea.setText(
                    "Event added successfully!\n"
                    + event.getEventName()
            );

            clearFields();

            refreshStats();
            refreshEventCards();

        } catch (NumberFormatException ex) {

            displayArea.setText("Capacity must be a number!");
        }
    }

    private void viewEvents() {

        StringBuilder result =
                new StringBuilder("===== ALL EVENTS =====\n\n");

        for (Event event : eventManager.getEvents()) {

            result.append("ID: ")
                    .append(event.getEventId())
                    .append("\n");

            result.append("Name: ")
                    .append(event.getEventName())
                    .append("\n");

            result.append("Date: ")
                    .append(event.getEventDate())
                    .append("\n");

            result.append("Venue: ")
                    .append(event.getEventVenue())
                    .append("\n");

            result.append("Capacity: ")
                    .append(event.getCapacity())
                    .append("\n");

            result.append("-----------------------------\n");
        }

        displayArea.setText(result.toString());
    }

    private void deleteEvent() {

        boolean deleted =
                eventManager.deleteEvent(eventId.getText());

        if (deleted) {

            displayArea.setText("Event deleted successfully!");

        } else {

            displayArea.setText("Event ID not found!");
        }

        refreshStats();
        refreshEventCards();
    }

    // UPDATED GRAPHICAL REPORT WINDOW
    private void generateReport() {

        Stage reportStage = new Stage();
        reportStage.setTitle("Campus Connect - Event Reports");

        VBox root = new VBox(22);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #100D1F;");

        Label title = new Label("✦  EVENT REPORTS");

        title.setStyle(
                "-fx-text-fill: #E34DCC;" +
                "-fx-font-size: 28px;" +
                "-fx-font-weight: bold;"
        );

        Label subtitle = new Label(
                "Campus event overview and management analytics"
        );

        subtitle.setStyle(
                "-fx-text-fill: #AAA3C2;" +
                "-fx-font-size: 14px;"
        );

        VBox header = new VBox(8, title, subtitle);

        int totalEvents = eventManager.getEvents().size();
        int totalCapacity = 0;

        for (Event event : eventManager.getEvents()) {
            totalCapacity += event.getCapacity();
        }

        Label eventsValue = new Label(String.valueOf(totalEvents));
        Label capacityValue = new Label(String.valueOf(totalCapacity));
        Label statusValue = new Label("ACTIVE");

        HBox summaryCards = new HBox(18);

        summaryCards.getChildren().addAll(
                createStatCard("TOTAL EVENTS", eventsValue, "◈"),
                createStatCard("TOTAL CAPACITY", capacityValue, "♧"),
                createStatCard("SYSTEM STATUS", statusValue, "✦")
        );

        for (javafx.scene.Node node : summaryCards.getChildren()) {
            HBox.setHgrow(node, Priority.ALWAYS);
        }

        Label eventsHeading = new Label("Upcoming Campus Events");

        eventsHeading.setStyle(
                "-fx-text-fill: #F5F3FF;" +
                "-fx-font-size: 21px;" +
                "-fx-font-weight: bold;"
        );

        VBox eventList = new VBox(15);

        if (eventManager.getEvents().isEmpty()) {

            Label emptyMessage = new Label(
                    "No events available. Please create an event first."
            );

            emptyMessage.setStyle(
                    "-fx-text-fill: #AAA3C2;" +
                    "-fx-font-size: 15px;"
            );

            eventList.getChildren().add(emptyMessage);

        } else {

            int number = 1;

            for (Event event : eventManager.getEvents()) {

                VBox eventCard = new VBox(12);
                eventCard.setPadding(new Insets(20));

                eventCard.setStyle(
                        "-fx-background-color: #1D1830;" +
                        "-fx-background-radius: 14;" +
                        "-fx-border-color: #563477;" +
                        "-fx-border-radius: 14;"
                );

                Label eventNumber = new Label(
                        "EVENT " + String.format("%02d", number)
                );

                eventNumber.setStyle(
                        "-fx-text-fill: #E34DCC;" +
                        "-fx-font-size: 11px;" +
                        "-fx-font-weight: bold;"
                );

                Label eventNameLabel =
                        new Label(event.getEventName());

                eventNameLabel.setStyle(
                        "-fx-text-fill: #F5F3FF;" +
                        "-fx-font-size: 20px;" +
                        "-fx-font-weight: bold;"
                );

                Label eventIdLabel =
                        new Label("Event ID: " + event.getEventId());

                Label dateLabel =
                        new Label("Date: " + event.getEventDate());

                Label venueLabel =
                        new Label("Venue: " + event.getEventVenue());

                Label capacityLabel =
                        new Label(
                                "Capacity: "
                                + event.getCapacity()
                                + " students"
                        );

                String detailStyle =
                        "-fx-text-fill: #AAA3C2;" +
                        "-fx-font-size: 14px;";

                eventIdLabel.setStyle(detailStyle);
                dateLabel.setStyle(detailStyle);
                venueLabel.setStyle(detailStyle);
                capacityLabel.setStyle(detailStyle);

                eventCard.getChildren().addAll(
                        eventNumber,
                        eventNameLabel,
                        eventIdLabel,
                        dateLabel,
                        venueLabel,
                        capacityLabel
                );

                addHoverAnimation(eventCard);

                eventList.getChildren().add(eventCard);

                number++;
            }
        }

        ScrollPane scrollPane = new ScrollPane(eventList);

        scrollPane.setFitToWidth(true);
        scrollPane.setVvalue(0);
        scrollPane.setHbarPolicy(
                ScrollPane.ScrollBarPolicy.NEVER
        );

        scrollPane.setStyle(
                "-fx-background: transparent;" +
                "-fx-background-color: transparent;"
        );

        scrollPane.setStyle(
                "-fx-background: transparent;" +
                "-fx-background-color: transparent;"
        );

        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        root.getChildren().addAll(
                header,
                summaryCards,
                eventsHeading,
                scrollPane
        );

        Scene scene = new Scene(root, 900, 700);

        reportStage.setScene(scene);
        reportStage.show();
    }

    private void openRegistration() {

        RegistrationScreen screen = new RegistrationScreen();

        Stage stage = new Stage();

        stage.setTitle("Campus Connect - Student Registration");

        Scene scene = new Scene(screen, 750, 800);

        stage.setScene(scene);

        stage.setMinWidth(700);
        stage.setMinHeight(700);

        stage.show();
    }
    private void viewParticipants() {

        openRegistration();

    }

    private void refreshStats() {

        if (totalEventsLabel == null
                || totalCapacityLabel == null) {

            return;
        }

        int totalCapacity = 0;

        for (Event event : eventManager.getEvents()) {
            totalCapacity += event.getCapacity();
        }

        totalEventsLabel.setText(
                String.valueOf(eventManager.getEvents().size())
        );

        totalCapacityLabel.setText(
                String.valueOf(totalCapacity)
        );
    }

    private void clearFields() {

        eventId.clear();
        eventName.clear();
        eventDate.clear();
        eventVenue.clear();
        capacity.clear();
    }
}
