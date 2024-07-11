package io.seoleir.processor;

import io.seoleir.entity.Transaction;
import io.seoleir.entity.UserCreditCard;
import io.seoleir.entity.User;
import io.seoleir.enums.TransactionType;
import io.seoleir.strategy.DepositStrategy;
import io.seoleir.strategy.TransactionStrategy;
import io.seoleir.strategy.TransferStrategy;
import io.seoleir.strategy.WithdrawStrategy;

import java.util.*;
import java.util.regex.Pattern;

public class AtmProcessor {

    private final Map<String, User> users = new HashMap<>();

    private User currentUser = null;

    private final Scanner scanner = new Scanner(System.in);

    private final Map<TransactionType, TransactionStrategy> strategies = new HashMap<>();

    private static final Pattern cardNumberPattern = Pattern.compile("\\d{4} \\d{4} \\d{4} \\d{4}");
    private static final Pattern cardExpirationPattern = Pattern.compile("(0[1-9]|1[0-2])/\\d{2}");

    public AtmProcessor() {
        strategies.put(TransactionType.WITHDRAW, new WithdrawStrategy());
        strategies.put(TransactionType.DEPOSIT, new DepositStrategy());
        strategies.put(TransactionType.TRANSFER, new TransferStrategy());
    }

    private Boolean loggedIn() {
        return currentUser != null;
    }

    public void run() {
        System.out.println("Welcome to ATM simulator");
        System.out.println("To use our ATM, you must be logged in");
        welcome();
    }

    private void welcome() {
        System.out.println("1. Log in");
        System.out.println("2. Sign up");
        System.out.println("3. Exit");
        System.out.println("Enter choice: ");

        int choice = Integer.parseInt(scanner.next());
        while (true) {
            switch (choice) {
                case 1 -> login();
                case 2 -> registerUser();
                case 3 -> {
                    System.out.println("Thank you for using our ATM");
                    System.exit(0);
                }
            }
        }

    }

