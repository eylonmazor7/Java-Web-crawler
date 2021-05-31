package com.example.ex3_eylon_mazor;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * this servlet responsible for checking the url that the user gave and if is valid -
 * a. give to the request new ID and save the ID in user session.
 * b. create for this user a RequestDataBase.java (rdb) with all the data, and this DB launch the crawling action.
 * c. return a response of basic HTML things, (if the URL not valid - error page, else - start crawling HTML)
 */
@WebServlet(name = "StartCrawl", value = "/StartCrawl")
public class StartCrawl extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = request.getParameter("URL").trim();
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        if(!FullDataBase.checkUrl(url)){
            out.print("<html><head><h2>Error!!!</h2></head><body>"+
                    "Bad Url, <a href=\".\">back to main page</a>.</body></html>");
            out.close();
            return;
        }

        //get reference to full data base
        FullDataBase fdb = null;

        if (getServletContext().getAttribute("fullDB") instanceof FullDataBase){
            fdb = (FullDataBase) getServletContext().getAttribute("fullDB");
        }
        else {
            out.print("<html><head><h2>Error!!!</h2></head><body>"+
                    "Bad Url, <a href=\".\">back to main page</a>.</body></html>");
            out.close();
            return;
        }

        HttpSession session = request.getSession();
        Integer id = null;

        //new user or same one with new search
        synchronized (this) {
            session.setAttribute("userId", ++(AppContextListener.requestCounter));
            id = AppContextListener.requestCounter;
        }

        //its not a critical section because its on stack
        //any request get own rdb object - that save in main DB (fdb)
        RequestDataBase rdb = new RequestDataBase(id,Integer.parseInt(getServletContext().getInitParameter("maxdepth")), url);
        fdb.insertRequest(id, rdb);

        out.print("<html><head><h2>Crawling!!!</h2></head><body>"+
                "Crawling started, visit <a href=\"/Result\">this page</a> to track result");
        out.close();
    }
}
