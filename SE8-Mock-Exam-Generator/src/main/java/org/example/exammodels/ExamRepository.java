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

public class ExamRepository implements Savable{
    private static List<Exam> exams;
//    private static ExamRepository instance = new ExamRepository();
    private static final String persistentFilePath = "persistent/exam-repository-persistent.json";

    @Override
    public void saveProgress() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
//        String jsonString = mapper.writeValueAsString(instance);
        mapper.writeValue(new File(persistentFilePath), exams);
    }

    public ExamRepository(){
        ObjectMapper mapper = new ObjectMapper();
        File jsonFile = new File(persistentFilePath);
        if (jsonFile.exists()){
            try {
                exams = mapper.readValue(jsonFile, new TypeReference<List<Exam>>() {});
//            System.out.println( "IT WENT HERE" + questions.size());
            } catch (StreamReadException e) {
                throw new RuntimeException(e);
            } catch (DatabindException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        } else exams = new ArrayList<>();

    }

    public static void addExam(Exam exam){
        exams.add(exam);
    }

    public static List<Exam> getExams() {
        return exams;
    }

    public static void setExams(List<Exam> exams) {
        ExamRepository.exams = exams;
    }

//    public static ExamRepository getInstance() {
//        return instance;
//    }
//
//    public static void setInstance(ExamRepository instance) {
//        ExamRepository.instance = instance;
//    }
}
