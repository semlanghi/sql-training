package com.ververica.sql_training.data_producer.records;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Substitution implements BasketBallRecord{

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private Date eventTime;
    @JsonFormat
    private long playerId;
    @JsonFormat
    private String playerName;

    public Substitution() {
    }

    public Substitution(Date eventTime, long playerId, String playerName) {
        this.eventTime = eventTime;
        this.playerId = playerId;
        this.playerName = playerName;
    }

    @Override
    public Date getEventTime() {
        return eventTime;
    }
}
