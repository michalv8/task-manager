package task_manager.wizut.com.taskmanager;

import com.orm.SugarRecord;

import java.util.List;
/**
 * Created by micha_000 on 2015-01-06.
 */
public class TaskManager {

    public static java.util.Iterator<Task> findAllTasks() {
        return Task.findAll(Task.class);
    }

}
