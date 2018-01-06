package com.example.raskzor.weatherapp;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        getData();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void getData() {
        request mycurl = new request();
        try {
            String responsenya = mycurl.curl("https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22Yogyakarta%2CID%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys");
            Log.d("reson",responsenya);
            JSONObject jsonObj = new JSONObject(responsenya);

            // city
            String kota = jsonObj.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("location").getString("city");
            TextView nmx = (TextView) findViewById(R.id.city);
            nmx.setText("Kota :" + kota);

            // Negara
            String country = jsonObj.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("location").getString("country");
            TextView cnx = (TextView) findViewById(R.id.country);
            cnx.setText("Negara :" + country);

            // Region
            String region = jsonObj.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("location").getString("region");
            TextView rex = (TextView) findViewById(R.id.region);
            rex.setText("Provinsi :" + region);

            // Humidity
            String humidity = jsonObj.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("atmosphere").getString("humidity");
            TextView hux = (TextView) findViewById(R.id.humidity);
            hux.setText("Kelembaban :" + humidity + " kPa");

            // Sunrise
            String rise = jsonObj.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("astronomy").getString("sunrise");
            TextView rix = (TextView) findViewById(R.id.sunrise);
            rix.setText("Terbit :" + rise);

            // Sunset
            String set = jsonObj.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("astronomy").getString("sunset");
            TextView sex = (TextView) findViewById(R.id.sunset);
            sex.setText("Terbenam :" + set);

            // Date
            String date = jsonObj.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("item").getJSONObject("condition").getString("date");
            TextView dax = (TextView) findViewById(R.id.date);
            dax.setText("Tanggal  :" + date);

            // suhu
            String suhu = jsonObj.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("item").getJSONObject("condition").getString("temp");
            TextView sux = (TextView) findViewById(R.id.suhu);
            sux.setText("Suhu :" + suhu + " Fahrenheit");

            // Awan
            String awan = jsonObj.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("item").getJSONObject("condition").getString("text");
            TextView awx = (TextView) findViewById(R.id.awan);
            awx.setText("Keadaan Awan :" + awan);




        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
