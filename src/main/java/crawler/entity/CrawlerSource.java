package crawler.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import crawler.utility.EncryptString;

import java.util.Calendar;

@Entity
public class CrawlerSource {
    @Id
    private long id;
    private String url;
    private String linkSelector; //  .list_news a
    private String titleSelector;
    private String descriptionSelector;
    private String contentSelector;
    private String authorSelector;
    private String thumbnailSelector;
    private String CreatedAt;
    private String UpdatedAt;
    private String DeleteAt;
    private int status;

    public CrawlerSource() {
        this.id = Calendar.getInstance().getTimeInMillis();
        this.CreatedAt = EncryptString.getCurrentTime();
        this.UpdatedAt = EncryptString.getCurrentTime();
        this.status = 1;
    }

    public String getDescriptionSelector() {
        return descriptionSelector;
    }

    public long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getLinkSelector() {
        return linkSelector;
    }

    public String getTitleSelector() {
        return titleSelector;
    }

    public String getContentSelector() {
        return contentSelector;
    }

    public String getAuthorSelector() {
        return authorSelector;
    }

    public int getStatus() {
        return status;
    }

    public String getCreatedAt() {
        return CreatedAt;
    }


    public String getUpdatedAt() {
        return UpdatedAt;
    }

    public String getThumbnailSelector() {
        return thumbnailSelector;
    }

    public String getDeleteAt() {
        return DeleteAt;
    }

    public static final class CrawlerSourceBuilder {
        private String url;
        private String linkSelector; //  .list_news a
        private String titleSelector;
        private String descriptionSelector;
        private String contentSelector;
        private String authorSelector;
        private String thumbnailSelector;
        private String DeleteAt;
        private int status;

        private CrawlerSourceBuilder() {
        }

        public static CrawlerSourceBuilder aCrawlerSource() {
            return new CrawlerSourceBuilder();
        }

        public CrawlerSourceBuilder setUrl(String url) {
            this.url = url;
            return this;
        }

        public CrawlerSourceBuilder setLinkSelector(String linkSelector) {
            this.linkSelector = linkSelector;
            return this;
        }

        public CrawlerSourceBuilder setTitleSelector(String titleSelector) {
            this.titleSelector = titleSelector;
            return this;
        }

        public CrawlerSourceBuilder setDescriptionSelector(String descriptionSelector) {
            this.descriptionSelector = descriptionSelector;
            return this;
        }

        public CrawlerSourceBuilder setContentSelector(String contentSelector) {
            this.contentSelector = contentSelector;
            return this;
        }

        public CrawlerSourceBuilder setAuthorSelector(String authorSelector) {
            this.authorSelector = authorSelector;
            return this;
        }

        public CrawlerSourceBuilder setThumbnailSelector(String thumbnailSelector) {
            this.thumbnailSelector = thumbnailSelector;
            return this;
        }

        public CrawlerSourceBuilder setDeleteAt(String DeleteAt) {
            this.DeleteAt = DeleteAt;
            return this;
        }

        public CrawlerSourceBuilder setStatus(int status) {
            this.status = status;
            return this;
        }

        public CrawlerSource build() {
            CrawlerSource crawlerSource = new CrawlerSource();
            crawlerSource.authorSelector = this.authorSelector;
            crawlerSource.titleSelector = this.titleSelector;
            crawlerSource.url = this.url;
            crawlerSource.status = this.status;
            crawlerSource.thumbnailSelector = this.thumbnailSelector;
            crawlerSource.DeleteAt = this.DeleteAt;
            crawlerSource.linkSelector = this.linkSelector;
            crawlerSource.contentSelector = this.contentSelector;
            crawlerSource.descriptionSelector = this.descriptionSelector;
            return crawlerSource;
        }
    }
}
