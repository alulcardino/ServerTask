package net.thumbtack.school.hiring.service;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.dao.EmployeeDao;
import net.thumbtack.school.hiring.daoimpl.EmployeeDaoImpl;
import net.thumbtack.school.hiring.dto.request.AddNewSkillDtoRequest;
import net.thumbtack.school.hiring.dto.request.ChangeSkillLevelDtoRequest;
import net.thumbtack.school.hiring.dto.request.SkillServiceDtoRequest;
import net.thumbtack.school.hiring.dto.response.BooleanDtoResponse;
import net.thumbtack.school.hiring.dto.response.EmptyDtoResponse;
import net.thumbtack.school.hiring.dto.response.IntegerDtoResponse;
import net.thumbtack.school.hiring.dto.response.ErrorDtoResponse;
import net.thumbtack.school.hiring.exceptions.LabourMarketException;
import net.thumbtack.school.hiring.model.Skill;
import net.thumbtack.school.hiring.utils.Validator;

public class SkillService {
    private final EmployeeDao employeeDao = new EmployeeDaoImpl();
    private final Gson gson = new Gson();



    public String addNewSkill(String addSkillDtoRequest) {
        try {
            AddNewSkillDtoRequest skillData = UserService.getDtoFromJson(addSkillDtoRequest, AddNewSkillDtoRequest.class);
            Validator.validateAddNewSkillDtoRequest(skillData);
            Skill skill = new Skill(skillData.getSkillName(), skillData.getSkillLevel());
            employeeDao.addNewSkill(skill, skillData.getToken());
            return gson.toJson(new EmptyDtoResponse());
        } catch (LabourMarketException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }
    }

    public String removeSkill(String skillServiceDto) {
        try {
            SkillServiceDtoRequest skillData = UserService.getDtoFromJson(skillServiceDto, SkillServiceDtoRequest.class);
            Validator.validateSkillServiceDtoRequest(skillData);
            employeeDao.removeSkill(skillData.getToken(), skillData.getSkillName());

            return gson.toJson(new EmptyDtoResponse());
        } catch (LabourMarketException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }
    }

    public String changeSkillLevel(String changeSkillLevelDto) {
        try {
            ChangeSkillLevelDtoRequest skillData = UserService.getDtoFromJson(changeSkillLevelDto, ChangeSkillLevelDtoRequest.class);
            Validator.validateChangeSkillLevelDtoRequest(skillData);
            employeeDao.changeSkillLevel(skillData);
            return gson.toJson(new EmptyDtoResponse());
        } catch (LabourMarketException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }
    }

    public String containsSkill(String skillServiceDto) {
        try {
            SkillServiceDtoRequest skillData = UserService.getDtoFromJson(skillServiceDto, SkillServiceDtoRequest.class);
            Validator.validateSkillServiceDtoRequest(skillData);
            return gson.toJson(new BooleanDtoResponse(employeeDao.containsSkill(skillData.getSkillName(), skillData.getToken())));
        } catch (LabourMarketException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }
    }

    public String getSkillLevel(String skillServiceDto) {
        try {
            SkillServiceDtoRequest skillData = UserService.getDtoFromJson(skillServiceDto, SkillServiceDtoRequest.class);
            Validator.validateSkillServiceDtoRequest(skillData);
            return gson.toJson(new IntegerDtoResponse(employeeDao.getSkillLevel(skillData.getToken(), skillData.getSkillName())));
        } catch (LabourMarketException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }
    }

    public void clearDataBase() {
        employeeDao.clearSkillDataBase();
    }
}
