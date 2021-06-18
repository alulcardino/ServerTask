package net.thumbtack.school.hiring.dto.request;

public class ChangeEmployeeDataDtoRequest {
    private final String token;
    private final String name;
    private final String email;
    private final String password;

    public ChangeEmployeeDataDtoRequest(String token, String name, String email, String password) {
        this.token = token;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
