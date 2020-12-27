package com.tunepruner.fourwards.topics;

import com.tunepruner.fourwards.Cell;

public class TimeContainer {
    private final Cell cell;
    private final Topic topic;

    public TimeContainer(String topicName) {
        this.cell = new Cell("asdfasdf");
        this.topic = new Topic(topicName);
    }
}
