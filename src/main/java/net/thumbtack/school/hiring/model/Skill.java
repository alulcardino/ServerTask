package net.thumbtack.school.hiring.model;

import net.thumbtack.school.hiring.dto.response.SkillDtoResponse;

import java.util.Objects;

public class Skill {
    private int id;
    private String name;
    private int level;

    public Skill(int id, String name, int level) {
        this.id = id;
        this.name = name;
        this.level = level;
    }

    public Skill(String name, int level) {
        this.name = name;
        this.level = level;
        id = -1;
    }

    public Skill(SkillDtoResponse req) {
        this.name = req.getName();
        this.level = req.getLevel();
        id = -1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return
                "{\"name\":\"" + name + '\"' +
                        ",\"level\":" + level +
                        '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skill skill = (Skill) o;
        return Objects.equals(name, skill.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
