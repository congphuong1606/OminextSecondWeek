package multitheard.android.vn.multitheardusingpost;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity {
    Button btnDraw;
    EditText editText;
    AtomicBoolean aBoolean;
    LinearLayout layout;
    Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = (LinearLayout) findViewById(R.id.layout_draw_buton);
        btnDraw = (Button) findViewById(R.id.btn_draw);
        editText = (EditText) findViewById(R.id.tv_draw);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String text_buton = msg.obj.toString();
                Button button = new Button(MainActivity.this);
                button.setText(text_buton);
                LinearLayout.LayoutParams params = new
                        LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                button.setLayoutParams(params);
                layout.addView(button);
            }
        };
        btnDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start();
            }
        });

    }

    private void start() {
        aBoolean = new AtomicBoolean(false);
        final int soButon = Integer.parseInt(editText.getText() + "");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < soButon && aBoolean.get(); i++) {
                    SystemClock.sleep(200);
                    Message message = handler.obtainMessage();
                    message.obj = "Buton thứ " + i;
                    handler.sendMessage(message);

                }
            }
        });
        aBoolean.set(true);
        thread.start();
    }

}
