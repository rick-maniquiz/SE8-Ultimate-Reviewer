package org.example.topicmodels;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.exammodels.SolutionEntry;
import org.example.exammodels.SolutionRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ReviewerRepository {
    private static ReviewerRepository instance = new ReviewerRepository();
    private static List<Reviewer> reviewers;

    public ReviewerRepository(){
        ObjectMapper mapper = new ObjectMapper();
        File jsonFile = new File("reviewer/reviewers.json");
        try {
            reviewers = mapper.readValue(jsonFile, new TypeReference<List<Reviewer>>() {});

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

    public void printAllReviewers(){
        for (Reviewer reviewer : reviewers){
            reviewer.generatePDF();
        }
    }

}
