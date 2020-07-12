package com.ad_service.feignClients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="ad-service")
public interface UserFeignClient {

}
