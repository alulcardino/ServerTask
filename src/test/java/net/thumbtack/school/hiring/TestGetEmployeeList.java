package net.thumbtack.school.hiring;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.dto.request.RegisterEmployeeDtoRequest;
import net.thumbtack.school.hiring.dto.request.RegisterEmployerDtoRequest;
import net.thumbtack.school.hiring.dto.request.AddNewSkillDtoRequest;
import net.thumbtack.school.hiring.dto.request.TokenDtoRequest;
import net.thumbtack.school.hiring.dto.request.AddVacancyDtoRequest;
import net.thumbtack.school.hiring.dto.request.VacancyServiceDtoRequest;
import net.thumbtack.school.hiring.dto.response.EmployeeDataDtoResponse;
import net.thumbtack.school.hiring.exceptions.LabourMarketException;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestGetEmployeeList {
    Server server = new Server();
    Gson gson = new Gson();

    @Test
    public void test4() throws LabourMarketException, IOException, ClassNotFoundException {
        server.startServer(null);
        RegisterEmployerDtoRequest employerDtoRequest = new RegisterEmployerDtoRequest("Ivanov55", "1234560", "ivanov", "ABCGame", "Address, 1/2", "email@gmail.com");
        String employerToken = gson.fromJson(server.registerEmployer(gson.toJson(employerDtoRequest)), TokenDtoRequest.class).getToken();
        AddVacancyDtoRequest newVacancy = new AddVacancyDtoRequest(employerToken, "Web Developer", 1500);
        newVacancy.addDemand("html", 3, true);
        newVacancy.addDemand("css", 3, false);
        newVacancy.addDemand("java-script", 5, true);
        newVacancy.addDemand("java", 5, false);
        server.addVacancy(gson.toJson(newVacancy));

        RegisterEmployeeDtoRequest employeeDtoA = new RegisterEmployeeDtoRequest("A", "petr1", "1234", "1@mail.ru");
        String tokenEmployeeA = gson.fromJson(server.registerEmployee(gson.toJson(employeeDtoA)), TokenDtoRequest.class).getToken();
        server.addNewSkill(gson.toJson(new AddNewSkillDtoRequest(tokenEmployeeA, "html", 3)));
        server.addNewSkill(gson.toJson(new AddNewSkillDtoRequest(tokenEmployeeA, "css", 3)));
        server.addNewSkill(gson.toJson(new AddNewSkillDtoRequest(tokenEmployeeA, "java-script", 5)));
        server.addNewSkill(gson.toJson(new AddNewSkillDtoRequest(tokenEmployeeA, "java", 5)));

        RegisterEmployeeDtoRequest employeeDtoB = new RegisterEmployeeDtoRequest("B", "petr12", "1234", "2@mail.ru");
        String tokenEmployeeB = gson.fromJson(server.registerEmployee(gson.toJson(employeeDtoB)), TokenDtoRequest.class).getToken();
        server.addNewSkill(gson.toJson(new AddNewSkillDtoRequest(tokenEmployeeB, "html", 3)));
        server.addNewSkill(gson.toJson(new AddNewSkillDtoRequest(tokenEmployeeB, "css", 2)));
        server.addNewSkill(gson.toJson(new AddNewSkillDtoRequest(tokenEmployeeB, "java-script", 5)));
        server.addNewSkill(gson.toJson(new AddNewSkillDtoRequest(tokenEmployeeB, "java", 3)));


        RegisterEmployeeDtoRequest employeeDtoC = new RegisterEmployeeDtoRequest("C", "petr13", "1234", "3@mail.ru");
        String tokenEmployeeC = gson.fromJson(server.registerEmployee(gson.toJson(employeeDtoC)), TokenDtoRequest.class).getToken();
        server.addNewSkill(gson.toJson(new AddNewSkillDtoRequest(tokenEmployeeC, "html", 3)));
        server.addNewSkill(gson.toJson(new AddNewSkillDtoRequest(tokenEmployeeC, "css", 2)));
        server.addNewSkill(gson.toJson(new AddNewSkillDtoRequest(tokenEmployeeC, "java-script", 1)));
        server.addNewSkill(gson.toJson(new AddNewSkillDtoRequest(tokenEmployeeC, "java", 1)));


        RegisterEmployeeDtoRequest employeeDtoD = new RegisterEmployeeDtoRequest("D", "petr14", "1234", "4@mail.ru");
        String tokenEmployeeD = gson.fromJson(server.registerEmployee(gson.toJson(employeeDtoD)), TokenDtoRequest.class).getToken();
        server.addNewSkill(gson.toJson(new AddNewSkillDtoRequest(tokenEmployeeD, "html", 5)));

        List<EmployeeDataDtoResponse> control1 = new ArrayList<>();
        control1.add(gson.fromJson(server.getEmployeeData(gson.toJson(new TokenDtoRequest(tokenEmployeeA))), EmployeeDataDtoResponse.class));
        control1.add(gson.fromJson(server.getEmployeeData(gson.toJson(new TokenDtoRequest(tokenEmployeeB))), EmployeeDataDtoResponse.class));
        control1.add(gson.fromJson(server.getEmployeeData(gson.toJson(new TokenDtoRequest(tokenEmployeeC))), EmployeeDataDtoResponse.class));
        control1.add(gson.fromJson(server.getEmployeeData(gson.toJson(new TokenDtoRequest(tokenEmployeeD))), EmployeeDataDtoResponse.class));

        assertEquals(control1.toString().replaceAll("\\s", ""), server.getListOfEmployeesWhoHaveAtLeastOneTheNecessarySkillsOnAnyLvl(gson.toJson(new VacancyServiceDtoRequest(employerToken, "Web Developer"))));
        List<EmployeeDataDtoResponse> control2 = new ArrayList<>();
        control2.add(gson.fromJson(server.getEmployeeData(gson.toJson(new TokenDtoRequest(tokenEmployeeA))), EmployeeDataDtoResponse.class));
        assertEquals(control2.toString().replaceAll("\\s", ""), server.getListOfEmployeesWhoHaveAllTheNecessarySkillsOnNeededLvl(gson.toJson(new VacancyServiceDtoRequest(employerToken, "Web Developer"))));

        List<String> control3 = new ArrayList<>();
        control3.add(server.getEmployeeData(gson.toJson(new TokenDtoRequest(tokenEmployeeA))));
        control3.add(server.getEmployeeData(gson.toJson(new TokenDtoRequest(tokenEmployeeB))));
        assertEquals(control3.toString().replaceAll("\\s", ""), server.getListOfEmployeesWhoHaveAllTheRequiredSkillsOnNeededLvl(gson.toJson(new VacancyServiceDtoRequest(employerToken, "Web Developer"))));

        List<EmployeeDataDtoResponse> control = new ArrayList<>();

        control.add(gson.fromJson(server.getEmployeeData(gson.toJson(new TokenDtoRequest(tokenEmployeeA))), EmployeeDataDtoResponse.class));
        control.add(gson.fromJson(server.getEmployeeData(gson.toJson(new TokenDtoRequest(tokenEmployeeB))), EmployeeDataDtoResponse.class));
        control.add(gson.fromJson(server.getEmployeeData(gson.toJson(new TokenDtoRequest(tokenEmployeeC))), EmployeeDataDtoResponse.class));
        assertEquals(control.toString().replaceAll("\\s", ""), server.getListOfEmployeesWhoHaveAllTheNecessarySkillsOnAnyLvl(gson.toJson(new VacancyServiceDtoRequest(employerToken, "Web Developer"))));
    }
}
