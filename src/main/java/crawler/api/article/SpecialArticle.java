package crawler.api.article;

import com.google.gson.Gson;
import crawler.entity.Article;
import crawler.entity.JsonObjApi;
import crawler.utility.EncryptString;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SpecialArticle extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String contentString = EncryptString.convertInputStreamToString(req.getInputStream());

        JSONObject jsonObject = new JSONObject(contentString);
        String url = jsonObject.getString("url");
        String titleSelector = jsonObject.getString("titleSelector");
        String descriptionSelector = jsonObject.getString("descriptionSelector");
        String contentSelector = jsonObject.getString("contentSelector");
        String thumbnailSelector = jsonObject.getString("thumbnailSelector");
        String categorySelector = jsonObject.getString("categorySelector");
        String status = jsonObject.getString("status");

        Document document = Jsoup.connect(url).ignoreContentType(true).get();
        Article article = new Article();
        article.setTitle(document.select(titleSelector).text());
        article.setDescription(document.select(descriptionSelector).text());
        article.setContent(document.select(contentSelector).text());
        article.setAuthor("");
        article.setCategoryId(Long.parseLong(categorySelector));
        article.setStatus(Article.Status.findByValue(Integer.parseInt(status)));
        Elements elements = document.select(thumbnailSelector);
        List<String> images = new ArrayList<>();
        for (Element element : elements) {
            images.add(element.attr("src"));
            article.setThumbnail(images);
        }


        resp.getWriter().println(new JsonObjApi().setStatus(HttpServletResponse.SC_OK)
                .setMessage(" ")
                .setData(article)
                .toJsonString());
    }
}
