package com.tasks.domain;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by andre on 7/2/17.
 */
@Entity
@Table(name = "user_tasks")
@NamedQueries({
        @NamedQuery(name = "com.tasks.domain.Task.findAllByUserId", query = "select a from Task a where a.user.id = :userId"),
        @NamedQuery(name = "com.tasks.domain.Task.findByTaskAndUserId", query = "select a from Task a where a.id = :id and " +
                "a.user.id = :userId"),
        @NamedQuery(name = "com.tasks.domain.Task.deleteTaskById", query = "DELETE Task where id = :id")
})
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private Date date_time;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private CoreUser user;

    public Task () {}

    public Task(String name, String description, String date_time, CoreUser user) throws ParseException {
        this.description = description;
        this.user = user;
        this.name = name;
        setDate_timeFromString(date_time);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate_time() {
        return date_time;
    }

    public void setDate_time(Date date_time) {
        this.date_time = date_time;
    }

    public void setDate_timeFromString(String date_time) {
        SimpleDateFormat fs = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
        try {
            this.date_time = fs.parse(date_time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public CoreUser getUser() {
        return user;
    }

    public void setUser(CoreUser user) {
        this.user = user;
    }
}
