package net.thumbtack.school.hiring.dto.request;

public class AddNewSkillDtoRequest {
    private final String token;
    private final String skillName;
    private final int skillLevel;

    public AddNewSkillDtoRequest(String token, String skillName, int skillLevel) {
        this.token = token;
        this.skillName = skillName;
        this.skillLevel = skillLevel;
    }

    public String getToken() {
        return token;
    }

    public String getSkillName() {
        return skillName;
    }

    public int getSkillLevel() {
        return skillLevel;
    }

}
