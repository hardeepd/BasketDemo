package uk.co.hd_tech.basketdemo.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import uk.co.hd_tech.basketdemo.R;


/**
 * Splash screen page
 */
public class SplashScreenActivity extends AppCompatActivity {

    private SplashTimer splashTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getSupportActionBar().hide();

        splashTimer = new SplashTimer(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        splashTimer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        splashTimer.stop();
    }

    private static class SplashTimer extends Handler {

        private static final int MSG_START = 100;
        private static final int SPLASH_TIME_OUT = 1500;

        private Activity activity;

        public SplashTimer(Activity activity) {
            super(Looper.getMainLooper());
            this.activity = activity;
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_START) {
                Intent basketIntent = new Intent(activity, BasketActivity.class);
                basketIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                activity.startActivity(basketIntent);

                stop();
                activity.finish();
            }
        }

        public void start() {
            sendEmptyMessageDelayed(MSG_START, SPLASH_TIME_OUT);
        }

        public void stop() {
            removeCallbacksAndMessages(null);
        }
    }
}
