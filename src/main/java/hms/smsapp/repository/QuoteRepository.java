package hms.smsapp.repository;


import java.sql.SQLException;

/**
 * Created by nimila on 7/27/15.
 */
public interface QuoteRepository {
    public abstract boolean addQuote(String message, String number) throws SQLException;
    public abstract String getQuote();
}
