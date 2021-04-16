package de.nerdis;

import de.nerdis.apis.Config;
import de.nerdis.apis.TwitterUser;
import de.nerdis.apis.WebPageScreenshotTaker;
import org.apache.commons.io.FileUtils;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

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

        // Initiate WebPageScreenshotTaker
        WebPageScreenshotTaker webPageScreenshotTaker = new WebPageScreenshotTaker();

        // Map TwitterUser to ArrayList
        ArrayList<TwitterUser> twitterUserList = Arrays.stream(config.getTwitterUsers()).map(TwitterUser::new).collect(Collectors.toCollection(ArrayList::new));

        if(twitterUserList.size() >= 180) {
            System.err.println("Too many Users, only 180 are allowed.");
            System.exit(1);
        }

        // Run every 2 Minutes
        ScheduledExecutorService execService = Executors.newScheduledThreadPool(1);
        execService.scheduleAtFixedRate(() -> {
            for (TwitterUser twitterUser : twitterUserList) {
                List<Status> statusList = null;
                try {
                    statusList = twitter.getUserTimeline(twitterUser.getUserName());
                } catch (TwitterException e) {
                    e.printStackTrace();
                }
                Status lastStatus = statusList.get(0);

                // Check if lastTweetId isn't the same as the latest Tweet Id.
                if (twitterUser.getLastTweetId() != lastStatus.getId()) {
                    twitterUser.setLastTweetId(lastStatus.getId());

                    String url = "https://twitter.com/" + lastStatus.getUser().getScreenName() + "/status/" + lastStatus.getId();

                    // Take Image
                    File image = webPageScreenshotTaker.capture(url);
                    try {
                        FileUtils.copyFile(image, new File(lastStatus.getUser().getScreenName() + "_" + lastStatus.getId() + ".png"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // TODO Implement Image Upload Service
                }
            }
        }, 0L, 5L, TimeUnit.SECONDS);

    }

}
