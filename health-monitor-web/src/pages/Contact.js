import { useState } from "react";
import { Card, CardContent } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Textarea } from "@/components/ui/textarea";
import { Button } from "@/components/ui/button";
import { Facebook, Instagram, X, MapPin, Mail, Phone } from "lucide-react";

const Contact = () => {
    const [form, setForm] = useState({ name: "", email: "", phone: "", message: "" });

    const handleChange = (e) => {
        setForm({ ...form, [e.target.name]: e.target.value });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        console.log("Form submitted", form);
    };

    return (
        <div className="max-w-4xl mx-auto p-6 space-y-6">
            <h1 className="text-3xl font-bold text-center">Liên hệ</h1>

            <Card>
                <CardContent className="p-6 space-y-4">
                    <div className="flex items-center space-x-2">
                        <MapPin className="text-blue-500" />
                        <p>123 Đường ABC, Quận XYZ, TP. HCM</p>
                    </div>
                    <div className="flex items-center space-x-2">
                        <Phone className="text-green-500" />
                        <p>+84 123 456 789</p>
                    </div>
                    <div className="flex items-center space-x-2">
                        <Mail className="text-red-500" />
                        <p>support@gymhealth.vn</p>
                    </div>
                </CardContent>
            </Card>

            <Card>
                <CardContent className="p-6">
                    <form onSubmit={handleSubmit} className="space-y-4">
                        <Input name="name" placeholder="Họ và tên" value={form.name} onChange={handleChange} required />
                        <Input type="email" name="email" placeholder="Email" value={form.email} onChange={handleChange} required />
                        <Input type="tel" name="phone" placeholder="Số điện thoại" value={form.phone} onChange={handleChange} />
                        <Textarea name="message" placeholder="Nội dung tin nhắn" value={form.message} onChange={handleChange} required />
                        <Button type="submit" className="w-full">Gửi tin nhắn</Button>
                    </form>
                </CardContent>
            </Card>

            <div className="flex justify-center space-x-4">
                <Facebook className="text-blue-600 cursor-pointer" />
                <Instagram className="text-pink-500 cursor-pointer" />
                <X className="text-blue-400 cursor-pointer" />
            </div>
        </div>
    );
}

export default Contact;