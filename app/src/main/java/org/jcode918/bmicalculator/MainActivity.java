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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.jcode918.bmicalculator.model.BMIModel;

import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText heightEditText;
    EditText weightEditText;
    Button calculateBMIButton;
    TextView bmiResultsTextView;
    TextView bmiRiskTextView;
    Button educateButton;
    String URL = "";
    BMIModel bmi;

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

        calculateBMIButton.setOnClickListener(this);
        educateButton.setOnClickListener(this);
    }

    private void educateMe() {
        // TODO : Work on Webview Activity.
        // TODO : Create a Random Number generator to select a URL
        if (null != bmi) {
            Intent intent = new Intent(this, WebsiteActivity.class);
            Random rand = new Random();
            int index = rand.nextInt(bmi.linkCount() + 1);
            intent.putExtra("url", bmi.specificLink(index));
            startActivity(intent);
        } else {
            Toast.makeText(this, "Calculate Your BMI First...", Toast.LENGTH_SHORT).show();
        }

    }


    private void callBmiApi() {
        Gson gson = new Gson();

        Editable height = heightEditText.getText();
        Editable weight = weightEditText.getText();
        // Setting Decimal Format
        DecimalFormat dFormat = new DecimalFormat("#.00000");

        URL = "http://webstrar99.fulton.asu.edu/page3/Service1.svc/calculateBMI?height=" + height.toString() + "&weight=" + weight.toString();

        // Basic Validation of Inputs
        if (height.length() > 0 && weight.length() > 0) {
            // Validation of Height
            if (Integer.valueOf(height.toString()) <= 99 && Integer.valueOf(height.toString()) >= 21) {
                // Validation of Weight
                if (Integer.valueOf(weight.toString()) >= 5 && Integer.valueOf(weight.toString()) <= 1400) {
                    StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            bmi = gson.fromJson(response, BMIModel.class);
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
                } else {
                    Toast.makeText(this, "Enter a value within a normal range of human weight", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Enter a value within a normal range of human height", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please Enter Valid Inputs For\nWeight and Height", Toast.LENGTH_SHORT).show();
        }
    }

    public void setTextColor(TextView tView, double value) {
        if (value != 0 && value < 18) {
            tView.setTextColor(Color.BLUE);
        } else if (value >= 18 && value < 25) {
            tView.setTextColor(Color.GREEN);
        } else if (value >= 25 && value <= 30) {
            tView.setTextColor(Color.rgb(102, 0, 153));
        } else if (value > 30) {
            tView.setTextColor(Color.RED);
        } else {
            tView.setTextColor(Color.BLACK);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.CalculateBMIButton:
                callBmiApi();
                break;
            case R.id.EducateMeButton:
                educateMe();
                break;
            default:
                break;
        }
    }
}