package ch.bzz.it.buchbewertungen.model;

import javax.ws.rs.FormParam;
import java.util.UUID;

/**
 * user that can write reviews
 * <p>
 * BookReviews
 *
 * @author Felix Reiniger
 * @version 1.0
 * @since 05.03.20
 */
public class User {
    @FormParam("name")
    private String name;

    private UUID uuid;

    private String about;

    @FormParam("password")
    private String password;
    private String role;

    /**
     * Gets the name
     *
     * @return value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the uuid
     *
     * @return value of uuid
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Gets the about
     *
     * @return value of about
     */
    public String getAbout() {
        return about;
    }

    /**
     * Gets the favBook
     *  TODO:
     * @return value of favBook
     */
    /*public Book getFavBook() {
        return favBook;
    }*/

    /**
     * Sets the name
     *
     * @param name the value to set
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the about
     *
     * @param about the value to set
     */

    public void setAbout(String about) {
        this.about = about;
    }

    /**
     * Sets the favBook
     * TODO:
     * @param favBook the value to set
     */

    /*public void setFavBook(Book favBook) {
        this.favBook = favBook;
    }*/

    /**
     * Sets the uuid
     *
     * @param uuid the value to set
     */

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * Gets the password
     *
     * @return value of password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password
     *
     * @param password the value to set
     */

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the role
     *
     * @return value of role
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the role
     *
     * @param role the value to set
     */

    public void setRole(String role) {
        this.role = role;
    }
}
