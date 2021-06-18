package net.thumbtack.school.hiring.daoimpl;

import net.thumbtack.school.hiring.dao.UserDao;
import net.thumbtack.school.hiring.database.DataBase;
import net.thumbtack.school.hiring.exceptions.LabourMarketException;
import net.thumbtack.school.hiring.model.User;

import java.io.IOException;

public class UserDaoImpl implements UserDao {
    protected static DataBase dataBase;



    @Override
    public void logIn(String token, User user) throws LabourMarketException {
        dataBase.userLogIn(token, user);
    }

    @Override
    public void logOut(String token) throws LabourMarketException {
        dataBase.userLogOut(token);
    }

    @Override
    public User getUser(String login, String password) throws LabourMarketException {
        return dataBase.getOneUserOfAll(login, password);
    }

    @Override
    public void registerUser(User user) throws LabourMarketException {
        dataBase.registerUser(user);
    }

    @Override
    public void removeUser(String token) throws LabourMarketException {
        dataBase.removeUser(token);
    }

    @Override
    public boolean isLogIn(String userToken) {
        return dataBase.userIsLogIn(userToken);
    }

    @Override
    public void loadDataBase(String savedDataFileName) throws IOException, ClassNotFoundException {
        dataBase = DataBase.getInstance(savedDataFileName);
    }

    @Override
    public String unloadDataBase(String savedDataFileName) throws IOException {
        return dataBase.saveInstanceState(savedDataFileName);
    }


}
