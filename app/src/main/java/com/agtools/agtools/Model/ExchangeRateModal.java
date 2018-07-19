package com.agtools.agtools.Model;

/**
 * Created by soulsticesoftware on 30-01-2018.
 */

public class ExchangeRateModal {
    String lastUpdate;
    String canadaExchange;
    String mexicoExchange;

    public ExchangeRateModal(){

    }
    public ExchangeRateModal(String lastUpdate, String canadaExchange, String mexicoExchange) {
        this.lastUpdate = lastUpdate;
        this.canadaExchange = canadaExchange;
        this.mexicoExchange = mexicoExchange;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getCanadaExchange() {
        return canadaExchange;
    }

    public void setCanadaExchange(String canadaExchange) {
        this.canadaExchange = canadaExchange;
    }

    public String getMexicoExchange() {
        return mexicoExchange;
    }

    public void setMexicoExchange(String mexicoExchange) {
        this.mexicoExchange = mexicoExchange;
    }
}
