package com.ws.masterserver.utils.seeder;

import com.ws.masterserver.entity.*;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.common.BeanUtils;
import com.ws.masterserver.utils.common.JsonUtils;
import com.ws.masterserver.utils.common.UidUtils;
import com.ws.masterserver.utils.constants.WsConst;
import com.ws.masterserver.utils.constants.enums.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.transaction.Transactional;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

@Transactional
@Slf4j
public class ChainSeeder implements Seeder {

    private WsRepository repository = BeanUtils.getBean(WsRepository.class);

    private Random random = SecureRandom.getInstanceStrong();

    public ChainSeeder() throws NoSuchAlgorithmException {
        //add cmt vao k d' pass sonar
    }

    @Override
    public void seed() {
        List<TypeEntity> types = initTypes();
        List<ColorEntity> colors = initColors();
        List<MaterialEntity> materials = initMaterials();
        CategoryEntity category = initCategory(types);
        ProductEntity product = initProduct(category, materials);
        List<ProductOptionEntity> productOptions = initProductOptions(product, colors);
        List<OrderEntity> orders = initUsers();
        orders.forEach(order -> {
            initOrderDetails(order, productOptions);
        });

    }

    private List<TypeEntity> initTypes() {

        List<TypeEntity> types = Arrays.asList(
                TypeEntity.builder()
                        .id(UidUtils.generateUid())
                        .active(Boolean.TRUE)
                        .name(TypeEnum.MALE.name())
                        .build(),
                TypeEntity.builder()
                        .id(UidUtils.generateUid())
                        .active(Boolean.TRUE)
                        .name(TypeEnum.FEMALE.name())
                        .build(),
                TypeEntity.builder()
                        .id(UidUtils.generateUid())
                        .active(Boolean.TRUE)
                        .name(TypeEnum.UNISEX.name())
                        .build()
        );
        log.info("1. save type list: {}", JsonUtils.toJson(types));
        return repository.typeRepository.saveAll(types);
    }

    private List<MaterialEntity> initMaterials() {

        List<MaterialEntity> materials = Arrays.asList(
                MaterialEntity.builder()
                        .id(UidUtils.generateUid())
                        .name(MaterialEnum.MTR01.getName())
                        .active(Boolean.TRUE)
                        .build(),
                MaterialEntity.builder()
                        .id(UidUtils.generateUid())
                        .name(MaterialEnum.MTR02.getName())
                        .active(Boolean.TRUE)
                        .build(),
                MaterialEntity.builder()
                        .id(UidUtils.generateUid())
                        .name(MaterialEnum.MTR03.getName())
                        .active(Boolean.TRUE)
                        .build()
        );
        log.info("3. save material list: {}", JsonUtils.toJson(materials));
        repository.materialRepository.saveAll(materials);
        return materials;
    }

    private List<ColorEntity> initColors() {

        List<ColorEntity> colors = Arrays.asList(
                ColorEntity.builder()
                        .id(UidUtils.generateUid())
                        .name(ColorEnum.BLUE.getViName())
                        .hex(ColorEnum.BLUE.getHex())
                        .active(Boolean.TRUE)
                        .build(),
                ColorEntity.builder()
                        .id(UidUtils.generateUid())
                        .name(ColorEnum.WHITE.getViName())
                        .hex(ColorEnum.WHITE.getHex())
                        .active(Boolean.TRUE)
                        .build(),
                ColorEntity.builder()
                        .id(UidUtils.generateUid())
                        .name(ColorEnum.BLACK.getViName())
                        .hex(ColorEnum.BLACK.getHex())
                        .active(Boolean.TRUE)
                        .build(),
                ColorEntity.builder()
                        .id(UidUtils.generateUid())
                        .name(ColorEnum.GREEN.getViName())
                        .hex(ColorEnum.GREEN.getHex())
                        .active(Boolean.TRUE)
                        .build(),
                ColorEntity.builder()
                        .id(UidUtils.generateUid())
                        .name(ColorEnum.GREY.getViName())
                        .hex(ColorEnum.GREY.getHex())
                        .active(Boolean.TRUE)
                        .build(),
                ColorEntity.builder()
                        .id(UidUtils.generateUid())
                        .name(ColorEnum.VIOLET.getViName())
                        .hex(ColorEnum.VIOLET.getHex())
                        .active(Boolean.TRUE)
                        .build(),
                ColorEntity.builder()
                        .id(UidUtils.generateUid())
                        .name(ColorEnum.RED.getViName())
                        .hex(ColorEnum.RED.getHex())
                        .active(Boolean.TRUE)
                        .build()
        );
        log.info("2. save color list: {}", JsonUtils.toJson(colors));
        repository.colorRepository.saveAll(colors);
        return colors;
    }

