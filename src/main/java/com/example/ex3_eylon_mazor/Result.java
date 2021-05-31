package com.example.ex3_eylon_mazor;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * this servlet responsible for show the result. any time that the user refresh the page - the result will update.
 * first of all - check the user, and valid that he's not a new user (if he is - send him to main page)
 * after that - get all the relevant data from the DB and return a response to the user.
 */
@WebServlet(name = "Result", value = "/Result")
public class Result extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer id = null;
        if(request.getSession().getAttribute("userId") instanceof Integer)
            id = (Integer) request.getSession().getAttribute("userId");

        else { //if the user get here directly - send him to main page
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("firstPage.html");
            requestDispatcher.include(request,response);
            return;
        }

        FullDataBase db;
        RequestDataBase rdb;

        if(!(getServletContext().getAttribute("fullDB") instanceof FullDataBase)){
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.print("<html><head><h2>Data Base Error!</h2></head><body>"+
                    "Its seems to be a problem with the our DB, please restart the server");
            out.close();
            return;
        }
        db = (FullDataBase) getServletContext().getAttribute("fullDB");
        rdb = db.getUserById(id);

        //create html response
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.print("<html><head><h2>Crawling " + rdb.getUrl() + "</h2></head><body>"+
                "Found " + rdb.getCounter() + "  images!<br/>");

        if(rdb.checkUserProcess())
            out.print("still crawling...<a href=\"/Result\">reload this page</a> for final result!<br/>");
        else
            out.print("<b>Done!</b><br/>");

        out.print("<a href=\".\">back to main page</a></body></html>");
        out.close();
    }
}
