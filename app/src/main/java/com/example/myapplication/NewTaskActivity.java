package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.media.MediaPlayer;

public class NewTaskActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY = "com.example.android.myapplication.REPLY";
            //"com.example.android.roomwordssample.REPLY";
    public static final String EXTRA_WEIGHT = "com.example.android.myapplication.WEIGHT";


    private EditText mEditTaskView;
    private EditText mEditWeightView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);
        mEditTaskView = findViewById(R.id.edit_task);
        mEditWeightView = findViewById(R.id.edit_weight);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditTaskView.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String task = mEditTaskView.getText().toString();
                    String weight = mEditWeightView.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY, task);
                    replyIntent.putExtra(EXTRA_WEIGHT, weight);
                    setResult(RESULT_OK, replyIntent);
                }
                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.hitsound);
                mp.start();
                finish();
            }
        });
    }
}