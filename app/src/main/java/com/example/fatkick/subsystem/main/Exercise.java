package com.example.fatkick.subsystem.main;

public class Exercise {
    private String type;
    private Double duration; //duration in minutes


    //constructor
    public Exercise(String type, Double duration) {
        this.type = type;
        this.duration = duration;
    }


    //methods
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getDuration() {
        return duration;
    }


    public void setDuration(Double duration) {
        this.duration = duration;
    }
}
