package kg.manasdict.android.app.data;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by fukuro on 4/1/16.
 */
public class TrWordDetails {

    public static final String ID_FIELD = "trword_id";

    @DatabaseField(generatedId = true, columnName = ID_FIELD)
    public int trWordId;

    @DatabaseField(columnName = "en_word")
    public String trWord;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    public RuWordDetails ruWordDetails;


    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    public KgWordDetails kgWordDetails;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    public EnWordDetails enWordDetails;

    public TrWordDetails() {
    }

    public TrWordDetails(final String word) {
        this.trWord = word;
    }

    public TrWordDetails(final String word,  RuWordDetails ruWordDetails){
        this.trWord = word;
        this.ruWordDetails = ruWordDetails;
    }

    public TrWordDetails(final String word,  KgWordDetails kgWordDetails){
        this.trWord = word;
        this.enWordDetails = enWordDetails;
    }

    public TrWordDetails(final String word,  EnWordDetails enWordDetails){
        this.trWord = word;
        this.enWordDetails = enWordDetails;
    }
}