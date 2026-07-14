package stackoverflow.strategy;

import stackoverflow.models.Question;

import java.util.List;

public interface SearchStrategy {
    List<Question> filter(List<Question> questions);
}
