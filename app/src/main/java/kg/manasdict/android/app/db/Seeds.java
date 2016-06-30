package kg.manasdict.android.app.db;

import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kg.manasdict.android.app.db.dao.WordDetailsDao;
import kg.manasdict.android.app.db.model.WordDetails;

/**
 * Created by root on 4/2/16.
 */
public class Seeds {

    private static List<WordDetails> seeds;

    public static void install() throws SQLException {
        WordDetailsDao dao = HelperFactory.getHelper().getWordDetailsDao();
        setSeeds();

        for (WordDetails wordDetails : seeds) {
            dao.createIfNotExists(wordDetails);
        }

        test();
    }

    protected static void setSeeds() {
        seeds = new ArrayList<>();
        seeds.add(new WordDetails("жаны", "новый", "new", "yeni"));
        seeds.add(new WordDetails("макул", "хорошо", "ok", "tamam"));
        seeds.add(new WordDetails("студент", "студент", "student", "ogrenci"));
        seeds.add(new WordDetails("мугалим", "учитель", "teacher", "ogretmen"));
        seeds.add(new WordDetails("акча", "деньги", "money", "para"));
        seeds.add(new WordDetails("унаа", "машина", "car", "araba"));
        seeds.add(new WordDetails("алма", "яблоко", "apple", "elma"));
        seeds.add(new WordDetails("компьютер", "компьютер", "computer", "bilgisayar"));
       seeds.add(new WordDetails("асман", "небо", "sky", "gokyuzu"));

    }

    protected static void test() throws SQLException {
        WordDetailsDao dao = HelperFactory.getHelper().getWordDetailsDao();

        List<WordDetails> wordDetails = dao.findAll();

        for (WordDetails item : wordDetails) {
            Log.d(Seeds.class.getName(), item.toString());
        }
    }
}
