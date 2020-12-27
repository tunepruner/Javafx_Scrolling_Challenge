package com.tunepruner.fourwards.topics;

import com.tunepruner.fourwards.ListArea;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class TimeContainers {
    private ObservableList<TimeContainer> listOfEntries = FXCollections.observableArrayList();
    private Calendar dateOfList;

    public TimeContainers(ListArea listArea, Calendar dateOfList) {
        this.listOfEntries = listOfEntries;
        this.dateOfList = Calendar.getInstance();
        dateOfList.set(Calendar.HOUR_OF_DAY, 0);
        createListOfTimeContainersForTesting(listArea);
    }

    public void createListOfTimeContainersForTesting(ListArea listArea){

        List<String> listOfStrings = Arrays.asList("The strings", "seen here", "are persisted", "in a text file!", "-------", "The cells", "are made of", "custom shapes!", "--------", "The positioning", "is determined", "in a custom", "grid of points!", "---------", "It supports", "SCROLLING!", "----------", "It also", "supports", "DROP", "DRAG AND", "REORDERING!");
        for ( String string : listOfStrings ) {
            TimeContainer timeContainer = new TimeContainer(listArea, string);
            listOfEntries.add(timeContainer);
        }
    }
}
