package sample.model;

public class User {
    private int id;
    private String username;
    private Role role;

    public User(int id, String userName, Role role) {
        this.id = id;
        this.username = userName;
        this.role = role;
    }
    public User(int id, String userName) {
        this.id = id;
        this.username = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Role getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return username;
    }
}