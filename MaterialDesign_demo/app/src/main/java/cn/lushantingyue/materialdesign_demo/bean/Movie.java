package cn.lushantingyue.materialdesign_demo.bean;

/**
 * Created by diyik on 2018/1/11.
 */

public class Movie {

    private String[] genres;
    private String title;

    private Rating rating;

    public String[] getGenres() {
        return genres;
    }

    public String getTitle() {
        return title;
    }

    public Rating getRating() {
        return rating;
    }
/*
    {
        "rating": {
                "max": 10,
                "average": 5.3,
                "stars": "30",
                "min": 0
        },
        "genres": [
            "剧情",
            "奇幻"
        ],
        "title": "解忧杂货店",
        "casts": [
                {
                    "alt": "https://movie.douban.com/celebrity/1339594/",
                    "avatars": {
                    "small": "https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1503377320.23.jpg",
                    "large": "https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1503377320.23.jpg",
                    "medium": "https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1503377320.23.jpg"
                },
                "name": "王俊凯",
                "id": "1339594"
                },
                {
                    "alt": "https://movie.douban.com/celebrity/1325127/",
                    "avatars": {
                        "small": "https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1492095415.48.jpg",
                        "large": "https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1492095415.48.jpg",
                        "medium": "https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1492095415.48.jpg"
                    },
                    "name": "迪丽热巴",
                    "id": "1325127"
                },
                {
                    "alt": "https://movie.douban.com/celebrity/1330472/",
                    "avatars": {
                    "small": "https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1385100761.5.jpg",
                    "large": "https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1385100761.5.jpg",
                    "medium": "https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1385100761.5.jpg"
                },
                "name": "董子健",
                "id": "1330472"
            }
        ],
        "collect_count": 33094,
            "original_title": "解忧杂货店",
            "subtype": "movie",
            "directors": [
                {
                    "alt": "https://movie.douban.com/celebrity/1316056/",
                    "avatars": {
                    "small": "https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p34888.jpg",
                    "large": "https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p34888.jpg",
                    "medium": "https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p34888.jpg"
                },
                "name": "韩杰",
                "id": "1316056"
                }
            ],
            "year": "2017",
            "images": {
                "small": "https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2503544593.jpg",
                "large": "https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2503544593.jpg",
                "medium": "https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2503544593.jpg"
            },
            "alt": "https://movie.douban.com/subject/26654146/",
            "id": "26654146"
    },
    */

    class Rating {
        int max;
        int stars;
        int min;
        float average;

        public int getMax() {
            return max;
        }

        public int getStars() {
            return stars;
        }

        public int getMin() {
            return min;
        }

        public float getAverage() {
            return average;
        }
    }
}
