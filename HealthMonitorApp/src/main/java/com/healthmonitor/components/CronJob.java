// import org.springframework.scheduling.annotation.Scheduled;
// import org.springframework.stereotype.Component;

// @Component
// public class GoiTapScheduler {

//     // Cron chạy mỗi 1 giờ (lúc phút 0 mỗi giờ)
//     @Scheduled(cron = "0 0 * * * *")
//     public void kiemTraGoiTapHetHan() {
//         System.out.println(">>> Đang kiểm tra gói tập hết hạn <<<");
//         // Gọi service kiểm tra logic hết hạn ở đây
//     }

//     // Cron chạy mỗi 5 phút
//     @Scheduled(cron = "0 */5 * * * *")
//     public void thongBaoMoiNguoi() {
//         System.out.println(">>> Gửi thông báo định kỳ <<<");
//     }
// }
