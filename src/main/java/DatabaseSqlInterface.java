
public interface DatabaseSqlInterface {
    public String makeUpdateSql();
    public String makeDeleteSql();
    public String makeInsertSql();
    public String makeSelectSql();
    public int setId(int id);
}
