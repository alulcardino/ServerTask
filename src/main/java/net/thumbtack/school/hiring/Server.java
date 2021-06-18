package net.thumbtack.school.hiring;

import net.thumbtack.school.hiring.database.DataBase;
import net.thumbtack.school.hiring.service.*;

import java.io.IOException;

public class Server {
    private final EmployeeService EMPLOYEE_SERVICE = new EmployeeService();
    private final EmployerService EMPLOYER_SERVICE = new EmployerService();
    private final UserService USER_SERVICE = new UserService();
    private final VacancyService VACANCY_SERVICE = new VacancyService();
    private final SkillService SKILL_SERVICE = new SkillService();


    public void startServer(String savedDataFileName) throws IOException, ClassNotFoundException {
        USER_SERVICE.loadDataBase(savedDataFileName);
    }

    public String stopServer(String savedDataFileName) throws IOException {
        return USER_SERVICE.unloadUserBase(savedDataFileName);
    }

    public String logIn(String logInDtoJson) {
        return USER_SERVICE.logIn(logInDtoJson);
    }

    public String logOut(String userTokenDtoJson) {
        return USER_SERVICE.logOut(userTokenDtoJson);
    }

    public String isLogIn(String userTokenDtoJson) {
        return USER_SERVICE.isLogIn(userTokenDtoJson);
    }

    public String containsEmployer(String employerJson) {
        return EMPLOYER_SERVICE.containsEmployer(employerJson);
    }

    public String containsEmployee(String employeeJson) {
        return EMPLOYEE_SERVICE.containsEmployee(employeeJson);
    }

    public String removeUser(String userTokenDtoJson) {
        return USER_SERVICE.removeUser(userTokenDtoJson);
    }

    public String registerEmployer(String gsonEmployer) {
        return EMPLOYER_SERVICE.registerEmployer(gsonEmployer); //+
    }


    public String changeEmployerData(String newEmployerDataDtoJson) {
        return EMPLOYER_SERVICE.changeEmployerData(newEmployerDataDtoJson);//+
    }

    public String getEmployerData(String userTokenDtoJson) {
        return EMPLOYER_SERVICE.getEmployerData(userTokenDtoJson);
    }

    public String registerEmployee(String gsonEmployee) {
        return EMPLOYEE_SERVICE.registerEmployee(gsonEmployee);//+
    }
    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!//

    public String changeEmployeeData(String newEmployeeDataDtoJson) {
        return EMPLOYEE_SERVICE.changeEmployeeData(newEmployeeDataDtoJson);//+
    }

    public String getEmployeeData(String userTokenDtoJson) {
        return EMPLOYEE_SERVICE.getEmployeeData(userTokenDtoJson);
    }

    public String madeAccountInactive(String userTokenDtoJson) {
        return EMPLOYEE_SERVICE.madeAccountInactive(userTokenDtoJson);//+
    }

    public String madeAccountActive(String userTokenDtoJson) {
        return EMPLOYEE_SERVICE.madeAccountActive(userTokenDtoJson);//+
    }

    public String employeeIsActive(String userTokenDtoJson) {
        return EMPLOYEE_SERVICE.employeeIsActive(userTokenDtoJson);
    }

    public String addVacancy(String addVacancyDtoRequest) {
        return VACANCY_SERVICE.addVacancy(addVacancyDtoRequest);//+
    }

    public String getVacancy(String vacancyServiceDto) {
        return VACANCY_SERVICE.getVacancy(vacancyServiceDto);//+
    }

    public String madeVacancyActive(String vacancyServiceDto) {
        return VACANCY_SERVICE.madeVacancyActive(vacancyServiceDto);//+
    }

    public String madeVacancyInactive(String vacancyServiceDto) {
        return VACANCY_SERVICE.madeVacancyInactive(vacancyServiceDto);//+
    }

    public String removeVacancy(String vacancyServiceDto) {
        return VACANCY_SERVICE.removeVacancy(vacancyServiceDto);//+
    }

