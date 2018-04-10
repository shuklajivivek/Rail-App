package com.example.android.railapp;

        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.JsonObjectRequest;
        import com.android.volley.toolbox.Volley;

        import org.json.JSONException;
        import org.json.JSONObject;
        import android.app.ProgressDialog;

public class trainFare extends AppCompatActivity {

    private TextView mTextViewResult;
    private RequestQueue mQueue;
    String train_no,travel_date,from,to, preference,travel_quota,train_name,old;
    EditText from_stn,to_stn,date,pref,quota,train;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_fare);
        pd=new ProgressDialog(trainFare.this);
        pd.setMessage("Loading . . .");

        mTextViewResult = findViewById(R.id.textView);
        Button buttonparse = findViewById(R.id.button_parse);
        train = findViewById(R.id.train);
        from_stn = findViewById(R.id.from_stn);
        to_stn = findViewById(R.id.to_stn);
        date = findViewById(R.id.date);
        pref = findViewById(R.id.pref);
        quota = findViewById(R.id.quota);

        mQueue = Volley.newRequestQueue(this);

        buttonparse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // PERFORM VALIDATION UPON INCORRECT USER INPUT
                if(train.length()<5){
                    Toast.makeText(getApplicationContext(), " Train No. Incorrect. ", Toast.LENGTH_SHORT).show();
                }
                else if(from_stn.length()<2){
                    Toast.makeText(getApplicationContext(), " Source Incorrect. ", Toast.LENGTH_SHORT).show();
                }
                else if(to_stn.length()<2){
                    Toast.makeText(getApplicationContext(), " Destination Incorrect. ", Toast.LENGTH_SHORT).show();
                }
                else if(date.length()<10){
                    Toast.makeText(getApplicationContext(), " Date Incorrect. ", Toast.LENGTH_SHORT).show();
                }
                else if(pref.length()>2){
                    Toast.makeText(getApplicationContext(), " Preference Incorrect. ", Toast.LENGTH_SHORT).show();
                }
                else if(quota.length()>2){
                    Toast.makeText(getApplicationContext(), " Quota Incorrect. ", Toast.LENGTH_SHORT).show();
                }

                else {
                    train_no = train.getText().toString();
                    from = from_stn.getText().toString();
                    to = to_stn.getText().toString();
                    preference = pref.getText().toString();
                    travel_quota = quota.getText().toString();// GN -general quota,PWD -disabled,TQ -tatkal etc;
                    travel_date = date.getText().toString(); //dd-mm-yyyy.
                    jsonParse();
                }
            }
        });
    }
    private void jsonParse() {
        Log.d("FIRE","Service Called");
        String url = "https://api.railwayapi.com/v2/fare/train/"+train_no+"/source/"+from+"/dest/"+to+"/age/18/pref/"+preference+"/quota/"+travel_quota+"/date/"+travel_date+"/apikey/1x3xcqt9ec/";
pd.show();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("FIRE","response success");
                            JSONObject jsonObject = response.getJSONObject("train");
                            train_name = jsonObject.getString("name"); // get train name
                            String fare = response.getString("fare"); // get train fare

                            mTextViewResult.setText("Enquired Train:"+train_name+"\nFARE:"+fare);

                pd.hide();
                        }
                        //on response failure
                        catch (JSONException e) {
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