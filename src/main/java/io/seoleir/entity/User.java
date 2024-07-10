package io.seoleir.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {

    private UUID id;

    private String name;

    private Long phoneNumber;

    private String login;

    private String password;

    private List<UserCreditCard> userCard;

    private List<Transaction> transactionHistory;

    public User(String name, Long phoneNumber, String login, String password) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.login = login;
        this.password = password;
        this.userCard = new ArrayList<>();
        this.transactionHistory = new ArrayList<>();
    }

    public User(String name, Long phoneNumber, String login, String password, List<UserCreditCard> userCard) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.login = login;
        this.password = password;
        this.userCard = userCard;
        this.transactionHistory = new ArrayList<>();
    }

    // Добавление транзакции юзера к его истории
    public void addTransaction(Transaction transaction) {
        this.transactionHistory.add(transaction);
    }

    // Добавление кредитной карты к картам юзера
    public void addCreditCard(UserCreditCard userCreditCard) {
        this.userCard.add(userCreditCard);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    public void setTransactionHistory(List<Transaction> transactionHistory) {
        this.transactionHistory = transactionHistory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<UserCreditCard> getUserCard() {
        return userCard;
    }

    public void setUserCard(List<UserCreditCard> userCard) {
        this.userCard = userCard;
    }


}
