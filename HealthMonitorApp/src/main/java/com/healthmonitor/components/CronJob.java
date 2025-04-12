package com.healthmonitor.components;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CronJob {

    // Cron chạy mỗi 1 giờ (lúc phút 0 mỗi giờ)
    @Scheduled(cron = "0 0 * * * *")
    public void checkSubscriptionExpired() {
        System.out.println(">>> Đang kiểm tra gói tập hết hạn <<<");
    }

    // Cron chạy mỗi 5 phút
    @Scheduled(cron = "0 */5 * * * *")
    public void checkSchedule() {
        System.out.println(">>> Gửi thông báo định kỳ <<<");
    }
}
