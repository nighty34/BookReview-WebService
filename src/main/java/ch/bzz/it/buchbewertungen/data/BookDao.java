package ch.bzz.it.buchbewertungen.data;

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
    public Book getEntity(String bookUUID){
        Book book = new Book();
        //TODO SQL Query
        String sqlQuery = "";
        try {
            Connection connection = MySqlDB.getInstance().getConnection();
            PreparedStatement prepStmt = connection.prepareStatement(sqlQuery);
            ResultSet resultSet = prepStmt.executeQuery();
            if (resultSet.next()){
                // TODO Setters
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
    public List<Book> getAll(String bookUUID){
        Connection connection;
        PreparedStatement preStmt;
        ResultSet resultSet;
        List<Book> bookList = new ArrayList<>();
        String sqlQuery = "";

        try{
            connection = MySqlDB.getInstance().getConnection();
            preStmt = connection.prepareStatement(sqlQuery);
            resultSet = preStmt.executeQuery();
            while (resultSet.next()){
                Book book = new Book();
                //TODO Settings

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
        Connection connection;
        PreparedStatement prepStmt;
        String sqlQuery = "";

        try{
            connection = MySqlDB.getInstance().getConnection();
            prepStmt = connection.prepareStatement(sqlQuery);
            int affectedRows = prepStmt.executeUpdate();
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
