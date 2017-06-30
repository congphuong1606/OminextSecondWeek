package picdesignbeauty.android.vn.picdesign.Fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import picdesignbeauty.android.vn.picdesign.Database.Database;
import picdesignbeauty.android.vn.picdesign.Model.PicDesgin;
import picdesignbeauty.android.vn.picdesign.PicDesginAdapter.RecyclerAdapter;
import picdesignbeauty.android.vn.picdesign.R;

/**
 * Created by MyPC on 29/06/2017.
 */

public class HomeFragment extends android.support.v4.app.Fragment {
    final String DATABASE_NAME = "PicDesign.sqlite";
    SQLiteDatabase database;
    PicDesgin picDesgin;
    ArrayList<PicDesgin> picDesgins = new ArrayList<>();
    RecyclerView.Adapter adapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycleview);
        layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new RecyclerAdapter(getActivity().getBaseContext(), R.layout.row_layout, picDesgins);
        recyclerView.setAdapter(adapter);
        readData();
        return rootView;
    }

    private void readData() {
        database = Database.initDatabase(getActivity(), DATABASE_NAME);
        Cursor cursor = database.rawQuery("select * from picdesign", null);
        cursor.moveToFirst();
//        picDesgins.clear();
//        if (cursor != null && cursor.getCount() > 0) {
//            if (cursor.moveToFirst()) {
//                do {
//                    int id = cursor.getInt(0);
//                    String description = cursor.getString(1);
//                    byte[] image = cursor.getBlob(2);
//                    picDesgins.add(new PicDesgin(id, description, image));
//                } while (cursor.moveToNext());
//            }
//        }
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            int id = cursor.getInt(0);
            String description = cursor.getString(1);

            byte[] image = cursor.getBlob(2);
            picDesgins.add(new PicDesgin(id, description, image));
        }
        adapter.notifyDataSetChanged();
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }
}
