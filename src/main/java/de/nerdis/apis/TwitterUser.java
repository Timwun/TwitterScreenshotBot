package de.nerdis.apis;

public class TwitterUser {

    private String userName;
    private Long lastTweetId = 0L;

    public TwitterUser(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public Long getLastTweetId() {
        return lastTweetId;
    }

    public void setLastTweetId(Long lastTweetId) {
        this.lastTweetId = lastTweetId;
    }
}
