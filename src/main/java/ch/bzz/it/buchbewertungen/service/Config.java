package ch.bzz.it.buchbewertungen.service;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;


/**
 * Config for restful-webservice
 * <p>
 * Book Review
 *
 * @author Felix Reiniger
 * @version 1.0
 * @since 27.02.20
 */

@ApplicationPath("/resource")

public class Config extends Application {
    private static final String PROPERTIES_PATH = "/home/bzz/webapp/review.properties";
    private static Properties properties = null;


    /**
     * Add modelclasses
     * @return
     */
    @Override
    public Set<Class<?>> getClasses() {
        HashSet hashSet = new HashSet<Class<?>>();
        hashSet.add(ReviewService.class);
        hashSet.add(BookService.class);
        hashSet.add(UserService.class);
        hashSet.add(AuthorService.class);
        return hashSet;
    }

    /**
     * Returns property
     * @param property
     * @return
     */
    public static String getProperty(String property) {
        if (Config.properties == null) {
            setProperties(new Properties());
            readProperties();
        }
        String value = Config.properties.getProperty(property);
        if (value == null) return "";
        return value;
    }

    /**
     * Loads config file
     */
    private static void readProperties() {

        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(PROPERTIES_PATH);
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        } finally {

            try {
                if (inputStream != null) inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException();
            }

        }

    }


    /**
     * sets the properties
     * @param properties
     */
    private static void setProperties(Properties properties) {
        Config.properties = properties;
    }
}
