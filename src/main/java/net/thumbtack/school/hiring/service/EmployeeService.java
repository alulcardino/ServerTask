package net.thumbtack.school.hiring.service;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.dao.EmployeeDao;
import net.thumbtack.school.hiring.dao.EmployerDao;
import net.thumbtack.school.hiring.dao.UserDao;
import net.thumbtack.school.hiring.daoimpl.EmployeeDaoImpl;
import net.thumbtack.school.hiring.daoimpl.EmployerDaoImpl;
import net.thumbtack.school.hiring.daoimpl.UserDaoImpl;
import net.thumbtack.school.hiring.dto.request.ChangeEmployeeDataDtoRequest;
import net.thumbtack.school.hiring.dto.request.RegisterEmployeeDtoRequest;
import net.thumbtack.school.hiring.dto.request.TokenDtoRequest;
import net.thumbtack.school.hiring.dto.request.DemandDtoRequest;
import net.thumbtack.school.hiring.dto.response.BooleanDtoResponse;
import net.thumbtack.school.hiring.dto.response.EmptyDtoResponse;
import net.thumbtack.school.hiring.dto.response.EmployeeDataDtoResponse;
import net.thumbtack.school.hiring.dto.response.SkillDtoResponse;
import net.thumbtack.school.hiring.dto.response.ErrorDtoResponse;
import net.thumbtack.school.hiring.exceptions.LabourMarketException;
import net.thumbtack.school.hiring.model.Employee;
import net.thumbtack.school.hiring.model.Vacancy;
import net.thumbtack.school.hiring.utils.Validator;

import java.util.*;
import java.util.stream.Collectors;

public class EmployeeService {
    private final EmployeeDao employeeDao = new EmployeeDaoImpl();
    private final EmployerDao employerDao = new EmployerDaoImpl();
    private final UserDao userDao = new UserDaoImpl();
    private final Gson gson = new Gson();



    public String registerEmployee(String json) {
        try {
            RegisterEmployeeDtoRequest registerData = UserService.getDtoFromJson(json, RegisterEmployeeDtoRequest.class);
            Validator.validateRegisterEmployeeDtoRequest(registerData);
            Employee employee = new Employee(registerData);
            userDao.registerUser(employee);
            String token = UUID.randomUUID().toString();
            userDao.logIn(token, employee);
            return gson.toJson(new TokenDtoRequest(token));
        } catch (LabourMarketException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }
    }

    public String changeEmployeeData(String newEmployeeDataDtoJson) {
        try {
            ChangeEmployeeDataDtoRequest employeeData = UserService.getDtoFromJson(newEmployeeDataDtoJson, ChangeEmployeeDataDtoRequest.class);
            Validator.validateChangeEmployeeDataDto(employeeData);
            employeeDao.changeEmployeeData(employeeData);
            return gson.toJson(new EmptyDtoResponse());
        } catch (LabourMarketException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }
    }

    public String madeAccountInactive(String userTokenDtoJson) {
        try {
            TokenDtoRequest tokenDto = UserService.getDtoFromJson(userTokenDtoJson, TokenDtoRequest.class);
            Validator.validateTokenDto(tokenDto);
            employeeDao.madeAccountInactive(tokenDto.getToken());
            return gson.toJson(new EmptyDtoResponse());
        } catch (LabourMarketException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }
    }

    public String madeAccountActive(String userTokenDtoJson) {
        try {
            TokenDtoRequest tokenDto = UserService.getDtoFromJson(userTokenDtoJson, TokenDtoRequest.class);
            Validator.validateTokenDto(tokenDto);
            employeeDao.madeAccountActive(tokenDto.getToken());

            return gson.toJson(new EmptyDtoResponse());
        } catch (LabourMarketException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }
    }

