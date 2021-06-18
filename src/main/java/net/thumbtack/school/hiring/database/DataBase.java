package net.thumbtack.school.hiring.database;

import com.google.common.collect.TreeMultimap;
import net.thumbtack.school.hiring.dto.response.SkillDtoResponse;
import net.thumbtack.school.hiring.exceptions.LabourMarketErrorCode;
import net.thumbtack.school.hiring.exceptions.LabourMarketException;
import net.thumbtack.school.hiring.model.*;

import java.io.*;
import java.util.*;

public class DataBase {
    private static volatile DataBase dataBase;
    private boolean cleaned = false;
    private final TreeMultimap<Skill, Employee> employees;
    private final TreeMultimap<Skill, Vacancy> vacancies;
    private final Map<String, User> userMap;
    private final Map<String, User> logins;

    private DataBase() {
        logins = new HashMap<>();
        userMap = new HashMap<>();
        employees = TreeMultimap.create((o1, o2) -> {
            int nameCompareResult = o1.getName().compareTo(o2.getName());
            return nameCompareResult != 0 ? nameCompareResult : o1.getLevel() - o2.getLevel();
        }, Comparator.comparing(User::getFirstName));
        vacancies = TreeMultimap.create((o1, o2) -> {
            int nameCompareResult = o1.getName().compareTo(o2.getName());
            return nameCompareResult != 0 ? nameCompareResult : o1.getLevel() - o2.getLevel();
        }, Comparator.comparing(Vacancy::getPostName));
    }

    public  void addEmployee(Employee employee, Skill skill) {
        employees.put(skill, employee);
    }

    public  Set<Employee> getEmployee(Skill skill) {
        return employees.get(skill);
    }

    public  void addVacancy(Vacancy vacancy) {
        for (SkillDtoResponse skill : vacancy.getAllSkills()) {
            vacancies.put(new Skill(skill), vacancy);
        }
    }

    public  Set<Vacancy> getVacancy(Skill skill) {
        return vacancies.get(skill);
    }

    public static DataBase getInstance(String savedDataFileName) throws IOException, ClassNotFoundException {
        if (dataBase == null || dataBase.cleaned) {
            if (savedDataFileName != null)
                try (ObjectInputStream ois = new ObjectInputStream(
                        new FileInputStream(savedDataFileName))) {
                    dataBase = (DataBase) ois.readObject();
                }
            else dataBase = new DataBase();
            dataBase.cleaned = false;
        }
        return dataBase;
    }

    public String saveInstanceState(String savedDataFileName) throws IOException {
        if (savedDataFileName != null) {
            try (ObjectOutputStream oos = new ObjectOutputStream(
                    new FileOutputStream(savedDataFileName))) {
                oos.writeObject(dataBase);
            }
        }
        logins.clear();
        dataBase=null;
        return "Server stopped";
    }


    public  void registerUser(User user) throws LabourMarketException {
        User check = userMap.putIfAbsent(user.getLogin(), user);
        if (check != null) {
            throw new LabourMarketException(LabourMarketErrorCode.LOGIN_ALREADY_EXISTS);
        }
    }

    public User getOneUserOfAll(String login, String password) throws LabourMarketException {
        User user = userMap.get(login);
        if (user == null) {
            throw new LabourMarketException(LabourMarketErrorCode.USER_DOESNT_EXIST);
        }
        return user;
    }

    public User getLogInUser(String token) throws LabourMarketException {
        User user = logins.get(token);
        if (user == null) {
            throw new LabourMarketException(LabourMarketErrorCode.USER_DOESNT_EXIST);
        }
        return user;
    }

    public  void removeUser(String token) {
        userMap.remove(logins.get(token).getLogin());
        logins.remove(token);
    }

    public List<Employee> getAllEmployee() {
        List<Employee> result = new ArrayList<>();
        for (User elem : userMap.values()) {
            if (elem.getClass() == Employee.class) {
                result.add((Employee) elem);
            }
        }
        return result;
    }

    public List<Employer> getAllEmployer() {
        List<Employer> result = new ArrayList<>();
        for (User elem : userMap.values()) {
            if (elem.getClass() == Employer.class) {
                result.add((Employer) elem);
            }
        }
        return result;
    }

    public  void userLogIn(String token, User user) {
        logins.put(token, user);
    }

    public  void userLogOut(String token) throws LabourMarketException {

        logins.remove(token);
    }

    public boolean userIsLogIn(String userToken) {
        return logins.containsKey(userToken);
    }

    public  void getDown() {
        userMap.clear();
        logins.clear();
        employees.clear();
        vacancies.clear();
        cleaned = true;
    }
}
