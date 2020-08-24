package ch.bzz.it.buchbewertungen.service;

import ch.bzz.it.buchbewertungen.data.DataHandler;
import ch.bzz.it.buchbewertungen.model.Author;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
    public Response listAuthors(){
        Map<String, Author> authorMap = DataHandler.getAuthors();
        Response response = Response
                .status(200)
                .entity(authorMap)
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
    public Response readAuthor(@QueryParam("uuid") String uuidString){
        int httpCode = 404;
        try{
            UUID uuid = UUID.fromString(uuidString);
            Map<String, Author> authorMap = DataHandler.getAuthors();
            if(authorMap.containsKey(uuid.toString())) {
                Author author = authorMap.get(uuid.toString());
                httpCode = 200;
                return Response
                        .status(httpCode)
                        .entity(author)
                        .build();
            }
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
            Map<String, Author> authorMap = DataHandler.getAuthors();

            UUID uuid = UUID.randomUUID();
            author.setUuid(uuid);

            authorMap.put(uuid.toString(), author);
            DataHandler.writeAuthors(authorMap);
            httpStatus = 200;
        }else{
            httpStatus = 403;
        }
        Response response = Response
                .status(httpStatus)
                .build();

        return response;
    }


    /**
     * Service-Method to update exsiting authors
     *
     * @param authorid
     * @param name
     * @param language
     * @return
     */
    @Path("update")
    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateAuthor(
            @FormParam("authorId") String authorid,
            @FormParam("name") String name,
            @FormParam("language") String language,
            @CookieParam("userRole") String userRole
    ){
        int httpStatus;
        if(!(userRole == null) && userRole.equals("admin")) {
            Map<String, Author> authorMap = DataHandler.getAuthors();
            if (authorMap.containsKey(authorid)) {
                Author author = authorMap.get(authorid);
                author.setName(name);
                author.setLanguage(language);

                DataHandler.writeAuthors(authorMap);

                httpStatus = 200;
            }else{
                httpStatus = 404;
            }
        }else{
            httpStatus = 403;
        }

        return Response
                .status(httpStatus)
                .build();
    }

    /**
     * Deleting a specific author
     *
     * @param uuid UUID of the author that will be deleted
     * @return Response
     */
    @Path("delete")
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteAuthor(
            @QueryParam("uuid") String uuid,
            @CookieParam("userRole") String userRole
    ){
        int httpStatus;
        if(!(userRole == null) && userRole.equals("admin")) {
            Map<String, Author> authorMap = DataHandler.getAuthors();

            if (authorMap.containsKey(uuid)) {
                authorMap.remove(uuid);

                DataHandler.writeAuthors(authorMap);

                httpStatus = 200;
            }else{
                httpStatus = 404;
            }
        }else{
            httpStatus = 403;
        }
        return Response
                .status(httpStatus)
                .build();
    }






}
