package notification_system.gateway;

import notification_system.exception.DeliveryException;
import notification_system.models.Notification;

public interface NotificationGateway {
    void send(Notification notification) throws DeliveryException;
}
