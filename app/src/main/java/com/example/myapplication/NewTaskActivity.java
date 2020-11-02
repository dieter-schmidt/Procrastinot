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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class NewTaskActivity extends AppCompatActivity {
    public static final String EXTRA_NAME = "com.example.android.myapplication.NAME";
            //"com.example.android.roomwordssample.REPLY";
    public static final String EXTRA_WEIGHT = "com.example.android.myapplication.WEIGHT";
    public static final String EXTRA_TYPE = "com.example.android.myapplication.TYPE";
    public static final String EXTRA_NOTES = "com.example.android.myapplication.NOTES";

    private EditText mEditTaskView;
    private EditText mEditTypeView;
    private EditText mEditNotesView;
    private RadioGroup mEditWeightRadioGroup;
    private RadioButton mWeightRadioButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);
        mEditTaskView = findViewById(R.id.edit_task);
        mEditTypeView = findViewById(R.id.edit_type);
        mEditNotesView = findViewById(R.id.edit_notes);
        mEditWeightRadioGroup = findViewById(R.id.edit_weight_radio_group);
        mEditWeightRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
             @Override
             public void onCheckedChanged(RadioGroup group, int checkedId)
             {
                 mWeightRadioButton = (RadioButton) findViewById(checkedId);
                 Toast.makeText(getBaseContext(), mWeightRadioButton.getText(), Toast.LENGTH_SHORT).show();
             }
         }
        );

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditTaskView.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String task = mEditTaskView.getText().toString();
                    String notes;
                    if (mEditNotesView.getText() != null)
                    {
                        notes = mEditNotesView.getText().toString();
                    }
                    else {
                        notes = null;
                    }
                    String weight;
                    if (mWeightRadioButton != null) {
                        weight = mWeightRadioButton.getText().toString();
                    }
                    else {
                        weight = "Medium";
                    }
                    String type = mEditTypeView.getText().toString();
                    replyIntent.putExtra(EXTRA_NAME, task);
                    replyIntent.putExtra(EXTRA_WEIGHT, weight);
                    replyIntent.putExtra(EXTRA_TYPE, type);
                    replyIntent.putExtra(EXTRA_NOTES, notes);
                    setResult(RESULT_OK, replyIntent);
                }
                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.hitsound);
                mp.start();
                finish();
            }
        });
    }
}