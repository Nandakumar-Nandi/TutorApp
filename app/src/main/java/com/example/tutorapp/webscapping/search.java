package com.example.tutorapp.webscapping;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tutorapp.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.w3c.dom.Element;

import java.io.IOException;

public class search extends AppCompatActivity {
    TextView question,answer,your_answer,webanswer,source;
    Button browse;
    String url="https://www.google.com/search?q=";
    Elements sourceelement,sourcelink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        question=findViewById(R.id.web_question);
        your_answer=findViewById(R.id.web_yours);
        answer=findViewById(R.id.web_given);
        webanswer=findViewById(R.id.web_google);
        source=findViewById(R.id.web_source);
        browse=findViewById(R.id.web_browse);
        question.setText(getIntent().getStringExtra("Question"));
        your_answer.setText(getIntent().getStringExtra("YourAnswer"));
        answer.setText(getIntent().getStringExtra("GivenAnswer"));
        try {
            Document doc= Jsoup.connect(url+question.getText().toString()).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/101.0.4951.54 Safari/537.36").get();
            validatedata(doc);
        } catch (IOException e) {
            e.printStackTrace();
        }
        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url+question.getText().toString())));
            }
        });

    }
    private void validatedata(Document doc) {
        Elements res=doc.getElementsByClass("hgKElc");
        sourceelement=doc.getElementsByClass("VuuXrf");
        sourcelink=doc.getElementsByClass("apx8Vc qLRx3b tjvcx GvPZzd cHaqb");
        webanswer.setText(res.text().toString());
        source.setText(sourceelement.get(0).text().toString());
    }
}