package crawler.controller;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.gson.Gson;
import crawler.entity.CrawlerSource;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class AddQueue extends HttpServlet {
    private static Queue q = QueueFactory.getQueue("queue-article");
    private static final Logger LOGGER = Logger.getLogger(AddQueue.class.getSimpleName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<CrawlerSource> crawlerSources = ofy().load().type(CrawlerSource.class).list();
        if (crawlerSources.size() == 0)
            return;

        for (CrawlerSource source : crawlerSources) {
            Document document = Jsoup.connect(source.getUrl()).ignoreContentType(true).get();

            Elements elements = document.select(source.getLinkSelector());
            for (Element el : elements) {
                String linkHref = el.attr("abs:href");
//                nature link
                int lastPos;
                if(linkHref.indexOf("?") > 0) {
                    lastPos = linkHref.indexOf("?");
                } else if (linkHref.indexOf("&") > 0){
                    lastPos = linkHref.indexOf("&");
                }
                else if (linkHref.indexOf("#") > 0){
                    lastPos = linkHref.indexOf("#");
                }
                else lastPos = -1;

                if(lastPos != -1)
                    linkHref = linkHref.substring(0, lastPos);

                LOGGER.info(linkHref);
                HashMap<String, Object> hashMapQueue = new HashMap<>();
                hashMapQueue.put("link", linkHref);
                hashMapQueue.put("sourceId", source.getId());
                q.add(TaskOptions.Builder.withMethod(TaskOptions.Method.PULL).payload(new Gson().toJson(hashMapQueue)));
            }
            resp.getWriter().println("get xong link");
        }

    }

}

