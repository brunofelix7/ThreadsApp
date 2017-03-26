package br.com.sample.threadsapp.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

import br.com.sample.threadsapp.R;

public class MainActivity extends AppCompatActivity {

    private TextView tv_number;
    private Handler handler = new Handler();
    private Random random   = new Random();
    private String number;
    private int delay = 10000;
    private int x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_number = (TextView) findViewById(R.id.tv_numer);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(new Runnable(){
            public void run(){
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        generateNumber();
                    }
                };
                new Thread(runnable).start();
        handler.postDelayed(this, delay);
            }
        }, delay);
    }

    public void generateNumber(){
        for (int i = 0; i <= 40; i++) {
            x       = random.nextInt(100) + 1;
            number  = String.valueOf(x);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //  ATUALIZA MINHA UI
            handler.post(new Runnable() {
                @Override
                public void run() {
                    tv_number.setText(number);
                }
            });
        }
    }

    public void showNumbers(View view) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                generateNumber();
            }
        };
        new Thread(runnable).start();
    }

    public void goToDownload(View view){
        startActivity(new Intent(MainActivity.this, ImageDownloadActivity.class));
    }
}
