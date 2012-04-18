package se.mah.helmet;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AlarmAcknowledgeActivity extends Activity {
	public static final String TAG = AlarmAcknowledgeActivity.class.getSimpleName();

	public static final int RESULT_ALARM_CANCELLED = 100;
	public static final int RESULT_SEND_ALARM = 90000;

	private int countDown = 30000; // Time left in milliseconds
	private long period = 1000;
	
	private Button btnCancel;
	private TextView txtvCountDown;
	
	private Timer timer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alarm_acknowledge);
		
		btnCancel = (Button) findViewById(R.id.btnAlarmAcknowledge);
		txtvCountDown = (TextView) findViewById(R.id.txtvAlarmCountDown);
		
		btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_ALARM_CANCELLED);
                finish();
            }
        });
		
		timer = new Timer(TAG + "Timer");
		timer.scheduleAtFixedRate(
				new AlarmTimerTask(), 
				0, 
				period);
		
	}
	
	private class AlarmTimerTask extends TimerTask {
		@Override
		public void run() {
			countDown -= period;
			if (countDown <= 0) {
				timer.cancel();
				setResult(RESULT_SEND_ALARM);
				finish();
			} else {
				txtvCountDown.setText(countDown / 1000 + " seconds left to cancel.");
			}
		}
	}
}