/*install extension unaccent postgresql*/
CREATTE EXTENSION IF NOT EXISTS unaccent
/*Chạy 2 câu đầu này trước*/
DROP DATABASE IF EXISTS "woman-shirt";
CREATE DATABASE "woman-shirt" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'en_US.UTF-8';
/*Sau khi chạy xong connect đến db woman-shirt rồi mở console*/

/*Copy từ dong này đến hết rồi chạy*/
SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;


ALTER DATABASE "woman-shirt" SET search_path TO '$user', 'public', 'heroku_ext';



SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

DROP SCHEMA IF EXISTS public; 
CREATE SCHEMA public;


SET default_tablespace = '';

SET default_table_access_method = heap;


CREATE TABLE public.address (
    id character varying(255) NOT NULL,
    active boolean,
    combination character varying(255),
    created_by character varying(255),
    created_date timestamp without time zone,
    district_name character varying(255),
    exact character varying(255),
    is_default boolean,
    province_name character varying(255),
    updated_by character varying(255),
    updated_date timestamp without time zone,
    user_id character varying(255),
    ward_code character varying(255),
    ward_name character varying(255),
    address_detail character varying(255),
    district_id character varying(255),
    name_of_recipient character varying(255),
    phone_number character varying(255),
    province_id character varying(255),
    "extract" character varying(255)
);



CREATE TABLE public.blog (
    id character varying(255) NOT NULL,
    content character varying,
    created_date timestamp without time zone,
    title character varying(255),
    topic_id character varying(255),
    image character varying(255),
    active boolean,
    description character varying,
    name character varying(255)
);



CREATE TABLE public.body_height (
    id character varying(255) NOT NULL,
    code character varying(255),
    max_height bigint,
    min_height bigint
);



CREATE TABLE public.body_weight (
    id character varying(255) NOT NULL,
    code character varying(255),
    max_weight bigint,
    min_weight bigint
);



CREATE TABLE public.cart (
    id character varying(255) NOT NULL,
    product_option_id character varying(255),
    quantity integer,
    total_price bigint,
    user_id character varying(255)
);



CREATE TABLE public.category (
    id character varying(255) NOT NULL,
    active boolean,
    created_by character varying(255),
    created_date timestamp without time zone,
    des character varying(1000),
    name character varying(255),
    type_id character varying(255),
    updated_by character varying(255),
    updated_date timestamp without time zone,
    image character varying(255),
    slug character varying(255)
);



CREATE SEQUENCE public.codeseq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;



CREATE TABLE public.color (
    id character varying(255) NOT NULL,
    active boolean,
    created_date timestamp without time zone,
    hex character varying(255),
    name character varying(255),
    created_by character varying(255),
    updated_by character varying(255),
    updated_date timestamp without time zone
);



CREATE TABLE public.customer_group (
    id character varying(255) NOT NULL,
    customer_type_id character varying(255),
    user_id character varying(255),
    customer_id character varying(255)
);



CREATE TABLE public.customer_type (
    id character varying(255) NOT NULL,
    active boolean,
    name character varying(255)
);



CREATE TABLE public.discount (
    id character varying(255) NOT NULL,
    apply_type character varying(255),
    code character varying(255),
    created_by character varying(255),
    created_date timestamp without time zone,
    customer_type character varying(255),
    deleted boolean,
    end_date timestamp without time zone,
    once_per_customer boolean,
    prerequisite_type character varying(255),
    prerequisite_value character varying(255),
    start_date timestamp without time zone,
    status character varying(255),
    type character varying(255),
    type_value character varying(255),
    updated_by character varying(255),
    updated_date timestamp without time zone,
    usage_limit bigint,
    des character varying(500),
    discount_type_id character varying(255)
);



CREATE TABLE public.discount_category (
    id character varying(255) NOT NULL,
    category_id character varying(255),
    discount_id character varying(255)
);



CREATE TABLE public.discount_customer (
    id character varying(255) NOT NULL,
    discount_id character varying(255),
    user_id character varying(255)
);



CREATE TABLE public.discount_customer_type (
    id character varying(255) NOT NULL,
    customer_type_id character varying(255),
    discount_id character varying(255)
);



CREATE TABLE public.discount_product (
    id character varying(255) NOT NULL,
    discount_id character varying(255),
    product_id character varying(255)
);



CREATE TABLE public.email_log (
    id character varying(255) NOT NULL,
    content character varying(255),
    created_date timestamp without time zone,
    email character varying(255),
    sent boolean
);



CREATE TABLE public.exchange (
    id character varying(255) NOT NULL,
    created_by character varying(255),
    created_date timestamp without time zone,
    order_id character varying(255),
    accept boolean
);



CREATE TABLE public.exchange_detail (
    id character varying(255) NOT NULL,
    order_detail_id character varying(255),
    price bigint,
    product_option_id character varying(255),
    qty bigint
);



CREATE TABLE public.exchange_media (
    id character varying(255) NOT NULL,
    exchange_detail_id character varying(255),
    type character varying(255),
    url character varying(255)
);



CREATE TABLE public.favourite (
    id character varying(255) NOT NULL,
    product_id character varying(255),
    user_id character varying(255)
);



CREATE TABLE public.material (
    id character varying(255) NOT NULL,
    active boolean,
    name character varying(255),
    code character varying(255)
);



CREATE TABLE public.notification (
    id character varying(255) NOT NULL,
    content character varying(255),
    created_date timestamp without time zone,
    user_id character varying(255),
    is_read boolean,
    type character varying(255),
    object_type character varying(255),
    object_type_id character varying(255),
    user_type character varying(255),
    template character varying(255)
);



CREATE TABLE public.order_detail (
    id character varying(255) NOT NULL,
    order_id character varying(255),
    price bigint,
    product_option_id character varying(255),
    qty bigint,
    discount bigint,
    created_by character varying(255),
    created_date timestamp without time zone,
    updated_by character varying(255),
    updated_date timestamp without time zone
);



CREATE TABLE public.order_status (
    id character varying(255) NOT NULL,
    created_by character varying(255),
    created_date timestamp without time zone,
    note character varying(255),
    order_id character varying(255),
    status character varying(255)
);



CREATE TABLE public.orders (
    id character varying(255) NOT NULL,
    address_id character varying(255),
    code character varying(255),
    created_by character varying(255),
    created_date timestamp without time zone,
    note character varying(255),
    payed boolean,
    ship_price bigint,
    total bigint,
    updated_by character varying(255),
    updated_date timestamp without time zone,
    user_id character varying(255),
    payment character varying(255),
    ship_method character varying(255),
    status character varying(255),
    discount_id character varying(255),
    ship_price_discount bigint,
    shop_price bigint,
    shop_price_discount bigint,
    ship_type_id character varying(255)
);



CREATE TABLE public.product (
    id character varying(255) NOT NULL,
    active boolean,
    category_id character varying(255),
    created_by character varying(255),
    created_date timestamp without time zone,
    des character varying(2000),
    material_id character varying(255),
    name character varying(255),
    updated_by character varying(255),
    updated_date timestamp without time zone,
    view_number bigint DEFAULT 0
);



CREATE TABLE public.product_option (
    id character varying(255) NOT NULL,
    color_id character varying(255),
    image character varying(255),
    price bigint,
    product_id character varying(255),
    qty bigint,
    size character varying(255),
    size_id character varying(255),
    active boolean
);



CREATE TABLE public.reset_token (
    id character varying(255) NOT NULL,
    created_date timestamp without time zone,
    token character varying(255),
    user_id character varying(255),
    active boolean
);



CREATE TABLE public.review (
    id character varying(255) NOT NULL,
    active boolean,
    content character varying(255),
    created_by character varying(255),
    created_date timestamp without time zone,
    product_id character varying(255),
    rating real,
    review_id character varying(255),
    updated_by character varying(255),
    updated_date timestamp without time zone,
    user_id character varying(255),
    order_id character varying(255)
);



CREATE TABLE public.ship_type (
    id character varying(255) NOT NULL,
    des character varying(255),
    name character varying(255)
);



CREATE TABLE public.size (
    id character varying(255) NOT NULL,
    name character varying(255),
    active boolean,
    created_date timestamp without time zone
);



CREATE TABLE public.suggest (
    id character varying(255) NOT NULL,
    body_height_id character varying(255),
    body_weight_id character varying(255),
    category_id character varying(255),
    size_id character varying(255)
);



CREATE TABLE public.topic (
    id character varying(255) NOT NULL,
    name character varying(255),
    image character varying
);



CREATE TABLE public.transaction_history (
    id character varying(255) NOT NULL,
    amount integer NOT NULL,
    bank_code character varying(255),
    create_date character varying(255),
    invoice_number character varying(255),
    order_id character varying(255),
    order_info character varying(255),
    status character varying(255),
    transaction_no character varying(255)
);



CREATE TABLE public.type (
    id character varying(255) NOT NULL,
    active boolean,
    name character varying(255)
);



CREATE TABLE public.user_notification (
    id character varying(255) NOT NULL,
    notification_id character varying(255),
    user_id character varying(255)
);



CREATE TABLE public.users (
    id character varying(255) NOT NULL,
    active boolean,
    created_by character varying(255),
    created_date timestamp without time zone,
    email character varying(255),
    first_name character varying(255),
    gender boolean,
    last_name character varying(255),
    password character varying(255),
    phone character varying(255),
    role character varying(255),
    updated_by character varying(255),
    updated_date timestamp without time zone,
    dob timestamp without time zone
);



