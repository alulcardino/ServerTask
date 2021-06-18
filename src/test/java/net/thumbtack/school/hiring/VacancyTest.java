package net.thumbtack.school.hiring;

import com.google.gson.Gson;

import net.thumbtack.school.hiring.dto.request.AddVacancyDtoRequest;
import net.thumbtack.school.hiring.dto.request.RegisterEmployerDtoRequest;
import net.thumbtack.school.hiring.dto.request.TokenDtoRequest;
import net.thumbtack.school.hiring.dto.request.VacancyServiceDtoRequest;
import net.thumbtack.school.hiring.dto.response.BooleanDtoResponse;
import net.thumbtack.school.hiring.exceptions.LabourMarketException;
import org.junit.Test;


import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class VacancyTest {
    Gson gson = new Gson();
    Server server = new Server();

    @Test
    public void test6() throws LabourMarketException, IOException, ClassNotFoundException {
        server.startServer(null);

        RegisterEmployerDtoRequest employerDto1 = new RegisterEmployerDtoRequest("Ivanov55", "ivanov", "123", "email@gmail.com", "ABCGame", "Address, 1/2");
        String tokenEmployer1 = this.gson.fromJson(server.registerEmployer(gson.toJson(employerDto1)), TokenDtoRequest.class).getToken();

        AddVacancyDtoRequest addVacancyDtoRequest1 = new AddVacancyDtoRequest(tokenEmployer1, "name", 200);
        addVacancyDtoRequest1.addDemand("html", 3, true);
        addVacancyDtoRequest1.addDemand("css", 4, false);
        addVacancyDtoRequest1.addDemand("java-script", 4, true);

        server.addVacancy(this.gson.toJson(addVacancyDtoRequest1));
        BooleanDtoResponse bool1 = gson.fromJson(server.containsVacancy(this.gson.toJson(new VacancyServiceDtoRequest(tokenEmployer1, "name"))), BooleanDtoResponse.class);
        assertEquals(true, bool1.isValue());

        RegisterEmployerDtoRequest employerDto2 = new RegisterEmployerDtoRequest("Ivanov55", "ivanov", "123", "email@gmail.com", "ABCGame", "Address, 1/2");
        String tokenEmployer2 = this.gson.fromJson(server.registerEmployer(gson.toJson(employerDto2)), TokenDtoRequest.class).getToken();
        server.addVacancy(this.gson.toJson(new AddVacancyDtoRequest(tokenEmployer2, "name", 200)));

        server.removeVacancy(this.gson.toJson(new VacancyServiceDtoRequest(tokenEmployer2, "name")));
        BooleanDtoResponse bool2 = gson.fromJson(server.containsVacancy(this.gson.toJson(new VacancyServiceDtoRequest(tokenEmployer2, "name"))), BooleanDtoResponse.class);

        assertEquals(false, bool2.isValue());


    }

}
