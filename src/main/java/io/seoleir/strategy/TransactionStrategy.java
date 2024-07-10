package io.seoleir.strategy;

import io.seoleir.entity.UserCreditCard;
import io.seoleir.entity.User;

public interface TransactionStrategy {

    void execute(User fromUser, UserCreditCard fromUserCard, Double transactionAmount, User toUser, UserCreditCard toUserCard);
}
