package com.moka.flickr.entity;

import java.util.List;

public class FlickrResponse {
    public Photos photos;

    public Photos getPhotos() {
        return photos;
    }

    public static class Photos {
        private long page;
        private long pages;
        private long perpage;
        private long total;
        private List<Photo> photo;

        public long getPage() {
            return page;
        }

        public List<Photo> getPhotoList() {
            return photo;
        }

        public static class Photo {
            private long id;
            private String owner;
            private String secret;
            private String server;
            private long farm;
            private String title;
            private long ispublic;
            private long isfriend;
            private long isfamily;
            
            public long getId() {
            	return id;
            }
            
            public String getUrl() {
                return "http://farm" + farm + ".staticflickr.com/" + server + "/" + id + "_"
                        + secret + "_b.jpg";
            }

            public String getTitle() {
                return title;
            }

            @Override
            public boolean equals(Object o) {
                if (o instanceof Photo) {
                    return ((Photo) o).id == id;
                }
                return false;
            }
        }
    }
}
