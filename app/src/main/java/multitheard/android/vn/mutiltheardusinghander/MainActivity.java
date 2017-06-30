package multitheard.android.vn.mutiltheardusinghander;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity {
    ProgressBar bar;
    TextView tvBar;
    Handler handler;
    Button btnStart;
    AtomicBoolean aBoolean = new AtomicBoolean(false);


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        handler = new Handler() {
            public void handleMessage(Message message) {
                super.handleMessage(message);
                bar.setProgress(message.arg1);
                tvBar.setText(message.arg1 + "%");
            }
        };
        tvBar = (TextView) findViewById(R.id.tv_bar);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start();
            }
        });


    }

    private void start() {
        bar.setProgress(0);
        aBoolean.set(false);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 100 && aBoolean.get(); i++) {
                    SystemClock.sleep(100);
                    Message message = handler.obtainMessage();

                    message.arg1 = i;
                    handler.sendMessage(message);
                }


            }
        });
        aBoolean.set(true);
        thread.start();
    }

    private void addControls() {
        btnStart = (Button) findViewById(R.id.btn_start);
        bar = (ProgressBar) findViewById(R.id.prb_bar);

    }


}