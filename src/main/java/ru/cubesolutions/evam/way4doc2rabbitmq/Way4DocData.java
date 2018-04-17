package ru.cubesolutions.evam.way4doc2rabbitmq;

import java.math.BigDecimal;

/**
 * Created by Garya on 09.02.2018.
 */
public class Way4DocData {

    private String iban;
    private int currency;
    private BigDecimal amount;
    private boolean res;
    private int branch;

    public Way4DocData(String iban, int currency, BigDecimal amount, boolean res, int branch) {
        this.iban = iban;
        this.currency = currency;
        this.amount = amount;
        this.res = res;
        this.branch = branch;
    }

    public String getIban() {
        return iban;
    }

    public int getCurrency() {
        return currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public boolean getRes() {
        return res;
    }

    public int getBranch() {
        return branch;
    }
}
