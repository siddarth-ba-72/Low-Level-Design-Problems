package stackoverflow.models;

import stackoverflow.observer.PostObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Post extends Content {
    private int voteCount = 0;
    private final Map<String, VoteType> voters = new HashMap<>();
    private final List<Comment> comments = new ArrayList<>();
    private final List<PostObserver> observers = new ArrayList<>();

    public Post(String id, String body, User author) {
        super(id, body, author);
    }

    public void addObserver(PostObserver observer) {
        this.observers.add(observer);
    }

    protected void notifyObservers(Event event) {
        observers.forEach(o -> o.onPostEvent(event));
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public List<Comment> getComments() {
        return comments;
    }

    public synchronized void vote(User user, VoteType voteType) {
        String userId = user.getId();
        if (userId.equals(author.getId()))
            return; // Cannot vote on your own post

        VoteType existingVote = voters.get(userId);
        if (existingVote == voteType)
            return; // Already voted this way

        // If the user is changing their vote, reverse the previous vote first
        if (existingVote != null) {
            voteCount += existingVote == VoteType.UPVOTE ? -1 : 1;
            notifyObservers(new Event(eventTypeFor(existingVote), user, this, true));
        }

        // Apply the new vote
        voteCount += voteType == VoteType.UPVOTE ? 1 : -1;
        voters.put(userId, voteType);
        notifyObservers(new Event(eventTypeFor(voteType), user, this, false));
    }

    private EventType eventTypeFor(VoteType voteType) {
        if (this instanceof Question) {
            return voteType == VoteType.UPVOTE ? EventType.UPVOTE_QUESTION : EventType.DOWNVOTE_QUESTION;
        }
        return voteType == VoteType.UPVOTE ? EventType.UPVOTE_ANSWER : EventType.DOWNVOTE_ANSWER;
    }
}
