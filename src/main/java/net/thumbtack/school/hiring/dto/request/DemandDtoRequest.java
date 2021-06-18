package net.thumbtack.school.hiring.dto.request;

import net.thumbtack.school.hiring.dto.response.SkillDtoResponse;

import java.util.Objects;

public class DemandDtoRequest {
    private SkillDtoResponse skill;
    private final boolean mandatory;

    public DemandDtoRequest(SkillDtoResponse skill, boolean mandatory) {
        this.skill = skill;
        this.mandatory = mandatory;
    }

    public DemandDtoRequest(String skillName, int skillLvl, boolean mandatory) {
        this(new SkillDtoResponse(skillName, skillLvl), mandatory);
    }

    public DemandDtoRequest(DemandDtoRequest data) {
        this(data.getSkill(), data.isMandatory());
    }

    public SkillDtoResponse getSkill() {
        return skill;
    }

    public void setSkill(SkillDtoResponse skill) {
        this.skill = skill;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DemandDtoRequest)) return false;
        DemandDtoRequest that = (DemandDtoRequest) o;
        return isMandatory() == that.isMandatory() &&
                Objects.equals(getSkill(), that.getSkill());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSkill(), isMandatory());
    }
}