    private List<ProductOptionEntity> initProductOptions(ProductEntity product, List<ColorEntity> colors) {
    
        List<ProductOptionEntity> productOptions = Arrays.asList(
                ProductOptionEntity.builder()
                        .id(UidUtils.generateUid())
                        .productId(product.getId())
                        .colorId(colors.get(getRandomIndex(colors.size())).getId())
                        .image(WsConst.Seeders.PRODUCT_OPTION_BLUE_IMG)
//                        .size(SizeEnum.S)
//                        .sizeId()
                        .qty(10L)
                        .price(WsConst.Seeders.PRODUCT_OPTION_BLUE_PRICE)
                        .build(),
                ProductOptionEntity.builder()
                        .id(UidUtils.generateUid())
                        .productId(product.getId())
                        .colorId(colors.get(getRandomIndex(colors.size())).getId())
                        .image(WsConst.Seeders.PRODUCT_OPTION_GREY_IMG)
//                        .size(SizeEnum.M)
                        .qty(5L)
                        .price(WsConst.Seeders.PRODUCT_OPTION_GREY_PRICE)
                        .build()
        );

        repository.productOptionRepository.saveAll(productOptions);
        log.info("8. save Product Option List: {}", JsonUtils.toJson(productOptions));
        return productOptions;
    }

    private ProductEntity initProduct(CategoryEntity category, List<MaterialEntity> materials) {
        ProductEntity product = ProductEntity.builder()
                .id(WsConst.Seeders.PRODUCT_ID)
                .categoryId(category.getId())
                .name(WsConst.Seeders.PRODUCT_NAME)
                .des(WsConst.Seeders.PRODUCT_DES)
                .materialId(materials.get(getRandomIndex(materials.size())).getId())
                .active(Boolean.TRUE)
                .build();
        repository.productRepository.save(product);
        log.info("7. save Product: {}", product);
        return product;
    }

    private CategoryEntity initCategory(List<TypeEntity> types) {
        CategoryEntity category = CategoryEntity.builder()
                .id(UUID.randomUUID().toString())
                .name(WsConst.Seeders.CATEGORY_NAME)
                .active(Boolean.TRUE)
                .des(WsConst.Seeders.CATEGORY_DES)
                .typeId(types.get(getRandomIndex(types.size())).getId())
                .build();
        repository.categoryRepository.save(category);
        log.info("6. save category: {}", JsonUtils.toJson(category));
        return category;

    }

    private void initOrderStatus(OrderEntity order, UserEntity customer, UserEntity staff) {
        Calendar calendar = Calendar.getInstance();
        repository.orderStatusRepository.save(OrderStatusEntity.builder()
                .id(UUID.randomUUID().toString())
                .orderId(order.getId())
                .status(StatusEnum.PENDING.name())
                .createdDate(calendar.getTime())
                .createdBy(customer.getId())
                .build());
        calendar.add(Calendar.HOUR, 4);
        repository.orderStatusRepository.save(OrderStatusEntity.builder()
                .id(UUID.randomUUID().toString())
                .orderId(order.getId())
                .status(StatusEnum.ACCEPTED.name())
                .createdDate(calendar.getTime())
                .createdBy(staff.getId())
                .build());
    }

    private void initOrderDetails(OrderEntity order, List<ProductOptionEntity> productOptions) {
        ArrayList<OrderDetailEntity> ods = new ArrayList<OrderDetailEntity>();
        long total = 0L;
        for (ProductOptionEntity po : productOptions) {
            ods.add(OrderDetailEntity.builder()
                    .id(UidUtils.generateUid())
                    .orderId(order.getId())
                    .productOptionId(po.getId())
                    .price(po.getPrice())
                    .qty(2)
                    .build());
            total += po.getPrice() * 2L;
        }
        log.info("12. Order Detail: {}", ods);
        repository.orderDetailRepository.saveAll(ods);

        order.setTotal(total);
        log.info("13: update order: ", JsonUtils.toJson(order));
        repository.orderRepository.save(order);
    }

