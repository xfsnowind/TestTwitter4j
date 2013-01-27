package com.nesstar;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;

public class TestTwitter4jServlet extends HttpServlet{

   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   
   private static final Logger logger = Logger.getLogger(TestTwitter4jServlet.class);
   
   private static final String CALLBACK_URL = "http://testtwitter4j.com";
   
   private static final String RESOURCE_URL = "https://api.twitter.com/oauth/request_token";

   @Override
   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      HttpClient client = new DefaultHttpClient(); 
      try {
         HttpGet httpGet = new HttpGet(RESOURCE_URL + "?oauth_callback=" + CALLBACK_URL);
         
         HttpResponse response = client.execute(httpGet);
         
         HttpEntity entity = response.getEntity();
         
         if (null != entity) {
            InputStream instream = entity.getContent();
            logger.info(entity.toString());
            try {
               instream.read();
               // do something useful with the response
           } catch (IOException ex) {
               // In case of an IOException the connection will be released
               // back to the connection manager automatically
               throw ex;
           } catch (RuntimeException ex) {
               // In case of an unexpected exception you may want to abort
               // the HTTP request in order to shut down the underlying
               // connection immediately.
               httpGet.abort();
               throw ex;
           } finally {
               // Closing the input stream will trigger connection release
               try { instream.close(); } catch (Exception ignore) {}
           }
         }
      } finally {
         client.getConnectionManager().shutdown();
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
