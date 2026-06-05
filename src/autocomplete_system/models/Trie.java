package autocomplete_system.models;

import java.util.ArrayList;
import java.util.List;

public class Trie {

    private final TrieNode root;

    public Trie() {
        this.root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode currentNode = root;
        for (char c : word.toCharArray()) {
            currentNode = currentNode.getChildren().computeIfAbsent(c, k -> new TrieNode());
        }
        currentNode.setEndOfWord(true);
        currentNode.incrementFrequency();
    }

    public TrieNode searchPrefix(String prefix) {
        TrieNode currentNode = root;
        for (char c : prefix.toCharArray()) {
            TrieNode node = currentNode.getChildren().get(c);
            if (node == null) {
                return null;
            }
            currentNode = node;
        }
        return currentNode;
    }

    public List<Suggestion> collectSuggestions(TrieNode startNode, String prefix) {
        List<Suggestion> suggestions = new ArrayList<>();
        collect(startNode, prefix, suggestions);
        return suggestions;
    }

    private void collect(TrieNode node, String currentPrefix, List<Suggestion> suggestions) {
        if (node.isEndOfWord()) {
            suggestions.add(new Suggestion(currentPrefix, node.getFrequency()));
        }
        for (Character ch : node.getChildren().keySet()) {
            collect(node.getChildren().get(ch), currentPrefix + ch, suggestions);
        }
    }

}
