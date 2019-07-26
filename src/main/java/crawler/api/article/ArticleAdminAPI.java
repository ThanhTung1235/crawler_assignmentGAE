package crawler.api.article;

import com.google.gson.Gson;
import com.googlecode.objectify.Key;
import crawler.entity.Article;
import crawler.entity.JsonObjApi;
import crawler.utility.EncryptString;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class ArticleAPI extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        List<Article> articles = ofy().load().type(Article.class).list();
        resp.getWriter().println(new JsonObjApi().setStatus(HttpServletResponse.SC_OK)
                .setMessage(" ")
                .setData(articles)
                .toJsonString());
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String content = EncryptString.convertInputStreamToString(req.getInputStream());
        Article updateArticle = new Gson().fromJson(content, Article.class);
        Article existArticle = ofy().load().type(Article.class).id(updateArticle.getLink()).now();
        if (existArticle == null) {
            resp.getWriter().print(new JsonObjApi()
                    .setMessage("Article not found")
                    .setStatus(HttpServletResponse.SC_NOT_FOUND)
                    .toJsonString());
            return;
        }
        existArticle.setStatus(Article.Status.findByValue(updateArticle.getStatus()))
                .setLink(updateArticle.getLink())
                .setCategoryId(updateArticle.getCategoryId())
                .setTitle(updateArticle.getTitle())
                .setContent(updateArticle.getContent())
                .setThumbnail(updateArticle.getThumbnail())
                .setAuthor(updateArticle.getAuthor())
                .setDescription(updateArticle.getDescription())
                .setSourceId(updateArticle.getSourceId())
                .setCreatedAtMLS(updateArticle.getCreatedAtMLS())
                .setUpdatedAtMLS(EncryptString.getCurrentTime());


        ofy().save().entity(existArticle).now();
        resp.getWriter().print(new Gson().toJson(new JsonObjApi()
                .setMessage("Update success")
                .setStatus(HttpServletResponse.SC_OK)
                .setData(existArticle)));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String id = req.getParameter("id");
        Article existArticle = ofy().load().type(Article.class).id(id).now();
        if (existArticle == null) {
            resp.getWriter().println(new JsonObjApi()
                    .setMessage("Article not found")
                    .setStatus(HttpServletResponse.SC_NOT_FOUND)
                    .toJsonString());
            return;
        }
        existArticle.setStatus(Article.Status.DELETED).setDeletedAtMLS(EncryptString.getCurrentTime());
        Key<Article> key = ofy().save().entity(existArticle).now();
        resp.getWriter().println(new JsonObjApi()
                .setMessage("Article is delete")
                .setStatus(HttpServletResponse.SC_OK)
                .setData(key)
                .toJsonString());
    }
}
