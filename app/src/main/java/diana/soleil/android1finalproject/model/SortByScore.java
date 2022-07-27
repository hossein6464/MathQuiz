package diana.soleil.android1finalproject.model;

import java.util.Comparator;

public class SortByScore implements Comparator<Score> {
    @Override
    public int compare(Score o1, Score o2) {
        return Boolean.compare( o2.getScore(), o1.getScore());
    }
}
