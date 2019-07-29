package crawler.entity;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.Calendar;

@Entity
public class Credential {
    @Id
    private String token;
    @Index
    private String secretToken;
    @Index
    private String AccountId;
    @Index
    private long createdAtMLS;
    @Index
    private long updatedAtMLS;

    public Credential() {
        this.createdAtMLS = Calendar.getInstance().getTimeInMillis();
        this.updatedAtMLS = Calendar.getInstance().getTimeInMillis();
    }

    public String getToken() {
        return token;
    }

    public Credential setToken(String token) {
        this.token = token;
        return this;
    }

    public String getSecretToken() {
        return secretToken;
    }

    public Credential setSecretToken(String secretToken) {
        this.secretToken = secretToken;
        return this;
    }

    public String getAccountId() {
        return AccountId;
    }

    public Credential setAccountId(String accountId) {
        AccountId = accountId;
        return this;
    }

    public long getCreatedAtMLS() {
        return createdAtMLS;
    }

    public Credential setCreatedAtMLS(long createdAtMLS) {
        this.createdAtMLS = createdAtMLS;
        return this;
    }

    public long getUpdatedAtMLS() {
        return updatedAtMLS;
    }

    public Credential setUpdatedAtMLS(long updatedAtMLS) {
        this.updatedAtMLS = updatedAtMLS;
        return this;
    }
}