    private void userMenu() {
        boolean loggedIn = loggedIn();

        while (loggedIn) {
            System.out.println("1. View my cards");
            System.out.println("2. Add card");
            System.out.println("3. Withdraw");
            System.out.println("4. Deposit");
            System.out.println("5. Transfer");
            System.out.println("6. View Transaction History");
            System.out.println("7. Logout");
            System.out.println("Enter choice: ");
            int choice = Integer.parseInt(scanner.next());

            switch (choice) {
                case 1 -> currentUserCards();
                case 2 -> addUserCreditCard();
                case 3 -> executeTransaction(TransactionType.WITHDRAW);
                case 4 -> executeTransaction(TransactionType.DEPOSIT);
                case 5 -> executeTransaction(TransactionType.TRANSFER);
                case 6 -> viewTransactionHistory();
                case 7 -> {
                    loggedIn = false;
                    currentUser = null;
                    System.out.println("Logged out.");
                    welcome();
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void currentUserCards() {
        System.out.println("My cards: ");
        if(currentUser.getUserCard() != null && !currentUser.getUserCard().isEmpty()) {
            List<UserCreditCard> userCards = currentUser.getUserCard();

            for (int i = 0; i < userCards.size(); i++) {
                String cardNumber = userCards.get(i).getCardNumber();
                System.out.println(i + 1 + ") Card: " + cardNumber.substring(0, 4) + " "
                                   + cardNumber.substring(4, 8) + " "
                                   + cardNumber.substring(8, 12) + " "
                                   + cardNumber.substring(12, 16) + " "
                                   + " Balance: " + userCards.get(i).getBalance());
            }
        } else {
            System.out.println("[]");
        }
    }

    // User registration
    private void registerUser() {
        System.out.println("Enter name: ");
        String name = scanner.next();

        System.out.println("Enter phone number: ");
        Long phoneNumber = Long.parseLong(scanner.next());

        System.out.println("Enter login: ");
        String login = scanner.next();

        System.out.println("Enter password: ");
        String password = scanner.next();

        User user = new User(name, phoneNumber, login, password);
        users.put(login, user);
        System.out.println("Successfully registered");
        welcome();
    }

    // User logging in
    private void login() {
        System.out.println("Enter login: ");
        String login = scanner.next();

        System.out.println("Enter password: ");
        String password = scanner.next();

        User foundUser = users.get(login);

        if (foundUser != null) {
            if(foundUser.getPassword().equals(password)) {
                currentUser = foundUser;
                userMenu();
            } else {
                System.err.println("Incorrect password or login");
                welcome();
            }
        } else {
            System.err.println("User not found");
            welcome();
        }
    }

    private void addUserCreditCard() {
        System.out.println("Enter card number: ");
        String cardNumber = scanner.nextLine();

        while (!cardNumberPattern.matcher(cardNumber.trim()).matches()) {
            System.err.println("Enter the card number in the format: ХХХХ ХХХХ ХХХХ ХХХХ");
            cardNumber = scanner.nextLine();
        }

        System.out.println("Enter card expiration date in format xx/xx: ");
        String cardExpiration = scanner.nextLine();
        while (!cardExpirationPattern.matcher(cardExpiration).matches()) {
            System.err.println("Enter card expiration date in format xx/xx: ");
            cardExpiration = scanner.nextLine();
        }

        UserCreditCard userCreditCard = new UserCreditCard(cardNumber.trim().replace(" ", ""), currentUser.getName(), cardExpiration, 0.0);
        currentUser.addCreditCard(userCreditCard);
        System.out.println("Card successfully added to current user");
        userMenu();
    }

    private void viewTransactionHistory() {
        System.out.println("Transaction history: ");
        if (currentUser.getTransactionHistory() != null && !currentUser.getTransactionHistory().isEmpty()) {

            for (Transaction transaction : currentUser.getTransactionHistory()) {
                System.out.println(transaction);
            }
        } else {
            System.out.println("[]");
        }
    }

    private void executeTransaction(TransactionType type) {
        System.out.println("Please select card to perform transaction: ");

        List<UserCreditCard> currentUserCards = currentUser.getUserCard();
        for (int i = 0; i < currentUserCards.size(); i++) {
            System.out.println(i + 1 + ") Card: " + currentUserCards.get(i).getCardNumber() + " Balance: " + currentUserCards.get(i).getBalance());
        }

        int userCardIndex = Integer.parseInt(scanner.next());

        System.out.println("Write amount to perform transaction: ");
        double amount = scanner.nextDouble();

        UserCreditCard usersCardToTransfer = null;
        User userToTransfer = null;
        if (type.equals(TransactionType.TRANSFER)) {
            usersCardToTransfer = transferCardHistory();

            if (usersCardToTransfer == null) {
                userMenu();
            } else {
                userToTransfer = users.get(usersCardToTransfer.getCardHolder());
            }
        }
        TransactionStrategy transactionStrategy = strategies.get(type);
        transactionStrategy.execute(currentUser, currentUserCards.get(userCardIndex - 1), amount, userToTransfer, usersCardToTransfer);
    }

    private UserCreditCard transferCardHistory() {
        UserCreditCard creditCard = null;

        System.out.println("Select the recipient from 2 options: ");
        System.out.println("1) Select a recipient from the transfer history");
        System.out.println("2) Write recipient card number");
        System.out.println("3) Previous");
        System.out.println("Enter option: ");

        int choice = Integer.parseInt(scanner.next());

        if (choice == 1) {
            if (currentUser.getTransactionHistory().isEmpty()) {
                System.out.println("0 - back");
                int code = Integer.parseInt(scanner.next());

                while (code != 0) {
                    System.err.println("Option not found");
                    code = Integer.parseInt(scanner.next());
                }

                return transferCardHistory();
            }

            List<Transaction> userTransactionHistory = currentUser.getTransactionHistory();

            int counter = 1;
            for (Transaction transaction : userTransactionHistory) {
                if (transaction.getToUserCard() != null) {
                    System.out.println(counter + ") Card holder name: " + transaction.getToUserCard().getCardHolder() +
                                       " card number: " + transaction.getToUserCard().getCardNumber());
                    counter++;
                }
            }

            int cardIndexChoice = Integer.parseInt(scanner.next());

            for (Transaction transaction : userTransactionHistory) {
                if (transaction.getToUserCard() != null) {

                    if (cardIndexChoice == counter) {
                        creditCard = transaction.getToUserCard();
                        break;
                    }

                    counter--;
                }
            }
            return creditCard;
        } else if (choice == 2) {
            while (creditCard == null) {
                System.out.println("Write recipient card number: ");
                String cardNumber = scanner.nextLine();

                while (!cardNumberPattern.matcher(cardNumber.trim()).matches()) {
                    System.err.println("Enter the card number in the format: ХХХХ ХХХХ ХХХХ ХХХХ");
                    cardNumber = scanner.nextLine();
                }

                String spaceRemovedCardNumber = cardNumber.trim().replace(" ", "");

                outer: for (Map.Entry<String, User> entry : users.entrySet()) {
                    List<UserCreditCard> userCards = entry.getValue().getUserCard();

                    for (UserCreditCard userCard : userCards) {
                        if (userCard.getCardNumber().equals(spaceRemovedCardNumber)) {
                            creditCard = userCard;
                            break outer;
                        }
                    }
                }

                if (creditCard == null) {
                    System.err.println("Recipient with card number: " + cardNumber + " not found");
                }
            }

            return creditCard;
        } else if (choice == 3) {
            return null;
        } else {
            System.err.println("Option not found");
            return transferCardHistory();
        }
    }
}
