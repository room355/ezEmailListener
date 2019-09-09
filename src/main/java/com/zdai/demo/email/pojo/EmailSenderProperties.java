package com.zdai.demo.email.pojo;

public class EmailSenderProperties {

    private String username;

    private String password;

    private String host;

    private String mailbox;

    private String pollRate;

    public EmailSenderProperties(String username, String password, String host, String mailbox, String pollRate) {
        this.username = username;
        this.password = password;
        this.host = host;
        this.mailbox = mailbox;
        this.pollRate = pollRate;
    }

    public String getPollRate() {
        return pollRate;
    }

    public String getImapUrl() {
        return String.format("imaps://%s:%s@%s/%s",
                this.username,
                this.password,
                this.host,
                this.mailbox);
    }
}
