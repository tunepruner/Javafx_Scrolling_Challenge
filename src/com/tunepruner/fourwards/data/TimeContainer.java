package com.tunepruner.fourwards.data;

import com.tunepruner.fourwards.gui.Cell;
import com.tunepruner.fourwards.gui.ListArea;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
//@XmlType(propOrder={"topicName"})
public class TimeContainer {
    @XmlElement(name = "topic", type = Topic.class)
    private Topic topic;
    private String topicName;
    public static final String TOPIC_NAME="topicName";

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic){
        this.topic = topic;
    }

    public TimeContainer(){}

    public TimeContainer(String topicName) {
        this.topic = new Topic(topicName);
    }
}
