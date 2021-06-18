package net.thumbtack.school.hiring;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.dto.request.RegisterEmployeeDtoRequest;
import net.thumbtack.school.hiring.dto.response.BooleanDtoResponse;
import net.thumbtack.school.hiring.dto.response.ErrorDtoResponse;
import net.thumbtack.school.hiring.exceptions.LabourMarketException;
import org.junit.Test;


import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class EmployeeTests {
    Server server = new Server();

    @Test
    public void test1() throws LabourMarketException, IOException, ClassNotFoundException {
        server.startServer(null);
        Gson gson = new Gson();
        RegisterEmployeeDtoRequest employeeDtoRequest = new RegisterEmployeeDtoRequest("Petrov", "ptr20202", "123", "email@gmail.com");
        String employeeTokenDto = server.registerEmployee(gson.toJson(employeeDtoRequest));
        BooleanDtoResponse bool = gson.fromJson(server.containsEmployee(gson.toJson(employeeDtoRequest)), BooleanDtoResponse.class);
        assertEquals(true, bool.isValue());
        bool = gson.fromJson(server.isLogIn(employeeTokenDto), BooleanDtoResponse.class);
        assertEquals(true, bool.isValue());

        RegisterEmployeeDtoRequest controlDtoRequest = new RegisterEmployeeDtoRequest("Sidiriv", "ptr20202", "123", "email@gmail.com");
        assertEquals("LOGIN_ALREADY_EXISTS", gson.fromJson(server.registerEmployee(gson.toJson(controlDtoRequest)), ErrorDtoResponse.class).getError());

        ErrorDtoResponse error = gson.fromJson(server.registerEmployee(""), ErrorDtoResponse.class);
        assertEquals("INCORRECT_JSON", error.getError());
        error = gson.fromJson(server.registerEmployee("ADSZFXGCHJVKBL.;"), ErrorDtoResponse.class);
        assertEquals("INCORRECT_JSON", error.getError());

        error = gson.fromJson(server.registerEmployee(gson.toJson(new RegisterEmployeeDtoRequest("", "ptr20202", "123", "email@gmail.com"))), ErrorDtoResponse.class);
        assertEquals("INCORRECT_FIRSTNAME", error.getError());
        error = gson.fromJson(server.registerEmployee(gson.toJson(new RegisterEmployeeDtoRequest("ptr20202", "", "123", "email@gmail.com"))), ErrorDtoResponse.class);
        assertEquals("INCORRECT_LOGIN", error.getError());
        error = gson.fromJson(server.registerEmployee(gson.toJson(new RegisterEmployeeDtoRequest("ывфывфы", "ptr20202", "", "email@gmail.com"))), ErrorDtoResponse.class);
        assertEquals("INCORRECT_PASSWORD", error.getError());
        error = gson.fromJson(server.registerEmployee(gson.toJson(new RegisterEmployeeDtoRequest("ывфывфы", "ptr20202", "dffsdfs", ""))), ErrorDtoResponse.class);
        assertEquals("INCORRECT_EMAIL", error.getError());

        RegisterEmployeeDtoRequest employeeDtoRequest1 = new RegisterEmployeeDtoRequest("Petrov", "ptr20202", "123", "email@gmail.com");
        String employeeTokenDto1 = server.registerEmployee(gson.toJson(employeeDtoRequest1));

        BooleanDtoResponse bool1 = gson.fromJson(server.employeeIsActive(employeeTokenDto1), BooleanDtoResponse.class);
        assertEquals(false, bool1.isValue());

        server.madeAccountInactive(employeeTokenDto1);
        bool1 = gson.fromJson(server.employeeIsActive(employeeTokenDto1), BooleanDtoResponse.class);
        assertEquals(false, bool1.isValue());

        server.madeAccountActive(employeeTokenDto1);
        bool1 = gson.fromJson(server.employeeIsActive(employeeTokenDto1), BooleanDtoResponse.class);
        assertEquals(false, bool1.isValue());
    }

}
