package sample.utils;
public final class UserSession {

    private static UserSession instance;

    private int id;
    private String username;

    private UserSession(int id, String userName) {
        this.id = id;
        this.username = userName;
    }

    public static UserSession getInstance(int id, String userName) {
        if(instance == null) {
            instance = new UserSession(id, userName);
        }
        return instance;
    }

    public static UserSession getInstance() {
        return instance;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void cleanUserSession() {
        instance=null;
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}