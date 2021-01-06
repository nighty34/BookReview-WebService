package ch.bzz.it.buchbewertungen.service;

import ch.bzz.it.buchbewertungen.data.AuthorDao;
import ch.bzz.it.buchbewertungen.data.DataHandler;
import ch.bzz.it.buchbewertungen.model.Author;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Service class for Author related methods
 * <p>
 * BookReviews
 *
 * @author Felix Reiniger
 * @version 1.0
 * @since 02.04.20
 */

@Path("author")
public class AuthorService {

    /**
     * Service for listing all available Authors
     *
     * @return Response
     */
    @Path("list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAuthors(@QueryParam("filter") String filter){
        List<Author> authorList = new AuthorDao().getAll(filter);
        Response response = Response
                .status(200)
                .entity(authorList)
                .build();

        return response;
    }


    /**
     * Service Method for reading a specific author
     *
     * @param uuidString
     * @return
     */
    @Path("read")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response readAuthor(@QueryParam("filter") String filter){
        int httpCode = 404;
        try{

            Author author = new AuthorDao().getEntity(filter);
                httpCode = 200;
                return Response
                        .status(httpCode)
                        .entity(author)
                        .build();

        }catch (IllegalArgumentException e){
            httpCode = 400;
        }
        return Response
                .status(httpCode)
                .build();
    }


    /**
     * Service Method for creating a new author
     *
     * @param author Created author object
     */
    @Path("create")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response createAuthor(
            @Valid @BeanParam Author author,
            @CookieParam("userRole") String userRole
    ){
        int httpStatus;
        if(!(userRole == null) && userRole.equals("admin")) {

            httpStatus = new AuthorDao().save(author).getCode();
        }else{
            httpStatus = 403;
        }




        Response response = Response
                .status(httpStatus)
                .build();

        return response;
    }


    /**
     * Update existign Authors
     * @param author
     * @param userRole
     * @return
     */
    @Path("update")
    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateAuthor(
            @Valid @BeanParam Author author,
            @CookieParam("userRole") String userRole
    ) {
        int httpStatus;
        if (!(userRole == null) && userRole.equals("admin")) {

            httpStatus = new AuthorDao().save(author).getCode();

        } else {
            httpStatus = 403;
        }

        return Response
                .status(httpStatus)
                .build();
    }






}
