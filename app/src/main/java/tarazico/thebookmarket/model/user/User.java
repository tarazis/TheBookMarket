package tarazico.thebookmarket.model.user;

public class User {
    private String userID;
    private String email;
    private String displayName;

    public User(String userID, String email) {
        this.userID = userID;
        this.email = email;
//        this.displayName = displayName;
    }
    public User() {

    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
