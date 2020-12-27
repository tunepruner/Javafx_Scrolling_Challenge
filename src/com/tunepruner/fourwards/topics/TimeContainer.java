package com.tunepruner.fourwards.topics;

import com.tunepruner.fourwards.Cell;
import com.tunepruner.fourwards.ListArea;

public class TimeContainer {
    private final Cell cell;
    private final Topic topic;

    public TimeContainer(ListArea listArea, String topicName) {
        this.cell = new Cell(listArea, "asdfasdf");
        this.topic = new Topic(topicName);
    }
}
