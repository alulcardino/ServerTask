package net.thumbtack.school.hiring;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.dto.request.RegisterEmployeeDtoRequest;
import net.thumbtack.school.hiring.dto.request.AddNewSkillDtoRequest;
import net.thumbtack.school.hiring.dto.request.SkillServiceDtoRequest;
import net.thumbtack.school.hiring.dto.request.TokenDtoRequest;
import net.thumbtack.school.hiring.dto.response.ErrorDtoResponse;
import net.thumbtack.school.hiring.exceptions.LabourMarketException;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class SkillTest {
    final Gson gson = new Gson();
    Server server = new Server();

    @Test
    public void test3() throws LabourMarketException, IOException, ClassNotFoundException {
        server.startServer(null);

        RegisterEmployeeDtoRequest employeeDtoRequest = new RegisterEmployeeDtoRequest("Petrov", "ptr20202", "123", "email@gmail.com");
        String employeeToken = gson.fromJson(server.registerEmployee(gson.toJson(employeeDtoRequest)), TokenDtoRequest.class).getToken();

        server.addNewSkill(gson.toJson(new AddNewSkillDtoRequest(employeeToken, "html", 3)));
        server.containsSkill(gson.toJson(new SkillServiceDtoRequest(employeeToken, "html")));

        RegisterEmployeeDtoRequest employeeDtoRequest1 = new RegisterEmployeeDtoRequest("Petrov", "ptr20202", "123", "email@gmail.com");
        String employeeToken1 = server.registerEmployee(gson.toJson(employeeDtoRequest1));

        ErrorDtoResponse error = gson.fromJson(server.addNewSkill(null), ErrorDtoResponse.class);

        assertEquals("INCORRECT_JSON", error.getError());
        error = gson.fromJson(server.addNewSkill(""), ErrorDtoResponse.class);
        assertEquals("INCORRECT_JSON", error.getError());
        error = gson.fromJson(server.addNewSkill("NULL"), ErrorDtoResponse.class);
        assertEquals("INCORRECT_JSON", error.getError());
    }



}