package project.coict.BuyandSell;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import project.coict.buyandsell.R;

public class Login extends AppCompatActivity {

    EditText user,pass;
    Button btnlogin;
    String username;
    String password;
    String TAG_SUCCESS="success";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user=(EditText)findViewById(R.id.txtusername);
        pass=(EditText)findViewById(R.id.txtpassword);
        btnlogin=(Button)findViewById(R.id.btnlogin);

         username=user.getText().toString().trim();
         password=pass.getText().toString().trim();

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Call Login method
                //you can use if statement to check if fields are empty before calling Login method
                Login(username,password);
            }
        });





    }

    public void Login(final String user, final String pass){

        RequestQueue mRequestQueue;

// Instantiate the cache
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

// Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

// Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);

// Start the queue
        mRequestQueue.start();

        //change this IP 192.168.56.1 to your IP
        String url ="http://192.168.56.1/BuyandSell/login.php";

// Formulate the request and handle the response.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Do something with the response
                       // Toast.makeText(getApplicationContext(),"Response"+response,Toast.LENGTH_SHORT).show();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Integer success=jsonObject.getInt(TAG_SUCCESS);
                            if(success==1){
                                 //Call MainActivity if successful login
                                Intent intent=new Intent(Login.this,MainActivity.class);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("Error",e.toString());
                        }



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        Toast.makeText(getApplicationContext(),"Error"+error,Toast.LENGTH_SHORT).show();
                    }
                }){

            @Override
        protected Map<String ,String> getParams() throws AuthFailureError {

            Map<String,String> params=new HashMap<String,String>();
            params.put("username",user);
            params.put("password",pass);
            return params;
        }};

       // stringRequest.setShouldCache(false); //Use this line if dont want to save in cache
// Add the request to the RequestQueue.
        mRequestQueue.add(stringRequest);

    }
}
