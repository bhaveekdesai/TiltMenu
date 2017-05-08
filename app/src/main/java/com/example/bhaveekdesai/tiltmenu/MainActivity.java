package com.example.bhaveekdesai.tiltmenu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText participant_id, trial_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        participant_id = (EditText) findViewById(R.id.participant_id);
        trial_id = (EditText) findViewById(R.id.trial_id);

        final Button twozone_button = (Button) findViewById(R.id.twozone_button);
        twozone_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                if (validate()){
                    Intent i = new Intent(getApplicationContext(), TwoZoneActivity.class);
                    i.putExtra("participant_id", participant_id.getText().toString());
                    i.putExtra("trial_id", trial_id.getText().toString());
                    startActivity(i);
                }
            }
        });

        final Button fourzone_button = (Button) findViewById(R.id.fourzone_button);
        fourzone_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                if (validate()){
                    Intent i = new Intent(getApplicationContext(), FourZoneActivity.class);
                    i.putExtra("participant_id", participant_id.getText().toString());
                    i.putExtra("trial_id", trial_id.getText().toString());
                    startActivity(i);
                }
            }
        });

        final Button survey_button = (Button) findViewById(R.id.survey_button);
        survey_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                if (!participant_id.getText().toString().trim().equals("")){
                    Intent i = new Intent(getApplicationContext(), SurveyActivity.class);
                    i.putExtra("participant_id", participant_id.getText().toString());
                    i.putExtra("trial_id", trial_id.getText().toString());
                    startActivity(i);
                } else {
                    participant_id.setError( "Participant ID is required!" );
                }
            }
        });
    }

    public boolean validate() {
        if (participant_id.getText().toString().trim().equals("")){
            participant_id.setError( "Participant ID is required!" );
            return false;
        }
        if (trial_id.getText().toString().trim().equals("")){
            trial_id.setError( "Trial# is required!" );
            return false;
        }
        return true;
    }
}
