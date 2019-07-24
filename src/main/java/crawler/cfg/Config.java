package crawler.cfg;

public class Config {
    public static final int MORE = -1;
    public static final int THOI_SU = 1;
    public static final int GIAO_DUC = 2;
    public static final int THE_GIOI = 3;
    public static final int KINH_DOANH = 4;
    public static final int GIAI_TRI = 5;
    public static final int PHAP_LUAT = 6;
    public static final int SUC_KHOE = 7;
    public static final int KHOA_HOC = 8;
    public static final int SO_HOA = 9;
    public static final int XE = 10;
    public static final int THE_THAO = 11;

    public static int detectCategory(String url) {
        String[] links = url.split("/");
        switch (links[3]) {
            case "thoi-su":
                System.out.println("category 1");
                return Config.THOI_SU;
            case "giao-duc":
                System.out.println("category 2");
                return Config.GIAO_DUC;
            case "the-gioi":
                System.out.println("category 3");
                return Config.THE_GIOI;
            case "kinh-doanh":
                System.out.println("category 4");
                return Config.KINH_DOANH;
            case "giai-tri":
                System.out.println("category 5");
                return Config.GIAI_TRI;
            case "phap-luat":
                System.out.println("category 6");
                return Config.PHAP_LUAT;
            case "suc-khoe":
                System.out.println("category 7");
                return Config.SUC_KHOE;
            case "khoa-hoc":
                System.out.println("category 8");
                return Config.KHOA_HOC;
            case "so-hoa":
                System.out.println("category 9");
                return Config.SO_HOA;
            case "oto-xe-may":
                System.out.println("category 10");
                return Config.XE;
            case "the-thao":
                System.out.println("category 11");
                return Config.THE_THAO;
            default:
                System.out.println("category more");
                return Config.MORE;
        }
    }
}
