package com.tunepruner.fourwards.data;

import com.tunepruner.fourwards.gui.ListArea;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class TimeContainers {
    private static ObservableList<TimeContainer> listOfTimeContainers = FXCollections.observableArrayList();
    private LocalDate dateOfList;
    public ObservableList<TimeContainer> getListOfTimeContainers() {
        return listOfTimeContainers;
    }

    public TimeContainers(ListArea listArea) {
        createListOfTimeContainersForTesting(listArea);
        this.dateOfList = LocalDate.now();
        createListOfTimeContainersForTesting(listArea);
    }

    public LocalDate getDateOfList() {
        return dateOfList;
    }

    public void createListOfTimeContainersForTesting(ListArea listArea) {
        List<String> listOfStrings = Arrays.asList("The strings", "seen here", "are persisted", "in a text file!", "-------", "The cells", "are made of", "custom shapes!", "--------", "The positioning", "is determined", "in a custom", "grid of points!", "---------", "It supports", "SCROLLING!", "----------", "It also", "supports", "DROP", "DRAG AND", "REORDERING!");
        for ( String string : listOfStrings ) {
            TimeContainer timeContainer = new TimeContainer(listArea, string);
            listOfTimeContainers.add(timeContainer);
        }


    }

    public Integer getIndex(TimeContainer timeContainerToEvaluate) {
        Integer indexOfTimeContainer = null;
        for ( int i = 0; i > listOfTimeContainers.size(); i++ ) {
            if (timeContainerToEvaluate == listOfTimeContainers.get(i)) {
                indexOfTimeContainer = i;
                break;
            }
        }
        return indexOfTimeContainer;
    }

    public Integer getIndex(String topicName) {
        Integer indexOfTimeContainer = null;
        for ( int i = 0; i < listOfTimeContainers.size(); i++ ) {
            if (listOfTimeContainers.get(i).getTopic().equals(topicName)) {
                indexOfTimeContainer = i;
                break;
            }
        }
        return indexOfTimeContainer;
    }

    public void add(TimeContainer timeContainerToAdd) {
        listOfTimeContainers.add(timeContainerToAdd);
    }

    public void add(ListArea listArea, String topicNameToAdd) {
        TimeContainer timeContainer = new TimeContainer(listArea, topicNameToAdd);
        add(timeContainer);
    }

    public void remove(TimeContainer timeContainerToRemove) {
        listOfTimeContainers.remove(timeContainerToRemove);
    }

    public void remove(String topicNameToRemove) {
        listOfTimeContainers.remove(getIndex(topicNameToRemove));
    }

    public boolean contains(TimeContainer timeContainerToQuery) {
        return listOfTimeContainers.contains(timeContainerToQuery);
    }

    public boolean contains(String topicNameToQuery) {
        return listOfTimeContainers
                .stream()
                .anyMatch(timeContainer -> timeContainer.getTopic().equals(topicNameToQuery));
    }
}
