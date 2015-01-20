package task_manager.wizut.com.taskmanager;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by micha_000 on 2015-01-06.
 */
public class Task extends SugarRecord<Task> {
    private String title;
    private String description;
    private Date date;

    public Task() {

    }

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
        this.date = new Date();
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
