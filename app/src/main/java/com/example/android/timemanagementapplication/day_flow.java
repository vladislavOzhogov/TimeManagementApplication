package com.example.android.timemanagementapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.android.timemanagementapplication.controllers.RewardController;
import com.example.android.timemanagementapplication.controllers.TaskController;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class day_flow extends AppCompatActivity {

    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private final String userEmail = firebaseUser.getEmail();
    private final int maxNameLength = 20;
    private final int maxPointsLength = 5;
    private DatabaseReference storedIdTask;
    private DatabaseReference storedIdReward;
    private TextView currentPointsTextView;
    private Button createTask;
    private Button createReward;
    private Button save;
    private TableLayout tableLayout1;
    private TableLayout tableLayout2;
    private TableRow tableRow;
    private TableRow.LayoutParams rowParams;
    private String taskName;
    private String awardedPoints;
    private Task task;
    private Reward newReward;
    private String rewardName;
    private String spentPoints;
    private boolean taskChecker = false;
    private boolean rewardsChecker = false;
    private List<EditText> taskViewList;
    private List<EditText> awardedPointsList;
    private List<EditText> rewardViewList;
    private List<EditText> spentPointsList;
    private String currentDate;
    int temp = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_flow);
        setDate();
        getAllViews();
        setTaskOnClickListener();
        setRewardsOnClickListener();
        setSaveOnClickListener();
        showExistingRows();
        taskViewList = new LinkedList<>();
        awardedPointsList = new LinkedList<>();
        rewardViewList = new LinkedList<>();
        spentPointsList = new LinkedList<>();
    }

    public void getAllViews()
    {
        createTask = (Button) findViewById(R.id.createTaskButton);
        createReward = (Button) findViewById(R.id.createRewardButton);
        save = (Button) findViewById(R.id.save);
        tableLayout1 = findViewById(R.id.first);
        currentPointsTextView = findViewById(R.id.pointsNumber);
        tableLayout2 = findViewById(R.id.second);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

    public void setTaskOnClickListener()
    {
        createTask.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                createTaskRow("New Task", "0");
            }
        });
    }

    public void setRewardsOnClickListener()
    {
        createReward.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                createRewardRow("New Reward", "0");
            }
        });
    }

    public void setSaveOnClickListener()
    {
        save.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                DatabaseReference taskReference = firebaseDatabase.getReference("Tasks");
                taskReference.orderByChild("date").equalTo(currentDate).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        for (DataSnapshot ds : dataSnapshot.getChildren())
                        {
                            ds.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError)
                    {
                    }

                });

                DatabaseReference rewardReference = firebaseDatabase.getReference("Rewards");
                rewardReference.orderByChild("date").equalTo(currentDate).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        for (DataSnapshot ds : dataSnapshot.getChildren())
                        {
                            ds.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError)
                    {
                    }

                });

                for(int i = 0; i<taskViewList.size(); i++)
                {
                    Task newTask = new Task(taskViewList.get(i).getText().toString(), awardedPointsList.get(i).getText().toString());
                    TaskController taskController = new TaskController(userEmail, newTask, currentDate);
                    storedIdTask = taskReference.push();
                    storedIdTask.setValue(taskController);
                }
                for(int i = 0; i<rewardViewList.size(); i++)
                {
                    Reward newReward = new Reward(rewardViewList.get(i).getText().toString(), spentPointsList.get(i).getText().toString());
                    RewardController rewardController = new RewardController(userEmail, newReward, currentDate);
                    storedIdReward = rewardReference.push();
                    storedIdReward.setValue(rewardController);
                }
            }
        });

    }

    public void setDate()
    {
        currentDate = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date());
        TextView currentDateView = findViewById(R.id.currentDate);
        currentDateView.setText("Day follow up for the date: " + currentDate);
    }

    public void showExistingRows()
    {
        DatabaseReference tasksReference = firebaseDatabase.getReference("Tasks");
        DatabaseReference rewardsReference = firebaseDatabase.getReference("Rewards");
        ValueEventListener taskListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!taskChecker)
                {
                    for(DataSnapshot snapshot1 : dataSnapshot.getChildren()){
                        for(DataSnapshot snapshot2 : snapshot1.getChildren()){
                            try{
                                if(snapshot1.child("date").getValue().toString().equals(currentDate))
                                {
                                    createTaskRow(snapshot2.child("taskName").getValue().toString(), snapshot2.child("pointsAwarded").getValue().toString());
                                    temp += Integer.parseInt(snapshot2.child("pointsAwarded").getValue().toString());
                                }
                            }
                            catch (Exception e)
                            {

                            }
                        }
                    }
                    taskChecker = true;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        tasksReference.addValueEventListener(taskListener);

        ValueEventListener rewardsListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!rewardsChecker)
                {
                    for(DataSnapshot snapshot1 : dataSnapshot.getChildren()){
                        for(DataSnapshot snapshot2 : snapshot1.getChildren()){
                            try{
                                if(snapshot1.child("date").getValue().toString().equals(currentDate)) {
                                    createRewardRow(snapshot2.child("rewardName").getValue().toString(), snapshot2.child("spentPoints").getValue().toString());
                                    temp -= Integer.parseInt(snapshot2.child("spentPoints").getValue().toString());
                                }
                            }
                            catch (Exception e)
                            {

                            }
                        }
                    }
                    rewardsChecker = true;
                    currentPointsTextView.setText(Integer.toString(temp));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        rewardsReference.addValueEventListener(rewardsListener);

    }

