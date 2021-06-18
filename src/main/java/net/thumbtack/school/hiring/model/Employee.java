package net.thumbtack.school.hiring.model;

import net.thumbtack.school.hiring.dto.request.RegisterEmployeeDtoRequest;
import net.thumbtack.school.hiring.dto.response.SkillDtoResponse;
import net.thumbtack.school.hiring.exceptions.LabourMarketException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Employee extends User {
    private final List<SkillDtoResponse> skillsList;
    private final int id;
    private boolean isActive;

    public Employee(String firstName, String email, String login, String password, int id, boolean isActive, List<SkillDtoResponse> skillsList) {
        super(firstName, email, login, password);
        this.id = id;
        this.isActive = isActive;
        this.skillsList = skillsList;
    }

    public Employee(String firstName, String email, String login, String password, int id, int isActive, List<SkillDtoResponse> skillsList) {
        super(firstName, email, login, password);
        this.id = id;
        this.isActive = isActive != 0;
        this.skillsList = skillsList;
    }

    public Employee(String firstName, String email, String login, String password) {
        super(firstName, email, login, password);
        isActive = true;
        skillsList = new ArrayList<>();
        id = -1;
    }

    public Employee(String firstName, String email, String login, String password, int id, boolean isActive) {
        super(firstName, email, login, password);
        this.isActive = isActive;
        skillsList = new ArrayList<>();
        this.id = id;
    }

    public Employee(RegisterEmployeeDtoRequest request) {
        super(request.getFirstName(), request.getEmail(), request.getLogin(), request.getPassword());
        isActive = true;
        skillsList = new ArrayList<>();
        id = -1;
    }

    public Employee(Employee registerEmployee) {
        super(registerEmployee.getFirstName(), registerEmployee.getEmail(), registerEmployee.getLogin(), registerEmployee.getPassword());
        this.id = registerEmployee.getId();
        this.isActive = registerEmployee.isActive();
        this.skillsList = registerEmployee.getSkillsList();
    }


    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<SkillDtoResponse> getSkillsList() {
        return skillsList;
    }

    public SkillDtoResponse getSkillByName(String skillName) {
        for (SkillDtoResponse s : skillsList) {
            if (s.getName().compareTo(skillName) == 0) {
                return s;
            }
        }
        return null;
    }

    public void changeSkillLevel(String skillName, int newLvl) {
        SkillDtoResponse s = getSkillByName(skillName);
        if (s != null) {
            s.setLevel(newLvl);
        }

    }

    public void removeSkill(String skillName) {
        for (SkillDtoResponse s : skillsList) {
            if (s.getName().compareTo(skillName) == 0) {
                skillsList.remove(s);
            }
        }
    }

    public void addNewSkill(SkillDtoResponse newSkill) throws LabourMarketException {
        skillsList.add(newSkill);
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        if (!super.equals(o)) return false;
        Employee employee = (Employee) o;
        return isActive() == employee.isActive() &&
                Objects.equals(getSkillsList(), employee.getSkillsList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), isActive(), getSkillsList());
    }
}
