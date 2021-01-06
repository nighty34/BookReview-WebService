package ch.bzz.it.buchbewertungen.service;

import ch.bzz.it.buchbewertungen.data.BookDao;
import ch.bzz.it.buchbewertungen.data.DataHandler;
import ch.bzz.it.buchbewertungen.model.Author;
import ch.bzz.it.buchbewertungen.model.Book;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Service-Class for the book-modelclass
 * <p>
 * BookReviews
 *
 * @author Felix Reiniger
 * @version 1.0
 * @since 26.03.20
 */


@Path("book")
public class BookService {


    /**
     * Service for listing all available Books
     *
     * @return Response
     */
    @Path("list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listBooks(
            @QueryParam("filter") String filter
    ){
        List<Book> books = new BookDao().getAll(filter);
        Response response = Response
                .status(200)
                .entity(books)
                .build();

        return response;
    }


    /**
     * Service Method for reading a specific book
     *
     * @param uuidString
     * @return
     */
    @Path("read")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response readBook(@QueryParam("filter") String filter){ //TODO: find better solution
        int httpCode = 404;
        try{
            Book book = new BookDao().getEntity(filter);
            httpCode = 200;
            return Response.status(httpCode)
                    .entity(book)
                    .build();
        }catch (IllegalArgumentException e){
            httpCode = 400;
        }

        return Response
                .status(httpCode)
                .build();
    }


    /**
     * Service Method for creating a new Book
     *
     * @param book  Created Book object
     * @param authorid  UUID of bookauthor
     * @return Response
     */
    @Path("create")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response createBook(
            @Valid @BeanParam Book book,
            @FormParam("authorid") String authorid,
            @CookieParam("userRole") String userRole
    ){
        int httpStatus;
        if(!(userRole == null) && userRole.equals("admin")) {

            httpStatus = new BookDao().save(book).getCode();
        }else{
            httpStatus = 403;
        }

        Response response = Response
                .status(httpStatus)
                .build();

        return response;
    }


    /**
     *
     * @param book
     * @param authorid
     * @param userRole
     * @return
     */
    //TODO: Use @FormParam Book book instead of title, series, etc.
    @Path("update")
    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateBook(
            @Valid @BeanParam Book book,
            @FormParam("authorid") String authorid,
            @CookieParam("userRole") String userRole
    ){
        int httpStatus;
        if(!(userRole == null) && userRole.equals("admin")) {

            httpStatus = new BookDao().save(book).getCode();
        }else{
            httpStatus = 403;
        }

        Response response = Response
                .status(httpStatus)
                .build();

        return response;
    }
}
