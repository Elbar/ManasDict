package kg.manasdict.android.app.db.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
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

    public WordDetails findByKgWord(String s) throws SQLException {
        QueryBuilder<WordDetails, Integer> queryBuilder = queryBuilder();
        queryBuilder.where().eq("kg_word", s);
        PreparedQuery<WordDetails> preparedQuery = queryBuilder.prepare();

        return queryForFirst(preparedQuery);
    }

    public WordDetails findByRuWord(String s) throws SQLException {
        QueryBuilder<WordDetails, Integer> queryBuilder = queryBuilder();
        queryBuilder.where().eq("ru_word", s);
        PreparedQuery<WordDetails> preparedQuery = queryBuilder.prepare();

        return queryForFirst(preparedQuery);
    }

    public WordDetails findByEnWord(String s) throws SQLException {
        QueryBuilder<WordDetails, Integer> queryBuilder = queryBuilder();
        queryBuilder.where().eq("en_word", s);
        PreparedQuery<WordDetails> preparedQuery = queryBuilder.prepare();

        return queryForFirst(preparedQuery);
    }

    public WordDetails findByTrWord(String s) throws SQLException {
        QueryBuilder<WordDetails, Integer> queryBuilder = queryBuilder();
        queryBuilder.where().eq("tr_word", s);
        PreparedQuery<WordDetails> preparedQuery = queryBuilder.prepare();

        return queryForFirst(preparedQuery);
    }
}
