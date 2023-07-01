package Entities.P05BillsPaymentSystem;

import jakarta.persistence.*;

@Entity
@Table(name = "credit_card")
public class CreditCard extends BillingDetails {
    private CardType cardType;
    private Integer month;
    private Integer year;

    public CreditCard() {
    }

    @Enumerated(EnumType.STRING)
    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    @Column(name = "expiration_month")
    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    @Column(name = "expiration_year")
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
