package crawler.api.article;

import crawler.cfg.Config;
import crawler.entity.Article;
import crawler.entity.JsonObjApi;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class ArticleAPI extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String category = req.getParameter("ct");
        String id = req.getParameter("id");
        if (category != null) {
            int categoryId = Config.detectCategory("///" + category);
            System.out.println(categoryId);
            List<Article> articles = ofy().load().type(Article.class).filter("status", 1).filter("categoryId", categoryId).list();
            resp.getWriter().println(new JsonObjApi().setStatus(HttpServletResponse.SC_OK)
                    .setMessage(" ")
                    .setData(articles)
                    .toJsonString());
        } else if (id != null) {
            Article article = ofy().load().type(Article.class).id(id).now();
            if (article.getStatus() == 0) {
                resp.getWriter().println(new JsonObjApi().setStatus(HttpServletResponse.SC_NOT_FOUND)
                        .setMessage("Article not found")
                        .toJsonString());
                return;
            }
            resp.getWriter().println(new JsonObjApi().setStatus(HttpServletResponse.SC_OK)
                    .setMessage(" ")
                    .setData(article)
                    .toJsonString());

        } else {
            List<Article> articles = ofy().load().type(Article.class).filter("status", 1).list();
            resp.getWriter().println(new JsonObjApi().setStatus(HttpServletResponse.SC_OK)
                    .setMessage(" ")
                    .setData(articles)
                    .toJsonString());
        }


    }
}
