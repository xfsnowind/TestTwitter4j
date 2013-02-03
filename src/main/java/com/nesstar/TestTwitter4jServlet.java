package com.nesstar;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class TestTwitter4jServlet extends HttpServlet{

   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   
   private static final Logger logger = Logger.getLogger(TestTwitter4jServlet.class);
   
   
   @Override
   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      String operation = req.getParameter("operation");
      
      TwitterDAO twitterDAO = (TwitterDAO)req.getSession().getAttribute("twitter");
      
      logger.info("The operation: " + operation);
      
      if (null != operation) {
         if ("showstatus".equals(operation)) {
            twitterDAO.showStatus(req, resp);
         }
      }
   }

   @Override
   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      String operation = req.getParameter("operation");
      
      TwitterDAO twitterDAO = (TwitterDAO)req.getSession().getAttribute("twitter");
      
      logger.info("The operation: " + operation);
      
      if (null != operation) {
         if ("login".equals(operation)) {
            twitterDAO.login(req, resp);
         } else if ("getAccessToken".equals(operation)) {
            twitterDAO.getAccessToken(req, resp);
         }
      }
   }

   @Override
   protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      if (null == request.getSession().getAttribute("twitter")) {
         request.getSession().setAttribute("twitter", new TwitterDAO());
      }
      
      // set charset for the page
      response.setContentType("text/html;charset=utf-8");


      if ("POST".equals(request.getMethod())) {
         doPost(request, response);
      }

      if ("GET".equals(request.getMethod())) {
         doGet(request, response);
      }
   }

}
