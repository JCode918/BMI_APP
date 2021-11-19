package org.jcode918.bmicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.jcode918.bmicalculator.model.BMIModel;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    EditText heightEditText;
    EditText weightEditText;
    Button calculateBMIButton;
    TextView bmiResultsTextView;
    TextView bmiRiskTextView;
    Button educateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        heightEditText = findViewById(R.id.HeightEditText);
        weightEditText = findViewById(R.id.WeightEditText);
        bmiResultsTextView = findViewById(R.id.BmiResultTextView);
        bmiRiskTextView = findViewById(R.id.BmiMessageTextView);
        calculateBMIButton = findViewById(R.id.CalculateBMIButton);
        educateButton = findViewById(R.id.EducateMeButton);

        calculateBMIButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBmiApi();
            }
        });

        educateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                educateMe();
            }
        });
    }

    private void educateMe() {
        // TODO : Work on Webview Activity.
        // TODO : Create a Random Number generator to select a URL
        //Intent intent = new Intent(getApplicationContext(), WebV);
    }

    private void callBmiApi() {
        Gson gson = new Gson();

        Editable height = heightEditText.getText();
        Editable weight = weightEditText.getText();
        // Setting Decimal Format
        DecimalFormat dFormat = new DecimalFormat("#.00000");

        String URL = "http://webstrar99.fulton.asu.edu/page3/Service1.svc/calculateBMI?height=" + height.toString() + "&weight=" +weight.toString();

        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                BMIModel bmi = gson.fromJson(response, BMIModel.class);
                // Set Different UI Elements.
                bmiResultsTextView.setText(String.valueOf(dFormat.format(bmi.getBmi())));
                setTextColor(bmiResultsTextView, 0);

                bmiRiskTextView.setText(bmi.getRisk());
                setTextColor(bmiRiskTextView, bmi.getBmi());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
        queue.start();
    }

    public void setTextColor(TextView tView, double value)
    {
      if (value != 0 && value < 18)
      {
          tView.setTextColor(Color.BLUE);
      }
      else if (value >= 18 && value < 25)
      {
          tView.setTextColor(Color.GREEN);
      }
      else if (value >= 25 && value <= 30)
      {
          tView.setTextColor(Color.rgb(102,0,153));
      }
      else if (value > 30)
      {
          tView.setTextColor(Color.RED);
      }
      else
      {
          tView.setTextColor(Color.BLACK);
      }
    }
}