package com.bettingapp.florian.bettingappv2.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by floriangoeteyn on 31-Mar-16.
 */
public class Bet {

    private String _id;
    private String title;
    private Option optionA;
    private Option optionB;
    private String reward;
    private BetCategory category;
    private String date;
    private String creator;

    public Bet(String title, String optionA, String optionB, String reward, BetCategory category) {
        this.title = title;
        this.optionA = new Option(optionA, creator);
        this.optionB = new Option(optionB, creator);;
        this.reward = reward;
        this.category = category;
    }

    public String getId() {
        return _id;
    }

    public String getTitle() {
        return title;
    }

    public Option getOptionA() {
        return optionA;
    }

    public Option getOptionB() {
        return optionB;
    }

    public String getReward() {
        return reward;
    }

    public BetCategory getCategory() {
        return category;
    }

    public String getCreator() {
        return creator;
    }

    public Date getDate() {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.ENGLISH);
        try {
            return format.parse(date);
        } catch (ParseException|NullPointerException e) {
            return new Date();
        }
    }
}
