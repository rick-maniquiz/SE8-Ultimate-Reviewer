package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.exammodels.*;
import org.example.topicmodels.ReviewerRepository;
import org.example.topicmodels.TopicRepository;

import java.io.*;
import java.nio.file.*;

public class Main {
    public static void main(String[] args) throws IOException {

        ReviewerRepository reviewerRepository = new ReviewerRepository();
        Exam exam = new Exam(56, "hard"); // easy, medium, hard
        SolutionSheet solutionSheet = new SolutionSheet(exam);
        exam.generatePDF();
        solutionSheet.generatePDF();

        reviewerRepository.printAllReviewers();
//        QuestionRepository.saveProgress();

    }
}
