package com.ike.ChangeMaker;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ChangeMaker {

    private BigDecimal amount;
    private List<Coin> coins;

    public ChangeMaker(String amount, String[] newCoins) {
        this.amount = new BigDecimal(amount);
        coins = new ArrayList<>();

        //hard-coded coin types
        coins.add(new Coin("Quarter", new BigDecimal("0.25")));
        coins.add(new Coin("Dime", new BigDecimal("0.10")));
        coins.add(new Coin("Nickel", new BigDecimal("0.05")));
        coins.add(new Coin("Penny", new BigDecimal("0.01")));

        //adds custom coins if provided
        if(newCoins.length != 0) {
            for(String coin : newCoins) {
                coins.add(new Coin(new BigDecimal(coin)));
            }
        }

        // sorts coins in descending order
        coins.sort((o1, o2) -> {
            if (o1.getValue().compareTo(o2.getValue()) == 0)
                return 0;
            return o1.getValue().compareTo(o2.getValue()) > 0 ? -1 : 1;
        });
    }

    // returns a full summary of starting amount, coin types used and how many, and lists all unused coin types
    public String getChange() {
        DecimalFormat df = new DecimalFormat("#,##0.00"); // for formatting money values, American currency
        BigDecimal zero = new BigDecimal("0"); // easier to understand if statements


        if (amount.compareTo(zero) <= 0) {
            return "No change can be made.";
        }
        if (amount.stripTrailingZeros().scale() > 2) {
            return "Error: Invalid currency format";
        }

        // Summary Header
        StringBuilder coinSummary = new StringBuilder();
        coinSummary.append("Input Value: ");
        coinSummary.append(df.format(amount));
        coinSummary.append("\n\nYour minimum change is:");

        BigDecimal[] coinsAndRemainder;
        List<Coin> unusedCoins = new ArrayList<>();
        for(Coin coinType : coins) {

            if(amount.compareTo(zero) == 0) { // only continues the loop to record coin types that were not used
                unusedCoins.add(coinType);
                continue;
            }
            if (coinType.getValue().stripTrailingZeros().scale() > 2) {
                return "Error: Invalid currency format";
            }

            coinsAndRemainder = amount.divideAndRemainder(coinType.getValue()); // splits the number of coins used and the remaining total into two values

            if(coinsAndRemainder[0].compareTo(zero) != 0) { // if at least one coin of this type is used, record it
                coinSummary.append("\n");
                coinSummary.append(coinType.getName());
                coinSummary.append(": ");
                coinSummary.append(coinsAndRemainder[0].stripTrailingZeros());
                amount = coinsAndRemainder[1];
            }
            else {
                unusedCoins.add(coinType);
            }
        }

        // ending summary of leftover coins
        coinSummary.append("\n\n(Unused Coins: ");
        if(unusedCoins.isEmpty()) {
            coinSummary.append("None!");
        }
        else {
            coinSummary.append(unusedCoins.remove(0).getName());
            for(Coin coin : unusedCoins) {
                coinSummary.append(", ");
                coinSummary.append(coin.getName());
            }
        }
        coinSummary.append(")");


        return coinSummary.toString();
    }
}
