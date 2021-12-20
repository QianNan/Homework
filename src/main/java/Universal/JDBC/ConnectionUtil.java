package Universal.JDBC;

import Universal.envConfig.EnvironmentConfig;

import java.sql.*;


public class ConnectionUtil {

    private static String dataBaseName;

    public static String getDataBaseName() {
        return dataBaseName;
    }

    public static void setDataBaseName(String dataBaseName) {
        ConnectionUtil.dataBaseName = dataBaseName;
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            EnvironmentConfig.readEnvConfigProperties();
            Class.forName(EnvironmentConfig.driver);
            System.out.println("当前URL是："+EnvironmentConfig.url);
            connection = DriverManager.getConnection(EnvironmentConfig.url, EnvironmentConfig.userName, EnvironmentConfig.password);
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closeStatement(Statement statement) {
        try {
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closePreparedStatement(PreparedStatement pStatement) {
        try {
            pStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closeResultSet(ResultSet rs) {
        try {
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        System.out.println(getConnection());
    }

}
