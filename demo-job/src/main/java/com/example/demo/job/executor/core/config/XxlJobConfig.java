package com.example.demo.job.executor.core.config;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/9/12 9:39
 * @Version: 1.0
 */
@Configuration
public class XxlJobConfig {
    private Logger logger = LoggerFactory.getLogger(XxlJobConfig.class);

    @Value("${xxl-job.admin.addresses:}")
    private String adminAddresses;

    @Value("${xxl-job.executor.appName:}")
    private String appName;

    @Value("${xxl-job.executor.address:}")
    private String address;

    @Value("${xxl-job.executor.ip:}")
    private String ip;

    @Value("${xxl-job.executor.port:}")
    private int port;

    @Value("${xxl-job.accessToken:}")
    private String accessToken;

    @Value("${xxl-job.executor.logPath:}")
    private String logPath;

    @Value("${xxl-job.executor.logRetentionDays:}")
    private int logRetentionDays;

    @Bean(initMethod = "start", destroyMethod = "destroy")
    public XxlJobSpringExecutor xxlJobExecutor() {
        logger.debug("======xxlJobConfig start initialization======");
        XxlJobSpringExecutor xxlJobExecutor = new XxlJobSpringExecutor();
        xxlJobExecutor.setAdminAddresses(adminAddresses);
//        xxlJobExecutor.setAppName(appName);
        xxlJobExecutor.setAppname(appName);
        xxlJobExecutor.setAddress(address);
        xxlJobExecutor.setIp(ip);
        xxlJobExecutor.setPort(port);
        xxlJobExecutor.setAccessToken(accessToken);
        xxlJobExecutor.setLogPath(logPath);
        xxlJobExecutor.setLogRetentionDays(logRetentionDays);
        return xxlJobExecutor;
    }

    /**
     * 针对多网卡、容器内部署等情况，可借助 "spring-cloud-commons" 提供的 "InetUtils" 组件灵活定制注册IP；
     *
     *      1、引入依赖：
     *          <dependency>
     *             <groupId>org.springframework.cloud</groupId>
     *             <artifactId>spring-cloud-commons</artifactId>
     *             <version>${version}</version>
     *         </dependency>
     *
     *      2、配置文件，或者容器启动变量
     *          spring.cloud.inetutils.preferred-networks: 'xxx.xxx.xxx.'
     *
     *      3、获取IP
     *          String ip_ = inetUtils.findFirstNonLoopbackHostInfo().getIpAddress();
     */
}
