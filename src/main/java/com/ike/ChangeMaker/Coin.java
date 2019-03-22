package com.ike.ChangeMaker;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Coin {

    private String name;
    private BigDecimal value;

    public Coin(String name, BigDecimal value) {
        this.name = name;
        this.value = value;
    }

    public Coin(BigDecimal value) {
        StringBuilder coinName = new StringBuilder();
        DecimalFormat df = new DecimalFormat("#,##0.00");
        this.value = value;

        coinName.append("$");
        coinName.append(df.format(value));
        coinName.append(" coin");
        this.name = coinName.toString();
    }

    public String getName() {
        return name;
    }

    public BigDecimal getValue() {
        return value;
    }
}
