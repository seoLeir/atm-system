package io.seoleir.strategy;

import io.seoleir.entity.Transaction;
import io.seoleir.entity.UserCreditCard;
import io.seoleir.entity.User;
import io.seoleir.enums.TransactionOperationType;
import io.seoleir.enums.TransactionType;

import java.util.Map;

public class TransferStrategy implements TransactionStrategy {

    @Override
    public void execute(User fromUser, UserCreditCard fromUserCard, Double transactionAmount, User toUser, UserCreditCard toUserCard) {
        Double userCardBalance = fromUserCard.getBalance();

        if (userCardBalance >= transactionAmount) {
            fromUserCard.operate(transactionAmount, TransactionOperationType.MINUS);
            toUserCard.operate(transactionAmount, TransactionOperationType.PLUS);

            fromUser.addTransaction(new Transaction(TransactionType.TRANSFER, transactionAmount, fromUserCard, toUserCard));
            toUser.addTransaction(new Transaction(TransactionType.TRANSFER, transactionAmount, fromUserCard, toUserCard));

            System.out.println("Transfer successful!");
        } else {
            System.err.println("Insufficient funds");
        }
    }
}
