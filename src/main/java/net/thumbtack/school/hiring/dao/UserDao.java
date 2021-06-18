package net.thumbtack.school.hiring.dao;

import net.thumbtack.school.hiring.exceptions.LabourMarketException;
import net.thumbtack.school.hiring.model.User;

import java.io.IOException;

public interface UserDao {
    void logIn(String token, User user) throws LabourMarketException;

    void logOut(String token) throws LabourMarketException;

    User getUser(String login, String password) throws LabourMarketException;

    void registerUser(User user) throws LabourMarketException;

    void removeUser(String token) throws LabourMarketException;

    boolean isLogIn(String userToken);

    void loadDataBase(String savedDataFileName) throws IOException, ClassNotFoundException;

    String unloadDataBase(String savedDataFileName) throws IOException;

}
