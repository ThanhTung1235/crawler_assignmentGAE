package crawler.controller;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskHandle;
import crawler.cfg.Config;
import crawler.entity.Article;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class GetQueue extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Queue q = QueueFactory.getQueue("queue-green");
        List<TaskHandle> tasks = q.leaseTasks(30, TimeUnit.SECONDS, 1);
        if (tasks.size() > 0) {
            TaskHandle task = tasks.get(0);
            String link = new String(task.getPayload());
            Document document = Jsoup.connect(link).ignoreContentType(true).get();

            Article article = new Article();
            article.setLink(link);
            article.setTitle(document.select(".title_news_detail").text());
            article.setDescription(document.select(".sidebar_1 .description").text());
            article.setContent(document.select(".content_detail p.Normal").text());
            article.setAuthor(document.select(".author_mail").text());
            article.setCategoryId(Config.detectCategory(link));
            Elements elements = document.select(".content_detail img");
            List<String> images = new ArrayList<>();
            for (Element element : elements) {
                images.add(element.attr("src"));
                article.setThumbnail(images);
            }
            ofy().save().entity(article).now();

            q.deleteTask(task);
        }
    }

    public static void main(String[] args) throws IOException {
        String url = "https://vnexpress.net/thoi-su";
//        System.out.println(detectCategory(url));;
    }


}

