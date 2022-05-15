package com.example.todoapp.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "tasks")
public class Task {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    @NonNull
    private String title;
    private String description;
    private int priority;
    @ColumnInfo(name="created_date")
    private Date createdDate;
    @ColumnInfo(name="due_date")
    private Date dueDate;



    public Task(String title, String description,int priority, Date createdDate, Date dueDate) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.createdDate = createdDate;
        this.dueDate = dueDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @Ignore
    public Task(int id,String title, String description, int priority, Date createdDate, String status) {
        this.id=id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.createdDate = createdDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

}
