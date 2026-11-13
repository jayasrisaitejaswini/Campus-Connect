
package com.campusconnect;

import java.util.ArrayList;

public class ReportManager {

    public String generateEventReport(ArrayList<Event> events) {

        StringBuilder report = new StringBuilder();

        int totalCapacity = 0;

        report.append("========================================\n");
        report.append("       CAMPUS CONNECT EVENT REPORT      \n");
        report.append("========================================\n\n");

        report.append("Total Events: ")
              .append(events.size())
              .append("\n");

        for (Event event : events) {
            totalCapacity += event.getCapacity();
        }

        report.append("Total Event Capacity: ")
              .append(totalCapacity)
              .append("\n\n");

        report.append("----------------------------------------\n");
        report.append("           EVENT DETAILS                \n");
        report.append("----------------------------------------\n\n");

        if (events.isEmpty()) {

            report.append("No events are available.\n");
            report.append("Please create an event first.\n");

        } else {

            int count = 1;

            for (Event event : events) {

                report.append("Event Number: ")
                      .append(count)
                      .append("\n");

                report.append("Event ID: ")
                      .append(event.getEventId())
                      .append("\n");

                report.append("Event Name: ")
                      .append(event.getEventName())
                      .append("\n");

                report.append("Date: ")
                      .append(event.getEventDate())
                      .append("\n");

                report.append("Venue: ")
                      .append(event.getEventVenue())
                      .append("\n");

                report.append("Capacity: ")
                      .append(event.getCapacity())
                      .append("\n");

                report.append("----------------------------------------\n");

                count++;
            }
        }

        report.append("\n========================================\n");
        report.append("         END OF EVENT REPORT            \n");
        report.append("========================================\n");

        return report.toString();
    }
}
