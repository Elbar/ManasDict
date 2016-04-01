package kg.manasdict.android.app.data;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by fukuro on 4/1/16.
 */
public class WordDetails {
    public static final String ID_FIELD = "word_id";


    @DatabaseField(generatedId = true, columnName = ID_FIELD)
    public int wordId;

    @DatabaseField(columnName = "kg_word")
    public String kgWord;


    @DatabaseField(columnName = "ru_word")
    public String ruWord;

    @DatabaseField(columnName = "en_word")
    public String enWord;

    @DatabaseField(columnName = "tr_word")
    public String trWord;

    public WordDetails() {
    }

    public WordDetails(final String kgWord, final String ruWord, final String enWord, final String trWord) {
        this.kgWord = kgWord;
        this.ruWord = ruWord;
        this.enWord = enWord;
        this.trWord = trWord;
    }
}
