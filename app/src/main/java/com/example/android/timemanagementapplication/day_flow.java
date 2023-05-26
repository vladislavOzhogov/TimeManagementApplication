package com.example.android.timemanagementapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.android.timemanagementapplication.controllers.TaskController;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class day_flow extends AppCompatActivity {

    TableLayout tableLayout1;
    TableLayout tableLayout2;
    TableRow tableRow;
    TableRow.LayoutParams rowParams;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_flow);
        String currentDateString = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date());
        TextView currentDate = findViewById(R.id.currentDate);
        currentDate.setText("Day follow up for the date: " + currentDateString);
        Button createTask = (Button) findViewById(R.id.createTaskButton);
        tableLayout1 = findViewById(R.id.first);
        TextView currentPointsTextView = findViewById(R.id.pointsNumber);

        storeNewRow();

        createTask.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                tableRow = new TableRow(day_flow.this);
                tableRow.setWeightSum(2);
                long id = TableRow.generateViewId();
                rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.FILL_PARENT, id);
                tableLayout1.addView(tableRow, rowParams);
                EditText firstView = new EditText(day_flow.this);
                int maxLength1 = 20;
                int maxLength2 = 5;
                Drawable drawable = getDrawable(R.drawable.linear_layout_border);
                firstView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));
                firstView.setText("Task name");
                firstView.setTextSize(12);
                firstView.setTextColor(getResources().getColor(R.color.white));
                firstView.setBackground(drawable);
                firstView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                firstView.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength1)});
                EditText secondView = new EditText(day_flow.this);
                secondView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));
                secondView.setInputType(InputType.TYPE_CLASS_NUMBER);
                secondView.setText("0");
                secondView.setTextSize(12);
                secondView.setTextColor(getResources().getColor(R.color.white));
                secondView.setBackground(drawable);
                secondView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                secondView.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength2)});
                secondView.addTextChangedListener(new TextWatcher() {
                    int temp = 0;
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        if(!editable.toString().equals(""))
                        {
                            currentPointsTextView.setText((Integer.parseInt(currentPointsTextView.getText().toString()) + Integer.parseInt(editable.toString()) - temp) + "");
                            temp = Integer.parseInt(editable.toString());
                        }
                    }
                });
                tableRow.addView(firstView);
                tableRow.addView(secondView);
            }
        });

        Button createReward = (Button) findViewById(R.id.createRewardButton);
        tableLayout2 = findViewById(R.id.second);
        getSupportActionBar().setHomeButtonEnabled(true);

        createReward.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                tableRow = new TableRow(day_flow.this);
                tableRow.setWeightSum(2);
                long id = TableRow.generateViewId();
                rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.FILL_PARENT, id);
                tableLayout2.addView(tableRow, rowParams);
                EditText firstView = new EditText(day_flow.this);
                int maxLength1 = 20;
                int maxLength2 = 5;
                Drawable drawable = getDrawable(R.drawable.linear_layout_border);
                firstView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));
                firstView.setText("Reward name");
                firstView.setTextSize(12);
                firstView.setTextColor(getResources().getColor(R.color.white));
                firstView.setBackground(drawable);
                firstView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                firstView.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength1)});
                EditText secondView = new EditText(day_flow.this);
                secondView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));
                secondView.setInputType(InputType.TYPE_CLASS_NUMBER);
                secondView.setText("0");
                secondView.setTextSize(12);
                secondView.setTextColor(getResources().getColor(R.color.white));
                secondView.setBackground(drawable);
                secondView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                secondView.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength2)});
                secondView.addTextChangedListener(new TextWatcher() {
                    int temp = 0;
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        if(!editable.toString().equals(""))
                        {
                            currentPointsTextView.setText((Integer.parseInt(currentPointsTextView.getText().toString()) - Integer.parseInt(editable.toString()) + temp) + "");
                            temp = Integer.parseInt(editable.toString());
                        }
                    }
                });
                tableRow.addView(firstView);
                tableRow.addView(secondView);
            }
        });
    }

    public void storeNewRow() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Tasks");

        String key = databaseReference.push().getKey();
        TaskController taskController = new TaskController("TaskWritten1", "PointsAwardedWritten1", key);
        databaseReference.setValue(taskController);
    }
}