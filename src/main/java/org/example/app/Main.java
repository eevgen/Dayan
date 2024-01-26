package main.java.org.example.app;

import java.io.IOException;
import java.math.BigDecimal;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import main.java.org.example.chatGPT.ChatGPTConnector;
import main.java.org.example.speech.DialogPhrases;
import main.java.org.example.commands.WordToNumber;

public class Main{

        private final WordToNumber wordToNumber = new WordToNumber();

        public Main() {

                LiveSpeechRecognizer recognizer;

                Configuration configuration = new Configuration();
                configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
                configuration.setDictionaryPath("src\\main\\resources\\8612.dic");
                configuration.setLanguageModelPath("src\\main\\resources\\8612.lm");

                try {
                        recognizer = new LiveSpeechRecognizer(configuration);
                        recognizer.startRecognition(true);

                        SpeechResult speechResult = null;

                        while((speechResult = recognizer.getResult()) != null) {
                                String voiceCommand = speechResult.getHypothesis();
                                System.out.println("You said: " + voiceCommand);

                                wordToNumber.gettingNumbers(voiceCommand.toLowerCase());


//                                String startPhrase = "Increase Computer Volume By";
//                                String startPhraseToUpperCase = startPhrase.toUpperCase();
//                                String numberFromVoice = voiceCommand.replace("Increase Computer Volume By ".toUpperCase(), "");
//                                boolean containsNumber = voiceCommand.contains(startPhraseToUpperCase);
//                                int number = wordToNumber.wordToNumber(numberFromVoice.toLowerCase());
//
//
//                                System.out.println(startPhraseToUpperCase);
//                                System.out.println(voiceCommand);
//                                if(containsNumber) {
//                                        System.out.println("YES");
//                                        System.out.println(numberFromVoice);
//                                        System.out.println(number);
//                                } else {
//                                        System.out.println("NOT");
//                                }


                                makingTasksWithMethods(voiceCommand, "Decrease Computer Volume By".toUpperCase(), new Runnable() {
                                        @Override
                                        public void run() {
                                                String numberFromVoice = voiceCommand.replace("Decrease Computer Volume By ".toUpperCase(), "");
                                                int number = wordToNumber.wordToNumber(numberFromVoice.toLowerCase());
                                                setSystemVolume(number, VolumeSetter.DECREASE);
                                        }
                                });
                                makingTasksWithMethods(voiceCommand, "Increase Computer Volume By".toUpperCase(), new Runnable() {
                                        @Override
                                        public void run() {
                                                String numberFromVoice = voiceCommand.replace("Increase Computer Volume By ".toUpperCase(), "");
                                                int number = wordToNumber.wordToNumber(numberFromVoice.toLowerCase());
                                                setSystemVolume(number, VolumeSetter.INCREASE);

                                        }
                                });
                                makingTasksWithCmdCommand(voiceCommand, "Open Chrome", "cmd.exe /c start chrome");
                                makingTasksWithCmdCommand(voiceCommand, "Close Chrome", "cmd.exe /c taskkill /IM chrome.exe /F");
                                makingTasksWithCmdCommand(voiceCommand, "Highest Computer Volume", "cmd.exe /c C:\\Users\\PC\\Documents\\nircmd.exe setsysvolume 65535");
                                //to choose how many windows we want to close
                        }

                } catch (IOException e) {
                        throw new RuntimeException(e);
                }


        }


        public static void main (String[]args){
                new Main();
//                try {
//                        System.out.println(ChatGPTConnector.chatGPT("How are you"));
//                } catch (Exception e) {
//                        throw new RuntimeException(e);
//                }
        }
        public void makingTasksWithMethods(String voiceCommandSaidByPerson, String voiceCommandForCompetingCommand, Runnable code) {
                if(voiceCommandSaidByPerson.contains(voiceCommandForCompetingCommand)) {
                        code.run();
                        say(DialogPhrases.sayAgreePhrases());
                }
        }
        public void makingTasksWithCmdCommand(String voiceCommandSaidByPerson, String voiceCommandForCompetingCommand, String command) {

                try {
                        if(voiceCommandSaidByPerson.equalsIgnoreCase(voiceCommandForCompetingCommand)) {
                                ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
                                Process process = processBuilder.start();
                                process.waitFor();
                                say(DialogPhrases.sayAgreePhrases());
                        }
                } catch (IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                }
        }


        private int extractNumber(String text) {
                String numberStr = text.replaceAll("[^\\d]", "");
                return numberStr.isEmpty() ? 0 : Integer.parseInt(numberStr);
        }

        public void say(String text) {
                System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
                Voice voice = VoiceManager.getInstance().getVoice("kevin");
                Voice[] voices = VoiceManager.getInstance().getVoices();

                if (voices != null) {
                        voice.allocate();
                        voice.speak(text);
                        voice.deallocate();
                } else {
                        System.out.println("Error in getting voices");
                }
        }

        public void setSystemVolume(int volume, VolumeSetter volumeSetter) {
                try {
                        String symbol = volumeSetter.getSymbol();
                        String command = "cmd.exe /c C:\\Users\\PC\\Documents\\nircmd.exe changesysvolume " + symbol +
                                (new BigDecimal(volume)).multiply(new BigDecimal(655.35));
                        ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
                        Process process = processBuilder.start();
                        int exitCode = process.waitFor();

                        if (exitCode == 0) {
                                System.out.println("Command executed successfully.");
                        } else {
                                System.out.println("Command failed with exit code: " + exitCode);
                        }
                } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                }
        }

        public enum VolumeSetter{

                INCREASE {
                        public String getSymbol() {
                                return "+";
                        }
                },
                DECREASE {
                        public String getSymbol() {
                                return "-";
                        }
                };

                public abstract String getSymbol();
        }


}
