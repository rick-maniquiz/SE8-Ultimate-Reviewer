package org.example;

import org.example.exammodels.Exam;
import org.example.exammodels.QuestionRepository;
import org.example.topicmodels.TopicRepository;

import java.io.*;
import java.nio.file.*;

public class Main {
    public static void main(String[] args) {

        TopicRepository topicRepository = new TopicRepository();
        QuestionRepository questionRepository = new QuestionRepository();
        Exam exam = new Exam(56, "easy");
        exam.generatePDF();
    }
}
