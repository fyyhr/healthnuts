package com.example.darkdark.app;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.*;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;


import org.w3c.dom.Text;

import java.io.Console;


public class MainActivity extends AppCompatActivity/*FragmentActivity*/ implements SecFragment.OnFragmentInteractionListener,
                                                                                SecondFragment.OnFragmentInteractionListener,
                                                                                ThirdFragment.OnFragmentInteractionListener {

//    private TextView textCount;
//    private TextView textTotal;
    public SensorManager mSensorManager;
    public Sensor mStepCounterSensor;
    public Sensor mStepDetectorSensor;
    private int steps = -1;
    private String PREV_S;

    // 10/29 --->
    boolean activityRunning;
    private Toolbar mToolbar; //v7 widget supports older devices
    private TabLayout mTabLayout;
    private ViewPager mPager;
    private MyPagerAdapter mAdapter;


    // <---- 10/29



    //11/7 ---->
    private SharedPreferences mPrefs;

    EditText mHeight;
    EditText mWeight;

    TextView mCount;
    TextView mTotal;

    public static final String DEFAULT="N/A";


    // <---- 11/7

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (savedInstanceState != null)
        {
            steps = savedInstanceState.getInt(PREV_S) - 1;
        }
        setContentView(R.layout.fragment_main);//changed from activity_main cause it was just calling fragment_main anyway

        mToolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(mToolbar);
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
        mTabLayout.setTabsFromPagerAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mPager);
        mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        //Shared preference stuff 11/7 ---->
        SharedPreferences mPrefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);


        // 11/7 <----

//        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
//        mStepCounterSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
//        mStepDetectorSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

        //fragment manager stuff to check if the fragment worked i guess,
        //this is unnecessary when implementing viewpager. since its taken care of.
//         SecFragment frag = new SecFragment();
//         FragmentManager manager= getSupportFragmentManager();
//         android.support.v4.app.FragmentTransaction transaction= manager.beginTransaction();
//         transaction.add(R.id.mainthing,frag,"MainFragment");
//         transaction.commit();

        // <---
        //the shit below needs to go below this fragment manager stuff FUCK, give me back all of my time T_T

//        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
//        mStepCounterSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
//        mStepDetectorSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
//       textCount = (TextView) findViewById(R.id.count);
//        textTotal = (TextView) findViewById(R.id.total);




    }

    public void save(View view){

        SharedPreferences sharedPreferences =getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("height",mHeight.getText().toString()); //stores height data with key "height"
        editor.putString("weight",mWeight.getText().toString()); //stores weight data with key "weight"

        editor.commit(); //commits the data

        Toast.makeText(this, "data was saved", Toast.LENGTH_LONG).show();


    }

    public void load(View view){
        SharedPreferences sharedPreferences =getSharedPreferences("MyData",Context.MODE_PRIVATE);
        String count = sharedPreferences.getString("height", DEFAULT); //initialized default height data with key "height"
        String total = sharedPreferences.getString("weight",DEFAULT); //initialized default weight data with key "weight"

        if (count.equals(DEFAULT)||total.equals(DEFAULT))
        {

            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();

        }

        //for testing
        else{
            Toast.makeText(this, "Data Loaded", Toast.LENGTH_SHORT).show();
            mCount.setText(count);
            mTotal.setText(total);

        }

    }

    @Override
    protected void onResume() {
        super.onResume();


//        activityRunning = true;
//        Sensor countSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
//        if (countSensor != null) {
//            mSensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
//        }
//        //mSensorManager.registerListener(this, mStepDetectorSensor, SensorManager.SENSOR_DELAY_FASTEST);
//        else {
//            Toast.makeText(this, "Count sensor not available!", Toast.LENGTH_LONG).show();
//        }
    }
    @Override
    protected void onPause()
    {
        super.onPause();
       // activityRunning = false;
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

//    public void onSensorChanged(SensorEvent event)
//    {
//        /*Sensor sensor = event.sensor;
//        float[] values = event.values;
//        int value = -1;
//        if (values.length > 0)
//        {
//            steps++;
//            value = (int) values[0];
//        }
//        if (sensor.getType() == Sensor.TYPE_STEP_COUNTER)
//        {
//            textCount.setText("" + steps);
//            textTotal.setText("" + value);
//
//        }*/
//        if(activityRunning){
//
//
//                textCount.setText(String.valueOf((int) event.values[0]));
//                //added (int) so no decimal
//
//
//        }
//
//    }
//    @Override
//    public void onAccuracyChanged(Sensor sensor, int accuracy)
//    {
//
//    }
//    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        //SecFragment fragment = (SecFragment) getSupportFragmentManager().findFragmentById(R.id.frag1);
        //int total = fragment.gettotal();
        //savedInstanceState.putInt(PREV_S, total);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Toast.makeText(this,"Success",Toast.LENGTH_SHORT).show();

    }


    //
//    public static class MyFragment extends Fragment{
//        public MyFragment(){
//
//        }
//
//        //public static MyFragment newInstance(int pageN)
//
//        public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState){
//            View myFragmentView = inflater.inflate(R.layout.counter_info,container,false);
//
//            return myFragmentView;
//        }
//    }



    //For TABLAYOUT CRAP
    class MyPagerAdapter extends FragmentStatePagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        //gives position and ask to return fragment at that position
        {
            switch(position) {

                case 0:
                    return SecFragment.newInstance("FirstFragment", "1");
                case 1:
                    return SecondFragment.newInstance("2ndFragment", "2");
                case 2:
                    return ThirdFragment.newInstance("3rdFragment", "3");
                default:
                    return SecFragment.newInstance("FirstFragment", "1");
            }
            //return fragment at the position
        }

        @Override
        public int getCount() {
            return 3;
        }

        public CharSequence getPageTitle(int position){

            switch(position){
                case 0: return ""+position;
                case 1: return ""+position;
                case 2: return ""+position;
                    default: return "0";
            }

        }
    }




}