//    public void storeNewTask(String taskName, int awardedPoints)
//    {
//        DatabaseReference databaseReference = firebaseDatabase.getReference("Tasks");
//        storedIdTask = databaseReference.push();
//        storedIdTask.setValue(taskName);
//        storedIdTask.setValue(awardedPoints);
//    }
//
//    public void storeNewReward(Reward reward)
//    {
//        DatabaseReference databaseReference = firebaseDatabase.getReference("Rewards");
//        RewardController rewardController = new RewardController(userEmail, reward);
//        storedIdReward = databaseReference.push();
//        storedIdReward.setValue(rewardController);
//    }

    public EditText createTaskView(String taskName, Drawable drawable)
    {
        EditText taskView = new EditText(day_flow.this);
        taskView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        taskView.setText(taskName);
        taskView.setTextSize(12);
        taskView.setTextColor(getResources().getColor(R.color.white));
        taskView.setBackground(drawable);
        taskView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        taskView.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxNameLength)});
        return taskView;
    }

    public EditText createSpentPointsView(String spentPointsAmount, EditText rewardView, Drawable drawable)
    {
        EditText spentPointsView = new EditText(day_flow.this);
        spentPointsView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        spentPointsView.setInputType(InputType.TYPE_CLASS_NUMBER);
        spentPointsView.setText(spentPointsAmount);
        spentPointsView.setTextSize(12);
        spentPointsView.setTextColor(getResources().getColor(R.color.white));
        spentPointsView.setBackground(drawable);
        spentPointsView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        spentPointsView.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxPointsLength)});
        spentPointsView.addTextChangedListener(new TextWatcher() {
            int temp = 0;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                temp = Integer.parseInt(spentPointsView.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().equals(""))
                {
                    if(temp>Integer.parseInt(editable.toString()))
                    {
                        temp -= Integer.parseInt(editable.toString());
                    }
                    else
                    {
                        temp = Integer.parseInt(editable.toString()) - temp;
                    }
                    currentPointsTextView.setText((Integer.parseInt(currentPointsTextView.getText().toString()) +temp) + "");
                }
            }
        });
        return spentPointsView;
    }

    public EditText createRewardView(String rewardName, Drawable drawable)
    {
        EditText rewardView = new EditText(day_flow.this);
        rewardView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        rewardView.setText(rewardName);
        rewardView.setTextSize(12);
        rewardView.setTextColor(getResources().getColor(R.color.white));
        rewardView.setBackground(drawable);
        rewardView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        rewardView.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxNameLength)});
        return rewardView;
    }

    public EditText createAwardedPointsView(String awardedPointsAmount, Drawable drawable, EditText taskView)
    {
        EditText awardedPointsView = new EditText(day_flow.this);
        awardedPointsView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        awardedPointsView.setInputType(InputType.TYPE_CLASS_NUMBER);
        awardedPointsView.setText(awardedPointsAmount);
        awardedPointsView.setTextSize(12);
        awardedPointsView.setTextColor(getResources().getColor(R.color.white));
        awardedPointsView.setBackground(drawable);
        awardedPointsView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        awardedPointsView.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxPointsLength)});
        awardedPointsView.addTextChangedListener(new TextWatcher() {
            int temp = 0;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                temp = Integer.parseInt(awardedPointsView.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().equals(""))
                {
                    if(temp>Integer.parseInt(editable.toString()))
                    {
                        temp -= Integer.parseInt(editable.toString());
                    }
                    else
                    {
                        temp = Integer.parseInt(editable.toString()) - temp;
                    }
                    currentPointsTextView.setText((Integer.parseInt(currentPointsTextView.getText().toString()) - temp) + "");
                }
            }
        });
        return awardedPointsView;
    }

    public void createTaskRow(String taskName, String awardedPoints)
    {
        try{
            tableRow = new TableRow(day_flow.this);
            tableRow.setWeightSum(2);
            long id = TableRow.generateViewId();
            rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.FILL_PARENT, id);
            tableLayout1.addView(tableRow, rowParams);
            Drawable drawable = getDrawable(R.drawable.linear_layout_border);
            EditText taskView = createTaskView(taskName, drawable);
            taskViewList.add(taskView);
            EditText awardedPointsView = createAwardedPointsView(awardedPoints, drawable, taskView);
            awardedPointsList.add(awardedPointsView);
            tableRow.addView(taskView);
            tableRow.addView(awardedPointsView);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void createRewardRow(String rewardName, String spentPoints)
    {
        tableRow = new TableRow(day_flow.this);
        tableRow.setWeightSum(2);
        long id = TableRow.generateViewId();
        rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.FILL_PARENT, id);
        tableLayout2.addView(tableRow, rowParams);
        Drawable drawable = getDrawable(R.drawable.linear_layout_border);
        EditText rewardView = createRewardView(rewardName, drawable);
        rewardViewList.add(rewardView);
        EditText spentPointsView = createSpentPointsView(spentPoints, rewardView, drawable);
        spentPointsList.add(spentPointsView);
        tableRow.addView(rewardView);
        tableRow.addView(spentPointsView);
    }
}