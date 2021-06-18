package net.thumbtack.school.hiring.dto.response;

import net.thumbtack.school.hiring.exceptions.LabourMarketErrorCode;
import net.thumbtack.school.hiring.exceptions.LabourMarketException;

import java.util.Objects;

public class SkillDtoResponse {
    private final int id;
    private String name;
    private int level;

    public SkillDtoResponse(String name, int lvl) {
        this.name = name;
        this.level = lvl;
        id = -1;
    }

    public SkillDtoResponse(int id, String name, int lvl) {
        this.name = name;
        this.level = lvl;
        this.id = id;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SkillDtoResponse that = (SkillDtoResponse) o;
        return level == that.level &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, level);
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"name\":\"" + name +
                "\", \"level\":" + level +
                "}";
    }
}
