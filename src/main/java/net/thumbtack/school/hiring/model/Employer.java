package net.thumbtack.school.hiring.model;

import net.thumbtack.school.hiring.dto.request.RegisterEmployerDtoRequest;
import net.thumbtack.school.hiring.dto.response.VacancyDtoResponse;
import net.thumbtack.school.hiring.exceptions.LabourMarketException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Employer extends User {
    private final List<VacancyDtoResponse> vacancyList;
    private final int id;
    private String companyName;
    private String address;

    public Employer(String firstName, String email, String login, String password, int id, String companyName, String address, List<VacancyDtoResponse> vacancyList) {
        super(firstName, email, login, password);
        this.id = id;
        this.companyName = companyName;
        this.address = address;
        this.vacancyList = vacancyList;
    }

    public Employer(String firstName, String email, String login, String password, int id, String companyName, String address) {
        super(firstName, email, login, password);
        this.id = id;
        this.companyName = companyName;
        this.address = address;
        this.vacancyList = new ArrayList<>();
    }

    public Employer(RegisterEmployerDtoRequest request) {
        super(request.getFirstName(), request.getEmail(), request.getLogin(), request.getPassword());
        this.companyName = request.getCompanyName();
        this.address = request.getAddress();
        vacancyList = new ArrayList<>();
        id = -1;
    }

    public Employer(Employer registerEmployer) {
        this(registerEmployer.getFirstName(), registerEmployer.getEmail(), registerEmployer.getLogin(),
                registerEmployer.getPassword(), registerEmployer.getId(), registerEmployer.getCompanyName(),
                registerEmployer.getAddress(), registerEmployer.getVacancyList());
    }

    public List<VacancyDtoResponse> getVacancyList() {
        return vacancyList;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) throws LabourMarketException {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) throws LabourMarketException {
        this.address = address;
    }

    public void addVacancy(VacancyDtoResponse vacancy) {
        vacancyList.add(vacancy);
    }

    public VacancyDtoResponse getVacancy(String vacancyName) {
        for (VacancyDtoResponse v : vacancyList) {
            if (v.getPostName().compareTo(vacancyName) == 0) {
                return v;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Employer employer = (Employer) o;
        return Objects.equals(companyName, employer.companyName) &&
                Objects.equals(address, employer.address) &&
                Objects.equals(vacancyList, employer.vacancyList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), companyName, address, vacancyList);
    }
}
