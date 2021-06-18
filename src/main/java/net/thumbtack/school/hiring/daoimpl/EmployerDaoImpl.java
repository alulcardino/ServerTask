package net.thumbtack.school.hiring.daoimpl;

import net.thumbtack.school.hiring.dao.EmployerDao;
import net.thumbtack.school.hiring.database.DataBase;
import net.thumbtack.school.hiring.dto.request.ChangeEmployerDataRequest;
import net.thumbtack.school.hiring.dto.request.AddVacancyDtoRequest;
import net.thumbtack.school.hiring.dto.request.VacancyServiceDtoRequest;
import net.thumbtack.school.hiring.dto.response.SkillDtoResponse;
import net.thumbtack.school.hiring.dto.response.VacancyDtoResponse;
import net.thumbtack.school.hiring.exceptions.LabourMarketErrorCode;
import net.thumbtack.school.hiring.exceptions.LabourMarketException;
import net.thumbtack.school.hiring.model.Employer;
import net.thumbtack.school.hiring.model.Skill;
import net.thumbtack.school.hiring.model.User;
import net.thumbtack.school.hiring.model.Vacancy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EmployerDaoImpl extends UserDaoImpl implements EmployerDao {
    @Override
    public Employer getEmployer(String token) throws LabourMarketException {
        User e = dataBase.getLogInUser(token);
        if (e.getClass() != Employer.class) {
            throw new LabourMarketException(LabourMarketErrorCode.USER_DOESNT_EXIST);
        }
        return (Employer) e;
    }

    @Override
    public List<Employer> getAllEmployers() {
        return dataBase.getAllEmployer();
    }

    @Override
    public Employer getRegisterEmployer(String login, String password) throws LabourMarketException {
        User e = dataBase.getOneUserOfAll(login, password);
        if (e.getClass() != Employer.class) {
            throw new LabourMarketException(LabourMarketErrorCode.USER_DOESNT_EXIST);
        }
        return (Employer) e;
    }

    public void addVacancyInSortedTree(Vacancy vacancy) {
        dataBase.addVacancy(vacancy);
    }


    @Override
    public Set<Vacancy> getVacancySetBySkillLevel(String skillName, int minLevel) {
        Set<Vacancy> result = new HashSet<>();
        Skill skill = new Skill(skillName, minLevel);
        for (int i = minLevel + 1; i < 7; i++) {
            result.addAll(dataBase.getVacancy(skill));
            skill.setLevel(i);
        }
        return result;
    }

    @Override
    public void removeVacancyFromTree(VacancyDtoResponse vacancyDto) {
        Vacancy vacancy = new Vacancy(vacancyDto.getPostName(), vacancyDto.getSalary(), vacancyDto.getDemands());
        for (SkillDtoResponse skill : vacancy.getAllSkills()) {
            dataBase.getVacancy(new Skill(skill)).remove(vacancy);
        }
    }

    @Override
    public void clearVacancyDataBase() {

    }

    @Override
    public void clearEmployerDataBase() {

    }

    @Override
    public void changeEmployerData(ChangeEmployerDataRequest employerData) throws LabourMarketException {
        Employer em = getEmployer(employerData.getToken());
        em.setFirstName(employerData.getName());
        em.setEmail(employerData.getEmail());
        em.setPassword(employerData.getPassword());
        em.setAddress(employerData.getAddress());
        em.setCompanyName(employerData.getCompanyName());
    }

    @Override
    public void addVacancy(AddVacancyDtoRequest vacancyData, Vacancy vacancy) throws LabourMarketException {
        getEmployer(vacancyData.getToken()).addVacancy(new VacancyDtoResponse(vacancy.getPostName(), vacancy.getSalary(), vacancy.getDemands()));
        addVacancyInSortedTree(vacancy);
    }

    @Override
    public void madeVacancyActive(String token, String vacancyName) throws LabourMarketException {
        getEmployer(token).getVacancy(vacancyName).setActive(true);
    }

    @Override
    public void madeVacancyInactive(String token, String vacancyName) throws LabourMarketException {
        getEmployer(token).getVacancy(vacancyName).setActive(false);
    }

    @Override
    public void removeVacancy(VacancyServiceDtoRequest vacancyData, VacancyDtoResponse vacancy) throws LabourMarketException {
        removeVacancyFromTree(vacancy);
        getEmployer(vacancyData.getToken()).getVacancyList().remove(vacancy);
    }

    @Override
    public List<VacancyDtoResponse> getListOfActiveVacancy(String token) throws LabourMarketException {
        List<VacancyDtoResponse> newlist = new ArrayList<>();
        for (VacancyDtoResponse v : getEmployer(token).getVacancyList()) {
            if (v.isActive()) {
                newlist.add(v);
            }
        }
        return newlist;
    }

    @Override
    public List<VacancyDtoResponse> getListOfInactiveVacancy(String token) throws LabourMarketException {
        List<VacancyDtoResponse> newlist = new ArrayList<>();
        for (VacancyDtoResponse v : getEmployer(token).getVacancyList()) {
            if (!v.isActive()) {
                newlist.add(v);
            }
        }
        return newlist;
    }


}
