package net.thumbtack.school.hiring.dto.request;

public class VacancyServiceDtoRequest {
    private final String token;
    private final String vacancyName;


    public VacancyServiceDtoRequest(String token, String vacancyName) {
        this.token = token;
        this.vacancyName = vacancyName;
    }

    public String getToken() {
        return token;
    }

    public String getVacancyName() {
        return vacancyName;
    }

}
