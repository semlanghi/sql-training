package com.ververica.sql_training.data_producer.records;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Passage implements BasketBallRecord{
    @JsonFormat
    private long senderId;
    @JsonFormat
    private long receiverId;
    @JsonFormat
    private long actionId;
    @JsonFormat
    private long gameId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private Date eventTime;

    public Passage() {
    }

    public Passage(long senderId, long receiverId, long actionId, long gameId, Date eventTime) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.actionId = actionId;
        this.gameId = gameId;
        this.eventTime = eventTime;
    }

    @Override
    public Date getEventTime() {
        return eventTime;
    }
}
