package picdesignbeauty.android.vn.picdesign.Model;

/**
 * Created by MyPC on 29/06/2017.
 */

public class PicDesgin {
    private int id;
    private String description;
    private byte[] image;

    public PicDesgin(int id, String description, byte[] image) {
        this.id = id;
        this.description = description;
        this.image = image;
    }

    public PicDesgin() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
