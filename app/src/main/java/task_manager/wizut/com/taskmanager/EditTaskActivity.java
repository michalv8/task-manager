package task_manager.wizut.com.taskmanager;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class EditTaskActivity extends Activity implements Validator.ValidationListener, OnDateSetListener, OnTimeSetListener {

    @Required(order = 1, messageResId = R.string.validation_required)
    @InjectView(R.id.edit_task_title)
    TextView taskTitle;

    @Required(order = 2,messageResId = R.string.validation_required)
    @InjectView(R.id.edit_task_description)
    TextView taskDescription;

    @InjectView(R.id.edit_task_date)
    TextView taskDateText;

    Calendar taskDateTime;

    private TimePickerDialog taskTime;
    private DatePickerDialog taskDate;
    private SimpleDateFormat dateFormatter;

    private Validator validator;

    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        ButterKnife.inject(this);
        validator = new Validator(this);
        validator.setValidationListener(this);
        taskDateTime = Calendar.getInstance();
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy H:m", Locale.getDefault());
        task = Task.findById(Task.class, getIntent().getLongExtra("task_id", -1));
        taskTitle.setText(task.getTitle());
        taskDescription.setText(task.getDescription());
        taskDateTime = Calendar.getInstance();
        taskDateTime.setTime(task.getDate());
        taskDateText.setText(dateFormatter.format(taskDateTime.getTime()));
        taskDate = new DatePickerDialog(this, this, taskDateTime.get(Calendar.YEAR), taskDateTime.get(Calendar.MONTH), taskDateTime.get(Calendar.DAY_OF_MONTH));
        taskTime = new TimePickerDialog(this, this, taskDateTime.get(Calendar.HOUR), taskDateTime.get(Calendar.MINUTE), true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id) {
            case R.id.action_save_task:
                validator.validate();
                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        taskDateTime.set(Calendar.HOUR, hourOfDay);
        taskDateTime.set(Calendar.MINUTE, minute);
        taskDateText.setText(dateFormatter.format(taskDateTime.getTime()));
    }

    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        taskDateTime.set(year, monthOfYear, dayOfMonth);
        taskDateText.setText(dateFormatter.format(taskDateTime.getTime()));
        taskTime.show();
    }

    @Override
    public void onValidationSucceeded() {
        task.save();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), R.string.message_task_modified, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onValidationFailed(View view, Rule<?> rule) {
        view.requestFocus();
        ((TextView)view).setError(rule.getFailureMessage());
    }

    @OnClick(R.id.edit_task_date)
    public void onTaskDateClick() {
        taskDate.show();
    }

    private void resetErrors() {
        taskTitle.setError(null);
        taskDescription.setError(null);
    }
}