INSERT INTO public.address VALUES ('743eb8f9-0026-4956-9976-8198d5ad6349', true, 'Phường Trúc Bạch, Quận Ba Đình, Thành phố Hà Nội', NULL, '2022-06-26 10:26:45.349', 'Quận Ba Đình', 'Số nhà 170', false, 'Thành phố Hà Nội', NULL, '2022-06-26 10:26:45.349', 'c37f89a1-932c-4350-baed-7eb0d1fb30be', '4', 'Phường Trúc Bạch', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.address VALUES ('bad4e53e-145a-43d4-a07a-d6c77acc027c', true, NULL, '6fac7f97-6c5c-44d5-9ed0-5bfc66326f2b', '2022-08-02 20:48:19.311', 'Quận Cầu Giấy', NULL, true, 'Hà Nội', '6fac7f97-6c5c-44d5-9ed0-5bfc66326f2b', '2022-08-02 20:48:27.19', '6fac7f97-6c5c-44d5-9ed0-5bfc66326f2b', '1A0601', 'Phường Dịch Vọng', 'So 110 3/2', '1485', 'Le Dai Hanh', '0378689632', '201', NULL);
INSERT INTO public.address VALUES ('e62be07e-490b-4d0f-b72c-f8d5da94bfca', true, 'Phường Phúc Xá, Quận Ba Đình, Thành phố Hà Nội', NULL, '2022-06-26 10:26:45.13', 'Quận Hải Châu', 'Số nhà 200', false, 'Đà Nẵng', '6fac7f97-6c5c-44d5-9ed0-5bfc66326f2b', '2022-08-02 20:49:03.831', '6fac7f97-6c5c-44d5-9ed0-5bfc66326f2b', '40105', 'Phường Hòa Cường Bắc', 'So 110 Nguyen Van Cu', '1526', 'Le Van Nam', '0378567412', '203', NULL);
INSERT INTO public.address VALUES ('17ecf9ae-b0ff-4f2e-84b9-185650c57c71', true, '118 Cau Dien, Xã Đắk Ruồng, Huyện Kon Rẫy, Huyện Kon Rẫy', '280a1c4f-40cb-4b19-bde6-322149adc532', '2022-08-28 18:42:09.224', 'Huyện Kon Rẫy', '118 Cau Dien', true, 'Kon Tum', '280a1c4f-40cb-4b19-bde6-322149adc532', '2022-08-28 19:20:24.911', '280a1c4f-40cb-4b19-bde6-322149adc532', '360804', 'Xã Đắk Ruồng', '187 Phu Dien', '2148', 'Tu Nguyen', '0378687421', '259', NULL);
INSERT INTO public.address VALUES ('4e870a12-6b67-4242-8a5c-1bda0be3e958', true, '118 Le Duc Tho, Xã Kon Đào, Huyện Đắk Tô, Huyện Đắk Tô', '280a1c4f-40cb-4b19-bde6-322149adc532', '2022-08-28 18:43:38.572', 'Huyện Đắk Tô', '118 Le Duc Tho', false, 'Kon Tum', '280a1c4f-40cb-4b19-bde6-322149adc532', '2022-08-28 19:20:46.744', '280a1c4f-40cb-4b19-bde6-322149adc532', '360405', 'Xã Kon Đào', '125 Giai Phong', '2121', 'Nguyen Anh Tu', '0358798752', '259', NULL);
INSERT INTO public.address VALUES ('a8375110-c934-4a99-875f-0f5015716cd4', true, 'Thái bình, Xã Phương Trung, Huyện Thanh Oai, Huyện Thanh Oai', '20ef979e-340d-4329-a9c5-387ae4f0d3c4', '2022-08-28 22:41:22.889', 'Huyện Thanh Oai', 'Thái bình', false, 'Hà Nội', '20ef979e-340d-4329-a9c5-387ae4f0d3c4', '2022-08-28 22:41:22.889', '20ef979e-340d-4329-a9c5-387ae4f0d3c4', '1B2414', 'Xã Phương Trung', NULL, '1809', 'ĐỨc Anh', '0355444666', '201', NULL);
INSERT INTO public.address VALUES ('731f1129-b959-47eb-8ec1-c36e26072c6b', true, 'dsaiodusaidbsa, Xã Độc Lập, Huyện Kỳ Sơn, Hòa Bình', 'eb05aa1c-c054-43eb-96e1-72c808170b47', '2022-09-14 16:13:58.741', 'Huyện Kỳ Sơn', 'dsaiodusaidbsa', true, 'Hòa Bình', 'eb05aa1c-c054-43eb-96e1-72c808170b47', '2022-09-14 16:14:02.89', 'eb05aa1c-c054-43eb-96e1-72c808170b47', '230604', 'Xã Độc Lập', NULL, '1955', 'hoangnjsfnej', '0368689238', '267', NULL);
INSERT INTO public.address VALUES ('83e495a4-d63e-490d-8245-2d1cd9f06a16', true, 'Xom 1 Phong Cau, Xã Trung Bình, Huyện Trần Đề, Sóc Trăng', '5e2ec171-97be-4599-922c-0b48fd028589', '2022-08-28 13:26:36.969', 'Huyện Trần Đề', 'Xom 1 Phong Cau', true, 'Sóc Trăng', '5e2ec171-97be-4599-922c-0b48fd028589', '2022-09-06 20:38:52.343', '5e2ec171-97be-4599-922c-0b48fd028589', '591109', 'Xã Trung Bình', '118 Cau Dien', '2037', 'Nguyen Anh Tuan', '0378687973', '218', NULL);
INSERT INTO public.address VALUES ('11160276-4f73-494c-8f4c-74f131199494', true, 'Xom 1 Cau Dien, Phường Mỹ Đình 1, Quận Nam Từ Liêm, Hà Nội', '5e2ec171-97be-4599-922c-0b48fd028589', '2022-09-06 20:38:41.567', 'Quận Nam Từ Liêm', 'Xom 1 Cau Dien', false, 'Hà Nội', '5e2ec171-97be-4599-922c-0b48fd028589', '2022-09-06 20:38:52.345', '5e2ec171-97be-4599-922c-0b48fd028589', '13004', 'Phường Mỹ Đình 1', NULL, '3440', 'Nguyen Anh Tuan', '0378589632', '201', NULL);



INSERT INTO public.blog VALUES ('a02fe526-9a80-4a04-bc79-902aab634b0c', '<h3><strong>Kích cỡ</strong></h3><p><img src="https://blogcaodep.com/wp-content/uploads/2020/03/cach-phoi-quan-ao-cho-nguoi-beo-5-600x765.jpg" alt="" srcset="https://blogcaodep.com/wp-content/uploads/2020/03/cach-phoi-quan-ao-cho-nguoi-beo-5-600x765.jpg 600w, https://blogcaodep.com/wp-content/uploads/2020/03/cach-phoi-quan-ao-cho-nguoi-beo-5-768x979.jpg 768w, https://blogcaodep.com/wp-content/uploads/2020/03/cach-phoi-quan-ao-cho-nguoi-beo-5.jpg 800w" sizes="100vw" width="600"></p><p>Nếu như bạn sở hữu một cặp chân to và ngắn thì nên lựa chọn những chiếc quần jean phù hợp với kích cỡ của cơ thể. Không nên về nhược điểm tự ti về đôi chân mà lựa chọn những chiếc quần có kích thước hơi rộng hơn để che giấu đi, điều này là hoàn toàn sai lầm và bạn có thể vô tình tố giác đôi chân của mình. Việc lựa chọn một chiếc quần không phù hợp về kích cỡ sẽ làm cho bạn cảm giác thùng thình, không thon gọn.</p><p><img src="https://blogcaodep.com/wp-content/uploads/2020/03/c2b279e9ab832b90ad57336272dd79e6-600x600.jpg" alt="" srcset="https://blogcaodep.com/wp-content/uploads/2020/03/c2b279e9ab832b90ad57336272dd79e6-600x600.jpg 600w, https://blogcaodep.com/wp-content/uploads/2020/03/c2b279e9ab832b90ad57336272dd79e6-768x768.jpg 768w, https://blogcaodep.com/wp-content/uploads/2020/03/c2b279e9ab832b90ad57336272dd79e6.jpg 960w" sizes="100vw" width="600"></p><p>Do đó hãy hiểu về số đo cơ thể và lựa chọn những chiếc quần phù hợp, nên ưu tiên lựa chọn những chiếc quần cạp cao vì điều này có thể kéo dài được chiều cao của bạn và nên tận dụng màu sắc tối màu để giúp khéo léo khoe đôi chân thon dài.</p><h3><strong>Kiểu dáng</strong></h3><p>&nbsp;</p><p><img src="https://blogcaodep.com/wp-content/uploads/2020/03/2-1497801423970-600x600.jpg" alt="" srcset="https://blogcaodep.com/wp-content/uploads/2020/03/2-1497801423970-600x600.jpg 600w, https://blogcaodep.com/wp-content/uploads/2020/03/2-1497801423970-768x768.jpg 768w, https://blogcaodep.com/wp-content/uploads/2020/03/2-1497801423970.jpg 1080w" sizes="100vw" width="600"></p><p>Cách chọn quần jean cho người chân to và ngắn cũng nên cần lưu ý đến kiểu dáng, kiểu dáng của chiếc quần có thể ảnh hưởng rất lớn và giúp cho bạn có thể khéo léo che được nhược điểm chân to đùi ếch của mình.</p><p><img src="https://blogcaodep.com/wp-content/uploads/2020/03/882bad10-a6b3-11e7-a828-7bef44811af9-600x536.png" alt="" srcset="https://blogcaodep.com/wp-content/uploads/2020/03/882bad10-a6b3-11e7-a828-7bef44811af9-600x536.png 600w, https://blogcaodep.com/wp-content/uploads/2020/03/882bad10-a6b3-11e7-a828-7bef44811af9-768x685.png 768w, https://blogcaodep.com/wp-content/uploads/2020/03/882bad10-a6b3-11e7-a828-7bef44811af9.png 800w" sizes="100vw" width="600"></p><p>Nên ưu tiên lựa chọn những chiếc quần có phần cạp cao, sẽ giúp che đi được khuyết điểm ở Phần hông, chân và mông. Những người sở hữu đôi chân này có thể lựa chọn những chiếc quần có phần trên ôm sát và phần ống hơi loe. Đây là một trong những cách thức tuyệt vời giúp cho bạn thu nhỏ lại phần mông tròn trịa của mình đồng thời giúp cải thiện được chiều dài của đôi chân.</p><p><img src="https://blogcaodep.com/wp-content/uploads/2020/03/a761890a5e652d18babbdced5799e0c5-600x600.jpg" alt="" srcset="https://blogcaodep.com/wp-content/uploads/2020/03/a761890a5e652d18babbdced5799e0c5-600x600.jpg 600w, https://blogcaodep.com/wp-content/uploads/2020/03/a761890a5e652d18babbdced5799e0c5-768x768.jpg 768w, https://blogcaodep.com/wp-content/uploads/2020/03/a761890a5e652d18babbdced5799e0c5.jpg 800w" sizes="100vw" width="600"></p><p>&nbsp;</p><p>Một điểm mà bạn cần lưu ý khi lựa chọn quần jean đó là nên ưu tiên lựa chọn những chiếc quần jean có phần túi lớn, sâu và rộng. Hai chiếc túi được may gần nhau sẽ có tác dụng rất tốt giúp cho vòng ba của bạn có cảm giác nhỏ hơn. Điều này sẽ giúp cho thân hình của bạn được cải thiện một cách tối ưu.</p><h3><strong>Màu sắc</strong></h3><p><img src="https://blogcaodep.com/wp-content/uploads/2020/03/unnamed-1.jpg" alt=""></p><p>Cách chọn quần jean cho người chân to và ngắn cũng cần lưu ý đến màu sắc. Màu sắc là một trong những phần có thể đóng góp quan trọng Giúp cho chúng ta có thể cải thiện được nhược điểm trên đôi chân.</p><p><img src="https://blogcaodep.com/wp-content/uploads/2020/03/quan-jean-nu-lung-cao-gia-re-thumbnail-600x429.jpg" alt="" srcset="https://blogcaodep.com/wp-content/uploads/2020/03/quan-jean-nu-lung-cao-gia-re-thumbnail-600x429.jpg 600w, https://blogcaodep.com/wp-content/uploads/2020/03/quan-jean-nu-lung-cao-gia-re-thumbnail.jpg 700w" sizes="100vw" width="600"></p><p>Thay vì lựa chọn những chiếc quần jean quá sáng màu thì thay vào đó bạn nên lựa chọn những chiếc quần jean có màu hơi tối hoặc lựa chọn những chiếc quần jean đen là một trong những cách giúp che đi nhược điểm của mình một cách tối ưu nhất.</p><p><img src="https://blogcaodep.com/wp-content/uploads/2020/03/quan-jean-nu-cap-cao-1.jpg" alt=""></p><p>Đặc biệt những kiểu quần jean có màu hơi tối sẽ rất dễ dàng trong việc kết hợp trang phục mà bạn không cần phải lo lắng vì sợ nó không hợp hoặc nó bị chổi khi kết hợp cùng với nhau.</p><ul><li><a href="https://blogcaodep.com/cach-phoi-do-cho-nguoi-lun-1m50/ao-khoac-cnl/">Chọn áo khoác cho người lùn vừa fashion, vừa phù hợp</a></li><li><a href="https://blogcaodep.com/cach-phoi-do-cho-nguoi-lun-1m50/dam-cho-nguoi-thap-tron/">Cách chọn đầm cho người thấp tròn: tưởng khó nhưng lại dễ</a></li><li><a href="https://blogcaodep.com/cach-phoi-do-cho-nguoi-lun-1m50/giay-an-gian-chieu-cao-cho-nu/">Giày ăn gian chiều cao cho nữ: Giải pháp cho các bạn “nấm lùn”</a></li><li><a href="https://blogcaodep.com/cach-phoi-do-cho-nguoi-lun-1m50/jumpsuit/">5 cách mặc Jumpsuit cho người thấp cực kì vi diệu !!!</a></li></ul><h3><strong>Quần jean cạp cao</strong></h3><p><img src="https://blogcaodep.com/wp-content/uploads/2019/09/s1.jpg" alt=""></p><p>Quần jean cạp cao được xem là một trong những kiểu quần jean sinh ra là dành cho những cô nàng có chiều cao khiêm tốn. Đây là một trong những kiểu quần có thể giúp cho bạn ăn gian được chiều cao một cách khá hữu hiệu, Là một trong những kiểu quần cần phải có trong bất kỳ tủ đồ của những cô nàng nấm lùn.</p><h3><strong>Quần rách gối</strong></h3><p><img src="https://blogcaodep.com/wp-content/uploads/2019/09/phoi_do_cho_nguoi_chan_to_nu_2_3d5fc464bab34d1b95afdebb41f99204_grande.jpg" alt=""></p><p>Chọn quần jean cho người chân to và ngắn có thể ưu tiên lựa chọn những kiểu quần rách gối. Đây được xem là một trong những kiểu quần có thể đánh lừa được thì giá của người nhìn một cách tốt nhất. Thay vì quá chú ý vào phần đùi thì người ta sẽ chú ý vào phần rách ở phần gối. Đây là một trong những item thời trang mà bạn không thể bỏ qua nếu sở hữu một cặp đùi to.</p><p><img src="https://blogcaodep.com/wp-content/uploads/2020/03/quan-jean-nu-cap-cao-1.jpg" alt=""><img src="https://blogcaodep.com/wp-content/uploads/2020/03/2015streetwearbluetrendybaggybrokenholewomenjeans-xhe042904bu-7015-20170702011830-600x867.jpg" alt="" srcset="https://blogcaodep.com/wp-content/uploads/2020/03/2015streetwearbluetrendybaggybrokenholewomenjeans-xhe042904bu-7015-20170702011830-600x867.jpg 600w, https://blogcaodep.com/wp-content/uploads/2020/03/2015streetwearbluetrendybaggybrokenholewomenjeans-xhe042904bu-7015-20170702011830.jpg 700w" sizes="100vw" width="600"></p><p>Đặc biệt loại quần này cũng rất dễ dàng để kết hợp trang phục tạo nên những phong cách thời trang khác nhau. Không chỉ khéo léo che đi nhược điểm trên cơ thể của bạn mà nó còn biến bạn trở thành một trong những tín đồ đồ thời trang theo phong cách street style đường phố.</p><p><img src="https://blogcaodep.com/wp-content/uploads/2020/03/cach-phoi-do-cho-nguoi-lun-25-600x360.jpg" alt="" srcset="https://blogcaodep.com/wp-content/uploads/2020/03/cach-phoi-do-cho-nguoi-lun-25-600x360.jpg 600w, https://blogcaodep.com/wp-content/uploads/2020/03/cach-phoi-do-cho-nguoi-lun-25.jpg 700w" sizes="100vw" width="600"></p><p>&nbsp;</p><p>Bài viết đã chia sẻ cách chọn quần jean cho người chân to và ngắn, hy vọng những thông tin mà chúng tôi chia sẻ giúp ích cho bạn trong việc lựa chọn các các món đồ thời trang phù hợp. Quần jean là một trong những loại quần thích hợp với nhiều sự biến tấu thời trang do đó đừng ngại về việc chúng ta sở hữu một cặp chân ngắn, đùi ếch mà từ bỏ một một trong những quần áo thời trang có tính ứng dụng cao như vậy. Chúc các bạn vui và có thể để tự tin hơn với phong cách mix đồ với quần jean.</p>', '2022-09-08 22:00:14.572', 'Cách chọn quần jean cho người chân to và ngắn không nên bỏ qua', '2', 'https://luvinus.com/wp-content/uploads/2021/04/cach-phoi-do-voi-quan-jean-cho-nguoi-map-13.jpg', true, 'Cách chọn quần jean cho cách mặc đồ cho người lùn mập chân to và ngắn phù hợp cần lưu ý một số điểm sau đây để đảm bảo vừa che được khuyết điểm vừa đảm bảo được yếu tố thời trang khi kết hợp với những loại áo kiểu khác nhau.', NULL);
INSERT INTO public.blog VALUES ('d7f87c88-8469-4373-9e11-3c11c3f223g9', '<p><i><strong>Nhiều người nghĩ rằng học sinh nam chỉ cần mặc chỉn chu là được. Tuy nhiên, thời trang được sinh ra để dành cho tất cả mọi người. Dưới đây, YODY sẽ chia sẻ với các bạn cách phối đồi nam học sinh cực sành điệu nhưng vẫn đảm bảo yếu tố gọn gàng.</strong></i></p><p>&nbsp;</p><h2>1. Kết hợp áo sơ mi cùng quần âu/denim&nbsp;- Cách phối đồ nam học sinh chuẩn đẹp</h2><p><img src="https://bizweb.dktcdn.net/100/438/408/files/cach-phoi-do-nam-hoc-sinh-yodyvn-jpeg.jpg?v=1661657918016" alt="cách phối đồ nam học sinh"></p><p><i>Sơ mi trắng mix cùng quần tây ghi</i></p><p>Sơ mi, quần bò chắc hẳn là 2 item không còn quá xa lạ gì đối với những ai đang đi đam mê phong cách thanh lịch, trẻ trung. Ở bản phối này, sơ mi mix cùng quần jeans sẽ đem lại vẻ cá tính, năng động cho người mặc. Trong khi đó, quần âu&nbsp;thì lại làm người mặc toát lên vẻ sang trọng, lịch sự.</p><p>Khi sử dụng 2 mẫu quần nào, các bạn có thể biến hoá trang phục của mình một chút bằng cách sơ vin và đeo thêm thắt lưng. Ngoài ra, cũng đừng quên kết hợp thêm chiếc đồng hồ thời thượng nhé!</p><h2>2. Áo phông phối với quần kaki/jeans - Thời trang basic luôn hợp mốt</h2><p><img src="https://bizweb.dktcdn.net/100/438/408/files/cach-phoi-do-nam-hoc-sinh-yodyvn1.jpg?v=1661657998279" alt="cách phối đồ nam học sinh"></p><p><i>Set đồ basic, trẻ trung đi học</i></p><p>Để không phải tốn nhiều thời gian trong khâu chọn outfit đi học vào mỗi sáng, bạn hãy áp dụng ngay công thức phối đồ basic áo thun quần kaki. Với option này, anh em nên ưu tiên những item có size vừa vặn để tổng thể trở nên gọn gàng, chỉn chu.</p><p>Bên cạnh đó, mọi người cũng nên thử qua combo áo thun, quần denim để giữ nét trẻ trung vốn có của tuổi học trò. Về phụ kiện thì sneaker chắc chắn là item không thể thiếu. Không những tạo cảm giác thoải mái, dễ chịu, đôi giày thời thượng này còn đem đến cho anh em một diện mạo vô cùng bảnh bao và cá tính.</p><h2>3. Áo thun mix cùng quần jogger - Option hoàn hảo của những chàng trai cá tính</h2><p><img src="https://bizweb.dktcdn.net/100/438/408/files/cach-phoi-do-nam-hoc-sinh-yodyvn2.jpg?v=1661658042619" alt="cách phối đồ nam học sinh"></p><p><i>Áo thun trắng phối cùng jogger xanh rêu</i></p><p>Phần lớn quần jogger được làm từ chất liệu co giãn, phần ống quần bó lại và ôm sát vào mắt cá chân. Kiểu thiết kế này mang lại cho người dùng một trải nghiệm vô cùng thoải mái, dễ chịu.</p><p>Với những buổi học thêm, hoạt động ngoại khoá, hoạt động thể thao,..combo này chính là option lý tưởng nhất dành cho bạn. Đặc biệt là đối với những chàng trai muốn thể hiện phong cách hip hop cá tính của mình.</p><p>Chưa kể, việc phối item này với áo phông đơn giản sẽ tạo nên một set đồ cực ăn ý và hài hoà, giúp gây ấn tượng với mọi người xung quanh.</p><h2>4. Nâng tầm diện mạo với set đồ oversize</h2><p><img src="https://bizweb.dktcdn.net/100/438/408/files/cach-phoi-do-nam-hoc-sinh-yodyvn3.jpg?v=1661658073848" alt="cách phối đồ nam học sinh"></p><p><i>Outfit oversize mặc đến trường</i></p><p>Điểm qua những cách phối đồ nam học sinh, chúng ta không thể bỏ qua những set đồ oversize. Đây là outfit hoàn hảo để diện vào những ngày tiết trời nóng bức. Chúng giúp mọi vận động của anh em trở nên thoải mái và dễ dàng hơn rất nhiều.</p><p>Với outfit này, mọi người có thể phối quần cargo với áo thun oversize hoặc sơ mi thanh lịch. Tất cả đều phù hợp để diện đến lớp.</p><p>Bên cạnh đó, để diện mạo thêm phần hiphop và cá tính, các bạn có thể mang thêm 1 chiếc vòng cổ hoặc kính râm cool ngầu.</p><h2>5. Phối hợp áo polo, quần tây - Set đồ chuẩn thời trang</h2><p><img src="https://bizweb.dktcdn.net/100/438/408/files/cach-phoi-do-nam-hoc-sinh-yodyvn4.jpg?v=1661658247413" alt="cách phối đồ nam học sinh"></p><p><i>Nam học sinh mặc gì đến trường</i></p><p>Nếu chiếc áo thông đem lại cho người mặc nét trẻ trung, cá tính. Thì áo polo lại có phần chỉn chu, lịch sự hơn. Loại áo thun này có phần cổ khá giống với sơ mi cùng 2 đến 3 nút khuy được trang trí ở cổ.</p><p>Về quần thì các bạn có thể mix cùng quần tây. Vẻ thanh lịch của áo polo kết hợp cùng nét trưởng thành của quần âu sẽ tạo nên một bộ outfit vô cùng lịch sự nhưng cũng không kém phần trẻ trung. Khi diện combo này, các bạn nên ưu tiên những item có size vừa vặn với cơ thể nhất. Điều này sẽ khiến tổng thể của bạn trông rất gọn gàng và bắt mắt.</p><h2>6. Mặc sơ mi cùng áo len mỏng - Phong cách thời trang của idol Hàn Quốc</h2><p><img src="https://bizweb.dktcdn.net/100/438/408/files/cach-phoi-do-nam-hoc-sinh-yodyvn5-5ecbf89f-e938-47f4-809c-f7f2ce9d8284.jpg?v=1661658336263" alt="cách phối đồ nam học sinh"></p><p><i>Set đồ chuẩn idol Hàn Quốc</i></p><p>Trong những năm gần đây, xu hướng ăn mặc giống idol Hàn Quốc được rất nhiều bạn trẻ lăng xê. Trong đó phải kể đến bản phối sơ mi bên trong và áo len mỏng bên ngoài. Đây là outfit vừa có thể có khả năng giữ ấm cho cơ thể cực tốt, đồng thời đem lại một diện mạo chuẩn soái ca vô cùng đẹp trai và dễ thương.</p><p>Để hoàn thiện set đồ, anh em có thể phối cùng quần tây hoặc quần jeans basic.</p><h2>7. Thu hút mọi ánh nhìn với hoodie, quần thể thao</h2><p><img src="https://bizweb.dktcdn.net/100/438/408/files/cach-phoi-do-nam-hoc-sinh-yodyvn6.jpg?v=1661658399472" alt="cách phối đồ nam học sinh"></p><p><i>Outfit đi học</i></p><p>Mỗi khi đông đến, mẫu áo hoodie lại làm khuấy đảo làng thời trang với nhiều kiểu thiết kế mới lại và độc đáo. Item này phần lớn được làm từ chất liệu thun, nỉ với phần mũ được gắn đằng sau. Các bạn có thể phối áo hoodie với rất nhiều trang phục khác nhau, tất cả đều phù hợp.</p><p>Tuy nhiên, nếu muốn mặc chuẩn phong cách hiphop, bụi bặm thì chiếc quần thể thao 3 sọc sẽ option lý tưởng nhất.</p><h2>8. Gấp đôi sành điệu khi mặc bomber với áo thun</h2><p>Áo bomber phối cùng áo thun luôn là cách phối đồ nam học sinh hoàn hảo để diện vào nhiều dịp khác nhau, từ đi học cho đến đi du lịch. Đây là outfit đem đến cho người mặc sự phóng khoáng, cá tính nhưng cũng không kém phần lịch sự và chỉnh chu. Với 2 item này, anh em có thể mix cùng rất nhiều loại quần khác nhau mà trông vẫn rất thời trang và sành điệu.</p><p>Nếu đam mê phong cách năng động, tươi trẻ, mọi người có thể kết hợp với quần bò hoặc quần kaki tối màu. Còn đối với những anh chàng bụi bặm, cá tính thì quần jeans rách gối sẽ là option phù hợp hơn.</p><p>Về phụ kiện, các bạn có thể mang theo giày boot hoặc sneaker. Đây sẽ chính là điểm nhấn giúp trang phục của bạn nổi bật hơn gấp bội.</p><p><img src="https://bizweb.dktcdn.net/100/438/408/files/cach-phoi-do-nam-hoc-sinh-yodyvn7.jpg?v=1661658518987" alt="cách phối đồ nam học sinh"></p><p><i>Set đồ thời trang đi học</i></p><p>Phía trên là những cách phối đồ nam học sinh mà YODY muốn gửi tới các bạn. Mong rằng với những gợi ý này, các chàng trai sẽ có nhiều niềm cảm hứng hơn trong việc biến tấu outfit đi học mỗi ngày.</p>', '2022-09-12 11:04:43.701', 'ABC', '1', 'https://res.cloudinary.com/anhcoming/image/upload/v1662955478/paw8ubfdi1qzxapjzo6t.jpg', true, 'Nếu bạn bỏ qua phong cách này, thì quả thật bạn đã bỏ qua cách mix đồ đơn giản, nhưng chưa bao giờ lỗi mode.

', NULL);
INSERT INTO public.blog VALUES ('d7f87c88-8469-4373-9e11-3c11c3f223f9', '<p>Nếu hỏi rằng trang phục nào dễ phối đồ và hợp với tất cả mọi dáng người thì chắc chắn quần jean, áo sơ mi chính là những cái tên được nhắc đến đầu tiên. Và có một sự thật rằng mix quần jean với áo sơ mi cũng cực đẹp và thời trang. Trong bài viết ngày hôm nay, Cardina sẽ chia sẻ với các bạn 6 cách mặc áo sơ mi với quần jean vừa trẻ trung mà vẫn giữ được nét thanh lịch cho các quý cô. Bên cạnh đó còn gợi ý cho các bạn một số lưu để chọn áo sơ mi sao cho phù hợp nhất.</p><p><img src="https://file.hstatic.net/1000317075/file/6-cach-mac-ao-so-mi-voi-quan-jean-cardina_3e9cc96459414cd6a4a3ec07f5e865ca.jpg" alt="Bỏ túi 6 cách mặc áo sơ mi với quần jean trẻ trung nhưng vẫn thanh lịch"></p><p><i>Mix áo sơ mi với quần jean thế nào thời trang và hợp mốt nhất?</i></p><h2><strong>Các cách mix áo sơ mi với quần jean cá tính và xinh gái&nbsp;</strong></h2><h3><strong>Mặc áo sơ mi kẻ sọc với một chiếc quần jean</strong></h3><p>Cách mặc áo sơ mi với quần jean đầu tiên các bạn có thể tham khảo là mix quần jean cùng với một chiếc áo sơ mi kẻ sọc. Đây là cách mix đồ cực phù hợp cho những cô nàng thích sự năng động và trẻ trung nhưng vẫn muốn set đồ của mình có sự thanh lịch. Nếu như bạn là một cô nàng mũm mĩm thì những chiếc áo sơ mi kẻ sọc chắc chắn sẽ là lựa chọn tuyệt vời. Thiết kế này sẽ giúp cho bạn nhìn thon thả hơn nhiều đó.</p><p>Đặc biệt, các bạn có thể chọn những chiếc quần jean gấu cao hoặc xắn gấu quần lên một hai gấp để tổng thể outfit sành điệu và cuốn hút hơn nhé. Hãy mix cùng với một đôi giày thể thao nếu như bạn muốn mình thoải mái và năng động. Nếu không, hãy mix cùng với một đôi giày cao gót để đem đến sự dịu dàng và tinh tế.</p><p><img src="https://file.hstatic.net/1000317075/file/mac-ao-so-mi-ke-soc-voi-quan-jean_abe4c6e8f09d47adad761ffcfe68a227.jpg" alt="Bỏ túi 6 cách mặc áo sơ mi với quần jean trẻ trung nhưng vẫn thanh lịch"></p><p><i>Mặc áo sơ mi kẻ sọc với một chiếc quần jean</i></p><h3><strong>Mặc áo sơ mi oversize với quần jean</strong></h3><p>Với những cô nàng thích phong cách thời trang cá tính và năng động thì chắc chắn không thể bỏ qua cách cách mặc áo sơ mi với quần jean thứ hai này. Một chiếc áo sơ mi oversize sẽ vùa phá cách mà lại cực độc đáo đó. Mix cùng với một đôi giày đế bệt, một chiếc balo và một chiếc mũ lưỡi trai. Tưởng tượng thôi đã thấy hơi thở của thanh xuân phơi phới rồi.</p><p>Tất nhiên, các cô nàng công sở cũng có thể chọn item này. Hãy sơ vin vạt trước của chiếc áo vào trong quần, chọn thêm một chiếc túi xách nữ tính và một chút phụ kiện như kính mắt, vòng cổ, vòng tay,.. Chắc chắn các bạn sẽ là ngôi sao sáng nhất của cả công ty đó.</p><p>Về họa tiết, các bạn cũng có thể chọn những chiếc áo có họa tiết ngộ nghĩnh hoặc họa tiết hoa, chấm bi,... Chọn sao cũng được, miễn là các chị em thích.</p><p><img src="https://file.hstatic.net/1000317075/file/mac-ao-so-mi-trang-voi-quan-jean__2__5a123347e0074a1aae458f8de77874ab.jpg" alt="Bỏ túi 6 cách mặc áo sơ mi với quần jean trẻ trung nhưng vẫn thanh lịch"></p><p><i>Mặc áo sơ mi oversize với quần jean</i></p><h3><strong>Mix quần jean với áo sơ mi cổ trụ (cổ tàu)</strong></h3><p>Bên cạnh những mẫu áo sơ mi cổ đức thông thường, các bạn cũng có thể chọn những chiếc áo sơ mi có thiết kế độc đáo khác như cổ sen, cổ tàu, cổ trụ hay thậm chí là không cổ cũng rất đẹp. Những chiếc áo sơ mi cổ trụ sẽ ít đi sự trang trọng và nghiêm túc nhưng cũng vẫn rất thanh lịch và duyên dáng. Một set đồ gồm một chiếc áo sơ mi cổ trụ và một chiếc quần jean cạp cao sẽ là một sự lựa chọn không tồi dành cho chị em.</p><p>Mix cùng với một đôi giày cao gót hoặc một đôi sneakers đế cao sẽ giúp cho nàng nữ tính và thời trang hơn đó. Bên cạnh quần jean cạp cao, các bạn cũng có thể mix chiếc áo sơ mi này với những chiếc quần culottes. Thiết kế ống rộng sẽ giúp che khuyết điểm cơ thể, đặc biệt là che đi đôi chân quá nhỏ hay quá to của bạn.</p><blockquote><p><strong>Xem thêm</strong>:&nbsp;<a href="https://cardina.vn/blogs/news/cach-phoi-do-voi-giay-the-thao-do">Cách phối đồ với giày thể thao đỏ</a></p></blockquote><p>Tại Cardina, <a href="https://cardina.vn/collections/quan-culottes-nu">các mẫu quần culottes</a> cũng rất đa dạng, nhiều màu sắc và mẫu mã cho chị em chọn lựa. Một chiếc quần culottes chất đũi cũng là item đáng cân nhắc bên cạnh những chiếc quần jean thông thường. Đừng ngần ngại thử sức trong những set đồ mơi nhé.</p><p><img src="https://file.hstatic.net/1000317075/file/mac-ao-so-m

', NULL);
INSERT INTO public.blog VALUES ('9c54c4ce-1e5a-411f-9c47-9410e9edd764', '<p>Viết gì đó nào...<br><img src="https://cdn.gumac.vn/image/01/thoi-trang/don-gian/phoi-vest-voi-ao-thun-nu-don-gian020620201201357201.jpg" alt="Cách phối đồ đơn giản mà đẹp cho nữ"></p><h2><strong>1.Cách phối đồ đơn giản mà đẹp cho nữ</strong></h2><p><strong>Cách phối đồ đơn giản mà đẹp </strong>luôn được các nàng chuộng phong cách tối giản, đặc biệt là phong cách minimalism sang trọng đang thịnh hành trong giới thời trang hiện đại.</p><h3><strong>Cách phối đồ nữ đơn giản với quần jean</strong></h3><p>Những mẫu quần jean được thiết kế với kiểu dáng cực đa dạng từ mom jean, baggy jean, jean ống loe, skinny jean,.. Nhưng các nàng chắc chắn phải thừa nhận chiếc skinny luôn biết cách hút mắt nàng mỗi lần muốn diện quần jean với áo thun đơn giản.</p><p><strong>Cách phối đồ đơn giản </strong>này nàng chỉ cần phối thêm với giày thể thao trắng thôi cũng đã đầy đủ phụ kiện chuẩn cho một set đồ hoàn hảo.<br><br><i>&gt;&gt;&gt; Cách phối đồ với quần jean, </i><a href="https://gumac.vn/quan-dai"><i><strong>quần dài</strong></i></a><i> đơn giản này nàng chỉ cần phối thêm với giày thể thao trắng thôi cũng đã đầy đủ phụ kiện chuẩn cho một set đồ hoàn hảo.</i><br>&nbsp;</p><p><img src="https://cdn.gumac.vn/image/01/thoi-trang/don-gian/cach-phoi-do-don-gian-cho-nu-voi-quan-jean020620201201357201.jpg" alt="Cách phối đồ đơn giản mà đẹp cho nữ"></p><h3><strong>Phối vest với áo thun nữ đơn giản</strong><br>&nbsp;</h3><p>Áo vest được xem là item phổ biến nhất cho nàng diện đặc trưng cho không gian công sở sang trọng. Những mẫu áo vest đơn giản khi diện với áo thun trắng và chân váy hoặc quần cùng tông màu thì nàng đã khéo léo diện chuẩn phong cách ton-sur-ton rồi đấy!<br><br><i>&gt;&gt;&gt; GUMAC mời bạn xem ngay bộ sưu tập </i><a href="https://gumac.vn/ao-thun"><i><strong>áo thun</strong></i></a><i> mới, xu hướng trendy nhất hôm nay, đừng bỏ qua khuyến mãi nhé!</i><br>&nbsp;</p><h3><strong>Quần jean giả váy</strong></h3><p>Quần jean giả váy mặc với áo gì đẹp hơn cho kiểu phong cách đơn giản. Nàng chuộng chất liệu jean chắc chắn không thể bỏ qua set đồ quần jean giả váy phối đơn giản với áo phông trơn hoặc họa tiết tối giản.</p><p>Kiểu phối này nàng dễ dàng <strong>phối đồ đơn giản</strong> khi đi kèm phụ kiện giày như các mẫu boot cổ trung vừa qua mắt cá hoặc giày thể thao năng động như balenciaga hoặc chỉ đơn giản là đôi sandal đế trung chuẩn sang chảnh nơi công sở.</p><p><img src="https://cdn.gumac.vn/image/01/thoi-trang/don-gian/cach-phoi-do-don-gian-ma-dep-quan-gia-vay020620201201357358.jpg" alt="Cách phối đồ đơn giản mà đẹp cho nữ">&nbsp;</p><p><i>&gt;&gt;&gt; CHIÊM NGƯỠNG item chân váy jean công sở<strong>&nbsp;</strong>ĐỘC ĐÁO cho phái đẹp được diện chuẩn phong cách&nbsp;</i><a href="https://gumac.vn/thoi-trang"><i><strong>thời trang nữ</strong></i></a><i> hiện đại trẻ trung và tràn đầy sự tự tin.</i></p><h3><strong>Đầm sơ mi suông</strong></h3><p>Với chiếc váy sơ mi thiết kế cách tân so với những kiểu đầm truyền thống thì chiếc đầm sơ mi tông trắng hoặc tông đen chủ đạo đúng chuẩn phong cách đơn giản được các nàng chuộng cách <strong>phối đồ đơn giản cho nữ </strong>ưu tiên hơn hẳn.</p><p>Mẫu đầm này nàng phối với giày trong suốt cực thích hợp khi gặp gỡ đối tác hoặc có sự kiện quan trọng cần được chỉnh chu và khẳng định được khả năng phối đồ chuẩn fashionista của mình.</p><p><img src="https://cdn.gumac.vn/image/01/thoi-trang/don-gian/phoi-do-don-gian-cho-nu-dam-so-mi020620201201357358.jpg" alt="Đầm sơ mi suông">&nbsp;</p><h3><strong>Đầm suông che bụng</strong></h3><p><strong>Phối đồ đơn giản mà đẹp cho nữ </strong>với đầm suông khá thực tế cho các nàng công sở ngồi nhiều nhưng không có thời gian tập luyện khiến bụng ngày càng “phì nhiêu”.</p><p>Mẫu đầm đơn giản sang trọng này thích hợp cho nàng mặc diện đến công sở hằng ngày để toát lên nét sang trọng mặc cho chiếc bụng con kiến giờ đã đi xa.&nbsp;<br><br><i>&gt;&gt;&gt; Các mẫu váy đầm suông&nbsp;luôn được các chị em văn phòng ưu tiên lựa chọn vì độ thoải mái mà nó mang lại. Tuy nhiên nàng hãy lựa chọn thật kỹ các mẫu mã phù hợp với dáng mình nhé!</i><br>&nbsp;</p><p><img src="https://cdn.gumac.vn/image/01/thoi-trang/don-gian/don-gian-sang-trong-dam-suong020620201201357201.jpg" alt="Cách phối đồ đơn giản mà đẹp cho nữ">&nbsp;</p><p><i>&gt;&gt;&gt;&nbsp;Cách </i><a href="https://gumac.vn/phoi-do-voi-vay-suong"><i><strong>PHỐI ĐỒ với váy suông</strong></i></a><i> ĐƠN GIẢN và MẸO chọn váy ĐẸP nàng nhất định phải biết để không "phá nát" sự lộng lẫy của từng phụ kiện trước khi diện.</i></p><h3><strong>Chân váy xếp ly dài phối sơ mi</strong><br>&nbsp;</h3><p>Kiểu phối này phù hợp cho các nàng khi bước vào mùa đông với <strong>cách phối đồ đơn giản cho nữ</strong>. Cách phối đồ với chân váy xếp ly dài mùa đông này cho nàng nét thanh cao nhưng không hề đơn điệu.&nbsp;</p><p>Chi tiết xếp ly dáng dài đình đám trong làng thời trang và chưa hề có dấu hiệu lỗi thời. Độ dài chân váy phù hợp cho nàng che được khuyết điểm bắp chân to, dễ dàng phối với nhiều loại giày công sở.&nbsp;</p><p><img src="https://cdn.gumac.vn/image/01/thoi-trang/don-gian/cach-phoi-do-don-gian-cho-nu-vay-xep-ly-dai020620201201357201.jpg" alt="Chân váy xếp ly dài phối sơ mi">&nbsp;</p><h3><strong>Quần tây lưng cao ống suông phối sơ mi</strong></h3><p><strong>Cách phối đồ đơn giản mà chất cho nữ </strong>với quần tây dáng cơ bản này chắc chắn không thể thiếu.&nbsp;</p><p>Không hẳn đơn giản thì nàng phải diện toàn tông trắng và đen đúng phong cách minimalism cổ điển. Thời trang không gò bó sức sáng tạo của con người. Chẳng hạn với câu hỏi mệnh thổ hợp màu vàng không thì nàng mệnh thổ hoặc mệnh kim thì diện chiếc quần tây vàng phối áo sơ mi trắng cực chuẩn vừa hợp phong thủy vừa đơn giản, vừa đẹp.</p><h2><strong>2.Mẹo phối đồ đơn giản mà chất</strong></h2><h3><strong>Phối phụ kiện đơn giản</strong></h3><p>Không hẳn kiểu thời trang đơn giản thì cũng lược bỏ hết phụ kiện đi kèm. Tất cả chỉ là phải qua sự tuyển chọn kỹ lưỡng hơn để chọn phối cùng.</p><p>Chẳng hạn chiếc túi xách dây nhỏ tông xám sẽ thích hợp với mọi set đồ bạn diện. Hay chút lấp lánh từ chiếc vòng cổ bạc dây nhuyễn cũng khá lý thú cho set đồ!&nbsp;</p><h3><strong>Họa tiết đơn giản</strong></h3><p><strong>Phối đồ đơn giản mà chất </strong>điểm nhấn có lẽ chính là phần họa tiết để nói lên tổng thể sự hài hòa của bộ trang phục. Chẳng hạn họa tiết độc nhất trên chiếc áo thun trơn là chú gấu Winnie the Pooh thích ăn mật với nước da vàng sáng ánh sẽ tạo cảm giác nàng tinh nghịch, có cá tính hơn!</p><h3><strong>Tận dụng phong cách đơn giản minimalism&nbsp;</strong></h3><p>Minimalism là gì là câu hỏi cực hot trong thời gian vừa qua khi kiểu phong cách này nàng cứ diện lên dù cực đơn giản nhưng lại cực sang trọng và quý phái nếu tuân thủ đúng và chuẩn quy tắc.</p><p><strong>Cách phối đồ đơn giản mà chất</strong> này chuộng kiểu tiết chế màu sắc mà chỉ tập trung các màu trắng, đen và sắc thái pha loãng của chúng. Những chi tiết trên áo quần được đưa về kiểu cơ bản - điều này phần nào giúp người nhìn có cảm giác cổ điển - mà cổ điển luôn mang lại sự sang trọng nhất định khi diện.</p><p><img src="https://cdn.gumac.vn/image/01/thoi-trang/don-gian/quan-tay-lung-cao-ong-suong-phoi-so-mi020620201201357201.jpg" alt="Tận dụng phong cách đơn giản minimalism ">&nbsp;</p><p><i><strong>&gt;&gt;&gt;Cách phối đồ đơn giản mà đẹp cho nữ </strong>không quá khó để thực hiện. Mẫu quần áo công sở trẻ trung thanh lịch với quần áo vest nữ</i>&nbsp;<i>vừa đơn giản lại không kén dáng cho nàng tha hồ lựa chọn để càng ngày càng thời trang, càng xinh đẹp.</i></p>', '2022-09-08 22:07:13.898', 'Mẹo MÁCH NHỎ nàng với 7 cách phối đồ ĐƠN GIẢN mà ĐẸP cho nữ ', '3', 'https://res.cloudinary.com/anhcoming/image/upload/v1662649629/bglm9drnksywqbqkwvg7.jpg', true, 'Cách phối đồ đơn giản mà đẹp cho nữ với 7 kiểu phối cực kỳ dễ thực hiện và những mẹo nhỏ mà shop thời trang Gumac mách riêng cho nàng yêu GU biết để phối theo cho chuẩn tông. Cùng GU tham khảo nhanh nào!', NULL);
INSERT INTO public.blog VALUES ('6', '6', '2022-09-08 22:07:13.898', '6', '3', 'https://mega.com.vn/media/news/0406_anh-gai-xinh-104.jpg', true, 'Cách phối đồ đơn giản mà đẹp cho nữ với 7 kiểu phối cực kỳ dễ thực hiện và những mẹo nhỏ mà shop thời trang Gumac mách riêng cho nàng yêu GU biết để phối theo cho chuẩn tông. Cùng GU tham khảo nhanh nào!', NULL);
INSERT INTO public.blog VALUES ('5', '5', '2022-09-08 22:07:13.898', '5', '2', 'https://mega.com.vn/media/news/0406_anh-gai-xinh-104.jpg', true, 'Cách phối đồ đơn giản mà đẹp cho nữ với 7 kiểu phối cực kỳ dễ thực hiện và những mẹo nhỏ mà shop thời trang Gumac mách riêng cho nàng yêu GU biết để phối theo cho chuẩn tông. Cùng GU tham khảo nhanh nào!', NULL);
INSERT INTO public.blog VALUES ('4', '4', '2022-09-08 22:07:13.898', '4', '4', 'https://mega.com.vn/media/news/0406_anh-gai-xinh-104.jpg', true, 'Cách phối đồ đơn giản mà đẹp cho nữ với 7 kiểu phối cực kỳ dễ thực hiện và những mẹo nhỏ mà shop thời trang Gumac mách riêng cho nàng yêu GU biết để phối theo cho chuẩn tông. Cùng GU tham khảo nhanh nào!', NULL);
INSERT INTO public.blog VALUES ('3', '3', '2022-09-08 22:07:13.898', '3', '3', 'https://mega.com.vn/media/news/0406_anh-gai-xinh-104.jpg', true, 'Cách phối đồ đơn giản mà đẹp cho nữ với 7 kiểu phối cực kỳ dễ thực hiện và những mẹo nhỏ mà shop thời trang Gumac mách riêng cho nàng yêu GU biết để phối theo cho chuẩn tông. Cùng GU tham khảo nhanh nào!', NULL);
INSERT INTO public.blog VALUES ('2', '2', '2022-09-08 22:07:13.898', '2', '2', 'https://mega.com.vn/media/news/0406_anh-gai-xinh-104.jpg', true, 'Cách phối đồ đơn giản mà đẹp cho nữ với 7 kiểu phối cực kỳ dễ thực hiện và những mẹo nhỏ mà shop thời trang Gumac mách riêng cho nàng yêu GU biết để phối theo cho chuẩn tông. Cùng GU tham khảo nhanh nào!', NULL);
INSERT INTO public.blog VALUES ('1', '1', '2022-09-08 22:07:13.898', '1', '1', 'https://mega.com.vn/media/news/0406_anh-gai-xinh-104.jpg', true, 'Cách phối đồ đơn giản mà đẹp cho nữ với 7 kiểu phối cực kỳ dễ thực hiện và những mẹo nhỏ mà shop thời trang Gumac mách riêng cho nàng yêu GU biết để phối theo cho chuẩn tông. Cùng GU tham khảo nhanh nào!', NULL);
INSERT INTO public.blog VALUES ('3229d702-9ef8-4305-b957-beaacc764f2b', '<p>&nbsp;</p><p>Để biết được Ulzzang Style là gì, đầu tiên phải tìm hiểu nghĩa của từ Ulzzang. Trong tiếng Hàn Quốc, Ulzzang được kết hợp bởi chữ ul nghĩa là mặt và zzang nghĩa là nhất. Khi ghép lại với nhau thì từ Ulzzang có nghĩa là chỉ những người có gương mặt đẹp nhất.</p><p><img src="https://andora.com.vn/wp-content/uploads/2022/04/ulzzang-la-gi-1.jpg" alt="Ulzzang là gì? Bật mí 12 phong cách Phối Đồ Ulzzang nữ đang HOT TREND"></p><p><i>Ulzzang là gì? Bật mí 12 phong cách Phối Đồ Ulzzang nữ đang HOT TREND</i></p><p>Còn nếu muốn sở hữu một phong cách thể hiện được cá tính riêng, bạn có thể mix quần áo theo phong cách Ulzzang Style như sau:</p><h3>Áo phông + Chân váy denim + Giày sneaker</h3><p>Áo phông là một item không thể thiếu trong tủ đồ của giới trẻ đúng không nào. Nếu muốn theo phong cách Ulzzang, thì các nàng hãy mix áo phông với chân váy denim, phối cùng một đôi giày sneaker là đã có một phong cách trẻ trung, xinh xắn rồi đấy.</p><p><img src="https://andora.com.vn/wp-content/uploads/2022/04/ulzzang-la-gi-2.jpg" alt="Áo phông + Chân váy denim + Giày sneaker"></p><p><i>Áo phông + Chân váy denim + Giày sneaker</i></p><p>Hãy nhớ là phải đóng thùng áo phông bên trong chân váy nhé, điều này giúp trang phục trông gọn gàng, hút mắt hơn đấy.</p><h3>Áo phông + Quần jeans xắn gấu</h3><p>Áo phông trơn màu đơn giản, có thể mix với mọi trang phục khác nhau. Đơn giản nhất là mix áo phông trơn với quần jean xắn gấu, thêm một đôi giày sneaker là bạn đã có một phong cách hoàn hảo xuống phố. Đây là phong cách mix quần áo hot nhất hiện nay, được rất nhiều cô nàng hot girl yêu thích đấy.</p><p>&nbsp;</p><h3>Áo sơ mi lụa + Chân váy bút chì</h3><p>Nàng công sở yêu thích các phong phối đồ Ulzzang không thể bỏ qua kiểu áo sơ mi lụa này. Kiểu áo sơ mi thắt nơ ở cổ giúp chị em thoả sức sáng tạo, tự tin trong các kiểu thắt nơ của mình.</p><p><img src="https://andora.com.vn/wp-content/uploads/2022/04/ulzzang-la-gi-4.jpg" alt="Áo sơ mi lụa + Chân váy bút chì" srcset="https://andora.com.vn/wp-content/uploads/2022/04/ulzzang-la-gi-4.jpg 600w, https://andora.com.vn/wp-content/uploads/2022/04/ulzzang-la-gi-4-90x120.jpg 90w" sizes="100vw" width="600"></p><p><i>Áo sơ mi lụa + Chân váy bút chì</i></p><p>Khi diện cùng chân váy công sở dáng bút chì, nàng hoàn toàn có thể tôn lên nét đẹp quyến rũ bởi body của mình. Vừa che được khuyết điểm bởi kiểu dáng bồng bềnh vừa giúp nàng hack chiều cao đáng kể.</p><h3>Áo phông + Chân váy chữ A</h3><p>Chân váy chữ A vừa khỏe khoắn vừa năng động là kiểu váy được rất nhiều cô nàng ưa chuộng. Với kiểu chân váy này, các nàng Ulzzang có thể mix với áo phông tay dài hoặc tay ngắn đều được.</p><p>Để thêm nổi bật, các nàng có thể chọn thêm phụ kiện mũ lưỡi trai, giày sneaker khi phối đồ Ulzzang nữ tôn lên phong cách riêng của bản thân.</p><p><img src="https://andora.com.vn/wp-content/uploads/2022/04/ulzzang-la-gi-5.jpg" alt="Áo phông + Chân váy chữ A" srcset="https://andora.com.vn/wp-content/uploads/2022/04/ulzzang-la-gi-5.jpg 600w, https://andora.com.vn/wp-content/uploads/2022/04/ulzzang-la-gi-5-90x120.jpg 90w" sizes="100vw" width="600"></p><p><i>Áo phông + Chân váy chữ A</i></p><h3>Áo phông + Quần kaki</h3><p>Xu hướng diện quần kaki được nhiều tín đồ thời trang yêu thích thời gian gần đây. Bạn hãy thử mix kiểu quần kaki với áo phông cho bản thân nhé. Đây là cách mix đồ được rất nhiều Ulzzang Hàn ưa chuộng, vừa cá tính lại hợp thời trang.</p><p><img src="https://andora.com.vn/wp-content/uploads/2022/04/ulzzang-la-gi-6.jpg" alt="Áo phông + Quần kaki"></p><p><i>Áo phông + Quần kaki</i></p><h3>Váy yếm + Áo phông trắng</h3><p>Phong cách Ulzzang Style không thể thiếu váy yếm đúng không nào. Đa số các cô nàng Ulzzang Hàn đều sở hữu item này trong tủ đồ của mình. Bạn có thể mix váy yếm với nhiều trang phục khác nhau, nhưng đơn giản và thời trang nhất vẫn là mix cùng áo phông trắng.</p><p><img src="https://andora.com.vn/wp-content/uploads/2022/04/ulzzang-la-gi-7.jpg" alt="Váy yếm + Áo phông trắng"></p><p><i>Váy yếm + Áo phông trắng</i></p><h3>Quần culottes mix áo kẻ ngang</h3><p>Áo kẻ ngang là item không bao giờ lỗi mốt, có thể diện trong mọi hoàn cảnh như đi học, đi chơi, hẹn hò. Với phong cách Ulzzang, thì các nàng hãy mix áo kẻ ngang với quần Culottes. Với trang phục này, hãy chọn giày slip on và túi vải để tổng thể trông thật hài hòa nhé.</p><p><img src="https://andora.com.vn/wp-content/uploads/2022/04/ulzzang-la-gi-8.jpg" alt="Quần culottes mix áo kẻ ngang"></p><p><i>Quần culottes mix áo kẻ ngang</i></p><h3>Đầm đuôi cá, đầm hoa phong cách Ulzzang</h3><p>Đã là con gái thì không thể thiếu các loại đầm trong tủ đồ của mình. Các cô nàng Ulzzang thường diện đầm đuôi cá, đầm hoa nhỏ xinh. Hai kiểu đầm này phù hợp với nhiều vóc dáng, không kén da, tôn lên vẻ ngoài tươi trẻ dễ thương. Với các kiểu đầm này, các nàng nên chọn đi cùng giày bệt, giày búp bê, dép trệt nhé.</p><p><img src="https://andora.com.vn/wp-content/uploads/2022/04/ulzzang-la-gi-9.jpeg" alt="Đầm đuôi cá, đầm hoa phong cách Ulzzang"></p><p><i>Đầm đuôi cá, đầm hoa phong cách Ulzzang</i></p><h3>Áo gile mix với áo thun và chân váy</h3><p>Vào những ngày đông se lạnh, thì áo gile là sự lựa chọn hoàn hảo cho phong cách thời trang. Áo gile có nhiều kiểu dáng và chất liệu khác nhau cho bạn lựa chọn. Nếu chọn áo gile sẫm màu thì các bạn nên chọn áo thun và quần jeans sáng màu, như vậy tổng thể trang phục sẽ hài hòa hơn.</p><p><img src="https://andora.com.vn/wp-content/uploads/2022/04/ulzzang-la-gi-10.jpg" alt="Áo gile mix với áo thun và chân váy"></p><p><i>Áo gile mix với áo thun và chân váy</i></p><h3>Áo len mỏng mix với áo sơ mi và quần jeans</h3><p>Áo len mỏng là item quen thuộc trong những ngày đầu đông. Bạn có thể mix áo len mỏng với áo sơ mi và quần jeans.</p><p>Cách phối đồ Ulzzang này rất đơn giản, mang đến phong cách nữ tính, năng động trong ngày đông. Hãy nhớ để phần cổ áo sơ mi ra ngoài áo lên để trang phục trở nên hút mắt người nhìn hơn nhé.</p><p><img src="https://andora.com.vn/wp-content/uploads/2022/04/ulzzang-la-gi-11.jpg" alt="Áo len mỏng mix với áo sơ mi và quần jeans"></p><p><i>Áo len mỏng mix với áo sơ mi và quần jeans</i></p><h3>Áo len cổ cao mix áo khoác dạ dáng dài</h3><p>Đây là cách phối đồ Ulzzang nữ mà bạn có thể thấy trong mọi bộ phim hàn quốc mùa đông. Set đồ này không chỉ giúp giữ ấm hiệu quả mà còn tạo nên phong cách hoàn hảo cho bạn.</p><p>Chính vì vậy, vào những ngày rét đậm các bạn hãy thử cách mix áo len cổ cao và áo khoác dạ, quần jeans ngay nhé. Chú ý là chọn độ dài áo khoác dạ hợp lý, tránh trường hợp quá dài hoặc quá ngắn, gây ảnh hưởng đến vóc dáng cơ thể.</p><p><img src="https://andora.com.vn/wp-content/uploads/2022/04/ulzzang-la-gi-12-600x600.jpg" alt="Áo len cổ cao mix áo khoác dạ dáng dài" srcset="https://andora.com.vn/wp-content/uploads/2022/04/ulzzang-la-gi-12-600x600.jpg 600w, https://andora.com.vn/wp-content/uploads/2022/04/ulzzang-la-gi-12.jpg 640w" sizes="100vw" width="600"></p><p><i>Áo len cổ cao mix áo khoác dạ dáng dài</i></p><h3>Áo hoodie mix với áo thun rộng và quần jeans</h3><p>Áo hoodie phối cùng áo thun form rộng và quần jeans là cách mix đồ Ulzzang cực chất, được giới trẻ ưa chuộng ngày nay. Khi diện set đồ này, hãy chọn áo thun rộng để phần vạt áo dài qua áo hoodie, kiểu phối đồ Ulzzang tạo nên sự sành điệu.</p><p><img src="https://andora.com.vn/wp-content/uploads/2022/04/ulzzang-la-gi-13.jpg" alt="Áo hoodie mix với áo thun rộng và quần jeans"></p><p><i>Áo hoodie mix với áo thun rộng và quần jeans</i></p><p><i>Ulzzang style là gì? Mix đồ phong cách Ulzzang như thế nào?</i></p><p>Hy vọng thông qua những chia sẻ này, những người theo phong cách Ulzzang sẽ có thêm nhiều gợi ý về phối đồ hay cho bản thân. Chúc các bạn xinh đẹp nổi bật mỗi ngày và hãy thường xuyên truy cập Andora để cập nhật thêm nhiều thông tin thú vị nhé.</p>', '2022-09-08 23:03:57.067', 'Ulzzang là gì? Bật mí 12 phong cách Phối Đồ Ulzzang nữ đang HOT TREND', '4', 'https://res.cloudinary.com/anhcoming/image/upload/v1662649001/xzc7t8tkaxkpwg60sfod.jpg', true, 'Bạn là người yêu thích phong cách Hàn Quốc theo đuổi những kiểu phối đồ Ulzzang nữ đang HOT TREND, chắc hẳn bạn đã nghe qua nhưng chưa biết Ulzzang là gì đúng không nào? Ulzzang là từ để chỉ những cô nàng, chàng trai đẹp trong xã hội, nhận được sự quan tâm yêu mến của cộng đồng mạng.', NULL);



INSERT INTO public.body_height VALUES ('319ba30f-97c9-4e03-8c9c-db5dd15cf8c1', 'TYPE1', 155, 149);
INSERT INTO public.body_height VALUES ('423d9164-f46f-4614-923e-b98d77f6c752', 'TYPE2', 160, 156);
INSERT INTO public.body_height VALUES ('ea603e44-f611-45bb-b1a2-c07fd2ef7e45', 'TYPE3', 169, 161);
INSERT INTO public.body_height VALUES ('ea603e44-f611-45bb-b1a2-c07fd2ef7e45

efd11705-0aa3-430b-944b-efb7477e72c8', 'TYPE4', 178, 170);
INSERT INTO public.body_height VALUES ('4ff7c4bd-a325-4f90-8721-68bfe4b6a6ec', 'TYPE5', 188, 179);
INSERT INTO public.body_height VALUES ('b1dfdc29-6c66-4c7b-94bb-71458fc210b3', 'TYPE6', 195, 189);



INSERT INTO public.body_weight VALUES ('8fcea0c0-e4f3-4568-8c41-d815260c7d38', 'TYPE1', 49, 41);
INSERT INTO public.body_weight VALUES ('2e8c9ca5-46e6-4776-a4a1-f071a610caf3', 'TYPE3', 69, 60);
INSERT INTO public.body_weight VALUES ('94d2c208-92a2-42bc-982a-e28d1d79bb93', 'TYPE4', 79, 70);
INSERT INTO public.body_weight VALUES ('5413c08c-9bfd-4f4e-8a99-644795da4329', 'TYPE5', 90, 80);
INSERT INTO public.body_weight VALUES ('558cb46a-4e80-46e9-9f00-67b919700616', 'TYPE6', 105, 91);
INSERT INTO public.body_weight VALUES ('a14dbed0-3fb3-43e0-803c-bf7e74e8f04b', 'TYPE2', 59, 50);



INSERT INTO public.cart VALUES ('799f78d9-2ea9-4a42-a842-7c7cf76ef07d', '37d1f3a2-3d82-43fb-98e2-1b3a7c7401fc', 2, NULL, '6fac7f97-6c5c-44d5-9ed0-5bfc66326f2b');
INSERT INTO public.cart VALUES ('ede2fd11-0e6d-42b9-8624-4af8d3504295', '832b1824-0476-40c9-8d66-91e2741b65e8', 1, NULL, '6fac7f97-6c5c-44d5-9ed0-5bfc66326f2b');
INSERT INTO public.cart VALUES ('c0a469cb-d862-4dbf-9267-9d32925359f1', '31cb57ad-ca5a-4eba-ab95-1c4ce614db73', 1, NULL, '41560275-3409-4cde-9e5e-c4a0585ca3c6');
INSERT INTO public.cart VALUES ('2e2e20ce-a3fb-4299-9a12-e66393f0afe6', 'fc907208-6867-4e21-b362-4708af5093fc', 1, NULL, 'af90d6af-516c-4b89-92c2-9dae1e12dda4');
INSERT INTO public.cart VALUES ('926d8cb7-edb7-41ba-bc74-04cbcbeb34f0', 'f498a37c-b7ac-4c4f-912b-bab24fb1d0c1', 5, NULL, 'af90d6af-516c-4b89-92c2-9dae1e12dda4');
INSERT INTO public.cart VALUES ('7c2595bb-a2e1-48ae-b1f6-c798289308db', '639eb3ff-de4f-4313-9521-1afab9c5a647', 1, NULL, 'eb05aa1c-c054-43eb-96e1-72c808170b47');



INSERT INTO public.category VALUES ('02cfad29-a96d-452d-9f46-897059ba5ecb', true, 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-07-04 22:25:24', 'Áo sơ mi nữ được xem là trang phục đậm chất casual, chắc chắn sẽ rất phù hợp với các cô gái yêu thích sự đơn giản nhưng lịch sự.', 'Áo sơ mi nữ', 'caf86bc4-5d64-41d9-9644-cc4d5434bbe4', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-07-04 22:27:21', 'https://4men.com.vn/thumbs/2022/03/ao-so-mi-oxford-theu-logo-4m-asm085-mau-xanh-bien-20386-p.JPG', 'ao-so-mi-nu');
INSERT INTO public.category VALUES ('0d6fe842-46e7-4139-98d7-41557dedac72', true, 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-07-04 22:28:09', 'Áo khoác nữ là trang phục rất cần thiết trong những ngày Đông lạnh giá .Áo khoác nữ form rộng, áo khoác nữ hàn quốc là một trong những item không thể thiếu, là vật “bất ly thân” đối với bất kể chàng trai, cô gái nào', 'Áo khoác nữ', 'caf86bc4-5d64-41d9-9644-cc4d5434bbe4', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-07-04 22:29:27', 'https://vn-test-11.slatic.net/p/05c5235d15cada59312e65239ce99d6f.jpg', 'ao-khoac-nu');
INSERT INTO public.category VALUES ('e70f7999-f0c8-44a4-8cd3-3d98c95d032d', true, 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-07-10 00:25:45', 'Mẫu áo thun nam 2022, áo phông nam cổ tròn mang đến cho các quý ông cảm giác khỏe khoắn, trẻ trung năng động nhưng lại không kém phần lịch lãm.', 'Áo Thun Nam', '89435dcd-8589-4e02-94c2-1ff16e7f617b', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-07-10 00:25:15', 'https://bizweb.sapocdn.net/thumb/large/100/438/408/products/tsm5145-den-7-jpg.jpg?v=1647657027000', 'ao-thun-nam');
INSERT INTO public.category VALUES ('1d48c0ea-ad57-4c20-bd8d-e7a64725d860', true, 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-07-10 00:18:04', 'Áo ba lỗ nam form rộng là chiếc áo cotton được thiết kế đơn giản và dễ mặc. Xây dựng cho phái mạnh một phong cách khỏe khoắn và năng động.', 'Áo ba lỗ', '89435dcd-8589-4e02-94c2-1ff16e7f617b', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-07-10 00:17:56', 'https://bizweb.sapocdn.net/thumb/large/100/438/408/products/stm5071-xcv-9-c8b892a5-a473-4683-b726-c944637f0435.jpg?v=1646973608000', 'ao-ba-lo');
INSERT INTO public.category VALUES ('f2a3b624-0944-438f-ae8c-29d5373d0892', true, 'anhcoming', '2022-06-26 10:26:39.349', 'Form chuẩn Unisex Nam Nữ Couple đều mặc được.
Thích hợp sử dụng khi đi chơi, dã ngoại , dạo, phố……
Đường kim mũi chỉ kép cực tinh tế, đường may kỹ. 
Nón trùm qua đầu, dây kéo tốt', 'Áo khoác nam', '89435dcd-8589-4e02-94c2-1ff16e7f617b', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-08-27 23:06:19.577', 'https://vn-test-11.slatic.net/p/cc1e8c4334b815a0c0b8703231a94d0b.jpg', 'ao-khoac-nam');
INSERT INTO public.category VALUES ('89a5780d-7288-4342-9ab9-dbb3104da77b', true, 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-07-10 00:20:00', 'Bộ sưu thập vest nam, comple nam cập nhật xu hướng thời trang công sở trẻ trung, năng động để tôn lên vẻ nam tính, lịch lãm, đẳng cấp của các quý ông.', 'Áo Vest Nam', '89435dcd-8589-4e02-94c2-1ff16e7f617b', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-07-10 00:20:45', 'https://bizweb.sapocdn.net/thumb/large/100/438/408/products/vem4007-xdm-4.jpg?v=1641618490000', 'ao-vest-nam');
INSERT INTO public.category VALUES ('f27a78db-a593-42c4-8390-0f7ef01106a1', true, 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-07-10 00:23:43', 'Áo sơ mi nam chính là lựa chọn ưu tiên của phái mạnh. Chiếc áo sơ mi thể hiện được sự trẻ trung nhưng vẫn không làm mất đi sự lịch sự, trưởng thành.', 'Áo Sơ Mi Nam', '89435dcd-8589-4e02-94c2-1ff16e7f617b', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-07-10 00:23:52', 'https://bizweb.sapocdn.net/thumb/large/100/438/408/products/smm5027-ghi-13.jpg?v=1653546348000', 'ao-so-mi-nam');
INSERT INTO public.category VALUES ('0d00e1ab-6c6f-4e65-a0aa-b4284477417c', true, 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-07-10 00:22:29', 'Áo Polo giờ đây là 1 trong những must-have-item của các chàng trai. Để giữ được nét lịch lãm nhưng vẫn đảm bảo được tiêu chí thoải mái, thoáng mát, lịch lãm áo Polo nam là một trong những lựa chọn tuyệt vời.', 'Áo Polo nam', '89435dcd-8589-4e02-94c2-1ff16e7f617b', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-08-27 23:04:43.623', 'https://yody.vn/ao-polo-nam-jade-cool-sieu-mat-basic', 'ao-polo-nam');
INSERT INTO public.category VALUES ('7985a124-9967-471a-8f00-ae24ee4fb727', true, 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-07-10 00:14:17', 'Áo Polo nữ là một trong những loại áo có cổ được may bằng chất liệu vải thun co giãn, đem đến cảm giác dễ chịu khi sử dụng. Thiết kế lịch sự, sang trọng toát lên vẻ ngoài năng động, tự tin cho phái nữ trong mọi hoạt động.', 'Áo Polo nữ', 'caf86bc4-5d64-41d9-9644-cc4d5434bbe4', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-08-26 15:52:39.763', 'https://bizweb.sapocdn.net/thumb/large/100/438/408/products/apn4396-tra-1-jpeg.jpg?v=1652063010000', 'ao-polo-nu');
INSERT INTO public.category VALUES ('44dd44f8-e903-4c81-a5e1-d5916ca4ec75', true, 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-07-04 22:21:30', 'Áo thun nữ chính hãng thiết kế không những đa dạng về mẫu mã mà còn được đánh giá cao bởi chất liệu vải. Đến với chúng tôi bạn sẽ được tư vấn và phục vụ nhiệt tình về cách phối đồ hợp với thời trang và dáng người.', 'Áo thun nữ', 'caf86bc4-5d64-41d9-9644-cc4d5434bbe4', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-07-04 22:24:33', 'https://s3-ap-southeast-1.amazonaws.com/lo-image/product/cover/pc-ao-thun-nu-large-1605777871-2268.jpg', 'ao-thun-nu');
INSERT INTO public.category VALUES ('bcabbac6-1758-4be2-8c09-41a372f28224', false, 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-08-12 22:17:01.053', 'Áo thoáng mát. Phù hợp những ngày nóng bức 123123', 'Áo sơ mi Nam cổ cao', '89435dcd-8589-4e02-94c2-1ff16e7f617b', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-09-13 22:53:41.435', NULL, 'ao-so-mi-nam-co-cao');



INSERT INTO public.color VALUES ('4dc1f92b-b287-46f8-a1a3-031add08008b', true, '2022-06-26 10:26:31.473', '#008000', 'Lục', NULL, NULL, NULL);
INSERT INTO public.color VALUES ('cf6f96df-2b99-4862-9b75-ccff88e93b29', true, '2022-06-26 10:26:31.911', '#000000', 'Đen', NULL, NULL, NULL);
INSERT INTO public.color VALUES ('5d1f416b-0d03-48fd-a645-712891128038', true, '2022-06-26 10:26:32.13', '#0000FF', 'Xanh', NULL, NULL, NULL);
INSERT INTO public.color VALUES ('ae965d3b-b6b2-4e46-bb16-ddd798b6bbe4', true, '2022-06-26 10:26:32.567', '#800080', 'Tím', NULL, NULL, NULL);
INSERT INTO public.color VALUES ('343ac3c3-b114-4c31-9a64-479d51437881', true, '2022-06-26 10:26:32.786', '#FF0000', 'Đỏ', NULL, NULL, NULL);
INSERT INTO public.color VALUES ('9d3254e6-5f41-41bf-b49b-7aff8f8b9428', true, '2022-07-10 01:37:46', '#FFFF00', 'Vàng', NULL, NULL, NULL);
INSERT INTO public.color VALUES ('328d41d0-bdb8-4a6d-affd-65b255d666fa', false, '2022-09-10 06:48:45.322', '#d000ff', 'Tím đậm', NULL, NULL, NULL);
INSERT INTO public.color VALUES ('1516a196-7bc6-4834-9668-bca739ee282d', true, '2022-09-10 06:49:01.809', '#00ff2d', 'Xanh lá cây', NULL, NULL, NULL);
INSERT INTO public.color VALUES ('09b8c2c2-b277-4bac-a1d7-8b910d263cc1', true, '2022-09-10 06:49:42.535', '#a6a6a6', 'Xám', NULL, NULL, NULL);
INSERT INTO public.color VALUES ('9b97d6b6-cba3-43a6-8949-36a138a04e33', false, '2022-06-26 10:26:31.692', '#FFFFFF', 'Trắng', NULL, NULL, NULL);









INSERT INTO public.discount VALUES ('cb0ce921-8570-4883-a4cb-64073827236d', 'ALL_PRODUCT', 'CHAOHE2022', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-08-10 22:32:56.954', 'ALL', false, NULL, false, 'NONE', NULL, '2022-08-10 22:32:00', 'ACTIVE', 'PERCENT', '{"percentageValue":"20","valueLimitAmount":"20000"}', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-08-10 22:32:56.954', NULL, NULL, NULL);
INSERT INTO public.discount VALUES ('82d008e2-e150-4e71-adc3-eb9b7155ed73', 'ALL_PRODUCT', '3Q0A6NVA', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-08-28 00:59:52.439', 'ALL', false, NULL, false, 'NONE', NULL, '2022-08-28 00:31:00', 'ACTIVE', 'PERCENT', '{"percentageValue":"10","valueLimitAmount":""}', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-08-28 00:59:52.439', NULL, NULL, NULL);
INSERT INTO public.discount VALUES ('26ef8cb8-f8b1-4abc-a4dc-826247844000', 'ALL_PRODUCT', 'JHRBLVFE', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-08-28 09:05:32.614', 'ALL', false, NULL, false, 'NONE', NULL, '2022-08-28 09:01:00', 'ACTIVE', 'PERCENT', '{"percentageValue":"10","valueLimitAmount":"10000"}', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-08-28 09:05:32.614', NULL, NULL, NULL);
INSERT INTO public.discount VALUES ('76ae9418-4153-4994-ae24-ae378a0556df', 'ALL_PRODUCT', '79MLER1B', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-08-31 13:41:07.877', 'ALL', false, NULL, false, 'NONE', NULL, '2022-08-30 13:40:00', 'ACTIVE', 'PERCENT', '{"percentageValue":"10","valueLimitAmount":""}', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-08-31 13:41:07.877', NULL, NULL, NULL);
INSERT INTO public.discount VALUES ('617fc42d-8e9d-4305-8763-5cefd374e162', 'ALL_PRODUCT', 'SDFGHJK', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-09-06 01:04:15.579', 'ALL', false, '2022-09-21 02:00:00', false, 'NONE', NULL, '2022-09-08 01:00:00', 'PENDING', 'PERCENT', '{"percentageValue":"10","valueLimitAmount":"1000"}', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-09-06 01:04:15.579', NULL, NULL, NULL);
INSERT INTO public.discount VALUES ('82e35d19-8a48-4276-8c1a-69926dcd92f4', 'ALL_PRODUCT', '5BERKT44', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-09-06 01:07:06.74', 'ALL', false, '2022-09-06 01:06:00', false, 'NONE', NULL, '2022-09-06 01:06:00', 'DE_ACTIVE', 'PERCENT', '{"percentageValue":"10","valueLimitAmount":"1000"}', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-09-06 01:07:06.74', NULL, NULL, NULL);



INSERT INTO public.discount_category VALUES ('5151669c-3771-4a6e-a472-d8e478c346d5', '0d00e1ab-6c6f-4e65-a0aa-b4284477417c', '8cef09c4-e91d-4d53-9e39-55ec37b1f52c');
INSERT INTO public.discount_category VALUES ('9219cacf-c154-4349-8c89-2a40503ac2e5', 'f27a78db-a593-42c4-8390-0f7ef01106a1', '8cef09c4-e91d-4d53-9e39-55ec37b1f52c');
INSERT INTO public.discount_category VALUES ('dffdf439-b047-4592-8154-428b4f3b2344', 'f2a3b624-0944-438f-ae8c-29d5373d0892', '8cef09c4-e91d-4d53-9e39-55ec37b1f52c');
INSERT INTO public.discount_category VALUES ('b9bfdc64-7250-4660-8aad-8ffd6276369b', 'e70f7999-f0c8-44a4-8cd3-3d98c95d032d', 'ba6a4518-6a02-4fc6-9646-454ce68ac344');
INSERT INTO public.discount_category VALUES ('bd136899-1b5f-4c9e-9441-3a71825f7f9b', 'e70f7999-f0c8-44a4-8cd3-3d98c95d032d', '2aa628ed-6e0b-4d56-a4b1-721e12368b75');
INSERT INTO public.discount_category VALUES ('7709fe8b-f310-4833-b1d6-556cb6d9d066', 'f2a3b624-0944-438f-ae8c-29d5373d0892', '0f50ab52-7acb-4d32-9148-b44e11c4e4ef');



INSERT INTO public.discount_customer VALUES ('47e40667-e9d6-4d9a-a4ca-bd0786425fc6', '0f50ab52-7acb-4d32-9148-b44e11c4e4ef', '41560275-3409-4cde-9e5e-c4a0585ca3c6');
INSERT INTO public.discount_customer VALUES ('25bb25bb-c002-4b59-878f-dfba79b076f7', '0f50ab52-7acb-4d32-9148-b44e11c4e4ef', 'fcf1566a-b1db-4fdf-aaf0-40f06dceaf6c');
INSERT INTO public.discount_customer VALUES ('9395da18-362b-411d-990c-b0799035ddc1', '0f50ab52-7acb-4d32-9148-b44e11c4e4ef', '3cae485f-6b50-4baf-afd2-e17efc3c18bf');
INSERT INTO public.discount_customer VALUES ('7c011c4d-4c04-4673-bb4b-1af733993b77', '0f50ab52-7acb-4d32-9148-b44e11c4e4ef', '143ef20e-085a-428a-8835-11463d7511dd');






INSERT INTO public.discount_product VALUES ('4c580f20-3198-4767-b12d-4099f79a2e03', '8cef09c4-e91d-4d53-9e39-55ec37b1f52c', '58c6c7cf-d338-42c4-9d18-53c7f16b941b');
INSERT INTO public.discount_product VALUES ('b08d5b14-648b-4b82-9fb0-8e1d04dbba50', '8cef09c4-e91d-4d53-9e39-55ec37b1f52c', 'dd6ae7be-ad59-448d-ad5a-9df73270be13');
INSERT INTO public.discount_product VALUES ('b79322f9-b8ba-4b4a-8ffc-8c4ede184e67', '8cef09c4-e91d-4d53-9e39-55ec37b1f52c', 'b860144a-6a9b-4866-a56a-bdec57ef1786');
INSERT INTO public.discount_product VALUES ('524c8961-95de-4f12-bc39-305bf14a3af1', '8cef09c4-e91d-4d53-9e39-55ec37b1f52c', '70280218-0a6c-47f3-90b1-49a9d8e4221c');
INSERT INTO public.discount_product VALUES ('459f195f-fce7-4ae5-991f-8c3cd9e5c91b', '8cef09c4-e91d-4d53-9e39-55ec37b1f52c', '1d107139-a667-460f-bda6-1d2d928fc06e');
INSERT INTO public.discount_product VALUES ('87fd0e7f-f4d7-49fa-8c32-3504acb7cd56', '8cef09c4-e91d-4d53-9e39-55ec37b1f52c', '22a15198-4e02-4535-80e3-3aa783514593');
INSERT INTO public.discount_product VALUES ('acf56577-a3dd-48f9-9e2d-3153af2cdfb1', '8cef09c4-e91d-4d53-9e39-55ec37b1f52c', '20a81c4c-de1d-4a9c-aa2e-b955063e62e6');
INSERT INTO public.discount_product VALUES ('8c5ff56e-5255-43f8-bc38-24c48e10f80a', '8cef09c4-e91d-4d53-9e39-55ec37b1f52c', '513f96f4-e4e4-4449-b146-4e039388f72e');
INSERT INTO public.discount_product VALUES ('6ee345b3-c8ff-410a-a892-be22e2afb36c', '8cef09c4-e91d-4d53-9e39-55ec37b1f52c', 'de270524-6fb5-44e0-8a0c-3b847610440d');
INSERT INTO public.discount_product VALUES ('c1f3cb56-2eef-40bf-9044-2b9b1bde4a01', '8cef09c4-e91d-4d53-9e39-55ec37b1f52c', '47af61e5-039e-4646-958b-a8d0a0763265');
INSERT INTO public.discount_product VALUES ('e978984a-d0dd-4225-b92b-edaf0d624a17', '8cef09c4-e91d-4d53-9e39-55ec37b1f52c', 'fa8c1c00-195b-49fc-a2a8-4d1dd88f8e16');
INSERT INTO public.discount_product VALUES ('40f9afd2-7d45-4749-a465-6e82fc744acb', '8cef09c4-e91d-4d53-9e39-55ec37b1f52c', '8ffc7829-5e9e-41e4-9572-f799084730c5');
INSERT INTO public.discount_product VALUES ('be97b713-441e-4b9e-8334-d4dc00e4d01f', '8cef09c4-e91d-4d53-9e39-55ec37b1f52c', '16204f7e-e987-4da5-9a1b-bf240690546c');
INSERT INTO public.discount_product VALUES ('f53365c3-45c5-4d75-aa0c-b0e0d461d834', '8cef09c4-e91d-4d53-9e39-55ec37b1f52c', 'ee0bc7ec-666b-49a3-9215-a3d02a3224fa');
INSERT INTO public.discount_product VALUES ('37800129-6098-4710-8750-514a4dd2e1fa', '8cef09c4-e91d-4d53-9e39-55ec37b1f52c', 'f9be3945-dade-4178-99ef-8439e83a8d03');
INSERT INTO public.discount_product VALUES ('bf0af5d1-6e98-4778-8756-c0b5742008f8', '8cef09c4-e91d-4d53-9e39-55ec37b1f52c', '0fe76906-5aff-465d-ae58-2d86a6101919');
INSERT INTO public.discount_product VALUES ('95b7600c-d621-4de7-8437-c8d60767a785', '8cef09c4-e91d-4d53-9e39-55ec37b1f52c', '98d90096-1d73-4c61-bf22-b6b6bb1ec6c6');
INSERT INTO public.discount_product VALUES ('f3e34aaf-e3c8-4b76-a2dd-af4fd4750760', '8cef09c4-e91d-4d53-9e39-55ec37b1f52c', 'cb8820fc-1847-47b8-86f4-9680358433c8');
INSERT INTO public.discount_product VALUES ('4c19b0a9-b34b-45d9-b12d-6943144d2ea7', '8cef09c4-e91d-4d53-9e39-55ec37b1f52c', '54dfdeca-eac0-46ce-ad03-7376836dec68');
INSERT INTO public.discount_product VALUES ('bd5ca3b2-8747-47e5-b7d3-a27feb7729d7', '8cef09c4-e91d-4d53-9e39-55ec37b1f52c', '20dfca66-1842-44a6-b543-95a87e26b04b');
INSERT INTO public.discount_product VALUES ('c7c8207c-b3c2-4506-b865-68f8b1d4d9a7', '8cef09c4-e91d-4d53-9e39-55ec37b1f52c', '711ce3ec-e651-46c5-8d80-2983cd5354cc');
INSERT INTO public.discount_product VALUES ('aac249c9-4bd3-4e58-8804-6282d38a7337', '8cef09c4-e91d-4d53-9e39-55ec37b1f52c', 'cf408a06-85bb-46a0-b0e5-0d30825a7f3c');
INSERT INTO public.discount_product VALUES ('745768d5-664a-45b5-b997-10ddc3c09b87', '8cef09c4-e91d-4d53-9e39-55ec37b1f52c', '15cee405-252c-4c87-9691-7b950ea4f3e2');
INSERT INTO public.discount_product VALUES ('ae268c8d-a397-494c-9945-480944837d3e', '8cef09c4-e91d-4d53-9e39-55ec37b1f52c', '3166675c-8def-42a0-b9f6-975a25738ce8');
INSERT INTO public.discount_product VALUES ('f604e92a-136e-4dd6-94df-c127da26ffeb', 'ba6a4518-6a02-4fc6-9646-454ce68ac344', '255e2f0e-6586-45c1-9b44-99e50d3cd33d');
INSERT INTO public.discount_product VALUES ('c4ebd08e-9586-43b6-bd06-3542943a533e', 'ba6a4518-6a02-4fc6-9646-454ce68ac344', '0ff0d2d5-b02f-4237-a4eb-2fe1387d7b1b');
INSERT INTO public.discount_product VALUES ('1dd7f5ea-2b79-40a3-bb7a-18377a61f447', '2aa628ed-6e0b-4d56-a4b1-721e12368b75', '255e2f0e-6586-45c1-9b44-99e50d3cd33d');
INSERT INTO public.discount_product VALUES ('0c53edfa-bd3d-41f2-a065-541a9fa722cf', '2aa628ed-6e0b-4d56-a4b1-721e12368b75', '0ff0d2d5-b02f-4237-a4eb-2fe1387d7b1b');
INSERT INTO public.discount_product VALUES ('960e6290-967e-475e-9142-02c711325310', '0f50ab52-7acb-4d32-9148-b44e11c4e4ef', '3166675c-8def-42a0-b9f6-975a25738ce8');



INSERT INTO public.email_log VALUES ('ec77c187-0eeb-449c-8ff5-7392793eee0b', NULL, '2022-08-03 23:19:01.135', 'hungnnit98@gmail.com', NULL);
INSERT INTO public.email_log VALUES ('82aee139-3354-4876-8a26-84baebdc658c', NULL, '2022-08-03 23:21:14.967', 'hungnnit98@gmail.com', NULL);
INSERT INTO public.email_log VALUES ('9a614a99-c9f9-45d1-8832-42a14d2d1923', NULL, '2022-08-03 23:27:21.308', 'hungnnph09719@fpt.edu.vn', NULL);
INSERT INTO public.email_log VALUES ('43684b68-0081-4186-bbde-080a49615b94', NULL, '2022-08-03 23:37:33.325', 'hungnnph09719@fpt.edu.vn', NULL);
INSERT INTO public.email_log VALUES ('25120758-a4a2-477c-ba8e-7fdbf0222905', NULL, '2022-08-03 23:39:00.246', 'hungnnph09719@fpt.edu.vn', NULL);
INSERT INTO public.email_log VALUES ('c1b781b8-060a-415f-8ba7-097f79b4e5bb', NULL, '2022-08-03 23:48:43.533', 'hungnnph09719@fpt.edu.vn', NULL);












INSERT INTO public.favourite VALUES ('4cdd7017-1912-46e7-851e-d9511743879f', '0fe76906-5aff-465d-ae58-2d86a6101919', '280a1c4f-40cb-4b19-bde6-322149adc532');
INSERT INTO public.favourite VALUES ('c6fea43b-5d7b-4656-9f99-e791a6bbfa2a', '20dfca66-1842-44a6-b543-95a87e26b04b', '20ef979e-340d-4329-a9c5-387ae4f0d3c4');
INSERT INTO public.favourite VALUES ('a81104ff-5e5d-456c-807e-7c3f1314fc47', '70280218-0a6c-47f3-90b1-49a9d8e4221c', '5e2ec171-97be-4599-922c-0b48fd028589');
INSERT INTO public.favourite VALUES ('411ec9de-f8d7-43c6-9038-43a1984646c2', '8ffc7829-5e9e-41e4-9572-f799084730c5', '5e2ec171-97be-4599-922c-0b48fd028589');



INSERT INTO public.material VALUES ('39cea751-a426-422b-a0ac-569d711ddd38', true, 'Vải', NULL);
INSERT INTO public.material VALUES ('8c20116a-ab17-45a9-ae03-35c62cdb029f', true, 'Cotton', NULL);
INSERT INTO public.material VALUES ('61018676-af7e-419d-9b91-f246315fbc8c', true, 'Ka-ki', NULL);
INSERT INTO public.material VALUES ('87293753-e39e-473b-91e4-eae4fed864b5', true, 'Polyester', NULL);
INSERT INTO public.material VALUES ('386004f5-f0d7-4626-a75f-2d3c3158c504', true, 'Lụa', NULL);



INSERT INTO public.notification VALUES ('bec3d357-c9d8-4d8d-89c1-8df6a04a58b0', 'Khách hàng Nguyễn Nhật Hùng vừa đặt hàng thành công. Vui lòng xử lý đơn hàng', '2022-06-30 10:45:09.504', NULL, NULL, 'NORMAL', 'ORDER', '9763c16d-3174-4415-836e-037ba4c32038', NULL, NULL);
INSERT INTO public.notification VALUES ('61018676-af7e-419d-9b91-f246315fbc8c', 'Khách hàng Nguyễn Nhật Hùng đã hủy đơn hàng', '2022-07-04 10:45:09.504', NULL, NULL, 'WARNING', 'ORDER', '9763c16d-3174-4415-836e-037ba4c32038', NULL, NULL);
INSERT INTO public.notification VALUES ('f4369c48-d8b6-40e7-8b08-30924c2a4cb5', 'Gi day', '2022-07-07 22:15:51.815', NULL, NULL, 'NORMAL', 'ORDER', '3383bbed-643c-4563-a628-5478bb3a1196', NULL, NULL);
INSERT INTO public.notification VALUES ('73177be2-6c1b-4661-ae19-c1c3785947e3', 'Gi day', '2022-07-07 22:16:16.822', NULL, NULL, 'NORMAL', 'ORDER', '3383bbed-643c-4563-a628-5478bb3a1196', NULL, NULL);
INSERT INTO public.notification VALUES ('09dece81-44d3-4319-b117-8da5d4bc9116', 'Gi day', '2022-07-11 20:57:50.223', NULL, NULL, 'NORMAL', 'ORDER', '3383bbed-643c-4563-a628-5478bb3a1196', NULL, NULL);
INSERT INTO public.notification VALUES ('66e74799-3803-4ddc-aeaa-d9a5f3017de9', 'Tét', '2022-07-11 20:58:24.519', NULL, NULL, 'NORMAL', 'ORDER', '3383bbed-643c-4563-a628-5478bb3a1196', NULL, NULL);
INSERT INTO public.notification VALUES ('fb9df931-0e03-4380-9e56-d0a2e12dbc54', 'Tét123', '2022-07-11 20:58:43.431', NULL, NULL, 'NORMAL', 'ORDER', '3383bbed-643c-4563-a628-5478bb3a1196', NULL, NULL);
INSERT INTO public.notification VALUES ('3bb5f06c-5529-4e41-a97d-06ee30fef2b3', 'Hekkiasda', '2022-07-11 21:02:00.727', NULL, NULL, 'NORMAL', 'ORDER', '3383bbed-643c-4563-a628-5478bb3a1196', NULL, NULL);
INSERT INTO public.notification VALUES ('e316e621-ee0a-406e-aeb1-11e817a219b1', 'Hekkiasda 123 12 3', '2022-07-11 21:02:22.818', NULL, NULL, 'NORMAL', 'ORDER', '3383bbed-643c-4563-a628-5478bb3a1196', NULL, NULL);
INSERT INTO public.notification VALUES ('040af831-0774-47e1-86bb-48ae8db7c235', 'qweasd', '2022-07-11 21:02:55.13', NULL, NULL, 'NORMAL', 'ORDER', '3383bbed-643c-4563-a628-5478bb3a1196', NULL, NULL);
INSERT INTO public.notification VALUES ('5a76f248-e918-4207-aaeb-45e052a2173f', 'Khách hàng Tuan Nguyen vừa đặt hàng thành công. Vui lòng xử lý đơn hàng', '2022-08-28 13:35:53.106', NULL, false, 'NORMAL', 'ORDER', 'b223f941-cfca-4555-b03d-7969da656d4f', NULL, NULL);
INSERT INTO public.notification VALUES ('adc2b5f7-cdba-471c-9ae4-a1668e624964', 'Khách hàng ho ten vừa đặt hàng thành công. Vui lòng xử lý đơn hàng', '2022-08-28 22:47:39.291', NULL, false, 'NORMAL', 'ORDER', 'bd35dbf7-7163-4784-9f2f-31b7ded8739c', NULL, NULL);
INSERT INTO public.notification VALUES ('35f7c9bc-bd07-47c6-b1f6-131c7134b08c', 'Khách hàng Tu Nguyen vừa đặt hàng thành công. Vui lòng xử lý đơn hàng', '2022-08-28 22:47:39.351', NULL, false, 'NORMAL', 'ORDER', 'd1bdd57b-1784-441a-bebf-e9b22ef08d72', NULL, NULL);
INSERT INTO public.notification VALUES ('013d2ed4-2993-4a1b-ad25-caf2af5c76db', 'Khách hàng ho ten vừa đặt hàng thành công. Vui lòng xử lý đơn hàng', '2022-08-28 22:52:45.675', NULL, false, 'NORMAL', 'ORDER', '83d84818-4973-450b-ad24-401532966c74', NULL, NULL);
INSERT INTO public.notification VALUES ('539b895b-8e3c-4f87-81b3-d2a3eb607abb', 'Khách hàng Tu Nguyen vừa đặt hàng thành công. Vui lòng xử lý đơn hàng', '2022-08-28 22:52:47.577', NULL, false, 'NORMAL', 'ORDER', 'eab08614-0bb3-48ec-92cd-775038629e70', NULL, NULL);
INSERT INTO public.notification VALUES ('7029eccb-9904-4c40-8a1c-b40fa400104c', 'Khách hàng ho ten vừa đặt hàng thành công. Vui lòng xử lý đơn hàng', '2022-09-06 23:10:18.61', NULL, false, 'NORMAL', 'ORDER', '00452930-8228-4849-aa56-7f07f1daa03a', NULL, NULL);
INSERT INTO public.notification VALUES ('1bb44513-b8a3-4afe-8ed5-39079a696b70', 'Khách hàng ho ten vừa đặt hàng thành công. Vui lòng xử lý đơn hàng', '2022-09-13 21:20:42.245', NULL, false, 'NORMAL', 'ORDER', '4e0d71a4-6446-42e0-92e9-88ef74c1b52b', NULL, NULL);
INSERT INTO public.notification VALUES ('ed03b080-d289-4701-ac7b-29d0f5173725', 'Khách hàng test tyewbfhe vừa đặt hàng thành công. Vui lòng xử lý đơn hàng', '2022-09-14 16:14:41.921', NULL, false, 'NORMAL', 'ORDER', 'a67be0d8-6fe5-4924-9284-b3d61bbeee40', NULL, NULL);
INSERT INTO public.notification VALUES ('18276904-73a9-4671-98ba-c2108a3803c5', 'Khách hàng test tyewbfhe vừa đặt hàng thành công. Vui lòng xử lý đơn hàng', '2022-09-14 16:48:09.696', NULL, false, 'NORMAL', 'ORDER', 'd1585653-c998-480c-9d45-824d2b603adb', NULL, NULL);
INSERT INTO public.notification VALUES ('801b2334-c51e-4abc-87dc-35c214781031', 'Khách hàng Tuan Nguyen vừa đặt hàng thành công. Vui lòng xử lý đơn hàng', '2022-09-14 21:13:40.875', NULL, false, 'NORMAL', 'ORDER', '6a3adfac-2cbb-4922-a7e4-1db3393cc9bd', NULL, NULL);



INSERT INTO public.order_detail VALUES ('9ecb65fb-abd6-419a-af3a-0b839eb529e4', 'ffceb60b-b0b5-4d54-b30e-f0d811828e09', 80000, 'dfe47b9e-c391-4d8c-9865-39860e8b2b0f', 2, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.order_detail VALUES ('5858add0-7f7b-48bb-a6d5-508045ed7b52', '9763c16d-3174-4415-836e-037ba4c32038', 100000, '406e93e1-6f45-4042-8255-bce62d4585cb', 2, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.order_detail VALUES ('20b8baf6-7bd8-4b10-8d0f-dc51a0cadd75', 'ffceb60b-b0b5-4d54-b30e-f0d811828e09', 100000, '49b8ed77-7760-490b-9fe7-37a941910bec', 2, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.order_detail VALUES ('c42dc014-4b43-4e92-a5de-2382564938d9', '9763c16d-3174-4415-836e-037ba4c32038', 80000, '06878557-6d83-4b3d-ae32-53484ea3da52', 2, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.order_detail VALUES ('bc41b79f-832f-4fbb-9825-4e416a876694', 'b223f941-cfca-4555-b03d-7969da656d4f', 217000, 'fc907208-6867-4e21-b362-4708af5093fc', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.order_detail VALUES ('7d0cc220-9461-47bb-8594-827ae740d7ba', 'b223f941-cfca-4555-b03d-7969da656d4f', 217000, '8cb575fc-c52b-4e43-ae7a-5bf8f8047896', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.order_detail VALUES ('c00e357a-9e53-4b38-84a1-d4e3cde1b398', 'bd35dbf7-7163-4784-9f2f-31b7ded8739c', 299000, '50ace73d-e502-4173-ae23-b598eda3392d', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.order_detail VALUES ('8527b705-b0f4-419c-9e59-2b7f5e86a067', 'd1bdd57b-1784-441a-bebf-e9b22ef08d72', 217000, 'fc907208-6867-4e21-b362-4708af5093fc', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.order_detail VALUES ('cbad9dd9-af77-4cfa-9eb2-793e3584932d', 'd1bdd57b-1784-441a-bebf-e9b22ef08d72', 299000, '50ace73d-e502-4173-ae23-b598eda3392d', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.order_detail VALUES ('a41e162f-98c7-4e8e-ab1a-4cf4df49b2a4', '83d84818-4973-450b-ad24-401532966c74', 299000, '50ace73d-e502-4173-ae23-b598eda3392d', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.order_detail VALUES ('64cb3dc7-373e-4780-a37b-588703a9ad47', 'eab08614-0bb3-48ec-92cd-775038629e70', 299000, '50ace73d-e502-4173-ae23-b598eda3392d', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.order_detail VALUES ('883aab40-4765-4ac7-aae8-381ea2299b61', '00452930-8228-4849-aa56-7f07f1daa03a', 299000, '50ace73d-e502-4173-ae23-b598eda3392d', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.order_detail VALUES ('cbe57c93-6cf3-4f00-a81a-a3299738ff3b', '4e0d71a4-6446-42e0-92e9-88ef74c1b52b', 269000, '79587205-ba4d-406f-be9a-d805134b53e9', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.order_detail VALUES ('74a264c6-a4df-4b30-9a61-b5544cb6d01a', 'a67be0d8-6fe5-4924-9284-b3d61bbeee40', 184000, '639eb3ff-de4f-4313-9521-1afab9c5a647', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.order_detail VALUES ('a1116d0b-015e-4ef3-9c2b-711fa966103b', 'd1585653-c998-480c-9d45-824d2b603adb', 184000, '639eb3ff-de4f-4313-9521-1afab9c5a647', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.order_detail VALUES ('eb8fd5ba-541b-4dfc-a41e-e0b04f1c3cad', '6a3adfac-2cbb-4922-a7e4-1db3393cc9bd', 184000, '04cdc49c-96d8-4cce-b500-5198483d2321', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.order_detail VALUES ('fc4631e5-de98-4c03-95bb-e17160153176', '6a3adfac-2cbb-4922-a7e4-1db3393cc9bd', 184000, '35d64b0d-be40-4dd9-aeb8-ec17dd09ca0e', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.order_detail VALUES ('3b89d96b-c82d-4303-91c7-16e2a424643b', '6a3adfac-2cbb-4922-a7e4-1db3393cc9bd', 184000, '35172424-2934-4194-8a99-da6c8105420f', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.order_detail VALUES ('ce9b0405-2ff5-42f8-b235-f3c93948ab6c', '6a3adfac-2cbb-4922-a7e4-1db3393cc9bd', 269000, '79587205-ba4d-406f-be9a-d805134b53e9', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.order_detail VALUES ('e7e5e2fe-dc2f-4e2d-8659-a4da62d1d674', '6a3adfac-2cbb-4922-a7e4-1db3393cc9bd', 269000, 'ad8dbf03-40b2-462e-876d-75d9811b62b1', 1, NULL, NULL, NULL, NULL, NULL);



INSERT INTO public.order_status VALUES ('17a19556-1d87-4cca-81e4-346feae6faf5', '6fac7f97-6c5c-44d5-9ed0-5bfc66326f2b', '2022-06-24 10:26:49.225', NULL, 'ffceb60b-b0b5-4d54-b30e-f0d811828e09', 'PENDING');
INSERT INTO public.order_status VALUES ('ef743470-da95-422c-bcbc-5097bd73c55b', 'c37f89a1-932c-4350-baed-7eb0d1fb30be', '2022-06-23 09:16:19.225', NULL, '9763c16d-3174-4415-836e-037ba4c32038', 'PENDING');
INSERT INTO public.order_status VALUES ('0aaacbd0-c621-41e6-b490-2ee72741c76f', '5e2ec171-97be-4599-922c-0b48fd028589', '2022-08-28 13:35:50.482', NULL, 'b223f941-cfca-4555-b03d-7969da656d4f', 'PENDING');
INSERT INTO public.order_status VALUES ('38f20d0e-a3b9-436b-89d0-b9292e1d8711', '20ef979e-340d-4329-a9c5-387ae4f0d3c4', '2022-08-28 22:47:38.737', NULL, 'bd35dbf7-7163-4784-9f2f-31b7ded8739c', 'PENDING');
INSERT INTO public.order_status VALUES ('e5e0bd73-cb00-48fa-9a1e-9a154d59b28b', '280a1c4f-40cb-4b19-bde6-322149adc532', '2022-08-28 22:47:36.566', NULL, 'd1bdd57b-1784-441a-bebf-e9b22ef08d72', 'PENDING');
INSERT INTO public.order_status VALUES ('25c087dd-3163-45bf-a37b-fad78449d9ae', '280a1c4f-40cb-4b19-bde6-322149adc532', '2022-08-28 22:52:45.373', NULL, 'eab08614-0bb3-48ec-92cd-775038629e70', 'PENDING');
INSERT INTO public.order_status VALUES ('214d92d2-7e92-4a44-a05b-6ae024aaa762', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-06-29 18:15:27.42', '', 'ffceb60b-b0b5-4d54-b30e-f0d811828e09', 'REJECTED');
INSERT INTO public.order_status VALUES ('165bf7fa-5883-4632-8617-07107fe695fd', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-08-28 22:59:11.788', '', 'eab08614-0bb3-48ec-92cd-775038629e70', 'ACCEPTED');
INSERT INTO public.order_status VALUES ('ad098fff-6ad7-4648-a018-2159ade4c174', '280a1c4f-40cb-4b19-bde6-322149adc532', '2022-08-28 23:02:14.4', 'Muốn thay đổi địa chỉ giao hàng', 'eab08614-0bb3-48ec-92cd-775038629e70', 'CANCELED');
INSERT INTO public.order_status VALUES ('8e1b8c88-fc91-4bba-a341-2792137a29b5', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-08-28 23:02:20.206', '', 'eab08614-0bb3-48ec-92cd-775038629e70', 'ACCEPTED');
INSERT INTO public.order_status VALUES ('0274d179-d65b-4502-852a-31293a4d0714', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-08-30 15:34:20.206', '', 'b223f941-cfca-4555-b03d-7969da656d4f', 'ACCEPTED');
INSERT INTO public.order_status VALUES ('a642926b-d8aa-4026-9001-6ff8727e741a', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-08-30 15:37:21.442', 'THanh toan ROI', 'b223f941-cfca-4555-b03d-7969da656d4f', 'RECEIVED');
INSERT INTO public.order_status VALUES ('08f170e8-96ff-4838-8c96-573426ad990e', '20ef979e-340d-4329-a9c5-387ae4f0d3c4', '2022-09-06 23:10:16.874', NULL, '00452930-8228-4849-aa56-7f07f1daa03a', 'PENDING');
INSERT INTO public.order_status VALUES ('f9c22d5d-5f13-4030-b315-f443fd4bf842', '20ef979e-340d-4329-a9c5-387ae4f0d3c4', '2022-09-13 21:20:41.675', NULL, '4e0d71a4-6446-42e0-92e9-88ef74c1b52b', 'PENDING');
INSERT INTO public.order_status VALUES ('46f61abb-6d2e-43ed-bc98-31d95b304855', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-09-13 21:22:07.187', '', '4e0d71a4-6446-42e0-92e9-88ef74c1b52b', 'ACCEPTED');
INSERT INTO public.order_status VALUES ('763cba9e-7cb5-42f7-a831-d11b5f566d47', '20ef979e-340d-4329-a9c5-387ae4f0d3c4', '2022-09-13 21:25:13.802', 'Muốn thay đổi địa chỉ giao hàng', '83d84818-4973-450b-ad24-401532966c74', 'CANCELED');
INSERT INTO public.order_status VALUES ('d3dcb345-a83e-4452-befc-96b5d4315304', 'eb05aa1c-c054-43eb-96e1-72c808170b47', '2022-09-14 16:48:09.157', NULL, 'd1585653-c998-480c-9d45-824d2b603adb', 'PENDING');
INSERT INTO public.order_status VALUES ('2aa3cf13-3a62-4363-9eaa-574f9fc555c0', '5e2ec171-97be-4599-922c-0b48fd028589', '2022-09-14 21:13:39.227', NULL, '6a3adfac-2cbb-4922-a7e4-1db3393cc9bd', 'RECEIVED');
INSERT INTO public.order_status VALUES ('8a333428-c04a-4aea-9d1e-4ae6bc27abf6', '20ef979e-340d-4329-a9c5-387ae4f0d3c4', '2022-08-28 22:52:45.134', NULL, '83d84818-4973-450b-ad24-401532966c74', 'PENDING');
INSERT INTO public.order_status VALUES ('1633f9ad-33cf-463b-8f6d-6fe048920eff', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-09-13 21:25:22.519', '', '83d84818-4973-450b-ad24-401532966c74', 'RECEIVED');
INSERT INTO public.order_status VALUES ('5d906174-f0e6-44e6-a8b8-99edbc2ee5a5', '20ef979e-340d-4329-a9c5-387ae4f0d3c4', '2022-09-13 21:24:45.478', 'Muốn thay đổi địa chỉ giao hàng', '00452930-8228-4849-aa56-7f07f1daa03a', 'RECEIVED');
INSERT INTO public.order_status VALUES ('4f67cf88-df4e-420a-a783-cee95c240192', 'eb05aa1c-c054-43eb-96e1-72c808170b47', '2022-09-14 16:14:41.368', NULL, 'a67be0d8-6fe5-4924-9284-b3d61bbeee40', 'RECEIVED');



INSERT INTO public.orders VALUES ('d1585653-c998-480c-9d45-824d2b603adb', '731f1129-b959-47eb-8ec1-c36e26072c6b', '#9', 'eb05aa1c-c054-43eb-96e1-72c808170b47', '2022-09-14 16:48:09.082', '', true, 31501, 215501, 'anonymousUser', '2022-09-14 17:06:02.059', 'eb05aa1c-c054-43eb-96e1-72c808170b47', 'VNPAY', '53322', 'PENDING', NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.orders VALUES ('bd35dbf7-7163-4784-9f2f-31b7ded8739c', 'a8375110-c934-4a99-875f-0f5015716cd4', '#2', '20ef979e-340d-4329-a9c5-387ae4f0d3c4', '2022-08-28 22:47:38.663', 'mai nhận luôn', false, 37500, 336500, '20ef979e-340d-4329-a9c5-387ae4f0d3c4', '2022-08-28 22:47:38.663', '20ef979e-340d-4329-a9c5-387ae4f0d3c4', 'VNPAY', '53321', 'PENDING', NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.orders VALUES ('6a3adfac-2cbb-4922-a7e4-1db3393cc9bd', '11160276-4f73-494c-8f4c-74f131199494', '#10', '5e2ec171-97be-4599-922c-0b48fd028589', '2022-09-14 21:13:39.15', 'âbc', false, 31501, 1121501, '5e2ec171-97be-4599-922c-0b48fd028589', '2022-09-14 21:13:39.15', '5e2ec171-97be-4599-922c-0b48fd028589', 'COD', '53321', 'RECEIVED', NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.orders VALUES ('eab08614-0bb3-48ec-92cd-775038629e70', '4e870a12-6b67-4242-8a5c-1bda0be3e958', '#5', '280a1c4f-40cb-4b19-bde6-322149adc532', '2022-08-28 22:52:45.304', 'nhanh', false, 42000, 341000, 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-08-28 23:02:20.207', '280a1c4f-40cb-4b19-bde6-322149adc532', 'COD', '53323', 'RECEIVED', NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.orders VALUES ('ffceb60b-b0b5-4d54-b30e-f0d811828e09', 'e62be07e-490b-4d0f-b72c-f8d5da94bfca', 'DH1KH1', '6fac7f97-6c5c-44d5-9ed0-5bfc66326f2b', '2022-07-28 10:26:49.225', 'Giao hàng giờ hành chính', true, 20000, 380000, 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2021-07-30 18:15:27.491', '6fac7f97-6c5c-44d5-9ed0-5bfc66326f2b', 'COD', NULL, 'REJECTED', NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.orders VALUES ('b223f941-cfca-4555-b03d-7969da656d4f', '83e495a4-d63e-490d-8245-2d1cd9f06a16', '#1', '5e2ec171-97be-4599-922c-0b48fd028589', '2022-08-28 13:35:50.219', 'Giao nhanh', true, 37500, 471500, 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-08-30 15:37:21.493', '5e2ec171-97be-4599-922c-0b48fd028589', 'VNPAY', '53321', 'RECEIVED', NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.orders VALUES ('a67be0d8-6fe5-4924-9284-b3d61bbeee40', '731f1129-b959-47eb-8ec1-c36e26072c6b', '#8', 'eb05aa1c-c054-43eb-96e1-72c808170b47', '2022-09-14 16:14:41.293', 'fasfdfsaf', false, 31501, 215501, 'eb05aa1c-c054-43eb-96e1-72c808170b47', '2022-09-14 16:14:41.293', 'eb05aa1c-c054-43eb-96e1-72c808170b47', 'VNPAY', '53322', 'PENDING', NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.orders VALUES ('9763c16d-3174-4415-836e-037ba4c32038', '743eb8f9-0026-4956-9976-8198d5ad6349', 'DH1KH2', 'c37f89a1-932c-4350-baed-7eb0d1fb30be', '2022-07-29 09:16:19.225', 'Giao cuối tuần', true, 20000, 380000, 'c37f89a1-932c-4350-baed-7eb0d1fb30be', '2022-07-30 10:26:51.007', 'c37f89a1-932c-4350-baed-7eb0d1fb30be', 'COD', NULL, 'PENDING', NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.orders VALUES ('d1bdd57b-1784-441a-bebf-e9b22ef08d72', '4e870a12-6b67-4242-8a5c-1bda0be3e958', '#3', '280a1c4f-40cb-4b19-bde6-322149adc532', '2022-08-28 22:47:36.303', 'Giao nahnh tao dang can gap', true, 42000, 558000, '280a1c4f-40cb-4b19-bde6-322149adc532', '2022-08-28 22:47:36.303', '280a1c4f-40cb-4b19-bde6-322149adc532', 'COD', '53323', 'RECEIVED', NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.orders VALUES ('4e0d71a4-6446-42e0-92e9-88ef74c1b52b', 'a8375110-c934-4a99-875f-0f5015716cd4', '#7', '20ef979e-340d-4329-a9c5-387ae4f0d3c4', '2022-09-13 21:20:41.595', 'abc', true, 36500, 305500, 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-09-13 21:22:07.191', '20ef979e-340d-4329-a9c5-387ae4f0d3c4', 'COD', '53321', 'RECEIVED', NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.orders VALUES ('00452930-8228-4849-aa56-7f07f1daa03a', 'a8375110-c934-4a99-875f-0f5015716cd4', '#6', '20ef979e-340d-4329-a9c5-387ae4f0d3c4', '2022-09-06 23:10:16.639', 'nfasjkjf', true, 36500, 335500, '20ef979e-340d-4329-a9c5-387ae4f0d3c4', '2022-09-13 21:24:45.479', '20ef979e-340d-4329-a9c5-387ae4f0d3c4', 'COD', '53321', 'RECEIVED', NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.orders VALUES ('83d84818-4973-450b-ad24-401532966c74', 'a8375110-c934-4a99-875f-0f5015716cd4', '#4', '20ef979e-340d-4329-a9c5-387ae4f0d3c4', '2022-08-28 22:52:45.062', 'fsdaf', true, 37500, 336500, 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-09-13 21:25:22.519', '20ef979e-340d-4329-a9c5-387ae4f0d3c4', 'COD', '53321', 'RECEIVED', NULL, NULL, NULL, NULL, NULL);



INSERT INTO public.product VALUES ('87293753-e39e-473b-91e4-eae4fed864b5', true, '44dd44f8-e903-4c81-a5e1-d5916ca4ec75', NULL, '2022-07-04 23:06:30', 'Chất liệu: 90% polyeste + 10% spandex . Chất liệu vải mềm mại, thông thoáng, thấm hút nhanh tạo cảm giác cử động thoải mái . Áo thun tay ngắn dáng ôm, khoe dáng tinh tế . Thiết kiểu dáng áo kết hợp đa dạng với quần bò baggy, chân váy, quần short . Áo mặc phù hợp cho mùa thu và mùa hè', '87293753-e39e-473b-91e4-eae4fed864b5', 'Áo Thun Nữ Cổ Tròn Borip Dáng Ôm Basic', NULL, '2022-07-04 23:07:31', 1);
INSERT INTO public.product VALUES ('3166675c-8def-42a0-b9f6-975a25738ce8', true, 'f2a3b624-0944-438f-ae8c-29d5373d0892', NULL, '2022-06-26 10:26:40.021', 'Chất liệu : dù 2 lớp , trong lót dù
Màu sắc đa dạng tha hồ lựa chọn nhé các chị em.
Size : chỉ có 1 size cân nặng từ 45-60kg , chiều cao từ 1m45- 1m6
Công dụng : chống nắng , tránh gió , giữ ấm ', '8c20116a-ab17-45a9-ae03-35c62cdb029f', 'Áo Khoác Gió Nam Thể Thao Phối Lưng', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-08-27 23:07:13.379', 4);
INSERT INTO public.product VALUES ('9b0e2225-bb98-4b82-8eef-8fdf8808d885', true, '02cfad29-a96d-452d-9f46-897059ba5ecb', NULL, '2022-07-04 23:15:10', 'Chất liệu sản phẩm: 100% Polyester- Finishing by Bio Protein. Công nghệ Bio Protein giúp vải mềm mượt, bắt sáng nhẹ tạo nét sang trọng, thanh lịch. Thân thiện an toàn cho người mặc, ngăn bụi bẩn bám trên bề mặt vải. Thiết kế thời trang suông rộng, mỏng nhẹ, đệm vai áo khỏe khoắn, chắc chắn mà vẫn giữ được nét nữ tính mềm mại ', '87293753-e39e-473b-91e4-eae4fed864b5', 'Áo Sơ Mi Nữ Không Tay Cổ Tròn Cách Điệu', NULL, '2022-07-04 23:13:41', 6);
INSERT INTO public.product VALUES ('48df424e-f1f9-4ab1-97d3-6ddaa21619d9', true, '1d48c0ea-ad57-4c20-bd8d-e7a64725d860', NULL, '2022-07-10 00:28:55', 'Chất liệu 41% Polyamide + 50% Polyester + 9% Spandex. Chất liệu thể thao co dãn tốt đã, thấm hút và nhanh khô tạo cảm giác thoải mái cho mùa hè sôi nổi. Chất liệu mềm mướt và cực kì nhẹ . Kiểu dệt đặc biệt tạo nên hiệu ứng bề mặt vải có các ô nhỏ, hỗ trợ thoát ẩm mồ hôi .Thông thoáng tối đa, độ bề cao, hạn chế nhăn nhàu . Kiểu dáng Tank Top thoải mái và năng động cho các bạn trẻ đi tập thể thao,đi chơi hoặc mặc ở nhà', '87293753-e39e-473b-91e4-eae4fed864b5', 'Áo Tanktop Nam Thể Thao Năng Động - Tím Than', NULL, '2022-07-10 00:30:56', 1);
INSERT INTO public.product VALUES ('513f96f4-e4e4-4449-b146-4e039388f72e', true, 'f2a3b624-0944-438f-ae8c-29d5373d0892', NULL, '2022-07-20 19:33:05', 'Chất liệu: Pique mắt chim cấu tạo từ 60% cotton + 35% polyester và 5% spandex. Sợi vải có hiệu ứng mắt chim thích thú, bắt mắt. 
		Các lỗ thoáng mắt chim giúp thấm hút mồ hôi nhanh hơn, thông thoáng hơn. Vải bền chắc, dẻo dai hơn, không bị bai dão co rút khi giặt.', '39cea751-a426-422b-a0ac-569d711ddd38', 'Áo Polo Nam Ngắn Tay Dáng Suông Pique Giấu Cúc', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-08-27 23:07:13.515', 20);
INSERT INTO public.product VALUES ('58c6c7cf-d338-42c4-9d18-53c7f16b941b', true, '0d00e1ab-6c6f-4e65-a0aa-b4284477417c', NULL, '2022-07-10 00:46:07', 'Chất liệu: Airmax bao gồm 49% cotton, 47% polyester và 4% spandex . Vải airmax thoáng mát tối đa, mềm mại, mịn màng . Thấm hút mồ hôi, co giãn đàn hồi tốt tạo cảm giác dễ chịu . Thoải mái vận động cường độ cao với vải bền bỉ.Thiết kế phối cổ, bo tay khỏe khoắn, lịch sự tạo điểm nhấn riêng.Form dáng polo cơ bản, dễ mặc và phối với nhiều trang phục khác nhau', '8c20116a-ab17-45a9-ae03-35c62cdb029f', 'Áo Polo Nam Airmax Phối Bo Thoáng Khí', NULL, '2022-07-10 00:46:03', 1);
INSERT INTO public.product VALUES ('711ce3ec-e651-46c5-8d80-2983cd5354cc', true, '0d00e1ab-6c6f-4e65-a0aa-b4284477417c', NULL, '2022-07-20 19:55:05', 'Chất liệu vải: Aircool có thành phần 85% Polyamide và 15% spandex. Sợi Polyamide là cấu tạo chính giúp tạo cảm giác thoải mái, dễ chịu khi mặc. 
		15% spandex giúp sợi vải thêm bền chắc, tạo form dáng thời trang. ản phẩm giúp bạn thoải mái, dễ chịu, khỏe khoắn hơn.', '39cea751-a426-422b-a0ac-569d711ddd38', 'Áo Polo Nam Airycool Thoáng Mát Phối Bo', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-08-27 22:54:37.801', 4);
INSERT INTO public.product VALUES ('20dfca66-1842-44a6-b543-95a87e26b04b', true, '0d00e1ab-6c6f-4e65-a0aa-b4284477417c', NULL, '2022-07-20 20:01:05', 'Chất liệu vải Airmax bao gồm 49% cotton, 47% polyester và 4% spandex. Áo có phom dáng polo cơ bản, dễ mặc và phối với nhiều trang phục khác nhau. 
		Chi tiết in hình animal giúp tạo điểm nhấn trẻ trung và mới lạ. Vải thoáng mát, mềm mại, mịn màng.', '39cea751-a426-422b-a0ac-569d711ddd38', 'Áo Polo Nam Airmax In Ngực-Trắng', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-08-27 22:54:37.938', 142);
INSERT INTO public.product VALUES ('cf408a06-85bb-46a0-b0e5-0d30825a7f3c', true, '0d00e1ab-6c6f-4e65-a0aa-b4284477417c', NULL, '2022-07-20 19:59:05', 'Chất liệu vải Airmax bao gồm 49% cotton, 47% polyester và 4% spandex. hấm hút mồ hôi, co giãn đàn hồi tốt tạo cảm giác dễ chịu. 
		Chi tiết in hình animal giúp tạo điểm nhấn trẻ trung và mới lạ. Vải thoáng mát, mềm mại, mịn màng.', '39cea751-a426-422b-a0ac-569d711ddd38', 'Áo Polo Nam Airmax In Ngực - Đen', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-08-27 22:54:38.076', 20);
INSERT INTO public.product VALUES ('15cee405-252c-4c87-9691-7b950ea4f3e2', true, 'f27a78db-a593-42c4-8390-0f7ef01106a1', NULL, '2022-07-10 00:37:30', 'Chất liệu: Lụa nến . Vải lụa là chất liệu vải có bề mặt mỏng, mịn, cảm giác mềm mướt khi chạm tay vào . Vải lụa nến cao cấp chống nhăn nhàu, giữ form khi mặc . Khả năng thấm hút mồ hôi tốt tạo sự thoáng mát khi mặc và không gây kích ứng da . Độ óng tự nhiên, chống tia UV hữu hiệu dưới ánh nắng mặt trời', '386004f5-f0d7-4626-a75f-2d3c3158c504', 'Áo Sơ Mi Nam Dài Tay Cổ Đức Lụa Nến', NULL, '2022-07-10 00:36:57', 3);
INSERT INTO public.product VALUES ('a8888137-a017-4844-b8b8-3655b53d3dd3', true, '89a5780d-7288-4342-9ab9-dbb3104da77b', NULL, '2022-07-10 00:32:01', 'Chất liệu vải polyester công nghệ Nano. Ve áo hẹp với chi tiết thùa khuyết. Ve áo được cài chặt bằng hai nút phía trước. Thân trước có 2 bên túi cân xứng cùng túi trong giúp để những vật dụng quan trọng như ví, thẻ . Hàng cúc trên cổ tay áo có bọ nổi . Kiểu dáng Slim Fit, vừa vặn với ngực và eo, và tay áo', '87293753-e39e-473b-91e4-eae4fed864b5', 'Áo Vest Nam Nano Công Sở Trẻ Trung', NULL, '2022-07-10 00:31:48', 1);
INSERT INTO public.product VALUES ('70280218-0a6c-47f3-90b1-49a9d8e4221c', false, 'bcabbac6-1758-4be2-8c09-41a372f28224', NULL, '2022-07-20 19:27:05', 'Chất liệu: Pique mắt chim với thành phần 60% Cotton USA + 35% Polyester + 5% Spandex. Sử dụng Cotton USA - sợi cotton tốt nhất trên thế giới.
	    Chất liệu vải độc đáo với hiệu ứng mắt chim độc đáo, mới lạ và trẻ trung. Vải pique thông thoáng, thấm hút tốt có độ bền cao.', '39cea751-a426-422b-a0ac-569d711ddd38', 'Áo Polo Nam Pique Mắt Chim Basic Co Giãn Thoáng Khí', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-09-13 22:53:42.595', 6);
INSERT INTO public.product VALUES ('dd6ae7be-ad59-448d-ad5a-9df73270be13', true, '0d00e1ab-6c6f-4e65-a0aa-b4284477417c', NULL, '2022-07-10 00:50:27', 'Chất liệu vải Askin cao cấp thành phần 95% Askin + 5% Spandex. Cảm giác mát lạnh từ khi chạm vào da, giảm đến 15% so với nhiệt độ thực ở ngoài trời. Khả năng thoát hơi ẩm tuyệt vời, giúp mồ hôi khô liên tục, mang đến cảm giác thoáng mát suốt cả ngày dài . Tính năng kháng tia UV, bảo vệ làn da người mặc . Áo polo ASKIN được làm đơn giản nên phù hợp để phối kết hợp với nhiều trang phục khác nhau', '39cea751-a426-422b-a0ac-569d711ddd38', 'Áo Polo Nam Askin Bo Trơn - Vàng', NULL, '2022-07-10 00:50:41', 1);
INSERT INTO public.product VALUES ('b860144a-6a9b-4866-a56a-bdec57ef1786', false, 'bcabbac6-1758-4be2-8c09-41a372f28224', NULL, '2022-07-20 19:26:05', 'Chất liệu: vải coolmax với cấu trúc sợi đặc biệt. Sợi vải bao gồm nhiều rãnh dẹt giúp thoáng khí, thoát ẩm cực tốt. Áo polo nam Coolmax mang lại cảm giác mát mẻ, dễ chịu cho người mặc ngay cả khi vận động mạnh. Thiết kế khỏe khoắn, nam tính. Thích hợp mặc đi chơi, đi làm, hoạt động thể thao, cafe, hẹn hò với bạn bè.', '39cea751-a426-422b-a0ac-569d711ddd38', 'Áo Polo Nam Coolmax Lacoste', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-09-13 22:53:42.148', 3);
INSERT INTO public.product VALUES ('0ff0d2d5-b02f-4237-a4eb-2fe1387d7b1b', true, 'e70f7999-f0c8-44a4-8cd3-3d98c95d032d', NULL, '2022-07-10 00:43:16', 'Chất liệu 80% Cotton Compact + 20% Iscra . Sợi vải Iscra với chiết suất sinh học, được polymer hóa trong quá trình sản xuất tạo thành cấu trúc lò xo xoắn đàn hồi vượt trội . Áo cho cảm giác mặc thông thoáng, mềm mịn . Co giãn đàn hồi tự nhiên, giữ form tốt vượt trội so với Spandex . Thấm hút hiệu quả và hạn chế nhăn nhàu . Form suông phù hợp với nhiều vóc dáng cơ thể . ', '8c20116a-ab17-45a9-ae03-35c62cdb029f', 'Áo Phông Nam Vải Iscra Siêu Mềm Mịn', NULL, '2022-07-10 00:44:06', 18);
INSERT INTO public.product VALUES ('255e2f0e-6586-45c1-9b44-99e50d3cd33d', true, 'e70f7999-f0c8-44a4-8cd3-3d98c95d032d', NULL, '2022-07-10 00:42:13', 'Chất liệu 100% Cotton Compact. Sợi bông cao cấp được sản xuất từ công nghệ kéo sợi Compact tiên tiến nhất hiện nay . Khắc phục được hạn chế xù lông của sợi thông thường giúp cho bề mặt vải mềm mịn và độ bền tăng cao . Thấm hút mồ hôi cực tốt, thoáng mát rất thích hợp với thời tiết nóng ẩm của Việt Nam . Vải có khả năng co giãn tốt, thích hợp với chuyển động của cơ thể khi hoạt động . Kiểu dáng suông phù hợp mọi vóc dáng', '8c20116a-ab17-45a9-ae03-35c62cdb029f', 'Áo Phông Nam Cotton Compact In Chữ Phản Quang', NULL, '2022-07-10 00:40:31', 9);
INSERT INTO public.product VALUES ('20a81c4c-de1d-4a9c-aa2e-b955063e62e6', true, '0d00e1ab-6c6f-4e65-a0aa-b4284477417c', NULL, '2022-07-20 19:31:05', 'Chất liệu: Pique mắt chim cấu tạo từ 60% cotton + 35% polyester và 5% spandex. Độ co giãn đàn hồi vải tốt. 
		Pique mắt chim là vải độc đáo với sự kết hợp giữa 3 loại sợi mới tạo ra hiệu ứng mắt chim thú vị. Vải bền chắc, dẻo dai hơn, không bị bai dão co rút.', '8c20116a-ab17-45a9-ae03-35c62cdb029f', 'Áo Polo Nam Pique Mắt Chim Thêu Ngực', NULL, '2022-07-20 19:32:15', 3);
INSERT INTO public.product VALUES ('1d107139-a667-460f-bda6-1d2d928fc06e', true, '0d00e1ab-6c6f-4e65-a0aa-b4284477417c', NULL, '2022-07-20 19:28:05', 'Chất liệu: Pique mắt chim cấu tạo từ 60% cotton + 35% polyester và 5% spandex. Sợi vải có hiệu ứng mắt chim độc đáo trên về mặt. 
		Sợi vải mềm mại, mịn màng. Thông thoáng, thấm hút mồ hôi tốt, không bị co rút, bai dão khi giặt. Độ co giãn tốt, giữ được form khi mặc và bền màu', '39cea751-a426-422b-a0ac-569d711ddd38', 'Áo Polo Nam Pique Mắt Chim Phối Kẻ', NULL, '2022-07-20 19:29:15', 2);
INSERT INTO public.product VALUES ('22a15198-4e02-4535-80e3-3aa783514593', true, '0d00e1ab-6c6f-4e65-a0aa-b4284477417c', NULL, '2022-07-20 19:29:05', 'Chất liệu: Pique mắt chim cấu tạo từ 60% cotton + 35% polyester và 5% spandex. Sợi cotton USA đạt chuẩn chứng nhận Mỹ. 
		Hiệu ứng mắt chim độc đáo trên về mặt, mềm mại, mịn màng. Thông thoáng, thấm hút mồ hôi tốt, không bị co rút, bai dão khi giặt.', '39cea751-a426-422b-a0ac-569d711ddd38', 'Áo Polo Nam Pique Mắt Chim Phối Bo Kẻ Nẹp', NULL, '2022-07-20 19:30:15', 2);
INSERT INTO public.product VALUES ('47af61e5-039e-4646-958b-a8d0a0763265', true, '0d00e1ab-6c6f-4e65-a0aa-b4284477417c', NULL, '2022-07-20 19:37:05', 'Chất liệu: Pique mắt chim cấu tạo từ 60% cotton + 35% polyester và 5% spandex. Áo polo nam mắt chim Melange phối nẹp - thiết kế mới. 
		Thiết kế trẻ trung, đơn giản phù hợp với các giới tính và độ tuổi khác nhau. Form áo ôm vừa phải phù hợp với nhiều vóc dáng.', '39cea751-a426-422b-a0ac-569d711ddd38', 'Áo Polo Nam Pique Mắt Chim Melange Phối Nẹp', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-08-27 22:54:38.351', 3);
INSERT INTO public.product VALUES ('fa8c1c00-195b-49fc-a2a8-4d1dd88f8e16', true, '0d00e1ab-6c6f-4e65-a0aa-b4284477417c', NULL, '2022-07-20 19:41:05', 'Chất liệu: CoolMax với thành phần 95% CoolMax + 5% spandex. Vải coolmax nhẹ, xốp, thoáng mát, truyền dẫn ẩm tốt. 
		Bề mặt sợi có rãnh làm tăng khả năng truyền dẫn khí và ẩm. Thấm hút mồ hôi tốt, hút ẩm nhanh và nhanh khô tạo sự thoải mái.', '39cea751-a426-422b-a0ac-569d711ddd38', 'Áo Polo Nam Coolmax Phối Vai', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-08-27 22:54:38.489', 1);
INSERT INTO public.product VALUES ('8ffc7829-5e9e-41e4-9572-f799084730c5', true, '0d00e1ab-6c6f-4e65-a0aa-b4284477417c', NULL, '2022-07-20 19:45:05', 'Chất liệu: CoolMax: 95% CoolMax, 5% spandex. Vải nhẹ, xốp, thoáng mát, truyền dẫn ẩm tốt, bề mặt sợi có rãnh làm tăng khả năng truyền dẫn khí và ẩm. 
		Khả năng thấm hút mồ hôi tốt, hút ẩm nhanh tạo sự thoải mái cho cơ thể khi sử dụng. Đàn hồi co giãn tốt, ít nhàu, tiện dụng khi vận động và sử dụng trong mọi hoàn cảnh.', '39cea751-a426-422b-a0ac-569d711ddd38', 'Áo Polo Nam Coolmax Ngắn Tay Phối Bo', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-08-27 22:54:38.626', 3);
INSERT INTO public.product VALUES ('16204f7e-e987-4da5-9a1b-bf240690546c', true, '0d00e1ab-6c6f-4e65-a0aa-b4284477417c', NULL, '2022-07-20 19:47:05', 'Chất liệu: CoolMax với thành phần 95% CoolMax + 5% spandex. Vải coolmax nhẹ, xốp, thoáng mát, truyền dẫn ẩm tốt. 
		Thấm hút mồ hôi tốt, hút ẩm nhanh và nhanh khô tạo sự thoải mái. Vải có tính đàn hồi co giãn tốt, ít nhàu, tiện dụng khi vận động.', '39cea751-a426-422b-a0ac-569d711ddd38', 'Áo Polo Nam Coolmax Thoáng Mát Bo Cổ Phối Màu', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-08-27 22:54:38.762', 2);
INSERT INTO public.product VALUES ('de270524-6fb5-44e0-8a0c-3b847610440d', true, '0d00e1ab-6c6f-4e65-a0aa-b4284477417c', NULL, '2022-07-20 19:35:05', 'Chất liệu: Pique mắt chim cấu tạo từ 60% cotton + 35% polyester và 5% spandex. Sử dụng Cotton USA - sợi cotton tốt nhất trên thế giới. 
		Công nghệ nhuộm Solid Dyed tạo nên hiệu ứng bắt mắt. Chất liệu vải độc đáo với hiệu ứng mắt chim độc đáo, mới lạ và trẻ trung.', '39cea751-a426-422b-a0ac-569d711ddd38', 'Áo Polo Nam Pique Mắt Chim Phối Tay Cổ Kẻ', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-08-27 22:54:38.214', 3);
INSERT INTO public.product VALUES ('ee0bc7ec-666b-49a3-9215-a3d02a3224fa', true, '0d00e1ab-6c6f-4e65-a0aa-b4284477417c', NULL, '2022-07-20 19:49:05', 'Thành phần sợi: 95% Coolmax, 5% Spandex. Áo polo Coolmax - một trong những dòng sản phẩm chủ đạo dành cho mùa hè tại Việt Nam. 
		Vải áo nhẹ mềm mại, khi mặc cho cảm giác khoan khoái, dễ chịu vô cùng. Co giãn tốt, bền màu và hạn chế nhăn nhàu trong quá trình sử dụng.', '39cea751-a426-422b-a0ac-569d711ddd38', 'Áo Polo Nam Phối Bo Coolmax Thoáng Mát', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-08-27 22:54:38.899', 1);
INSERT INTO public.product VALUES ('f9be3945-dade-4178-99ef-8439e83a8d03', true, '0d00e1ab-6c6f-4e65-a0aa-b4284477417c', NULL, '2022-07-20 19:51:05', 'Chất liệu:Vải Airycool với thành phần 85% nylon + 15% spandex. Công nghệ làm mát FREEZING tiên tiến siêu khô thoáng. 
		Kết cấu vải siêu mịn, tỉ mỉ, chắc chắn. Trong lượng nhẹ, thoáng khí hút ẩm cực tốt. Thấm hút mồ hôi tốt', '39cea751-a426-422b-a0ac-569d711ddd38', 'Áo Polo Nam Airycool Giữ Form Thoáng Mát', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-08-27 22:54:39.036', 2);
INSERT INTO public.product VALUES ('98d90096-1d73-4c61-bf22-b6b6bb1ec6c6', true, '0d00e1ab-6c6f-4e65-a0aa-b4284477417c', NULL, '2022-07-20 19:43:05', 'Chất liệu: CoolMax với thành phần 95% CoolMax + 5% spandex. Vải coolmax nhẹ, xốp, thoáng mát, truyền dẫn ẩm tốt. 
		Bề mặt sợi có rãnh làm tăng khả năng truyền dẫn khí và ẩm. Áo polo nam thiết kế tay lỡ kết hợp với dáng suông giúp người mặc luôn được thoải mái hàng ngày.', '39cea751-a426-422b-a0ac-569d711ddd38', 'Áo Polo Nam Coolmax Phối Bo Kẻ', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-08-27 22:54:39.173', 2);
INSERT INTO public.product VALUES ('54dfdeca-eac0-46ce-ad03-7376836dec68', true, '0d00e1ab-6c6f-4e65-a0aa-b4284477417c', NULL, '2022-07-20 19:39:05', 'Chất liệu: Pique mắt chim cấu tạo từ 60% cotton + 35% polyester và 5% spandex. Sử dụng Cotton USA - sợi cotton tốt nhất trên thế giới. 
		Công nghệ nhuộm Solid Dyed tạo nên hiệu ứng bắt mắt. Form áo suông được basic dễ phối đồ.', '39cea751-a426-422b-a0ac-569d711ddd38', 'Áo Polo Nam Pique Mắt Chim Họa Tiết Vương Miện', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-08-27 22:54:39.447', 7);
INSERT INTO public.product VALUES ('0fe76906-5aff-465d-ae58-2d86a6101919', true, '', NULL, '2022-07-20 19:53:05', 'Chất liệu:Vải Airycool với thành phần 85% nylon + 15% spandex. Công nghệ làm mát FREEZING tiên tiến siêu khô thoáng. 
		Kết cấu vải siêu mịn, tỉ mỉ, chắc chắn. trọng lượng nhẹ, thoáng khí hút ẩm cực tốt. Thấm hút mồ hôi tốt', '39cea751-a426-422b-a0ac-569d711ddd38', 'Áo Polo Nam Airycool Thoáng Khí Thấm Hút Tốt', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-08-27 22:54:39.585', 12);
INSERT INTO public.product VALUES ('cb8820fc-1847-47b8-86f4-9680358433c8', true, '', NULL, '2022-07-20 19:57:05', 'Chất liệu vải Airmax bao gồm 49% cotton, 47% polyester và 4% spandex. Chi tiết in hình animal giúp tạo điểm nhấn trẻ trung và mới lạ. 
		Vải thoáng mát, mềm mại, mịn màng. Thấm hút mồ hôi, co giãn đàn hồi tốt tạo cảm giác dễ chịu. Độ bền cao', '39cea751-a426-422b-a0ac-569d711ddd38', 'Áo Polo Nam Airmax In Ngực - Be', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-08-27 22:54:39.31', 4);



INSERT INTO public.product_option VALUES ('8699c581-c6f8-4b04-a4f3-469b8bea1b11', 'cf6f96df-2b99-4862-9b75-ccff88e93b29', 'https://bizweb.sapocdn.net/100/438/408/products/scn4172-den-3.jpg?v=1655280775793', 277000, '9b0e2225-bb98-4b82-8eef-8fdf8808d885', 100, 'S', '6a5e9e31-5046-460f-ad3b-3670f579ac0b', NULL);
INSERT INTO public.product_option VALUES ('3f59e9e3-61f2-4435-b8da-549b36839fea', 'cf6f96df-2b99-4862-9b75-ccff88e93b29', 'https://bizweb.sapocdn.net/100/438/408/products/tsn5002-den-10.jpg?v=1654220408350', 169000, '87293753-e39e-473b-91e4-eae4fed864b5', 100, 'XL', '3f1582ad-cc3a-4a2c-8eb3-4b70d92e910e', NULL);
INSERT INTO public.product_option VALUES ('b6851bd1-dd38-4d2c-93c6-d5580a6e0fdc', 'cf6f96df-2b99-4862-9b75-ccff88e93b29', 'https://bizweb.sapocdn.net/100/438/408/products/vem4007-den-5.jpg?v=1641611132510', 1589000, '9b0e2225-bb98-4b82-8eef-8fdf8808d885', 100, 'XL', '3f1582ad-cc3a-4a2c-8eb3-4b70d92e910e', NULL);
INSERT INTO public.product_option VALUES ('406e93e1-6f45-4042-8255-bce62d4585cb', '5d1f416b-0d03-48fd-a645-712891128038', 'https://bizweb.sapocdn.net/100/438/408/products/akm4027-xah-qjm3077-xde-1.jpg?v=1642381400497', 299000, '87293753-e39e-473b-91e4-eae4fed864b5', 50, 'M', '7419b033-baab-4138-a742-4a3030e3aa01', NULL);
INSERT INTO public.product_option VALUES ('6521b64e-7ec5-4fb0-b279-6c325aa551d1', '343ac3c3-b114-4c31-9a64-479d51437881', 'https://bizweb.sapocdn.net/100/438/408/products/tsn5002-ddo-13.jpg?v=1654220408350', 169000, '87293753-e39e-473b-91e4-eae4fed864b5', 100, 'L', 'd01b5a31-c56b-48d3-af5e-d5af631a6520', NULL);
INSERT INTO public.product_option VALUES ('37d1f3a2-3d82-43fb-98e2-1b3a7c7401fc', 'ae965d3b-b6b2-4e46-bb16-ddd798b6bbe4', 'https://bizweb.sapocdn.net/100/438/408/products/stm5071-tit-4-399821eb-3c07-4d80-8473-bb7917b3e113.jpg?v=1646973830017', 229000, '48df424e-f1f9-4ab1-97d3-6ddaa21619d9', 100, 'M', '7419b033-baab-4138-a742-4a3030e3aa01', NULL);
INSERT INTO public.product_option VALUES ('19a05d52-d6c2-4202-b2f9-a61f624783d2', '9b97d6b6-cba3-43a6-8949-36a138a04e33', 'https://bizweb.sapocdn.net/100/438/408/products/scn4172-tra-2.jpg?v=1656094090237', 139000, '9b0e2225-bb98-4b82-8eef-8fdf8808d885', 100, 'M', '7419b033-baab-4138-a742-4a3030e3aa01', NULL);
INSERT INTO public.product_option VALUES ('dfe47b9e-c391-4d8c-9865-39860e8b2b0f', 'cf6f96df-2b99-4862-9b75-ccff88e93b29', 'https://bizweb.sapocdn.net/100/438/408/products/akm4027-den-qjm3077-xde-3.jpg?v=1642381400497', 399000, '3166675c-8def-42a0-b9f6-975a25738ce8', 60, 'S', '6a5e9e31-5046-460f-ad3b-3670f579ac0b', NULL);
INSERT INTO public.product_option VALUES ('e64cd91f-d462-4f92-84d1-d2e96186bfa6', 'ae965d3b-b6b2-4e46-bb16-ddd798b6bbe4', 'https://bizweb.sapocdn.net/100/438/408/products/spm3399-tmn.jpg?v=1636507010367', 277000, '15cee405-252c-4c87-9691-7b950ea4f3e2', 100, 'M', '7419b033-baab-4138-a742-4a3030e3aa01', NULL);
INSERT INTO public.product_option VALUES ('b138cc1b-d8f6-4a98-b46f-2c74c25b8982', 'ae965d3b-b6b2-4e46-bb16-ddd798b6bbe4', 'https://bizweb.sapocdn.net/100/438/408/products/spm3399-tmn.jpg?v=1636507010367', 277000, '15cee405-252c-4c87-9691-7b950ea4f3e2', 100, 'L', 'd01b5a31-c56b-48d3-af5e-d5af631a6520', NULL);
INSERT INTO public.product_option VALUES ('49b8ed77-7760-490b-9fe7-37a941910bec', '9b97d6b6-cba3-43a6-8949-36a138a04e33', 'https://bizweb.sapocdn.net/100/438/408/products/tsm5217-tra-3.jpg?v=1649995253020', 269000, '255e2f0e-6586-45c1-9b44-99e50d3cd33d', 100, 'M', '7419b033-baab-4138-a742-4a3030e3aa01', NULL);
INSERT INTO public.product_option VALUES ('c57021c4-aa1c-4e87-8075-060cda956fb8', '9b97d6b6-cba3-43a6-8949-36a138a04e33', 'https://bizweb.sapocdn.net/100/438/408/products/tsm5217-tra-3.jpg?v=1649995253020', 269000, '255e2f0e-6586-45c1-9b44-99e50d3cd33d', 100, 'L', 'd01b5a31-c56b-48d3-af5e-d5af631a6520', NULL);
INSERT INTO public.product_option VALUES ('b0007753-8a3b-49ce-a715-ef60238a7b50', 'cf6f96df-2b99-4862-9b75-ccff88e93b29', 'https://bizweb.sapocdn.net/100/438/408/products/vem4007-den-5.jpg?v=1641611132510', 1589000, '9b0e2225-bb98-4b82-8eef-8fdf8808d885', 200, 'L', 'd01b5a31-c56b-48d3-af5e-d5af631a6520', NULL);
INSERT INTO public.product_option VALUES ('fc85318a-79e1-40a7-af05-af820dddfda3', 'cf6f96df-2b99-4862-9b75-ccff88e93b29', 'https://bizweb.sapocdn.net/100/438/408/products/scn4172-den-3.jpg?v=1655280775793', 277000, '9b0e2225-bb98-4b82-8eef-8fdf8808d885', 100, 'M', '7419b033-baab-4138-a742-4a3030e3aa01', NULL);
INSERT INTO public.product_option VALUES ('06878557-6d83-4b3d-ae32-53484ea3da52', '9b97d6b6-cba3-43a6-8949-36a138a04e33', 'https://bizweb.sapocdn.net/100/438/408/products/tsn5002-tra-14.jpg?v=1654220408350', 249000, '87293753-e39e-473b-91e4-eae4fed864b5', 40, 'L', 'd01b5a31-c56b-48d3-af5e-d5af631a6520', NULL);
INSERT INTO public.product_option VALUES ('832b1824-0476-40c9-8d66-91e2741b65e8', '5d1f416b-0d03-48fd-a645-712891128038', 'https://bizweb.sapocdn.net/100/438/408/products/vem4007-xdm-4.jpg?v=1641618490457', 1589000, 'a8888137-a017-4844-b8b8-3655b53d3dd3', 100, 'M', '7419b033-baab-4138-a742-4a3030e3aa01', NULL);
INSERT INTO public.product_option VALUES ('3b198caf-2ad1-40af-9875-bccc3af22636', 'cf6f96df-2b99-4862-9b75-ccff88e93b29', 'https://bizweb.sapocdn.net/100/438/408/products/tsm5217-den-3.jpg?v=1649995253823', 269000, '255e2f0e-6586-45c1-9b44-99e50d3cd33d', 100, 'M', '7419b033-baab-4138-a742-4a3030e3aa01', NULL);
INSERT INTO public.product_option VALUES ('fbccf615-e4a8-48b7-97ee-fcafcbbd6e96', 'cf6f96df-2b99-4862-9b75-ccff88e93b29', 'https://bizweb.sapocdn.net/100/438/408/products/tsm5217-den-3.jpg?v=1649995253823', 269000, '255e2f0e-6586-45c1-9b44-99e50d3cd33d', 100, 'XL', '3f1582ad-cc3a-4a2c-8eb3-4b70d92e910e', NULL);
INSERT INTO public.product_option VALUES ('04cdc49c-96d8-4cce-b500-5198483d2321', 'cf6f96df-2b99-4862-9b75-ccff88e93b29', 'https://bizweb.sapocdn.net/100/438/408/products/tsm5261-den-6.jpg?v=1648002598750', 184000, '0ff0d2d5-b02f-4237-a4eb-2fe1387d7b1b', 99, 'M', '7419b033-baab-4138-a742-4a3030e3aa01', NULL);
INSERT INTO public.product_option VALUES ('35172424-2934-4194-8a99-da6c8105420f', 'cf6f96df-2b99-4862-9b75-ccff88e93b29', 'https://bizweb.sapocdn.net/100/438/408/products/tsm5261-den-6.jpg?v=1648002598750', 184000, '0ff0d2d5-b02f-4237-a4eb-2fe1387d7b1b', 99, 'L', 'd01b5a31-c56b-48d3-af5e-d5af631a6520', NULL);
INSERT INTO public.product_option VALUES ('55b1ee96-3b33-41ca-8713-af3f2a2c2a89', '343ac3c3-b114-4c31-9a64-479d51437881', 'https://bizweb.sapocdn.net/100/438/408/products/tsm5261-ddo-13.jpg?v=1648002598750', 184000, '0ff0d2d5-b02f-4237-a4eb-2fe1387d7b1b', 100, 'M', '7419b033-baab-4138-a742-4a3030e3aa01', NULL);
INSERT INTO public.product_option VALUES ('f1f23dc3-aa60-4c56-8b9f-b9f7e1e1ce10', '9b97d6b6-cba3-43a6-8949-36a138a04e33', 'https://bizweb.sapocdn.net/100/438/408/products/apm5115-tra-10.jpg?v=1654759032397', 299000, '58c6c7cf-d338-42c4-9d18-53c7f16b941b', 100, 'M', '7419b033-baab-4138-a742-4a3030e3aa01', NULL);
INSERT INTO public.product_option VALUES ('079a184e-2bc0-44bc-b5b7-1ba3bed7e2ae', '9b97d6b6-cba3-43a6-8949-36a138a04e33', 'https://bizweb.sapocdn.net/100/438/408/products/apm5115-tra-10.jpg?v=1654759032397', 299000, '58c6c7cf-d338-42c4-9d18-53c7f16b941b', 100, 'L', 'd01b5a31-c56b-48d3-af5e-d5af631a6520', NULL);
INSERT INTO public.product_option VALUES ('1b4a9c86-f28a-4974-9033-f0fd7fe52bc4', '9d3254e6-5f41-41bf-b49b-7aff8f8b9428', 'https://bizweb.sapocdn.net/100/438/408/products/apm5089-vag-4-7aa701c0-aef2-4f16-b4bd-bfc495f66e62.jpg?v=1646820667967', 299000, 'dd6ae7be-ad59-448d-ad5a-9df73270be13', 100, 'M', '7419b033-baab-4138-a742-4a3030e3aa01', NULL);
INSERT INTO public.product_option VALUES ('81301c9a-084e-4546-a13f-2016e56fa397', '9d3254e6-5f41-41bf-b49b-7aff8f8b9428', 'https://bizweb.sapocdn.net/100/438/408/products/apm5089-vag-4-7aa701c0-aef2-4f16-b4bd-bfc495f66e62.jpg?v=1646820667967', 299000, 'dd6ae7be-ad59-448d-ad5a-9df73270be13', 100, 'L', 'd01b5a31-c56b-48d3-af5e-d5af631a6520', NULL);
INSERT INTO public.product_option VALUES ('54bcf3b4-5326-4777-8b94-c565428a126b', '5d1f416b-0d03-48fd-a645-712891128038', 'https://bizweb.sapocdn.net/100/438/408/products/ao-polo-namapm3519-cba1-yody-vn.jpg?v=1657076512617', 299000, 'b860144a-6a9b-4866-a56a-bdec57ef1786', 100, 'L', 'd01b5a31-c56b-48d3-af5e-d5af631a6520', NULL);
INSERT INTO public.product_option VALUES ('04bf500e-8c5b-4b2f-9694-f884dc591b66', '9b97d6b6-cba3-43a6-8949-36a138a04e33', 'https://bizweb.sapocdn.net/100/438/408/products/apm3299-tra-qjm5029-xah-39.jpg?v=1655697277310', 299000, '70280218-0a6c-47f3-90b1-49a9d8e4221c', 100, 'L', 'd01b5a31-c56b-48d3-af5e-d5af631a6520', NULL);
INSERT INTO public.product_option VALUES ('09078ac2-04e9-46e3-8de8-204418697dc1', '9b97d6b6-cba3-43a6-8949-36a138a04e33', 'https://bizweb.sapocdn.net/100/438/408/products/ao-polo-namapm3639-den-qjm3075-xnh-2-yody-vn.jpg?v=1654828356357', 299000, '1d107139-a667-460f-bda6-1d2d928fc06e', 100, 'L', 'd01b5a31-c56b-48d3-af5e-d5af631a6520', NULL);
INSERT INTO public.product_option VALUES ('582feddd-c928-4b50-8695-af39ea358218', 'cf6f96df-2b99-4862-9b75-ccff88e93b29', 'https://bizweb.sapocdn.net/100/438/408/products/apm3641-den-6.jpg?v=1657334006967', 299000, '22a15198-4e02-4535-80e3-3aa783514593', 50, 'S', '6a5e9e31-5046-460f-ad3b-3670f579ac0b', NULL);
INSERT INTO public.product_option VALUES ('d2dff107-96a4-4b15-a43e-67d314db44bd', 'cf6f96df-2b99-4862-9b75-ccff88e93b29', 'https://bizweb.sapocdn.net/thumb/large/100/438/408/products/apm3739-den-3.jpg?v=1657851934000', 209000, '20a81c4c-de1d-4a9c-aa2e-b955063e62e6', 50, 'S', '6a5e9e31-5046-460f-ad3b-3670f579ac0b', NULL);
INSERT INTO public.product_option VALUES ('704c5b07-f398-4e63-b616-0bcf18259c38', 'cf6f96df-2b99-4862-9b75-ccff88e93b29', 'https://bizweb.sapocdn.net/thumb/large/100/438/408/products/apm5039-tit5-yody-vn.jpg?v=1657868560000', 299000, 'de270524-6fb5-44e0-8a0c-3b847610440d', 100, 'XL', '3f1582ad-cc3a-4a2c-8eb3-4b70d92e910e', NULL);
INSERT INTO public.product_option VALUES ('794d96ac-d59d-43cd-87fb-c4839aa34912', '9d3254e6-5f41-41bf-b49b-7aff8f8b9428', 'https://bizweb.sapocdn.net/thumb/large/100/438/408/products/apm5331-vag04.jpg?v=1658134437000', 299000, '47af61e5-039e-4646-958b-a8d0a0763265', 100, 'XL', '3f1582ad-cc3a-4a2c-8eb3-4b70d92e910e', NULL);
INSERT INTO public.product_option VALUES ('6261139d-4063-4a1a-9066-7c609de163bc', '9b97d6b6-cba3-43a6-8949-36a138a04e33', 'https://bizweb.sapocdn.net/thumb/large/100/438/408/products/apm3725-tmt-7.jpg?v=1654496319000', 299000, 'fa8c1c00-195b-49fc-a2a8-4d1dd88f8e16', 50, 'M', '7419b033-baab-4138-a742-4a3030e3aa01', NULL);
INSERT INTO public.product_option VALUES ('a0792aff-056b-4ee1-9c14-c1b2de5c8b4d', 'cf6f96df-2b99-4862-9b75-ccff88e93b29', 'https://bizweb.sapocdn.net/thumb/large/100/438/408/products/apm3681-den-2-2.jpg?v=1635494004000', 299000, '98d90096-1d73-4c61-bf22-b6b6bb1ec6c6', 50, 'M', '7419b033-baab-4138-a742-4a3030e3aa01', NULL);
INSERT INTO public.product_option VALUES ('7c8bd78b-fdad-4091-8159-2a78d33008a5', '9b97d6b6-cba3-43a6-8949-36a138a04e33', 'https://bizweb.sapocdn.net/thumb/large/100/438/408/products/apm4233-tmt-2.jpg?v=1652682824000', 279000, '8ffc7829-5e9e-41e4-9572-f799084730c5', 50, 'M', '7419b033-baab-4138-a742-4a3030e3aa01', NULL);
INSERT INTO public.product_option VALUES ('5af2ec10-19c3-4433-b9df-07da37772694', '343ac3c3-b114-4c31-9a64-479d51437881', 'https://bizweb.sapocdn.net/thumb/large/100/438/408/products/apm5181-dod-4.jpg?v=1657071548000', 299000, '16204f7e-e987-4da5-9a1b-bf240690546c', 100, '2XL', '6873eb3a-0038-46d8-b767-9f87d00913be', NULL);
INSERT INTO public.product_option VALUES ('5c0571b8-cbfe-49bf-8ce1-4f01b39de170', '5d1f416b-0d03-48fd-a645-712891128038', 'https://bizweb.sapocdn.net/thumb/large/100/438/408/products/apm5179-xng.jpg?v=1655439495000', 299000, 'ee0bc7ec-666b-49a3-9215-a3d02a3224fa', 100, '2XL', '6873eb3a-0038-46d8-b767-9f87d00913be', NULL);
INSERT INTO public.product_option VALUES ('31838b32-92b6-4131-b07f-18c1821a4f32', '9b97d6b6-cba3-43a6-8949-36a138a04e33', 'https://bizweb.sapocdn.net/thumb/large/100/438/408/products/apm5083-tra-5.jpg?v=1657614738000', 299000, 'f9be3945-dade-4178-99ef-8439e83a8d03', 100, '2XL', '6873eb3a-0038-46d8-b767-9f87d00913be', NULL);
INSERT INTO public.product_option VALUES ('bb988639-adf3-4e88-84a3-ed0c3792f779', '9b97d6b6-cba3-43a6-8949-36a138a04e33', 'https://bizweb.sapocdn.net/thumb/large/100/438/408/products/apm5351-tra-2-80196d65-0bf2-4b5a-8a0c-1771b5836c3f.jpg?v=1658134910000', 299000, '711ce3ec-e651-46c5-8d80-2983cd5354cc', 50, 'S', '6a5e9e31-5046-460f-ad3b-3670f579ac0b', NULL);
INSERT INTO public.product_option VALUES ('6a37d937-de09-45be-a0e1-5b641f7748cc', '9b97d6b6-cba3-43a6-8949-36a138a04e33', 'https://bizweb.sapocdn.net/100/438/408/products/apm4053-den-2.jpg?v=1648449199367', 244000, '513f96f4-e4e4-4449-b146-4e039388f72e', 50, 'S', '6a5e9e31-5046-460f-ad3b-3670f579ac0b', NULL);
INSERT INTO public.product_option VALUES ('35d64b0d-be40-4dd9-aeb8-ec17dd09ca0e', '343ac3c3-b114-4c31-9a64-479d51437881', 'https://bizweb.sapocdn.net/100/438/408/products/tsm5261-ddo-13.jpg?v=1648002598750', 184000, '0ff0d2d5-b02f-4237-a4eb-2fe1387d7b1b', 99, 'L', 'd01b5a31-c56b-48d3-af5e-d5af631a6520', NULL);
INSERT INTO public.product_option VALUES ('639eb3ff-de4f-4313-9521-1afab9c5a647', 'cf6f96df-2b99-4862-9b75-ccff88e93b29', 'https://bizweb.sapocdn.net/100/438/408/products/tsm5261-den-6.jpg?v=1648002598750', 184000, '0ff0d2d5-b02f-4237-a4eb-2fe1387d7b1b', 98, 'XL', '3f1582ad-cc3a-4a2c-8eb3-4b70d92e910e', NULL);
INSERT INTO public.product_option VALUES ('f498a37c-b7ac-4c4f-912b-bab24fb1d0c1', 'cf6f96df-2b99-4862-9b75-ccff88e93b29', 'https://bizweb.sapocdn.net/thumb/large/100/438/408/products/ao-polo-nam-apm4025-den-2-yody-vn.jpg?v=1651564055000', 217000, 'cf408a06-85bb-46a0-b0e5-0d30825a7f3c', 100, 'L', 'd01b5a31-c56b-48d3-af5e-d5af631a6520', NULL);
INSERT INTO public.product_option VALUES ('31cb57ad-ca5a-4eba-ab95-1c4ce614db73', 'cf6f96df-2b99-4862-9b75-ccff88e93b29', 'https://bizweb.sapocdn.net/thumb/large/100/438/408/products/apm5081-den-6.jpg?v=1653875580000', 299000, '54dfdeca-eac0-46ce-ad03-7376836dec68', 1, 'XL', '3f1582ad-cc3a-4a2c-8eb3-4b70d92e910e', NULL);
INSERT INTO public.product_option VALUES ('8cb575fc-c52b-4e43-ae7a-5bf8f8047896', '9b97d6b6-cba3-43a6-8949-36a138a04e33', 'https://bizweb.sapocdn.net/thumb/large/100/438/408/products/apm4025-bee-23.jpg?v=1650705312000', 217000, 'cb8820fc-1847-47b8-86f4-9680358433c8', 49, 'S', '6a5e9e31-5046-460f-ad3b-3670f579ac0b', NULL);
INSERT INTO public.product_option VALUES ('fc907208-6867-4e21-b362-4708af5093fc', '9b97d6b6-cba3-43a6-8949-36a138a04e33', 'https://bizweb.sapocdn.net/thumb/large/100/438/408/products/apm4025-tra-3-63aaeed6-c320-40a0-bb99-c3ca832baa9a.jpg?v=1650705172000', 217000, '20dfca66-1842-44a6-b543-95a87e26b04b', 98, 'L', 'd01b5a31-c56b-48d3-af5e-d5af631a6520', NULL);
INSERT INTO public.product_option VALUES ('50ace73d-e502-4173-ae23-b598eda3392d', '9b97d6b6-cba3-43a6-8949-36a138a04e33', 'https://bizweb.sapocdn.net/thumb/large/100/438/408/products/apm5243-tra-6.jpg?v=1646533421000', 299000, '0fe76906-5aff-465d-ae58-2d86a6101919', 2, 'S', '6a5e9e31-5046-460f-ad3b-3670f579ac0b', NULL);
INSERT INTO public.product_option VALUES ('79587205-ba4d-406f-be9a-d805134b53e9', '9b97d6b6-cba3-43a6-8949-36a138a04e33', 'https://bizweb.sapocdn.net/100/438/408/products/tsm5217-tra-3.jpg?v=1649995253020', 269000, '255e2f0e-6586-45c1-9b44-99e50d3cd33d', 98, 'XL', '3f1582ad-cc3a-4a2c-8eb3-4b70d92e910e', NULL);
INSERT INTO public.product_option VALUES ('ad8dbf03-40b2-462e-876d-75d9811b62b1', 'cf6f96df-2b99-4862-9b75-ccff88e93b29', 'https://bizweb.sapocdn.net/100/438/408/products/tsm5217-den-3.jpg?v=1649995253823', 269000, '255e2f0e-6586-45c1-9b44-99e50d3cd33d', 99, 'L', 'd01b5a31-c56b-48d3-af5e-d5af631a6520', NULL);



INSERT INTO public.reset_token VALUES ('c839c3ef-5355-42d2-935e-97ba9e440cda', '2022-08-18 09:44:04.647', '890036', '20ef979e-340d-4329-a9c5-387ae4f0d3c4', true);
INSERT INTO public.reset_token VALUES ('b1cc97a7-c8c7-4de8-8d26-179663c6e6c4', '2022-08-18 11:28:41.698', '874006', '20ef979e-340d-4329-a9c5-387ae4f0d3c4', true);
INSERT INTO public.reset_token VALUES ('da4602a8-5b30-4b04-8f52-ca7c1bdc6577', '2022-08-18 11:30:03.709', '746543', '20ef979e-340d-4329-a9c5-387ae4f0d3c4', true);
INSERT INTO public.reset_token VALUES ('a90ff866-c5d2-4143-aff5-dbc52391bb67', '2022-08-18 11:36:26.103', '883951', '143ef20e-085a-428a-8835-11463d7511dd', true);
INSERT INTO public.reset_token VALUES ('60e66cfc-e36e-4ebe-b694-d1e0d01b9592', '2022-08-18 11:45:59.105', '947757', '143ef20e-085a-428a-8835-11463d7511dd', true);
INSERT INTO public.reset_token VALUES ('edea6ea7-23e3-49b0-aa97-d6d8d4864f94', '2022-08-18 11:46:46.787', '608176', '143ef20e-085a-428a-8835-11463d7511dd', true);
INSERT INTO public.reset_token VALUES ('1b3bcfc2-6c4d-4304-b946-ba5eaf6b07a0', '2022-08-18 12:07:43.165', '833383', '20ef979e-340d-4329-a9c5-387ae4f0d3c4', true);
INSERT INTO public.reset_token VALUES ('2900b948-44d9-46f2-bfc9-752374207b0b', '2022-08-18 12:08:54.897', '866793', '143ef20e-085a-428a-8835-11463d7511dd', true);
INSERT INTO public.reset_token VALUES ('3b1f2bae-9d0f-4288-8a48-709a08a7a73a', '2022-08-18 12:09:30.368', '245530', '143ef20e-085a-428a-8835-11463d7511dd', true);
INSERT INTO public.reset_token VALUES ('37737a49-2c4b-409e-8541-740d9dbf844a', '2022-08-18 16:56:55.543', '326743', '20ef979e-340d-4329-a9c5-387ae4f0d3c4', true);
INSERT INTO public.reset_token VALUES ('dc4d5fc1-91b6-4387-8869-6b0891b9656a', '2022-08-18 18:10:03.581', '481446', 'd6e59f7d-bec2-4fb9-9e4d-84bd4c4b6da1', true);
INSERT INTO public.reset_token VALUES ('3285afe5-67a9-40ec-b76a-0975f44394dd', '2022-08-18 18:08:17.699', '865064', 'd6e59f7d-bec2-4fb9-9e4d-84bd4c4b6da1', false);
INSERT INTO public.reset_token VALUES ('95e2b160-ab23-4f27-a18b-769ac7214df9', '2022-08-18 18:19:32.795', '686980', 'd6e59f7d-bec2-4fb9-9e4d-84bd4c4b6da1', false);
INSERT INTO public.reset_token VALUES ('42fce83f-9df2-4102-92f0-82aeacb859fd', '2022-08-04 00:02:36.484', '552642', 'f6ff6302-1f3e-47ac-9900-0b8a18c1c3e2', true);
INSERT INTO public.reset_token VALUES ('0c110540-139c-42b2-bbf4-7c2f1745018c', '2022-08-03 00:01:15.896', '276603', 'f6ff6302-1f3e-47ac-9900-0b8a18c1c3e2', true);
INSERT INTO public.reset_token VALUES ('5abf0fd6-8599-49aa-9b4d-d066354cbf68', '2022-08-03 00:01:15.896', '517413', 'f6ff6302-1f3e-47ac-9900-0b8a18c1c3e2', true);
INSERT INTO public.reset_token VALUES ('b3e29731-9a72-442b-b1be-d63dd1f13ab9', '2022-08-03 00:01:15.896', '657838', 'f6ff6302-1f3e-47ac-9900-0b8a18c1c3e2', true);
INSERT INTO public.reset_token VALUES ('5d5d651d-6886-4192-afc1-f35cf4849c83', '2022-08-03 00:01:15.896', '458763', 'f6ff6302-1f3e-47ac-9900-0b8a18c1c3e2', true);
INSERT INTO public.reset_token VALUES ('11e6e4e4-8dad-4f74-8259-7b5dadfad6e7', '2022-08-03 00:01:15.896', '423117', 'f6ff6302-1f3e-47ac-9900-0b8a18c1c3e2', true);
INSERT INTO public.reset_token VALUES ('e86de7d6-547a-41c7-85be-4e5bd0e546a9', '2022-08-03 00:01:15.896', '722079', 'f6ff6302-1f3e-47ac-9900-0b8a18c1c3e2', true);
INSERT INTO public.reset_token VALUES ('e6029040-e1e9-462d-971e-f00f08722566', '2022-08-03 00:01:15.896', '620092', 'f6ff6302-1f3e-47ac-9900-0b8a18c1c3e2', true);
INSERT INTO public.reset_token VALUES ('f39fb7e1-37fe-4ceb-aa9f-d731dfae8b02', '2022-08-04 00:13:23.059', '479912', 'f6ff6302-1f3e-47ac-9900-0b8a18c1c3e2', true);
INSERT INTO public.reset_token VALUES ('abe68895-8b0a-4426-9b08-f30fea9688e2', '2022-08-07 10:17:32.66', '956528', '20ef979e-340d-4329-a9c5-387ae4f0d3c4', true);
INSERT INTO public.reset_token VALUES ('458d4baf-afae-4d10-a3d1-5748251f114a', '2022-08-11 09:48:05.959', '335519', '20ef979e-340d-4329-a9c5-387ae4f0d3c4', true);
INSERT INTO public.reset_token VALUES ('0ee08a44-b309-4aa1-af92-918e732fcb28', '2022-08-11 09:58:39.136', '792114', '20ef979e-340d-4329-a9c5-387ae4f0d3c4', true);
INSERT INTO public.reset_token VALUES ('1a464c8d-0d23-4731-8048-181b63d7dac3', '2022-08-11 10:24:19.491', '859472', '20ef979e-340d-4329-a9c5-387ae4f0d3c4', true);
INSERT INTO public.reset_token VALUES ('242a6d2f-03d2-4d54-ad6e-5175d626e4d1', '2022-08-11 10:46:20.019', '564204', '20ef979e-340d-4329-a9c5-387ae4f0d3c4', true);
INSERT INTO public.reset_token VALUES ('f9a86117-7b23-477f-986d-2261ef6c161b', '2022-08-13 16:07:43.538', '992149', '20ef979e-340d-4329-a9c5-387ae4f0d3c4', true);
INSERT INTO public.reset_token VALUES ('9f07c070-cf7c-4a6e-bc5e-69e239c1bab4', '2022-08-18 18:24:31.131', '319410', 'd6e59f7d-bec2-4fb9-9e4d-84bd4c4b6da1', false);
INSERT INTO public.reset_token VALUES ('14447cf6-0b7f-4b6d-bf75-7f204c8fdf04', '2022-08-13 18:20:16.509', '204349', 'f6ff6302-1f3e-47ac-9900-0b8a18c1c3e2', false);
INSERT INTO public.reset_token VALUES ('7835c2c2-2ddb-4c02-a9dd-551f4f4a4202', '2022-08-17 07:00:41.954', '625776', '20ef979e-340d-4329-a9c5-387ae4f0d3c4', false);
INSERT INTO public.reset_token VALUES ('610d9f83-ec9f-4c24-a603-385a631bdd2a', '2022-08-17 07:10:21.11', '323584', '20ef979e-340d-4329-a9c5-387ae4f0d3c4', false);
INSERT INTO public.reset_token VALUES ('cfbae2ad-6a1f-45b8-be33-b969b4c9f459', '2022-08-17 07:50:46.993', '694141', '20ef979e-340d-4329-a9c5-387ae4f0d3c4', true);
INSERT INTO public.reset_token VALUES ('d1b34995-8b79-48fe-8901-c3f71a56ddea', '2022-08-17 07:58:43.62', '803109', '20ef979e-340d-4329-a9c5-387ae4f0d3c4', true);
INSERT INTO public.reset_token VALUES ('8af1befe-f531-4f5b-8205-2b668a729c4d', '2022-08-17 15:24:13.745', '625487', '20ef979e-340d-4329-a9c5-387ae4f0d3c4', true);
INSERT INTO public.reset_token VALUES ('292152a3-6a6b-433b-a1c9-fb259de3165f', '2022-08-18 18:28:34.526', '927735', 'd6e59f7d-bec2-4fb9-9e4d-84bd4c4b6da1', false);
INSERT INTO public.reset_token VALUES ('daee1b9b-9ad5-4b9c-9a94-afc9445c61de', '2022-08-18 18:33:33.832', '138588', 'bb5bac29-e233-4f5c-bcd3-c45a50ff95d3', false);
INSERT INTO public.reset_token VALUES ('e3b36cc4-8681-4d2b-9193-54df0e73f1fb', '2022-08-18 18:37:55.139', '402589', 'bb5bac29-e233-4f5c-bcd3-c45a50ff95d3', false);
INSERT INTO public.reset_token VALUES ('9a6199fc-c58d-4daa-818e-41f6dbdb4ff5', '2022-08-18 18:41:13.362', '789602', 'bb5bac29-e233-4f5c-bcd3-c45a50ff95d3', false);
INSERT INTO public.reset_token VALUES ('9f9fcd7d-8373-4d32-8784-4b92f87c8113', '2022-08-18 18:43:26.081', '811523', 'bb5bac29-e233-4f5c-bcd3-c45a50ff95d3', false);
INSERT INTO public.reset_token VALUES ('879f3819-2ede-4c15-bc36-62d5d6c19759', '2022-08-22 18:16:29.487', '518204', 'f6ff6302-1f3e-47ac-9900-0b8a18c1c3e2', true);
INSERT INTO public.reset_token VALUES ('09d667eb-1198-40be-98e3-db8771336942', '2022-08-22 18:18:31.995', '455583', 'f6ff6302-1f3e-47ac-9900-0b8a18c1c3e2', true);
INSERT INTO public.reset_token VALUES ('bce8f977-82f3-4c07-b1e7-2392fe17b1c0', '2022-08-22 18:18:36.233', '469734', 'f6ff6302-1f3e-47ac-9900-0b8a18c1c3e2', true);
INSERT INTO public.reset_token VALUES ('76b5372b-7f6c-4470-828b-ad10d4e9e6bb', '2022-08-22 18:18:39.624', '222671', 'f6ff6302-1f3e-47ac-9900-0b8a18c1c3e2', true);
INSERT INTO public.reset_token VALUES ('29f9677c-a54e-45ca-906a-77fa22a2b924', '2022-08-22 18:19:44.447', '296477', 'f6ff6302-1f3e-47ac-9900-0b8a18c1c3e2', true);



INSERT INTO public.review VALUES ('39b86f0a-bd05-4a80-be91-d58bfff5791c', true, 'Sản phẩm đúng mô tả. Giao hàng nhanh', 'f6ff6302-1f3e-47ac-9900-0b8a18c1c3e2', '2022-07-31 09:58:36.411', '3166675c-8def-42a0-b9f6-975a25738ce8', 5, NULL, NULL, NULL, 'f6ff6302-1f3e-47ac-9900-0b8a18c1c3e2', NULL);
INSERT INTO public.review VALUES ('d1bdd57b-1784-441a-bebf-e9b22ef08d72', true, 'TEST THOOOI', '280a1c4f-40cb-4b19-bde6-322149adc532', '2022-08-28 22:52:45.304', '8527b705-b0f4-419c-9e59-2b7f5e86a067', 4.5, NULL, NULL, NULL, '280a1c4f-40cb-4b19-bde6-322149adc532', 'eab08614-0bb3-48ec-92cd-775038629e70');
INSERT INTO public.review VALUES ('a2c80397-099a-4aa2-bb37-863951f60bde', true, 'Áo đẹp, đúng size yêu cầu, chất vải tốt, đẹp. Đườngmay không nuột lắm nhưng cũng chấp nhận được, có nhãn mác xuất sứ Made in China chính hãng.', '5e2ec171-97be-4599-922c-0b48fd028589', '2022-08-30 16:39:06.465', '20dfca66-1842-44a6-b543-95a87e26b04b', 5, NULL, '5e2ec171-97be-4599-922c-0b48fd028589', '2022-08-30 16:39:06.465', '5e2ec171-97be-4599-922c-0b48fd028589', NULL);
INSERT INTO public.review VALUES ('69912249-0ec6-415a-b92e-184a2831d475', true, 'Áo đẹp, đúng size yêu cầu, chất vải tốt, đẹp. Đườngmay không nuột lắm nhưng cũng chấp nhận được, có nhãn mác xuất sứ Made in China chính hãng. ', '5e2ec171-97be-4599-922c-0b48fd028589', '2022-09-14 23:56:15.392', '0ff0d2d5-b02f-4237-a4eb-2fe1387d7b1b', 5, NULL, '5e2ec171-97be-4599-922c-0b48fd028589', '2022-09-14 23:56:15.392', '5e2ec171-97be-4599-922c-0b48fd028589', NULL);






INSERT INTO public.size VALUES ('6a5e9e31-5046-460f-ad3b-3670f579ac0b', 'S', true, '2022-08-23 10:51:54.675');
INSERT INTO public.size VALUES ('7419b033-baab-4138-a742-4a3030e3aa01', 'M', true, '2022-08-23 10:51:54.675');
INSERT INTO public.size VALUES ('d01b5a31-c56b-48d3-af5e-d5af631a6520', 'L', true, '2022-08-23 10:52:55.444');
INSERT INTO public.size VALUES ('3f1582ad-cc3a-4a2c-8eb3-4b70d92e910e', 'XL', true, '2022-08-23 10:51:45.075');
INSERT INTO public.size VALUES ('6873eb3a-0038-46d8-b767-9f87d00913be', 'XXL', true, '2022-08-23 10:10:54.444');
INSERT INTO public.size VALUES ('76de6f2f-6d71-4a0d-8b94-28c951a5c653', 'XXXL', true, '2022-08-28 12:05:54.015');
INSERT INTO public.size VALUES ('b9c4b2a0-0a1a-4784-94e8-638aec8c81eb', 'XXXXL', false, '2022-09-11 00:54:32.268');



INSERT INTO public.suggest VALUES ('3ce4d9c9-f9ce-4661-a5f3-7985428604ef', '319ba30f-97c9-4e03-8c9c-db5dd15cf8c1', '8fcea0c0-e4f3-4568-8c41-d815260c7d38', NULL, '6a5e9e31-5046-460f-ad3b-3670f579ac0b');
INSERT INTO public.suggest VALUES ('f545038c-823b-4c41-b931-126cc2779c86

', '423d9164-f46f-4614-923e-b98d77f6c752', 'a14dbed0-3fb3-43e0-803c-bf7e74e8f04b', NULL, '7419b033-baab-4138-a742-4a3030e3aa01');
INSERT INTO public.suggest VALUES ('8e3cd69d-34ee-4f88-a20a-7a64efefbfc8

', 'ea603e44-f611-45bb-b1a2-c07fd2ef7e45', '2e8c9ca5-46e6-4776-a4a1-f071a610caf3', NULL, 'd01b5a31-c56b-48d3-af5e-d5af631a6520');
INSERT INTO public.suggest VALUES ('d6c341e7-af5f-445d-a5c7-0e3e472a6537

', 'ea603e44-f611-45bb-b1a2-c07fd2ef7e45

efd11705-0aa3-430b-944b-efb7477e72c8', '94d2c208-92a2-42bc-982a-e28d1d79bb93', NULL, '3f1582ad-cc3a-4a2c-8eb3-4b70d92e910e');
INSERT INTO public.suggest VALUES ('6c6a0655-6412-482a-814f-3757fc6e9539

', '4ff7c4bd-a325-4f90-8721-68bfe4b6a6ec', '5413c08c-9bfd-4f4e-8a99-644795da4329', NULL, '6873eb3a-0038-46d8-b767-9f87d00913be');
INSERT INTO public.suggest VALUES ('70158b9e-25d7-45b3-b77f-7553d2f5f60e

', 'b1dfdc29-6c66-4c7b-94bb-71458fc210b3', '558cb46a-4e80-46e9-9f00-67b919700616', NULL, '76de6f2f-6d71-4a0d-8b94-28c951a5c653');



INSERT INTO public.topic VALUES ('1', 'Thời trang Nam', 'https://www.kooding.com/images/static/shop-mens-main.jpg');
INSERT INTO public.topic VALUES ('2', 'Thời trang nữ', 'https://i.pinimg.com/originals/73/59/86/73598686a903e1a51c02f06c1814fca3.png
');
INSERT INTO public.topic VALUES ('4', 'Tin tức', 'https://4.bp.blogspot.com/-1pIw0JQ1gxA/Wrs9i3PFPLI/AAAAAAAAFq0/J8tDjfARn5o8eQjz_84pRANvJJnL_h9sQCLcBGAs/s1600/short%2Bpants.jpg');
INSERT INTO public.topic VALUES ('3', 'Mẹo hữu ích', 'https://cdn.shopify.com/s/files/1/0551/7839/5845/files/TOBISTREETWEAR-2_2048x2048.png?v=1633058414');



INSERT INTO public.transaction_history VALUES ('af55eb3c-46c1-4f80-a5e9-fdf1eef784a1', 471500, 'NCB', '2022-08-28 13:37:01', '37511753', 'b223f941-cfca-4555-b03d-7969da656d4f', 'b223f941-cfca-4555-b03d-7969da656d4f', '00', '13825292');
INSERT INTO public.transaction_history VALUES ('216af573-3c5a-46d2-8f59-66b7bb0d8f48', 215501, 'VNPAY', '2022-09-14 17:05:58', '25409800', 'd1585653-c998-480c-9d45-824d2b603adb', 'd1585653-c998-480c-9d45-824d2b603adb', '02', '0');



INSERT INTO public.type VALUES ('89435dcd-8589-4e02-94c2-1ff16e7f617b', true, 'Nam');
INSERT INTO public.type VALUES ('caf86bc4-5d64-41d9-9644-cc4d5434bbe4', true, 'Nữ');
INSERT INTO public.type VALUES ('235187a1-8d85-452c-9780-5a4044816cb2', true, 'Unisex');



INSERT INTO public.user_notification VALUES ('e0679d8f-7af2-4302-8fb3-9353e28e093a', '61018676-af7e-419d-9b91-f246315fbc8c', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6');
INSERT INTO public.user_notification VALUES ('a252aad5-72be-4aa2-8e72-a368a07dd72b', 'bec3d357-c9d8-4d8d-89c1-8df6a04a58b0', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6');
INSERT INTO public.user_notification VALUES ('55afc669-b50c-4e24-b85b-408f2b51a977', 'f4369c48-d8b6-40e7-8b08-30924c2a4cb5', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6');
INSERT INTO public.user_notification VALUES ('5a659e51-8603-45bb-94dc-9deb375588e9', '73177be2-6c1b-4661-ae19-c1c3785947e3', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6');
INSERT INTO public.user_notification VALUES ('08fd8c22-b052-4edb-ae20-cda6fca89765', '09dece81-44d3-4319-b117-8da5d4bc9116', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6');
INSERT INTO public.user_notification VALUES ('6e52906d-03ea-4b0c-85a2-1128dac6bc88', '66e74799-3803-4ddc-aeaa-d9a5f3017de9', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6');
INSERT INTO public.user_notification VALUES ('f1216544-b87a-4cdc-8284-28569dafa377', 'fb9df931-0e03-4380-9e56-d0a2e12dbc54', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6');
INSERT INTO public.user_notification VALUES ('a204719e-2a31-48c2-8806-0ccee994210f', '3bb5f06c-5529-4e41-a97d-06ee30fef2b3', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6');
INSERT INTO public.user_notification VALUES ('85ea04e9-541b-499e-83bf-c61b6708620d', 'e316e621-ee0a-406e-aeb1-11e817a219b1', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6');
INSERT INTO public.user_notification VALUES ('286947a9-e7d8-461e-a049-48f762cdc06c', '040af831-0774-47e1-86bb-48ae8db7c235', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6');
INSERT INTO public.user_notification VALUES ('c34bb176-799b-44cb-90f5-3e9aab1b3697', '5a76f248-e918-4207-aaeb-45e052a2173f', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6');
INSERT INTO public.user_notification VALUES ('c7833d5d-12ca-4291-994c-d6ebfd349d68', 'adc2b5f7-cdba-471c-9ae4-a1668e624964', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6');
INSERT INTO public.user_notification VALUES ('89ea3410-c5f6-410f-9d62-26a7cf4df113', '35f7c9bc-bd07-47c6-b1f6-131c7134b08c', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6');
INSERT INTO public.user_notification VALUES ('a646c855-1bfc-413a-9bf9-4b90c51e5c0c', '013d2ed4-2993-4a1b-ad25-caf2af5c76db', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6');
INSERT INTO public.user_notification VALUES ('fa22269e-28d4-4c48-a9b7-68200cd06f38', '539b895b-8e3c-4f87-81b3-d2a3eb607abb', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6');



INSERT INTO public.users VALUES ('53967738-d838-41f4-81df-60c9e65b9f8e', true, NULL, '2022-06-26 10:26:42.473', 'staff@gmail.com', 'Nguyễn Nhân', true, 'Viên', '$2a$10$RgjzGXJXF9EmkuvnTuGov.mPY.vObED.Ou/GiTxMzsaJdnNM5dVdi', NULL, 'ROLE_STAFF', NULL, '2022-06-26 10:26:42.473', NULL);
INSERT INTO public.users VALUES ('bb2b994b-9737-4b58-81e7-4497f7d1c3eb', true, 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-07-16 12:29:39.929', '23hun1@gmail.com', 'Nguyen', true, 'Hung', '$2a$10$M.CCs560j1uy2QtuUGWev.0mz.7vwIMq0lIaoYcV5lHPxOBFWntb6', '0999888999', 'ROLE_ADMIN', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-07-16 12:29:39.929', '2008-07-16 00:00:00');
INSERT INTO public.users VALUES ('03f14900-de1c-476d-8281-ac599aec4930', true, 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-07-16 17:13:21.023', 'create2@gmail.com', 'Nguyen', true, 'Hung update', '$2a$10$.DYHNAjmUjYe57noE79aTufXoXXdE8hiy0EQK8Qjz4rKvS0LViYFu', '0999888991', 'ROLE_ADMIN', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-07-16 18:07:49.536', '2008-07-16 00:00:00');
INSERT INTO public.users VALUES ('f6ff6302-1f3e-47ac-9900-0b8a18c1c3e2', true, 'anonymousUser', '2022-07-31 09:58:36.411', 'hungnnph09719@fpt.edu.vn', 'vu', NULL, 'anh', '$2a$10$vkIzUePuEu7fc0i7UZAkk.tjcwdnaKIa2fL4E5Nz.ITaibCILWNa6', '0987654222', 'ROLE_CUSTOMER', 'anonymousUser', '2022-08-13 18:22:14.638', '2005-05-09 00:00:00');
INSERT INTO public.users VALUES ('bb5bac29-e233-4f5c-bcd3-c45a50ff95d3', true, 'anonymousUser', '2022-08-18 18:33:17.203', 'ngaptph15102@fpt.edu.vn', 'Phạm', false, 'nga', '$2a$10$H85CN8ggsVCKjJdrOGVgvey1ox816RfdviFoVqatXL/MGfk5ZwJC6', '0355222333', 'ROLE_CUSTOMER', 'anonymousUser', '2022-08-18 18:43:47.61', '2000-01-12 00:00:00');
INSERT INTO public.users VALUES ('3cae485f-6b50-4baf-afd2-e17efc3c18bf', true, 'anonymousUser', '2022-08-07 17:07:15.072', 'dam@gmail.com', 'sư', NULL, 'de', '$2a$10$wIwCskrvPekcSobXDN8VVu7Rrvbe/9l.YTsDRuxKcwU7iss9QDzN.', '0355301887', 'ROLE_CUSTOMER', 'anonymousUser', '2022-08-07 17:07:15.072', '2002-02-02 00:00:00');
INSERT INTO public.users VALUES ('41560275-3409-4cde-9e5e-c4a0585ca3c6', true, 'anonymousUser', '2022-08-06 20:38:01.236', 'anhghd@gmail.com', 'phạm', true, 'anh', '$2a$10$whScVUecwSoImAIDLURnquosZ/.OQNm/U1wiDBmlSfH.fTHLVPfVC', '0366445555', 'ROLE_CUSTOMER', '41560275-3409-4cde-9e5e-c4a0585ca3c6', '2022-08-07 17:25:34.768', '2002-02-02 00:00:00');
INSERT INTO public.users VALUES ('fcf1566a-b1db-4fdf-aaf0-40f06dceaf6c', true, 'anonymousUser', '2022-08-07 17:57:44.896', 'bug@gmail.com', 'bug', false, 'bug', '$2a$10$jGRn9rdKBEt4XVkPbgAtiOJRjkDNucJyOqcdOn4ZEykEPE8qmmYZm', '0355444555', 'ROLE_CUSTOMER', 'anonymousUser', '2022-08-07 17:57:44.896', '2002-02-02 00:00:00');
INSERT INTO public.users VALUES ('20ef979e-340d-4329-a9c5-387ae4f0d3c4', true, 'anonymousUser', '2022-07-30 23:38:09.95', 'anhcoming@gmail.com', 'ho', NULL, 'ten', '$2a$10$B6sOa8Fo/KR86oWlE/gW1.aASquy.Ye7.kvxjPlaMn.MpBkc9UXi6', '0355301999', 'ROLE_CUSTOMER', 'anonymousUser', '2022-08-17 07:12:48.352', '2002-07-29 00:00:00');
INSERT INTO public.users VALUES ('fb6e037a-9f6b-49e0-826e-d1d1539413f6', true, NULL, '2022-06-26 10:26:42.24', 'admin@gmail.com', 'Chủ', true, 'Cửa hàng', '$2a$10$4RmIJx/TARs8OqIq7BhSqOs77B4dP6rVH649IX8PSWd24O7/olST6', '0988999222', 'ROLE_ADMIN', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-08-07 10:36:00.97', '1998-07-16 00:00:00');
INSERT INTO public.users VALUES ('372fc438-53d3-4e78-987c-ea957d7b2ef4', true, 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-07-16 17:04:39.845', 'create1@gmail.com', 'Nguyen', true, 'Hung', '$2a$10$Wey46vYqlOqKk6WvHh/idOkr.xTllUF6/xbtafZEXl51v2UkYm1ni', '0999888992', 'ROLE_STAFF', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-08-07 06:56:57.59', '2008-07-16 00:00:00');
INSERT INTO public.users VALUES ('12b204c8-7cd1-4a36-babc-eacda60e6086', true, 'anonymousUser', '2022-08-17 15:56:54.094', 'a@gmail.com', 'Nguyễn', false, 'Văn A', '$2a$10$.YMgxR9qr8myEvFcx5CIGexag6ZXq42T71hPaefEEs1xvyYoG.h4q', '0311225556', 'ROLE_CUSTOMER', 'anonymousUser', '2022-08-17 15:56:54.094', '2002-04-05 00:00:00');
INSERT INTO public.users VALUES ('68b84d1b-151e-4ebe-9f93-5e97ed8bff98', true, 'anonymousUser', '2022-08-07 17:59:19.943', 'bug2@gmail.com', 'nguyễn', true, 'tân', '$2a$10$4j58aQdYXFR6zCCa2tIOde4OvOtHRtioDZcjPkCWVMH3NwkjZLPEq', '0335544555', 'ROLE_CUSTOMER', '68b84d1b-151e-4ebe-9f93-5e97ed8bff98', '2022-08-07 18:00:07.188', '2002-02-02 00:00:00');
INSERT INTO public.users VALUES ('143ef20e-085a-428a-8835-11463d7511dd', true, 'anonymousUser', '2022-07-31 10:06:11.218', 'nga@gmail.com', 'Phạm', true, 'Nga', '$2a$10$8to6VadmFiYprmJ/QIXJ7u1yXAftuTLvgizhe68DJpYtffl48TRv.', '0326872723', 'ROLE_CUSTOMER', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-08-07 06:57:34.628', '2002-03-13 00:00:00');
INSERT INTO public.users VALUES ('14cd3243-e4fa-4e5a-abdb-4232f68c9675', true, 'anonymousUser', '2022-08-11 11:12:58.096', 'pho@gmail.com', 'Trần', false, 'Văn Phô', '$2a$10$dH93rOrikr99/FdG0ojuE.dGDnUs3ClwzUWHDq/1rHnck9NeaVuJe', '0322111999', 'ROLE_CUSTOMER', '14cd3243-e4fa-4e5a-abdb-4232f68c9675', '2022-08-11 11:19:57.872', '1999-01-01 07:00:00');
INSERT INTO public.users VALUES ('af90d6af-516c-4b89-92c2-9dae1e12dda4', true, 'anonymousUser', '2022-08-13 00:42:05.052', 'thangho@gmail.com', 'Ho', false, 'Thang', '$2a$10$oNsgl7vNi/xNLFwvW1iz9.3EX9ipsDHzG9r/0yCunSM3.ZZWQWIQO', '0364813037', 'ROLE_CUSTOMER', 'anonymousUser', '2022-08-13 00:42:05.052', '2001-01-01 00:00:00');
INSERT INTO public.users VALUES ('5e2ec171-97be-4599-922c-0b48fd028589', true, 'anonymousUser', '2022-08-28 12:22:10.778', 'anhtuan@gmail.com', 'Tuan', true, 'Nguyen', '$2a$10$Z42BIz98Sf9uGQEmUPVI6OZC4VuzhA9DYrXZwQilpwcbM1VgFHxci', '0378687961', 'ROLE_CUSTOMER', '5e2ec171-97be-4599-922c-0b48fd028589', '2022-08-28 12:22:34.74', '1996-01-28 07:00:00');
INSERT INTO public.users VALUES ('6fac7f97-6c5c-44d5-9ed0-5bfc66326f2b', false, NULL, '2022-06-26 10:26:42.693', 'customer1@gmail.com', 'Lê Thị', false, 'Xinh', '$2a$10$7g5RY7LO/Kecowc2vCr4aejVbq/Twe/dQAzVHkcJ.eRHjwflfAIj6', '0988777888', 'ROLE_CUSTOMER', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-08-07 06:08:16.757', NULL);
INSERT INTO public.users VALUES ('c37f89a1-932c-4350-baed-7eb0d1fb30be', false, NULL, '2022-06-26 10:26:42.911', 'customer2@gmail.com', 'Nguyễn Văn', true, 'An', '$2a$10$URk5CxFciSCjO3x3r1e33ukZCi/R8nMpaa2sLjAeCMWsDh15L1FD6', '0963012012', 'ROLE_CUSTOMER', 'fb6e037a-9f6b-49e0-826e-d1d1539413f6', '2022-08-07 07:01:55.942', NULL);
INSERT INTO public.users VALUES ('280a1c4f-40cb-4b19-bde6-322149adc532', true, 'anonymousUser', '2022-08-28 18:40:36.356', 'tunguyen@gmail.com', 'Tu', false, 'Nguyen', '$2a$10$ObB81VLMyePrXe9Xv6bK4.mKXdggnkPb.PbHSBseO4zf4GLTt8.Va', '0378689632', 'ROLE_CUSTOMER', 'anonymousUser', '2022-08-28 18:40:36.356', '2003-01-28 00:00:00');
INSERT INTO public.users VALUES ('eb05aa1c-c054-43eb-96e1-72c808170b47', true, 'anonymousUser', '2022-09-14 16:12:16.013', 'abcxyz@gmail.com', 'test', false, 'tyewbfhe', '$2a$10$B4BuhYw14V28PkDYbFq4lOFUzZZ6q7Oo3ZUet1b0hdlSQPIZAAsuC', '0368689238', 'ROLE_CUSTOMER', 'anonymousUser', '2022-09-14 16:12:16.013', '1998-08-08 00:00:00');
INSERT INTO public.users VALUES ('d6e59f7d-bec2-4fb9-9e4d-84bd4c4b6da1', true, 'anonymousUser', '2022-08-18 18:07:55.217', 'anhvdph07601@fpt.edu.vn', 'Vũ', false, 'anh', '$2a$10$vIAIguJltclI.BTei5DrgeI8DihdWFpn1W7gaedpCzKuxeMuYTBPO', '0355301888', 'ROLE_CUSTOMER', 'anonymousUser', '2022-08-18 18:29:04.901', '2000-02-12 00:00:00');



SELECT pg_catalog.setval('public.codeseq', 10, true);



ALTER TABLE ONLY public.address
    ADD CONSTRAINT address_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.blog
    ADD CONSTRAINT blog_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.body_height
    ADD CONSTRAINT body_height_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.body_weight
    ADD CONSTRAINT body_weight_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.cart
    ADD CONSTRAINT cart_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.category
    ADD CONSTRAINT category_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.color
    ADD CONSTRAINT color_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.customer_group
    ADD CONSTRAINT customer_group_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.customer_type
    ADD CONSTRAINT customer_type_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.discount_category
    ADD CONSTRAINT discount_category_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.discount_customer
    ADD CONSTRAINT discount_customer_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.discount_customer_type
    ADD CONSTRAINT discount_customer_type_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.discount
    ADD CONSTRAINT discount_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.discount_product
    ADD CONSTRAINT discount_product_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.email_log
    ADD CONSTRAINT email_log_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.exchange_detail
    ADD CONSTRAINT exchange_detail_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.exchange_media
    ADD CONSTRAINT exchange_media_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.exchange
    ADD CONSTRAINT exchange_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.favourite
    ADD CONSTRAINT favourite_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.material
    ADD CONSTRAINT material_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.notification
    ADD CONSTRAINT notification_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.order_detail
    ADD CONSTRAINT order_detail_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.order_status
    ADD CONSTRAINT order_status_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.product_option
    ADD CONSTRAINT product_option_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.product
    ADD CONSTRAINT product_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.reset_token
    ADD CONSTRAINT reset_token_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.review
    ADD CONSTRAINT review_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.ship_type
    ADD CONSTRAINT ship_type_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.size
    ADD CONSTRAINT size_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.suggest
    ADD CONSTRAINT suggest_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.topic
    ADD CONSTRAINT topic_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.transaction_history
    ADD CONSTRAINT transaction_history_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.type
    ADD CONSTRAINT type_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.user_notification
    ADD CONSTRAINT user_notification_pkey PRIMARY KEY (id);



ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);




