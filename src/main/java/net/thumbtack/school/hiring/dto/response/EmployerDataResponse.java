package net.thumbtack.school.hiring.dto.response;

public class EmployerDataResponse {
    private final String name;
    private final String email;
    private final String companyName;
    private final String address;

    public EmployerDataResponse(String name, String email, String companyName, String address) {
        this.name = name;
        this.email = email;
        this.companyName = companyName;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getAddress() {
        return address;
    }
}
