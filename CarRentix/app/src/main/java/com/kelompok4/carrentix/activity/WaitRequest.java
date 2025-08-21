package com.kelompok4.carrentix.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.kelompok4.carrentix.R;

public class WaitRequest extends AppCompatActivity implements View.OnClickListener{

    private Button Back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_wait_request);

        Back = findViewById(R.id.back);
        Back.setOnClickListener((View.OnClickListener) this);

        Handler h = new Handler(){
            @Override
            public void handleMessage(Message msg) {

                Intent i = new Intent().setClass(WaitRequest.this, PaymentActivity.class);
                startActivity(i);
            }
        };

        h.sendEmptyMessageDelayed(0, 2000);
    }
    public void onClick(View v){
        if (v.getId() == R.id.back){
            onBackPressed();
        }
    }
}