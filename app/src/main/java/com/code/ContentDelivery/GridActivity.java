package com.code.ContentDelivery;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class GridActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    ArrayList<String> alphaList = new ArrayList<>();
    int columnCount=2;
    private FirebaseAuth mAuth;

    public void attachViews(){

        recyclerView = findViewById(R.id.recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()){
            case R.id.column1:
                columnCount = 1;
                updateGrid();
                return true;
            case R.id.column2:
                columnCount = 2;
                updateGrid();
                return true;
            case R.id.column3:
                columnCount = 3;
                updateGrid();
                return true;
            case R.id.column4:
                columnCount = 4;
                updateGrid();
                return true;
            default:
                return false;
        }
    }
    public void updateGrid(){
        recyclerView.setLayoutManager(new GridLayoutManager(this,columnCount));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        attachViews();
        Intent intent = getIntent();
        setTitle("Welcome " + intent.getStringExtra("username"));

        final MyAdapter customAdapter = new MyAdapter(alphaList,this);
        recyclerView.setLayoutManager(new GridLayoutManager(this,columnCount));
        recyclerView.setAdapter(customAdapter);



        FirebaseDatabase.getInstance().getReference().child("Alpha").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String alpha = String.valueOf(dataSnapshot.getKey());
                alphaList.add(alpha);
                Log.i("data",alpha);
                customAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                alphaList.remove(dataSnapshot.getKey());
                customAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    public void onBackPressed() {

        finish();

    }
}
