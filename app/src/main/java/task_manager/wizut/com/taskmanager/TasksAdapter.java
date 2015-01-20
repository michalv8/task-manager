package task_manager.wizut.com.taskmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by micha_000 on 2015-01-06.
 */
public class TasksAdapter extends ArrayAdapter<Task> {


    public TasksAdapter(Context context, int resource, List<Task> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Task task = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_item, parent, false);
        }

        TextView tvTitle = (TextView) convertView.findViewById(R.id.task_item_title);
        TextView tvDate = (TextView) convertView.findViewById(R.id.task_item_date);
        //TextView tvDes

        tvTitle.setText(task.getTitle());
        tvDate.setText(task.getDate().toString());

        return convertView;
    }
}
