package com.tunepruner.fourwards.data;

import com.tunepruner.fourwards.gui.ListArea;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class TimeContainers {
    private ObservableList<TimeContainer> listOfTimeContainers = FXCollections.observableArrayList();
    private Calendar dateOfList;

    public ObservableList<TimeContainer> getListOfTimeContainers() {
        return listOfTimeContainers;
    }

    public TimeContainers(ListArea listArea) {
        createListOfTimeContainersForTesting(listArea);
        this.dateOfList = Calendar.getInstance();
        this.dateOfList.set(Calendar.HOUR_OF_DAY, 0);
        createListOfTimeContainersForTesting(listArea);
    }

    public void createListOfTimeContainersForTesting(ListArea listArea){

        List<String> listOfStrings = Arrays.asList("The strings", "seen here", "are persisted", "in a text file!", "-------", "The cells", "are made of", "custom shapes!", "--------", "The positioning", "is determined", "in a custom", "grid of points!", "---------", "It supports", "SCROLLING!", "----------", "It also", "supports", "DROP", "DRAG AND", "REORDERING!");
        for ( String string : listOfStrings ) {
            TimeContainer timeContainer = new TimeContainer(listArea, string);
            listOfTimeContainers.add(timeContainer);
        }

    }
}
