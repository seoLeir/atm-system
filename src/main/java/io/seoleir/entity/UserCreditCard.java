package io.seoleir.entity;

import io.seoleir.enums.TransactionOperationType;

import java.util.UUID;

public class UserCreditCard {

    private UUID id;

    private String cardNumber;

    private String cardHolder;

    private String cardExpiration;

    private Double balance;

    public UserCreditCard(String cardNumber, String cardHolder, String cardExpiration, Double balance) {
        this.id = UUID.randomUUID();
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
        this.cardExpiration = cardExpiration;
        this.balance = balance;
    }

    public UserCreditCard(UUID id, String cardNumber, String cardHolder, String cardExpiration, Double balance) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
        this.cardExpiration = cardExpiration;
        this.balance = balance;
    }

    public void operate(Double amount, TransactionOperationType operationType) {
        switch (operationType) {
            case PLUS -> this.balance = this.balance + amount;
            case MINUS -> this.balance = this.balance - amount;
            case MULTIPLY -> this.balance = this.balance * amount;
            case DIVIDE -> this.balance = this.balance / amount;
            default -> {}
        }
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getCardExpiration() {
        return cardExpiration;
    }

    public void setCardExpiration(String cardExpiration) {
        this.cardExpiration = cardExpiration;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "UserCreditCard{" +
               "id=" + id +
               ", cardNumber='" + cardNumber + '\'' +
               ", cardHolder='" + cardHolder + '\'' +
               ", cardExpiration='" + cardExpiration + '\'' +
               ", balance=" + balance +
               '}';
    }
}
