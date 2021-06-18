package net.thumbtack.school.hiring.service;

import com.google.common.collect.Sets;
import com.google.common.collect.TreeMultimap;
import com.google.gson.Gson;
import net.thumbtack.school.hiring.dao.EmployeeDao;
import net.thumbtack.school.hiring.dao.EmployerDao;
import net.thumbtack.school.hiring.daoimpl.EmployeeDaoImpl;
import net.thumbtack.school.hiring.daoimpl.EmployerDaoImpl;
import net.thumbtack.school.hiring.dto.request.TokenDtoRequest;
import net.thumbtack.school.hiring.dto.request.AddVacancyDtoRequest;
import net.thumbtack.school.hiring.dto.request.DemandDtoRequest;
import net.thumbtack.school.hiring.dto.request.VacancyServiceDtoRequest;
import net.thumbtack.school.hiring.dto.response.BooleanDtoResponse;
import net.thumbtack.school.hiring.dto.response.EmptyDtoResponse;
import net.thumbtack.school.hiring.dto.response.EmployeeDataDtoResponse;
import net.thumbtack.school.hiring.dto.response.SkillDtoResponse;
import net.thumbtack.school.hiring.dto.response.VacancyDtoResponse;
import net.thumbtack.school.hiring.dto.response.ErrorDtoResponse;
import net.thumbtack.school.hiring.exceptions.LabourMarketErrorCode;
import net.thumbtack.school.hiring.exceptions.LabourMarketException;
import net.thumbtack.school.hiring.model.Employee;
import net.thumbtack.school.hiring.model.Employer;
import net.thumbtack.school.hiring.model.Vacancy;
import net.thumbtack.school.hiring.utils.Validator;

import java.util.*;

public class VacancyService {

    private final EmployerDao employerDao = new EmployerDaoImpl();
    private final EmployeeDao employeeDao = new EmployeeDaoImpl();
    private final Gson gson = new Gson();




    public String addVacancy(String addVacancyDtoRequest) {
        try {
            AddVacancyDtoRequest vacancyData = UserService.getDtoFromJson(addVacancyDtoRequest, AddVacancyDtoRequest.class);
            Validator.validateAddVacancyDtoRequest(vacancyData);
            Vacancy vacancy = new Vacancy(vacancyData.getPostName(), vacancyData.getSalary(), vacancyData.getDemands());
            employerDao.addVacancy(vacancyData, vacancy);
            return gson.toJson(new EmptyDtoResponse());
        } catch (LabourMarketException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }
    }

    public VacancyDtoResponse getVacancy(String token, String vacancyName) {
        try {
            return employerDao.getEmployer(token).getVacancy(vacancyName);
        } catch (LabourMarketException e) {
            return null;
        }
    }

    public String getVacancy(String vacancyServiceDto) {
        try {
            VacancyServiceDtoRequest vacancyData = UserService.getDtoFromJson(vacancyServiceDto, VacancyServiceDtoRequest.class);
            Validator.validateVacancyServiceDtoRequest(vacancyData);
            Employer em = employerDao.getEmployer(vacancyData.getToken());
            VacancyDtoResponse v = new VacancyDtoResponse(em.getVacancy(vacancyData.getVacancyName()));
            return gson.toJson(v);
        } catch (LabourMarketException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }
    }

    public String madeVacancyActive(String vacancyServiceDto) {
        try {
            VacancyServiceDtoRequest vacancyData = UserService.getDtoFromJson(vacancyServiceDto, VacancyServiceDtoRequest.class);
            Validator.validateVacancyServiceDtoRequest(vacancyData);
            employerDao.madeVacancyActive(vacancyData.getToken(), vacancyData.getVacancyName());
            return gson.toJson(new EmptyDtoResponse());
        } catch (LabourMarketException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }
    }

    public String madeVacancyInactive(String vacancyServiceDto) {
        try {
            VacancyServiceDtoRequest vacancyData = UserService.getDtoFromJson(vacancyServiceDto, VacancyServiceDtoRequest.class);
            Validator.validateVacancyServiceDtoRequest(vacancyData);
            employerDao.madeVacancyInactive(vacancyData.getToken(), vacancyData.getVacancyName());

            return gson.toJson(new EmptyDtoResponse());
        } catch (LabourMarketException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }
    }

    public String removeVacancy(String vacancyServiceDto) {
        try {
            VacancyServiceDtoRequest vacancyData = UserService.getDtoFromJson(vacancyServiceDto, VacancyServiceDtoRequest.class);
            Validator.validateVacancyServiceDtoRequest(vacancyData);
            VacancyDtoResponse vacancy = getVacancy(vacancyData.getToken(), vacancyData.getVacancyName());
            employerDao.removeVacancy(vacancyData, vacancy);


            return gson.toJson(new EmptyDtoResponse());
        } catch (LabourMarketException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }
    }

