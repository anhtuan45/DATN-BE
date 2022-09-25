package com.ws.masterserver.utils.constants;

public class WsConst {

    private WsConst() {}

    public static class Values {

        private Values() {}

        public static final Long ACCESS_TOKEN_EXPIRED = 1000L * 60 * 60 * 12;
        public static final Long REFRESH_TOKEN_EXPIRED = 7200000L;
        public static final Integer CUSTOMER_AGE_MIN = 16;
        public static final Integer CUSTOMER_AGE_MAX = 120;

        public static final String TIME_ZONE = "Asia/Ho_Chi_Minh";

        public static final String WEB_SOCKET_TOPIC_NOTIFICATION_URL = "/topic/notification";
        public static final String WEB_SOCKET_END_POINT_URL = "/ws-socket";

        public static final Integer PAGE_DEFAULT = 0;
        public static final Integer PAGE_SIZE_DEFAULT = 10;
        public static final String SORT_FIELD_DEFAULT = "createdDate";
        public static final String SORT_DIRECTION_DEFAULT = "desc";
        public static final String ROLE = "Role";
        public static final String JWT_SECRET = "womanshirt2022";
        public static final String BEARER_SPACE = "Bearer ";
    }

    /**
     * message + character
     * */
    public static class Messages {

        private Messages() {}

        public static final String NOT_FOUND = "Không tìm thấy %s!";
        public static final String NOT_EXIST = "Không tồn tại";

        /** Result */
        public static final String SUCCESS = "Thành công!";
        public static final String FAILED = "Thất bại!";

        /** action */
        public static final String CREATE = "Thêm mới";
        public static final String UPDATE = "Cập nhật";
        public static final String  DELETE = "Xóa";

        /** hệ thống */
        public static final String FORBIDDEN = "Không có quyền!";
        public static final String TOKEN_EXPIRED = "Hết phiên sử dụng! Vui lòng đăng nhập lại!";
        public static final String UN_KNOW_EXCEPTION = "Lỗi không xác định!";
        public static final String SERVER_RETRY = "Vui lòng thử lại sau ít phút!";

        /** validate */
        public static final String NOT_BLANK = "Không được để trống %s!";
        public static final String INVALID = "%s không hợp lệ!";
        public static final String INVALID_PASSWORD = "Mật khẩu không hợp lệ, phải dài từ 6 - 20 ký tự!";
        public static final String INVALID_CUSTOMER_AGE = "Tuổi không hợp lệ!(Từ %d - %d tuổi)";
        public static final String CART_EMPTY_EXCEPTION = "Không được để trống giỏ hàng!";
        public static final String CART_QTY_EXCEPTION = "Số lượng đặt quá số lượng trong kho. Vui lòng điều chỉnh lại!";

        /** dấu */
        public static final String EXCLAMATION = "!";
        public static final String SPACE = " ";

    }

    public static class Nouns {
        private Nouns() {}

        /** field */
        public static final String EMAIL = "Email";
        public static final String PASSWORD_VI = "Mật khẩu";
        public static final String FIRST_NAME_VI = "Họ";
        public static final String LAST_NAME_VI = "Tên";
        public static final String DOB_VI = "Ngày sinh";
        public static final String GENDER_VI = "Giới tính";
        public static final String PHONE_VI = "Số điện thoại";
        public static final String AVATAR_VI = "Avatar";
        public static final String PRODUCT_VI = "Sản phẩm";
        public static final String PRODUCT_OPTION_VI = "Tùy chọn Sản phẩm";
        public static final String QTY_VI = "Số lượng";
        public static final String STATUS_VI = "Trạng thái";
        public static final String CATEGORY_VI = "Loại sản phẩm";
        public static final String MATERIAL_VI = "Chất liệu";
        public static final String COLOR_VI = "Màu sắc";
        public static final String SIZE_VI = "Kích thước";
        /** Object */
        public static final String USER_VI = "Người dùng";
        public static final String CUSTOMER_VI = "Khách hàng";
        public static final String STAFF_VI = "Nhân viên";
        public static final String PASSWORD = "Password";
        public static final String ID = "ID";
        public static final String NAME = "Name";
        public static final String ROLE = "Role";
        public static final String ACCESS_TOKEN_FIELD = "accessToken";
        public static final String REFRESH_TOKEN_FIELD = "refreshToken";
    }

    public static class Regex {
        private Regex(){}

        public static final String EMAIL = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        public static final String FULL_NAME = "^[-a-zA-ZÀÁÂÃĂẠẢẤẦẨẪẬẮẰẲẴẶÄäăạảấầẩẫậắằẳăẵặàáâãÈÉÊЁÊĔÊ̆ẸẺẼỀỀỂẾÊ̆ỄỆẹẻẽềềểếê̆èéêёĕễệÒÓÔÕŎÔ̆ƠơòóôõŏÌÍĨĬĬỈỊỉịĩìíĭčČỌỎỐỒỔỖỘỚỜỞỠỢơ̆ọỏốồổỗộớờởỡợỤŬŨÙÚỦỨỪƯ̆ƯỬỮỰụủứừùúũưửữựŭỲỴÝỶỸýỳỵỷỹЙÑйñĐđɃƀ'`‘\\s\\\\W|_]+[^@!#$%^&*():;/?.,<>{}|0123456789]$";
        public static final String PHONE = "\\b(84|0[3|5|7|8|9])+([0-9]{8})\\b";
    }


    public static class CategoryFields {
        private CategoryFields() {}

        public static final String NAME_VAL = "tên loại sản phẩm";
        public static final String DES_VAL = "miêu tả loại sản phẩm";
    }

    public static class UserFields {

        private UserFields() {}

