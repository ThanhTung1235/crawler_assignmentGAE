package crawler.api;

import com.google.gson.Gson;
import com.googlecode.objectify.Key;
import crawler.entity.Account;
import crawler.entity.Category;
import crawler.entity.JsonObjApi;
import crawler.utility.EncryptString;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class AuthAPI extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String content = EncryptString.convertInputStreamToString(req.getInputStream());
        System.out.println(content);
        Account account = new Gson().fromJson(content, Account.class);
        Key<Account> productKey = ofy().save().entity(account).now();
        resp.getWriter().println(new JsonObjApi().setStatus(HttpServletResponse.SC_CREATED)
                .setMessage("Create success")
                .setData(productKey)
                .toJsonString());
    }
}
