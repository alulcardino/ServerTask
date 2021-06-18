package net.thumbtack.school.hiring.utils;

import net.thumbtack.school.hiring.dto.request.*;
import net.thumbtack.school.hiring.dto.response.SkillDtoResponse;
import net.thumbtack.school.hiring.dto.response.VacancyDtoResponse;
import net.thumbtack.school.hiring.exceptions.LabourMarketErrorCode;
import net.thumbtack.school.hiring.exceptions.LabourMarketException;

public class Validator {

    public static void validateVacancy(VacancyDtoResponse vacancyDtoResponse) throws LabourMarketException {
        if (vacancyDtoResponse.getPostName() == null || "".equals(vacancyDtoResponse.getPostName())) {
            throw new LabourMarketException(LabourMarketErrorCode.INCORRECT_VACANCY);
        }
        if (vacancyDtoResponse.getSalary() <= 0) {
            throw new LabourMarketException(LabourMarketErrorCode.INCORRECT_MONEY);
        }
        if (vacancyDtoResponse.getDemands().isEmpty()) {
            throw new LabourMarketException(LabourMarketErrorCode.INCORRECT_DEMANDS);
        }
        for (DemandDtoRequest d : vacancyDtoResponse.getDemands()) {
            if (d.getSkill().getName() == null || d.getSkill().getName().length() == 0) {
                throw new LabourMarketException(LabourMarketErrorCode.INCORRECT_DEMAND);
            }
            if (d.getSkill().getLevel() < 1 || d.getSkill().getLevel() > 5) {
                throw new LabourMarketException(LabourMarketErrorCode.INCORRECT_LEVEL);
            }
        }
    }

    public static void validateSkill(SkillDtoResponse skillDtoResponse) throws LabourMarketException {
        if (skillDtoResponse.getName() == null || "".compareTo(skillDtoResponse.getName()) == 0) {
            throw new LabourMarketException(LabourMarketErrorCode.INCORRECT_SKILL);
        }
        if (skillDtoResponse.getLevel() < 1 || skillDtoResponse.getLevel() > 5) {
            throw new LabourMarketException(LabourMarketErrorCode.INCORRECT_LEVEL);
        }
    }

    public static void validateTokenDto(TokenDtoRequest userTokenDtoJson) throws LabourMarketException {
        if (userTokenDtoJson == null) {
            throw new LabourMarketException(LabourMarketErrorCode.INCORRECT_JSON);
        }
        if (userTokenDtoJson.getToken() == null || "".compareTo(userTokenDtoJson.getToken()) == 0) {
            throw new LabourMarketException(LabourMarketErrorCode.INCORRECT_JSON);
        }
    }

    public static void validateChangeEmployeeDataDto(ChangeEmployeeDataDtoRequest dtoRequest) throws LabourMarketException {
        if (dtoRequest == null) {
            throw new LabourMarketException(LabourMarketErrorCode.INCORRECT_JSON);
        }
        if (dtoRequest.getName() == null || "".equals(dtoRequest.getName())) {
            throw new LabourMarketException(LabourMarketErrorCode.INCORRECT_FIRSTNAME);
        }
        if (dtoRequest.getPassword() == null || "".equals(dtoRequest.getPassword())) {
            throw new LabourMarketException(LabourMarketErrorCode.INCORRECT_PASSWORD);
        }
        if (dtoRequest.getEmail() == null || "".equals(dtoRequest.getEmail())) {
            throw new LabourMarketException(LabourMarketErrorCode.INCORRECT_EMAIL);
        }

    }

    public static void validateRegisterEmployeeDtoRequest(RegisterEmployeeDtoRequest dtoRequest) throws LabourMarketException {
        if (dtoRequest == null) {
            throw new LabourMarketException(LabourMarketErrorCode.INCORRECT_JSON);
        }
        if (dtoRequest.getFirstName() == null || "".equals(dtoRequest.getFirstName())) {
            throw new LabourMarketException(LabourMarketErrorCode.INCORRECT_FIRSTNAME);
        }
        if (dtoRequest.getLogin() == null || "".equals(dtoRequest.getLogin())) {
            throw new LabourMarketException(LabourMarketErrorCode.INCORRECT_LOGIN);
        }
        if (dtoRequest.getPassword() == null || "".equals(dtoRequest.getPassword())) {
            throw new LabourMarketException(LabourMarketErrorCode.INCORRECT_PASSWORD);
        }
        if (dtoRequest.getEmail() == null || "".equals(dtoRequest.getEmail())) {
            throw new LabourMarketException(LabourMarketErrorCode.INCORRECT_EMAIL);
        }

    }


