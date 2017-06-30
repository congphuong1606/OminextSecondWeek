package studentmanager.android.vn.studentmanager;

/**
 * Created by MyPC on 25/06/2017.
 */

public class Student {
    Boolean check = false;
    private int id;
    private String name;
    private int number;
    private String address;
    private byte[] image;


    public Student(Boolean check, int id, String name, int number, String address, byte[] image) {
        this.check = check;
        this.id = id;
        this.name = name;
        this.number = number;
        this.address = address;
        this.image = image;
    }

    public Boolean isCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public Student(int id, String name, int number, String address, byte[] image) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.address = address;
        this.image = image;
    }

    public Student() {
    }

    public Student(String name, int number, String address, byte[] image) {
        this.name = name;
        this.number = number;
        this.address = address;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
