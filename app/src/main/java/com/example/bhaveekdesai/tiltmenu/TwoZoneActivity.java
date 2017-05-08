package com.example.bhaveekdesai.tiltmenu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bhaveekdesai.tiltmenu.datastructures.Menu_two_item;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class TwoZoneActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor acc;
    private float x, y, z;
    private String optionSelected[];
    private float initX, initY;
    private Vibrator vib;

    ImageView ball, circ_menu;
    TextView route, up, down;

    Menu_two_item menu2, next;

    String options[][] = new String[8][4];
    long startTime, elapsedTime;
    String participant_id, trial_id;
    DataIO writer;

    int pixel, level, round;
    Bitmap bmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twozone);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        vib = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);

        ball = (ImageView) findViewById(R.id.cball);
        circ_menu = (ImageView) findViewById(R.id.circ_menu);
        route = (TextView) findViewById(R.id.croute);

        up = (TextView) findViewById(R.id.cup);
        down = (TextView) findViewById(R.id.cdown);
        level = 0;
        round = 0;

        participant_id = getIntent().getStringExtra("participant_id");
        trial_id = getIntent().getStringExtra("trial_id");

        writer = new DataIO();

        optionSelected = new String[2];
        optionSelected[0] = "";

        initX = 423;
        initY = 423;

        initialize();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        acc = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, acc, SensorManager.SENSOR_DELAY_NORMAL);

        bmap = ((BitmapDrawable)circ_menu.getDrawable()).getBitmap();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(round<=7) {
            x = event.values[0];
            y = event.values[1];
            z = event.values[2];

            if (x > 2.0) {
                ball.setX(ball.getX() - 20);
            } //move left
            if (x < -2.0) {
                ball.setX(ball.getX() + 20);
            } //move right
            if (y > 2.0) {
                ball.setY(ball.getY() + 20);
            } //move down
            if (y < -2.0) {
                ball.setY(ball.getY() - 20);
            } //move up

            if (ball.getX() >= 0 && ball.getY() >= 0)
                pixel = bmap.getPixel((int) ball.getX(), (int) ball.getY());
            else pixel = 0;

            //detect selection
            if (ball.getY() < initY && pixel == -9265201) {
                optionSelected[0] = "up";
                optionSelected[1] = up.getText().toString();
            } //UP round
            else if (ball.getY() > initY && pixel == -9265201) {
                optionSelected[0] = "down";
                optionSelected[1] = down.getText().toString();
            } //DOWN round

            if (optionSelected[0] != "") {

                if (optionSelected[1] == options[round][level]) {
                    if (level == 3) {
                        vib.vibrate(50);
                        vib.vibrate(50);
                        recordAndLoadNext(true);
                    } else {
                        //next level
                        level++;
                        switch (optionSelected[0]) {
                            case "up":
                                next = next.up;
                                break;

                            case "down":
                                next = next.down;
                                break;
                        }

                        up.setText(next.up.item_name);
                        down.setText(next.down.item_name);
                        vib.vibrate(50);
                    }

                } else {
                    vib.vibrate(200);
                    recordAndLoadNext(false);
                }

                ball.setX(initX);
                ball.setY(initY);
                pixel = 0;
                optionSelected[0] = "";
                optionSelected[1] = "";

                if (round <= 7)
                    route.setText(options[round][0] + " >> " + options[round][1] + " >> " + options[round][2] + " >> " + options[round][3]);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    void initialize() {

        //create menu tree
        menu2 = new Menu_two_item("root");
        Menu_two_item actions = new Menu_two_item("Actions");
        Menu_two_item send = new Menu_two_item("Send");
        Menu_two_item stats = new Menu_two_item("Stats");
        Menu_two_item location = new Menu_two_item("Location");
        Menu_two_item pulseSend = new Menu_two_item("Pulse");
        Menu_two_item communication = new Menu_two_item("Communication");
        Menu_two_item wave = new Menu_two_item("Wave");
        Menu_two_item text = new Menu_two_item("Text");

        Menu_two_item settings = new Menu_two_item("Settings");
        Menu_two_item systems = new Menu_two_item("Systems");
        Menu_two_item bluetooth = new Menu_two_item("Bluetooth");
        Menu_two_item gps = new Menu_two_item("GPS");
        Menu_two_item modes = new Menu_two_item("Modes");
        Menu_two_item dnd = new Menu_two_item("Do not disturb");
        Menu_two_item zzz = new Menu_two_item("Sleep");

        Menu_two_item info = new Menu_two_item("Info");
        Menu_two_item calendar = new Menu_two_item("Calendar");
        Menu_two_item view = new Menu_two_item("View");
        Menu_two_item today = new Menu_two_item("Today");
        Menu_two_item tomorrow = new Menu_two_item("Tomorrow");
        Menu_two_item change = new Menu_two_item("Change");
        Menu_two_item addEvent = new Menu_two_item("Add Event");
        Menu_two_item cancelEvent = new Menu_two_item("Cancel Event");

        Menu_two_item health = new Menu_two_item("Health");
        Menu_two_item cardiac = new Menu_two_item("Cardiac");
        Menu_two_item pulseHealth = new Menu_two_item("Pulse");
        Menu_two_item o2 = new Menu_two_item("O2");
        Menu_two_item energy = new Menu_two_item("Energy");
        Menu_two_item calories = new Menu_two_item("Calories");
        Menu_two_item steps = new Menu_two_item("Steps");

        menu2.up = actions;
        menu2.down = info;

        actions.up = send;
        actions.down = settings;

        send.up = stats;
        send.down = communication;
        settings.up = systems;
        settings.down = modes;
        stats.up = location;
        stats.down = pulseSend;
        communication.up = wave;
        communication.down = text;

        systems.up = bluetooth;
        systems.down = gps;
        modes.up = dnd;
        modes.down = zzz;

        info.up = calendar;
        info.down = health;
        calendar.up = view;
        calendar.down = change;
        health.up = cardiac;
        health.down = energy;

        view.up = today;
        view.down = tomorrow;
        change.up = addEvent;
        change.down = cancelEvent;
        cardiac.up = pulseHealth;
        cardiac.down = o2;
        energy.up = calories;
        energy.down = steps;


        //create routes
        options[0] = new String[] {"Actions", "Send", "Stats", "Pulse"};
        options[1] = new String[] {"Actions", "Send", "Communication", "Text"};
        options[2] = new String[] {"Actions", "Settings", "Modes", "Do not disturb"};
        options[3] = new String[] {"Actions", "Settings", "Modes", "Sleep"};
        options[4] = new String[] {"Info", "Calendar", "View", "Today"};
        options[5] = new String[] {"Info", "Calendar", "View", "Tomorrow"};
        options[6] = new String[] {"Info", "Health", "Cardiac", "Pulse"};
        options[7] = new String[] {"Info", "Health", "Energy", "Calories"};

        //randomize routes
        Random rnd = ThreadLocalRandom.current();
        for (int i = options.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            String temp[] = options[index];
            options[index] = options[i];
            options[i] = temp;
        }

        //init menu
        up.setText(menu2.up.item_name);
        down.setText(menu2.down.item_name);

        next = menu2;

        route.setText(options[round][0]+">>"+options[round][1]+" >> "+options[round][2]+">>"+options[round][3]);
        startTime = System.currentTimeMillis();
    }

    void recordAndLoadNext(Boolean success) {
        //next round
        up.setText(menu2.up.item_name);
        down.setText(menu2.down.item_name);

        //record time
        elapsedTime = System.currentTimeMillis() - startTime;
        startTime = System.currentTimeMillis();

        String dataToWrite = participant_id+","+trial_id+","+"Two-Zone"+","+
                options[round][0]+" >> "+options[round][1]+" >> "+options[round][2]+" >> "+options[round][3]+","+
                success+","+(elapsedTime/1000.00)+"sec"+"\n";
        if (writer.isExternalStorageWritable()) writer.writeToFile(dataToWrite, getString(R.string.user_data));
        else System.out.println("AARGHH");
        round++;
        level = 0;

        next = menu2;

        if (round==8) {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }
    }
}
