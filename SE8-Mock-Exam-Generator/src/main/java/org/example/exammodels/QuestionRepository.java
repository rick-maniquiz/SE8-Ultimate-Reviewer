package org.example.exammodels;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.topicmodels.Topic;
import org.example.topicmodels.TopicRepository;
import org.example.utils.Savable;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class QuestionRepository implements Savable {

//    private static QuestionRepository instance = new QuestionRepository();
    private List<QuestionEntry> questions;
    private static final String persistentFilePath = "persistent/question-repository-persistent.json";


    public QuestionRepository(){
        ObjectMapper mapper = new ObjectMapper();
        File jsonFile = (new File(persistentFilePath)).exists()? new File(persistentFilePath) : new File("questions/question-repository-verbatim.json");
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

    @Override
    public void saveProgress() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
//        String jsonString = mapper.writeValueAsString(instance);
        mapper.writeValue(new File(persistentFilePath), questions);
    }

//    public static QuestionRepository getInstance() {
//        return instance;
//    }

    public List<QuestionEntry> getQuestions() {
        return questions;
    }
}
