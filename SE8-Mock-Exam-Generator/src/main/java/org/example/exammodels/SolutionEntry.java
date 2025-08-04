package org.example.exammodels;

import java.util.Map;

public class SolutionEntry {
    private int topicId, solutionId;
    private String topic;
    private Map<String, String> explanation;

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public int getSolutionId() {
        return solutionId;
    }

    public void setSolutionId(int solutionId) {
        this.solutionId = solutionId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Map<String, String> getExplanation() {
        return explanation;
    }

    public void setExplanation(Map<String, String> explanation) {
        this.explanation = explanation;
    }
}
