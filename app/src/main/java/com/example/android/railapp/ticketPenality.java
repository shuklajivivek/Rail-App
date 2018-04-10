package com.example.android.railapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class ticketPenality extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_penality);
    }

    public void onRadioButtonClicked(View view) {
        TextView textviews = findViewById(R.id.textView);
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        // below code is self-explanatory
        switch(view.getId()) {
            case R.id.donthave:
                if (checked)
                    textviews.setText("You are liable to be imposed full penalty from the source station to the destination station of the train. We suggest you to leave the train as soon as possible.");
                break;
            case R.id.haveavailable:
                if (checked)
                    textviews.setText("You are liable to be imposed a penalty equal to the difference of ticket fare of class you are travelling to the fare of your general ticket.");
                break;
            case R.id.havenotavailable:
                if (checked)
                    textviews.setText("You are liable to be imposed a penalty equal to the difference of ticket fare of class you are travelling to the fare of your general ticket plus Rs. 250/- fine.");
                break;
        }
    }
}