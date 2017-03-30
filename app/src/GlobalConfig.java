public class GlobalConfig {
    private static GlobalConfig instance = null;

    private String dbLogin;
    private String dbPassword;
    private String dbSchema;

    protected GlobalConfig() {
        Load();
    }

    protected void Load() {
        dbLogin = "root";
        dbPassword = "2246Baklan";
        dbSchema = "sys";
    }

    public String getDBLogin() {
        return dbLogin;
    }

    public String getDBPassword() {
        return dbPassword;
    }

    public String getDBSchema() {
        return dbSchema;
    }

    public static GlobalConfig getInstance() {
        if (instance == null) {
            instance = new GlobalConfig();
        }

        return instance;
    }
}
