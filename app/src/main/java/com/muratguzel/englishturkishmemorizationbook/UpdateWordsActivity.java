package com.muratguzel.englishturkishmemorizationbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.muratguzel.englishturkishmemorizationbook.databinding.ActivityUpdateWordsBinding;

public class UpdateWordsActivity extends AppCompatActivity {
    private ActivityUpdateWordsBinding binding;
    int wordId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateWordsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Intent intent = getIntent();
         wordId = intent.getIntExtra("wordId",0);
        DictionaryDatabase dictionaryDatabase = new DictionaryDatabase(this);
        Words words = dictionaryDatabase.getWordById(wordId);

        binding.englishUpdatetext.setText(words.getEnglishWords());
        binding.turkishUpdateText.setText(words.getTurkishWord());

    }
    public void update(View view) {
        String englishWord = binding.englishUpdatetext.getText().toString();
        String turkishWord = binding.turkishUpdateText.getText().toString();

        DictionaryDatabase dictionaryDatabase = new DictionaryDatabase(this);
        boolean updated = dictionaryDatabase.updateWord(wordId,englishWord,turkishWord);
        if (updated && wordId != 0) {
            Toast.makeText(this, "Kelime güncellendi", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Kelime güncellenirken hata oluştu", Toast.LENGTH_SHORT).show();
        }

        finish();
    }
    }
