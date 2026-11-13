package com.campusconnect;

import java.io.*;
import java.util.ArrayList;

public class FileManager {

    private static final String FILE_NAME = "events.txt";

    public void saveEvents(ArrayList<Event> events) {

        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {

            for (Event event : events) {
                writer.println(
                    event.getEventId() + "," +
                    event.getEventName() + "," +
                    event.getEventDate() + "," +
                    event.getEventVenue() + "," +
                    event.getCapacity()
                );
            }

        } catch (IOException e) {
            System.out.println("Error saving events: " + e.getMessage());
        }
    }

    public ArrayList<Event> loadEvents() {

        ArrayList<Event> events = new ArrayList<>();

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return events;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",");

                if (data.length == 5) {
                    Event event = new Event(
                        data[0],
                        data[1],
                        data[2],
                        data[3],
                        Integer.parseInt(data[4])
                    );

                    events.add(event);
                }
            }

        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading events: " + e.getMessage());
        }

        return events;
    }
}