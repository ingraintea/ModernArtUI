package com.coursera.lab4.modernartui;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.net.Uri;

import static android.graphics.Color.parseColor;

// SeekBar doesn't work
public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private static String MOMA_URL = "http://www.moma.org/";

    private static String C_REG11 = "#6A77B7";
    private static String C_REG12 = "#D64F97";
    private static String C_REG21 = "#A31D21";
    private static String C_REG22 = "#E6E6E6";
    private static String C_REG23 = "#273A97";

    // Define elements within the page
    LinearLayout reg11 = null;
    LinearLayout reg12 = null;
    LinearLayout reg21 = null;
    LinearLayout reg22 = null;
    LinearLayout reg23 = null;

    TextView testText = null;

    SeekBar colorChangerBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // For testing purpose
        testText = (TextView) findViewById(R.id.testText);

        colorChangerBar = (SeekBar) findViewById(R.id.seekBar);
        colorChangerBar.setOnSeekBarChangeListener(this);


        reg11 = (LinearLayout) findViewById(R.id.reg11);
        reg12 = (LinearLayout) findViewById(R.id.reg12);
        reg21 = (LinearLayout) findViewById(R.id.reg21);
        reg22 = (LinearLayout) findViewById(R.id.reg22);
        reg23 = (LinearLayout) findViewById(R.id.reg23);

        // Set colors
        reg11.setBackgroundColor(parseColor(C_REG11));
        reg12.setBackgroundColor(parseColor(C_REG12));
        reg21.setBackgroundColor(parseColor(C_REG21));
        reg22.setBackgroundColor(parseColor(C_REG22));
        reg23.setBackgroundColor(parseColor(C_REG23));
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
        if (id == R.id.more_information_menu) {
            moreInformationDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Menu dialog for "More Information"
    private void moreInformationDialog() {

        new AlertDialog.Builder(this)
                .setTitle(R.string.dialog_title)
                .setMessage(R.string.dialog_message)
                .setPositiveButton(R.string.dialog_positive_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // For test
                        doVisitSite();
                    }
                })
                .setNegativeButton(R.string.dialog_negative_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Just do nothing
                    }
                }).show();
    }

    // Open web-site
    public void doVisitSite() {
        Intent visitSite = new Intent(Intent.ACTION_VIEW, Uri.parse(MOMA_URL));
        startActivity(visitSite);
    }

    // SeekBar section
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        colorChangerBar.setMax(300);

        testText.setText("The value is: " + progress + " - " + convertHsbToRgb(progress));
        testText.setTextColor(parseColor(convertHsbToRgb(progress)));

//        reg11.setBackgroundColor(parseColor("#00FF00"));
//        reg12.setBackgroundColor(parseColor("#00FF00"));
    }

    private void setProgressBasedBackgroundColor(LinearLayout reg, int progress) {

        int originalRegColor = reg.getSolidColor();

        float[] hsvColor = new float[3];
        Color.colorToHSV(originalRegColor, hsvColor);
        hsvColor[0] = hsvColor[0] + progress;
        hsvColor[0] = hsvColor[0] % 360;
        reg.setBackgroundColor(Color.HSVToColor(Color.alpha(originalRegColor), hsvColor));
    }


    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // Do nothing
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // Do nothing
    }


    public static String convertHsbToRgb(int color) {
        String red, green, blue;

        red = String.valueOf(Integer.toHexString(color % 255));
        green = String.valueOf(Integer.toHexString((color + 50) % 255));
        blue = String.valueOf(Integer.toHexString((color + 10) % 255));

        return "#" + formatHexToDigits(red) + formatHexToDigits(green) + formatHexToDigits(blue);
    }

    public static String formatHexToDigits(String hexVal) {
        if (hexVal.length() < 2) {
            hexVal = "0" + hexVal;
        }
        return hexVal;
    }


}
