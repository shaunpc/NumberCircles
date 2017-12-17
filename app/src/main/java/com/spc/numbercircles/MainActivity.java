package com.spc.numbercircles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "NumberCircles";
    EditText etNumber;  // The number entered
    TextView tvResult;  // the result view
    int number_to_find, tier, side_length, BR, BL, TL, TR, BR_fake, x_offset, y_offset, steps;
    String results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find the relevant screen components
        etNumber = findViewById(R.id.number);
        tvResult = findViewById(R.id.result);


        etNumber.setText("");
        tvResult.setText(R.string.results);

        etNumber.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN
                        && keyCode == KeyEvent.KEYCODE_ENTER)) {
                    numberCircle();
                    return true;
                }
                return false;
            }

            private void numberCircle() {
                number_to_find = Integer.parseInt(etNumber.getText().toString());
                Log.d(TAG,"number to find is "+number_to_find );

                tier = -1;  /* ensure first iteration starts at zero */
                do {
                    tier = tier + 1;
                    side_length = (tier*2)+1;
                    BR = side_length*side_length;   /* x^y does not work! */
                    Log.d(TAG,"tier="+tier + "/side="+side_length+"/BR="+BR );
                } while (BR < number_to_find);

                /* calc numbers in the other corners */
                BL = BR - side_length + 1 ;
                TL = BL - side_length + 1 ;
                TR = TL - side_length + 1 ;
                BR_fake = TR - side_length + 1 ;  // bit special

                /* the number to find is on this tier, but which side */
                if (number_to_find>BL) {
                    /* on bottom - numbers increasing to right */
                    y_offset = -tier;
                    x_offset = number_to_find - ((BL+BR)/2);
                } else {
                    if (number_to_find > TL) {
                        /* on left - numbers increasing downwards */
                        y_offset = ((TL+BL)/2)- number_to_find ;
                        x_offset = -tier;
                    } else {
                        if (number_to_find > TR) {
                            /* on top - numbers decreasing to right */
                            y_offset = tier;
                            x_offset = ((TL+TR)/2) - number_to_find;
                        } else {
                            /* on right - numbers decreasing downwards AND a bit special*/
                            y_offset = number_to_find - ((TR+BR_fake)/2);
                            x_offset = tier;
                        }
                    }
                }
                steps = Math.abs(x_offset) + Math.abs(y_offset);
                results = "Found in number circle " + tier + "\n";
                results = results + "Side length is " + side_length + "\n";
                results = results + "BR is #" + BR + " / BL is #" + BL + "\n";
                results = results + "TL is #" + TL + " / TR is #" + TR + "\n";
                results = results + "X offset=" + x_offset + " / Y offset=" + y_offset + "\n";
                results = results + "Steps to center = "+ steps + "\n";
                Log.d(TAG, results);
                tvResult.setText(results);
            }
        });
    }

    // Options menu handling  - inflate the menu
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    // Options menu handling - set handler if menu item selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_opt1:
                startActivity(new Intent(this, HelpActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
