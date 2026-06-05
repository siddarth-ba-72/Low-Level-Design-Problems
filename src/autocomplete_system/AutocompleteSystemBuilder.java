package autocomplete_system;

import autocomplete_system.ranking.FrequencyBasedRanking;
import autocomplete_system.ranking.RankingStrategy;

public class AutocompleteSystemBuilder {

    private RankingStrategy rankingStrategy = new FrequencyBasedRanking();
    private int maxSuggestions = 10;

    public AutocompleteSystemBuilder withRankingStrategy(RankingStrategy rankingStrategy) {
        this.rankingStrategy = rankingStrategy;
        return this;
    }

    public AutocompleteSystemBuilder withMaxSuggestions(int maxSuggestions) {
        this.maxSuggestions = maxSuggestions;
        return this;
    }

    public AutocompleteSystem build() {
        return new AutocompleteSystem(rankingStrategy, maxSuggestions);
    }

}
