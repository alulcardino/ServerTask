package net.thumbtack.school.hiring.exceptions;

public class LabourMarketException extends Exception {
    private final LabourMarketErrorCode error;

    public LabourMarketException(LabourMarketErrorCode error) {
        this.error = error;
    }

    public String getErrorCode() {
        return error.toString();
    }
}
