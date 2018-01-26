package cn.lushantingyue.materialdesign_demo.bean;

/**
 * Created by Lushantingyue on 2017/12/25 16.
 * Responsibilities:
 * Description:
 * ProjectName:
 */

public class Articles {

// "_id": "593a6466667af304b49d4ef5",
// "author": "韩大爷的杂货铺",
// "title": "从现在起，培养五个获益终生的思维习惯。",
// "_abstract": "文/韩大爷的杂货铺导语：平时喜欢写一些干货类的文章，觉得这样能让大家通过阅读获取更多实在的精神收益，长此以往，与读者朋友们的互动也就多了起来。最近，有位读者朋友发来简信问我：...",
// "date": "2016-05-12T19:47:24+08:00",
// "avatar": "//upload.jianshu.io/users/upload_avatars/1767483/6321b54d19be.jpeg?imageMogr2/auto-orient/strip|imageView2/1/w/96/h/96",
// "href": "/p/bf965da0082c",
    private String _id;
    private String author;
    private String title;
    private String _abstract;
    private String date;
    private String avatar;
    private String href;

    public String get_id() {
        return _id;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String get_abstract() {
        return _abstract;
    }

    public String getDate() {
        return date;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getHref() {
        return href;
    }

}
