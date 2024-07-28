package com.muratguzel.englishturkishmemorizationbook;

import java.io.Serializable;

public class Words implements Serializable {
    private int wordId;
    private String englishWords;
    private String turkishWord;


    public Words(int wordId, String englishWords, String turkishWord) {
        this.wordId = wordId;
        this.englishWords = englishWords;
        this.turkishWord = turkishWord;
    }
    public int getWordId() {
        return wordId;
    }

    public void setWordId(int wordId) {
        this.wordId = wordId;
    }

    public String getEnglishWords() {
        return englishWords;
    }

    public void setEnglishWords(String englishWords) {
        this.englishWords = englishWords;
    }

    public String getTurkishWord() {
        return turkishWord;
    }

    public void setTurkishWord(String turkishWord) {
        this.turkishWord = turkishWord;
    }

}
