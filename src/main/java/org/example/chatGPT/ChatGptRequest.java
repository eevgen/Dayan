package main.java.org.example.chatGPT;

public record ChatGptRequest(String model, String prompt, int temperature, int max_tokens) {
}
