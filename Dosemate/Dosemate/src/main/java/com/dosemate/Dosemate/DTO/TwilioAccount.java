package com.dosemate.Dosemate.DTO;

public class TwilioAccount {
    public String accountSid;
    public String apiKeySid;
    public String apiKeySecret;
    public String fromNumber;

    public TwilioAccount(String sid, String key, String secret, String from) {
        this.accountSid = sid;
        this.apiKeySid = key;
        this.apiKeySecret = secret;
        this.fromNumber = from;
    }
}

