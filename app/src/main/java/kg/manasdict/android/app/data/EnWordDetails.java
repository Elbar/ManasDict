package kg.manasdict.android.app.data;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * Created by fukuro on 4/1/16.
 */
public class EnWordDetails implements Serializable {

    public static final String ID_FIELD = "enword_id";

    @DatabaseField(generatedId = true, columnName = ID_FIELD)
    public int enWordId;

    @DatabaseField(columnName = "en_word")
    public String enWord;


    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    public RuWordDetails ruWordDetails;


    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    public KgWordDetails kgWordDetails;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    public TrWordDetails trWordDetails;

    public EnWordDetails() {
    }

    public EnWordDetails(final String word) {
        this.enWord = word;
    }

    public EnWordDetails(final String word,  RuWordDetails ruWordDetails){
        this.enWord = word;
        this.ruWordDetails =  ruWordDetails;
    }

    public EnWordDetails(final String word,  KgWordDetails kgWordDetails){
        this.enWord = word;
        this.kgWordDetails =  kgWordDetails;
    }

    public EnWordDetails(final String word,  TrWordDetails trWordDetails){
        this.enWord = word;
        this.trWordDetails = trWordDetails;
    }



}


