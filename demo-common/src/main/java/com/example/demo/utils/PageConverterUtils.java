package com.example.demo.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;

import java.util.List;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/14 14:06
 * @Version: 1.0
 */
public class PageConverterUtils {
    public PageConverterUtils() {
    }

    public static JSONObject toDataTable(Page page, int draw) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("draw", draw);
        jsonObject.put("recordsTotal", (long) page.getTotal());
        jsonObject.put("recordsFiltered", (long) page.getTotal());
        List<?> records = page.getRecords();
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(records));
        jsonObject.put("data", jsonArray);
        return jsonObject;
    }

}
