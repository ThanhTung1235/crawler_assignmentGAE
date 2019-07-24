package crawler.api;

import com.google.gson.Gson;
import com.googlecode.objectify.Key;
import crawler.utility.EncryptString;
import crawler.entity.Category;
import crawler.entity.JsonObjApi;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

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
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }
}
