package com.muratguzel.englishturkishmemorizationbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.muratguzel.englishturkishmemorizationbook.databinding.ActivityMyWordsBinding;

import java.util.ArrayList;

public class MyWordsActivity extends AppCompatActivity {

    private ActivityMyWordsBinding binding;


    DictionaryDatabase dictionaryDatabase;

    WordsAdapter wordsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyWordsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        dictionaryDatabase = new DictionaryDatabase(this);


        dictionaryDatabase.addDefaultWords();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));



        ArrayList<Words> allWords = dictionaryDatabase.getAllWords();

        wordsAdapter = new WordsAdapter(allWords,this);

        binding.recyclerView.setAdapter(wordsAdapter);
        updateRecyclerView();

    }


    @Override
    protected void onResume() {
        super.onResume();
        updateRecyclerView();
    }
    private void updateRecyclerView() {
        ArrayList<Words> allWords = dictionaryDatabase.getAllWords();
        if (wordsAdapter != null) {
            wordsAdapter.setWordsList(allWords);
            wordsAdapter.notifyDataSetChanged();
        }
    }
}