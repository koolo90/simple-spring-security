package com.example.buisseness;

public class Answer {
    private Question question;
    private String content;

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Question getQuestion() {
        return question;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public boolean isCorrect() {
        return this.getQuestion().getSuggestedAnswer().equals(this.getContent());
    }
}
