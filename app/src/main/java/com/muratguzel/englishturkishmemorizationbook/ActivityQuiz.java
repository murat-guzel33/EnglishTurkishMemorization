package com.muratguzel.englishturkishmemorizationbook;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.muratguzel.englishturkishmemorizationbook.databinding.ActivityQuizBinding;

import java.util.ArrayList;
import java.util.Collections;

public class ActivityQuiz extends AppCompatActivity {

    private ActivityQuizBinding binding;

    private DictionaryDatabase dictionaryDatabase;
    private int correctAnswerCount = 0;
    private int wrongAnswerCount = 0;
    private int questionCount = 0;

    private ArrayList<Words> soruHavuzu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        dictionaryDatabase = new DictionaryDatabase(this);
        soruHavuzu = new ArrayList<>();

        soruHavuzu.addAll(dictionaryDatabase.getAllWords());

        soruSor();
    }

    private void soruSor() {

        if (soruHavuzu.isEmpty()) {

            soruHavuzu.addAll(dictionaryDatabase.getAllWords());
        }

        int randomIndex = (int) (Math.random() * soruHavuzu.size());

        if (randomIndex >= soruHavuzu.size()) {

            return;
        }
        Words word = soruHavuzu.get(randomIndex);


        soruHavuzu.remove(randomIndex);



        ArrayList<String> siklar = new ArrayList<>();
        siklar.add(word.getTurkishWord());
        siklar.add(soruHavuzu.get(0).getTurkishWord());
        siklar.add(soruHavuzu.get(1).getTurkishWord());
        Collections.shuffle(siklar);


        binding.textViewEnglishWord.setText(word.getEnglishWords());


        binding.radioButtonA.setText(siklar.get(0));
        binding.radioButtonB.setText(siklar.get(1));
        binding.radioButtonC.setText(siklar.get(2));


        String correctAnswer = word.getTurkishWord();

        binding.radioGroup.clearCheck();

        binding.radioButtonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRadioButtonClicked(binding.radioButtonA, correctAnswer);
            }
        });

        binding.radioButtonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRadioButtonClicked(binding.radioButtonB, correctAnswer);
            }
        });

        binding.radioButtonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRadioButtonClicked(binding.radioButtonC, correctAnswer);
            }
        });
    }

    private void onRadioButtonClicked(RadioButton radioButton, String correctAnswer) {

        if (radioButton.isChecked()) {

            String selectedAnswer = radioButton.getText().toString();

            if (selectedAnswer.equals(correctAnswer)) {
                correctAnswerCount++;
                Toast.makeText(ActivityQuiz.this, "Doğru cevap", Toast.LENGTH_SHORT).show();
            } else {
                wrongAnswerCount++;
                Toast.makeText(ActivityQuiz.this, "Yanlış cevap", Toast.LENGTH_SHORT).show();
            }

            questionCount++;

            if (questionCount == 8) {
                showResultsAlertDialog();
            } else {
                soruSor();
            }
        }
    }



    private void showResultsAlertDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Test Sonucu");
        alertDialogBuilder.setMessage("Doğru: " + correctAnswerCount + " Yanlış: " + wrongAnswerCount);

        alertDialogBuilder.setPositiveButton("Tekrar Çöz", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                resetQuiz();
                soruSor();
            }
        });
        alertDialogBuilder.setNegativeButton("Kapat", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void resetQuiz() {
        correctAnswerCount = 0;
        wrongAnswerCount = 0;
        questionCount = 0;

        binding.radioGroup.clearCheck();

        soruHavuzu.clear();
        soruHavuzu.addAll(dictionaryDatabase.getAllWords());
    }
}