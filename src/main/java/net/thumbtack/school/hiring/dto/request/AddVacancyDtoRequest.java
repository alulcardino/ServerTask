package net.thumbtack.school.hiring.dto.request;

import net.thumbtack.school.hiring.dto.response.SkillDtoResponse;
import net.thumbtack.school.hiring.exceptions.LabourMarketErrorCode;
import net.thumbtack.school.hiring.exceptions.LabourMarketException;

import java.util.ArrayList;
import java.util.List;

public class AddVacancyDtoRequest {
    private final String token;
    private final String postName;
    private final Integer salary;
    private final List<DemandDtoRequest> demands;

    public AddVacancyDtoRequest(String token, String postName, Integer salary) {
        this.token = token;
        this.postName = postName;
        this.salary = salary;
        this.demands = new ArrayList<>();
    }

    public String getToken() {
        return token;
    }

    public String getPostName() {
        return postName;
    }

    public Integer getSalary() {
        return salary;
    }

    public List<DemandDtoRequest> getDemands() {
        return demands;
    }

    public void addDemand(String skillName, int skillLevel, boolean mandatory) throws LabourMarketException {
        if (skillName == null || "".compareTo(skillName) == 0) {
            throw new LabourMarketException(LabourMarketErrorCode.INCORRECT_SKILL);
        }
        if (skillLevel < 1 || skillLevel > 5) {
            throw new LabourMarketException(LabourMarketErrorCode.INCORRECT_LEVEL);
        }
        demands.add(new DemandDtoRequest(new SkillDtoResponse(skillName, skillLevel), mandatory));
    }

}
