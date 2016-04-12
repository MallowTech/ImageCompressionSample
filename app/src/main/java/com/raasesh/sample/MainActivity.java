package com.raasesh.sample;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

public class MainActivity extends Activity {
    String fname;
    File file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.png);
        saveImage(bitmap);
        send();

    }
    public void saveImage(Bitmap bitmap){
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/My Folder");
        String receiverN = "raasesh";

        myDir.mkdirs();
        //name convention--------------------------------------------->>
        Calendar c = Calendar.getInstance();
        String month, day, year, hour, minute, second;
        month = ""+ (c.get(Calendar.MONTH)+1);
        day = "" + c.get(Calendar.DAY_OF_MONTH);
        year = "" + c.get(Calendar.YEAR);
        hour = ""+c.get(Calendar.HOUR_OF_DAY);
        if(hour.equals("0")) hour = "0"+hour;
        minute = "" + c.get(Calendar.MINUTE);
        int seconds = c.get(Calendar.SECOND);
        if (seconds<10) second = "0"+ seconds;
        else second = ""+seconds;

        fname = receiverN + "-" + hour + ":" + minute + ":" + second + "_"  + month + "-" + day + "-" + year +".png";
        //name convention ends----------------------------------------->>

       file = new File (myDir, fname);


        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 10, out);
            out.flush();
            out.close();
            //a
            //String[] paths = {file.toString()};
            //String[] mimeTypes = {"/image/png"};
            //MediaScannerConnection.scanFile(this, paths, mimeTypes, null);
            Toast.makeText(this, "Image Saved", Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void send()
    {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, "donraasesh@gmail.com");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "message");
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "subject");
        emailIntent.setType("image/png");
        Uri uri = Uri.parse("file:/storage/emulated/0/My Folder/"+fname);
        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(emailIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
