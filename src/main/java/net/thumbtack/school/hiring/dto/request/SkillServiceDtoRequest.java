package net.thumbtack.school.hiring.dto.request;

public class SkillServiceDtoRequest {
    private final String token;
    private final String skillName;

    public SkillServiceDtoRequest(String token, String skillName) {
        this.token = token;
        this.skillName = skillName;
    }

    public String getToken() {
        return token;
    }

    public String getSkillName() {
        return skillName;
    }

}
