package com.muratguzel.englishturkishmemorizationbook;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.muratguzel.englishturkishmemorizationbook.databinding.WordsRowBinding;

import java.util.ArrayList;

public class WordsAdapter extends RecyclerView.Adapter<WordsAdapter.WordsHolder> {
    private ArrayList<Words> wordsArrayListList;
    private Context context;
    private DictionaryDatabase dictionaryDatabase;

    public WordsAdapter(ArrayList<Words> wordsArrayListList, Context context) {
        this.wordsArrayListList = wordsArrayListList;
        this.context = context;
        this.dictionaryDatabase = new DictionaryDatabase(context); // Database nesnesini oluşturun
    }




    @NonNull
    @Override
    public WordsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        WordsRowBinding wordsRowBinding = WordsRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new WordsHolder(wordsRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull WordsHolder holder, int position) {
        Words words = wordsArrayListList.get(position);

        holder.binding.recyclerViewEnglishText.setText(words.getEnglishWords());
        holder.binding.recyclerViewTurkishText.setText(words.getTurkishWord());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final int position = holder.getAdapterPosition();
                final Words word = wordsArrayListList.get(position);

                new AlertDialog.Builder(context)
                        .setTitle("Kelimeyi Sil")
                        .setMessage("" + word.getEnglishWords() + " kelimesini silmek istediğinize emin misiniz?")
                        .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteItem(position);
                            }
                        })
                        .setNegativeButton("Hayır", null)
                        .show();

                return true;
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //updateActivity i aç
                int wordId = wordsArrayListList.get(position).getWordId();
                Intent intent = new Intent(context, UpdateWordsActivity.class);
                intent.putExtra("wordId",wordId);
                context.startActivity(intent);
            }
        });
    }


    private void deleteItem(int position) {
        int wordId = wordsArrayListList.get(position).getWordId();
        boolean isDeleted = dictionaryDatabase.deleteWord(wordId);
        if (isDeleted) {
            wordsArrayListList.remove(position);
            notifyItemRemoved(position);
        } else {
            Toast.makeText(context, "Kelime silinirken bir hata oluştu", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return wordsArrayListList.size();
    }

    public void setWordsList(ArrayList<Words> allWords) {
        {
            this.wordsArrayListList = allWords;
            notifyDataSetChanged(); // Adapter'a verinin güncellendiğini bildir
        }
    }



    public class WordsHolder extends RecyclerView.ViewHolder{
        private WordsRowBinding binding;
        public WordsHolder(WordsRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}
