package io.seoleir.strategy;

import io.seoleir.entity.Transaction;
import io.seoleir.entity.UserCreditCard;
import io.seoleir.entity.User;
import io.seoleir.enums.TransactionOperationType;
import io.seoleir.enums.TransactionType;

public class WithdrawStrategy implements TransactionStrategy {

    @Override
    public void execute(User fromUser, UserCreditCard fromUserCard, Double transactionAmount, User toUser, UserCreditCard toUserCard) {
        Double userCardBalance = fromUserCard.getBalance();

        if (userCardBalance >= transactionAmount) {
            fromUserCard.operate(transactionAmount, TransactionOperationType.MINUS);
            fromUser.addTransaction(new Transaction(TransactionType.WITHDRAW, transactionAmount, fromUserCard,null));

            System.out.println("Withdrawal successful!");
        } else {
            System.err.println("Insufficient funds");
        }
    }
}
