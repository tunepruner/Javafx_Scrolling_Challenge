package com.tunepruner.fourwards.topics;

import com.tunepruner.fourwards.Cell;
import com.tunepruner.fourwards.ListArea;

public class TimeContainer {

    private final Cell cell;
    private final Topic topic;

    public Cell getCell() {
        return cell;
    }

    public Topic getTopic() {
        return topic;
    }

    public TimeContainer(ListArea listArea, String topicName) {
        this.cell = new Cell(listArea, topicName);
        this.topic = new Topic(topicName);
    }
}
