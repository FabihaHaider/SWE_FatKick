package com.example.fatkick.subsystem.goal_setting;
import com.example.fatkick.subsystem.authenticator.User;

public class FinalGoalManager {
    private User user;
    private FinalGoal finalGoal;


    public FinalGoalManager(User user, FinalGoal finalGoal) {
        this.user = user;
        this.finalGoal = finalGoal;
    }

    public FinalGoalManager(User user)
    {
        this.user = user;
    }

    public double calculateBMI() {
        double BMI = ( finalGoal.getWeight() / (user.getHeight() * user.getHeight()) ) * 10000;
        return BMI;
    }


    public String suggestGoal()
    {
        double lower_bound = (16 * user.getHeight() * user.getHeight()) /10000;
        double upper_bound = (30 * user.getHeight() * user.getHeight()) /10000;

        return Double.toString(lower_bound) + " kg to " + (user.getWeight() - 1);
    }

    public String isGoalSafe()
    {
        double BMI = calculateBMI();

        if(user.getWeight() <= finalGoal.getWeight())
            return "User can only lose weight";
        else if(BMI > 16 && BMI < 30)
            return "safe goal";
        else if(BMI <= 16)
            return "underweight";
        else
            return "overweight";


    }
}
