package studentmanager.android.vn.studentmanager;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class AddStudent extends AppCompatActivity {
    Button btnGetImage, btnTakeImge, btnInsertStudent;
    EditText sedtNameInsert, edtNumberInsert, edtAddressInsert;
    ImageView ivImageInsert;
    private SQLiteDatabase database;
    final String DATABASE_NAME = "db_student.sqlite";
    private ArrayList<Student> students;
    final int REQUEST_TAKE_PHOTO = 123;
    final int REQUEST_CHOOSE_PHOTO = 321;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        addControls();
        addEvents();


    }


    private void addEvents() {
        btnTakeImge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });
        btnGetImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhoto();
            }
        });
        btnInsertStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertStudent();
            }
        });

    }


    private void takePhoto() {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i, REQUEST_TAKE_PHOTO);
    }

    private void choosePhoto() {
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityForResult(i, REQUEST_CHOOSE_PHOTO);
    }


    private void addControls() {


        btnInsertStudent = (Button) findViewById(R.id.btn_save_update);

        btnGetImage = (Button) findViewById(R.id.btn_get_image);
        btnTakeImge = (Button) findViewById(R.id.btn_take_image);
        edtAddressInsert = (EditText) findViewById(R.id.edt_address_update);
        sedtNameInsert = (EditText) findViewById(R.id.edt_name_update);
        edtNumberInsert = (EditText) findViewById(R.id.edt_number_update);
        ivImageInsert = (ImageView) findViewById(R.id.iv_image_update);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO) {

                try {
                    Uri imageUri = data.getData();
                    InputStream inputStream = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    ivImageInsert.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            } else if (requestCode == REQUEST_CHOOSE_PHOTO && data != null) {
                Uri imageUri = data.getData();
                ivImageInsert.setImageURI(imageUri);

            }

        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        getMenuInflater().inflate(R.menu.menu, menu);
//        MenuItem iteminfor = menu.findItem(R.id.ic_save);
//        iteminfor.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem menuItem) {
//                switch (menuItem.getItemId()) {
//                    case R.id.ic_save:
//                        update();
//                        break;
//                }
//                return true;
//
//            }
//
//
//        });
//return true;
//    }

    private void insertStudent() {
        String name = sedtNameInsert.getText().toString();
        int number = Integer.parseInt(edtNumberInsert.getText().toString());
        String address = edtAddressInsert.getText().toString();
        byte[] image = imageViewToByteArray(ivImageInsert);
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("number", number);
        values.put("address", address);
        values.put("image", image);
        SQLiteDatabase database = Database.initDatabase(this, DATABASE_NAME);
        database.insert("student", null, values);
//        Cursor cursor=database.rawQuery("select * from student",null);
//        cursor.moveToPosition(id);
//        Toast.makeText(this,cursor.getString(1),Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private byte[] imageViewToByteArray(ImageView imgv) {

        BitmapDrawable drawable = (BitmapDrawable) imgv.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;


    }
}
