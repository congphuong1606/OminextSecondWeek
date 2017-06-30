package note.android.vn.note.Adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

import note.android.vn.note.Notes;
import note.android.vn.note.R;

/**
 * Created by MyPC on 19/06/2017.
 */

public class NotesAdapter extends ArrayAdapter {
    Context myContext;
    int myLayout;
    ArrayList<Notes> arrayList;
    public boolean iCheck = false;
    public boolean echeck = false;


    public NotesAdapter(@NonNull Context context, @LayoutRes int layout, ArrayList<Notes> arrayList, boolean iCheck, boolean echeck) {
        super(context, layout, arrayList);
        this.myContext = context;
        this.myLayout = layout;
        this.arrayList = arrayList;
        this.iCheck = iCheck;
        this.echeck = echeck;
    }




    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parrent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(myContext).inflate(R.layout.dong_note, parrent, false);
            viewHolder = new ViewHolder();
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.chk_selected);
            viewHolder.tvTile = (TextView) convertView.findViewById(R.id.tvTile);
            viewHolder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);


            convertView.setTag(viewHolder);
        } else viewHolder = (ViewHolder) convertView.getTag();

        final Notes note = arrayList.get(position);
        viewHolder.tvTile.setText(note.getmTile().toString());
        viewHolder.tvTime.setText(note.getmTime().toString());
        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


            }
        });

        if (echeck) {
            viewHolder.checkBox.setChecked(true);
        } else viewHolder.checkBox.setChecked(false);


        if (iCheck) {

            viewHolder.checkBox.setVisibility(View.VISIBLE);
        } else {

            viewHolder.checkBox.setVisibility(View.GONE);
        }


        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    note.setmCheckbox(true);
                } else
                    note.setmCheckbox(false);

            }
        });

        if (viewHolder.checkBox.isChecked())
            note.setmCheckbox(true);
        else
            note.setmCheckbox(false);


        return convertView;
    }

    public Notes getItemObject(int position) {

        return arrayList.get(position);
    }


    private class ViewHolder {
        TextView tvTile, tvTime;
        CheckBox checkBox;


    }

}