    public String containsVacancy(String vacancyServiceDto) {
        try {
            VacancyServiceDtoRequest vacancyData = UserService.getDtoFromJson(vacancyServiceDto, VacancyServiceDtoRequest.class);
            Validator.validateVacancyServiceDtoRequest(vacancyData);
            Employer em = employerDao.getEmployer(vacancyData.getToken());
            for (VacancyDtoResponse v : em.getVacancyList()) {
                if (v.getPostName().compareTo(vacancyData.getVacancyName()) == 0) {
                    return gson.toJson(new BooleanDtoResponse(true));
                }
            }
            return gson.toJson(new BooleanDtoResponse(false));
        } catch (LabourMarketException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }
    }

    public String vacancyIsActive(String vacancyServiceDto) {
        try {
            VacancyServiceDtoRequest vacancyData = UserService.getDtoFromJson(vacancyServiceDto, VacancyServiceDtoRequest.class);
            Validator.validateVacancyServiceDtoRequest(vacancyData);
            return gson.toJson(new BooleanDtoResponse(employerDao.getEmployer(vacancyData.getToken()).getVacancy(vacancyData.getVacancyName()).isActive()));
        } catch (LabourMarketException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }
    }

    public String getListOfActiveVacancy(String userTokenDtoJson) {
        try {
            TokenDtoRequest tokenDto = UserService.getDtoFromJson(userTokenDtoJson, TokenDtoRequest.class);
            Validator.validateTokenDto(tokenDto);
            return gson.toJson(employerDao.getListOfActiveVacancy(tokenDto.getToken()));
        } catch (LabourMarketException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }
    }

//==============================================================================//

    public String getListOfInactiveVacancy(String userTokenDtoJson) {
        List<VacancyDtoResponse> newlist = new ArrayList<>();
        try {
            TokenDtoRequest tokenDto = UserService.getDtoFromJson(userTokenDtoJson, TokenDtoRequest.class);
            Validator.validateTokenDto(tokenDto);
            return gson.toJson(employerDao.getListOfInactiveVacancy(tokenDto.getToken()));

        } catch (LabourMarketException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }
    }

    public String getListOfEmployeesWhoHaveAllTheNecessarySkillsOnNeededLvl(String vacancyServiceDto) {
        List<EmployeeDataDtoResponse> result = new ArrayList<>();
        Set<Employee> employeesSet = new HashSet<>();
        try {
            //получили список своих скилов
            VacancyServiceDtoRequest vacancyData = UserService.getDtoFromJson(vacancyServiceDto, VacancyServiceDtoRequest.class);
            Validator.validateVacancyServiceDtoRequest(vacancyData);
            VacancyDtoResponse vacancy = new VacancyDtoResponse(employerDao.getEmployer(vacancyData.getToken()).getVacancy(vacancyData.getVacancyName()));
            List<SkillDtoResponse> neededSkills = vacancy.getAllSkills();

            employeesSet.addAll(employeeDao.getEmployeeSetBySkillLevel(neededSkills.get(0).getName(), neededSkills.get(0).getLevel()));
            for (SkillDtoResponse skill : neededSkills) {
                employeesSet = Sets.intersection(employeesSet, employeeDao.getEmployeeSetBySkillLevel(skill.getName(), skill.getLevel()));
            }
            for (Employee e : employeesSet) {
                result.add(new EmployeeDataDtoResponse(e.getFirstName(), e.getEmail(), e.getSkillsList()));
            }
            Collections.sort(result, Comparator.comparing(EmployeeDataDtoResponse::getName));

            return gson.toJson(result);
        } catch (LabourMarketException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }

    }

    public String getListOfEmployeesWhoHaveAllTheRequiredSkillsOnNeededLvl(String vacancyServiceDto) {
        List<EmployeeDataDtoResponse> result = new ArrayList<>();
        try {
            VacancyServiceDtoRequest vacancyData = UserService.getDtoFromJson(vacancyServiceDto, VacancyServiceDtoRequest.class);
            Validator.validateVacancyServiceDtoRequest(vacancyData);
            VacancyDtoResponse vacancy = new VacancyDtoResponse(employerDao.getEmployer(vacancyData.getToken()).getVacancy(vacancyData.getVacancyName()));
            List<SkillDtoResponse> neededSkills = vacancy.getAllSkills();
            Set<Employee> employeesSet = new HashSet<>();

            employeesSet.addAll(employeeDao.getEmployeeSetBySkillLevel(neededSkills.get(0).getName(), neededSkills.get(0).getLevel()));

            for (DemandDtoRequest d : vacancy.getDemands()) {
                if (d.isMandatory()) {
                    employeesSet = Sets.intersection(employeesSet, employeeDao.getEmployeeSetBySkillLevel(d.getSkill().getName(), d.getSkill().getLevel()));
                } else {
                    employeesSet = Sets.intersection(employeesSet, employeeDao.getEmployeeSetBySkillLevel(d.getSkill().getName(), 1));
                }
            }
            for (Employee e : employeesSet) {
                result.add(new EmployeeDataDtoResponse(e.getFirstName(), e.getEmail(), e.getSkillsList()));
            }
            Collections.sort(result, Comparator.comparing(EmployeeDataDtoResponse::getName));
            return gson.toJson(result);
        } catch (LabourMarketException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }

    }

