package autocomplete_system.ranking;

import autocomplete_system.models.Suggestion;

import java.util.Comparator;
import java.util.List;

public class FrequencyBasedRanking implements RankingStrategy {

    @Override
    public List<Suggestion> rank(List<Suggestion> suggestions) {
        return suggestions.stream()
                .sorted(Comparator.comparingInt(Suggestion::getWeight).reversed())
                .toList();
    }

}
