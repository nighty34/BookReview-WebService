package ch.bzz.it.buchbewertungen.data;

import ch.bzz.it.buchbewertungen.model.Author;
import ch.bzz.it.buchbewertungen.model.Book;
import ch.bzz.it.buchbewertungen.util.Result;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * short description
 * <p>
 * BookReview-WebService
 *
 * @author Noah Altenburger
 * @version 1.0
 * @since 06.01.21
 */
public class BookDao implements Dao<Book, String> {

    @Override
    public Book getEntity(String filter){
        Book book = new Book();
        //TODO SQL Query
        String sqlQuery = "SELECT * FROM book WHERE ?";
        try {
            ResultSet resultSet = MySqlDB.getInstance().sqlSelect(sqlQuery, filter);
            if (resultSet.next()){
                book.setAuthor(new AuthorDao().getEntity("uuidAuthor="+resultSet.getString("uuidAuthor")));
                book.setiSBN(resultSet.getString("isbn"));
                book.setPrice(resultSet.getDouble("price"));
                book.setSeriesName("seriesname");
                book.setTitle("title");
            }

        } catch (SQLException sqlEx){
            sqlEx.printStackTrace();
            throw new RuntimeException();
        } finally {
            MySqlDB.getInstance().sqlClose();
        }
        return book;
    }

    @Override
    public List<Book> getAll(String filter){
        ResultSet resultSet;
        List<Book> bookList = new ArrayList<>();
        String sqlQuery = "SELECT * FROM book WHERE ?";

        try{
            resultSet = MySqlDB.getInstance().sqlSelect(sqlQuery, filter);
            while (resultSet.next()){
                Book book = new Book();
                book.setAuthor(new AuthorDao().getEntity("uuidAuthor="+resultSet.getString("uuidAuthor")));
                book.setiSBN(resultSet.getString("isbn"));
                book.setPrice(resultSet.getDouble("price"));
                book.setSeriesName("seriesname");
                book.setTitle("title");
                bookList.add(book);
            }
        }catch (SQLException  sqlEx){
            sqlEx.printStackTrace();
            throw new RuntimeException();
        } finally {
            MySqlDB.getInstance().sqlClose();
        }
        return bookList;

    }

    @Override
    public Result save(Book book){

        String sqlQuery = "INSERT INTO books SET uuidBook=?, title=?, seriesname=?, uuidAuthor=?, isbn=?, price=?";

        try{
            int affectedRows = MySqlDB.getInstance().sqlUpdate(sqlQuery, book.getUuid().toString(), book.getTitle(), book.getSeriesname(), book.getAuthor().getUuid().toString(), book.getiSBN(), "" + book.getPrice());
            if (affectedRows <= 2) {
                return Result.SUCCESS;
            } else if (affectedRows == 0) {
                return Result.NOACTION;
            } else {
                return Result.ERROR;
            }

        } catch (SQLException sqlEx){
            sqlEx.printStackTrace();
            throw new RuntimeException();
        }
    }
}
