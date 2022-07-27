package diana.soleil.android1finalproject.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

public class ScoreDataCollection implements Serializable {
    private ArrayList<Score> scores;

    public ScoreDataCollection(ArrayList<Score> scores) {
        this.scores = scores;
    }

    public ArrayList<Score> getScores() {
        return scores;
    }

    public void setScores(ArrayList<Score> scores) {
        this.scores = scores;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ScoreDataCollection)) return false;
        ScoreDataCollection that = (ScoreDataCollection) o;
        return Objects.equals(getScores(), that.getScores());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getScores());
    }

    @Override
    public String toString() {
        return "ScoreDataCollection{" +
                "scores=" + scores +
                '}';
    }
}
