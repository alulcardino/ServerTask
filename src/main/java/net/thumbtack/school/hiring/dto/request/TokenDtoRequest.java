package net.thumbtack.school.hiring.dto.request;

public class TokenDtoRequest {
    private final String token;

    public TokenDtoRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
