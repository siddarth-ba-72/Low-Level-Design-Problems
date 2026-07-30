package linkedin.service;

import linkedin.models.Member;
import linkedin.models.NewsFeed;
import linkedin.models.Post;
import linkedin.sort.FeedSortingStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NewsFeedService {
    private final Map<String, List<Post>> allPosts; // A map of memberId -> list of their posts

    public NewsFeedService() {
        this.allPosts = new ConcurrentHashMap<>();
    }

    public void addPost(Member member, Post post) {
        // computeIfAbsent creates the list in one atomic step, avoiding a check-then-act race
        allPosts.computeIfAbsent(member.getId(), k -> new ArrayList<>()).add(post);
    }

    public List<Post> getMemberPosts(Member member) {
        return allPosts.getOrDefault(member.getId(), new ArrayList<>());
    }

    public void displayFeedForMember(Member member, FeedSortingStrategy feedSortingStrategy) {
        List<Post> feedPosts = new ArrayList<>();
        // Add posts from the member's connections
        for (Member connection : member.getConnections()) {
            List<Post> connectionPosts = allPosts.get(connection.getId());
            if (connectionPosts != null) {
                feedPosts.addAll(connectionPosts);
            }
        }

        NewsFeed feed = new NewsFeed(feedPosts);
        feed.display(feedSortingStrategy);
    }
}
