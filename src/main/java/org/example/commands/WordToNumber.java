package main.java.org.example.commands;

import main.java.org.example.speech.DialogPhrases;
import main.java.org.example.speech.Voice;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordToNumber {
    private static final int MIN_NUMBER_OF_VALUES = 2;
    private static final int MIN_NUMBER_OF_SYMBOLS = 1;

    private static final Voice voice = new Voice();
    private static final DialogPhrases dialogPhrases = new DialogPhrases();

    List<String> allowedStrings = Arrays.asList(
            "zero", "one", "two", "three", "four", "five", "six", "seven",
            "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen",
            "fifteen", "sixteen", "seventeen", "eighteen", "nineteen", "twenty",
            "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety",
            "hundred", "thousand", "million", "billion", "trillion"
    );
    public int wordToNumber(String input){
        boolean isValidInput = true;
        int result = 0;
        int finalResult = 0;

        if(input != null && input.length()> 0)
        {
            input = input.replaceAll("-", " ");
            input = input.toLowerCase().replaceAll(" and", " ");
            String[] splittedParts = input.trim().split("\\s+");

            for(String str : splittedParts)
            {
                if(!allowedStrings.contains(str))
                {
                    isValidInput = false;
                    System.out.println("Invalid word found : "+str);
                    break;
                }
            }
            if(isValidInput)
            {
                for(String str : splittedParts)
                {
                    if(str.equalsIgnoreCase("zero")) {
                        result += 0;
                    }
                    else if(str.equalsIgnoreCase("one")) {
                        result += 1;
                    }
                    else if(str.equalsIgnoreCase("two")) {
                        result += 2;
                    }
                    else if(str.equalsIgnoreCase("three")) {
                        result += 3;
                    }
                    else if(str.equalsIgnoreCase("four")) {
                        result += 4;
                    }
                    else if(str.equalsIgnoreCase("five")) {
                        result += 5;
                    }
                    else if(str.equalsIgnoreCase("six")) {
                        result += 6;
                    }
                    else if(str.equalsIgnoreCase("seven")) {
                        result += 7;
                    }
                    else if(str.equalsIgnoreCase("eight")) {
                        result += 8;
                    }
                    else if(str.equalsIgnoreCase("nine")) {
                        result += 9;
                    }
                    else if(str.equalsIgnoreCase("ten")) {
                        result += 10;
                    }
                    else if(str.equalsIgnoreCase("eleven")) {
                        result += 11;
                    }
                    else if(str.equalsIgnoreCase("twelve")) {
                        result += 12;
                    }
                    else if(str.equalsIgnoreCase("thirteen")) {
                        result += 13;
                    }
                    else if(str.equalsIgnoreCase("fourteen")) {
                        result += 14;
                    }
                    else if(str.equalsIgnoreCase("fifteen")) {
                        result += 15;
                    }
                    else if(str.equalsIgnoreCase("sixteen")) {
                        result += 16;
                    }
                    else if(str.equalsIgnoreCase("seventeen")) {
                        result += 17;
                    }
                    else if(str.equalsIgnoreCase("eighteen")) {
                        result += 18;
                    }
                    else if(str.equalsIgnoreCase("nineteen")) {
                        result += 19;
                    }
                    else if(str.equalsIgnoreCase("twenty")) {
                        result += 20;
                    }
                    else if(str.equalsIgnoreCase("thirty")) {
                        result += 30;
                    }
                    else if(str.equalsIgnoreCase("forty")) {
                        result += 40;
                    }
                    else if(str.equalsIgnoreCase("fifty")) {
                        result += 50;
                    }
                    else if(str.equalsIgnoreCase("sixty")) {
                        result += 60;
                    }
                    else if(str.equalsIgnoreCase("seventy")) {
                        result += 70;
                    }
                    else if(str.equalsIgnoreCase("eighty")) {
                        result += 80;
                    }
                    else if(str.equalsIgnoreCase("ninety")) {
                        result += 90;
                    }
                    else if(str.equalsIgnoreCase("hundred")) {
                        result *= 100;
                    }
                    else if(str.equalsIgnoreCase("thousand")) {
                        result *= 1000;
                        finalResult += result;
                        result=0;
                    }
                    else if(str.equalsIgnoreCase("million")) {
                        result *= 1000000;
                        finalResult += result;
                        result=0;
                    }
                    else if(str.equalsIgnoreCase("billion")) {
                        result *= 1000000000;
                        finalResult += result;
                        result=0;
                    }
                    else if(str.equalsIgnoreCase("trillion")) {
                        result *= 1000000000000L;
                        finalResult += result;
                        result=0;
                    }
                }

                finalResult += result;
                result=0;
            }
        }
        return finalResult;
    }

    public void gettingNumbers(String speech) {
        if(testOnNumber(speech)) {
            System.out.println("Number");
            String[] array = speech.split("\\s*(plus|minus|multiply|division)\\s*");

            LinkedList<String> symbolsList = new LinkedList<>();

            Pattern pattern = Pattern.compile("\\b(plus|minus|multiply|division)\\b");
            Matcher matcher = pattern.matcher(speech);
            while (matcher.find()) {
                symbolsList.add(matcher.group(1));
            }
            int calculationResult = wordToNumber(array[0]);

            for (int i = 1; i < array.length; i++) {
                if (symbolsList.getFirst().equalsIgnoreCase("plus")) {
                    calculationResult += wordToNumber(array[i]);
                } else if (symbolsList.getFirst().equalsIgnoreCase("minus")) {
                    calculationResult -= wordToNumber(array[i]);
                } else if (symbolsList.getFirst().equalsIgnoreCase("multiply")) {
                    calculationResult *= wordToNumber(array[i]);
                } else if (symbolsList.getFirst().equalsIgnoreCase("division")) {
                    if(wordToNumber(array[i]) != 0) {
                        calculationResult /= wordToNumber(array[i]);
                    }
                }
                symbolsList.removeFirst();
            }
            voice.say(dialogPhrases.sayAgreeToCalculation(calculationResult));
            System.out.println("Calculation Result: " + calculationResult);
        }
    }

    public boolean testOnNumber(String speech) {
        if(detectNumbers(speech) && detectSymbols(speech)) {
            return true;
        }
        return false;
    }


    public boolean detectSymbols(String speech) {
        Pattern pattern = Pattern.compile("\\b(plus|minus|multiply|division)\\b");
        Matcher matcher = pattern.matcher(speech);
        while (matcher.find()) {
            return true;
        }
        return false;
    }

    public boolean detectNumbers(String speech) {
        AtomicInteger countedNumberWords = new AtomicInteger();
        String[] splitSpeech = speech.split(" ");
        allowedStrings.forEach(number -> {
            for (String speechWord : splitSpeech) {
                if(speechWord.equalsIgnoreCase(number)) {
                    countedNumberWords.getAndIncrement();
                }
            }
        });
        if(countedNumberWords.intValue() >= 2)
            return true;

        return false;
    }

}
