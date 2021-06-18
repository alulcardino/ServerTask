package net.thumbtack.school.hiring.dto.request;

public class ChangeSkillLevelDtoRequest {
    private final String token;
    private final String skillName;
    private final int newLevel;

    public ChangeSkillLevelDtoRequest(String token, String skillName, int newLevel) {
        this.token = token;
        this.skillName = skillName;
        this.newLevel = newLevel;
    }

    public String getToken() {
        return token;
    }

    public String getSkillName() {
        return skillName;
    }

    public int getNewLevel() {
        return newLevel;
    }

}
