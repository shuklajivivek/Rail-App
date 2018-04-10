package com.example.android.railapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;




import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import android.app.ProgressDialog;

import android.widget.Toast;

public class pnrStatus extends AppCompatActivity {

    private TextView mTextViewResult;
    private RequestQueue mQueue;
    String pnr;
    EditText editText;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pnr_status);
        pd = new ProgressDialog(pnrStatus.this);
        pd.setMessage("Loading . . ."); // Loading symbol when the user presses the button


        mTextViewResult = findViewById(R.id.text_view_result);
        Button buttonparse = findViewById(R.id.button_parse);
        editText=findViewById(R.id.editText2);

        mQueue = Volley.newRequestQueue(this);

        //event listener
        buttonparse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.length()<10){
                    Toast.makeText(getApplicationContext(), " PNR No. Incorrect. ", Toast.LENGTH_SHORT).show();
                }
                else {
                    mTextViewResult.setText(null);
                    pnr = editText.getText().toString();
                    jsonParse();
                }

            }
        });
    }

    private void jsonParse() {
        Log.d("FIRE","Service Called");
        String url = "https://api.railwayapi.com/v2/pnr-status/pnr/"+pnr+"/apikey/sisd0pcu35/"; //Calls railway api and extracts the json response and displays the specified information
        pd.show();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("FIRE","response success");

        /*get train number, name, boarding point*/
                            JSONObject train = response.getJSONObject("train");
                            int no = train.getInt("number");
                            String name = train.getString("name");


                            JSONObject boarding = response.getJSONObject("boarding_point");
                            String b_p = boarding.getString("name");

                            mTextViewResult.append("Train Number: "+String.valueOf(no)+"\nTrain Name: "+name+"\nBoarding Point: "+b_p+"\n");

                  /*iterate through all the passengers contained in the booked ticket and get the current status and booking status */
                            JSONArray jsonArray = response.getJSONArray("passengers");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject pas = jsonArray.getJSONObject(i);

                                String c_s = pas.getString("current_status").toString();
                                String b_s = pas.getString("booking_status").toString();

                                mTextViewResult.append("Booking status :" + b_s + "\nCurrent status: " + c_s + " \n");
                                pd.hide();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("FIRE","Error");
                            pd.hide();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.d("FIRE","Error");
            }
        });

        mQueue.add(request);
    }
}
