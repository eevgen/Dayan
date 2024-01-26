package main.java.org.example.chatGPT;

public record ChatGptResponseChoice(
        String text,
        int index,
        Object logprobs,
        String finish_reason
) {
}
