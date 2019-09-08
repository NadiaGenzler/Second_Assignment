package com.example.secondassignment2;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    ArrayList<User> usersList;
    MyArrayAdapter adapter=null;
    DatabaseHelper databaseHelper;
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);
        usersList=new ArrayList();
        adapter=new MyArrayAdapter(getBaseContext(),android.R.layout.simple_list_item_2,usersList);

        listview=findViewById(R.id.myLV);
        listview.setAdapter(adapter);
        listview.setClickable(true);
        listview.setLongClickable(true);

        onClicks();

    };

    public void onClicks(){

        //add user
        findViewById(R.id.addBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final OkHttpClient client=new OkHttpClient();
                String url="https://randomuser.me/api";

                client.newCall(new Request.Builder().url(url).build()).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {

                            try {
                                JSONObject serverResponse=new JSONObject(response.body().string());
                                JSONArray array=serverResponse.getJSONArray("results");
                                JSONObject data=new JSONObject(array.getString(0));
                                JSONObject name=new JSONObject(data.getString("name"));
                                JSONObject location=new JSONObject(data.getString("location"));

                                String fullName=name.getString("first")+" "+name.getString("last");
                                String gender=data.getString("gender");
                                String exactLocation=location.getString("street")+" "+location.getString("city")+" "
                                        +location.getString("state")+" "+location.getString("postcode");

                                final User user=new User(fullName,gender,exactLocation);
                                usersList.add(user);
                                databaseHelper.insertData(fullName, gender, exactLocation);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.notifyDataSetChanged();
                                }
                            });

                        }
                    }
                });
            }
        });

        //show all the list
        findViewById(R.id.showBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = databaseHelper.getAllUsers();
                if (cursor.getCount() == 0)
                { QuickToast("no data");
                    return;
                }
                while (cursor.moveToNext())
                { usersList.add(new User(cursor.getString(0),cursor.getString(1),cursor.getString(2)));
                }

                adapter.notifyDataSetChanged();
            }
        });

        //delete user
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int position=i;
                showAlert(position,"Delete '" + usersList.get(position).fullName + "'",
                        "Are you sure you want to delete "+usersList.get(position).fullName +"?");
                return true;
            }
        });

        //clear all users
        findViewById(R.id.clearBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usersList.clear();
                databaseHelper.deleteAllData();
                adapter.notifyDataSetChanged();
            }
        });

        //update on new activity
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int index=i;
                Intent intent=new Intent(getBaseContext(),SecondActivity.class);
                intent.putExtra("KEY_FOR_NAME",usersList.get(i).fullName);
                intent.putExtra("KEY_FOR_GENDER",usersList.get(i).gender);
                intent.putExtra("KEY_FOR_LOCATION",usersList.get(i).exactLocation);
                startActivity(intent);

            }
        });
    }

    private void QuickToast(String message)
    { Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showAlert(final int position, String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // adapter.remove(user.fullName);
                usersList.remove(position);
                databaseHelper.deleteData(usersList.get(position).fullName);
                adapter.notifyDataSetChanged();
                Toast.makeText(getBaseContext(), "Deleted", Toast.LENGTH_LONG).show();
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getBaseContext(), "Ok, forget about it", Toast.LENGTH_LONG).show();
                dialogInterface.dismiss();
            }
        });

        builder.show();
    }

}
