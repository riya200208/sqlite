package com.example.sqlitedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText username, password;
    Button add, show;
    ListView lv;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        add = (Button) findViewById(R.id.add);
        show = (Button) findViewById(R.id.show);
        lv = (ListView) findViewById(R.id.lvlist);
    //    delete = (Button) findViewById(R.id.delete);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customer_model cust = null;
                try {
                    cust = new customer_model(username.getText().toString(), password.getText().toString(), -1);
                    //  Toast.makeText(MainActivity.this, cust.toString(), Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    //   Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
                }

                DBHelper datab = new DBHelper(MainActivity.this);
                boolean res = datab.addOne(cust);
                Toast.makeText(MainActivity.this, "Success " + res, Toast.LENGTH_LONG).show();
                username.setText("");
                password.setText("");
            }


        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                customer_model custum = (customer_model) adapterView.getItemAtPosition(i);
                DBHelper sdb = new DBHelper(getApplicationContext());
                sdb.delete(custum);
                List<customer_model> everyone = sdb.getEveryOne();
                extracted(everyone);

            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dhp = new DBHelper(MainActivity.this);
                List<customer_model> everyone = dhp.getEveryOne();
                extracted(everyone);
                //  Toast.makeText(getApplicationContext(),everyone.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void extracted(List<customer_model> everyone) {
        adapter = new ArrayAdapter<customer_model>(MainActivity.this, android.R.layout.simple_list_item_1, everyone);
        lv.setAdapter((ListAdapter) adapter);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        SearchView sv = (SearchView) menu.findItem(R.id.search_bar).getActionView();

        SearchManager sm = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        sv.setSearchableInfo(sm.getSearchableInfo(getComponentName()));
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.home:
                username.setText("");
                password.setText("");
                lv.setAdapter(null);
                return true;
//
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//
}
