package hms.smsapp.repository.implementation;

import hms.smsapp.domain.Quote;
import hms.smsapp.repository.QuoteRepository;
import hms.smsapp.repository.data.DBConnManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

/**
 * Created by nimila on 7/27/15.
 */
public class QuoteRepositoryImpl implements QuoteRepository {
    private Quote quote = null;
    private Connection connection = null;
    private PreparedStatement statement = null;
    private static final String ADD_QUOTE = "INSERT INTO quote (quoteID, quoteText, quoteAuthor, senderNo) VALUES (?,?,?,?)";
    private static final String GET_QUOTE = "SELECT * FROM quote";

    public QuoteRepositoryImpl() {
        connection = DBConnManager.getMySQLConnection();
    }

    @Override
    public boolean addQuote(String message, String number) throws SQLException {
        this.quote = buildQuote(message, number);

        statement = connection.prepareStatement(ADD_QUOTE);
        statement.setString(1, quote.getQuoteID());
        statement.setString(2, quote.getQuoteText());
        statement.setString(3, quote.getQuoteAuthor());
        statement.setString(4, quote.getSenderNo());

        this.quote = null;

        return statement.execute();
    }

    private Quote buildQuote(String message, String number) {
        String arr[] = message.split("-");

        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        String quoteID = dateFormat.format(date);

        String arr2[] = number.split(":");

        return quote = new Quote(quoteID, arr[0], arr[1], arr2[1]);
    }

    @Override
    public String getQuote() {
        this.quote = null;
        String todaysQuote = "";
        ArrayList<Quote> quotes = new ArrayList<Quote>();

        try {
            statement = connection.prepareStatement(GET_QUOTE);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                quote = new Quote(resultSet.getString("quoteID"),
                        resultSet.getString("quoteText"),
                        resultSet.getString("quoteAuthor"),
                        resultSet.getString("senderNo"));
                quotes.add(quote);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        int element = (int) Math.floor(Math.random() * quotes.size());
        todaysQuote = quotes.get(element).getQuoteText() + " - " + quotes.get(element).getQuoteAuthor();

        return todaysQuote;
    }
}
