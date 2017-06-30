package studentmanager.android.vn.studentmanager;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    Button btnEdit, btnDelete, btnGetImage, btnTakeImge, btnSaveUpdate;
    EditText edtNameUpdate, edtNumberUpdate, edtAddressUpdate;
    ImageView ivImageUpdate;
    private SQLiteDatabase database;
    final String DATABASE_NAME = "db_student.sqlite";
    private ArrayList<Student> students;
    final int REQUEST_TAKE_PHOTO = 123;
    final int REQUEST_CHOOSE_PHOTO = 321;
    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        students = new ArrayList<>();
        addControls();
        status();
        initUI();
        addEvents();


    }

    private void status() {
        edtNameUpdate.setEnabled(false);
        edtNameUpdate.setClickable(false);
        edtNumberUpdate.setEnabled(false);
        edtNumberUpdate.setClickable(false);
        edtAddressUpdate.setEnabled(false);
        edtAddressUpdate.setClickable(false);
        btnSaveUpdate.setVisibility(View.GONE);
        btnTakeImge.setVisibility(View.GONE);
        btnGetImage.setVisibility(View.GONE);
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
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtNameUpdate.setEnabled(true);
                edtNameUpdate.setClickable(true);
                edtNumberUpdate.setEnabled(true);
                edtNumberUpdate.setClickable(true);
                edtAddressUpdate.setEnabled(true);
                edtAddressUpdate.setClickable(true);
                btnSaveUpdate.setVisibility(View.VISIBLE);
                btnTakeImge.setVisibility(View.VISIBLE);
                btnGetImage.setVisibility(View.VISIBLE);
                btnEdit.setVisibility(View.GONE);
                btnDelete.setVisibility(View.GONE);
            }
        });
        btnSaveUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(DetailActivity.this);
                alertDialog.setIcon(R.drawable.delete);
                alertDialog.setTitle("Xác nhận xóa");
                alertDialog.setMessage("Bạn có chắc chắn muốn xóa sinh viên này không?");
                alertDialog.setPositiveButton("có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteStudent();
                    }
                });
                alertDialog.setNegativeButton("không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = alertDialog.create();
                dialog.show();
            }
        });
    }

    private void deleteStudent() {
        SQLiteDatabase database = Database.initDatabase(this, DATABASE_NAME);
        database.delete("student", "id = ? ", new String[]{id + ""});
        Intent intent = new Intent(DetailActivity.this, MainActivity.class);
        startActivity(intent);
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

    private void initUI() {
        Intent i = getIntent();
        id = i.getIntExtra("id", -1);
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("select * from student where id = ? ", new String[]{id + "",});

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            String name = cursor.getString(1);
            int number = cursor.getInt(2);
            String address = cursor.getString(3);
            byte[] image = cursor.getBlob(4);
            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            ivImageUpdate.setImageBitmap(bitmap);
            edtNameUpdate.setText(name);
            edtNumberUpdate.setText(String.valueOf(number));
            edtAddressUpdate.setText(address);
        }


    }

    private void addControls() {

        btnDelete = (Button) findViewById(R.id.btn_delete);
        btnSaveUpdate = (Button) findViewById(R.id.btn_save_update);
        btnEdit = (Button) findViewById(R.id.btn_edit);
        btnGetImage = (Button) findViewById(R.id.btn_get_image);
        btnTakeImge = (Button) findViewById(R.id.btn_take_image);
        edtAddressUpdate = (EditText) findViewById(R.id.edt_address_update);
        edtNameUpdate = (EditText) findViewById(R.id.edt_name_update);
        edtNumberUpdate = (EditText) findViewById(R.id.edt_number_update);
        ivImageUpdate = (ImageView) findViewById(R.id.iv_image_update);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO) {

                try {
                    Uri imageUri = data.getData();
                    InputStream inputStream = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    ivImageUpdate.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            } else if (requestCode == REQUEST_CHOOSE_PHOTO && data != null) {
                Uri imageUri = data.getData();
                ivImageUpdate.setImageURI(imageUri);

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


    private void update() {
        String name = edtNameUpdate.getText().toString();
        int number = Integer.parseInt(edtNumberUpdate.getText().toString());
        String address = edtAddressUpdate.getText().toString();
        byte[] image = imageViewToByteArray(ivImageUpdate);
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("number", number);
        values.put("address", address);
        values.put("image", image);
        SQLiteDatabase database = Database.initDatabase(this, DATABASE_NAME);
        database.update("student", values, "id = ? ", new String[]{id + ""});
//        Cursor cursor=database.rawQuery("select * from student",null);
//        cursor.moveToPosition(id);
//        Toast.makeText(this,cursor.getString(1),Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(DetailActivity.this, MainActivity.class);
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
