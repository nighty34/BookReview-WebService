package ch.bzz.it.buchbewertungen.service;

import ch.bzz.it.buchbewertungen.data.DataHandler;
import ch.bzz.it.buchbewertungen.model.User;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.UUID;

/**
 * user service-class
 * <p>
 * BookReviews
 *
 * @author Felix Reiniger
 * @version 1.0
 * @since 05.03.20
 */
@Path("user")
public class UserService {

    //HI
    @Path("list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listUser(
            @CookieParam("userRole") String userRole
    ){
        int httpStatus;
        Map<String, User> userList = null;
        if(!(userRole == null) && userRole.equals("admin")) {
            userList = DataHandler.getUsers();
            httpStatus = 200;
        }else{
            httpStatus = 403;
        }
        Response response = Response
                .status(httpStatus)
                .entity(userList)
                .build();

        return response;
    }

    @Path("read")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response readUser(
            @QueryParam("uuid") String uuidString,
            @CookieParam("userRole") String userRole
    ){ //TODO: find better solution
        int httpCode;
        User user = null;
        if(!(userRole == null) && userRole.equals("admin")) {
            try {
                UUID uuid = UUID.fromString(uuidString);
                Map<String, User> userList = DataHandler.getUsers();
                if(userList.containsKey(uuid.toString())) {
                   user = userList.get(uuid.toString());
                   httpCode = 200;
                }else{
                    httpCode = 404;
                }
            } catch (IllegalArgumentException e) {
                httpCode = 400;
            }
        }else{
            httpCode = 403;
        }

        return Response.status(httpCode)
                .entity(user)
                .build();
    }


    @POST
    @Path("login")
    @Produces(MediaType.TEXT_PLAIN)
    public Response login(
            @FormParam("username") String username,
            @FormParam("password") String password
    ){
        int httpStatus;
        NewCookie cookie = null;
        User user = DataHandler.getUserByName(username);
        if(!(user == null) && user.getPassword().equals(password)) { //TODO: Wat?
            cookie = new NewCookie(
                    "userRole",
                    user.getRole(),
                    "/",
                    "",
                    "Role-Cookie",
                    600,
                    false
            );
            httpStatus = 200;
        }else{
            httpStatus = 400;
        }

        Response response = Response
                .status(httpStatus)
                .cookie(cookie)
                .build();
        return response;
    }

    @DELETE
    @Path("logout")
    @Produces(MediaType.TEXT_PLAIN)
    public Response logout(){
        NewCookie cookie = new NewCookie(
                "userRole",
                "guest",
                "/",
                "",
                "Role-Cookie",
                1,
                false
        );
        Response response = Response
                .status(200)
                .cookie(cookie)
                .build();
        return response;
    }

    @POST
    @Path("register")
    @Produces(MediaType.TEXT_PLAIN)
    public Response register(
            @Valid @BeanParam User user
    ){
        int httpStatus;
        Map<String, User> userMap = DataHandler.getUsers();
        if(DataHandler.getUserByName(user.getName())==null) {
            UUID uuid = UUID.randomUUID();
            user.setRole("guest");
            user.setUuid(uuid);
            httpStatus = 200;
            userMap.put(uuid.toString(), user);
            DataHandler.writeUsers(userMap);
        }else{
            httpStatus = 409;
        }

        Response response = Response
                .status(httpStatus)
                .build();
        return response;
    }
}