    public String getListOfActiveVacancy(String userTokenDtoJson) {
        return VACANCY_SERVICE.getListOfActiveVacancy(userTokenDtoJson);//+
    }

    public String getListOfInactiveVacancy(String userTokenDtoJson) {
        return VACANCY_SERVICE.getListOfInactiveVacancy(userTokenDtoJson);//+
    }

    public String containsVacancy(String vacancyServiceDto) {
        return VACANCY_SERVICE.containsVacancy(vacancyServiceDto);
    }

    public String vacancyIsActive(String vacancyServiceDto) {
        return VACANCY_SERVICE.vacancyIsActive(vacancyServiceDto);
    }

    public String addNewSkill(String addSkillDtoRequest) {
        return SKILL_SERVICE.addNewSkill(addSkillDtoRequest);//+
    }


    public String removeSkill(String skillServiceDto) {
        return SKILL_SERVICE.removeSkill(skillServiceDto);//+
    }

    public String changeSkillLevel(String changeSkillLevelDto) {
        return SKILL_SERVICE.changeSkillLevel(changeSkillLevelDto);//+
    }

    public String getSkillLevel(String skillServiceDto) {
        return SKILL_SERVICE.getSkillLevel(skillServiceDto);//+
    }

    public String containsSkill(String skillServiceDto) {
        return SKILL_SERVICE.containsSkill(skillServiceDto);
    }

    public String getListOfAllVacanciesMeetsTheRequirementsOfTheEmployerAtANeededLvl(String employeeTokenDto) {
        return EMPLOYEE_SERVICE.getListOfAllVacanciesMeetsTheRequirementsOfTheEmployerAtANeededLvl(employeeTokenDto);
    }

    public String getListOfAllVacanciesMeetsTheBindingRequirementsOfTheEmployerAtANeededLvl(String employeeTokenDto) {
        return EMPLOYEE_SERVICE.getListOfAllVacanciesMeetsTheBindingRequirementsOfTheEmployerAtANeededLvl(employeeTokenDto);
    }

    public String getListOfAllVacanciesMeetsTheRequirementsOfTheEmployerOnAnyLvl(String employeeTokenDto) {
        return EMPLOYEE_SERVICE.getListOfAllVacanciesMeetsTheRequirementsOfTheEmployerOnAnyLvl(employeeTokenDto);
    }

    public String getListOfAllVacanciesMeetsAtLeastOneOfTheRequirementsOfTheEmployerAtNeededLvl(String employeeTokenDto) {
        return EMPLOYEE_SERVICE.getListOfAllVacanciesMeetsAtLeastOneOfTheRequirementsOfTheEmployerAtNeededLvl(employeeTokenDto);
    }

    public String getListOfEmployeesWhoHaveAllTheNecessarySkillsOnNeededLvl(String vacancyServiceDto) {
        return VACANCY_SERVICE.getListOfEmployeesWhoHaveAllTheNecessarySkillsOnNeededLvl(vacancyServiceDto);
    }


    public String getListOfEmployeesWhoHaveAllTheRequiredSkillsOnNeededLvl(String vacancyServiceDto) {
        return VACANCY_SERVICE.getListOfEmployeesWhoHaveAllTheRequiredSkillsOnNeededLvl(vacancyServiceDto);
    }

    public String getListOfEmployeesWhoHaveAllTheNecessarySkillsOnAnyLvl(String vacancyServiceDto) {
        return VACANCY_SERVICE.getListOfEmployeesWhoHaveAllTheNecessarySkillsOnAnyLvl(vacancyServiceDto);
    }

    public String getListOfEmployeesWhoHaveAtLeastOneTheNecessarySkillsOnAnyLvl(String vacancyServiceDto) {
        return VACANCY_SERVICE.getListOfEmployeesWhoHaveAtLeastOneTheNecessarySkillsOnAnyLvl(vacancyServiceDto);
    }
}





