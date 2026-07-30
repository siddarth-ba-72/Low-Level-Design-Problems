package linkedin.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Post extends Subject {
    private final String id;
    private final Member author;
    private final String content;
    private final LocalDateTime createdAt;
    private final List<Like> likes = new ArrayList<>();
    private final List<Comment> comments = new ArrayList<>();

    public Post(Member author, String content) {
        this.id = UUID.randomUUID().toString();
        this.author = author;
        this.content = content;
        this.createdAt = LocalDateTime.now();
        // The author observes their own post; commenters join the thread when they participate
        this.addObserver(author);
    }

    public void addLike(Member member) {
        likes.add(new Like(member));
        String notificationContent = member.getName() + " liked " + author.getName() + "'s post.";
        Notification notification = new Notification(NotificationType.POST_LIKE, notificationContent);
        notifyObservers(notification, member);
    }

    public void addComment(Member member, String text) {
        comments.add(new Comment(member, text));
        String notificationContent = member.getName() + " commented on " + author.getName() + "'s post: \"" + text + "\"";
        Notification notification = new Notification(NotificationType.POST_COMMENT, notificationContent);
        notifyObservers(notification, member);
        // The commenter joins the thread and hears about future activity on this post
        addObserver(member);
    }

    public String getId() {
        return id;
    }

    public Member getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public List<Comment> getComments() {
        return comments;
    }
}