        public static final String ID_VAR = "id";
        public static final String NAME_VAR = "name";
        public static final String ROLE_VAR = "role";
        public static final String EMAIL_VAR = "email";
        public static final String PASSWORD = "password";
    }

    public static class Seeders {
        private Seeders() {}

        /**
         * Admin
         */
        public static final String ADMIN_ID = "fb6e037a-9f6b-49e0-826e-d1d1539413f6";
        public static final String ADMIN_EMAIL = "admin@gmail.com";
        public static final String ADMIN_FIRST_NAME = "Woman";
        public static final String ADMIN_LAST_NAME = "Shop";
        public static final String ADMIN_PASSWORD = "admin123";

        /**
         * Staff
         */
        public static final String STAFF_ID = "53967738-d838-41f4-81df-60c9e65b9f8e";
        public static final String STAFF_EMAIL = "staff@gmail.com";
        public static final String STAFF_FIRST_NAME = "Nguyễn Nhân";
        public static final String STAFF_LAST_NAME = "Viên";
        public static final String STAFF_PASSWORD = "staff123";

        /**
         * Customer
         */
        public static final String CUSTOMER_EMAIL1 = "customer1@gmail.com";
        public static final String CUSTOMER_FIRST_NAME1 = "Lê Thị";
        public static final String CUSTOMER_LAST_NAME1 = "Xinh";
        public static final String CUSTOMER_PASSWORD1 = "customer123";
        public static final String CUSTOMER_PHONE1 = "0988777888";

        public static final String CUSTOMER_EMAIL2 = "customer2@gmail.com";
        public static final String CUSTOMER_FIRST_NAME2 = "Nguyễn Văn";
        public static final String CUSTOMER_LAST_NAME2 = "An";
        public static final String CUSTOMER_PASSWORD2 = "customer123";
        public static final String CUSTOMER_PHONE2 = "0963012012";

        /**
         * address
         * */
        public static final String CUSTOMER_ADDRESS_PROVINCE_CODE1 = "1";
        public static final String CUSTOMER_ADDRESS_PROVINCE_NAME1 = "Thành phố Hà Nội";
        public static final String CUSTOMER_ADDRESS_DISTRICT_CODE1 = "1";
        public static final String CUSTOMER_ADDRESS_DISTRICT_NAME1 = "Quận Ba Đình";
        public static final String CUSTOMER_ADDRESS_WARD_CODE1 = "1";
        public static final String CUSTOMER_ADDRESS_WARD_NAME1 = "Phường Phúc Xá";
        public static final String CUSTOMER_ADDRESS_EXACT1 = "Số nhà 200";
        public static final String CUSTOMER_ADDRESS_COMBINATION1 = "Phường Phúc Xá, Quận Ba Đình, Thành phố Hà Nội";

        public static final String CUSTOMER_ADDRESS_PROVINCE_CODE2 = "1";
        public static final String CUSTOMER_ADDRESS_PROVINCE_NAME2 = "Thành phố Hà Nội";
        public static final String CUSTOMER_ADDRESS_DISTRICT_CODE2 = "1";
        public static final String CUSTOMER_ADDRESS_DISTRICT_NAME2 = "Quận Ba Đình";
        public static final String CUSTOMER_ADDRESS_WARD_CODE2 = "4";
        public static final String CUSTOMER_ADDRESS_WARD_NAME2 = "Phường Trúc Bạch";
        public static final String CUSTOMER_ADDRESS_EXACT2 = "Số nhà 170";
        public static final String CUSTOMER_ADDRESS_COMBINATION2 = "Phường Trúc Bạch, Quận Ba Đình, Thành phố Hà Nội";

        /** category */

        public static final String CATEGORY_NAME = "Áo khoác nam";
        public static final String CATEGORY_DES = "Form chuẩn Unisex Nam Nữ Couple đều mặc được.\n" +
                "Thích hợp sử dụng khi đi chơi, dã ngoại , dạo, phố……\n" +
                "Đường kim mũi chỉ kép cực tinh tế, đường may kỹ. \n" +
                "Nón trùm qua đầu, dây kéo tốt";

        /** product */

        public static final String PRODUCT_ID = "3166675c-8def-42a0-b9f6-975a25738ce8";
        public static final String PRODUCT_NAME = "Áo phông nam Adidas";
        public static final String PRODUCT_DES = "Chất liệu : dù 2 lớp , trong lót dù\n" +
                "Màu sắc đa dạng tha hồ lựa chọn nhé các chị em.\n" +
                "Size : chỉ có 1 size cân nặng từ 45-60kg , chiều cao từ 1m45- 1m6\n" +
                "Công dụng : chống nắng , tránh gió , giữ ấm ";

        public static final String PRODUCT_THUMBNAIL = "https://media.coolmate.me/cdn-cgi/image/width=672,height=990,quality=100/uploads/March2022/6-0_55.jpg";
        public static final String PRODUCT_OPTION_BLUE_IMG = "https://media.coolmate.me/cdn-cgi/image/width=672,height=990,quality=100/uploads/March2022/6-0_55.jpg";
        public static final String PRODUCT_OPTION_GREY_IMG = "https://media.coolmate.me/cdn-cgi/image/width=672,height=990,quality=100/uploads/March2022/5-0_copy.jpg";

        public static final Long PRODUCT_PRICE = 90000L;
        public static final Long PRODUCT_OPTION_GREY_PRICE = 100000L;
        public static final Long PRODUCT_OPTION_BLUE_PRICE = 80000L;
    }

    public static class TimeRanges {
        public static final String DAY = "day";
        public static final String WEEK = "week";
        public static final String MONTH = "month";
    }
}
