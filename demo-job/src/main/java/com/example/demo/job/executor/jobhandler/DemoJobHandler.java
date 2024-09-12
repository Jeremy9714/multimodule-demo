package com.example.demo.job.executor.jobhandler;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/9/12 11:17
 * @Version: 1.0
 */
@Component
public class DemoJobHandler {

    @XxlJob("demoJobHandler")
    public void demoJobHandler() throws Exception {
        XxlJobHelper.log("======执行中======");
    }
}
