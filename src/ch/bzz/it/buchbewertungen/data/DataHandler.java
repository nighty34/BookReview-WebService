package ch.bzz.it.buchbewertungen.data;

import ch.bzz.it.buchbewertungen.model.Author;
import ch.bzz.it.buchbewertungen.model.Book;
import ch.bzz.it.buchbewertungen.model.Review;
import ch.bzz.it.buchbewertungen.model.User;
import ch.bzz.it.buchbewertungen.service.Config;

import java.io.*;
import java.util.*;

/**
 * Provides the data for service-classes
 * <p>
 * BookReviews
 *
 * @author Felix Reiniger
 * @version 1.0
 * @since 12.03.20
 */
public class DataHandler {

    //TODO: FilePaths
    private static Map<String, Review> reviewMap = new HashMap<>();
    private static Map<String, Book> bookMap = new HashMap<>();
    private static Map<String, Author> authorMap = new HashMap<>();
    private static Map<String, User> userMap = new HashMap<>();


    //TODO: Read

    /**
     * Reads all reviews and puts them in reviewMap
     */
    private static void readReviews(){
        ArrayList<String[]> reviews = readFile("reviewFile"); //UUID, User, Book, Value, Comment
        try {
            for (String[] values : reviews) {
                Review review = new Review();
                review.setUuid(UUID.fromString(values[0]));
                User user = getUsers().get(values[2]); //TODO: getUsers() method
                review.setUser(user);
                Book book = getBooks().get(values[1]);
                review.setBook(book);
                review.setValue(new Integer(values[3]));
                review.setComment(values[4]);


                reviewMap.put(values[0], review);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * Reads all Books and puts them into bookMap
     */
    private static void readBooks(){
        ArrayList<String[]> books = readFile("bookFile");   //UUID, Title, SeriesName, Author, ISBN, Price
        try {
            for (String[] values : books) {
                Book book = new Book();
                book.setUuid(UUID.fromString(values[0]));
                book.setTitle(values[1]);
                book.setSeriesName(values[2]);
                Author author = getAuthors().get(values[3]);
                book.setAuthor(author);
                book.setiSBN(values[4]);
                book.setPrice(Double.parseDouble(values[5]));

                bookMap.put(values[0], book);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * Reads all Authors and puts them in authorMap
     */
    private static void readAuthors(){
        ArrayList<String[]> authors = readFile("authorFile");   //UUID, Name, Language
        try {
            for (String[] values : authors) {
                Author author = new Author();
                author.setUuid(UUID.fromString(values[0]));
                author.setName(values[1]);
                author.setLanguage(values[2]);
                authorMap.put(values[0], author);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * Reads all user and puts them into userMap
     */
    private static void readUsers(){
        ArrayList<String[]> users = readFile("userFile");   //UUID, Name, Password, Role, About, FavBook
        try {
            for (String[] values : users) {
                User user = new User();
                user.setUuid(UUID.fromString(values[0]));
                user.setName(values[1]);
                user.setPassword(values[3]);
                user.setRole(values[4]);
                user.setAbout(values[2]);
                //Book book = getBooks().get(values[5]);
                //user.setFavBook(book);
                userMap.put(values[0], user);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * Returns reviewMap
     * @return reviewMap
     */
    public static Map<String, Review> getReviews(){
        readUsers();
        readAuthors();
        readBooks();
        readReviews();
        return reviewMap;
    }

    /**
     * Returns bookMap
     * @return bookMap
     */
    public static Map<String, Book> getBooks(){
        readAuthors();
        readBooks();
        return bookMap;
    }

    /**
     * Returns authorMap
     * @return authorMap
     */
    public static Map<String, Author> getAuthors(){
        readAuthors();
        return authorMap;
    }

    /**
     * Returns userMap
     * @return userMap
     */
    public static Map<String, User> getUsers(){
        readUsers();
        return userMap;
    }

    public static User getUserByName(String name){
        readUsers();
        for(Map.Entry<String, User> entry : userMap.entrySet()) {
            if(entry.getValue().getName().equals(name)){
                return entry.getValue();
            }
        }
        return null;
    }


    /**
     * writes reviewMap into CSV-File
     * @param reviewMap
     */
    public static void writeReviews(Map<String, Review> reviewMap){
        ArrayList<String> outputs = new ArrayList<>();
        String output;
        for(Map.Entry<String, Review> entry : reviewMap.entrySet()){
            Review review = entry.getValue();
            output = String.join(";",
                    review.getUuid().toString(),
                    review.getBook().getUuid().toString(),
                    review.getUser().getUuid().toString(),
                    String.valueOf(review.getValue()),
                    review.getComment()
            );
            outputs.add(output);
        }

        writeFile("reviewFile", outputs);
    }

    /**
     * writes bookMap into CSV-File
     * @param bookMap
     */
    public static void writeBooks(Map<String, Book> bookMap){
        ArrayList<String> outputs = new ArrayList<>();
        String output;
        for(Map.Entry<String, Book> entry : bookMap.entrySet()){
            Book book = entry.getValue();
            output = String.join(";",
                    book.getUuid().toString(),
                    book.getTitle(),
                    book.getSeriesname(),
                    book.getAuthor().getUuid().toString(),
                    book.getiSBN(),
                    String.valueOf(book.getPrice())
            );
            outputs.add(output);
        }

        writeFile("bookFile", outputs);
    }

    /**
     * writes authorMap into CSV-File
     * @param authorMap
     */
    public static void writeAuthors(Map<String, Author> authorMap){
        ArrayList<String> outputs = new ArrayList<>();
        String output;
        for(Map.Entry<String, Author> entry : authorMap.entrySet()){
            Author author = entry.getValue();
            output = String.join(";",
                    author.getUuid().toString(),
                    author.getName(),
                    author.getLanguage()
            );
            outputs.add(output);
        }

        writeFile("authorFile", outputs);
    }

    /**
     * writes userMap into CSV-File
     * @param userMap
     */
    public static void writeUsers(Map<String, User> userMap){
        ArrayList<String> outputs = new ArrayList<>();
        String output;
        for(Map.Entry<String, User> entry : userMap.entrySet()){
            User user = entry.getValue();

            output = String.join(";",
                    user.getUuid().toString(),
                    user.getName(),
                    user.getAbout(),
                    user.getPassword(),
                    user.getRole()
/*, TODO:
                    favBook*/
            );
            outputs.add(output);
        }

        writeFile("userFile", outputs);
    }

    /**
     * writes into File
     * @param property
     * @param outputs
     */
    private static void writeFile(String property, List<String> outputs){
        Writer writer = null;
        FileOutputStream fileOutputStream = null;

        try {
            String path = Config.getProperty(property);
            fileOutputStream = new FileOutputStream(path);
            writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream, "utf-8"));
            for (String output : outputs) {
                writer.write(output + '\n');
            }
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
            throw new RuntimeException();

        } finally {

            try {
                if (writer != null) {
                    writer.close();
                }

                if (fileOutputStream != null) {
                    fileOutputStream.close();

                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }



    private static ArrayList<String[]> readFile(String property){
        BufferedReader bufferedReader;
        FileReader fileReader;
        try {
            String bookPath = Config.getProperty(property);
            fileReader = new FileReader(bookPath);
            bufferedReader = new BufferedReader(fileReader);



        } catch (FileNotFoundException fileEx) {
            fileEx.printStackTrace();
            throw new RuntimeException();
        }
        try{
            ArrayList<String[]> entries = new ArrayList<>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] entry = line.split(";");
                entries.add(entry);
            }
            return entries;
        } catch (IOException ioEx) {
                ioEx.printStackTrace();
        }
        return null;

    }



}
