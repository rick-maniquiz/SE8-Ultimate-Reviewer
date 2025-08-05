package org.example.exammodels;

import org.example.utils.Answerable;

import java.util.List;
import java.util.Map;

public class QuestionEntry implements Answerable {
    private int topicId;
    private String topic;
    private int questionId;
    private String difficulty;
    private String type;
    private String question;
    private Map<String, String> choices;
    private List<String> answers;

    private boolean isAttempted = false;
    private boolean isAnswered = false;
    private int attempts = 0;

//    public QuestionEntry(){
//
//    }
//
//    public QuestionEntry(int topicId, int questionId, String difficulty, String type, String question, Map<String, String> choices, List<String> answers) {
//        this.topicId = topicId;
//        this.questionId = questionId;
//        this.difficulty = difficulty;
//        this.type = type;
//        this.question = question;
//        this.choices = choices;
//        this.answers = answers;
//    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Map<String, String> getChoices() {
        return choices;
    }

    public void setChoices(Map<String, String> choices) {
        this.choices = choices;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    @Override
    public void correct(){
        isAnswered = true;
        isAttempted = true;
        attempts +=1;
    }

    @Override
    public void attempted(){
        isAttempted = true;
        attempts +=1;
    }

    public boolean isAttempted() {
        return isAttempted;
    }

    public boolean isAnswered() {
        return isAnswered;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempted(boolean attempted) {
        isAttempted = attempted;
    }

    public void setAnswered(boolean answered) {
        isAnswered = answered;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }
}
