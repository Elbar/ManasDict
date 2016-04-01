package kg.manasdict.android.app.data;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * Created by fukuro on 4/1/16.
 */
public class RuWordDetails implements Serializable {

    public static final String ID_FIELD = "ruword_id";

    @DatabaseField(generatedId = true, columnName = ID_FIELD )
    public int ruWordId;

    @DatabaseField(columnName = "ru_word")
    public String ruWord;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    public KgWordDetails kgWordDetails;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    public EnWordDetails enWordDetails;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    public TrWordDetails trWordDetails;

    public RuWordDetails() {
    }

    public RuWordDetails(final String word) {
        this.ruWord = word;
    }

    public RuWordDetails(final String word,  KgWordDetails kgWordDetails){
        this.ruWord = word;
        this.kgWordDetails = kgWordDetails;
    }

    public RuWordDetails(final String word,  EnWordDetails enWordDetails) {
        this.ruWord = word;
        this.enWordDetails = enWordDetails;
    }

    public RuWordDetails(final String word,  TrWordDetails trWordDetails){
        this.ruWord = word;
        this.trWordDetails = trWordDetails;
    }
}
