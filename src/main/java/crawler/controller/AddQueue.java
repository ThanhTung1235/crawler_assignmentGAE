package crawler.controller;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddQueue extends HttpServlet {
    private static Queue q = QueueFactory.getQueue("queue-green");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String url = "https://vnexpress.net/";
        Document document = Jsoup.connect(url).ignoreContentType(true).get();

        Elements elements = document.select("section>article.list_news");
        for (Element el : elements) {
            String link = el.select(".title_news a[title]").attr("href");
            q.add(TaskOptions.Builder.withMethod(TaskOptions.Method.PULL).payload(link));
        }
        resp.getWriter().println("get xong link");
    }

}

