package net.thumbtack.school.hiring.dto.response;

import net.thumbtack.school.hiring.dto.request.DemandDtoRequest;
import net.thumbtack.school.hiring.exceptions.LabourMarketErrorCode;
import net.thumbtack.school.hiring.exceptions.LabourMarketException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VacancyDtoResponse {
    private final int id;
    private final String postName;
    private final Integer salary;
    private final List<DemandDtoRequest> demands;
    private boolean isActive;

    public VacancyDtoResponse(int id, String postName, Integer salary, boolean isActive) {
        this.postName = postName;
        this.salary = salary;
        this.demands = new ArrayList<>();
        this.isActive = isActive;
        this.id = id;
    }

    public VacancyDtoResponse(int id, String postName, Integer salary, List<DemandDtoRequest> demands, boolean isActive) {
        this.postName = postName;
        this.salary = salary;
        this.demands = demands;
        this.isActive = isActive;
        this.id = id;
    }

    public VacancyDtoResponse(VacancyDtoResponse data) {
        this(data.getId(), data.getPostName(), data.getSalary(), data.getDemands(), data.isActive());
    }

    public VacancyDtoResponse(String postName, Integer salary, List<DemandDtoRequest> demands) {
        this.postName = postName;
        this.salary = salary;
        this.demands = demands;
        this.isActive = true;
        id = -1;
    }

    public VacancyDtoResponse(Integer salary, String postName, List<DemandDtoRequest> demands, boolean isActive) {
        this.postName = postName;
        this.salary = salary;
        this.demands = demands;
        this.isActive = isActive;
        id = -1;
    }

    public String getPostName() {
        return postName;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getId() {
        return id;
    }

    public Integer getSalary() {
        return salary;
    }

    public List<DemandDtoRequest> getDemands() {
        return demands;
    }

    public List<SkillDtoResponse> getAllSkills() {
        List<SkillDtoResponse> list = new ArrayList<>();
        for (DemandDtoRequest d : demands) {
            list.add(d.getSkill());
        }
        return list;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VacancyDtoResponse that = (VacancyDtoResponse) o;
        return isActive == that.isActive &&
                Objects.equals(postName, that.postName) &&
                Objects.equals(salary, that.salary) &&
                Objects.equals(demands, that.demands);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postName, salary, demands, isActive);
    }
}
