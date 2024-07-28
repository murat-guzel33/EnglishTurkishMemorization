package com.muratguzel.englishturkishmemorizationbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.muratguzel.englishturkishmemorizationbook.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

         private ActivityMainBinding binding;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            binding = ActivityMainBinding.inflate(getLayoutInflater());
            View view = binding.getRoot();
            setContentView(view);
        }


    public void myWords(View view) {
        Intent intent = new Intent(MainActivity.this,MyWordsActivity.class);
        startActivity(intent);
    }
        public void addWords(View view) {
            Intent intent = new Intent(MainActivity.this, AddWordsActivity.class);
            startActivity(intent);
        }

        public  void quiz(View view) {
            Intent intent =  new Intent(MainActivity.this,ActivityQuiz.class);
            startActivity(intent);
        }


    }