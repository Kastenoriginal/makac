package sk.tuke.smart.makac;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WorkoutDetailActivity extends AppCompatActivity {

    @BindView(R.id.textview_workoutdetail_workouttitle)
    TextView tvWorkoutTitle;

    @BindView(R.id.textview_workoutdetail_sportactivity)
    TextView tvSportActivityType;

    @BindView(R.id.textview_workoutdetail_activitydate)
    TextView tvActivityDate;

    @BindView(R.id.textview_workoutdetail_valueduration)
    TextView tvActivityDuration;

    @BindView(R.id.textview_workoutdetail_valuecalories)
    TextView tvActivityCalories;

    @BindView(R.id.textview_workoutdetail_valuedistance)
    TextView tvActivityDistance;

    @BindView(R.id.textview_workoutdetail_valueavgpace)
    TextView tvActivityAvgPace;

    private int sportActivity;
    private long duration;
    private double distance;
    private double pace;
    private double calories;
    private ArrayList<List<Location>> finalPositionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_detail);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_workoutdetail_gplusshare)
    void shareGooglePlus() {
        // TODO: 11/7/2017
    }

    @OnClick(R.id.button_workoutdetail_twittershare)
    void shareTwitter() {
        // TODO: 11/7/2017
    }

    @OnClick(R.id.button_workoutdetail_emailshare)
    void sendEmail() {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"recipient@example.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
        i.putExtra(Intent.EXTRA_TEXT   , "body of email");
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(WorkoutDetailActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.button_workoutdetail_fbsharebtn)
    void shareFacebook() {
        // TODO: 11/7/2017
    }
}
