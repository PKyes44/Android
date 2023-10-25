package com.example.communityexample;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Map;

public class DetailActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;

    AppCompatButton backButton;
    TextView title;
    TextView content;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        Intent recevieIntent = getIntent();
        String articleId = recevieIntent.getStringExtra("articleId");

        backButton = findViewById(R.id.onBack);
        title = findViewById(R.id.title);
        content = findViewById(R.id.content);

        mDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://hackertontemp-default-rtdb.firebaseio.com/");
        assert articleId != null;
        mDatabase.child("article").child(articleId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    Map<String, Object> tmp = (Map<String, Object>) task.getResult().getValue();
                    ArticleModel article = new ArticleModel(
                            articleId,
                            tmp.get("title").toString(),
                            tmp.get("content").toString(),
                            tmp.get("regDate").toString()
                    );
                    title.setText(article.title);
                    content.setText(article.content);
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
