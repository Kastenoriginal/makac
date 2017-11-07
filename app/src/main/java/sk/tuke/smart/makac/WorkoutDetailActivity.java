package sk.tuke.smart.makac;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import butterknife.OnClick;

public class WorkoutDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_detail);
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
