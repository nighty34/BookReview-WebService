package ch.bzz.it.buchbewertungen.util;

/**
 * short description
 * <p>
 * BookReview-WebService
 *
 * @author TODO
 * @version 1.0
 * @since 06.01.21
 */
public enum Result {
    SUCCESS(0), // command was successfully executed
    NOACTION(1), // nothing to be done
    ERROR(9); // there was an error => throw exception

    private int code;

    Result(int code) {
        this.setCode(code);
    }

    /**
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * @param code
     *            the code to set
     */
    public void setCode(int code) {
        this.code = code;
    }
}
