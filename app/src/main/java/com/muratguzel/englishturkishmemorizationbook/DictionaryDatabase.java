package com.muratguzel.englishturkishmemorizationbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

public class DictionaryDatabase {
    private SQLiteDatabase database;

    public DictionaryDatabase(Context context) {
        try {
            database = context.openOrCreateDatabase("Words", Context.MODE_PRIVATE, null);
            String createTableSQL = "CREATE TABLE IF NOT EXISTS words " +
                    "(id , " +
                    "englishWord TEXT, " +
                    "turkishWord TEXT)";
            database.execSQL(createTableSQL);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean addWord(String englishWord, String turkishWord) {


        ContentValues values = new ContentValues();
        values.put("englishWord", englishWord);
        values.put("turkishWord", turkishWord);
        long result = database.insert("words", null, values);
        if (result == -1) {
            Log.e("Database", "Kelime eklenirken hata oluştu");
            return false;
        } else {
            Log.d("Database", "Kelime başarıyla eklendi");
            return true;

        }

    }

    public void addDefaultWords() {

        ArrayList<Words> existingWords = getAllWords();

        if (existingWords.isEmpty()) {
            addWord("Hello", "Merhaba");
            addWord("Goodbye", "Hoşçakal");
            addWord("door", "kapı");
            addWord("pencil", "kalem");
            addWord("window", "pencere");
            addWord("chair", "sandalye");
            addWord("lock", "kilit");
            addWord("box", "kutu");
            addWord("night", "gece");
            addWord("day", "gün");
            addWord("morning", "sabah");
            addWord("flower", "çiçek");
            addWord("mouse", "fare");
        }
    }

    public ArrayList<Words> getAllWords() {
        ArrayList<Words> wordsArrayList = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM words", null);

        int idIx = cursor.getColumnIndex("id");
        int englishWordIx = cursor.getColumnIndex("englishWord");
        int turkishWordIx = cursor.getColumnIndex("turkishWord");

        while (cursor.moveToNext()) {
            int id = cursor.getInt(idIx);
            String englishWords = cursor.getString(englishWordIx);
            String turkishWords = cursor.getString(turkishWordIx);
            Words words = new Words(id, englishWords, turkishWords);
            wordsArrayList.add(words);
        }

        cursor.close();
        return wordsArrayList;
    }

    public boolean updateWord(int id, String newEnglishWord, String newTurkishWord) {
        ContentValues values = new ContentValues();
        values.put("englishWord", newEnglishWord);
        values.put("turkishWord", newTurkishWord);

        int rowsAffected = database.update("words", values, "id=?", new String[]{String.valueOf(id)});

        if (rowsAffected > 0) {
            Log.d("Database", "Kelime güncellendi");
            return true;
        } else {
            Log.e("Database", "Kelime güncellenirken hata oluştu");
            return false;
        }
    }

    public Words getWordById(int wordId) {
        Cursor cursor = database.rawQuery("SELECT * FROM words WHERE id = ?", new String[]{String.valueOf(wordId)});

        if (!cursor.moveToNext()) {
            return null;
        }

        int idIx = (cursor.getColumnIndex("id"));
        int englishWordIx = (cursor.getColumnIndex("englishWord"));
        int turkishWordIx = (cursor.getColumnIndex("turkishWord"));
        int id = cursor.getInt(idIx);
        String englishWord = cursor.getString(englishWordIx);
        String turkishWord = cursor.getString(turkishWordIx);
        return new Words(id, englishWord, turkishWord);
    }

    public boolean deleteWord(int id) {
        int rowsAffected = database.delete("words", "id=?", new String[]{String.valueOf(id)});

        if (rowsAffected > 0) {
            Log.d("Database", "Kelime silindi");
            return true;
        } else {
            Log.e("Database", "Kelime silinirken hata oluştu");
            return false;
        }

    }
}

