package com.example.bhaveekdesai.tiltmenu;

import android.content.Context;
import android.os.Environment;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class DataIO {
    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public void writeToFile(String dataToWrite, String table) {
        // Get the public documents directory.
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS+"/TiltMenu");
        if (!path.exists()) {
            if(path.mkdir()){
                System.out.printf("");
            } else {
                System.out.printf("");
            }
        }

        File file = new File(path, table);
        if(file.exists())
        {
            try
            {
                FileOutputStream fOut = new FileOutputStream(file, true);
                OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                myOutWriter.append(dataToWrite);
                myOutWriter.close();
                fOut.close();
            } catch(Exception e){}
        }
        else
        {
            try {
                file.createNewFile();
                FileOutputStream fOut = new FileOutputStream(file);
                OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);

                if(table.equals("user_data.csv")) myOutWriter.append("PARTICIPANT_ID, TRIAL#, MODEL_VARIANT, MENU_OPTION, SUCCESS, TIME\n");
                else if(table.equals("survey_data.csv")) myOutWriter.append("PARTICIPANT_ID, FOUR_ZONE_RATING, TWO_ZONE_RATING, PREFERENCE\n");

                myOutWriter.append(dataToWrite);
                myOutWriter.close();
                fOut.close();

            } catch(Exception e){
                System.out.println(e.toString());
            }
        }
    }

}
