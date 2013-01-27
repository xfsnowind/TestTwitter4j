package com.nesstar;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class TwitterDAO {
   
   private static final Logger logger = Logger.getLogger(TwitterDAO.class);
   
   /**
    * The twitter singleton instance.
    */
   private Twitter twitter;
   
   /**
    * The request token.
    */
   private RequestToken requestToken;
   
   /**
    * The access token.
    */
   private AccessToken accessToken;
   
   public TwitterDAO() {
      this.twitter = TwitterFactory.getSingleton();
   }
   
   public void login(HttpServletRequest req, HttpServletResponse resp) {
      this.twitter.setOAuthAccessToken(StaticData.CONSUMER_KEY, StaticData.CONSUMER_SECRET);
   }

   public void showStatus(HttpServletRequest req, HttpServletResponse resp) {
      this.twitter.setOAuthConsumer(StaticData.CONSUMER_KEY, StaticData.CONSUMER_SECRET);
      
      try {
         this.requestToken = twitter.getOAuthRequestToken();
      } catch (TwitterException e1) {
         logger.error("Failed to get request token");
         logger.error(e1.getMessage());
      }
      
      List<Status> statuses = null;
      try {
         statuses = twitter.getHomeTimeline();
      } catch (TwitterException e) {
         logger.error(e.getMessage());
      }
      logger.info("Showing home timeline.");
      for (Status status : statuses) {
          logger.info(status.getUser().getName() + ":" +
                             status.getText());
      }
   }
}
