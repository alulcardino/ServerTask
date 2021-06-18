package net.thumbtack.school.hiring.service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import net.thumbtack.school.hiring.dao.UserDao;
import net.thumbtack.school.hiring.daoimpl.UserDaoImpl;
import net.thumbtack.school.hiring.dto.request.LogInDtoRequest;
import net.thumbtack.school.hiring.dto.request.TokenDtoRequest;
import net.thumbtack.school.hiring.dto.response.BooleanDtoResponse;
import net.thumbtack.school.hiring.dto.response.EmptyDtoResponse;
import net.thumbtack.school.hiring.dto.response.ErrorDtoResponse;
import net.thumbtack.school.hiring.dto.response.MessageDtoResponse;
import net.thumbtack.school.hiring.exceptions.LabourMarketErrorCode;
import net.thumbtack.school.hiring.exceptions.LabourMarketException;
import net.thumbtack.school.hiring.utils.Validator;

import java.io.IOException;
import java.util.UUID;

public class UserService {
    private final UserDao userDao = new UserDaoImpl();
    private final Gson gson = new Gson();



    private static void validateTokenDto(TokenDtoRequest userTokenDtoJson) throws LabourMarketException {
        if (userTokenDtoJson == null) {
            throw new LabourMarketException(LabourMarketErrorCode.INCORRECT_JSON);
        }
        if (userTokenDtoJson.getToken() == null || "".compareTo(userTokenDtoJson.getToken()) == 0) {
            throw new LabourMarketException(LabourMarketErrorCode.INCORRECT_TOKEN);
        }
    }

    public String logIn(String logInDtoGson) {
        try {
            LogInDtoRequest logInDto = UserService.getDtoFromJson(logInDtoGson, LogInDtoRequest.class);
            Validator.validateLogInDto(logInDto);
            String token = UUID.randomUUID().toString();
            userDao.logIn(token, userDao.getUser(logInDto.getLogin(), logInDto.getPassword()));
            return gson.toJson(new TokenDtoRequest(token));
        } catch (LabourMarketException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }
    }

    public String logOut(String userTokenDtoJson) {
        try {
            TokenDtoRequest tokenDto = UserService.getDtoFromJson(userTokenDtoJson, TokenDtoRequest.class);
            validateTokenDto(tokenDto);
            userDao.logOut(tokenDto.getToken());
            return gson.toJson(new EmptyDtoResponse());
        } catch (LabourMarketException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }
    }

    // ======================================================== //

    public String removeUser(String userTokenDtoJson) {
        try {
            TokenDtoRequest tokenDto = UserService.getDtoFromJson(userTokenDtoJson, TokenDtoRequest.class);
            validateTokenDto(tokenDto);
            userDao.removeUser(tokenDto.getToken());
            return gson.toJson(new EmptyDtoResponse());
        } catch (LabourMarketException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }
    }

    public String isLogIn(String userTokenDtoJson) {
        try {
            TokenDtoRequest tokenDto = UserService.getDtoFromJson(userTokenDtoJson, TokenDtoRequest.class);
            validateTokenDto(tokenDto);
            return gson.toJson(new BooleanDtoResponse(userDao.isLogIn(tokenDto.getToken())));
        } catch (LabourMarketException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }
    }

    public static  <T> T getDtoFromJson(String requestJsonString, Class<T> tClass) throws LabourMarketException {
        if (requestJsonString == null)
            throw new LabourMarketException(LabourMarketErrorCode.INCORRECT_JSON);
        try {
            return new Gson().fromJson(requestJsonString, tClass);
        } catch (JsonSyntaxException ex) {
            throw new LabourMarketException(LabourMarketErrorCode.INCORRECT_JSON);
        }
    }

    public void loadDataBase(String savedDataFileName) throws IOException, ClassNotFoundException {
        userDao.loadDataBase(savedDataFileName);
    }

    public String unloadUserBase(String savedDataFileName) throws IOException {
        return gson.toJson(new MessageDtoResponse(userDao.unloadDataBase(savedDataFileName)));
    }

}
