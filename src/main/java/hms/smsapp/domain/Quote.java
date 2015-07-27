package hms.smsapp.domain;

/**
 * Created by nimila on 7/27/15.
 */
public class Quote {
    private String quoteID;
    private String quoteText;
    private String quoteAuthor;
    private String senderNo;

    public Quote(String quoteID, String quoteText, String quoteAuthor, String senderNo) {
        this.quoteID = quoteID;
        this.quoteText = quoteText;
        this.quoteAuthor = quoteAuthor;
        this.senderNo = senderNo;
    }

    public String getQuoteID() {
        return quoteID;
    }

    public void setQuoteID(String quoteID) {
        this.quoteID = quoteID;
    }

    public String getQuoteText() {
        return quoteText;
    }

    public void setQuoteText(String quoteText) {
        this.quoteText = quoteText;
    }

    public String getQuoteAuthor() {
        return quoteAuthor;
    }

    public void setQuoteAuthor(String quoteAuthor) {
        this.quoteAuthor = quoteAuthor;
    }

    public String getSenderNo() {
        return senderNo;
    }

    public void setSenderNo(String senderNo) {
        this.senderNo = senderNo;
    }
}
