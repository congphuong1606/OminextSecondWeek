package note.android.vn.note;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import note.android.vn.note.Adapter.NotesAdapter;

public class MainActivity extends AppCompatActivity {
    ReadWrite readWrite;
    ListView listViewNote;
    ImageButton btn_new;
    TextView tv_appname, btn_delete, btn_cancel;
    ArrayList<Notes> notesArrayList;
    NotesAdapter notesAdapter;
    CheckBox chk_all;
    private boolean clickListview;

    public void finId() {
        tv_appname = (TextView) findViewById(R.id.tv_appname);
        btn_delete = (TextView) findViewById(R.id.btn_delete);
        btn_cancel = (TextView) findViewById(R.id.btn_cancel);
        chk_all = (CheckBox) findViewById(R.id.chk_all);
        btn_new = (ImageButton) findViewById(R.id.btn_new);
        listViewNote = (ListView) findViewById(R.id.listViewNote);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readWrite = new ReadWrite(this);
        finId();
        btn_delete.setVisibility(View.GONE);
        chk_all.setVisibility(View.GONE);
        btn_cancel.setVisibility(View.GONE);
        notesArrayList = readWrite.readNotes(readWrite.readData());
        notesAdapter = new NotesAdapter(MainActivity.this, R.layout.dong_note, notesArrayList, false,false);
        listViewNote.setAdapter(notesAdapter);
        listViewNote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, DetailActivity.class);
                i.putExtra("position", position);
                i.putExtra("title", notesArrayList.get(position).getmTile());
                i.putExtra("description", notesArrayList.get(position).getmDescription());
                i.putExtra("time", notesArrayList.get(position).getmTime());
                startActivity(i);

            }
        });
        btn_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, WriteNoteActivity.class);
                startActivity(i);
            }
        });
        listViewNote.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                btn_delete.setVisibility(View.VISIBLE);
                btn_cancel.setVisibility(View.VISIBLE);
                chk_all.setVisibility(View.VISIBLE);
                btn_new.setVisibility(View.GONE);

//                for (Notes notes : notesArrayList) {
//                    notes.setmCheckbox(false);
//                }
                notesAdapter = new NotesAdapter(MainActivity.this, R.layout.dong_note, notesArrayList, true,false);
                listViewNote.setAdapter(notesAdapter);
                notesAdapter.notifyDataSetChanged();
                return true;
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dem = 0;
                for (Notes notes : notesArrayList) {
                    if (notes.getmCheckbox()) {
                        dem++;
                    }
                }
                if (dem > 0) {
                    for (int i = 0; i < notesArrayList.size(); i++) {

                        if (notesAdapter.getItemObject(i).getmCheckbox()) {
                            notesArrayList.remove(i);
                            i--;
                        }
                    }
                    notesAdapter = new NotesAdapter(MainActivity.this, R.layout.dong_note, notesArrayList, false,false);
                    listViewNote.setAdapter(notesAdapter);
                    notesAdapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, String.format("xóa thành công"), Toast.LENGTH_SHORT).show();
                    notesAdapter.notifyDataSetChanged();
                    String json = new Gson().toJson(notesArrayList);
                    readWrite.saveNotes(json);
                    btn_delete.setVisibility(View.GONE);
                    btn_cancel.setVisibility(View.GONE);
                    btn_new.setVisibility(View.VISIBLE);
                    chk_all.setVisibility(View.GONE);
                }

            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_delete.setVisibility(View.GONE);
                btn_cancel.setVisibility(View.GONE);
                btn_new.setVisibility(View.VISIBLE);
                chk_all.setVisibility(View.GONE);
//                for (Notes notes : notesArrayList) {
//                    notes.setmCheckbox(true);
//                }
                notesAdapter = new NotesAdapter(MainActivity.this, R.layout.dong_note, notesArrayList, false,false);
                listViewNote.setAdapter(notesAdapter);
                notesAdapter.notifyDataSetChanged();
            }
        });
        chk_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chk_all.isChecked()) {
                    notesAdapter = new NotesAdapter(MainActivity.this, R.layout.dong_note, notesArrayList, true,true);
                    listViewNote.setAdapter(notesAdapter);
                    notesAdapter.notifyDataSetChanged();
                } else {
                    notesAdapter = new NotesAdapter(MainActivity.this, R.layout.dong_note, notesArrayList, true,false);
                    listViewNote.setAdapter(notesAdapter);
                    notesAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
