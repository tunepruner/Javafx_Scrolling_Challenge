package com.tunepruner.fourwards.topics;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Topics {
    private static ObservableList<Topic> topics = FXCollections.observableArrayList();
    public void addTopicToList(String topicName){
        boolean foundMatch = false;
        for ( Topic topic : topics ) {
            if (topic.getName() == topicName) foundMatch = true;
        }
        if (!foundMatch) topics.add(new Topic(topicName));
    }

}
