package ch.bzz.it.buchbewertungen.service;

import ch.bzz.it.buchbewertungen.data.DataHandler;
import ch.bzz.it.buchbewertungen.data.ReviewDao;
import ch.bzz.it.buchbewertungen.model.Review;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.UUID;

/**
 * provides services for review
 * <p>
 * BookReviews
 *
 * @author Felix Reiniger
 * @version 1.0
 * @since 05.03.20
 */
@Path("review")

public class ReviewService {


    /**
     * Service method for printing a list of all available reviews
     *
     * @return
     */
    @Path("list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response istReviews(){
        Map<String, Review> reviewList = DataHandler.getReviews();
        Response response = Response.status(200).
                entity(reviewList).
                build();

        return response;
    }


    /**
     * Service method for reading specific review
     *
     * @param filter    UUID for identifying review
     * @return  Response
     */
    @Path("read")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response readReviews(@QueryParam("filter") String filter){ //TODO: find better solution
        int httpCode = 404;

        try{
            Review review = new ReviewDao().getEntity(filter);
            httpCode = 200;
            return Response
                    .status(httpCode)
                    .entity(review)
                    .build();
        }catch (IllegalArgumentException e){
            httpCode = 400;
        }

        return Response
                .status(httpCode)
                .build();

    }

    /**
     * Service method for creating new reviews
     *
     * @param userid    UUID of the review author
     * @param bookid    UUID of the reviewed book
     * @param review    review object (includes comment and value)
     * @return  Response
     */
    @Path("create")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response createReview(
            @FormParam("userid") String userid,
            @FormParam("bookid") String bookid,
            @Valid @BeanParam Review review,
            @CookieParam("userRole") String userRole
    ){
        int httpStatus;
        if(!(userRole == null) && userRole.equals("admin")) {
            Map<String, Review> reviewList = DataHandler.getReviews();

            UUID uuid = UUID.randomUUID();
            review.setUuid(uuid);

            review.setBook(DataHandler.getBooks().get(bookid));
            review.setUser(DataHandler.getUsers().get(userid));

            reviewList.put(uuid.toString(), review);
            DataHandler.writeReviews(reviewList);

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
     * Service Method for updating a existing review
     *
     * @param reviewid  UUID for identifying the review
     * @param userid    UUID of review author
     * @param bookid    UUID of reviewed book
     * @return
     */
    @Path("update")
    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateReview(
            @FormParam("reviewid") String reviewid,
            @FormParam("userid") String userid,
            @FormParam("bookid") String bookid,
            @FormParam("value") int value,
            @FormParam("comment") String comment,
            @CookieParam("userRole") String userRole
    ){
        int httpStatus;
        if(!(userRole == null) && userRole.equals("admin")) {
            Map<String, Review> reviewList = DataHandler.getReviews();
            if (reviewList.containsKey(reviewid)) {
                Review review = reviewList.get(reviewid);
                review.setUser(DataHandler.getUsers().get(userid));
                review.setBook(DataHandler.getBooks().get(bookid));
                review.setValue(value);
                review.setComment(comment);

                DataHandler.writeReviews(reviewList);


                httpStatus = 200;
            }else{
                httpStatus = 404;
            }
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
     * @param uuid UUID of trageted Review
     * @return Response
     */
    @Path("delete")
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteReview(
            @QueryParam("uuid") String uuid,
            @CookieParam("userRole") String userRole
    ){
        int httpStatus;
        if(!(userRole == null) && userRole.equals("admin")) {
            Map<String, Review> reviewList = DataHandler.getReviews();

            if (reviewList.containsKey(uuid)) {
                reviewList.remove(uuid);

                DataHandler.writeReviews(reviewList);

                httpStatus = 200;
            }else{
                httpStatus = 404;
            }
        }else{
            httpStatus = 403;
        }
        Response response = Response
                .status(httpStatus)
                .build();
        return response;
    }
}
