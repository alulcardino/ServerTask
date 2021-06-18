package net.thumbtack.school.hiring;


import com.google.gson.Gson;
import net.thumbtack.school.hiring.dto.request.ChangeEmployerDataRequest;
import net.thumbtack.school.hiring.dto.request.RegisterEmployerDtoRequest;
import net.thumbtack.school.hiring.dto.request.TokenDtoRequest;
import net.thumbtack.school.hiring.dto.response.BooleanDtoResponse;
import net.thumbtack.school.hiring.dto.response.ErrorDtoResponse;
import net.thumbtack.school.hiring.exceptions.LabourMarketException;
import org.junit.Test;


import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class EmployerTests {
    Server server = new Server();
    Gson gson = new Gson();
    @Test
    public void test2() throws LabourMarketException, IOException, ClassNotFoundException {
        server.startServer(null);

        Gson gson = new Gson();
        RegisterEmployerDtoRequest employerDto = new RegisterEmployerDtoRequest("Ivanov55", "123", "ivanov", "ABCGame", "Address, 1/2", "email@gmail.com");
        String tokenEmployerDto = server.registerEmployer(gson.toJson(employerDto));

        BooleanDtoResponse bool = gson.fromJson(server.containsEmployer(gson.toJson(employerDto)), BooleanDtoResponse.class);
        assertEquals(true, bool.isValue());
        bool = gson.fromJson(server.isLogIn(tokenEmployerDto), BooleanDtoResponse.class);
        assertEquals(true, bool.isValue());

        RegisterEmployerDtoRequest controlDto = new RegisterEmployerDtoRequest("Ivanov55", "123", "ivanov", "ABCGame", "Address, 1/2", "email@gmail.com");
        assertEquals("LOGIN_ALREADY_EXISTS", gson.fromJson(server.registerEmployer(gson.toJson(controlDto)), ErrorDtoResponse.class).getError());

        ErrorDtoResponse error = gson.fromJson(server.registerEmployer(""), ErrorDtoResponse.class);
        assertEquals("INCORRECT_JSON", error.getError());
        error = gson.fromJson(server.registerEmployer("ADSZFXGCHJVKBL.;"), ErrorDtoResponse.class);
        assertEquals("INCORRECT_JSON", error.getError());

        error = gson.fromJson(server.registerEmployer(gson.toJson(new RegisterEmployerDtoRequest("", "ptr20202", "123", "email@gmail.com", "asdfad", "sads"))), ErrorDtoResponse.class);
        assertEquals("INCORRECT_FIRSTNAME", error.getError());
        error = gson.fromJson(server.registerEmployer(gson.toJson(new RegisterEmployerDtoRequest("ptr20202", "", "123", "email@gmail.com", "asdfad", "sads"))), ErrorDtoResponse.class);
        assertEquals("INCORRECT_LOGIN", error.getError());
        error = gson.fromJson(server.registerEmployer(gson.toJson(new RegisterEmployerDtoRequest("ывфывфы", "ptr20202", "", "email@gmail.com", "asdfad", "sads"))), ErrorDtoResponse.class);
        assertEquals("INCORRECT_PASSWORD", error.getError());
        error = gson.fromJson(server.registerEmployer(gson.toJson(new RegisterEmployerDtoRequest("ывфывфы", "ptr20202", "dffsdfs", "", "asdfad", "sads"))), ErrorDtoResponse.class);
        assertEquals("INCORRECT_EMAIL", error.getError());
        error = gson.fromJson(server.registerEmployer(gson.toJson(new RegisterEmployerDtoRequest("ывфывфы", "ptr20202", "dffsdfs", "sdf", "", "sads"))), ErrorDtoResponse.class);
        assertEquals("INCORRECT_COMPANY", error.getError());
        error = gson.fromJson(server.registerEmployer(gson.toJson(new RegisterEmployerDtoRequest("ывфывфы", "ptr20202", "dffsdfs", "sdfsd", "asdfad", ""))), ErrorDtoResponse.class);
        assertEquals("INCORRECT_ADDRESS", error.getError());



        RegisterEmployerDtoRequest employerDto1 = new RegisterEmployerDtoRequest("Ivanov55", "123", "ivanov", "ABCGame", "Address, 1/2", "email@gmail.com");
        String tokenEmployerDto1 = server.registerEmployer(gson.toJson(employerDto1));
        String tokenEmployer = gson.fromJson(tokenEmployerDto1, TokenDtoRequest.class).getToken();
        ChangeEmployerDataRequest newEmployerData = new ChangeEmployerDataRequest(tokenEmployer, "ABC", "DEF", "0123", "XYZ", "GHJ");
        server.changeEmployerData(gson.toJson(newEmployerData));

        server.logOut(tokenEmployerDto1);

    }

}
