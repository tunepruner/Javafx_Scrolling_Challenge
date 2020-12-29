package com.tunepruner.fourwards.data;

import com.tunepruner.fourwards.gui.ListArea;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class TimeContainers {
    private static boolean listHasBeenCreated = false;
    private static ObservableList<TimeContainer> listOfTimeContainers = FXCollections.observableArrayList();
    private static LocalDate dateOfList = LocalDate.now();

    public static ObservableList<TimeContainer> getListOfTimeContainers() {
        return listOfTimeContainers;
    }

    public static LocalDate getDateOfList() {
        return dateOfList;
    }

    public static void createListOfTimeContainersForTesting(ListArea listArea) {
        if (!listHasBeenCreated) {
            List<String> listOfStrings = Arrays.asList("The strings", "seen here", "are persisted", "in a text file!", "-------", "The cells", "are made of", "custom shapes!", "--------", "The positioning", "is determined", "in a custom", "grid of points!", "---------", "It supports", "SCROLLING!", "----------", "It also", "supports", "DROP", "DRAG AND", "REORDERING!");
            for ( String string : listOfStrings ) {
                TimeContainer timeContainer = new TimeContainer(listArea, string);
                listOfTimeContainers.add(timeContainer);
            }
            dateOfList = LocalDate.now();
            listHasBeenCreated = true;
        }
    }

    public static Integer indexOf(TimeContainer timeContainerToEvaluate) {
        Integer indexOfTimeContainer = null;
        for ( int i = 0; i > listOfTimeContainers.size(); i++ ) {
            if (timeContainerToEvaluate == listOfTimeContainers.get(i)) {
                indexOfTimeContainer = i;
                break;
            }
        }
        return indexOfTimeContainer;
    }

    public static Integer indexOf(String topicName) {
        Integer indexOfTimeContainer = null;
        for ( int i = 0; i < listOfTimeContainers.size(); i++ ) {
            if (listOfTimeContainers.get(i).getTopic().getName().equals(topicName)) {
                indexOfTimeContainer = i;
                break;
            }
        }
        return indexOfTimeContainer;
    }

    public static void add(TimeContainer timeContainerToAdd) {
        listOfTimeContainers.add(timeContainerToAdd);
    }

    public static void add(int index, TimeContainer timeContainerToAdd) {
        listOfTimeContainers.add(index, timeContainerToAdd);
    }

    public static void add(ListArea listArea, String topicNameToAdd) {
        TimeContainer timeContainer = new TimeContainer(listArea, topicNameToAdd);
        add(timeContainer);
    }

    public static void add(int index, ListArea listArea, String topicNameToAdd) {
        TimeContainer timeContainer = new TimeContainer(listArea, topicNameToAdd);
        add(index, timeContainer);
    }

    public static void remove(TimeContainer timeContainerToRemove) {
        listOfTimeContainers.remove(timeContainerToRemove);
    }

    public static void remove(String topicNameToRemove) {
        TimeContainers.printAllContent();
        int indexOf = indexOf(topicNameToRemove);
        listOfTimeContainers.remove(indexOf);
        TimeContainers.printAllContent();
    }

    public static boolean contains(TimeContainer timeContainerToQuery) {
        return listOfTimeContainers.contains(timeContainerToQuery);
    }

    public static boolean contains(String topicNameToQuery) {
        return listOfTimeContainers
                .stream()
                .anyMatch(timeContainer -> timeContainer.getTopic().getName().equals(topicNameToQuery));
    }

    public static void printAllContent(){
        for ( TimeContainer timeContainer : listOfTimeContainers ) {
            System.out.println(timeContainer.getTopic().getName());
        }
    }
}
