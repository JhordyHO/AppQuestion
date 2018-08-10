package com.codsit.appquestion;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.codsit.appquestion.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // UI references.
    private Spinner mSpinerDoc;
    private EditText mNdoc;

    RequestQueue requestQueue;
    private String BaseUrl ="http://softjhor.com/";
    private int t_doc;
    private String n_doc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.doc_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinerDoc.setAdapter(adapter);
        mSpinerDoc.setOnItemSelectedListener(this);
    }

    public void init(){
        mSpinerDoc =  findViewById(R.id.doc_spinner);
        mNdoc = findViewById(R.id.n_doc);
        requestQueue = Volley.newRequestQueue(this);
    }

    public void authUser(View view){
        n_doc = mNdoc.getText().toString();
        JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST,BaseUrl+"auth?t_doc="+t_doc+"&n_doc="+n_doc,
                (JSONObject) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseJSONResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        jsonObject.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

        requestQueue.add(jsonObject);
    }

    private ArrayList<User> parseJSONResponse(JSONObject response){
        ArrayList<User> lispost = new ArrayList<>();
        if (response != null && response.length()>0){
            try {
                  int state =  response.getInt("state");
                  if(state == 1){
                      String nombre =  response.getString("nombre");
                      String a_p =  response.getString("apellido_p");
                      String a_m =  response.getString("apellido_m");
                      String n_doc =  response.getString("n_doc");

                      Toast.makeText(this, "Bienvenido "+nombre+" "+a_p,
                              Toast.LENGTH_LONG).show();
                      Intent sl = new Intent(this,SelectActivity.class);
                      startActivity(sl);
                      finish();
                  }else{
                      Toast.makeText(this, "Usuario No Existe !",
                              Toast.LENGTH_SHORT).show();
                  }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return lispost;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        t_doc = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

