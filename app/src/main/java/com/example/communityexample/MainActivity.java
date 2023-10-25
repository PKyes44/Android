package com.example.communityexample;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

        /* initiate recyclerview */
        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mCommunityList = new ArrayList<>();

//        // 현재 날짜 구하기
//        LocalDate now = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            now = LocalDate.now();
//        }
//
//        // 포맷 정의
//        DateTimeFormatter formatter = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
//        }
//
//        // 포맷 적용
//        String formatedNow = "";
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            formatedNow = now.format(formatter);
//        }
        mDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://hackertontemp-default-rtdb.firebaseio.com/");
        mDatabase.child("article").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
//                     List<Object> tempList = (List<Object>) task.getResult().getValue();
//                     List<ArticleModel> articleModelList = new ArrayList<>();
//                     for (int i = 0; i < tempList.size(); i++) {
//                         if (tempList.get(i) == null) {
//                             continue;
//                         }
//                         ArticleModel article = (ArticleModel) tempList.get(i);
//                         mCommunityList.add(new CommunityListModel(article.id, article.title, article.regDate));
//                     }
//                    mRecyclerAdapter.setFriendList(mCommunityList);
                }
            }
        });
    }
}