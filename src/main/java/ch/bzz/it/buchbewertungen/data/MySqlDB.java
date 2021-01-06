package ch.bzz.it.buchbewertungen.data;


import ch.bzz.it.buchbewertungen.service.Config;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * common methods for MySQL database
 * <p>
 * M151: BookshelfDB
 *
 * @author Lukas Buchli
 */

public class MySqlDB {
    private static MySqlDB instance;
    private Connection connection = null;
    private PreparedStatement prepStmt;
    private ResultSet resultSet;

    /**
     * default constructor: defeat instantiation
     */
    private MySqlDB() {
        getConnection();
    }

    public static MySqlDB getInstance() {
        if (instance == null) {
            instance = new MySqlDB();
        }

        return instance;
    }

    /**
     * Close resultSet and prepared statement
     */
    void sqlClose() {
        try {
            if (getResultSet() != null) getResultSet().close();
            if (getPrepStmt() != null) getPrepStmt().close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }


    /**
     * Gets the connection: open new connection if needed
     *
     * @return connection
     */
    public Connection getConnection() {
        try {
            if (connection == null || !connection.isValid(2)) {
                InitialContext initialContext = new InitialContext();
                DataSource dataSource = (DataSource) initialContext.lookup(
                        Config.getProperty("jdbcRessource")
                );
                setConnection(dataSource.getConnection());
            }
        } catch (NamingException namingException) {
            namingException.printStackTrace();
            throw new RuntimeException();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw new RuntimeException();
        }

        return connection;
    }

    public void setValues(String... vals) throws SQLException {
        for (int i = 0; i < vals.length; i++) {
            prepStmt.setString(i+1, vals[i]);
        }
    }

    public synchronized ResultSet sqlSelect(String query, String... vals) throws SQLException {
        prepStmt = connection.prepareStatement(query);
        setValues(vals);
        return prepStmt.executeQuery();
    }

    public synchronized int sqlUpdate(String query, String... vals) throws SQLException {
        prepStmt = connection.prepareStatement(query);
        setValues(vals);
        return prepStmt.executeUpdate();
    }

    /**
     * Sets the connection
     *
     * @param connection the value to set
     */

    private void setConnection(Connection connection) {
        connection = connection;
    }

    /**
     * Gets the prepStmt
     *
     * @return value of prepStmt
     */
    private PreparedStatement getPrepStmt() {
        return prepStmt;
    }

    /**
     * Sets the prepStmt
     *
     * @param prepStmt the value to set
     */

    public void setPrepStmt(PreparedStatement prepStmt) {
        prepStmt = prepStmt;
    }

    /**
     * Gets the resultSet
     *
     * @return value of resultSet
     */
    public ResultSet getResultSet() {
        return resultSet;
    }

    /**
     * Sets the resultSet
     *
     * @param resultSet the value to set
     */

    public void setResultSet(ResultSet resultSet) {
        resultSet = resultSet;
    }
}
