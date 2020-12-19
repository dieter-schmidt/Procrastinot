package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Arrays;

public class EditTaskActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String EXTRA_NEW_WEIGHT = "com.example.android.myapplication.NEW_WEIGHT";
    public static final String EXTRA_NEW_NAME = "com.example.android.myapplication.NEW_NAME";
    public static final String EXTRA_NEW_TYPE = "com.example.android.myapplication.NEW_TYPE";
    public static final String EXTRA_NEW_ID = "com.example.android.myapplication.NEW_ID";
    public static final String EXTRA_NEW_NOTES = "com.example.android.myapplication.NEW_NOTES";
    public static final String EXTRA_NEW_COLOR = "com.example.android.myapplication.NEW_COLOR";

    private EditText mEditTaskView;
    private EditText mEditTypeView;
    private EditText mEditNotesView;
    private RadioGroup mEditWeightRadioGroup;
    private RadioButton mWeightRadioButton;
    private View mColorPreview;
    private Spinner mColorSpinner;
    private boolean spinnerSelectedByUser = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

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


        mColorPreview = findViewById(R.id.color_preview);
        //set initial preview color
        mColorPreview.setBackgroundColor(getResources().getIntArray(R.array.colors_array)[0]);
        // Create the spinner.
        mColorSpinner = findViewById(R.id.color_spinner);
        if (mColorSpinner != null) {
            mColorSpinner.setOnItemSelectedListener(this);
        }

        // Create an ArrayAdapter using the string array and default spinner
        // layout.
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.color_labels_array,
                android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner.
        if (mColorSpinner != null) {
            mColorSpinner.setAdapter(adapter);
        }

        //Populate fields with current task data - sent from Main Activity
        Bundle taskData = getIntent().getExtras();
        final int id = taskData.getInt(TaskFragment.EXTRA_ID);
        String name = taskData.getString(TaskFragment.EXTRA_NAME);
        String type = taskData.getString(TaskFragment.EXTRA_TYPE);
        String weight = taskData.getString(TaskFragment.EXTRA_WEIGHT);
        String notes = taskData.getString(TaskFragment.EXTRA_NOTES);
        String color = taskData.getString(TaskFragment.EXTRA_COLOR);
        Log.e("BIBBERY", weight);

        mEditTaskView.setText(name);
        mEditTypeView.setText(type);
        mEditNotesView.setText(notes);
        mColorPreview.setBackgroundColor(Color.parseColor(color));

        //String[] colors = getResources().getStringArray(R.array.colors_array);
        String[] colors = getResources().getStringArray(R.array.colors_array);
        int position = Arrays.asList(colors).indexOf(color);
        mColorSpinner.setSelection(position);

        for (int i = 0; i < mEditWeightRadioGroup.getChildCount(); i++)
        {
            Object radioButton = mEditWeightRadioGroup.getChildAt(i);
            if (radioButton instanceof RadioButton)
            {
                String radioWeight = ((RadioButton) radioButton).getText().toString();
                if (radioWeight.equals(weight))
                {
                    ((RadioButton) radioButton).setChecked(true);
                    break;
                }
            }
        }

        //Return intent with updated task data to Main Activity
        final Button button = findViewById(R.id.edit_button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditTaskView.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                }
                else {
                    String task = mEditTaskView.getText().toString();
                    String notes = mEditNotesView.getText().toString();
                    String weight;
                    if (mWeightRadioButton != null) {
                        weight = mWeightRadioButton.getText().toString();
                    }
                    else {
                        weight = "Medium";
                    }
                    int color = Color.BLUE;
                    Drawable background = mColorPreview.getBackground();
                    if (background instanceof ColorDrawable)
                        color = ((ColorDrawable) background).getColor();
                    String colorString = "#"+Integer.toHexString(color).toUpperCase().substring(2);
                    Log.e("WEIGHT_TEST", "Weight = "+weight);
                    Log.e("ID_TEST", "ID = "+id);
                    String type = mEditTypeView.getText().toString();
                    Log.e("TEST", "TYPE = "+type);
                    replyIntent.putExtra(EXTRA_NEW_NAME, task);
                    replyIntent.putExtra(EXTRA_NEW_WEIGHT, weight);
                    replyIntent.putExtra(EXTRA_NEW_TYPE, type);
                    replyIntent.putExtra(EXTRA_NEW_NOTES, notes);
                    replyIntent.putExtra(EXTRA_NEW_ID, id);
                    replyIntent.putExtra(EXTRA_NEW_COLOR, colorString);
                    setResult(RESULT_OK, replyIntent);
                }
                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.hitsound);
                mp.start();
                finish();
            }
        });

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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (spinnerSelectedByUser) {
            String[] colors = getResources().getStringArray(R.array.colors_array);
            mColorPreview.setBackgroundColor(Color.parseColor(colors[i]));
            String spinnerLabel = adapterView.getItemAtPosition(i).toString();
            Toast.makeText(getApplicationContext(), spinnerLabel, Toast.LENGTH_LONG).show();
        }
        spinnerSelectedByUser = true;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}