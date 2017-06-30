package note.android.vn.note;

/**
 * Created by MyPC on 19/06/2017.
 */

public class Notes {
    private boolean check=false;
    private String title;
    private String description;
    private String time;

    public Notes(boolean mCheckbox, String mTile, String mDescription, String mTime) {
        this.check = mCheckbox;
        this.title = mTile;
        this.description = mDescription;
        this.time = mTime;
    }

    public boolean getmCheckbox() {
        return check;
    }

    public void setmCheckbox(boolean mCheckbox) {
        this.check = mCheckbox;
    }

    public String getmTile() {
        return title;
    }

    public void setmTile(String mTile) {
        this.title = mTile;
    }

    public String getmDescription() {
        return description;
    }

    public void setmDescription(String mDescription) {
        this.description = mDescription;
    }

    public String getmTime() {
        return time;
    }

    public void setmTime(String mTime) {
        this.time = mTime;
    }

    public Notes() {
    }



}
