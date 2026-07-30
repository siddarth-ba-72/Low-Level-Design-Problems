package linkedin.models;

import linkedin.observer.NotificationObserver;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject {
    private final List<NotificationObserver> observers = new ArrayList<>();

    public void addObserver(NotificationObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void removeObserver(NotificationObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(Notification notification) {
        notifyObservers(notification, null);
    }

    // Notify every observer except the one who triggered the event (e.g. the liker)
    public void notifyObservers(Notification notification, NotificationObserver excluded) {
        for (NotificationObserver observer : observers) {
            if (observer != excluded) {
                observer.update(notification);
            }
        }
    }
}
