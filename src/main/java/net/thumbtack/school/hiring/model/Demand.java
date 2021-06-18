package net.thumbtack.school.hiring.model;

import net.thumbtack.school.hiring.dto.request.DemandDtoRequest;
import net.thumbtack.school.hiring.dto.response.SkillDtoResponse;

import java.util.Objects;

public class Demand {
    private SkillDtoResponse skill;
    private boolean mandatory;

    public Demand(SkillDtoResponse skill, boolean mandatory) {
        this.skill = skill;
        this.mandatory = mandatory;
    }

    public Demand(DemandDtoRequest demandDtoRequest) {
        this.skill = demandDtoRequest.getSkill();
        this.mandatory = demandDtoRequest.isMandatory();
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

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Demand demand = (Demand) o;
        return mandatory == demand.mandatory &&
                Objects.equals(skill, demand.skill);
    }

    @Override
    public int hashCode() {
        return Objects.hash(skill, mandatory);
    }
}
