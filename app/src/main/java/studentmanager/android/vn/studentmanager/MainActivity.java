package studentmanager.android.vn.studentmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

import studentmanager.android.vn.studentmanager.Adapter.StudentAdapter;

public class MainActivity extends AppCompatActivity {
    final String DATABASE_NAME = "db_student.sqlite";
    SQLiteDatabase database;
    Student student;
    ArrayList<Student> students;
    StudentAdapter studentAdapter;
    ImageButton iBtnCreat, iBtnDelete, ibtn_cancel;
    ListView lvStudents;
    CheckBox checkBox;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        students = new ArrayList<>();
        addControls();
        iBtnCreat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddStudent.class);
                startActivity(i);
            }
        });
        studentAdapter = new StudentAdapter(this, R.layout.student_row, students);
        lvStudents.setAdapter(studentAdapter);
        readData();
        lvStudents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Student student = students.get(position);
                Intent i = new Intent(MainActivity.this, DetailActivity.class);
                i.putExtra("id", student.getId());
                startActivity(i);
            }
        });
        lvStudents.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                checkBox.setVisibility(View.VISIBLE);
                ibtn_cancel.setVisibility(View.VISIBLE);
                iBtnCreat.setVisibility(View.GONE);
                iBtnDelete.setVisibility(View.VISIBLE);

//                for (Student student: students){
//                    student.setCheck(false);
//                }
                studentAdapter = new StudentAdapter(MainActivity.this, R.layout.student_row, students, true, false);
                lvStudents.setAdapter(studentAdapter);
                studentAdapter.notifyDataSetChanged();
                return true;
            }
        });
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    studentAdapter = new StudentAdapter(MainActivity.this, R.layout.student_row, students, true, true);
                    lvStudents.setAdapter(studentAdapter);
                    studentAdapter.notifyDataSetChanged();
                } else {
                    studentAdapter = new StudentAdapter(MainActivity.this, R.layout.student_row, students, true, false);
                    lvStudents.setAdapter(studentAdapter);
                    studentAdapter.notifyDataSetChanged();
                }
            }
        });
        ibtn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iBtnCreat.setVisibility(View.VISIBLE);
                checkBox.setVisibility(View.GONE);
                ibtn_cancel.setVisibility(View.GONE);
                iBtnDelete.setVisibility(View.GONE);
                studentAdapter = new StudentAdapter(MainActivity.this, R.layout.student_row, students, false, false);
                lvStudents.setAdapter(studentAdapter);
                studentAdapter.notifyDataSetChanged();
            }
        });
        iBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dem = 0;
                for (Student student : students) {
                    if (student.isCheck()) {
                        dem++;
                    }
                }

                if (dem > 0) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
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
            }
        });
    }


    private void deleteStudent() {
        for (Student student : students) {

            if (student.isCheck()) {
                int i = student.getId();
                SQLiteDatabase database = Database.initDatabase(MainActivity.this, DATABASE_NAME);

                database.delete("student", "id = ? ", new String[]{i + ""});
            }
        }

        studentAdapter = new StudentAdapter(MainActivity.this, R.layout.student_row, students, false, false);
        lvStudents.setAdapter(studentAdapter);
        studentAdapter.notifyDataSetChanged();
        checkBox.setVisibility(View.GONE);
        iBtnDelete.setVisibility(View.GONE);
        iBtnCreat.setVisibility(View.VISIBLE);
        ibtn_cancel.setVisibility(View.GONE);
        readData();
    }

    private void readData() {
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("select * from student ", null);
//        cursor.moveToFirst();
        students.clear();
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(0);
                    String name = cursor.getString(1);
                    int number = cursor.getInt(2);
                    String address = cursor.getString(3);
                    byte[] image = cursor.getBlob(4);
                    students.add(new Student(id, name, number, address, image));
                } while (cursor.moveToNext());
            }
        }
//        for (int i = 0; i < cursor.getCount(); i++) {
//            cursor.moveToPosition(i);
//            int id = cursor.getInt(0);
//            String name = cursor.getString(1);
//            int number = cursor.getInt(2);
//            String address = cursor.getString(3);
//            byte[] image = cursor.getBlob(4);
//            students.add(new Student(id, name, number, address, image));
//        }
        studentAdapter.notifyDataSetChanged();
    }

    private void addControls() {

        iBtnCreat = (ImageButton) findViewById(R.id.ibtn_creat);
        iBtnDelete = (ImageButton) findViewById(R.id.ibtn_delete);
        ibtn_cancel = (ImageButton) findViewById(R.id.ibtn_cancel);
        lvStudents = (ListView) findViewById(R.id.lv_students);
        checkBox = (CheckBox) findViewById(R.id.checkbox);

    }


}
