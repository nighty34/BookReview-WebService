package ch.bzz.it.buchbewertungen.model;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;
import java.util.UUID;

/**
 * book that can be reviewed
 * <p>
 * BookReviews
 *
 * @author Felix Reiniger
 * @version 1.0
 * @since 05.03.20
 */
public class Book {

    @FormParam("title")
    @Size(max=100, min=1)
    private String title;

    @FormParam("series")
    @Size(max=100, min=1)
    private String seriesname;

    private Author author;

    @FormParam("isbn")
    private String iSBN;

    @FormParam("price")
    @DecimalMax(value="200.00")
    @DecimalMin(value ="0.05")
    private double price;

    //TODO: Setup
    private UUID uuid;


    /**
     * Gets the title
     *
     * @return value of title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title
     *
     * @param title the value to set
     */

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the seriesname
     *
     * @return value of seriesname
     */
    public String getSeriesname() {
        return seriesname;
    }

    /**
     * Sets the seriesname
     *
     * @param seriesname the value to set
     */

    public void setSeriesName(String seriesname) {
        this.seriesname = seriesname;
    }

    /**
     * Gets the author
     *
     * @return value of author
     */
    public Author getAuthor() {
        return author;
    }

    /**
     * Sets the author
     *
     * @param author the value to set
     */

    public void setAuthor(Author author) {
        this.author = author;
    }

    /**
     * Gets the iSBN
     *
     * @return value of iSBN
     */
    public String getiSBN() {
        return iSBN;
    }

    /**
     * Sets the iSBN
     *
     * @param iSBN the value to set
     */

    public void setiSBN(String iSBN) {
        this.iSBN = iSBN;
    }

    /**
     * Gets the price
     *
     * @return value of price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price
     *
     * @param price the value to set
     */

    public void setPrice(double price) {
        this.price = price;
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


