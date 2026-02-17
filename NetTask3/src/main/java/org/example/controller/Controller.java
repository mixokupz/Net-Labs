package org.example.controller;

import java.util.List;
import java.util.Scanner;
import org.example.view.ConsoleView;
import org.example.model.entities.Location;

public class Controller {
    private final ConsoleView consoleView;
    private final Scanner scanner;

    public Controller(ConsoleView consoleView) {
        this.consoleView = consoleView;
        this.scanner = new Scanner(System.in);
    }

    public String inputNameOfLocation() {
        String locationName;
        while (true) {
            consoleView.showLocationNameInput();
            locationName = scanner.nextLine();

            if (!isWhiteSpace(locationName)) {
                break;
            }
        }
        return locationName;
    }

    public int inputIndexOfLocation(List<Location> locations) {
        consoleView.showLocations(locations);

        int locationNumber = inputInt(
                () -> consoleView.showIndexOfLocationInput(),
                () -> consoleView.showInvalidInput(),
                1,
                locations.size()
        );
        return locationNumber - 1;
    }

    private int inputInt(Runnable valueInput,
                         Runnable invalidValueHandle,
                         Integer minIncluded,
                         Integer maxIncluded) {
        int result;
        while (true) {
            try {
                valueInput.run();
                String input = scanner.nextLine();
                result = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                invalidValueHandle.run();
                continue;
            }

            boolean minMatch = result >= minIncluded;
            boolean maxMatch = result <= maxIncluded;

            if (minMatch && maxMatch) {
                consoleView.showEmptyLine();
                break;
            }

            invalidValueHandle.run();
        }
        return result;
    }

    private static boolean isWhiteSpace(String s) {

        return s.trim().isEmpty();
    }
}
