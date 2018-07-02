package com.demo.scenicspot.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：sonnerly on 2016/11/23 0023 16:16
 */
public class AnviRecommendBean {


    /**
     * lastPage : 1
     * rows : [{"group_id":1,"group_name":"乱炖景区","listData":[{"creatime":"2016-11-01 16:02:44","id":1,"info":"旅游景区（tourist attraction），是指具有吸引国内外游客前往游览的明确的区域场所，能够满足游客游览观光，消遣娱乐，康体健身，求知等旅游需求，应具备相应的旅游服务设施并提供相应旅游服务的独立管理区","name":"景区","soft":1,"type":1,"x_point":"114.608216","y_point":"38.018082"},{"creatime":"2016-11-01 16:02:46","id":2,"info":"白宫（英语：The White House，也称为白屋）是美国总统的官邸和办公室。白宫由美国国家公园管理局拥有，是\u201c总统公园\u201d的一部分。二十美元纸币的背面图片就是白宫。","name":"白宫","soft":2,"type":1,"x_point":"114.6082","y_point":"38.01845"},{"creatime":"2016-11-01 16:02:45","id":6,"info":"自由女神像（英文：Statue Of Liberty），全名为\u201c自由女神铜像国家纪念碑\u201d，正式名称是\u201c自由照耀世界（Liberty Enlightening the World）\u201d，位于美国纽约海港内自由岛的哈德逊河口附近。是法国于1876年为纪念美国独立战争期间的美法联盟赠送给美国的礼物，1886年10月28日铜像落成","name":"自由女神像","soft":3,"type":0,"x_point":"114.608189","y_point":"38.018745"},{"creatime":"2016-11-01 16:02:47","id":3,"info":"埃菲尔铁塔（法语：La Tour Eiffel；英语：the Eiffel Tower）矗立在塞纳河南岸法国巴黎的战神广场，它是世界著名建筑、法国文化象征之一、巴黎城市地标之一、巴黎最高建筑物。被法国人爱称为\u201c铁娘子\u201d 。","name":"埃菲尔铁塔","soft":4,"type":1,"x_point":"114.608189","y_point":"38.018838"},{"creatime":"2016-11-01 16:02:54","id":111,"info":"这是李朋成强烈要求","name":"终点景区","soft":5,"type":1,"x_point":"114.608195","y_point":"38.018936"}]},{"group_id":2,"group_name":"路线2","listData":[{"creatime":"2016-11-01 16:41:50","id":4,"info":"第一个点第一个点第一个点第一个点第一个点第一个点第一个点第一个点第一个点第一个点第一个点第一个点第一个点第一个点第一个点第一个点第一个点第一个点第一个点第一个点第一个点第一个点第一个点","name":"第一个点","soft":1,"type":1,"x_point":"114.608306","y_point":"38.018046"},{"creatime":"2016-11-01 16:42:42","id":5,"info":"第二个点第二个点第二个点第二个点第二个点第二个点第二个点第二个点第二个点第二个点第二个点第二个点第二个点第二个点第二个点","name":"第二个点","soft":2,"type":1,"x_point":"114.608247","y_point":"38.018459"},{"creatime":"2016-11-01 16:43:15","id":999,"info":"第三个点第三个点第三个点第三个点第三个点第三个点第三个点第三个点第三个点第三个点第三个点第三个点第三个点第三个点第三个点第三个点第三个点第三个点第三个点第三个点第三个点第三个点","name":"第三个点","soft":3,"type":1,"x_point":"114.608264","y_point":"38.018947"}]},{"group_id":3,"group_name":"方大一会游","listData":[{"creatime":"2016-10-28 16:34:18","id":7,"info":"大门口吧","name":"大门口吧","soft":1,"type":1,"x_point":"114.608307","y_point":"38.019191"},{"creatime":"2016-10-28 16:35:09","id":8,"info":"你可以在此买早餐吃","name":"早餐","soft":2,"type":0,"x_point":"114.608709","y_point":"38.019051"},{"creatime":"2016-10-28 16:36:44","id":9,"info":"湖边","name":"湖边","soft":3,"type":0,"x_point":"114.608066","y_point":"38.019203"},{"creatime":"2016-10-28 16:38:07","id":10,"info":"五号楼","name":"五号楼","soft":4,"type":1,"x_point":"114.607792","y_point":"38.019237"},{"creatime":"2016-10-28 16:38:05","id":11,"info":"六号楼","name":"六号楼","soft":5,"type":1,"x_point":"114.607722","y_point":"38.018975"},{"creatime":"2016-10-28 16:38:41","id":12,"info":"这有个朋成","name":"这有个朋成","soft":6,"type":0,"x_point":"114.608141","y_point":"38.01881"},{"creatime":"2016-10-28 16:39:27","id":13,"info":"这个更厉害,终极Boss","name":"朋成二号","soft":7,"type":0,"x_point":"114.608216","y_point":"38.018143"},{"creatime":"2016-10-28 16:40:31","id":14,"info":"历经一堆困难,终于赶来公司加班了","name":"终点","soft":8,"type":1,"x_point":"114.608082","y_point":"38.017855"}]}]
     * total : 3
     */

