package net.thumbtack.school.hiring.model;

import net.thumbtack.school.hiring.dto.request.DemandDtoRequest;
import net.thumbtack.school.hiring.dto.response.SkillDtoResponse;
import net.thumbtack.school.hiring.dto.response.VacancyDtoResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Vacancy {
    private final int id;
    private String postName;
    private int salary;
    private List<DemandDtoRequest> demands;
    private boolean isActive;

    public Vacancy(int id, String postName, int salary, List<DemandDtoRequest> demands, boolean isActive) {
        this.id = id;
        this.postName = postName;
        this.salary = salary;
        this.demands = demands;
        this.isActive = isActive;
    }

    public Vacancy(String postName, int salary, List<DemandDtoRequest> demands) {
        this.postName = postName;
        this.salary = salary;
        this.demands = demands;
        this.isActive = true;
        id = -1;
    }

    public Vacancy(int id, String postName, int salary, boolean isActive) {
        this(id, postName, salary, new ArrayList<>(), isActive);
    }

    public Vacancy(VacancyDtoResponse data) {
        this(data.getPostName(), data.getSalary(), data.getDemands());
    }

    public Vacancy(Vacancy data) {
        this.id = data.getId();
        this.postName = data.getPostName();
        this.salary = data.getSalary();
        this.isActive = data.isActive;
        this.demands = new ArrayList<>();
        for (DemandDtoRequest d : data.getDemands()) {
            demands.add(new DemandDtoRequest(d));
        }

    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public List<DemandDtoRequest> getDemands() {
        return demands;
    }

    public void setDemands(List<DemandDtoRequest> demands) {
        this.demands = demands;
    }

    public boolean getActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getId() {
        return id;
    }

    public List<SkillDtoResponse> getAllSkills() {
        List<SkillDtoResponse> list = new ArrayList<>();
        for (DemandDtoRequest d : demands) {
            list.add(d.getSkill());
        }
        return list;
    }

    public SkillDtoResponse getSkill(String name) {
        for (SkillDtoResponse s : getAllSkills()) {
            if (s.getName().compareTo(name) == 0) {
                return s;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Vacancy{" +
                "postName='" + postName + '\'' +
                ", salary=" + salary +
                ", demands=" + demands +
                ", isActive=" + isActive +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vacancy vacancy = (Vacancy) o;
        return postName.compareTo(vacancy.postName) == 0 &&
                salary == vacancy.salary &&
                demands.equals(vacancy.demands) &&
                isActive == vacancy.isActive;
    }

    @Override
    public int hashCode() {
        return Objects.hash(postName, salary, demands, isActive);
    }

}
