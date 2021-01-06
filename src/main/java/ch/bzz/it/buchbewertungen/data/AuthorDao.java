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
        String sqlQuery = "SELECT * FROM author";

        try{
            ResultSet result = MySqlDB.getInstance().sqlSelect(sqlQuery);

            while (result.next()) {
                Author author = new Author();
                author.setUuid(UUID.fromString(result.getString("")));
                author.setName(result.getString(""));
                author.setLanguage(result.getString(""));
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
                sqlQuery = "INSERT INTO auhtor SET uuidAuthor=?, language=?, name=?";
                result = MySqlDB.getInstance().sqlUpdate(sqlQuery, UUID.randomUUID().toString(), author.getLanguage(), author.getName());
            }else{
                sqlQuery = "UPDATE author SET WHERE uuidAuthor=?, language=?, name=?";
                result = MySqlDB.getInstance().sqlUpdate(sqlQuery, author.getUuid().toString(), author.getLanguage(), author.getName());
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
        Author author = new Author();
        try{
            ResultSet resultSet = MySqlDB.getInstance().sqlSelect(
                    "SELECT * FROM author WHERE " + filter
            );

            if(resultSet.next()){
                author.setUuid(UUID.fromString(resultSet.getString("uuidAuthor")));
                author.setLanguage(resultSet.getString("language"));
                author.setName(resultSet.getString("name"));
            }


        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        } finally {
            MySqlDB.getInstance().sqlClose();
        }


        return author;
    }





}
