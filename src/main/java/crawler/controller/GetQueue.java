package crawler.controller;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskHandle;
import crawler.cfg.Config;
import crawler.entity.Article;
import crawler.entity.CrawlerSource;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import sun.net.www.content.image.png;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class GetQueue extends HttpServlet {
    private static Queue q = QueueFactory.getQueue("queue-article");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<TaskHandle> tasks = q.leaseTasks(30, TimeUnit.SECONDS, 1);
        if (tasks.size() > 0) {
            TaskHandle task = tasks.get(0);
            String taskContent = new String(task.getPayload());
            JSONObject jsonObject = new JSONObject(taskContent);
            String link = jsonObject.getString("link");
            long sourceId = jsonObject.getLong("sourceId");
            CrawlerSource crawlerSource = ofy().load().type(CrawlerSource.class).id(sourceId).now();
            if (crawlerSource == null) {
                return;
            }
            Document document = Jsoup.connect(link).ignoreContentType(true).get();

            Article article = new Article();
            article.setLink(link);
            article.setTitle(document.select(crawlerSource.getTitleSelector()).text());
            article.setDescription(document.select(crawlerSource.getDescriptionSelector()).text());
            article.setContent(document.select(crawlerSource.getContentSelector()).text());
            article.setAuthor(document.select(crawlerSource.getAuthorSelector()).text());
            article.setCategoryId(Config.detectCategory(link));
            article.setSourceId(crawlerSource.getId());
            Elements elements = document.select(crawlerSource.getThumbnailSelector());
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
//        Elements elements = document.select(".content_detail img[src$=.jpg]");

        Document document = Jsoup.connect("https://vnexpress.net").ignoreContentType(true).get();
        Elements elements = document.select(".list_news h4.title_news a[title]");
        for (Element element : elements) {
            String linkHref = element.attr("abs:href");
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

            System.out.println(linkHref);
        }
    }


}

