package com.nesstar;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
      try {
         this.requestToken = this.twitter.getOAuthRequestToken();
//         logger.info(requestToken.toString());
      } catch (TwitterException e) {
         logger.error(e.getMessage());
      }
            
   }
   
   public void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
      this.accessToken = loadAccessToken();
      
      req.getSession().setAttribute("requestToken", this.requestToken);
      
      if (null == accessToken) {
         resp.sendRedirect(this.requestToken.getAuthorizationURL());
      }
      else {
         resp.sendRedirect("status.html");
      }
   }
   
   public void getAccessToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
      String oauth_token = request.getParameter("oauthtoken");
      String oauth_verifier = request.getParameter("oauthverifier");
      
      if (null != oauth_token && null != oauth_verifier) {
         try {
            
            this.requestToken = (RequestToken) request.getSession().getAttribute("requestToken");
            this.accessToken = this.twitter.getOAuthAccessToken(this.twitter.getOAuthRequestToken(), oauth_verifier);
            logger.info("save access token: " + this.accessToken.toString());
            
            storeAccessToken();
         } catch (TwitterException e) {
            if (401 == e.getStatusCode()) {
               logger.error("Unable to get the access token!");
            }
            logger.error(e.getMessage());
         }
         
         response.sendRedirect("status.html");
      }
   }

   private void storeAccessToken() {
      Properties properties = new Properties();
      OutputStream out = null;
      try {
         out = new FileOutputStream("src/main/resources/testTwitter4j.properties");
      } catch (FileNotFoundException e1) {
         logger.error(e1.getMessage());
      }
      
      properties.put("oauth.accessToken", accessToken.getToken());
      properties.put("oauth.accessTokenSecret", accessToken.getTokenSecret());
      try {
         properties.store(out, "The configuration of project");
         out.flush();
      } catch (IOException e) {
         logger.error(e.getMessage());
      }
   }
   
   
   private AccessToken loadAccessToken() {
      Properties properties = new Properties();
      InputStream is;
      try {
         is = new FileInputStream("src/main/resources/testTwitter4j.properties");
         properties.load(is);
      } catch (FileNotFoundException e) {
         logger.error(e.getMessage());
      } catch (IOException e) {
         logger.error(e.getMessage());
      }
      
      String token = properties.getProperty("oauth.accessToken");
      String tokenSecret = properties.getProperty("oauth.accessTokenSecret");
      
      if (null == token || null == tokenSecret) {
         return null;
      }
      
      return new AccessToken(token, tokenSecret);
   }
   

   public void showStatus(HttpServletRequest req, HttpServletResponse resp) throws IOException {
      JSONArray jsonList = new JSONArray();
      JSONObject jsonObject = new JSONObject();
      
      List<Status> statuses = null;
      try {
         if (null == this.twitter.getOAuthAccessToken()) {
            this.twitter.setOAuthAccessToken(loadAccessToken());
         }
         statuses = this.twitter.getHomeTimeline();
      } catch (TwitterException e) {
         logger.error(e.getMessage());
      }
      logger.info("Showing home timeline.");
      
      for (Status status : statuses) {
         jsonObject.element("name", status.getUser().getName());
         jsonObject.element("text", status.getText());
         jsonList.add(jsonObject);
      }
      
      resp.getWriter().print(jsonList);
   }
}
