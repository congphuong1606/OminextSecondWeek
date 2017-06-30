package note.android.vn.note;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by MyPC on 21/06/2017.
 */

public class ReadWrite {

    Context context;
    public ReadWrite(Context context){
        this.context=context;
    }

    public void saveNotes(String description) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = context.openFileOutput("note.txt", Context.MODE_PRIVATE);
            fileOutputStream.write(description.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readData() {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            FileInputStream fileInputStream = context.openFileInput("note.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }

    public ArrayList<Notes> readNotes(String nd) {
        String time = null;
        String description = null;
        String check = "null";
        String title = null;
        Boolean isCheck = false;
        ArrayList<Notes> arrayList = new ArrayList<Notes>();
        try {
            JSONArray array = new JSONArray(nd);
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                title = jsonObject.getString("title");
                description = jsonObject.getString("description");
                time = jsonObject.getString("time");
                check = jsonObject.getString("check");
                if (check.equals("false"))
                    isCheck = false;
                else isCheck=true;
                Notes notes = new Notes(isCheck,title, description, time);
                arrayList.add(notes);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }
}
