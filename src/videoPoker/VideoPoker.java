package videoPoker;

import com.sun.tools.javadoc.Start;

import java.util.*;

import static videoPoker.Poker.*;

/**
 * Main Game class with main methods
 */

public class VideoPoker {

    public static void main(String[] args) {

        System.out.println("\n === Welcome to Video Poker Game! === \n ------------------------------------ \n\n");

        // Create deck of cards
        Deck dealerDeck = new Deck();
        dealerDeck.populateDeck();
        dealerDeck.shuffle();
        System.out.println("DEALER DECK of CARDS: \n");
        System.out.println(dealerDeck);

        // Create a game deck of cards
        Deck gameDeck = new Deck();
        double playerFunds = 100.00;
        double playerBet = 10.00;
        boolean gameContinuous = false;

        Scanner playerInput = new Scanner(System.in);

        /*      GAME LOOP
        *   Every time the player has a turn
        *   it's gonna run through the loop once,
        *   until player has a money
        */

        while (true) {
            if (gameContinuous == false) {
                System.out.println("Would you like to start the game: YES(1) NO(2) ?");
                int response = playerInput.nextInt();
                    if (response == 2) {
                        gameContinuous = false;
                        break;
                    }
            }
            gameContinuous = true;

            // After player desided to play, player fund decreasing by bet amount of €
            System.out.println("You have €" + playerFunds);
            if (playerBet > playerFunds) {
                System.out.println("You can't bet more than you have. Game Over :(");
                break;
            }

            boolean endRound = false;

            // Start dealing - Game Deck (Five cards)
            for (int i = 0; i < 5; i++) {
                gameDeck.drawCard(dealerDeck);
            }

            while (playerFunds > playerBet) {
                playerFunds -= playerBet;
                System.out.println("Your funds after bet €" + playerFunds);
                // Printing out the unique 5 cards
                System.out.println("Dispensed 5 Cards : ");
                System.out.print(gameDeck);

                System.out.println("Enter number of cards you want to hold: ");
                int holdArrayLength = playerInput.nextInt();
                Integer[] holdArray = new Integer[holdArrayLength]; // TODO charArr?

                if (holdArrayLength < 5) {
                    System.out.println("Enter the card's positions (from 1 to 5) you want to hold: ");
                    for (int i = 0; i < holdArray.length; i++) {
                        System.out.print(i+1 + ". ");
                        holdArray[i] = playerInput.nextInt();
                    }
                } else {
                    for (int i = 0; i < 5; i++) {
                        holdArray[i] = i+1;
                    }
                }

                System.out.println("\nHere is the position's of Cards you wanna hold: ");
                for (int num : holdArray) {
                    System.out.print(num + " ");
                }

                List<Integer> cardsPositionsToChange = new ArrayList<>(Arrays.asList(1,2,3,4,5));
                List<Integer> selectedCardsPositions = new ArrayList<>(Arrays.asList(holdArray));
                Collections.sort(selectedCardsPositions);
                cardsPositionsToChange.removeAll(selectedCardsPositions);
                Collections.reverse(cardsPositionsToChange);

                if (holdArray.length < 5) {
                    System.out.println("\nHere is the position's of Cards you wanna change: ");
                    for (Integer num : cardsPositionsToChange) {
                        System.out.print(num + " ");
                    }

                    // Throw from gameDeck not not hold cards
                    dealerDeck.backToDeck(cardsPositionsToChange, gameDeck);
                    dealerDeck.shuffle();
                }

                // Add to game gameDeck randomly generated not hold cards
                for (int i = 0; i < cardsPositionsToChange.size(); i++) {
                    gameDeck.drawCard(dealerDeck);
                }

                gameDeck.shuffle();
                System.out.println("\n\nFinally dispenced cards:");
                for (Card card : gameDeck.getCards()) {
                    System.out.print(card + " " + " ");
                }
                System.out.println("\n======================================== \n");
                Poker poker = new Poker();
                boolean royalFlush = poker.isRoyalFlush(gameDeck);
                boolean straightFlush = poker.isStraightFlush(gameDeck);
                boolean fourKind = poker.isFourKind(gameDeck);
                boolean fullHouse = poker.isFullHouse(gameDeck);
                boolean flush = poker.isFlush(gameDeck);
                boolean straight = poker.isStraight(gameDeck);
                boolean threeKind = poker.isThreeKind(gameDeck);
                boolean twoPair = poker.isTwoPair(gameDeck);
                boolean pair = poker.isPair(gameDeck);
                Card higestCard = poker.higestCard(gameDeck);

                if (royalFlush) {
                    playerFunds += ROYAL_FLUSH;
                    System.out.println("Royal Flush !!! You got + " + ROYAL_FLUSH);

                } else if (straightFlush) {
                    playerFunds += STRAIGHT_FLUSH;
                    System.out.println("Straight Flush !!! You got + " + STRAIGHT_FLUSH);

                } else if (fourKind) {
                    playerFunds += FOUR_OF_KIND;
                    System.out.println("Four of Kind !!! You got + " + FOUR_OF_KIND);

                } else if (fullHouse) {
                    playerFunds += FULL_HOUSE;
                    System.out.println("Full House !!! You got + " + FULL_HOUSE);
                } else if (flush) {
                    playerFunds += FLUSH;
                    System.out.println("Flush !!! You got + " + FLUSH);
                } else if (straight) {
                    playerFunds += STRAIGHT;
                    System.out.println("Straight !!! You got + " + FLUSH);
                } else if (threeKind) {
                    playerFunds += THREE_OF_KIND;
                    System.out.println("Three of a Kind !!! You got + " + THREE_OF_KIND);
                } else if (twoPair) {
                    playerFunds += TWO_PAIRS;
                    System.out.println("Two Pairs !!! You got + " + TWO_PAIRS);
                } else if (pair) {
                    playerFunds += PAIR;
                    System.out.println("Pair !!! You got + " + PAIR);
                } else {
                    System.out.println("Your highest Card is: " + higestCard.toString());
                }

                System.out.println("Your funds now: " + playerFunds);
                System.out.println("\n\nWould you like to spin again (1)Yes (2)No");
                int response = playerInput.nextInt();
                if (response == 1) {
                    gameContinuous = true;
                } else {
                    gameContinuous = false;
                    break;
                }
            }
        }

        if (gameContinuous == false) {
            System.out.println("It's up to you, come next time.");
        } else {
            System.out.println(" Game over! You are out of money. :( ");
        }

    }
}
