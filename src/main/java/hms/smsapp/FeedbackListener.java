package hms.smsapp;

import hms.kite.samples.api.SdpException;
import hms.kite.samples.api.sms.MoSmsListener;
import hms.kite.samples.api.sms.SmsRequestSender;
import hms.kite.samples.api.sms.messages.MoSmsReq;
import hms.kite.samples.api.sms.messages.MtSmsReq;
import hms.kite.samples.api.sms.messages.MtSmsResp;
import hms.smsapp.repository.implementation.QuoteRepositoryImpl;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nimila on 7/27/15.
 */
public class FeedbackListener implements MoSmsListener {
    private String message;
    private String number;
    @Override
    public void init() {

    }

    @Override
    public void onReceivedSms(MoSmsReq moSmsReq) {
        this.message = moSmsReq.getMessage();
        this.number = moSmsReq.getSourceAddress();

        try {
            sendResponse(moSmsReq);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void sendResponse(MoSmsReq moSmsReq) throws SQLException {
        try {

            MtSmsReq mtSmsReq;
            SmsRequestSender smsRequestSender = new SmsRequestSender(new URL("http://localhost:7000/sms/send"));

            mtSmsReq = createSimpleMtSms(moSmsReq);
            mtSmsReq.setApplicationId(moSmsReq.getApplicationId());
            mtSmsReq.setPassword("password");


            MtSmsResp mtSmsResp = smsRequestSender.sendSmsRequest(mtSmsReq);

        } catch (SdpException e) {
            System.err.println("Error " + e);
        } catch (MalformedURLException e) {
            System.err.println("Error " + e);
        }
    }

    private MtSmsReq createSimpleMtSms(MoSmsReq moSmsReq) throws SQLException {
        MtSmsReq mtSmsReq = new MtSmsReq();

        QuoteRepositoryImpl quoteRepository = new QuoteRepositoryImpl();

        if(message.toLowerCase().equals("quote")){
            mtSmsReq.setMessage(quoteRepository.getQuote());
        } else if(message.contains("-")) {
            if(!quoteRepository.addQuote(message, number)){
                mtSmsReq.setMessage("Quote added. Thank you for the contribution.");
            } else {
                mtSmsReq.setMessage("Failure. Please try again.");
            }

        } else {
            mtSmsReq.setMessage("SMS not valid. Please send \"Quote\" to get quote of the day. Or add a quote \"Quote\"-\"Author\". Thank you.");
        }

        List<String> addressList = new ArrayList<String>();
        addressList.add(moSmsReq.getSourceAddress());
        mtSmsReq.setDestinationAddresses(addressList);

        return mtSmsReq;
    }
}
