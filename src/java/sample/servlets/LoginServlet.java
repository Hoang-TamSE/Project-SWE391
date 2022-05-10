/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import sample.user.UserDAO;
import sample.utils.VerifyRecaptcha;

/**
 *
 * @author Hoang Tam
 */
public class LoginServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(LoginServlet.class);
    private static final String ERROR = "invalid.html";
    private static final String SUCCESS = "search.html";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String userID = request.getParameter("userID");
            String password = request.getParameter("password");
            String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
            if (userID.isEmpty() || password.isEmpty()) {
                LOGGER.warn("User not fill in userID or Password!");
            } else {
                boolean verify = VerifyRecaptcha.verify(gRecaptchaResponse);
                UserDAO dao = new UserDAO();
                boolean check = dao.checkLogin(userID, password);
                if (check && verify) {
                    url = SUCCESS;
                    LOGGER.info("Login success!");
                } else {
                    LOGGER.warn("User not found!");
                    
                }
            }
        } catch (Exception e) {
            LOGGER.error(e);
        } finally {
            response.sendRedirect(url);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
