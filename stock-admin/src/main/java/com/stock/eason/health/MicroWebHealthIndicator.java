package com.stock.eason.health;

import com.netflix.appinfo.HealthCheckHandler;
import com.netflix.appinfo.InstanceInfo;
import com.stock.eason.controller.AdminController;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MicroWebHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        //这个状态就是数据库是否连接OK
        if(AdminController.canVisitDb) {
            return new Health.Builder(Status.UP).build();
        } else {
            return new Health.Builder(Status.DOWN).build();
        }
    }
}
