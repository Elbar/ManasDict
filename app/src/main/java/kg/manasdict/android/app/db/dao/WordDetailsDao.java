package kg.manasdict.android.app.db.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

import kg.manasdict.android.app.db.model.WordDetails;

public class WordDetailsDao extends BaseDaoImpl<WordDetails, Integer> {

    public WordDetailsDao(ConnectionSource connectionSource, Class<WordDetails> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<WordDetails> findAll() throws SQLException {
        return queryForAll();
    }
}
