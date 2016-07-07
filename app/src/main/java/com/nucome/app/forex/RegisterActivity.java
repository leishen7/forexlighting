package com.nucome.app.forex;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class RegisterActivity extends BaseActivity {
    private String TAG = getClass().getSimpleName();
    private String URL_REGISTER_INFO="http://www.wuzhenweb.com:8089/json?operation=registeruser";
    private EditText userIdView;
    private EditText passwordView;
    private EditText firstNameView;
    private EditText lastNameView;
    private EditText emailView;
    private EditText phoneView;
    private Button registerButton;
    private ProgressDialog progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userIdView = (EditText) findViewById(R.id.userIdEditText);
        passwordView = (EditText) findViewById(R.id.passwordEditText);
        firstNameView = (EditText) findViewById(R.id.firstNameEditText);
        lastNameView = (EditText) findViewById(R.id.lastNameEditText);
        emailView = (EditText) findViewById(R.id.emailEditText);
        phoneView = (EditText) findViewById(R.id.phoneEditText);
        registerButton = (Button) findViewById(R.id.submitRegister);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar = new ProgressDialog(RegisterActivity.this);
                progressBar.setMessage("Registering");
                progressBar.setIndeterminate(true);
                registerButton.setEnabled(false);
                progressBar.show();
                String url = URL_REGISTER_INFO;
                final StringRequest req = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.dismiss();
                        registerButton.setEnabled(true);
                        Log.d(TAG, response.toString());
                        if (response.length() > 0) {
                            try {
                                JSONObject registerObj = new JSONObject(response.toString());
                                if ("103".equals(registerObj.getString("errorCode"))) {
                                    Toast.makeText(getApplicationContext(), "User " + userIdView.getText() + " already exists, please select a different one.",Toast.LENGTH_LONG).show();

                                } else {
                                    JSONObject result = registerObj.getJSONObject("content");
                                    String token = result.getString("token");
                                    String userId = result.getString("userId");
                                    SharedPreferences pref = getApplicationContext().getSharedPreferences(getString(R.string.PREF_USER_TOKEN), Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.putString(getString(R.string.PREF_USER_TOKEN), token);
                                    editor.commit();
                                    Toast.makeText(getApplicationContext(), "User " + userId + " register successful.",Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(), UserInfoActivity.class);
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
                        registerButton.setEnabled(true);
                        Log.e("Error Message", error.getMessage());
                        Toast.makeText(RegisterActivity.this, "Register failed",Toast.LENGTH_LONG).show();
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
                            jsonObject.put("userId", userIdView.getText());
                            jsonObject.put("password", passwordView.getText());
                            jsonObject.put("firstname", firstNameView.getText());
                            jsonObject.put("lastname", lastNameView.getText());
                            jsonObject.put("email", emailView.getText());
                            jsonObject.put("phone", phoneView.getText());

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.register:
                Intent registerIntent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(registerIntent);
                return  true;
            case R.id.login:
                Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(loginIntent);
                return  true;
            case R.id.profile:
                Intent calendarIntent = new Intent(getApplicationContext(), UserInfoActivity.class);
                startActivity(calendarIntent);
                return  true;
            case R.id.openAccount:
                Intent learningIntent = new Intent(getApplicationContext(), OpenAccountActivity.class);
                startActivity(learningIntent);
                return  true;
            case R.id.recommend:
                Intent intent = new Intent(getApplicationContext(), RecommendationActivity.class);
                startActivity(intent);
                return  true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
