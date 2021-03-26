package de.nerdis;

import de.nerdis.apis.Config;
import de.nerdis.apis.TwitterUser;
import de.nerdis.apis.WebPageScreenshotTaker;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws TwitterException {

        // Load configurations
        Config config = new Config(".TwitterScreenshotBotCredentials", ".TwitterScreenshotBotUserList");

        // Setup Twitter API Access
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(config.getCredentials()[0])
                .setOAuthConsumerSecret(config.getCredentials()[1])
                .setOAuthAccessToken(config.getCredentials()[2])
                .setOAuthAccessTokenSecret(config.getCredentials()[3]);

        TwitterFactory tf = new TwitterFactory(cb.build());
        final Twitter twitter = tf.getInstance();

        WebPageScreenshotTaker webPageScreenshotTaker = new WebPageScreenshotTaker();

        ArrayList<TwitterUser> twitterUserList = new ArrayList<>(Arrays.stream(config.getTwitterUsers()).map(TwitterUser::new).collect(Collectors.toList()));

        for (TwitterUser twitterUser: twitterUserList) {
            List<Status> statusList = twitter.getUserTimeline(twitterUser.getUserName());
            Status lastStatus = statusList.get(0);

            // Check if lastTweetId isn't the same as the latest Tweet Id.
            if(twitterUser.getLastTweetId() != lastStatus.getId()) {
                twitterUser.setLastTweetId(lastStatus.getId());

                String url = "https://twitter.com/" + lastStatus.getUser().getScreenName() + "/status/" + lastStatus.getId();
                File image = webPageScreenshotTaker.capture(url);

                // TODO Implement Image Upload Service
            }
        }

    }


}
