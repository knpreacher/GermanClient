package com.dev.imr.knp.germanclient;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements ConnectFragment.OnFragmentInteractionListener, FileListFragment.OnFragmentInteractionListener, AttrsRecyclerAdapter.OnAttrChanged {
    Button button;
    String url = "http://172.24.77.17:8888";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl,ConnectFragment.newInstance()).commit();

    }

    @Override
    public void onSuccessGetAllRequest(String url, String sfl) {
        Toast.makeText(this, sfl, Toast.LENGTH_SHORT).show();
        getSupportFragmentManager().beginTransaction().replace(R.id.fl,FileListFragment.newInstance(sfl)).addToBackStack(null).commit();
    }

    @Override
    public void onErrorGetAllRequest(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFileClick(final String fn) {
        Toast.makeText(this, fn, Toast.LENGTH_SHORT).show();
        StringRequest request = new StringRequest(Request.Method.GET, url + "/ga/?filename="+fn, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle("Атрибуты");
                    RecyclerView rv = new RecyclerView(MainActivity.this);
                    rv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    rv.setAdapter(new AttrsRecyclerAdapter(fn, MainActivity.this, response, MainActivity.this));

                    //alertDialog.setMessage(response);
                    alertDialog.setView(rv);
                    alertDialog.show();
                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("filename",fn);
//                return params;
//            }

//            @Override
//            public byte[] getBody() throws AuthFailureError {
//                JSONObject jo = new JSONObject();
//                try {
//                    jo.put("filename",fn);
//                    jo.toString().getBytes();
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//
//                }
//                return new byte[]{};
//            }
        };
        App.addToQueue(request);
    }

    @Override
    public void onAttrChanged(String fn, String attr, boolean val) {
        //String ja = "{"+attr+":"+val+"}";
        StringRequest request = new StringRequest(Request.Method.GET, url + "/sa/?filename=" + fn + "&attr=" + attr + "&val="+ val, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        App.addToQueue(request);
    }
}