    public String getListOfEmployeesWhoHaveAllTheNecessarySkillsOnAnyLvl(String vacancyServiceDto) {
        List<EmployeeDataDtoResponse> result = new ArrayList<>();
        try {
            //получили список своих скилов
            VacancyServiceDtoRequest vacancyData = UserService.getDtoFromJson(vacancyServiceDto, VacancyServiceDtoRequest.class);
            Validator.validateVacancyServiceDtoRequest(vacancyData);
            VacancyDtoResponse vacancy = new VacancyDtoResponse(employerDao.getEmployer(vacancyData.getToken()).getVacancy(vacancyData.getVacancyName()));
            List<SkillDtoResponse> neededSkills = vacancy.getAllSkills();
            Set<Employee> employeesSet = new HashSet<>();

            employeesSet.addAll(employeeDao.getEmployeeSetBySkillLevel(neededSkills.get(0).getName(), 1));

            for (SkillDtoResponse skill : neededSkills) {
                employeesSet = Sets.intersection(employeesSet, employeeDao.getEmployeeSetBySkillLevel(skill.getName(), 1));
            }
            for (Employee e : employeesSet) {
                result.add(new EmployeeDataDtoResponse(e.getFirstName(), e.getEmail(), e.getSkillsList()));
            }
            Collections.sort(result, (EmployeeDataDtoResponse o1, EmployeeDataDtoResponse o2) -> o1.getName().compareTo(o2.getName()));
            return gson.toJson(result);
        } catch (LabourMarketException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }

    }

    // ======================================================== //

    public String getListOfEmployeesWhoHaveAtLeastOneTheNecessarySkillsOnAnyLvl(String vacancyServiceDto) {
        List<EmployeeDataDtoResponse> result = new ArrayList<>();
        Map<Integer, ArrayList<EmployeeDataDtoResponse>> sortedEmployee = new HashMap<>();
        try {
            //получили список своих скилов
            VacancyServiceDtoRequest vacancyData = UserService.getDtoFromJson(vacancyServiceDto, VacancyServiceDtoRequest.class);
            Validator.validateVacancyServiceDtoRequest(vacancyData);
            VacancyDtoResponse vacancy = new VacancyDtoResponse(employerDao.getEmployer(vacancyData.getToken()).getVacancy(vacancyData.getVacancyName()));
            Map<Employee, Integer> employeeMap = new HashMap<>();
            Set<Employee> employeesSet = new HashSet<>();
            for (SkillDtoResponse skill : vacancy.getAllSkills()) {
                employeesSet = employeeDao.getEmployeeSetBySkillLevel(skill.getName(), skill.getLevel());
                for (Employee em : employeesSet) {
                    if (employeeMap.containsKey(em)) {
                        employeeMap.put(em, employeeMap.get(em) + 1);
                    } else {
                        employeeMap.put(em, 1);
                    }
                }
            }
            TreeMultimap<Integer, Employee> tree = TreeMultimap.create((o1, o2) -> o2.compareTo(o1), (o1, o2) -> o1.getFirstName().compareTo(o2.getFirstName()));
            for (Map.Entry<Employee, Integer> entry : employeeMap.entrySet()) {
                tree.put(entry.getValue(), entry.getKey());
            }
            for (Integer key : tree.keySet()) {
                for (Employee e : tree.get(key)) {
                    result.add(new EmployeeDataDtoResponse(e.getFirstName(), e.getEmail(), e.getSkillsList()));
                }

            }
            Collections.sort(result, (EmployeeDataDtoResponse o1, EmployeeDataDtoResponse o2) -> o1.getName().compareTo(o2.getName()));

            return gson.toJson(result);
        } catch (LabourMarketException e) {
            return gson.toJson(new ErrorDtoResponse(e.getErrorCode()));
        }
    }



    public void clearDataBase() {
        employerDao.clearVacancyDataBase();
    }
}