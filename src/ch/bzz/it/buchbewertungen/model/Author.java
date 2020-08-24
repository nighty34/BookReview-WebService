package ch.bzz.it.buchbewertungen.model;

import javax.ws.rs.FormParam;
import java.util.UUID;

/**
 * author of a book
 * <p>
 * BookReviews
 *
 * @author Felix Reiniger
 * @version 1.0
 * @since 05.03.20
 */
public class Author {


    @FormParam("name")
    private String name;

    @FormParam("language")
    private String language;

    private UUID uuid;

    /**
     * Sets the name
     *
     * @param name the value to set
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the language
     *
     * @param language the value to set
     */

    public void setLanguage(String language) {
        this.language = language;
    }


    /**
     * Gets the name
     *
     * @return value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the language
     *
     * @return value of language
     */
    public String getLanguage() {
        return language;
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
