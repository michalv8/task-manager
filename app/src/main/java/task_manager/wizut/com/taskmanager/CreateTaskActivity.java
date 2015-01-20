package task_manager.wizut.com.taskmanager;

import android.app.Activity;
import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;

import java.sql.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class CreateTaskActivity extends Activity implements Validator.ValidationListener, OnDateSetListener, OnTimeSetListener {

    @Required(order = 1, messageResId = R.string.validation_required)
    @InjectView(R.id.create_task_title)
    TextView taskTitle;

    @Required(order = 2,messageResId = R.string.validation_required)
    @InjectView(R.id.create_task_description)
    TextView taskDescription;

    @InjectView(R.id.create_task_date)
    TextView taskDateText;

    Calendar taskDateTime;

    private TimePickerDialog taskTime;
    private DatePickerDialog taskDate;
    private SimpleDateFormat dateFormatter;

    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        ButterKnife.inject(this);
        validator = new Validator(this);
        validator.setValidationListener(this);
        taskDateTime = Calendar.getInstance();
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy H:m", Locale.getDefault());
        taskDateText.setText(dateFormatter.format(taskDateTime.getTime()));
        taskDate = new DatePickerDialog(this, this, taskDateTime.get(Calendar.YEAR), taskDateTime.get(Calendar.MONTH), taskDateTime.get(Calendar.DAY_OF_MONTH));
        taskTime = new TimePickerDialog(this, this, taskDateTime.get(Calendar.HOUR), taskDateTime.get(Calendar.MINUTE), true);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_create_task:
                resetErrors();
                validator.validate();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onValidationSucceeded() {
        Task task = new Task(taskTitle.getText().toString(), taskDescription.getText().toString());
        task.save();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), R.string.message_task_created, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onValidationFailed(View view, Rule<?> rule) {
        view.requestFocus();
        ((TextView)view).setError(rule.getFailureMessage());
    }

    @OnClick(R.id.create_task_date)
    public void onTaskDateClick() {
        taskDate.show();
    }

    private void resetErrors() {
        taskTitle.setError(null);
        taskDescription.setError(null);
    }
}
