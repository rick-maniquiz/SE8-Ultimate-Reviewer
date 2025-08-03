package org.example.topicmodels;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.exammodels.QuestionEntry;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class TopicRepository {
    private static List<Topic> topics;
    private static final TopicRepository instance = new TopicRepository();

    static {
        ObjectMapper mapper = new ObjectMapper();
        File jsonFile = new File("topic-category-generation/topics.json");
        try {
//            System.out.println("added topic");
            topics = mapper.readValue(jsonFile, new TypeReference<List<Topic>>() {});
            System.out.println(topics);
        } catch (StreamReadException e) {
            throw new RuntimeException(e);
        } catch (DatabindException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    public TopicRepository(){
//
//    }

    public static List<Topic> getTopics() {
        return topics;
    }

    public static TopicRepository getInstance(){
        return instance;
    }

    public static Optional<Topic> getTopicById(int topicId){
        return topics.stream()
                .filter(topic -> topic.getTopicId() == topicId)
                .findFirst();
    }
}
