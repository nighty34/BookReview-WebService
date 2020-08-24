package ch.bzz.it.buchbewertungen.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;
import java.util.UUID;

/**
 * review by a user for a book
 * <p>
 * BookReviews
 *
 * @author Felix Reiniger
 * @version 1.0
 * @since 05.03.20
 */
public class Review {


    private User user;
    private Book book;

    @FormParam("value")
    @Min(value = 1)
    @Max(value = 100)
    private int value;

    @FormParam("comment")
    @Size(max = 500)
    private String comment;


    //TODO: Pattern needed?
    //@Pattern(regexp = "[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}")
    private UUID uuid;


    /**
     * Gets the user
     *
     * @return value of user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user
     *
     * @param user the value to set
     */

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets the book
     *
     * @return value of book
     */
    public Book getBook() {
        return book;
    }

    /**
     * Sets the book
     *
     * @param book the value to set
     */

    public void setBook(Book book) {
        this.book = book;
    }

    /**
     * Gets the value
     *
     * @return value of value
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets the value
     *
     * @param value the value to set
     */

    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Gets the comment
     *
     * @return value of comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the comment
     *
     * @param comment the value to set
     */

    public void setComment(String comment) {
        this.comment = comment;
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
     * Sets the uuid
     *
     * @param uuid the value to set
     */

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}