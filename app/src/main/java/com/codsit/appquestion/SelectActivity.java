package com.codsit.appquestion;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.codsit.appquestion.adapter.RVadapter;
import com.codsit.appquestion.model.Question;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class SelectActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner mDepSpine,mservSpine;
    private ArrayList<String> depart,service;
    private JSONArray result;
    private EditText mDateM;

    private RecyclerView mListViewR;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<Question> RsQuestion;

    private String BaseUrl ="http://softjhor.com/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        init();
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy/MM/dd" );
        mDateM.setText( sdf.format( new Date() ));
        getData(BaseUrl+"listDep",1);
    }
    public void init(){
       mDepSpine = findViewById(R.id.dep_spiner);
       mservSpine = findViewById(R.id.serv_spiner);
       mDateM = findViewById(R.id.date_n);
       depart = new ArrayList<String>();
       service = new ArrayList<String>();
       mDepSpine.setOnItemSelectedListener(this);
    }
    private void getData(String url, final int position){
        //Creating a string request
        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //Parsing the fetched Json String to JSON Object
                            switch (position){
                                case 1 :
                                    result = new JSONArray(response);
                                    getDepartamento(result); break;
                                case 2 :
                                    service.clear();
                                    getServicio( new JSONArray(response)); break;
                                case 3 :
                                    getQuestion(new JSONArray(response)); break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        //Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //Adding request to the queue
        requestQueue.add(stringRequest);
    }
    private void getDepartamento(JSONArray j){

        for(int i=0;i<j.length();i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);
                //depart.add(json);
                //Adding the name of the student to array list
                depart.add(json.getString("nombre"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        mDepSpine.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, depart));
    }
    private void getServicio(JSONArray j){

        for(int i=0;i<j.length();i++){
            try {
                JSONObject json = j.getJSONObject(i);
                service.add(json.getString("nombre"));
                service.add(json.getString("idServicio"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinner
        mservSpine.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, Collections.singletonList(service.get(0))));
    }

    private void getQuestion(JSONArray j){
        RsQuestion = new ArrayList<>();
        for(int i=0;i<j.length();i++){
            try {
                JSONObject json = j.getJSONObject(i);
                Question qs = new Question();
                qs.setQuestion(json.getString("pregunta"));
                RsQuestion.add(qs);
                //RsQuestion.add(json.getString("state"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        mAdapter = new RVadapter(RsQuestion);
        mListViewR.setAdapter(mAdapter);
    }



    private String getIdDep(int position){
        String id_dep = "";
        try {
            JSONObject json = result.getJSONObject(position);

            id_dep = json.getString("idDepartamento");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return id_dep;

    }


    public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
        String exist = getIdDep(pos);
        if(exist!=""){
            mservSpine.setAdapter(null);
            getData(BaseUrl+"getService/"+exist,2);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public void serviceSelect(View view){
        String id_serv = service.get(1);
        getData(BaseUrl+"Question/"+id_serv,3);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Servicio de "+service.get(0));

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.question_layout, null);

        mListViewR =  dialogView.findViewById(R.id.list_quest);
        mListViewR.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mListViewR.setLayoutManager(mLayoutManager);

        alert.setView(dialogView);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //Your action here
            }
        });

        alert.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });

        alert.show();

    }
}
