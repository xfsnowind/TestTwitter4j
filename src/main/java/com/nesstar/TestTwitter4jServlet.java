package com.nesstar;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class TestTwitter4jServlet extends HttpServlet{

   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   
   private static final Logger logger = Logger.getLogger(TestTwitter4jServlet.class);
   
   @Override
   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      Twitter twitter = TwitterFactory.getSingleton();
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

   @Override
   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      // TODO Auto-generated method stub
      super.doPost(req, resp);
   }

   @Override
   protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      logger.info("test service");
      super.service(req, resp);
   }

}
