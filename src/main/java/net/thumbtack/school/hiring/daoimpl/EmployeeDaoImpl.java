package net.thumbtack.school.hiring.daoimpl;

import net.thumbtack.school.hiring.dao.EmployeeDao;
import net.thumbtack.school.hiring.dto.request.ChangeEmployeeDataDtoRequest;
import net.thumbtack.school.hiring.dto.request.ChangeSkillLevelDtoRequest;
import net.thumbtack.school.hiring.dto.response.SkillDtoResponse;
import net.thumbtack.school.hiring.exceptions.LabourMarketErrorCode;
import net.thumbtack.school.hiring.exceptions.LabourMarketException;
import net.thumbtack.school.hiring.model.Employee;
import net.thumbtack.school.hiring.model.Skill;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EmployeeDaoImpl extends UserDaoImpl implements EmployeeDao {
    @Override
    public Employee getEmployee(String token) throws LabourMarketException {
        try{
            return (Employee) dataBase.getLogInUser(token);
        }catch (LabourMarketException ex){
            throw new LabourMarketException(LabourMarketErrorCode.USER_DOESNT_EXIST);
        }

    }

    @Override
    public List<Employee> getAllEmployees() {
        return dataBase.getAllEmployee();
    }

    @Override
    public Employee getRegisterEmployee(String login, String password) throws LabourMarketException {
        try{
            return (Employee) dataBase.getOneUserOfAll(login, password);
        }catch (LabourMarketException ex){
            throw new LabourMarketException(LabourMarketErrorCode.USER_DOESNT_EXIST);
        }
    }

    @Override
    public void addEmployeeInSortedTree(Employee employee, Skill skill) {
        dataBase.addEmployee(employee, skill);
    }

    @Override
    public Set<Employee> getEmployeeSetBySkillLevel(String skillName, int minLevel) {
        Set<Employee> result = new HashSet<>();
        Skill skill = new Skill(skillName, minLevel);
        for (int i = minLevel + 1; i < 7; i++) {
            result.addAll(dataBase.getEmployee(skill));
            skill.setLevel(i);
        }
        return result;
    }

    @Override
    public void clearSkillDataBase() {

    }

    @Override
    public void clearEmployeeDataBase() {

    }

    public void removeEmployeeSkillFromTree(SkillDtoResponse skillByName, Employee employee) {
        dataBase.getEmployee(new Skill(skillByName)).remove(employee);
    }


    @Override
    public void changeEmployeeData(ChangeEmployeeDataDtoRequest employeeData) throws LabourMarketException {
        Employee em = getEmployee(employeeData.getToken());
        em.setFirstName(employeeData.getName());
        em.setEmail(employeeData.getEmail());
        em.setPassword(employeeData.getPassword());
    }

    @Override
    public boolean employeeIsActive(String token) throws LabourMarketException {
        return getEmployee(token).isActive();
    }

    @Override
    public void madeAccountInactive(String userTokenDtoJson) throws LabourMarketException {
        getEmployee(userTokenDtoJson).setActive(false);
    }

    @Override
    public void madeAccountActive(String token) throws LabourMarketException {
        getEmployee(token).setActive(true);
    }

    @Override
    public void addNewSkill(Skill skill, String token) throws LabourMarketException {
        Employee employee = getEmployee(token);
        employee.addNewSkill(new SkillDtoResponse(skill.getName(), skill.getLevel()));
        addEmployeeInSortedTree(employee, skill);
    }

    @Override
    public boolean containsSkill(String skillName, String token) throws LabourMarketException {
        for (SkillDtoResponse s : getEmployee(token).getSkillsList()) {
            if (skillName.compareTo(s.getName()) == 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void removeSkill(String token, String skillName) throws LabourMarketException {
        Employee employee = getEmployee(token);
        removeEmployeeSkillFromTree(employee.getSkillByName(skillName), employee);
        employee.removeSkill(skillName);
    }

    @Override
    public void changeSkillLevel(ChangeSkillLevelDtoRequest skillData) throws LabourMarketException {
        getEmployee(skillData.getToken()).changeSkillLevel(skillData.getSkillName(), skillData.getNewLevel());
    }

    @Override
    public int getSkillLevel(String token, String skillName) throws LabourMarketException {
        return getEmployee(token).getSkillByName(skillName).getLevel();
    }
}
