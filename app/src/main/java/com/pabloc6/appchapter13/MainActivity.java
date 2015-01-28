package com.pabloc6.appchapter13;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.StringTokenizer;


public class MainActivity extends ActionBarActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    Button goToInternet;
    TextView animalShow;
    String [] mAnimalsList;
    ListView mListViewAnimals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goToInternet = (Button) findViewById(R.id.b_goToInternet);
        goToInternet.setOnClickListener(this);

        animalShow = (TextView) findViewById(R.id.tv_animal);

        GetAnimalsFromInternet myThreadList = new GetAnimalsFromInternet();
        myThreadList.execute("");




//        mListViewAnimals = (ListView) findViewById(R.id.lv_ListViewAnimals);

/*
        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mAnimalsList);

        //MyAdapter adapter = new MyAdapter(this, 0, values);

        mListViewAnimals.setAdapter(adapter);

        //   myListView.setOnItemClickListener(this);*/


//        mListViewAnimals.getOnItemClickListener(this);






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

    @Override
    public void onClick(View v) {
        GetDataFromInternet myThread = new GetDataFromInternet();
        myThread.execute("");

        /*for (int i1 = 0; i1 < 100000; i1++) {
            for (int i2 = 0; i2 < 100000; i2++) {
                for (int i3 = 0; i3 < 100000; i3++) {
                    String sum = (Integer.toString(i1) + Integer.toString(i2) + Integer.toString(i3));
                    goToInternet.setText("Hello World");
                }
            }
        }*/
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String item = ((TextView)view).getText().toString();

//        Toast.makeText(getBaseContext(), item, Toast.LENGTH_SHORT).show();

        Log.d("DEBUG->", "animal selected: " + item);
        GetDataFromInternet myAnimal = new GetDataFromInternet();
        myAnimal.execute(item);

    }


    private class GetAnimalsFromInternet extends AsyncTask<String, Void, String> implements AdapterView.OnItemClickListener {

        ListView mListViewAnimals;

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

        }

        @Override
        protected String doInBackground(String... params) {

           String lineRead = "";

            try {
                URL url = new URL("http://www.11sheep.com/training/android/http_test.php?command=getAnimalList");
                URLConnection connection = url.openConnection();

                DataInputStream dataIn = new DataInputStream(connection.getInputStream());
                lineRead = dataIn.readLine();

            } catch (IOException e) {
                e.printStackTrace();
            }

           /* for (int i1 = 0; i1 < 100000; i1++) {
                for (int i2 = 0; i2 < 100000; i2++) {
                    for (int i3 = 0; i3 < 100000; i3++) {
                        String sum = (Integer.toString(i1) + Integer.toString(i2) + Integer.toString(i3));
                        Log.d("DEBUG->", "Sum: " + sum);
//                        goToInternet.setText(sum);
                    }
                }
            }*/
            return lineRead;
        }


        @Override
        protected void onPostExecute(String result) {
            Log.d("DEBUG->", "result: " + result);
            mAnimalsList = result.split(",");

            Log.d("DEBUG->", "list: " + mAnimalsList[0]+ " " + mAnimalsList[1]);
            animalShow.setText(mAnimalsList[0]);

            mListViewAnimals = (ListView) findViewById(R.id.lv_ListViewAnimals);
            ListAdapter adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, mAnimalsList);
            mListViewAnimals.setAdapter(adapter);
            mListViewAnimals.setOnItemClickListener(this);

        }



        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            String item = ((TextView)view).getText().toString();
            Log.d("DEBUG->", "Select animal: " + item);

            Toast.makeText(getBaseContext(), item, Toast.LENGTH_SHORT).show();

            GetDataFromInternet myThreadList = new GetDataFromInternet();
            myThreadList.execute(item);


        }
    }


    private class GetDataFromInternet extends AsyncTask<String, Void, String> {

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... params) {

            String animalToGet = params[0];
            String lineRead = "";

            Log.d("DEBUG->", "animalToGet: " + animalToGet);
            try {
                URL url = new URL("http://www.11sheep.com/training/android/http_test.php?command=getAnimalSound&animal=" + animalToGet);
                URLConnection connection = url.openConnection();

                DataInputStream dataIn = new DataInputStream(connection.getInputStream());
                lineRead = dataIn.readLine();

            } catch (IOException e) {
                e.printStackTrace();
            }

           /* for (int i1 = 0; i1 < 100000; i1++) {
                for (int i2 = 0; i2 < 100000; i2++) {
                    for (int i3 = 0; i3 < 100000; i3++) {
                        String sum = (Integer.toString(i1) + Integer.toString(i2) + Integer.toString(i3));
                        Log.d("DEBUG->", "Sum: " + sum);
//                        goToInternet.setText(sum);
                    }
                }
            }*/
            return lineRead;
        }


        @Override
        protected void onPostExecute(String result) {
            Log.d("DEBUG->", "sound: " + result);
//            mAnimalsList = result.split(",");

//            Log.d("DEBUG->", "list: " + mAnimalsList[0]+ " " + mAnimalsList[1]);
            animalShow.setText(result);
            Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
            intent.putExtra("animalSound", result);
            startActivity(intent);


        }
    }
}
