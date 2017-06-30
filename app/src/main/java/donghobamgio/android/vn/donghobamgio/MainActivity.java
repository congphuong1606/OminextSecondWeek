package donghobamgio.android.vn.donghobamgio;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

//code by congphuong
public class MainActivity extends AppCompatActivity {
    TextView tvTimer;
    Button btnStart, btnStop, btnPause;
    Handler handler;
    long lSartTime, lPauseTime, lSystemTime = 0L;
    boolean isRun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvTimer = (TextView) findViewById(R.id.tv_timer);
        btnPause = (Button) findViewById(R.id.btn_pause);
        btnStop = (Button) findViewById(R.id.btn_stop);
        btnStart = (Button) findViewById(R.id.btn_start);
        handler = new Handler();
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isRun)
                    return;
                isRun = true;
                lSartTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);


            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isRun)
                    return;
                isRun = false;
                lPauseTime = 0;
                handler.removeCallbacks(runnable);
            }
        });
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isRun)
                    return;
                isRun = false;
                lPauseTime = lSystemTime + lPauseTime;
                handler.removeCallbacks(runnable);
            }
        });
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            lSystemTime = SystemClock.uptimeMillis() - lSartTime;
            long lUpdateTime = lPauseTime + lSystemTime;
            long secs = (lUpdateTime / 1000);
            long mins = secs / 60;
            secs = secs % 60;
            long milliseconds = (lUpdateTime % 1000);
            tvTimer.setText("" + mins + ":" + String.format("%02d", secs) + ":" + String.format("%03d", milliseconds));
            handler.postDelayed(this, 0);
        }
    };
}
