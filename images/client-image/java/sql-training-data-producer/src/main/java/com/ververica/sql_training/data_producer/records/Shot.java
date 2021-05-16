package com.ververica.sql_training.data_producer.records;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Shot implements  BasketBallRecord{

    @JsonFormat
    private long playerId;
    @JsonFormat
    private boolean isMade;
    @JsonFormat
    private long actionId;
    @JsonFormat
    private long gameId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private Date eventTime;

    public Shot() {
    }

    public Shot(long playerId, boolean isMade, long actionId, long gameId, Date eventTime) {
        this.playerId = playerId;
        this.isMade = isMade;
        this.actionId = actionId;
        this.gameId = gameId;
        this.eventTime = eventTime;
    }

    @Override
    public Date getEventTime() {
        return eventTime;
    }
}
