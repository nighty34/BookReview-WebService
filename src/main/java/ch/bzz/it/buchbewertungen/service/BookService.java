package ch.bzz.it.buchbewertungen.service;

import ch.bzz.it.buchbewertungen.data.DataHandler;
import ch.bzz.it.buchbewertungen.model.Author;
import ch.bzz.it.buchbewertungen.model.Book;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
    public Response listBooks(){
        Map<String, Book> bookList = DataHandler.getBooks();
        Response response = Response
                .status(200)
                .entity(bookList)
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
    public Response readBook(@QueryParam("uuid") String uuidString){ //TODO: find better solution
        int httpCode = 404;
        try{
            UUID uuid = UUID.fromString(uuidString);
            Map<String, Book> bookMap = DataHandler.getBooks();
            if(bookMap.containsKey(uuid.toString())) {
                Book book = bookMap.get(uuid.toString());
                httpCode = 200;
                return Response.status(httpCode)
                        .entity(book)
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
        Map<String, Book> bookMap = null;
        if(!(userRole == null) && userRole.equals("admin")){
            bookMap = DataHandler.getBooks();

            UUID uuid = UUID.randomUUID();
            book.setUuid(uuid);

            Author author = DataHandler.getAuthors().get(authorid);
            if(author!=null) {
                book.setAuthor(author);
                bookMap.put(uuid.toString(), book);
                DataHandler.writeBooks(bookMap);

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
     * Service Method for updating a existing book
     *
     * @param bookid UUID of the book that will be edited
     * @param title Title of the book
     * @param series SeriesName of the book
     * @param isbn isbn-13 of the book
     * @param price price of the book
     * @return Response
     */
    //TODO: Use @FormParam Book book instead of title, series, etc.
    @Path("update")
    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateBook(
            @FormParam("bookid") String bookid,
            @FormParam("title") String title,
            @FormParam("series") String series,
            @FormParam("isbn") String isbn,
            @FormParam("price") double price,
            @CookieParam("userRole") String userRole
    ){
        int httpStatus;
        if(!(userRole == null) && userRole.equals("admin")){
            Map<String, Book> bookMap = DataHandler.getBooks();
            if (bookMap.containsKey(bookid)) {
                Book book = bookMap.get(bookid);
                book.setTitle(title);
                book.setSeriesName(series);
                book.setiSBN(isbn);
                book.setPrice(price);

                //FIXME: Update Book in Map

                DataHandler.writeBooks(bookMap);

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
     * Deleting a specific book
     *
     * @param uuid UUID of the book that will be deleted
     * @return Response
     */
    @Path("delete")
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteBook(
            @QueryParam("uuid") String uuid,
            @CookieParam("userRole") String userRole
    ){
        int httpStatus;
        if(!(userRole == null) && userRole.equals("admin")) {

            Map<String, Book> bookMap = DataHandler.getBooks();

            if (bookMap.containsKey(uuid)) {
                bookMap.remove(uuid);

                DataHandler.writeBooks(bookMap);

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