    public static void validateAddNewSkillDtoRequest(AddNewSkillDtoRequest dtoRequest) throws LabourMarketException {
        if (dtoRequest == null) {
            throw new LabourMarketException(LabourMarketErrorCode.INCORRECT_JSON);
        }
        if (dtoRequest.getToken() == null || "".compareTo(dtoRequest.getToken()) == 0) {
            throw new LabourMarketException(LabourMarketErrorCode.INCORRECT_TOKEN);
        }
        if (dtoRequest.getSkillName() == null || "".compareTo(dtoRequest.getSkillName()) == 0) {
            throw new LabourMarketException(LabourMarketErrorCode.INCORRECT_SKILL);
        }
        if (dtoRequest.getSkillLevel() < 1 || dtoRequest.getSkillLevel() > 5) {
            throw new LabourMarketException(LabourMarketErrorCode.INCORRECT_LEVEL);
        }
    }

    public static void validateChangeSkillLevelDtoRequest(ChangeSkillLevelDtoRequest dtoRequest) throws LabourMarketException {
        if (dtoRequest.getToken() == null || "".equals(dtoRequest.getToken())) {
            throw new LabourMarketException(LabourMarketErrorCode.INCORRECT_TOKEN);
        }
        if (dtoRequest.getSkillName() == null || "".equals(dtoRequest.getSkillName())) {
            throw new LabourMarketException(LabourMarketErrorCode.INCORRECT_SKILL);
        }
        if (dtoRequest.getNewLevel() < 1 || dtoRequest.getNewLevel() > 5) {
            throw new LabourMarketException(LabourMarketErrorCode.INCORRECT_LEVEL);
        }
    }

    public static void validateSkillServiceDtoRequest(SkillServiceDtoRequest skillServiceDto) throws LabourMarketException {
        if (skillServiceDto.getToken() == null || "".equals(skillServiceDto.getToken())) {
            throw new LabourMarketException(LabourMarketErrorCode.INCORRECT_TOKEN);
        }
        if (skillServiceDto.getSkillName() == null || "".equals(skillServiceDto.getSkillName())) {
            throw new LabourMarketException(LabourMarketErrorCode.INCORRECT_VACANCY);
        }
    }

    public static void validateLogInDto(LogInDtoRequest logInDtoJson) throws LabourMarketException {
        if (logInDtoJson == null) {
            throw new LabourMarketException(LabourMarketErrorCode.INCORRECT_JSON);
        }
        if (logInDtoJson.getLogin() == null || logInDtoJson.getLogin().compareTo("") == 0) {
            throw new LabourMarketException(LabourMarketErrorCode.INCORRECT_LOGIN);
        }
        if (logInDtoJson.getPassword() == null || logInDtoJson.getPassword().compareTo("") == 0) {
            throw new LabourMarketException(LabourMarketErrorCode.INCORRECT_PASSWORD);
        }
    }

    public static void validateVacancyServiceDtoRequest(VacancyServiceDtoRequest dtoRequest) throws LabourMarketException {
        if (dtoRequest.getToken() == null || "".equals(dtoRequest.getToken())) {
            throw new LabourMarketException(LabourMarketErrorCode.INCORRECT_TOKEN);
        }
        if (dtoRequest.getVacancyName() == null || "".equals(dtoRequest.getVacancyName())) {
            throw new LabourMarketException(LabourMarketErrorCode.INCORRECT_VACANCY);
        }
    }

    public static void validateAddVacancyDtoRequest(AddVacancyDtoRequest dtoRequest) throws LabourMarketException {
        if (dtoRequest.getToken() == null || "".equals(dtoRequest.getToken())) {
            throw new LabourMarketException(LabourMarketErrorCode.INCORRECT_TOKEN);
        }
        if (dtoRequest.getPostName() == null || "".equals(dtoRequest.getPostName())) {
            throw new LabourMarketException(LabourMarketErrorCode.INCORRECT_VACANCY);
        }
        if (dtoRequest.getSalary() <= 0) {
            throw new LabourMarketException(LabourMarketErrorCode.INCORRECT_MONEY);
        }
    }
}
