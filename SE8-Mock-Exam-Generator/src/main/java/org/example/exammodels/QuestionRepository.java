package org.example.exammodels;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuestionRepository {
    private static List<QuestionEntry> questions;
    private static QuestionRepository instance = new QuestionRepository();

    QuestionRepository(){
        ObjectMapper mapper = new ObjectMapper();
        File jsonFile = new File("questions/question-repository-verbatim.json");
        try {
            questions = mapper.readValue(jsonFile, new TypeReference<List<QuestionEntry>>() {});
        } catch (StreamReadException e) {
            throw new RuntimeException(e);
        } catch (DatabindException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static QuestionRepository getInstance() {
        return instance;
    }

    public static List<QuestionEntry> getQuestions() {
        return questions;
    }
}
