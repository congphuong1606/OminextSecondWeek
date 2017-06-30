package note.android.vn.note;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class WriteNoteActivity extends AppCompatActivity {
    ReadWrite readWrite;
    private Button btn_save;
    private EditText edt_title, edt_description;

    private Calendar cal1, cal;
    private Date date, gio;
private ImageButton ib_timer;
    private TextView tv_dename, tv_creat, tv_date, tv_timer,tvTitleTime;
    private TimePickerDialog.OnTimeSetListener callbackTime;
    private DatePickerDialog.OnDateSetListener callbackDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_note);
        anhxa();
        readWrite = new ReadWrite(this);
        cal1 = Calendar.getInstance();
        SimpleDateFormat adt = null;
        adt = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        String strTime = adt.format(cal1.getTime());

        tv_timer.setText(strTime);

        adt = new SimpleDateFormat("HH:mm", Locale.getDefault());
        tv_timer.setTag(adt.format(cal1.getTime()));



        ib_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callbackDay = new DatePickerDialog.OnDateSetListener() {
                    //Sự kiện khi click vào nút Done trên Dialog
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                        tv_date.setText(year + "/" + (month + 1) + "/" + day);

                        cal.set(year, month, day);
                        date = cal.getTime();
                    }
                };
                String d = tv_date.getText() + "";

                String strArrtmp[] = d.split("/");
                int nam = Integer.parseInt(strArrtmp[0]);
                int thang = Integer.parseInt(strArrtmp[1]) - 1;
                int ngay = Integer.parseInt(strArrtmp[2]);

                DatePickerDialog pic = new DatePickerDialog(WriteNoteActivity.this,callbackDay, nam, thang, ngay);
                pic.setTitle("ngày hoàn thành");
                pic.show();
                 callbackTime = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        String s = hour + ":" + minute;
                        int hourTam = hour;
                        if (hourTam > 12)
                            hourTam = hourTam - 12;
                        tv_timer.setText
                                (hourTam + ":" + minute + (hour > 12 ? " PM" : " AM"));

                        tv_timer.setTag(s);

                        cal.set(Calendar.HOUR_OF_DAY, hour);
                        cal.set(Calendar.MINUTE, minute);
                        gio = cal.getTime();
                    }
                };
                String s = tv_timer.getTag() + "";
                String strArr[] = s.split(":");
                int gio = Integer.parseInt(strArr[0]);
                int phut = Integer.parseInt(strArr[1]);
                TimePickerDialog time = new TimePickerDialog(WriteNoteActivity.this, callbackTime, gio, phut, true);
                time.setTitle("giờ hoàn thành");
                time.show();

            }
        });


        cal = Calendar.getInstance();
        SimpleDateFormat dft = null;

        dft = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        String strDate = dft.format(cal.getTime());

        tv_date.setText(strDate);



        final String datetime = strTime + " " + strDate;


        tv_creat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Notes notes = new Notes(false, edt_title.getText().toString(), edt_description.getText().toString(), datetime);

                ArrayList<Notes> notesArrayList = new ArrayList<Notes>();
                notesArrayList = readWrite.readNotes(readWrite.readData());
                notesArrayList.add(notes);
                String noidung = new Gson().toJson(notesArrayList);
                readWrite.saveNotes(noidung);
                Toast.makeText(WriteNoteActivity.this, "saved!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(WriteNoteActivity.this, MainActivity.class);
                startActivity(intent);


            }
        });
    }







    public void anhxa() {
        edt_title = (EditText) findViewById(R.id.edt_title);
        edt_description = (EditText) findViewById(R.id.edt_description);
        tv_timer = (TextView) findViewById(R.id.tv_timer);
ib_timer=(ImageButton)findViewById(R.id.ib_timer) ;
        tv_creat = (TextView) findViewById(R.id.tv_creat);
        tv_dename = (TextView) findViewById(R.id.tv_dename);
        tv_date = (TextView) findViewById(R.id.tv_date);
        tvTitleTime = (TextView) findViewById(R.id.tv_title_time);

    }


}