    private List<OrderEntity> initUsers() {
        BCryptPasswordEncoder passwordEncoder = BeanUtils.getBean(BCryptPasswordEncoder.class);
        UserEntity admin = UserEntity.builder()
                .id(UUID.fromString(WsConst.Seeders.ADMIN_ID).toString())
                .active(Boolean.TRUE)
                .email(WsConst.Seeders.ADMIN_EMAIL)
                .firstName(WsConst.Seeders.ADMIN_FIRST_NAME)
                .lastName(WsConst.Seeders.ADMIN_LAST_NAME)
                .password(passwordEncoder.encode(WsConst.Seeders.ADMIN_PASSWORD))
                .role(RoleEnum.ROLE_ADMIN)
//                .gender(Boolean.TRUE)
                .build();
        UserEntity staff = UserEntity.builder()
                .id(UUID.fromString(WsConst.Seeders.STAFF_ID).toString())
                .active(Boolean.TRUE)
                .email(WsConst.Seeders.STAFF_EMAIL)
                .firstName(WsConst.Seeders.STAFF_FIRST_NAME)
                .lastName(WsConst.Seeders.STAFF_LAST_NAME)
                .password(passwordEncoder.encode(WsConst.Seeders.STAFF_PASSWORD))
                .role(RoleEnum.ROLE_STAFF)
//                .gender(Boolean.TRUE)
                .build();
        UserEntity customer1 = UserEntity.builder()
                .id(UidUtils.generateUid())
                .active(Boolean.TRUE)
                .email(WsConst.Seeders.CUSTOMER_EMAIL1)
                .firstName(WsConst.Seeders.CUSTOMER_FIRST_NAME1)
                .lastName(WsConst.Seeders.CUSTOMER_LAST_NAME1)
                .password(passwordEncoder.encode(WsConst.Seeders.CUSTOMER_PASSWORD1))
                .role(RoleEnum.ROLE_CUSTOMER)
//                .gender(Boolean.TRUE)
                .phone(WsConst.Seeders.CUSTOMER_PHONE1)
                .build();
        UserEntity customer2 = UserEntity.builder()
                .id(UidUtils.generateUid())
                .active(Boolean.TRUE)
                .email(WsConst.Seeders.CUSTOMER_EMAIL2)
                .firstName(WsConst.Seeders.CUSTOMER_FIRST_NAME2)
                .lastName(WsConst.Seeders.CUSTOMER_LAST_NAME2)
                .password(passwordEncoder.encode(WsConst.Seeders.CUSTOMER_PASSWORD2))
                .role(RoleEnum.ROLE_CUSTOMER)
//                .gender(Boolean.TRUE)
                .phone(WsConst.Seeders.CUSTOMER_PHONE2)
                .build();


        List<UserEntity> users = Arrays.asList(admin, staff, customer1, customer2);

        log.info("9. save User list: {}", JsonUtils.toJson(users));
        repository.userRepository.saveAll(users);

        AddressEntity add1 = AddressEntity.builder()
                .id(UidUtils.generateUid())
                .provinceId(WsConst.Seeders.CUSTOMER_ADDRESS_PROVINCE_CODE1)
                .provinceName(WsConst.Seeders.CUSTOMER_ADDRESS_PROVINCE_NAME1)
                .districtId(WsConst.Seeders.CUSTOMER_ADDRESS_DISTRICT_CODE1)
                .districtName(WsConst.Seeders.CUSTOMER_ADDRESS_DISTRICT_NAME1)
//                .wardCode(WsConst.Seeders.CUSTOMER_ADDRESS_WARD_CODE1)
                .wardName(WsConst.Seeders.CUSTOMER_ADDRESS_WARD_NAME1)
                .combination(WsConst.Seeders.CUSTOMER_ADDRESS_COMBINATION1)
                .userId(customer1.getId())
                .active(Boolean.TRUE)
                .isDefault(Boolean.FALSE)
                .build();
        AddressEntity add2 = AddressEntity.builder()
                .id(UidUtils.generateUid())
                .provinceId(WsConst.Seeders.CUSTOMER_ADDRESS_PROVINCE_CODE2)
                .provinceName(WsConst.Seeders.CUSTOMER_ADDRESS_PROVINCE_NAME2)
                .districtId(WsConst.Seeders.CUSTOMER_ADDRESS_DISTRICT_CODE2)
                .districtName(WsConst.Seeders.CUSTOMER_ADDRESS_DISTRICT_NAME2)
//                .wardCode(WsConst.Seeders.CUSTOMER_ADDRESS_WARD_CODE2)
                .wardName(WsConst.Seeders.CUSTOMER_ADDRESS_WARD_NAME2)
                .combination(WsConst.Seeders.CUSTOMER_ADDRESS_COMBINATION2)
                .userId(customer2.getId())
                .active(Boolean.TRUE)
                .isDefault(Boolean.FALSE)
                .build();
        List<AddressEntity> address = Arrays.asList(add1, add2);
        log.info("10. save address listL {}", JsonUtils.toJson(address));
        repository.addressRepository.saveAll(address);

        OrderEntity order1 = OrderEntity.builder()
                .id(UUID.randomUUID().toString())
                .userId(customer1.getId())
                .addressId(add1.getId())
                .note("Giao hàng giờ hành chính")
                .payed(false)
                //.type(OrderTypeEnum.CASH.name())
                .payment(PaymentEnums.COD.name())
                .shipPrice(20000L)
                .code("DH260822KH01")
                .build();
        OrderEntity order2 = OrderEntity.builder()
                .id(UUID.randomUUID().toString())
                .userId(customer2.getId())
                .addressId(add2.getId())
                .note("Giao cuối tuần")
                .payed(false)
                //.type(OrderTypeEnum.CASH.name())
                .payment(PaymentEnums.COD.name())
                .shipPrice(20000L)
                .code("DH260822KH02")
                .build();

        List<OrderEntity> orders = Arrays.asList(order1, order2);
        log.info("11. save Order list: {}", JsonUtils.toJson(orders));
        repository.orderRepository.saveAll(orders);

        return orders;
    }

    private int getRandomIndex(int size) {
        return random.nextInt(size);
    }
}
