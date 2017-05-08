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

import com.example.bhaveekdesai.tiltmenu.datastructures.Menu_four_item;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class FourZoneActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor acc;
    private float x, y, z;
    private String optionSelected[];
    private float initX, initY, diff;
    private Vibrator vib;

    ImageView ball, rect_menu;
    TextView route, left, right, up, down;

    Menu_four_item menu4;

    String options[][] = new String[8][2];
    long startTime, elapsedTime;
    String participant_id, trial_id;
    DataIO writer;

    int color, level, round;
    Bitmap bmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourzone);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        vib = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);

        ball = (ImageView) findViewById(R.id.ball);
        rect_menu = (ImageView) findViewById(R.id.rect_menu);
        route = (TextView) findViewById(R.id.pixel);

        left = (TextView) findViewById(R.id.left);
        right = (TextView) findViewById(R.id.right);
        up = (TextView) findViewById(R.id.up);
        down = (TextView) findViewById(R.id.down);
        level = 0;
        round = 0;

        participant_id = getIntent().getStringExtra("participant_id");
        trial_id = getIntent().getStringExtra("trial_id");

        writer = new DataIO();

        optionSelected = new String[2];
        optionSelected[0] = "";

        initX = 423;//500;
        initY = 423;//722;

        initialize();

        //d = rect_menu.getDrawable();
        //bitmap = ((BitmapDrawable) d).getBitmap();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        acc = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, acc, SensorManager.SENSOR_DELAY_NORMAL);

        bmap = ((BitmapDrawable)rect_menu.getDrawable()).getBitmap();
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
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
                color = bmap.getPixel((int) ball.getX(), (int) ball.getY());
            else color = 0;

            diff = Math.abs(initX - ball.getX()) - Math.abs(initY - ball.getY());

            //detect selection
        if (ball.getY()<=115 && color == -9265201) {optionSelected[0]="up"; optionSelected[1]=up.getText().toString();} //UP round
        else if (ball.getY()>=770 && color == -9265201) {optionSelected[0]="down"; optionSelected[1]=down.getText().toString();} //DOWN round
        else if (ball.getX()<=115 && color == -9265201) {optionSelected[0]="left"; optionSelected[1]=left.getText().toString();} //LEFT round
        else if (ball.getX()>=770 && color == -9265201) {optionSelected[0]="right"; optionSelected[1]=right.getText().toString();} //RIGHT round

            /* if (ball.getY() < initY && color == -9265201) {
                optionSelected[0] = "up";
                optionSelected[1] = up.getText().toString();
            } //UP round
            else if (ball.getY() > initY && diff < 0 && color == -9265201) {
                optionSelected[0] = "down";
                optionSelected[1] = down.getText().toString();
            } //DOWN round
            else if (ball.getX() < initX && diff > 0 && color == -9265201) {
                optionSelected[0] = "left";
                optionSelected[1] = left.getText().toString();
            } //LEFT round
            else if (ball.getX() > initX && color == -9265201) {
                optionSelected[0] = "right";
                optionSelected[1] = right.getText().toString();
            } //RIGHT round
*/
            if (optionSelected[0] != "") {

                if (optionSelected[1] == options[round][level]) {
                    if (level == 1) {
                        vib.vibrate(50);
                        vib.vibrate(50);
                        recordAndLoadNext(true);
                    } else {
                        //next level
                        level++;
                        Menu_four_item next = menu4;
                        switch (optionSelected[0]) {
                            case "left":
                                next = next.left;
                                break;

                            case "right":
                                next = next.right;
                                break;

                            case "up":
                                next = next.up;
                                break;

                            case "down":
                                next = next.down;
                                break;
                        }

                        left.setText(next.left.item_name);
                        right.setText(next.right.item_name);
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
                color = 0;

                optionSelected[0] = "";
                optionSelected[1] = "";
                if (round <= 7) route.setText(options[round][0] + ">>" + options[round][1]);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    void initialize() {

        //create menu tree
        menu4 = new Menu_four_item("root");
        Menu_four_item send = new Menu_four_item("Send");
        Menu_four_item location = new Menu_four_item("Location");
        Menu_four_item pulseSend = new Menu_four_item("Pulse");
        Menu_four_item wave = new Menu_four_item("Wave");
        Menu_four_item text = new Menu_four_item("Text");

        Menu_four_item settings = new Menu_four_item("Settings");
        Menu_four_item bluetooth = new Menu_four_item("Bluetooth");
        Menu_four_item gps = new Menu_four_item("GPS");
        Menu_four_item dnd = new Menu_four_item("Do not disturb");
        Menu_four_item zzz = new Menu_four_item("Sleep");

        Menu_four_item calendar = new Menu_four_item("Calendar");
        Menu_four_item today = new Menu_four_item("Today");
        Menu_four_item tomorrow = new Menu_four_item("Tomorrow");
        Menu_four_item addEvent = new Menu_four_item("Add Event");
        Menu_four_item cancelEvent = new Menu_four_item("Cancel Event");

        Menu_four_item health = new Menu_four_item("Health");
        Menu_four_item pulseHealth = new Menu_four_item("Pulse");
        Menu_four_item o2 = new Menu_four_item("O2");
        Menu_four_item calories = new Menu_four_item("Calories");
        Menu_four_item steps = new Menu_four_item("Steps");

        menu4.left = send;
        menu4.right = settings;
        menu4.up = calendar;
        menu4.down = health;

        send.left = location;
        send.right = pulseSend;
        send.up = wave;
        send.down = text;

        settings.left = bluetooth;
        settings.right = gps;
        settings.up = dnd;
        settings.down = zzz;

        calendar.left = today;
        calendar.right = tomorrow;
        calendar.up = addEvent;
        calendar.down = cancelEvent;

        health.left = pulseHealth;
        health.right = o2;
        health.up = calories;
        health.down = steps;

        //create routes
        options[0] = new String[] {"Send", "Pulse"};
        options[1] = new String[] {"Send", "Text"};
        options[2] = new String[] {"Settings", "Do not disturb"};
        options[3] = new String[] {"Settings", "Sleep"};
        options[4] = new String[] {"Calendar", "Today"};
        options[5] = new String[] {"Calendar", "Tomorrow"};
        options[6] = new String[] {"Health", "Pulse"};
        options[7] = new String[] {"Health", "Calories"};

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
        left.setText(menu4.left.item_name);
        right.setText(menu4.right.item_name);
        up.setText(menu4.up.item_name);
        down.setText(menu4.down.item_name);

        route.setText(options[round][0]+">>"+options[round][1]);
        startTime = System.currentTimeMillis();
    }

    void recordAndLoadNext(Boolean success) {
        //next round
        left.setText(menu4.left.item_name);
        right.setText(menu4.right.item_name);
        up.setText(menu4.up.item_name);
        down.setText(menu4.down.item_name);

        //record time
        elapsedTime = System.currentTimeMillis() - startTime;
        startTime = System.currentTimeMillis();

        String dataToWrite = participant_id+","+trial_id+","+"Four-Zone"+","+
                options[round][0]+" >> "+options[round][1]+","+
                success+","+(elapsedTime/1000.00)+"sec"+"\n";
        if (writer.isExternalStorageWritable()) writer.writeToFile(dataToWrite, getString(R.string.user_data));
        else System.out.println("AARGHH");
        round++;
        level = 0;

        if (round==8) {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }
    }
}
