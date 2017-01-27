package util.interfaces;

import java.sql.ResultSet;

/**
 * Created by Bartłomiej Dziwoń on 22.01.2017.
 */
public interface DatabaseSqlInterface {
    String makeUpdateSql();
    String makeDeleteSql();
    String makeInsertSql();
    String makeSelectSql();
    int setId(int id);
    Object resultSetToObject(ResultSet resultSet);
}
