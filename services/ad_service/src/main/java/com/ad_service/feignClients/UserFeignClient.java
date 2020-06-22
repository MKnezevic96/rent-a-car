package com.ad_service.feignClients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="admin_service")
public interface UserFeignClient {

}
