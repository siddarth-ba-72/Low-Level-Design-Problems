package autocomplete_system.ranking;

import autocomplete_system.models.Suggestion;

import java.util.List;

public interface RankingStrategy {

    List<Suggestion> rank(List<Suggestion> suggestions);

}
