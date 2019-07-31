package crawler.api.article;
import com.google.gson.Gson;
import com.googlecode.objectify.Key;
import crawler.entity.Article;
import crawler.entity.Category;
import crawler.entity.CrawlerSource;
import crawler.entity.JsonObjApi;
import crawler.utility.EncryptString;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class SourceAPI extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String content = EncryptString.convertInputStreamToString(req.getInputStream());
        System.out.println(content);
        CrawlerSource source = new Gson().fromJson(content, CrawlerSource.class);
        Key<CrawlerSource> sourceKey = ofy().save().entity(source).now();
        resp.getWriter().println(new JsonObjApi().setStatus(HttpServletResponse.SC_CREATED)
                .setMessage("Save success!")
                .setData(sourceKey)
                .toJsonString());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        List<CrawlerSource> sources = ofy().load().type(CrawlerSource.class).list();
        resp.getWriter().println(new JsonObjApi().setStatus(HttpServletResponse.SC_OK)
                .setMessage(" ")
                .setData(sources)
                .toJsonString());
    }
}
