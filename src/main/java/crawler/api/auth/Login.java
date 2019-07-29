package crawler.api.auth;

import com.google.gson.Gson;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import crawler.entity.Account;
import crawler.entity.Credential;
import crawler.entity.JsonObjApi;
import crawler.utility.EncryptString;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class Login extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        Account accountExist = ofy().load().type(Account.class).id(account.getUsername()).now();
        try {
            if (accountExist == null && accountExist.getStatus() != 1) {
                resp.getWriter().println(new Gson().toJson(new JsonObjApi().setStatus(HttpServletResponse.SC_UNAUTHORIZED)
                        .setMessage("Account does not exist")
                        .toJsonString()));
                return;
            } else {
                String passwordEncrypted = EncryptString.hashPassword(account.getPassword()) + accountExist.getSalt();
                if (accountExist.getPassword().equals(passwordEncrypted)) {
                    generateToken(accountExist, resp);
                } else resp.getWriter().println(new JsonObjApi().setStatus(HttpServletResponse.SC_UNAUTHORIZED)
                        .setMessage("Login failure!")
                        .toJsonString());

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void generateToken(Account accountExist, HttpServletResponse resp) throws IOException {
        String token = "Bear " + EncryptString.generateToken();
        Credential credential = new Credential();
        credential.setToken(token);
        credential.setSecretToken(token);
        credential.setAccountId(accountExist.getUsername());
        ofy().save().entity(credential).now();
        resp.getWriter().println(new JsonObjApi().setStatus(HttpServletResponse.SC_OK)
                .setMessage(" ")
                .setData(credential)
                .toJsonString());
    }
}
