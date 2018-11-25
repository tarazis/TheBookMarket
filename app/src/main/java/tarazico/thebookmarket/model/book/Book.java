package tarazico.thebookmarket.model.book;

public class Book {

    private String userID; // Unique user ID
    private String title;
    private String courseName;
    private String desc;
    private double price;
    private String url;


    public Book(String userID, String title, String courseName, String desc, double price, String url) {
        this.userID = userID;
        this.title = title;
        this.courseName = courseName;
        this.desc = desc;
        this.price = price;
        this.url = url;
    }

    public Book(){} //needed for real time data

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUri(String url) {
        this.url = url;
    }
}
