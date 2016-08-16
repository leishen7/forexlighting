package com.nucome.app.forex;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class ForgetPasswordActivity extends RegisterActivity {
    private String TAG = getClass().getSimpleName();
//    private String URL_INFO = "http://www.wuzhenweb.com:8089/json?operation=newtrade";
   private String URL_INFO="http://www.wuzhenweb.com:8089/json?operation=forgetpassword";
    private EditText emailView;

    private TextView messageView;
    private Button submitButton;
    private ProgressDialog progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        emailView = (EditText) findViewById(R.id.fgemailEditText);


        messageView = (TextView) findViewById(R.id.messageView);

        submitButton = (Button) findViewById(R.id.submitForgetPassword);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(emailView.getText().toString().indexOf("@")<1 ||emailView.getText().toString().indexOf("@")>emailView.getText().toString().lastIndexOf(".")){

                    messageView.setText("* Invalid Email Address");
                    emailView.setText("");
                    return;
                }

                progressBar = new ProgressDialog(ForgetPasswordActivity.this);

                progressBar.setIndeterminate(true);
                submitButton.setEnabled(false);
                progressBar.show();
                String url = URL_INFO;
                final StringRequest req = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.dismiss();
                        submitButton.setEnabled(true);
                        Log.d(TAG, response.toString());
                        if (response.length() > 0) {
                            try {
                                JSONObject registerObj = new JSONObject(response.toString());
                                if (registerObj.getString("errorCode")!="null" && !("".equals(registerObj.getString("errorCode")))) {


                                    messageView.setText("* "+registerObj.getString("errorDescription"));
                                    emailView.setText("");
                                   // Toast.makeText(getApplicationContext(), "User " + emailView.getText() + " failed to register "+registerObj.getString("errorCode"),Toast.LENGTH_LONG).show();

                                } else {
                                    JSONObject result = registerObj.getJSONObject("content");
                                     Toast.makeText(getApplicationContext(), "你的发密码已经发到你的邮箱",Toast.LENGTH_LONG).show();
                                    Toast.makeText(getApplicationContext(), "请登录",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(intent);
                                    return;
                                }

                            } catch (JSONException e) {
                                Log.e(TAG, "JSON Parsing error: " + e.getMessage());
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.dismiss();
                        submitButton.setEnabled(true);
                        Log.e("Error Message", error.getMessage());
                        Toast.makeText(ForgetPasswordActivity.this, "Register failed",Toast.LENGTH_LONG).show();
                    }
                }) {

                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String,String> params = new HashMap<String, String>();
                        params.put("Content-Type","application/x-www-form-urlencoded");
                        return params;
                    }
                    @Override
                    public byte[] getBody()
                    {

                        JSONObject jsonObject = new JSONObject();
                        String body = null;
                        try
                        {
                            jsonObject.put("userId", emailView.getText());
                            jsonObject.put("email", emailView.getText());
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
