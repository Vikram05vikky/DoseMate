package com.dosemate.Dosemate.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dosemate.Dosemate.DTO.TwilioAccount;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;


@Service
public class SmsSender {

    private final List<TwilioAccount> twilioAccounts = new ArrayList<>();
    private final int MAX_RETRIES = 3;

    public SmsSender() {
        twilioAccounts.add(new TwilioAccount("AC9f4632fdbea7b0d2d79aa7f592e4c086", "SK171f04b821484f15370f3e58b9805d56", "0ZaaKQln79Przbn37KjBwFFHQcpRyFiH", "+15206524473")); // Primary
        twilioAccounts.add(new TwilioAccount("AC8e2d4fa6e8593ef6f575c8165c05ebb5", "SKafe79e4741aad3d3e0ea94253ed605b2", "DWAPkdIg9EY07ZdmoG5mc9ypBz9BfvIg", "+17178835745"));
        twilioAccounts.add(new TwilioAccount("AC8c9d192b5b159caa7b36c0f68c314cb9", "SK9daadf4e8605fb9106ebc26662fef4a9", "94U1V24FhoeceUeDpaTg00JKxkqc88Rv", "+17752489383"));
//        twilioAccounts.add(new TwilioAccount("SID4", "KEY4", "SECRET4", "+15206524476"));
    }

    public boolean sendSms(String phoneNumber, String message) {
        return trySendWithAccounts(phoneNumber, message, 0);
    }

    private boolean trySendWithAccounts(String phoneNumber, String message, int accountIndex) {
        if (accountIndex >= twilioAccounts.size()) return false;

        TwilioAccount account = twilioAccounts.get(accountIndex);
        Twilio.init(account.apiKeySid, account.apiKeySecret, account.accountSid);

        try {
            // Send MMS (media message) to bypass SMS length limitation
            Message msg = Message.creator(
                    new com.twilio.type.PhoneNumber("+91" + phoneNumber),
                    new com.twilio.type.PhoneNumber(account.fromNumber),
                    message
            ).setMediaUrl(Arrays.asList(
                    URI.create("https://dosemateimagesms.s3.us-east-1.amazonaws.com/ChatGPT+Image+Jul+10%2C+2025%2C+08_46_25+PM.png")
            )).create();

            if (!msg.getStatus().toString().equalsIgnoreCase("failed")) {
                System.out.println("✅ Sent via Twilio Account #" + (accountIndex + 1));
                return true;
            } else {
                System.out.println("⚠️ Failed via Account #" + (accountIndex + 1) + ", trying next...");
                return trySendWithAccounts(phoneNumber, message, accountIndex + 1);
            }
        } catch (Exception e) {
            System.err.println("❌ Error via Account #" + (accountIndex + 1) + ": " + e.getMessage());
            return trySendWithAccounts(phoneNumber, message, accountIndex + 1);
        }
    }
}
