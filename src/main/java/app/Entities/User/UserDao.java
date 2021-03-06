package app.Entities.User;

import app.DB.PostgreConnector;
import app.Entities.Type.TypeDao;
import app.Util.MyLogger;
import org.eclipse.jetty.util.StringUtil;
import java.sql.*;
import java.util.ArrayList;
import static app.DB.Query.*;

public class UserDao {
    private static MyLogger logger = MyLogger.getLogger(TypeDao.class);

    public static ArrayList<User> getUsers() throws SQLException {
        ArrayList<User> users = new ArrayList<User>();
        Connection connection = PostgreConnector.createConnection();
        try
        {
            PostgreConnector.createConnection();
            ResultSet resultSet = PostgreConnector.executeSQL(connection, SELECT_TABLE_USERS);

            while (resultSet.next())
            {
                String userId = resultSet.getString("user_id");
                String name = resultSet.getString("name");

                if(StringUtil.isEmpty(userId) && StringUtil.isEmpty(name))
                    continue;

                users.add(new User(userId, name));
            }
        }
        catch (SQLException e)
        {
            logger.error(e);
        }
        finally
        {
            connection.close();
        }
        return users;
    }

    public static ArrayList<User> getUser(String login) throws SQLException {
        ArrayList<User> users = new ArrayList<User>();
        Connection connection = PostgreConnector.createConnection();
        try
        {
            PostgreConnector.createConnection();
            PreparedStatement preparedStatement = PostgreConnector.createStatement(connection, SELECT_USER_BY_LOGIN);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
            {
                String userId = resultSet.getString("user_id");
                String password = resultSet.getString("password");
                String roleId = resultSet.getString("role_id");
                String name = resultSet.getString("name");

                if(!StringUtil.isEmpty(password) && !StringUtil.isEmpty( userId ) && !StringUtil.isEmpty(roleId) && !StringUtil.isEmpty(name))
                    users.add(new User(userId, password, roleId, name));
            }
        }
        catch (SQLException e)
        {
            logger.error(e);
        }
        finally
        {
            connection.close();
        }
        return users;
    }
}
