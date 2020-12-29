package com.tunepruner.fourwards.data;

import com.tunepruner.fourwards.gui.ListArea;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.io.StringWriter;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@XmlRootElement(name = "data")
@XmlAccessorType(XmlAccessType.FIELD)
public class Data {
    private static boolean listHasBeenCreated = false;
    @XmlElement(name = "time_container", type = TimeContainer.class)
    private static ObservableList<TimeContainer> list = FXCollections.observableArrayList();
    private static LocalDate dateOfList = LocalDate.now();

    public static ObservableList<TimeContainer> getList() {
        return list;
    }

    public static LocalDate getDateOfList() {
        return dateOfList;
    }
//
//    public static void createListOfTimeContainersForTesting(ListArea listArea) {
//        if (!listHasBeenCreated) {
//            List<String> listOfStrings = Arrays.asList("The strings", "seen here", "are persisted", "in a text file!", "-------", "The cells", "are made of", "custom shapes!", "--------", "The positioning", "is determined", "in a custom", "grid of points!", "---------", "It supports", "SCROLLING!", "----------", "It also", "supports", "DROP", "DRAG AND", "REORDERING!");
//            for ( String string : listOfStrings ) {
//                TimeContainer timeContainer = new TimeContainer(string);
//                list.add(timeContainer);
//            }
//            dateOfList = LocalDate.now();
//            listHasBeenCreated = true;
//        }
//        persist();
//    }

    public static Integer indexOf(TimeContainer timeContainerToEvaluate) {
        Integer indexOfTimeContainer = null;
        for ( int i = 0; i > list.size(); i++ ) {
            if (timeContainerToEvaluate == list.get(i)) {
                indexOfTimeContainer = i;
                break;
            }
        }
        return indexOfTimeContainer;
    }

    public static Integer indexOf(String topicName) {
        Integer indexOfTimeContainer = null;
        for ( int i = 0; i < list.size(); i++ ) {
            if (list.get(i).getTopic().getName().equals(topicName)) {
                indexOfTimeContainer = i;
                break;
            }
        }
        return indexOfTimeContainer;
    }

    public static void add(TimeContainer timeContainerToAdd) {
        list.add(timeContainerToAdd);
    }

    public static void add(int index, TimeContainer timeContainerToAdd) {
        list.add(index, timeContainerToAdd);
    }

    public static void add(ListArea listArea, String topicNameToAdd) {
        TimeContainer timeContainer = new TimeContainer(topicNameToAdd);
        add(timeContainer);
    }

    public static void add(int index, ListArea listArea, String topicNameToAdd) {
        TimeContainer timeContainer = new TimeContainer(topicNameToAdd);
        add(index, timeContainer);
    }

    public static void remove(TimeContainer timeContainerToRemove) {
        list.remove(timeContainerToRemove);
    }

    public static void remove(String topicNameToRemove) {
        int indexOf = indexOf(topicNameToRemove);
        list.remove(indexOf);
    }

    public static boolean contains(TimeContainer timeContainerToQuery) {
        return list.contains(timeContainerToQuery);
    }

    public static boolean contains(String topicNameToQuery) {
        return list
                .stream()
                .anyMatch(timeContainer -> timeContainer.getTopic().getName().equals(topicNameToQuery));
    }

    public static void printAllContent() {
        for ( TimeContainer timeContainer : list ) {
            System.out.println(timeContainer.getTopic().getName());
        }
    }

    public static void persist() {
        try {
            Data data = new Data();
            JAXBContext context = JAXBContext.newInstance(Data.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            StringWriter sw = new StringWriter();
            marshaller.marshal(data, sw);

            File f = new File("./data.xml");
            marshaller.marshal(data, f);

            System.out.println(sw.toString());
        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }

    public static void readFromFile() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Data.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        File f = new File("data.xml");

        Data data = (Data) unmarshaller.unmarshal(f);
    }
}
