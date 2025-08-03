package org.example.exammodels;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.topicmodels.Topic;
import org.example.topicmodels.TopicRepository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QuestionRepository {
    private static List<QuestionEntry> questions;
    private static QuestionRepository instance = new QuestionRepository();

    public QuestionRepository(){
        ObjectMapper mapper = new ObjectMapper();
        File jsonFile = new File("questions/question-repository-verbatim.json");
        try {
            questions = mapper.readValue(jsonFile, new TypeReference<List<QuestionEntry>>() {});
//            System.out.println( "IT WENT HERE" + questions.size());
            int topicId;
            for (QuestionEntry questionEntry : questions){
                topicId = questionEntry.getTopicId();
                Optional<Topic> topicIdSearchResult = TopicRepository.getTopicById(topicId);
                if (topicIdSearchResult.isPresent()){
                    topicIdSearchResult.get().addQuestionEntry(questionEntry);
                } else {
                    System.out.println("Invalid QuestionEntry topicId");
                }
            }
        } catch (StreamReadException e) {
            throw new RuntimeException(e);
        } catch (DatabindException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static QuestionRepository getInstance() {
        return instance;
    }

    public static List<QuestionEntry> getQuestions() {
        return questions;
    }
}
