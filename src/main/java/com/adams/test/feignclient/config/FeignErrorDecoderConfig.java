package com.adams.test.feignclient.config;

import com.adams.test.feignclient.exception.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author Adams
 * @create 2019/2/20 16:49
 */
@Slf4j
@Configuration
public class FeignErrorDecoderConfig {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new UserErrorDecoder();
    }

    public class UserErrorDecoder implements ErrorDecoder {
        @Override
        public Exception decode(String methodKey, Response response) {
            log.info("methodKey:", methodKey);
            Exception exception = null;
            try {
                // 获取原始的返回内容
                String json = Util.toString(response.body().asReader());
                exception = new RuntimeException(json);

                log.info("FeignErrorDecoderConfig exception:{}", json);
//                return new FeignException(json);
            } catch (IOException ex) {
                log.error(ex.getMessage(), ex);
            }
            return exception;
        }
    }
}
