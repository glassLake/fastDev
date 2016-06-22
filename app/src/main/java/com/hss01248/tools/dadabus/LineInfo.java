package com.hss01248.tools.dadabus;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22 0022.
 */
public class LineInfo {


    /**
     * ret : 0
     * msg :
     * server_time : 2016-06-22 11:52:14
     * data : {"line_code":"1180-227-7759","start_time":"07:50:00","distance":"8.75","take_time":"31","price":8,"discount_price":5,"on_site_id":"227","on_site_name":"群贤花园（公交站）","on_site_lng":"113.882841","on_site_lat":"22.566269","off_site_id":"7759","off_site_name":"香港理工大学","off_site_lng":"113.942509","off_site_lat":"22.531231","tog_line_id":"1180","company_id":"0","line_card":"","line_start_time":"07:45:00","on_site_type":2,"off_site_type":2,"company_name":"","labels":[],"ticket_info":[{"ticket_type":1,"ticket_discount":2}],"line_type":"0","line_type_name":"上下班","main_line_type":"1","req_count":"185","pre_sale_flag":2,"pre_sale_tip":"","promoter_flag":2,"open_count":0,"buy_count":0,"likes":"14","avatar_list":[],"seat_number":null,"pre_sale_status":"3"}
     */

    public int ret;
    public String msg;
    public String server_time;
    /**
     * line_code : 1180-227-7759
     * start_time : 07:50:00
     * distance : 8.75
     * take_time : 31
     * price : 8
     * discount_price : 5
     * on_site_id : 227
     * on_site_name : 群贤花园（公交站）
     * on_site_lng : 113.882841
     * on_site_lat : 22.566269
     * off_site_id : 7759
     * off_site_name : 香港理工大学
     * off_site_lng : 113.942509
     * off_site_lat : 22.531231
     * tog_line_id : 1180
     * company_id : 0
     * line_card :
     * line_start_time : 07:45:00
     * on_site_type : 2
     * off_site_type : 2
     * company_name :
     * labels : []
     * ticket_info : [{"ticket_type":1,"ticket_discount":2}]
     * line_type : 0
     * line_type_name : 上下班
     * main_line_type : 1
     * req_count : 185
     * pre_sale_flag : 2
     * pre_sale_tip :
     * promoter_flag : 2
     * open_count : 0
     * buy_count : 0
     * likes : 14
     * avatar_list : []
     * seat_number : null
     * pre_sale_status : 3
     */

    public DataBean data;

    public static class DataBean {
        public String line_code;
        public String start_time;
        public String distance;
        public String take_time;
        public int price;
        public int discount_price;
        public String on_site_id;
        public String on_site_name;
        public String on_site_lng;
        public String on_site_lat;
        public String off_site_id;
        public String off_site_name;
        public String off_site_lng;
        public String off_site_lat;
        public String tog_line_id;
        public String company_id;
        public String line_card;
        public String line_start_time;
        public int on_site_type;
        public int off_site_type;
        public String company_name;
        public String line_type;
        public String line_type_name;
        public String main_line_type;
        public String req_count;
        public int pre_sale_flag;
        public String pre_sale_tip;
        public int promoter_flag;
        public int open_count;
        public int buy_count;
        public String likes;
        public Object seat_number;
        public String pre_sale_status;
        public List<?> labels;
        /**
         * ticket_type : 1
         * ticket_discount : 2
         */

        public List<TicketInfoBean> ticket_info;
        public List<?> avatar_list;

        public static class TicketInfoBean {
            public int ticket_type;
            public int ticket_discount;
        }
    }
}
