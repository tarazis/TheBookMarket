package tarazico.thebookmarket.model.book;

public class BookPicture {
    String pictureID;
    String picturePath;

    public BookPicture() {}

    public BookPicture(String pictureID, String picturePath) {
        this.pictureID = pictureID;
        this.picturePath = picturePath;
    }

    public String getPictureID() {
        return pictureID;
    }

    public void setPictureID(String pictureID) {
        this.pictureID = pictureID;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }
}
