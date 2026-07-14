package stackoverflow.observer;

import stackoverflow.models.Event;

public interface PostObserver {
    void onPostEvent(Event event);
}
