package autocomplete_system;

import autocomplete_system.models.Suggestion;
import autocomplete_system.models.Trie;
import autocomplete_system.models.TrieNode;
import autocomplete_system.ranking.RankingStrategy;

import java.util.List;

public class AutocompleteSystem {

    private final Trie trie;
    private final RankingStrategy rankingStrategy;
    private final int maxSuggestions;

    public AutocompleteSystem(RankingStrategy rankingStrategy, int maxSuggestions) {
        this.trie = new Trie();
        this.rankingStrategy = rankingStrategy;
        this.maxSuggestions = maxSuggestions;
    }

    public void addWord(String term) {
        trie.insert(term.toLowerCase());
    }

    public void addWords(List<String> words) {
        words.forEach(this::addWord);
    }

    public List<String> getSuggestions(String prefix) {
        TrieNode prefixNode = trie.searchPrefix(prefix.toLowerCase());
        if (prefixNode == null) {
            return List.of();
        }
        List<Suggestion> rawSuggestions = trie.collectSuggestions(prefixNode, prefix.toLowerCase());
        List<Suggestion> rankedSuggestions = rankingStrategy.rank(rawSuggestions);

        return rankedSuggestions.stream()
                .limit(maxSuggestions)
                .map(Suggestion::getWord)
                .toList();
    }

}
