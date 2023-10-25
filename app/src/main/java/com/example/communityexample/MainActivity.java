package com.example.communityexample;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter mRecyclerAdapter;
    private ArrayList<CommunityListModel> mCommunityList;
    private DatabaseReference mDatabase;
    AppCompatButton writeButton;
    private int articleCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showArticle();
    }

    public void showArticle() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        /* initiate adapter */
        mRecyclerAdapter = new MyRecyclerAdapter();

        mRecyclerAdapter.setOnItemClickListener(new MyRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                final CommunityListModel item = mCommunityList.get(pos);
                Toast.makeText(getApplicationContext(), "onItemClick position : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                Intent sendIntent = new Intent(getApplicationContext(), DetailActivity.class);
                sendIntent.putExtra("articleId", item.id);
                startActivity(sendIntent);

            }
        });

        /* initiate recyclerview */
        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mCommunityList = new ArrayList<>();

        mDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://hackertontemp-default-rtdb.firebaseio.com/");
        mDatabase.child("article").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                     List<Object> tempList = (List<Object>) task.getResult().getValue();
                     for (int i = 0; i < tempList.size(); i++) {
                         if (tempList.get(i) == null) {
                             continue;
                         }
                         Map<String, Object> tmp = (Map<String, Object>) tempList.get(i);
                         ArticleModel article = new ArticleModel(
                                 tmp.get("id").toString(),
                                 tmp.get("title").toString(),
                                 tmp.get("content").toString(),
                                 tmp.get("regDate").toString()
                         );
                         mCommunityList.add(new CommunityListModel(article.id, article.title, article.regDate));
                         articleCount++;
                     }
                    mRecyclerAdapter.setFriendList(mCommunityList);
                }
            }
        });
        mRecyclerAdapter.setFriendList(mCommunityList);

        writeButton = findViewById(R.id.onWriteTap);
        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WriteActivity.class);
                intent.putExtra("articleCount", String.valueOf(articleCount));
                startActivity(intent);
            }
        });

    }
}

