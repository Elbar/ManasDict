package kg.manasdict.android.app.db.model;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by fukuro on 4/1/16.
 */
public class WordDetails {

    @DatabaseField(generatedId = true, columnName = "id")
    private int id;

    @DatabaseField(columnName = "kg_word")
    private String kgWord;

    @DatabaseField(columnName = "ru_word")
    private String ruWord;

    @DatabaseField(columnName = "en_word")
    private String enWord;

    @DatabaseField(columnName = "tr_word")
    private String trWord;

    public int getId() {
        return id;
    }

    public String getKgWord() {
        return kgWord;
    }

    public void setKgWord(String kgWord) {
        this.kgWord = kgWord;
    }

    public String getRuWord() {
        return ruWord;
    }

    public void setRuWord(String ruWord) {
        this.ruWord = ruWord;
    }

    public String getEnWord() {
        return enWord;
    }

    public void setEnWord(String enWord) {
        this.enWord = enWord;
    }

    public String getTrWord() {
        return trWord;
    }

    public void setTrWord(String trWord) {
        this.trWord = trWord;
    }

    public WordDetails() {
    }

    public WordDetails(final String kgWord, final String ruWord, final String enWord, final String trWord) {
        this.kgWord = kgWord;
        this.ruWord = ruWord;
        this.enWord = enWord;
        this.trWord = trWord;
    }
}
