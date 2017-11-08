package sk.tuke.smart.makac;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sk.tuke.smart.makac.helpers.MainHelper;
import sk.tuke.smart.makac.services.StopwatchCallbackListener;
import sk.tuke.smart.makac.utils.Constants;

public class StopwatchActivity extends AppCompatActivity implements StopwatchCallbackListener {

    @BindView(R.id.textview_stopwatch_duration)
    TextView tvDurationValue;

    @BindView(R.id.button_stopwatch_start)
    Button btnStartWorkout;

    @BindView(R.id.button_stopwatch_pause)
    Button btnPauseWorkout;

    @BindView(R.id.button_stopwatch_continue)
    Button btnContinueWorkout;

    @BindView(R.id.button_stopwatch_endworkout)
    Button btnEndWorkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_stopwatch_selectsport)
    void selectSport() {
    }

    @OnClick(R.id.button_stopwatch_start)
    void startWorkout() {
        btnStartWorkout.setVisibility(View.GONE);
        btnPauseWorkout.setVisibility(View.VISIBLE);
        btnEndWorkout.setVisibility(View.VISIBLE);
        sendBroadcastIntent(Constants.COMMAND_START);
    }

    @OnClick(R.id.button_stopwatch_endworkout)
    void endWorkout() {
        btnStartWorkout.setVisibility(View.VISIBLE);
        btnPauseWorkout.setVisibility(View.GONE);
        btnEndWorkout.setVisibility(View.GONE);

        sendBroadcastIntent(Constants.COMMAND_STOP);
    }

    @OnClick(R.id.button_stopwatch_pause)
    void pauseWorkout() {
        btnContinueWorkout.setVisibility(View.VISIBLE);
        btnPauseWorkout.setVisibility(View.GONE);
        sendBroadcastIntent(Constants.COMMAND_PAUSE);
    }

    @OnClick(R.id.button_stopwatch_continue)
    void continueWorkout() {
        btnContinueWorkout.setVisibility(View.GONE);
        btnPauseWorkout.setVisibility(View.VISIBLE);
        sendBroadcastIntent(Constants.COMMAND_CONTINUE);
    }

    private void sendBroadcastIntent(String broadcastAction) {
        Intent intent = new Intent();
        intent.setAction(broadcastAction);
        sendBroadcast(intent);
    }

    @Override
    public void onTick(Intent intent) {
        // TODO: 11/8/2017 updates all workout counters (pace, duration, calories etc., incl. buttons).
        long duration = (long) intent.getIntExtra(Constants.TAG_DURATION, 0);
        tvDurationValue.setText(MainHelper.formatDuration(duration));
    }
}
