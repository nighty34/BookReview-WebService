package ch.bzz.it.buchbewertungen.data;

import ch.bzz.it.buchbewertungen.model.Author;
import ch.bzz.it.buchbewertungen.model.Book;
import ch.bzz.it.buchbewertungen.model.Review;
import ch.bzz.it.buchbewertungen.model.User;
import ch.bzz.it.buchbewertungen.util.Result;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Review Data Access Object
 * <p>
 * BookReview-WebService
 *
 * @author Lukas Buchli
 * @version 1.0
 * @since 06.01.21
 */
public class ReviewDao implements Dao<Review, String> {

    @Override
    public List<Review> getAll(String s) {
        List<Review> reviewList = new ArrayList<>();
        try {
            ResultSet resultSet = MySqlDB.getInstance().sqlSelect(
                    "SELECT r.uuidReview, r.value, r.comment," +
                            " u.uuidUser, u.name, u.about, u.password, u.role," +
                            " b.uuidBook, b.title, b.seriesname, b.isbn, b.price," +
                            " a.uuidAuthor, a.language, a.name FROM review AS r" +
                            " JOIN user AS u USING (uuidUser)" +
                            " JOIN book AS b USING (uuidBook)" +
                            " JOIN author AS a USING (uuidAuthor) WHERE " + s
            );

            while (resultSet.next()) {
                reviewList.add(constructReview(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        } finally {
            MySqlDB.getInstance().sqlClose();
        }

        return reviewList;
    }

    @Override
    public Review getEntity(String s) {
        Review review = new Review();
        try {
            ResultSet resultSet = MySqlDB.getInstance().sqlSelect(
                    "SELECT r.uuidReview, r.value, r.comment," +
                            " u.uuidUser, u.name, u.about, u.password, u.role," +
                            " b.uuidBook, b.title, b.seriesname, b.isbn, b.price," +
                            " a.uuidAuthor, a.language, a.name FROM review AS r" +
                            " JOIN user AS u USING (uuidUser)" +
                            " JOIN book AS b USING (uuidBook)" +
                            " JOIN author AS a USING (uuidAuthor) WHERE " + s
            );

            if (resultSet.next()) {
                review = constructReview(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        } finally {
            MySqlDB.getInstance().sqlClose();
        }

        return review;
    }

    @Override
    public Result save(Review review) {

        try {
            int affectedRows = MySqlDB.getInstance().sqlUpdate(
                    "REPLACE Review SET uuidReview=?, uuidUser=?, uuidBook=?, value=?, comment=?",
                    review.getUuid().toString(),
                    review.getUser().getUuid().toString(),
                    review.getBook().getUuid().toString(),
                    String.valueOf(review.getValue()),
                    review.getComment()
            );

            if (affectedRows <= 2) {
                return Result.SUCCESS;
            } else if (affectedRows == 0) {
                return Result.NOACTION;
            } else {
                return Result.ERROR;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        } finally {
            MySqlDB.getInstance().sqlClose();
        }
    }

    private Review constructReview(ResultSet resultSet) throws SQLException {
        Review review = new Review();
        User user = new User();
        Book book = new Book();
        Author author = new Author();
        review.setUuid(UUID.fromString(resultSet.getString(1)));
        review.setValue(resultSet.getInt(2));
        review.setComment(resultSet.getString(3));
        user.setUuid(UUID.fromString(resultSet.getString(4)));
        user.setName(resultSet.getString(5));
        user.setAbout(resultSet.getString(6));
        user.setPassword(resultSet.getString(7));
        user.setRole(resultSet.getString(8));
        book.setUuid(UUID.fromString(resultSet.getString(9)));
        book.setTitle(resultSet.getString(10));
        book.setSeriesName(resultSet.getString(11));
        book.setiSBN(resultSet.getString(12));
        book.setPrice(resultSet.getDouble(13));
        author.setUuid(UUID.fromString(resultSet.getString(14)));
        author.setLanguage(resultSet.getString(15));
        author.setName(resultSet.getString(16));
        book.setAuthor(author);
        review.setBook(book);
        review.setUser(user);

        return review;
    }
}