    public String getEmployeeData(String employeeTokenGson) {
        try {
            TokenDtoRequest tokenDto = UserService.getDtoFromJson(employeeTokenGson, TokenDtoRequest.class);
            Validator.validateTokenDto(tokenDto);
            Employee em = employeeDao.getEmployee(tokenDto.getToken());
            Gson gson = new Gson();
            return gson.toJson(new EmployeeDataDtoResponse(em.getFirstName(), em.getEmail(), em.getSkillsList()));
        } catch (LabourMarketException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }
    }

    public String containsEmployee(String employeeGson) {
        try {
            RegisterEmployeeDtoRequest registerData = UserService.getDtoFromJson(employeeGson, RegisterEmployeeDtoRequest.class);
            Validator.validateRegisterEmployeeDtoRequest(registerData);
            Employee employee = new Employee(registerData);
            Employee em = new Employee(employeeDao.getRegisterEmployee(employee.getLogin(), employee.getPassword()));
            boolean t = em.equals(employee);
            return gson.toJson(new BooleanDtoResponse(t));
        } catch (LabourMarketException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }
    }


    public String employeeIsActive(String employeeTokenGson) {
        try {
            TokenDtoRequest tokenDto = UserService.getDtoFromJson(employeeTokenGson, TokenDtoRequest.class);
            Validator.validateTokenDto(tokenDto);
            return gson.toJson(new BooleanDtoResponse(employeeDao.employeeIsActive(tokenDto.getToken())));
        } catch (LabourMarketException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }
    }

    public String getListOfAllVacanciesMeetsTheRequirementsOfTheEmployerAtANeededLvl(String employeeTokenGson) {
        List<Vacancy> finish = new ArrayList<>();
        try {
            TokenDtoRequest tokenDto = UserService.getDtoFromJson(employeeTokenGson, TokenDtoRequest.class);
            Validator.validateTokenDto(tokenDto);
            Employee employee = new Employee(employeeDao.getEmployee(tokenDto.getToken()));
            List<SkillDtoResponse> skills = employee.getSkillsList();
            List<String> skillsName = skills.stream().map(x -> x.getName()).collect(Collectors.toList());

            Set<Vacancy> vacancySet = new HashSet<>();
            for (SkillDtoResponse skill : skills) {
                vacancySet.addAll(employerDao.getVacancySetBySkillLevel(skill.getName(), skill.getLevel()));
            }

            for (Vacancy v : vacancySet) {
                boolean key = true;
                List<String> vacancySkillsName = v.getAllSkills().stream().map(x -> x.getName()).collect(Collectors.toList());
                if (v.getActive() && skillsName.containsAll(vacancySkillsName)) {
                    for (SkillDtoResponse s : v.getAllSkills()) {
                        if (s.getLevel() > employee.getSkillByName(s.getName()).getLevel()) {
                            key = false;
                        }
                    }
                } else {
                    key = false;
                }
                if (key) {
                    finish.add(v);
                }
            }
            finish.sort(Comparator.comparing(Vacancy::getPostName));
            Gson gson = new Gson();
            return gson.toJson(finish);
        } catch (LabourMarketException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }

    }

    //только необходимые навыки на нужном уровне, остальные на любом
    public String getListOfAllVacanciesMeetsTheBindingRequirementsOfTheEmployerAtANeededLvl(String employeeTokenGson) {
        List<Vacancy> finish = new ArrayList<>();
        try {
            TokenDtoRequest tokenDto = UserService.getDtoFromJson(employeeTokenGson, TokenDtoRequest.class);
            Validator.validateTokenDto(tokenDto);
            Employee employee = employeeDao.getEmployee(tokenDto.getToken());
            List<SkillDtoResponse> skills = employee.getSkillsList();
            List<String> skillsName = skills.stream().map(x -> x.getName()).collect(Collectors.toList());

            Set<Vacancy> vacancySet = new HashSet<>();
            for (SkillDtoResponse skill : skills) {
                vacancySet.addAll(employerDao.getVacancySetBySkillLevel(skill.getName(), 1));
            }
            for (Vacancy v : vacancySet) {
                boolean key = true;
                List<String> vacancySkillsName = v.getAllSkills().stream().map(x -> x.getName()).collect(Collectors.toList());
                if (v.getActive() && skillsName.containsAll(vacancySkillsName)) {
                    for (DemandDtoRequest d : v.getDemands()) {
                        if (d.getSkill().getLevel() > employee.getSkillByName(d.getSkill().getName()).getLevel() && d.isMandatory()) {
                            key = false;
                        }
                    }
                } else {
                    key = false;
                }
                if (key) {
                    finish.add(v);
                }
            }
            finish.sort(Comparator.comparing(Vacancy::getPostName));
            Gson gson = new Gson();
            return gson.toJson(finish);
        } catch (LabourMarketException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }

    }

