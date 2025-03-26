import React from "react";
import { PageHead } from "../components";

const About = () => {
    return (
        <>
            <PageHead title="Về chúng tôi"/>
            <div className="container mx-auto px-4 py-10">
                {/* Phần tiêu đề */}
                <h1 className="text-4xl font-bold text-center text-gray-800 mb-6">Về Chúng Tôi</h1>

                {/* Giới thiệu chung */}
                <section className="mb-10 text-gray-700 text-center">
                    <p className="text-lg mb-5 text-justify">
                        Chào mừng bạn đến với <span className="font-semibold text-orange-500">Gym Health Monitor</span>!
                        Chúng tôi là một hệ thống quản lý phòng gym chuyên nghiệp, giúp hội viên theo dõi sức khỏe,
                        quản lý lịch tập và kết nối với huấn luyện viên dễ dàng.
                    </p>
                </section>

                {/* Sứ mệnh & Tầm nhìn */}
                <section className="mb-10">
                    <h2 className="text-2xl font-semibold text-gray-800 mb-2">Sứ mệnh & Tầm nhìn</h2>
                    <p className="text-gray-600 mb-5 text-justify">
                        Chúng tôi cam kết mang lại môi trường luyện tập hiện đại, an toàn và hiệu quả.
                        Với đội ngũ huấn luyện viên chuyên nghiệp, chúng tôi luôn hỗ trợ bạn đạt được mục tiêu thể chất tốt nhất.
                    </p>
                </section>

                {/* Đội ngũ huấn luyện viên */}
                <section className="mb-10">
                    <h2 className="text-2xl font-semibold text-gray-800 mb-2">Đội Ngũ Huấn Luyện Viên</h2>
                    <p className="text-gray-600 mb-5 text-justify">
                        Các huấn luyện viên của chúng tôi là những chuyên gia được chứng nhận, luôn sẵn sàng giúp bạn đạt được mục tiêu tập luyện.
                    </p>
                    <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mt-6">
                        <div className="bg-white shadow-lg p-4 rounded-lg text-center">
                            <img
                                src="/img/trainer1.webp" alt="Trainer 1"
                                className="w-100 h-100 mx-auto rounded-full mb-3"
                                style={{ maxWidth: '300px' }} />
                            <h3 className="text-lg font-semibold">Nguyễn Văn A</h3>
                            <p className="text-gray-500">Chuyên gia thể hình</p>
                        </div>
                        <div className="bg-white shadow-lg p-4 rounded-lg text-center">
                            <img
                                src="/img/trainer2.webp" alt="Trainer 2"
                                className="w-100 h-100 mx-auto rounded-full mb-3"
                                style={{ maxWidth: '300px' }} />
                            <h3 className="text-lg font-semibold">Trần Thị B</h3>
                            <p className="text-gray-500">Chuyên viên dinh dưỡng</p>
                        </div>
                        <div className="bg-white shadow-lg p-4 rounded-lg text-center">
                            <img
                                src="/img/trainer3.webp" alt="Trainer 3"
                                className="w-100 h-100 mx-auto rounded-full mb-3"
                                style={{ maxWidth: '300px' }} />
                            <h3 className="text-lg font-semibold">Lê Văn C</h3>
                            <p className="text-gray-500">HLV Yoga & Giãn Cơ</p>
                        </div>
                    </div>
                </section>

                {/* Dịch vụ của Gym */}
                <section className="mb-10 mt-5">
                    <h2 className="text-2xl font-semibold text-gray-800 mb-2 text-start">Dịch Vụ Cung Cấp</h2>
                    <ul className="list-disc list-inside text-gray-600 mb-5 text-start">
                        <li>Hệ thống quản lý tập luyện và theo dõi tiến độ</li>
                        <li>Huấn luyện viên cá nhân (PT) theo yêu cầu</li>
                        <li>Chế độ ăn uống và tư vấn dinh dưỡng</li>
                        <li>Phòng tập hiện đại với trang thiết bị tiên tiến</li>
                        <li>Lớp học nhóm: Yoga, Zumba, HIIT</li>
                    </ul>
                </section>

                {/* Liên hệ */}
                <section className="text-center">
                    <h2 className="text-2xl font-semibold text-gray-800 mb-2 text-start">Liên Hệ</h2>
                    <p className="text-gray-600 text-start">📍 Địa chỉ: 123 Nguyễn Văn Linh, Quận 7, TP.HCM</p>
                    <p className="text-gray-600 text-start">📞 Hotline: 0909-123-456</p>
                    <p className="text-gray-600 text-start">📧 Email: support@gymhealth.vn</p>
                </section>
            </div>
        </>
    );
};

export default About;
