package crawler.entity;

import com.google.appengine.repackaged.org.codehaus.jackson.annotate.JsonIgnore;
import com.google.gson.Gson;

import java.util.List;

public class JsonObjApi {
    private int status;
    private String message;
    private Object data;
    @JsonIgnore
    private static Gson gson = new Gson();

    public JsonObjApi() {

    }


    public int getStatus() {
        return status;
    }

    public JsonObjApi setStatus(int status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public JsonObjApi setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public JsonObjApi setData(Object data) {
        this.data = data;
        return this;
    }
    public String toJsonString(){
        return gson.toJson(this);
    }
}
