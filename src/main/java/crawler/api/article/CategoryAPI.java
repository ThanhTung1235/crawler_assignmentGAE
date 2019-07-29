package crawler.api.article;

import com.google.gson.Gson;
import com.googlecode.objectify.Key;
import crawler.entity.Article;
import crawler.utility.EncryptString;
import crawler.entity.Category;
import crawler.entity.JsonObjApi;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class CategoryAPI extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        List<Category> categories = ofy().load().type(Category.class).list();
        resp.getWriter().println(new JsonObjApi().setStatus(HttpServletResponse.SC_OK)
                .setMessage(" ")
                .setData(categories)
                .toJsonString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String content = EncryptString.convertInputStreamToString(req.getInputStream());
        System.out.println(content);
        Category product = new Gson().fromJson(content, Category.class);
        Key<Category> productKey = ofy().save().entity(product).now();
        resp.getWriter().println(new JsonObjApi().setStatus(HttpServletResponse.SC_CREATED)
                .setMessage(" ")
                .setData(productKey)
                .toJsonString());
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String content = EncryptString.convertInputStreamToString(req.getInputStream());
        Category updateCategory = new Gson().fromJson(content, Category.class);
        Category existCategory = ofy().load().type(Category.class).id(updateCategory.getId()).now();
        if (existCategory == null) {
            resp.getWriter().print(new JsonObjApi()
                    .setMessage("Article not found")
                    .setStatus(HttpServletResponse.SC_NOT_FOUND)
                    .toJsonString());
            return;
        }
        existCategory.setStatus(Category.Status.findByValue(updateCategory.getStatus()));
        existCategory.setName(updateCategory.getName());
        existCategory.setDeleteddAt(EncryptString.getCurrentTime());

        ofy().save().entity(existCategory).now();
        resp.getWriter().print(new Gson().toJson(new JsonObjApi()
                .setMessage("Update success")
                .setStatus(HttpServletResponse.SC_OK)
                .setData(existCategory)));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String id = req.getParameter("id");
        Category categoryExist = ofy().load().type(Category.class).id(id).now();
        if (categoryExist == null) {
            resp.getWriter().println(new JsonObjApi()
                    .setMessage("Article not found")
                    .setStatus(HttpServletResponse.SC_NOT_FOUND)
                    .toJsonString());
            return;
        }
        categoryExist.setStatus(Category.Status.DELETE);
        categoryExist.setDeleteddAt((EncryptString.getCurrentTime()));
        Key<Category> key = ofy().save().entity(categoryExist).now();
        resp.getWriter().println(new JsonObjApi()
                .setMessage("Category is delete")
                .setStatus(HttpServletResponse.SC_OK)
                .setData(key)
                .toJsonString());
    }

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^\b[A-Z0-9._%+-]$", Pattern.CASE_INSENSITIVE);

    public static void main(String[] args) {
        String emailStr = "ung";
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        System.out.println(matcher.find());;
    }
}
