# Java-Web-crawler

Name: Eylon Mazor

IDE: IntelliJ 

In this exercise we have asked to build a Web Crawling process. The user need to insert a valid URL - and the crawler crawling this url and all his links (can change the depth of the crawling (in recursion) in web.xml).

My Java classes:
Class FullDataBase- Responsible for memory management. hold a synchronize map that save all the data of all the users
Class RequestDataBase- Responsible for memory management of each user crawling request. crate a main thread that start to crawling each link in given url.
Class MyThread- Responsible for check the url that given and create another threads that continue to crawling the inside urls.

My Java Servlets Listeners:
AppContextListener- initiate the DB before anything.

My java Servlets:
Servlet - MainServlet- Welcome Servlets - send the user to firstPage.html
Servlet - StartCrawl - Responsible for stating the crawling process after validation of the given url. save the user ID in his session
Servlet - Result - Responsible for showing the most update result for each user.
