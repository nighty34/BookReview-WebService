package ch.bzz.it.buchbewertungen.data;

import ch.bzz.it.buchbewertungen.model.Author;
import ch.bzz.it.buchbewertungen.util.Result;
import ch.bzz.it.buchbewertungen.data.MySqlDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * short description
 * <p>
 * BookReview-WebService
 *
 * @author TODO
 * @version 1.0
 * @since 06.01.21
 */
public class AuthorDao implements Dao<Author, String> {

    @Override
    public List<Author> getAll(String authorUUID){
        List<Author> authors = new ArrayList<>();
        String sqlQuery = "";

        try{
            MySqlDB.getInstance().setResultSet(MySqlDB.getInstance().sqlSelect(sqlQuery));

            while (MySqlDB.getInstance().getResultSet().next()) {
                Author author = new Author();
                author.setUuid(UUID.fromString(MySqlDB.getInstance().getResultSet().getString("")));
                author.setName(MySqlDB.getInstance().getResultSet().getString(""));
                author.setLanguage(MySqlDB.getInstance().getResultSet().getString(""));
                authors.add(author);
            }

        }catch (SQLException sqlEx){
            sqlEx.printStackTrace();
        }finally {
            MySqlDB.getInstance().sqlClose();
        }

        return authors;
    }



    @Override
    public Result save(Author author){
        String sqlQuery = null;

        try {
            Integer result;
            if(author.getUuid()==null){
                sqlQuery = "INSERT INTO ... SET ";
                result = MySqlDB.getInstance().sqlUpdate(sqlQuery, ...)
            }else{
                sqlQuery = "UPDATE ... SET WHERE";
                result = MySqlDB.getInstance().sqlUpdate(sqlQuery, ...);
            }


            if (result == 2 || result == 1) {
                return Result.SUCCESS;
            } else if (result == 0) {
                return Result.NOACTION;
            } else {
                return Result.ERROR;
            }
        }catch (SQLException sqlEx){
            sqlEx.printStackTrace();
            throw new RuntimeException();
        }finally {
            MySqlDB.getInstance().sqlClose();
        }
    }


    @Override
    public Author getEntity(String filter){

    }





}
