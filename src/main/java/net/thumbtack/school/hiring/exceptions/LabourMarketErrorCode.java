package net.thumbtack.school.hiring.exceptions;

public enum LabourMarketErrorCode {
    INCORRECT_FIRSTNAME("INCORRECT FIRSTNAME"),
    INCORRECT_SURNAME("INCORRECT SURNAME"),
    INCORRECT_SECONDNAME("INCORRECT SECONDNAME"),
    NO_VACANCIES("THERE ARE NO VACANCIES"),
    NO_SKILLS("THERE ARE NO SKILLS"),
    INCORRECT_ID("INCORRECT ID!"),
    INCORRECT_EMAIL("INCORRECT EMAIL!"),
    INCORRECT_ADDRESS("INCORRECT ADDRESS!"),
    INCORRECT_COMPANY("INCORRECT COMPANY!"),
    INCORRECT_LOGIN("INCORRECT LOGIN"),
    INCORRECT_PASSWORD("INCORRECT PASSWORD!"),
    INCORRECT_JSON("INCORRECT JSON REQUEST!"),
    INCORRECT_TOKEN("INCORRECT TOKEN"),
    LOGIN_ALREADY_EXISTS("USER ALREADY EXISTS!"),
    INCORRECT_SKILLS("INCORRECT SKILLS"),
    INCORRECT_VACANCY("INCORRECT VACANCY!"),
    INCORRECT_MONEY("INCORRECT MONEY!"),
    INCORRECT_DEMANDS("INCORRECT DEMANDS!"),
    INCORRECT_DEMAND("INCORRECT DEMAND!"),
    INCORRECT_SKILL("INCORRECT SKILL!"),
    INCORRECT_LEVEL("INCORRECT LEVEL!"),
    LOGIN_PASSWORD_NOT_MATCH("INCORRECT PASSWORD OR LOGIN"),
    USER_DOESNT_EXIST("USER DOESNT EXIST");

    private final String str;

    LabourMarketErrorCode(String str) {
        this.str = str;
    }

    public String getDescription() {
        return str;
    }
}