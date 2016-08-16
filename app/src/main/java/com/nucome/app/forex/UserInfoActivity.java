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
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class UserInfoActivity extends AppCompatActivity {
    private EditText userIdVew;
    private EditText emailView;
    private EditText phoneView;
    private EditText firstNameView;
    private EditText lastNameView;
    private EditText nickNameView;
 //   private ProgressDialog progressBar;
    private Button finishButton;
    private String TAG = UserInfoActivity.this.getClass().getSimpleName();
    private String URL_USERINFO_INFO = "http://www.wuzhenweb.com:8089/json?operation=getuserdata";
    private String URL_UPDATE_USERINFO="http://www.wuzhenweb.com:8089/json?operation=updateuser";
    private String userToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences pref = getApplicationContext().getSharedPreferences(getString(R.string.PREF_USER_TOKEN), Context.MODE_PRIVATE);
        userToken=pref.getString(getString(R.string.PREF_USER_TOKEN), null);

        setContentView(R.layout.activity_user_info);
        userIdVew = (EditText) findViewById(R.id.userInfoUserIdEditText);
        emailView = (EditText) findViewById(R.id.userInfoEmailEditText);
        phoneView = (EditText) findViewById(R.id.userInfoPhoneEditText);
        firstNameView = (EditText) findViewById(R.id.userInfoFirstNameEditText);
        lastNameView = (EditText) findViewById(R.id.userInfoLastNameEditText);
        nickNameView = (EditText) findViewById(R.id.userInfoNickNameEditText);
        finishButton = (Button) findViewById(R.id.userInfoFinish);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = URL_UPDATE_USERINFO;
                StringRequest req = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        if (response.length() > 0) {
                            try {
                                JSONObject updateObj = new JSONObject(response.toString());
                                if (updateObj.getString("errorCode") != "null" && !("".equals(updateObj.getString("errorCode")))) {
                                    Toast.makeText(getApplicationContext(), "The update failed to be saved",Toast.LENGTH_LONG).show();

                                } else {
                                    Toast.makeText(getApplicationContext(), "The update has been saved successfully.", Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                Log.e(TAG, "JSON Parsing error: " + e.getMessage());
                            }

                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error Message", error.getMessage());
                    }
                }) {

                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String,String> params = new HashMap<String, String>();
                        //params.put("Content-Type","application/x-www-form-urlencoded");
                        //params.put("Content-Type","application/json; charset=ISO-8859-1");
                        params.put("Content-type", "application/json; charset=utf-8");
                        return params;
                    }
                    @Override
                    public byte[] getBody() {

                        JSONObject jsonObject = new JSONObject();
                        String body = null;
                        try {
                            jsonObject.put("userId", userIdVew.getText());
                            jsonObject.put("email", emailView.getText());
                            jsonObject.put("phone", phoneView.getText());
                            jsonObject.put("firstname", firstNameView.getText());
                            jsonObject.put("lastname", lastNameView.getText());
                           String  nameStr=new String(nickNameView.getText().toString().getBytes(), "UTF-8");
                            jsonObject.put("nickname", nameStr);
                            //SharedPreferences pref = getApplicationContext().getSharedPreferences(getString(R.string.PREF_USER_TOKEN), Context.MODE_PRIVATE);
                            jsonObject.put(getString(R.string.JSON_TOKEN), userToken);

                            body = jsonObject.toString();


                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException uee) {
                            // handle this
                        }

                        try
                        {
                            return body.toString().getBytes("utf-8");
                        } catch (UnsupportedEncodingException ue)
                        {
                            System.out.println(ue);
                            //e.printStackTrace();
                        }catch(Throwable e){
                            System.out.println(e);
                        }finally {
                            System.out.println("uuu");
                        }
                        return body.getBytes();
                    }
                };
                TradeApplication.getInstance().addToRequestQuest(req);
            }
        });
        if(userToken==null||userToken.length()<12){
            gotoRegister();
        }else{
            loadInfo();

        }
    }


    private void gotoRegister(){
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
        return;
    }

    private void loadInfo() {
        {
            String url = URL_USERINFO_INFO;
            StringRequest req = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(TAG, response.toString());
                    if (response.length() > 0) {
                        try {
                            JSONObject responseObj = new JSONObject(response.toString());
                            if(responseObj==null){
                                gotoRegister();
                            }
                            JSONObject userInfoObj = responseObj.getJSONObject("content");
                            if(userInfoObj==null){
                                gotoRegister();
                            }
                            userIdVew.setText(userInfoObj.getString("userId"));
                            emailView.setText(userInfoObj.getString("email"));
                            phoneView.setText(userInfoObj.getString("phone"));
                            firstNameView.setText(userInfoObj.getString("firstname"));
                            lastNameView.setText(userInfoObj.getString("lastname"));
                            nickNameView.setText(userInfoObj.getString("nickName"));
                           // progressBar.dismiss();
                        } catch (JSONException e) {
                            Log.e(TAG, "JSON Parsing error: " + e.getMessage());
                        }

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Error Message", error.getMessage());
                   // progressBar.dismiss();
                    Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
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
                     //   SharedPreferences pref = getApplicationContext().getSharedPreferences(getString(R.string.PREF_USER_TOKEN), Context.MODE_PRIVATE);
                      //  jsonObject.put(getString(R.string.JSON_TOKEN), pref.getString(getString(R.string.PREF_USER_TOKEN), null));
                        jsonObject.put(getString(R.string.JSON_TOKEN),userToken);
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
                    return body.getBytes();
                }
            };
            TradeApplication.getInstance().addToRequestQuest(req);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.profile:
                Intent calendarIntent = new Intent(getApplicationContext(), UserInfoActivity.class);
                startActivity(calendarIntent);
                return  true;
            case R.id.contactus:
                Intent aboutusIntent = new Intent(getApplicationContext(), ContactUsActivity.class);
                startActivity(aboutusIntent);
                return  true;
            case R.id.logout:
                SharedPreferences pref = getApplicationContext().getSharedPreferences(getString(R.string.PREF_USER_TOKEN), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString(getString(R.string.PREF_USER_TOKEN), null);
                editor.commit();
                Intent intentLogout = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intentLogout);
                return  true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
