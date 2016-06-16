package com.hss01248.tools.dadabus;

import java.util.List;

/**
 * Created by Administrator on 2016/5/26 0026.
 */
public class TickInfo {

    public DataBean data;
    /**
     * data : {"month_schedule_list":[{"day_count":21,"end_date":"2016-06-30","month":201606,"month_price":"99.75","original_price":"168","start_date":"2016-06-01","status":3,"ticket_status":3,"total_seat":"53","valid_seat":0}],"schedule_list":[{"date":"2016-05-26","date_price":"5.00","original_price":8,"status":3,"ticket_status":3,"total_seat":1,"valid_seat":0},{"date":"2016-05-27","date_price":"5.00","original_price":8,"status":3,"ticket_status":3,"total_seat":1,"valid_seat":0},{"date":"2016-05-30","date_price":"5.00","original_price":8,"status":3,"ticket_status":3,"total_seat":1,"valid_seat":0},{"date":"2016-05-31","date_price":"5.00","original_price":8,"status":3,"ticket_status":3,"total_seat":1,"valid_seat":0},{"date":"2016-06-01","date_price":"5.00","original_price":8,"status":3,"ticket_status":3,"total_seat":1,"valid_seat":0},{"date":"2016-06-02","date_price":"5.00","original_price":8,"status":3,"ticket_status":3,"total_seat":1,"valid_seat":0},{"date":"2016-06-03","date_price":"5.00","original_price":8,"status":3,"ticket_status":3,"total_seat":1,"valid_seat":0},{"date":"2016-06-06","date_price":"5.00","original_price":8,"status":3,"ticket_status":3,"total_seat":1,"valid_seat":0},{"date":"2016-06-07","date_price":"5.00","original_price":8,"status":3,"ticket_status":3,"total_seat":1,"valid_seat":0},{"date":"2016-06-08","date_price":"5.00","original_price":8,"status":1,"ticket_status":1,"total_seat":1,"valid_seat":1}]}
     * msg :
     * ret : 0
     * server_time : 2016-05-26 00:22:44
     */

    public String msg;
    public int ret;
    public String server_time;

    public static class DataBean {
        /**
         * day_count : 21
         * end_date : 2016-06-30
         * month : 201606
         * month_price : 99.75
         * original_price : 168
         * start_date : 2016-06-01
         * status : 3
         * ticket_status : 3
         * total_seat : 53
         * valid_seat : 0
         */

        public List<MonthScheduleListBean> month_schedule_list;
        /**
         * date : 2016-05-26
         * date_price : 5.00
         * original_price : 8
         * status : 3
         * ticket_status : 3
         * total_seat : 1
         * valid_seat : 0
         */

        public List<ScheduleListBean> schedule_list;

        public static class MonthScheduleListBean {
            public int day_count;
            public String end_date;
            public int month;
            public String month_price;
            public String original_price;
            public String start_date;
            public int status;
            public int ticket_status;
            public String total_seat;
            public int valid_seat;
        }

        public static class ScheduleListBean {
            public String date;
            public String date_price;
            public int original_price;
            public int status;
            public int ticket_status;
            public int total_seat;
            public int valid_seat;
        }
    }
}
