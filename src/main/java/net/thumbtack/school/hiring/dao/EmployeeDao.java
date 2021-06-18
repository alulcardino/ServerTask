package net.thumbtack.school.hiring.dao;


import net.thumbtack.school.hiring.dto.request.ChangeEmployeeDataDtoRequest;
import net.thumbtack.school.hiring.dto.request.ChangeSkillLevelDtoRequest;
import net.thumbtack.school.hiring.exceptions.LabourMarketException;
import net.thumbtack.school.hiring.model.Employee;
import net.thumbtack.school.hiring.model.Skill;

import java.util.List;
import java.util.Set;

public interface EmployeeDao {
    Employee getEmployee(String token) throws LabourMarketException;

    List<Employee> getAllEmployees();

    Employee getRegisterEmployee(String login, String password) throws LabourMarketException;

    void addEmployeeInSortedTree(Employee employee, Skill skill);

    Set<Employee> getEmployeeSetBySkillLevel(String skillNamel, int minLevel);

    void clearSkillDataBase();

    void clearEmployeeDataBase();

    void changeEmployeeData(ChangeEmployeeDataDtoRequest employeeData) throws LabourMarketException;

    boolean employeeIsActive(String token) throws LabourMarketException;

    void madeAccountInactive(String userTokenDtoJson) throws LabourMarketException;

    void madeAccountActive(String token) throws LabourMarketException;

    void addNewSkill(Skill skill, String token) throws LabourMarketException;

    boolean containsSkill(String skillName, String token) throws LabourMarketException;

    void removeSkill(String token, String skillName) throws LabourMarketException;

    void changeSkillLevel(ChangeSkillLevelDtoRequest skillData) throws LabourMarketException;

    int getSkillLevel(String token, String skillName) throws LabourMarketException;
}
