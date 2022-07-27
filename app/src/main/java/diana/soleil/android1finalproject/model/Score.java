package diana.soleil.android1finalproject.model;

import java.io.Serializable;
import java.util.Objects;

public class Score implements Serializable {
    public static int counter;
    private String question;
    private float correctAnswer;
    private float userAnswer;
    private boolean score;

    public Score(String question, float correctAnswer, float userAnswer, boolean score) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.userAnswer = userAnswer;
        this.score = score;
    }

    public float getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(float userAnswer) {
        this.userAnswer = userAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public float getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(float correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public boolean getScore() {
        return score;
    }

    public void setScore(boolean score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Score)) return false;
        Score score1 = (Score) o;
        return Float.compare(score1.getCorrectAnswer(), getCorrectAnswer()) == 0 && Float.compare(score1.getUserAnswer(), getUserAnswer()) == 0 && getScore() == score1.getScore() && getQuestion().equals(score1.getQuestion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getQuestion(), getCorrectAnswer(), getUserAnswer(), getScore());
    }

    @Override
    public String toString() {
        return "Question: " + question + "\n" + wrongOrRight(score) + "\n" +
                " Your Answer was: " + userAnswer + "\n" +
                " Correct Answer was: " + correctAnswer + "\n";
    }
    public String wrongOrRight(boolean score) {
        if (score) return "Your answer was Correct!";
        return "Your answer was Incorrect!";
    }
}
