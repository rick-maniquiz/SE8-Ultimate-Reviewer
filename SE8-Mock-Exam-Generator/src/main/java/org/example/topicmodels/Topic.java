package org.example.topicmodels;

import org.example.exammodels.QuestionEntry;

import java.util.ArrayList;
import java.util.List;

public class Topic {
    private int topicId;
    private String topicName;
    private String description;

    private List<QuestionEntry> topicQuestions = new ArrayList<>();

    public Topic(){};

    public Topic(int topicId, String topicName, String description) {
        this.topicId = topicId;
        this.topicName = topicName;
        this.description = description;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addQuestionEntry(QuestionEntry questionEntry){
        if (questionEntry.getTopicId() == topicId){
//            System.out.println("added");
            topicQuestions.add(questionEntry);
        } else {
            System.out.println("Question is not for the topic!");
        }
    }

    public List<QuestionEntry> getTopicQuestions() {
        return topicQuestions;
    }
}
