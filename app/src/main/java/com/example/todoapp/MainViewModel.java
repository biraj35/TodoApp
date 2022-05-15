package com.example.todoapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.todoapp.data.Repository;
import com.example.todoapp.data.Task;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    
    Repository repository;
    LiveData<List<Task>> tasks;
    Task task;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository=Repository.getRepository(application);
        tasks=repository.getAllTasks();
    }

    public LiveData<List<Task>> getAllTasks() {

        return tasks;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
