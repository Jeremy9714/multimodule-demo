package com.example.demo.config;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.example.demo.interceptor.LoginInterceptor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.cbor.MappingJackson2CborHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.smile.MappingJackson2SmileHttpMessageConverter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/16 16:35
 * @Version: 1.0
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${spring.application.admin:false}")
    private boolean admin;

    @Value("${spring.application.login.exclude.path:}")
    private String loginExclude;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**") // 对外暴露路径
                .addResourceLocations("classpath:/static/"); // 内部路径
    }
    
//    public void configurePathMatch(PathMatchConfigurer configurer){
//        configurer.setUseSuffixPatternMatch(false).setUseTrailingSlashMatch(true);
//    }
//
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler(new String[]{"/static/**"}).addResourceLocations(new String[]{"classpath:/static/"});
//    }
//
//    @Bean
//    public LocaleResolver localeResolver() {
//        SessionLocaleResolver slr = new SessionLocaleResolver();
//        slr.setDefaultLocale(Locale.CHINA);
//        return slr;l
//    }
//
//    public void addInterceptors(InterceptorRegistry registry) {
//        InterceptorRegistration registration = registry.addInterceptor(new LoginInterceptor());
//        if (this.admin) {
//            registration.excludePathPatterns(new String[]{"/static/**"});
//            if (StringUtils.isNotBlank(this.loginExclude)) {
//                String[] include = this.loginExclude.split(",");
//                String[] var4 = include;
//                int var5 = include.length;
//
//                for(int var6 = 0; var6 < var5; ++var6) {
//                    String path = var4[var6];
//                    if (StringUtils.isNotBlank(path) && StringUtils.isNotBlank(path.trim())) {
//                        registration.excludePathPatterns(new String[]{path.trim()});
//                    }
//                }
//            }
//        } else {
//            registration.excludePathPatterns(new String[]{"/**"});
//        }
//
//    }
//
//    @Bean
//    public ApplicationListener getListener() {
////        ApplicationListener applicationListener = new ApplicationListener();
////        return applicationListener;
//        return null;
//    }
//
//    @Bean
//    public ServletListenerRegistrationBean<ApplicationListener> servletListenerRegistrationBean() {
//        ServletListenerRegistrationBean<ApplicationListener> servletListenerRegistrationBean = new ServletListenerRegistrationBean();
////        servletListenerRegistrationBean.setListener(this.getListener());
//        return servletListenerRegistrationBean;
//    }
//
//    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//        Iterator iterable = converters.iterator();
//
//        while(true) {
//            HttpMessageConverter messageConverter;
//            do {
//                if (!iterable.hasNext()) {
//                    FastJsonHttpMessageConverter fjc = new FastJsonHttpMessageConverter();
//                    FastJsonConfig fj = new FastJsonConfig();
//                    fj.setSerializerFeatures(new SerializerFeature[]{SerializerFeature.DisableCircularReferenceDetect});
//                    SerializeConfig serializeConfig = SerializeConfig.globalInstance;
//                    serializeConfig.put(BigInteger.class, ToStringSerializer.instance);
//                    serializeConfig.put(Long.class, ToStringSerializer.instance);
//                    serializeConfig.put(Long.TYPE, ToStringSerializer.instance);
//                    fj.setSerializeConfig(serializeConfig);
//                    fjc.setFastJsonConfig(fj);
//                    List<MediaType> types = new ArrayList();
//                    types.add(MediaType.APPLICATION_JSON_UTF8);
//                    fjc.setSupportedMediaTypes(types);
//                    fjc.setDefaultCharset(Charset.forName("UTF-8"));
//                    converters.add(fjc);
//                    return;
//                }
//
//                messageConverter = (HttpMessageConverter)iterable.next();
//            } while(!(messageConverter instanceof MappingJackson2HttpMessageConverter) && !(messageConverter instanceof MappingJackson2SmileHttpMessageConverter) && !(messageConverter instanceof MappingJackson2CborHttpMessageConverter));
//
//            iterable.remove();
//        }
//    }
    
}
