package net.thumbtack.school.hiring.dto.response;

public class MessageDtoResponse {
    private final String string;

    public MessageDtoResponse(String msg) {
        this.string = msg;
    }

    public String getString() {
        return string;
    }
}

