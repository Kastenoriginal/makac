package sk.tuke.smart.makac;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sk.tuke.smart.makac.services.TrackerService;

public class StopwatchActivity extends AppCompatActivity {

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
        TrackerService.startWorkout();
        btnStartWorkout.setVisibility(View.GONE);
        btnPauseWorkout.setVisibility(View.VISIBLE);
        btnEndWorkout.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.button_stopwatch_endworkout)
    void endWorkout() {
        TrackerService.stopWorkout();
        btnStartWorkout.setVisibility(View.VISIBLE);
        btnPauseWorkout.setVisibility(View.GONE);
        btnEndWorkout.setVisibility(View.GONE);
    }

    @OnClick(R.id.button_stopwatch_pause)
    void pauseWorkout() {
        TrackerService.pauseWorkout();
        btnContinueWorkout.setVisibility(View.VISIBLE);
        btnPauseWorkout.setVisibility(View.GONE);
    }

    @OnClick(R.id.button_stopwatch_continue)
    void continueWorkout() {
        btnContinueWorkout.setVisibility(View.GONE);
        btnPauseWorkout.setVisibility(View.VISIBLE);
    }
}
