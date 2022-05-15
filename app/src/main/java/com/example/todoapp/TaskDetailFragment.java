package com.example.todoapp;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.todoapp.data.Repository;
import com.example.todoapp.data.Task;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskDetailFragment extends Fragment {
    private static final String LOG_TAG = TaskDetailFragment.class.getSimpleName();

    private Task mTask;
    private Repository sTaskRepository;
    private MainViewModel mViewModel;

    private EditText titleEditText;
    private EditText descEditText;
    private Button addButton;
    private Repository repository;
    private String title;
    private String description;
    private EditText textViewTitle;
    private EditText textViewDetail;
    private EditText textViewCalender;
    private Button deleteButton;
    private Button editButton;
    private EditText etDate;
    TextInputLayout tlTitle, tlDescription, tlDate;
    RadioButton rdHigh, rdLow, rdMedium;

    public static TaskDetailFragment newInstance() {
        return new TaskDetailFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);


        sTaskRepository = Repository.getRepository(getActivity().getApplication());

        View view = inflater.inflate(R.layout.fragment_task_detail, container, false);
        updateUI(view);
        textViewCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
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
                        textViewCalender.setText(date);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                mDatePicker.show();
            }
        });
        return view;

    }

    private void updateUI(View view) {

        mTask = mViewModel.getTask();

        textViewTitle = view.findViewById(R.id.textViewTitle);
        textViewTitle.setText(mTask.getTitle());

        textViewDetail = view.findViewById(R.id.textViewDetail);
        textViewDetail.setText(mTask.getDescription());

        textViewCalender = view.findViewById(R.id.textViewDate);
        textViewCalender.setText(DateUtils.convertToString(mTask.getDueDate(), "YYYY/MM/dd"));

        rdHigh = view.findViewById(R.id.rd_high);
        rdLow = view.findViewById(R.id.rd_low);
        rdMedium = view.findViewById(R.id.rd_medium);

        tlTitle = view.findViewById(R.id.tlTitle);
        tlDescription = view.findViewById(R.id.tlDescription);
        tlDate = view.findViewById(R.id.tlDate);
        etDate = view.findViewById(R.id.textViewDate);

        int priority=mTask.getPriority();
        if (priority==1)
        {
            //textview set text high
            rdHigh.setChecked(true);
        }

        if (priority==2)
        {
            //textview set text low
            rdLow.setChecked(true);
        }

        if (priority==3)
        {
            //textview set text medium
            rdMedium.setChecked(true);
        }

        deleteButton = view.findViewById(R.id.buttonDelete);
        deleteButton.setOnClickListener(mTaskListener);

        editButton = view.findViewById(R.id.buttonUpdate);
        editButton.setOnClickListener(mTaskListener);
    }

    public void setViewModel(MainViewModel mViewModel) {
        this.mViewModel = mViewModel;
    }

    /* Create an anonymous implementation of OnClickListener for all clickable view objects */
    private View.OnClickListener mTaskListener = new View.OnClickListener() {

        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.buttonUpdate:
                    if (textViewTitle.getText().toString().isEmpty()) {
                        tlTitle.setError("Required.");
                        textViewTitle.requestFocus();
                    } else if (textViewDetail.getText().toString().isEmpty()) {
                        tlDescription.setError("Required.");
                        textViewDetail.requestFocus();
                    } else if (textViewCalender.getText().toString().isEmpty()) {
                        tlDate.setError("Required.");
                        textViewCalender.requestFocus();
                    } else {
                        int priority = 1;
                        if (rdHigh.isChecked()) priority = 1;
                        if (rdLow.isChecked()) priority = 2;
                        if (rdMedium.isChecked()) priority = 3;
                        title = textViewTitle.getText().toString();
                        description = textViewDetail.getText().toString();
                        mTask.setTitle(title);
                        mTask.setDescription(description);
                        mTask.setPriority(priority);
                        mTask.setDueDate(DateUtils.convertToDate(textViewCalender.getText().toString()));
                        sTaskRepository.update(mTask);
                        doSubmit();
                    }

                    break;

                case R.id.buttonDelete:
                    sTaskRepository.delete(mTask);
                    doSubmit();
                    break;

                default:
                    break;
            }
        }
    };

    private void doSubmit() {

        mViewModel.setTask(mTask);

        TaskFragment taskFragment = TaskFragment.newInstance();

        assert getParentFragmentManager() != null;
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

        transaction.replace(R.id.container, taskFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }


}