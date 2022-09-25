package com.ws.masterserver.utils.base;

import com.ws.masterserver.repository.*;
import com.ws.masterserver.repository.custom.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WsRepository {
    public final AddressRepository addressRepository;
    public final BlogRepository blogRepository;
    public final TopicRepository topicRepository;

    public final CategoryRepository categoryRepository;
    public final ColorRepository colorRepository;
    public final FavouriteRepository favouriteRepository;
    public final MaterialRepository materialRepository;
    public final OrderRepository orderRepository;
    public final OrderDetailRepository orderDetailRepository;
    public final OrderStatusRepository orderStatusRepository;
    public final ProductRepository productRepository;
    public final ProductOptionRepository productOptionRepository;
    public final ResetTokenRepository resetTokenRepository;
    public final ReviewRepository reviewRepository;
    public final TypeRepository typeRepository;
    public final UserRepository userRepository;
    public final CartRepository cartRepository;
    public final NotificationRepository notificationRepository;
    public final UserNotificationRepository userNotificationRepository;
    public final SizeRepository sizeRepository;
    public final SuggestRepository suggestRepository;
    public final BodyHeightRepository bodyHeightRepository;
    public final BodyWeightRepository bodyWeightRepository;
    public final DiscountRepository discountRepository;
    public final DiscountCategoryRepository discountCategoryRepository;
    public final DiscountCustomerTypeRepository discountCustomerTypeRepository;
    public final DiscountProductRepository discountProductRepository;
    public final CustomerTypeRepository customerTypeRepository;
    public final CustomerGroupRepository customerGroupRepository;
    public final DiscountCustomerRepository discountCustomerRepository;
    public final TransactionHistoryRepository transactionHistoryRepository;

    /** custom */
    public final CategoryCustomRepository categoryCustomRepository;
    public final AdminOrderCustomRepository adminOrderCustomRepository;
    public final OrderDetailCustomRepository orderDetailCustomRepository;
    public final ProductCustomRepository productCustomRepository;
    public final ProductRevenueRepository productRevenueRepository;

    public final CustomerRevenueRepository customerRevenueRepository;
    public final AdminDiscountRevenueRepository adminDiscountRevenueRepository;
}
