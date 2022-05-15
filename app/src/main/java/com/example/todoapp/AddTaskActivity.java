package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.todoapp.data.Repository;
import com.example.todoapp.data.Task;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Date;

public class AddTaskActivity extends AppCompatActivity {

    private EditText titleEditText;
    private EditText descEditText;
    private EditText etDate;
    private Button addButton;
    private Repository repository;
    TextInputLayout tlTitle, tlDescription, tlDate;
    RadioButton rdHigh, rdLow, rdMedium;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        titleEditText = findViewById(R.id.title_at);
        rdHigh = findViewById(R.id.rd_high);
        rdLow = findViewById(R.id.rd_low);
        rdMedium = findViewById(R.id.rd_medium);

        rdHigh.setChecked(true);
        tlTitle = findViewById(R.id.tlTitle);
        tlDescription = findViewById(R.id.tlDescription);
        tlDate = findViewById(R.id.tlDate);
        descEditText = findViewById(R.id.desc_at);
        addButton = findViewById(R.id.add_button);
        etDate = findViewById(R.id.calender);
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(AddTaskActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        String month = "";
                        String day = "";
                        if (selectedmonth < 10) {
                            month = "0" + String.valueOf(selectedmonth + 1);
                        } else {
                            month = String.valueOf(selectedmonth + 1);
                        }
                        if (selectedday < 10) {
                            day = "0" + String.valueOf(selectedday);
                        } else {
                            day = String.valueOf(selectedday);
                        }
                        String date = String.valueOf(selectedyear + "/" + month + "/" + day);
                        etDate.setText(date);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                mDatePicker.show();
            }
        });


        repository = Repository.getRepository(this.getApplication());


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (titleEditText.getText().toString().isEmpty()) {
                    tlTitle.setError("Required.");
                    titleEditText.requestFocus();
                } else if (descEditText.getText().toString().isEmpty()) {
                    tlDescription.setError("Required.");
                    descEditText.requestFocus();
                } else if (etDate.getText().toString().isEmpty()) {
                    tlDate.setError("Required.");
                    etDate.requestFocus();
                } else {
                    String title = titleEditText.getText().toString();
                    String desc = descEditText.getText().toString();

                    int priority = 1;
                    if (rdHigh.isChecked()) priority = 1;
                    if (rdLow.isChecked()) priority = 2;
                    if (rdMedium.isChecked()) priority = 3;

                    Task task = new Task(title, desc, priority, new Date(), DateUtils.convertToDate(etDate.getText().toString()));
                    repository.addTask(task);
                    finish();
                }
            }
        });

    }
}