package com.bettingapp.florian.bettingappv2.model;

/**
 * Created by floriangoeteyn on 31-Mar-16.
 */
public class Bet {

    private String title;
    private Option optionA;
    private Option optionB;
    private String reward;
    private BetCategory category;
    private String creator;

    public Bet(String title, String optionA, String optionB, String reward, BetCategory category) {
        this.title = title;
        this.optionA = new Option(optionA, creator);
        this.optionB = new Option(optionB, creator);;
        this.reward = reward;
        this.category = category;
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
}