    private DataBean data;
    /**
     * msg : 成功
     * status : 0
     */

    private HeaderBean header;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public HeaderBean getHeader() {
        return header;
    }

    public void setHeader(HeaderBean header) {
        this.header = header;
    }

    public static class DataBean {
        private int lastPage;
        private int total;
        /**
         * group_id : 1
         * group_name : 乱炖景区
         * listData : [{"creatime":"2016-11-01 16:02:44","id":1,"info":"旅游景区（tourist attraction），是指具有吸引国内外游客前往游览的明确的区域场所，能够满足游客游览观光，消遣娱乐，康体健身，求知等旅游需求，应具备相应的旅游服务设施并提供相应旅游服务的独立管理区","name":"景区","soft":1,"type":1,"x_point":"114.608216","y_point":"38.018082"},{"creatime":"2016-11-01 16:02:46","id":2,"info":"白宫（英语：The White House，也称为白屋）是美国总统的官邸和办公室。白宫由美国国家公园管理局拥有，是\u201c总统公园\u201d的一部分。二十美元纸币的背面图片就是白宫。","name":"白宫","soft":2,"type":1,"x_point":"114.6082","y_point":"38.01845"},{"creatime":"2016-11-01 16:02:45","id":6,"info":"自由女神像（英文：Statue Of Liberty），全名为\u201c自由女神铜像国家纪念碑\u201d，正式名称是\u201c自由照耀世界（Liberty Enlightening the World）\u201d，位于美国纽约海港内自由岛的哈德逊河口附近。是法国于1876年为纪念美国独立战争期间的美法联盟赠送给美国的礼物，1886年10月28日铜像落成","name":"自由女神像","soft":3,"type":0,"x_point":"114.608189","y_point":"38.018745"},{"creatime":"2016-11-01 16:02:47","id":3,"info":"埃菲尔铁塔（法语：La Tour Eiffel；英语：the Eiffel Tower）矗立在塞纳河南岸法国巴黎的战神广场，它是世界著名建筑、法国文化象征之一、巴黎城市地标之一、巴黎最高建筑物。被法国人爱称为\u201c铁娘子\u201d 。","name":"埃菲尔铁塔","soft":4,"type":1,"x_point":"114.608189","y_point":"38.018838"},{"creatime":"2016-11-01 16:02:54","id":111,"info":"这是李朋成强烈要求","name":"终点景区","soft":5,"type":1,"x_point":"114.608195","y_point":"38.018936"}]
         */

        private List<RowsBean> rows=new ArrayList<>();

        public int getLastPage() {
            return lastPage;
        }

        public void setLastPage(int lastPage) {
            this.lastPage = lastPage;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<RowsBean> getRows() {
            return rows;
        }

        public void setRows(List<RowsBean> rows) {
            this.rows = rows;
        }

        public static class RowsBean {
            private long group_id;
            private String group_name;
            /**
             * creatime : 2016-11-01 16:02:44
             * id : 1
             * info : 旅游景区（tourist attraction），是指具有吸引国内外游客前往游览的明确的区域场所，能够满足游客游览观光，消遣娱乐，康体健身，求知等旅游需求，应具备相应的旅游服务设施并提供相应旅游服务的独立管理区
             * name : 景区
             * soft : 1
             * type : 1
             * x_point : 114.608216
             * y_point : 38.018082
             */

            private List<ListDataBean> listData;

            public long getGroup_id() {
                return group_id;
            }

            public void setGroup_id(long group_id) {
                this.group_id = group_id;
            }

            public String getGroup_name() {
                return group_name;
            }

            public void setGroup_name(String group_name) {
                this.group_name = group_name;
            }

            public List<ListDataBean> getListData() {
                return listData;
            }

            public void setListData(List<ListDataBean> listData) {
                this.listData = listData;
            }

            public static class ListDataBean implements Serializable{
                private String creatime;
                private long id;
                private String info;
                private String name;
                private int soft;
                private int type;
                private String x_point;
                private String y_point;

                public int getBobaoflag() {
                    return bobaoflag;
                }

                public void setBobaoflag(int bobaoflag) {
                    this.bobaoflag = bobaoflag;
                }

                private int bobaoflag;  //1,在20米以内,0不在20米以内
                public String getCreatime() {
                    return creatime;
                }

                public void setCreatime(String creatime) {
                    this.creatime = creatime;
                }

                public long getId() {
                    return id;
                }

                public void setId(long id) {
                    this.id = id;
                }

                public String getInfo() {
                    return info;
                }

                public void setInfo(String info) {
                    this.info = info;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public int getSoft() {
                    return soft;
                }

                public void setSoft(int soft) {
                    this.soft = soft;
                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public String getX_point() {
                    return x_point;
                }

                public void setX_point(String x_point) {
                    this.x_point = x_point;
                }

                public String getY_point() {
                    return y_point;
                }

                public void setY_point(String y_point) {
                    this.y_point = y_point;
                }
            }
        }
    }

    public static class HeaderBean {
        private String msg;
        private int status;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
