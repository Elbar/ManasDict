package kg.manasdict.android.app.db.dao;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import java.sql.SQLException;
import kg.manasdict.android.app.db.model.WordDetails;


public class WordDao  extends BaseDaoImpl<WordDetails, Integer>{

    public WordDao(Class<WordDetails> dataClass) throws SQLException {
        super(dataClass);
    }


}
