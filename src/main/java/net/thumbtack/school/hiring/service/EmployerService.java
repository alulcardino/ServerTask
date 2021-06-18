package net.thumbtack.school.hiring.service;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.dao.EmployerDao;
import net.thumbtack.school.hiring.dao.UserDao;
import net.thumbtack.school.hiring.daoimpl.EmployerDaoImpl;
import net.thumbtack.school.hiring.daoimpl.UserDaoImpl;
import net.thumbtack.school.hiring.dto.request.ChangeEmployerDataRequest;
import net.thumbtack.school.hiring.dto.request.RegisterEmployerDtoRequest;
import net.thumbtack.school.hiring.dto.request.TokenDtoRequest;
import net.thumbtack.school.hiring.dto.response.BooleanDtoResponse;
import net.thumbtack.school.hiring.dto.response.EmptyDtoResponse;
import net.thumbtack.school.hiring.dto.response.EmployerDataResponse;
import net.thumbtack.school.hiring.dto.response.ErrorDtoResponse;
import net.thumbtack.school.hiring.exceptions.LabourMarketErrorCode;
import net.thumbtack.school.hiring.exceptions.LabourMarketException;
import net.thumbtack.school.hiring.model.Employer;
import net.thumbtack.school.hiring.utils.Validator;

import java.util.UUID;

public class EmployerService {
    private final EmployerDao employerDao = new EmployerDaoImpl();
    private final UserDao userDao = new UserDaoImpl();
    private final Gson gson = new Gson();



    public String registerEmployer(String gsonEmployer) {//проверить оригинальость логина
        try {
            RegisterEmployerDtoRequest registerData = UserService.getDtoFromJson(gsonEmployer, RegisterEmployerDtoRequest.class);
            validateRegisterEmployerDtoRequest(registerData);
            Employer employer = new Employer(registerData);
            userDao.registerUser(employer);
            String token = UUID.randomUUID().toString();
            userDao.logIn(token, employer);
            return gson.toJson(new TokenDtoRequest(token));
        } catch (LabourMarketException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }
    }

    public String changeEmployerData(String newEmployerDataDtoJson) {
        try {
            ChangeEmployerDataRequest employerData = UserService.getDtoFromJson(newEmployerDataDtoJson, ChangeEmployerDataRequest.class);
            validateChangeEmployerData(employerData);
            employerDao.changeEmployerData(employerData);
            return gson.toJson(new EmptyDtoResponse());
        } catch (LabourMarketException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }
    }

    public String getEmployerData(String userTokenDtoJson) {
        try {
            TokenDtoRequest tokenDto = UserService.getDtoFromJson(userTokenDtoJson, TokenDtoRequest.class);
            Validator.validateTokenDto(tokenDto);
            Employer em = employerDao.getEmployer(tokenDto.getToken());
            EmployerDataResponse employerData = new EmployerDataResponse(em.getFirstName(), em.getEmail(), em.getCompanyName(), em.getAddress());
            return gson.toJson(employerData);
        } catch (LabourMarketException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }
    }

    public String containsEmployer(String employerGson) {
        try {
            RegisterEmployerDtoRequest registerData = UserService.getDtoFromJson(employerGson, RegisterEmployerDtoRequest.class);
            validateRegisterEmployerDtoRequest(registerData);
            Employer employer = new Employer(registerData);
            Employer em = new Employer(employerDao.getRegisterEmployer(employer.getLogin(), employer.getPassword()));
            boolean t = em.equals(employer);
            return gson.toJson(new BooleanDtoResponse(t));
        } catch (LabourMarketException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }
    }

    private void validateChangeEmployerData(ChangeEmployerDataRequest dtoRequest) throws LabourMarketException {
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
        if (dtoRequest.getCompanyName() == null || "".equals(dtoRequest.getCompanyName())) {
            throw new LabourMarketException(LabourMarketErrorCode.INCORRECT_COMPANY);
        }
        if (dtoRequest.getAddress() == null || "".equals(dtoRequest.getAddress())) {
            throw new LabourMarketException(LabourMarketErrorCode.INCORRECT_ADDRESS);
        }

    }

    private void validateRegisterEmployerDtoRequest(RegisterEmployerDtoRequest registerDtoRequest) throws LabourMarketException {
        if (registerDtoRequest == null) {
            throw new LabourMarketException(LabourMarketErrorCode.INCORRECT_JSON);
        }
        if (registerDtoRequest.getFirstName() == null || "".equals(registerDtoRequest.getFirstName())) {
            throw new LabourMarketException(LabourMarketErrorCode.INCORRECT_FIRSTNAME);
        }
        if (registerDtoRequest.getLogin() == null || "".equals(registerDtoRequest.getLogin())) {
            throw new LabourMarketException(LabourMarketErrorCode.INCORRECT_LOGIN);
        }
        if (registerDtoRequest.getPassword() == null || "".equals(registerDtoRequest.getPassword())) {
            throw new LabourMarketException(LabourMarketErrorCode.INCORRECT_PASSWORD);
        }
        if (registerDtoRequest.getEmail() == null || "".equals(registerDtoRequest.getEmail())) {
            throw new LabourMarketException(LabourMarketErrorCode.INCORRECT_EMAIL);
        }
        if (registerDtoRequest.getCompanyName() == null || "".equals(registerDtoRequest.getCompanyName())) {
            throw new LabourMarketException(LabourMarketErrorCode.INCORRECT_COMPANY);
        }
        if (registerDtoRequest.getAddress() == null || "".equals(registerDtoRequest.getAddress())) {
            throw new LabourMarketException(LabourMarketErrorCode.INCORRECT_ADDRESS);
        }

    }

    public void clearDataBase() {
        employerDao.clearEmployerDataBase();
    }
}
