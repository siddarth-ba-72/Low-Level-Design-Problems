package linkedin.models;

import java.time.LocalDateTime;
import java.util.UUID;

public class Notification {
    private final String id;
    private final NotificationType type;
    private final String content;
    private final LocalDateTime createdAt;
    private boolean isRead = false;

    public Notification(NotificationType type, String content) {
        this.id = UUID.randomUUID().toString();
        this.type = type;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }

    // Copy constructor: each recipient stores its own copy so read state is independent
    public Notification(Notification other) {
        this.id = other.id;
        this.type = other.type;
        this.content = other.content;
        this.createdAt = other.createdAt;
    }

    public String getContent() { return content; }
    public void markAsRead() { this.isRead = true; }
    public boolean isRead() { return isRead; }
}
