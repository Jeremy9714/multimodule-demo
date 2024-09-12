//package com.example.demo.job.executor.jobhandler;
//
//import com.xxl.job.core.biz.model.ReturnT;
//import com.xxl.job.core.handler.IJobHandler;
//import com.xxl.job.core.handler.annotation.JobHandler;
//import com.xxl.job.core.log.XxlJobLogger;
//import org.springframework.stereotype.Component;
//
///**
// * @Description:
// * @Author: zhangchy05 on 2024/9/12 10:12
// * @Version: 1.0
// */
//@JobHandler(value = "demoJobHandler")
//@Component
//public class DemoHandler extends IJobHandler {
//
//    @Override
//    public ReturnT<String> execute(String param) throws Exception {
//        XxlJobLogger.log("======执行======");
//        return SUCCESS;
//    }
//}
