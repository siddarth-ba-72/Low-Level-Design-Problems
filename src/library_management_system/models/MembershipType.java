package library_management_system.models;

public enum MembershipType {
    MONTHLY(10),
    ANNUAL(100),
    LIFETIME(500);

    private final int fees;

    MembershipType(int fees) {
        this.fees = fees;
    }

    public int getFees() {
        return fees;
    }

}
