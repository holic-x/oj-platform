package com.noob.framework.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis Plus 配置
 */
@Configuration
@MapperScan("com.noob.**.mapper")
public class MyBatisPlusConfig {

    /**
     * 拦截器配置（配置分页插件，可设定规则）
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

//    @Bean
//    public TypeHandlerRegistry mybatisPlusTypeHandlerRegistry() {
//        TypeHandlerRegistry registry = new TypeHandlerRegistry();
//        // 自定义类型转换器进行注册
//        registry.register(List.class, new ListTypeHandler());
//        return registry;
//    }

}