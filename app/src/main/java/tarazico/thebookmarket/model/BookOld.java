//package tarazico.thebookmarket.model;
//
//import android.graphics.Bitmap;
//
//import androidx.room.Entity;
//import androidx.room.Ignore;
//import androidx.room.PrimaryKey;
//
//@Entity(tableName = "book_table")
//public class Book {
//
//    @PrimaryKey(autoGenerate = true)
//    private int id;
//    private String title;
//    private String course;
//    private String desc;
//    private double price; // TODO: refactor this class and other classes to include price
//
//
//
//
//
//    //private Bitmap pic;
//
//
//    public Book(String title, String course, String desc, double price) {
//        this.title = title;
//        this.course = course;
//        this.desc = desc;
//        this.price = price;
////        this.pic = pic;
//    }
//
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public int getId() {
//        return this.id;
//    }
//
////    public Book(Bitmap picture) {
////        this.pic = picture;
////    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getCourse() {
//        return course;
//    }
//
//    public void setCourse(String course) {
//        this.course = course;
//    }
//
//    public String getDesc() {
//        return desc;
//    }
//
//    public void setDesc(String desc) {
//        this.desc = desc;
//    }
//
//    public double getPrice() {
//        return price;
//    }
//
//    public void setPrice(double price) {
//        this.price = price;
//    }
//
//
////    public Bitmap getPic() {
////        return pic;
////    }
//
////    public void setPic(Bitmap pic) {
////        this.pic = pic;
////    }
//}