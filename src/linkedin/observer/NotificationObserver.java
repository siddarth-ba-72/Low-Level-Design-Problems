package linkedin.observer;

import linkedin.models.Notification;

public interface NotificationObserver {
    void update(Notification notification);
}
