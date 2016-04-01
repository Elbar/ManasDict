package kg.manasdict.android.app.data;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * Created by fukuro on 4/1/16.
 */
public class KgWordDetails implements Serializable {

    public static final String ID_FIELD = "kgword_id";

    @DatabaseField(generatedId = true, columnName = ID_FIELD)
    public int kgWordId;

    @DatabaseField(columnName = "kg_word")
    public String kgWord;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    public RuWordDetails ruWordDetails;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    public EnWordDetails enWordDetails;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    public TrWordDetails trWordDetails;

    public KgWordDetails() {
    }

    public KgWordDetails(final String word) {
        this.kgWord = word;
    }

    public KgWordDetails(final String word,  RuWordDetails ruWordDetails){
        this.kgWord = word;
        this.ruWordDetails = ruWordDetails;
    }

    public KgWordDetails(final String word,  EnWordDetails enWordDetails){
        this.kgWord = word;
        this.enWordDetails = enWordDetails;
    }

    public KgWordDetails(final String word,  TrWordDetails trWordDetails){
        this.kgWord = word;
        this.trWordDetails = trWordDetails;
    }

}
