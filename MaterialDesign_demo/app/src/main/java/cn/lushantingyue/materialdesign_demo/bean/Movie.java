package cn.lushantingyue.materialdesign_demo.bean;

import java.util.List;

/**
 * Created by diyik on 2018/1/11.
 */

public class Movie {

    /**
     * rating : {"max":10,"average":5.3,"stars":"30","min":0}
     * genres : ["剧情","奇幻"]
     * title : 解忧杂货店
     * casts : [{"alt":"https://movie.douban.com/celebrity/1339594/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1503377320.23.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1503377320.23.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1503377320.23.jpg"},"name":"王俊凯","id":"1339594"},{"alt":"https://movie.douban.com/celebrity/1325127/","avatars":{"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1492095415.48.jpg","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1492095415.48.jpg","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1492095415.48.jpg"},"name":"迪丽热巴","id":"1325127"},{"alt":"https://movie.douban.com/celebrity/1330472/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1385100761.5.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1385100761.5.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1385100761.5.jpg"},"name":"董子健","id":"1330472"}]
     * collect_count : 33094
     * original_title : 解忧杂货店
     * subtype : movie
     * directors : [{"alt":"https://movie.douban.com/celebrity/1316056/","avatars":{"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p34888.jpg","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p34888.jpg","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p34888.jpg"},"name":"韩杰","id":"1316056"}]
     * year : 2017
     * images : {"small":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2503544593.jpg","large":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2503544593.jpg","medium":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2503544593.jpg"}
     * alt : https://movie.douban.com/subject/26654146/
     * id : 26654146
     */

    private Movie.RatingBean rating;
    private String title;
    private int collect_count;
    private String original_title;
    private String subtype;
    private String year;
    private Movie.ImagesBean images;
    private String alt;
    private String id;
    private List<String> genres;
    private List<Movie.CastsBean> casts;
    private List<Movie.DirectorsBean> directors;

    public Movie.RatingBean getRating() {
        return rating;
    }

    public void setRating(Movie.RatingBean rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public Movie setTitle(String title) {
        this.title = title;
        return this;
    }

    public int getCollect_count() {
        return collect_count;
    }

    public void setCollect_count(int collect_count) {
        this.collect_count = collect_count;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Movie.ImagesBean getImages() {
        return images;
    }

    public void setImages(Movie.ImagesBean images) {
        this.images = images;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<Movie.CastsBean> getCasts() {
        return casts;
    }

    public void setCasts(List<Movie.CastsBean> casts) {
        this.casts = casts;
    }

    public List<Movie.DirectorsBean> getDirectors() {
        return directors;
    }

    public void setDirectors(List<Movie.DirectorsBean> directors) {
        this.directors = directors;
    }

    public static class RatingBean {
        /**
         * max : 10
         * average : 5.3
         * stars : 30
         * min : 0
         */

        private int max;
        private double average;
        private String stars;
        private int min;

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public double getAverage() {
            return average;
        }

        public void setAverage(double average) {
            this.average = average;
        }

        public String getStars() {
            return stars;
        }

        public void setStars(String stars) {
            this.stars = stars;
        }

        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }
    }

    public static class ImagesBean {
        /**
         * small : https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2503544593.jpg
         * large : https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2503544593.jpg
         * medium : https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2503544593.jpg
         */

        private String small;
        private String large;
        private String medium;

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }
    }

    public static class CastsBean {
        /**
         * alt : https://movie.douban.com/celebrity/1339594/
         * avatars : {"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1503377320.23.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1503377320.23.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1503377320.23.jpg"}
         * name : 王俊凯
         * id : 1339594
         */

        private String alt;
        private Movie.CastsBean.AvatarsBean avatars;
        private String name;
        private String id;

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public Movie.CastsBean.AvatarsBean getAvatars() {
            return avatars;
        }

        public void setAvatars(Movie.CastsBean.AvatarsBean avatars) {
            this.avatars = avatars;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public static class AvatarsBean {
            /**
             * small : https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1503377320.23.jpg
             * large : https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1503377320.23.jpg
             * medium : https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1503377320.23.jpg
             */

            private String small;
            private String large;
            private String medium;

            public String getSmall() {
                return small;
            }

            public void setSmall(String small) {
                this.small = small;
            }

            public String getLarge() {
                return large;
            }

            public void setLarge(String large) {
                this.large = large;
            }

            public String getMedium() {
                return medium;
            }

            public void setMedium(String medium) {
                this.medium = medium;
            }
        }
    }

    public static class DirectorsBean {
        /**
         * alt : https://movie.douban.com/celebrity/1316056/
         * avatars : {"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p34888.jpg","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p34888.jpg","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p34888.jpg"}
         * name : 韩杰
         * id : 1316056
         */

        private String alt;
        private Movie.DirectorsBean.AvatarsBeanX avatars;
        private String name;
        private String id;

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public Movie.DirectorsBean.AvatarsBeanX getAvatars() {
            return avatars;
        }

        public void setAvatars(Movie.DirectorsBean.AvatarsBeanX avatars) {
            this.avatars = avatars;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public static class AvatarsBeanX {
            /**
             * small : https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p34888.jpg
             * large : https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p34888.jpg
             * medium : https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p34888.jpg
             */

            private String small;
            private String large;
            private String medium;

            public String getSmall() {
                return small;
            }

            public void setSmall(String small) {
                this.small = small;
            }

            public String getLarge() {
                return large;
            }

            public void setLarge(String large) {
                this.large = large;
            }

            public String getMedium() {
                return medium;
            }

            public void setMedium(String medium) {
                this.medium = medium;
            }
        }
    }
}
