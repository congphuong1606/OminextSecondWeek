package studentmanager.android.vn.studentmanager.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import studentmanager.android.vn.studentmanager.R;
import studentmanager.android.vn.studentmanager.Student;

public class StudentAdapter extends ArrayAdapter<Student> {
    Context context;
    int mylayout;
    ArrayList<Student> students;
    boolean icheck = false;
    boolean echeck = false;

    public StudentAdapter(Context context, int mylayout, ArrayList<Student> students, boolean icheck, boolean echeck) {
        super(context, mylayout, students);
        this.context = context;
        this.mylayout = mylayout;
        this.students = students;
        this.icheck = icheck;
        this.echeck = echeck;

    }

    public StudentAdapter(Context context, int mylayout, ArrayList<Student> students) {
        super(context, mylayout, students);
        this.context = context;
        this.mylayout = mylayout;
        this.students = students;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHoder holder = null;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.student_row, parent, false);
            holder = new ViewHoder();

            holder.ckStudent = (CheckBox) convertView.findViewById(R.id.chk_student);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_number = (TextView) convertView.findViewById(R.id.tv_number);
            holder.iv_avatar = (ImageView) convertView.findViewById(R.id.iv_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHoder) convertView.getTag();
        }
        final Student student = students.get(position);

        holder.tv_name.setText(student.getName());
        holder.tv_number.setText(String.valueOf(student.getNumber()));


        try {
            byte[] outImage = student.getImage();
            ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
            Bitmap theImage = BitmapFactory.decodeStream(imageStream);
            holder.iv_avatar.setImageBitmap(theImage);
        } catch (Exception e) {

        }
        if (echeck) {
            holder.ckStudent.setChecked(true);
        } else {
            holder.ckStudent.setChecked(false);
        }

        if (icheck) {
            holder.ckStudent.setVisibility(View.VISIBLE);
        } else {
            holder.ckStudent.setVisibility(View.GONE);
        }


        holder.ckStudent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    student.setCheck(true);
                } else
                    student.setCheck(false);

            }
        });

        if (holder.ckStudent.isChecked())
            student.setCheck(true);
        else
            student.setCheck(false);


        return convertView;
    }


    static class ViewHoder {
        ImageView iv_avatar;
        CheckBox ckStudent;
        TextView tv_number, tv_name;
    }

    public Student getItemObject(int position) {
        return students.get(position);
    }
}