    public String getListOfAllVacanciesMeetsTheRequirementsOfTheEmployerOnAnyLvl(String employeeTokenDto) {
        List<Vacancy> finish = new ArrayList<>();
        try {
            TokenDtoRequest tokenDto = UserService.getDtoFromJson(employeeTokenDto, TokenDtoRequest.class);
            Validator.validateTokenDto(tokenDto);
            Employee employee = employeeDao.getEmployee(tokenDto.getToken());
            List<String> skillsName = employee.getSkillsList().stream().map(x -> x.getName()).collect(Collectors.toList());

            Set<Vacancy> vacancySet = new HashSet<>();
            for (String skill : skillsName) {
                vacancySet.addAll(employerDao.getVacancySetBySkillLevel(skill, 1));
            }
            for (Vacancy v : vacancySet) {
                List<String> vacancySkillsName = v.getAllSkills().stream().map(x -> x.getName()).collect(Collectors.toList());
                if (v.getActive() && skillsName.containsAll(vacancySkillsName)) {
                    finish.add(v);
                }
            }
            finish.sort((o1, o2) -> o1.getPostName().compareTo(o2.getPostName()));
            Gson gson = new Gson();
            return gson.toJson(finish);
        } catch (LabourMarketException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }
    }

    public String getListOfAllVacanciesMeetsAtLeastOneOfTheRequirementsOfTheEmployerAtNeededLvl(String employeeTokenGson) {
        List<Vacancy> result = new ArrayList<>();
        Map<Integer, ArrayList<Vacancy>> sortedVacancy = new HashMap<>();
        try {
            TokenDtoRequest tokenDto = UserService.getDtoFromJson(employeeTokenGson, TokenDtoRequest.class);
            Validator.validateTokenDto(tokenDto);
            Employee employee = employeeDao.getEmployee(tokenDto.getToken());
            List<SkillDtoResponse> skills = employee.getSkillsList();
            List<String> skillsName = skills.stream().map(x -> x.getName()).collect(Collectors.toList());
            Set<Vacancy> vacancySet = new HashSet<>();
            for (String skill : skillsName) {
                vacancySet.addAll(employerDao.getVacancySetBySkillLevel(skill, 1));
            }
            for (Vacancy v : vacancySet) {
                if (v.getActive()) {
                    int workingSkillCount = 0;
                    for (DemandDtoRequest d : v.getDemands()) {
                        int demandSkillLvl = d.getSkill().getLevel();
                        int employeeSkillLvl = employee.getSkillByName(d.getSkill().getName()).getLevel();
                        if (skillsName.contains(d.getSkill().getName()) && demandSkillLvl <= employeeSkillLvl) {
                            workingSkillCount++;
                        }
                    }
                    if (workingSkillCount != 0) {
                        if (sortedVacancy.containsKey(workingSkillCount)) {
                            sortedVacancy.get(workingSkillCount).add(v);
                        } else {
                            ArrayList<Vacancy> newArray = new ArrayList<>();
                            newArray.add(v);
                            sortedVacancy.put(workingSkillCount, newArray);
                        }
                    }
                }
            }
            for (ArrayList<Vacancy> list : sortedVacancy.values()) {
                result.addAll(0, list);
            }
            Gson gson = new Gson();
            return gson.toJson(result);
        } catch (LabourMarketException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }
    }



    public void clearDataBase() {
        employeeDao.clearEmployeeDataBase();
    }



}
