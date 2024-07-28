package com.muratguzel.englishturkishmemorizationbook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.muratguzel.englishturkishmemorizationbook.databinding.ActivityAddWordsBinding;

import java.util.ArrayList;

public class AddWordsActivity extends MyWordsActivity {

      private  ActivityAddWordsBinding binding;

    DictionaryDatabase dictionaryDatabase;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            binding = ActivityAddWordsBinding.inflate(getLayoutInflater());
            View view = binding.getRoot();
            setContentView(view);

            dictionaryDatabase = new DictionaryDatabase(this);
        }

        public void addWords(View view) {
            String turkishInput = binding.turkishText.getText().toString();
            String englishInput = binding.englishText.getText().toString();if (turkishInput.equals("") || englishInput.equals("")) {
                Toast.makeText(this, "Lütfen boş alanları Doldurun", Toast.LENGTH_SHORT).show();
            } else {
                boolean wordAdded = dictionaryDatabase.addWord(englishInput, turkishInput);
                if (wordAdded) {
                    Toast.makeText(this, "Kelime Başarıyla eklendi", Toast.LENGTH_SHORT).show();
                    updateRecyclerView();
                    finish();
                } else {
                    Toast.makeText(this, "Kelime Eklenemedi", Toast.LENGTH_SHORT).show();
                }
            }
        }

        private void updateRecyclerView() {

            ArrayList<Words> allWords = dictionaryDatabase.getAllWords();
            if (wordsAdapter != null) {
                wordsAdapter.setWordsList(allWords);
                wordsAdapter.notifyDataSetChanged();
            }

        } 
    }