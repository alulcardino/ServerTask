package net.thumbtack.school.hiring.dao;

import net.thumbtack.school.hiring.dto.request.ChangeEmployerDataRequest;
import net.thumbtack.school.hiring.dto.request.AddVacancyDtoRequest;
import net.thumbtack.school.hiring.dto.request.VacancyServiceDtoRequest;
import net.thumbtack.school.hiring.dto.response.VacancyDtoResponse;
import net.thumbtack.school.hiring.exceptions.LabourMarketException;
import net.thumbtack.school.hiring.model.Employer;
import net.thumbtack.school.hiring.model.Vacancy;

import java.util.List;
import java.util.Set;

public interface EmployerDao {
    Employer getEmployer(String token) throws LabourMarketException;

    List<Employer> getAllEmployers();

    Employer getRegisterEmployer(String login, String password) throws LabourMarketException;

    Set<Vacancy> getVacancySetBySkillLevel(String skillName, int minLevel);

    void removeVacancyFromTree(VacancyDtoResponse vacancyDto) throws LabourMarketException;

    void clearVacancyDataBase();

    void clearEmployerDataBase();

    void changeEmployerData(ChangeEmployerDataRequest employerData) throws LabourMarketException;


    void addVacancy(AddVacancyDtoRequest vacancyData, Vacancy vacancy) throws LabourMarketException;

    void madeVacancyActive(String token, String vacancyName) throws LabourMarketException;

    void madeVacancyInactive(String token, String vacancyName) throws LabourMarketException;

    void removeVacancy(VacancyServiceDtoRequest vacancyData, VacancyDtoResponse vacancy) throws LabourMarketException;

    List<VacancyDtoResponse> getListOfActiveVacancy(String token) throws LabourMarketException;

    List<VacancyDtoResponse> getListOfInactiveVacancy(String token) throws LabourMarketException;

}
