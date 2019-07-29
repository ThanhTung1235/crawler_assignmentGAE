package crawler.api.auth;

import com.google.gson.Gson;
import com.google.gson.internal.bind.util.ISO8601Utils;
import com.googlecode.objectify.Key;
import crawler.entity.Account;
import crawler.entity.Category;
import crawler.entity.JsonObjApi;
import crawler.utility.EncryptString;
import org.w3c.dom.ls.LSOutput;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class Register extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            String content = EncryptString.convertInputStreamToString(req.getInputStream());
            Account account = new Gson().fromJson(content, Account.class);
            if ((account.getUsername().length() == 0 || account.getUsername() == null) || (account.getPassword().length() == 0 || account.getPassword() == null)) {
                resp.getWriter().println(new JsonObjApi().setStatus(HttpServletResponse.SC_FORBIDDEN)
                        .setMessage("Username and password can't be null")
                        .toJsonString());
                return;
            }
            Account acc = ofy().load().type(Account.class).id(account.getUsername()).now();
            if (acc != null) {
                resp.getWriter().println(new JsonObjApi().setStatus(HttpServletResponse.SC_FORBIDDEN)
                        .setMessage("That username is already in use!")
                        .toJsonString());
                return;
            }

            Key<Account> accountKey = ofy().save().entity(new Account()
                    .setUsername(account.getUsername())
                    .encryptPassword(account.getPassword())).now();
            resp.getWriter().println(new JsonObjApi().setStatus(HttpServletResponse.SC_CREATED)
                    .setMessage("Create success!")
                    .setData(accountKey)
                    .toJsonString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
