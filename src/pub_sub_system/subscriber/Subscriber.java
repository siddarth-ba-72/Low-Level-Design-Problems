package pub_sub_system.subscriber;

import pub_sub_system.models.Message;

public interface Subscriber {
    String getId();
    void onMessage(Message message);
}
