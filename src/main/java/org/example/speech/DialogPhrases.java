package main.java.org.example.speech;

import java.util.Random;

public class DialogPhrases {

    public static String sayAgreePhrases() {
        switch ((new Random()).nextInt(3) + 1) {
            case 1:
                return "Your wish is my command sir";
            case 2:
                return "At your command sir";
            case 3:
                return "Your orders are my priority sir";
            case 4:
                return "As you wish sir";
            case 5:
                return "Here it is";
        }
        return "done";
    }

    public String sayAgreeToCalculation(int result) {
        switch ((new Random()).nextInt(4) + 1) {
            case 1:
                return "It is " + result + " sir";
            case 2:
                return "The result is " + result + " sir";
            case 3:
                return "I am not good at math bud the result is " + result + " sir";
            case 4:
                return "The answer is " + result + " sir, I hope you knew it";
            case 5:
                return "My calculations tell me it is " + result + " sir";
        }
        return "" + result;
    }

}
