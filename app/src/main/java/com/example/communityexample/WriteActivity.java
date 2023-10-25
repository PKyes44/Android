package com.example.communityexample;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class WriteActivity extends AppCompatActivity {

    EditText title;
    EditText content;
    AppCompatButton uploadButton;
    private DatabaseReference mDatabase;
    private int articleCount;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_form);

        title = findViewById(R.id.titleInput);
        content = findViewById(R.id.contentInput);
        uploadButton = findViewById(R.id.uploadBtn);

        Intent recevieIntent = getIntent();
        String tempCount = recevieIntent.getStringExtra("articleCount");
        assert tempCount != null;
        articleCount = Integer.parseInt(tempCount);

        mDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://hackertontemp-default-rtdb.firebaseio.com/");

        // 현재 날짜 구하기
        LocalDate now = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            now = LocalDate.now();
        }

        // 포맷 정의
        DateTimeFormatter formatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        }

        // 포맷 적용
        String formatedNow = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatedNow = now.format(formatter);
        }

        String finalFormatedNow = formatedNow;
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child("article").child(String.valueOf(articleCount+1)).setValue(
                        new ArticleModel(
                                String.valueOf(articleCount+1),
                                title.getText().toString(),
                                content.getText().toString(),
                                finalFormatedNow)
                );
                Toast.makeText(getApplicationContext(), "Complete write", Toast.LENGTH_LONG);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
