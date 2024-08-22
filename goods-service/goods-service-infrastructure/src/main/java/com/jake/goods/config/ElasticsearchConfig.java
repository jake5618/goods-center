package com.jake.goods.config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchCustomConversions;

/**
 * @author chenjun
 * @since 2024/8/22
 */
@Configuration
public class ElasticsearchConfig extends ElasticsearchConfiguration {

    private static final String LOCAL_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    @Value("${spring.elasticsearch.uris}")
    private List<String> uris;
    @Value("${spring.elasticsearch.username}")
    private String username;
    @Value("${spring.elasticsearch.password}")
    private String password;

    @Override
    public ClientConfiguration clientConfiguration() {
        // 去除http开头
        uris = uris.stream().map(uri -> {
            if (uri.startsWith("http")) {
                uri = uri.split("://")[1];
            }
            return uri;
        }).collect(Collectors.toList());
        return ClientConfiguration.builder()
            .connectedTo(uris.toArray(new String[uris.size()])).withBasicAuth(username, password)
            .withConnectTimeout(60000)
            .build();
    }

    @Bean
    public ElasticsearchCustomConversions elasticsearchCustomConversions() {
        // 注入自定义转换器处理LocalDateTime数据
        List<Converter<?, ?>> converters = new ArrayList<>(16);
        converters.add(StringToLocalDateTimeConverter.INSTANCE);
        converters.add(LocalDateTimeToStringConverter.INSTANCE);
        return new ElasticsearchCustomConversions(converters);
    }
    /**
     * LocalDateTime转String
     */
    @WritingConverter
    private enum LocalDateTimeToStringConverter implements Converter<LocalDateTime, String> {
        /**
         * 实例化
         */
        INSTANCE;

        @Override
        public String convert(LocalDateTime source) {
            return source.format(DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_PATTERN));
        }
    }

    /**
     * String转LocalDateTime
     */
    @ReadingConverter
    private enum StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {
        /**
         * 实例化
         */
        INSTANCE;

        @Override
        public LocalDateTime convert(String source) {
            return LocalDateTime.parse(source, DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_PATTERN));
        }
    }

}

