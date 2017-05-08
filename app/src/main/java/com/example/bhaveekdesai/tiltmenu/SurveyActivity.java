package com.example.bhaveekdesai.tiltmenu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SurveyActivity extends AppCompatActivity {

    String participant_id;
    DataIO data;
    int two_zone, four_zone, pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        participant_id = getIntent().getStringExtra("participant_id");
        data = new DataIO();

        final Button submit_button = (Button) findViewById(R.id.survey_submit);
        submit_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                two_zone = ((RadioGroup) findViewById(R.id.twozone_group))
                            .indexOfChild( ((RadioGroup) findViewById(R.id.twozone_group))
                                    .findViewById(((RadioGroup) findViewById(R.id.twozone_group)).getCheckedRadioButtonId()));

                four_zone = ((RadioGroup) findViewById(R.id.fourzone_group))
                        .indexOfChild( ((RadioGroup) findViewById(R.id.fourzone_group))
                                .findViewById(((RadioGroup) findViewById(R.id.fourzone_group)).getCheckedRadioButtonId()));

                pref = ((RadioGroup) findViewById(R.id.pref_group)).getCheckedRadioButtonId();

                // Perform action on click
                if (pref!=-1){
                    //record data
                    RadioGroup rg1 = (RadioGroup) findViewById(R.id.pref_group);
                    int id= rg1.getCheckedRadioButtonId();
                    View radioButton = rg1.findViewById(id);
                    int radioId = rg1.indexOfChild(radioButton);
                    RadioButton btn = (RadioButton) rg1.getChildAt(radioId);
                    String preference = (String) btn.getText();

                    if (data.isExternalStorageWritable()){
                        String dataToWrite = participant_id+","+(two_zone+1)+","+(four_zone+1)+","+preference+"\n";
                        data.writeToFile(dataToWrite, getString(R.string.survey_data));
                    }
                    //Go back home
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                }
            }
        });
    }
}
