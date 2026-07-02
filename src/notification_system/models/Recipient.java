package notification_system.models;

import java.util.Optional;

public class Recipient {
    private final String userId;
    private final Optional<String> email;
    private final Optional<String> phoneNumber;
    private final Optional<String> pushToken;

    public Recipient(String userId, String email, String phoneNumber, String pushToken) {
        this.userId = userId;
        this.email = Optional.ofNullable(email);
        this.phoneNumber = Optional.ofNullable(phoneNumber);
        this.pushToken = Optional.ofNullable(pushToken);
    }

    public String getUserId() {
        return userId;
    }

    public Optional<String> getEmail() {
        return email;
    }

    public Optional<String> getPhoneNumber() {
        return phoneNumber;
    }

    public Optional<String> getPushToken() {
        return pushToken;
    }
}