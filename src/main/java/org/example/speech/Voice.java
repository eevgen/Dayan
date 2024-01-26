package main.java.org.example.speech;

import com.sun.speech.freetts.VoiceManager;

public class Voice {

    public void say(String text) {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        com.sun.speech.freetts.Voice voice = VoiceManager.getInstance().getVoice("kevin");
        com.sun.speech.freetts.Voice[] voices = VoiceManager.getInstance().getVoices();

        if (voices != null) {
            voice.allocate();
            voice.speak(text);
            voice.deallocate();
        } else {
            System.out.println("Error in getting voices");
        }
    }

}
