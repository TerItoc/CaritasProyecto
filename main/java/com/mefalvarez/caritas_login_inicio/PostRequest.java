package com.mefalvarez.caritas_login_inicio;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PostRequest extends AppCompatActivity {

    // creating variables for our edittext,
    // button, textview and progressbar.
    private EditText nameEdt, jobEdt;
    private Button postDataBtn;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initializing our views
        nameEdt = findViewById(R.id.ACTextView_Departamento);
        jobEdt = findViewById(R.id.editText_Necesidad);
        postDataBtn = findViewById(R.id.button_enviar);

        // adding on click listener to our button.
        postDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validating if the text field is empty or not.
                if (nameEdt.getText().toString().isEmpty() && jobEdt.getText().toString().isEmpty()) {
                    Toast.makeText(PostRequest.this, "Please enter both the values", Toast.LENGTH_SHORT).show();
                    return;
                }
                // calling a method to post the data and passing our name and job.
                postDataUsingVolley(nameEdt.getText().toString(), jobEdt.getText().toString(), dtf.format(now));
            }
        });
    }

    private void postDataUsingVolley(String name, String job, String date) {
        // url to post our data
        String url = "http://localhost:8000/visit/";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(PostRequest.this);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // inside on response method we are
                // hiding our progress bar
                // and setting data to edit text as empty
                nameEdt.setText("");
                jobEdt.setText("");

                // on below line we are displaying a success toast message.
                Toast.makeText(PostRequest.this, "Data added to API", Toast.LENGTH_SHORT).show();
                try {
                    // on below line we are passing our response
                    // to json object to extract data from it.
                    JSONObject respObj = new JSONObject(response);

                    // below are the strings which we
                    // extract from our json object.
                    String name = respObj.getString("departamento");
                    String job = respObj.getString("motivo");
                    String date = respObj.getString("yyyy/MM/dd HH:mm:ss");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(PostRequest.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key
                // and value pair to our parameters.
                params.put("name", name);
                params.put("job", job);
                params.put("date", date);

                // at last we are
                // returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }
}