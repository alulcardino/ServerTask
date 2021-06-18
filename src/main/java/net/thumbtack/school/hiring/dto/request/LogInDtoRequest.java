package net.thumbtack.school.hiring.dto.request;

public class LogInDtoRequest {
    private final String login;
    private final String password;

    public LogInDtoRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

}
