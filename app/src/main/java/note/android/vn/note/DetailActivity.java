package note.android.vn.note;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {
    private Calendar cal1, cal;
    private Date _date, _gio;

    private TextView tit,tvTime,tv_title;
    String hours, date;
    private EditText  edt_description;
    int position;
    ReadWrite readWrite;
    private TextView tv_edit, tv_save, tv_delete;
    private TimePickerDialog.OnTimeSetListener callbackTime;
    private DatePickerDialog.OnDateSetListener callbackDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        readWrite = new ReadWrite(this);
        cal1 = Calendar.getInstance();
        cal = Calendar.getInstance();
        String title = null;
        String description = null;
        String time = null;
        JSONObject jsonObject;
        title = getIntent().getStringExtra("title");
        position = getIntent().getIntExtra("position", 0);
        description = getIntent().getStringExtra("description");
        time = getIntent().getStringExtra("time");
        tit = (TextView) findViewById(R.id.tit);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(title);
        edt_description = (EditText) findViewById(R.id.edt_description2);
        edt_description.setEnabled(false);
        edt_description.setClickable(false);
        edt_description.setText(description);
        tvTime = (TextView) findViewById(R.id.tvTime);
        tvTime.setText(time);
        tvTime.setEnabled(false);
        tvTime.setClickable(false);
        tv_edit = (TextView) findViewById(R.id.tv_edit);
        tv_save = (TextView) findViewById(R.id.tv_save);
        tv_save.setVisibility(View.GONE);
        callbackDay = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                date = (year + "/" + (month + 1) + "/" + day);
                tvTime.setText(date + " " + hours);
            }
        };
         callbackTime = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                //Xử lý lưu giờ và AM,PM
                hours = hour + ":" + minute;
                int hourTam = hour;
                if (hourTam > 12)
                    hourTam = hourTam - 12;
                hours = hourTam + ":" + minute + (hour > 12 ? " PM" : " AM");

                cal.set(Calendar.HOUR_OF_DAY, hour);
                cal.set(Calendar.MINUTE, minute);
                _gio = cal.getTime();
                String d = "2017/12/12";
                String strArrtmp[] = d.split("/");
                int nam = Integer.parseInt(strArrtmp[0]);
                int thang = Integer.parseInt(strArrtmp[1]) - 1;
                int ngay = Integer.parseInt(strArrtmp[2]);
                DatePickerDialog pic = new DatePickerDialog(DetailActivity.this, callbackDay, nam, thang, ngay);
                pic.setTitle("selected date");
                pic.show();
            }
        };
        tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tit.setVisibility(View.VISIBLE);
                tv_title.setVisibility(View.GONE);
                edt_description.setEnabled(true);
                edt_description.setClickable(true);
                tvTime.setEnabled(true);
                tvTime.setClickable(true);
                tvTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SimpleDateFormat dft = null;
                        dft = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
                        String strDate = dft.format(cal.getTime());
                        String s = "12:12";
                        String strArr[] = s.split(":");
                        int gio = Integer.parseInt(strArr[0]);
                        int phut = Integer.parseInt(strArr[1]);
                        TimePickerDialog time = new TimePickerDialog(
                                DetailActivity.this,
                                callbackTime, gio, phut, true);
                        time.setTitle("hous selected !");
                        time.show();


                    }

                });


                tv_edit.setVisibility(View.GONE);
                tv_delete.setVisibility(View.GONE);
                tv_save.setVisibility(View.VISIBLE);
                tv_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ArrayList<Notes> notesArrayList = new ArrayList<Notes>();
                        readWrite = new ReadWrite(DetailActivity.this);
                        notesArrayList = readWrite.readNotes(readWrite.readData());
                        notesArrayList.get(position).setmDescription(edt_description.getText().toString());


                        notesArrayList.get(position).setmTime(tvTime.getText().toString());
                        String noidung = new Gson().toJson(notesArrayList);
                        readWrite.saveNotes(noidung);
                        Toast.makeText(DetailActivity.this, "saved !", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });


            }
        });
        tv_delete = (TextView) findViewById(R.id.tv_delete);
        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readWrite = new ReadWrite(DetailActivity.this);
                ArrayList<Notes> notesArrayList = new ArrayList<Notes>();
                notesArrayList = readWrite.readNotes(readWrite.readData());
                notesArrayList.remove(notesArrayList.get(position));
                Toast.makeText(DetailActivity.this, "deleted !", Toast.LENGTH_SHORT).show();
                String json = new Gson().toJson(notesArrayList);
                readWrite.saveNotes(json);
                Intent i = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(i);

            }
        });


    }
}
