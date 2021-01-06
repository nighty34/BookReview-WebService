package ch.bzz.it.buchbewertungen.data;

import ch.bzz.it.buchbewertungen.model.User;
import ch.bzz.it.buchbewertungen.util.Result;

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
 * @author Lukas Buchli
 * @version 1.0
 * @since 06.01.21
 */
public class UserDao implements Dao<User, String> {

    @Override
    public List<User> getAll(String s) {
        List<User> userList = new ArrayList<>();

        try {
            ResultSet resultSet = MySqlDB.getInstance().sqlSelect(
                    "SELECT uuidUser, name, about, password, role FROM user WHERE " + s
            );
            while (resultSet.next()) {
                User user = new User();
                user.setUuid(UUID.fromString(resultSet.getString(1)));
                user.setName(resultSet.getString(2));
                user.setAbout(resultSet.getString(3));
                user.setPassword(resultSet.getString(4));
                user.setRole(resultSet.getString(5));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        } finally {
            MySqlDB.getInstance().sqlClose();
        }

        return userList;
    }

    @Override
    public User getEntity(String s) {
        return null;
    }

    @Override
    public Result save(User user) {
        return null;
    }
}
