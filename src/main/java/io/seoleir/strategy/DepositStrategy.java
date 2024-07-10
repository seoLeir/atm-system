package io.seoleir.strategy;

import io.seoleir.entity.Transaction;
import io.seoleir.entity.UserCreditCard;
import io.seoleir.entity.User;
import io.seoleir.enums.TransactionOperationType;
import io.seoleir.enums.TransactionType;

public class DepositStrategy implements TransactionStrategy {


    @Override
    public void execute(User fromUser, UserCreditCard fromUserCard, Double transactionAmount, User toUser, UserCreditCard toUserCard) {
        fromUserCard.operate(transactionAmount, TransactionOperationType.PLUS);
        System.out.println("Deposit successful!");
        fromUser.addTransaction(new Transaction(TransactionType.DEPOSIT, transactionAmount, fromUserCard, toUserCard));
    }
}