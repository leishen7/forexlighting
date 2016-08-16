package com.nucome.app.forex;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class ContactUsActivity extends UserInfoActivity {
    private String TAG = ContactUsActivity.class.getSimpleName();
    private String URL_INFO = "http://www.wuzhenweb.com:8089/json?operation=newcomment";


    private Spinner questionCategory;
    private EditText question;
    private Button questionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        questionCategory = (Spinner) findViewById(R.id.questioncategorySpin);
        question = (EditText) findViewById(R.id.questionEditText);

        questionCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
             //   Toast.makeText(getApplicationContext(), "selected",Toast.LENGTH_LONG).show();
            }



            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });


      question.setSelected(true);
        questionButton = (Button) findViewById(R.id.question_Button);
        questionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(question==null || question.length()<5){
                    Toast.makeText(getApplicationContext(), "你的问题太短",Toast.LENGTH_LONG).show();
                    return;
                }


                String url = URL_INFO;
                StringRequest req = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        if (response.length() > 0) {
                            try {
                                JSONObject recommendObj = new JSONObject(response.toString());
                                if (recommendObj.getString("errorCode")==null || "null".equals(recommendObj.getString("errorCode"))) {

                                    Toast.makeText(getApplicationContext(), "我们已经收到你的问题.", Toast.LENGTH_LONG).show();
                                    Toast.makeText(getApplicationContext(), "我们会尽快答复.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                } else{
                                    Toast.makeText(getApplicationContext(), "Failed to submit comment",Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                Log.e(TAG, "JSON Parsing error: " + e.getMessage());
                            }
                            return;

                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(error.getMessage()!=null)
                        Log.e("Error Message", error.getMessage());
                    }
                }) {

                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String,String> params = new HashMap<String, String>();
                       // params.put("Content-Type","application/x-www-form-urlencoded");
                        params.put("Content-type", "application/json; charset=utf-8");
                        return params;
                    }
                    @Override
                    public byte[] getBody()
                    {

                        JSONObject jsonObject = new JSONObject();
                        String body = null;
                        try
                        {
                            jsonObject.put("topic", questionCategory.getSelectedItem().toString());
                            jsonObject.put("comment", question.getText());
                            SharedPreferences pref = getApplicationContext().getSharedPreferences(getString(R.string.PREF_USER_TOKEN), Context.MODE_PRIVATE);
                            jsonObject.put(getString(R.string.JSON_TOKEN), pref.getString(getString(R.string.PREF_USER_TOKEN), null));

                            body = jsonObject.toString();
                        } catch (JSONException e)
                        {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        try
                        {
                            return body.toString().getBytes("utf-8");
                        } catch (UnsupportedEncodingException e)
                        {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        return null;
                    }
                };
                TradeApplication.getInstance().addToRequestQuest(req);
            }
        });
    }
}
