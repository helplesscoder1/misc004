package com.example.gettingstartedapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Display greeting
        Greeter greeter = new Greeter("Alex", "Smith");
        TextView greetingText = findViewById(R.id.greetingText);
        greetingText.setText(greeter.getGreeting());

        // Display date
        TextView dateText = findViewById(R.id.dateText);
        dateText.setText(Date.date_now(1));

        // Fetch and display HTML content
        TextView contentText = findViewById(R.id.contentText);
        new Thread(() -> {
            String html = GetContent.fetch("https://172.20.1.21:5000/test");
            runOnUiThread(() -> contentText.setText(html));
        }).start();

        // Post Android version to server
        NetworkFunctions.postAndroidVersion();
    }
}
