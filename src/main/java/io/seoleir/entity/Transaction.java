package io.seoleir.entity;

import io.seoleir.entity.UserCreditCard;
import io.seoleir.enums.TransactionType;

import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {

    private UUID id;

    private TransactionType type;

    private Double amount;

    private UserCreditCard fromUserCard;

    private UserCreditCard toUserCard;

    private LocalDateTime dateTime;

    public Transaction(UUID transationUuid, TransactionType type, Double amount, UserCreditCard fromUserCard, UserCreditCard toUserCard, LocalDateTime dateTime) {
        this.id = transationUuid;
        this.type = type;
        this.amount = amount;
        this.fromUserCard = fromUserCard;
        this.toUserCard = toUserCard;
        this.dateTime = dateTime;
    }

    public Transaction(TransactionType type, Double amount, UserCreditCard fromUserCard, UserCreditCard toUserCard) {
        this.id = UUID.randomUUID();
        this.type = type;
        this.amount = amount;
        this.fromUserCard = fromUserCard;
        this.toUserCard = toUserCard;
        this.dateTime = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public UserCreditCard getFromUserCard() {
        return fromUserCard;
    }

    public void setFromUserCard(UserCreditCard fromUserCard) {
        this.fromUserCard = fromUserCard;
    }

    public UserCreditCard getToUserCard() {
        return toUserCard;
    }

    public void setToUserCard(UserCreditCard toUserCard) {
        this.toUserCard = toUserCard;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        if (type.equals(TransactionType.TRANSFER)) {
            return "Transaction{" +
                   "id=" + id +
                   ", type=" + type +
                   ", amount=" + amount +
                   ", fromUserCard=" + fromUserCard.getCardNumber() +
                   ", toUserCard=" + toUserCard.getCardNumber() +
                   ", dateTime=" + dateTime +
                   '}';

        } else {
            return "Transaction{" +
                   "id=" + id +
                   ", type=" + type +
                   ", amount=" + amount +
                   ", card=" + fromUserCard.getCardNumber() +
                   ", dateTime=" + dateTime +
                   '}';
        }
    }
}
