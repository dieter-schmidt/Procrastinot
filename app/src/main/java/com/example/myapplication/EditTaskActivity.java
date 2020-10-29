package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class EditTaskActivity extends AppCompatActivity {

    public static final String EXTRA_NEW_WEIGHT = "com.example.android.myapplication.NEW_WEIGHT";
    public static final String EXTRA_NEW_NAME = "com.example.android.myapplication.NEW_NAME";
    public static final String EXTRA_NEW_TYPE = "com.example.android.myapplication.NEW_TYPE";

    private EditText mEditTaskView;
    private EditText mEditTypeView;
    private RadioGroup mEditWeightRadioGroup;
    private RadioButton mWeightRadioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        mEditTaskView = findViewById(R.id.edit_task);
        mEditTypeView = findViewById(R.id.edit_type);
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

        //Return intent with updated task data to Main Activity
        final Button button = findViewById(R.id.edit_button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditTaskView.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String task = mEditTaskView.getText().toString();
                    String weight;
                    if (mWeightRadioButton != null) {
                        weight = mWeightRadioButton.getText().toString();
                    }
                    else {
                        weight = "Medium";
                    }
                    String type = mEditTypeView.getText().toString();
                    replyIntent.putExtra(EXTRA_NEW_NAME, task);
                    replyIntent.putExtra(EXTRA_NEW_WEIGHT, weight);
                    replyIntent.putExtra(EXTRA_NEW_TYPE, type);
                    setResult(RESULT_OK, replyIntent);
                }
                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.hitsound);
                mp.start();
                finish();
            }
        });

        //Populate fields with current task data - sent from Main Activity
        Bundle taskData = getIntent().getExtras();
        String name = taskData.getString(MainActivity.EXTRA_NAME);
        String type = taskData.getString(MainActivity.EXTRA_TYPE);
        String weight = taskData.getString(MainActivity.EXTRA_WEIGHT);

        mEditTaskView.setText(name);
        mEditTypeView.setText(type);
        for (int i = 0; i < mEditWeightRadioGroup.getChildCount()-1; i++)
        {
            Object radioButton = mEditWeightRadioGroup.getChildAt(i);
            if (radioButton instanceof RadioButton)
            {
                String radioWeight = ((RadioButton) radioButton).getText().toString();
                if (radioWeight.equals(weight))
                {
                    ((RadioButton) radioButton).toggle();
                    break;
                }
            }
        }

    }

//    public void onActivityCreated(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        String name = data.getStringExtra(MainActivity.EXTRA_NAME);
//        String type = data.getStringExtra(MainActivity.EXTRA_TYPE);
//        String weight = data.getStringExtra(MainActivity.EXTRA_WEIGHT);
//
//        mEditTaskView.setText(name);
//        mEditTypeView.setText(type);
//        for (int i = 0; i < mEditWeightRadioGroup.getChildCount()-1; i++)
//        {
//            Object radioButton = mEditWeightRadioGroup.getChildAt(i);
//            if (radioButton instanceof RadioButton)
//            {
//                String radioWeight = ((RadioButton) radioButton).getText().toString();
//                if (radioWeight.equals(weight))
//                {
//                    ((RadioButton) radioButton).toggle();
//                    break;
//                }
//            }
//        }
//    }
}