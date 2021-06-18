package net.thumbtack.school.hiring.dto.response;

import java.util.List;
import java.util.Objects;

public class EmployeeDataDtoResponse {
    private final String name;
    private final String email;
    private final List<SkillDtoResponse> skills;

    public EmployeeDataDtoResponse(String name, String email, List<SkillDtoResponse> skills) {
        this.name = name;
        this.email = email;
        this.skills = skills;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<SkillDtoResponse> getSkills() {
        return skills;
    }

    @Override
    public String toString() {
        return "{\"name\":\"" + name + '\"' +
                ",\"email\":\"" + email + '\"' +
                ",\"skills\":" + skills +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeDataDtoResponse that = (EmployeeDataDtoResponse) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(email, that.email) &&
                Objects.equals(skills, that.skills);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, skills);
    }
}
