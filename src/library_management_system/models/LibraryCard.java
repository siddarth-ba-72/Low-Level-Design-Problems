package library_management_system.models;

import java.util.Date;

public class LibraryCard {

    private final String cardId;
    private final int customerId;
    private MembershipType membershipType;
    private Date expiryDate;

    public LibraryCard(String cardId, int customerId, MembershipType membershipType, Date expiryDate) {
        this.cardId = cardId;
        this.customerId = customerId;
        this.membershipType = membershipType;
        this.expiryDate = expiryDate;
    }

    public String getCardId() {
        return cardId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public MembershipType getMembershipType() {
        return membershipType;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setMembershipType(MembershipType membershipType) {
        this.membershipType = membershipType;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

}
