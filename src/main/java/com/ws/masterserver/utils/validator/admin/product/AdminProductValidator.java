package com.ws.masterserver.utils.validator.admin.product;

import com.ws.masterserver.dto.admin.product.create_update.OptionDto;
import com.ws.masterserver.dto.admin.product.create_update.ProductDto;
import com.ws.masterserver.entity.CategoryEntity;
import com.ws.masterserver.entity.ProductEntity;
import com.ws.masterserver.utils.base.WsException;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.common.StringUtils;
import com.ws.masterserver.utils.common.ValidatorUtils;
import com.ws.masterserver.utils.constants.WsCode;

import java.util.List;

/**
 * @author myname
 */
public class AdminProductValidator {

    private static final String NAME = "Tên";
    private static final String DES = "Mô tả";
    private static final String PRICE = "Giá";
    private static final String QTY = "Số lượng";
    private static final String IMAGE = "Ảnh";
    private static final String COLOR = "Màu";
    private static final String SIZE = "Size";
    private static final String MATERIAL = "Chất liệu";
    private static final String CATEGORY = "Danh mục";

    private AdminProductValidator() {
        super();
    }

    public static void validCreate(ProductDto dto, WsRepository repository) {
        ValidatorUtils.validNullOrEmpty(CATEGORY, dto.getCategoryId());
        ValidatorUtils.validNullOrEmpty(NAME, dto.getName());
        ValidatorUtils.validOnlyCharacterAndNumber(NAME, dto.getName());
        ValidatorUtils.validLength(NAME, dto.getName(), 4, 100);
        ValidatorUtils.validNullOrEmpty(MATERIAL, dto.getMaterialId());
        ValidatorUtils.validNullOrEmpty(DES, dto.getDes());
//        ValidatorUtils.validOnlyCharacterAndNumber(DES, dto.getDes());
        ValidatorUtils.validLength(DES, dto.getDes(), 4, false);
        validOptionDto(dto.getOptions(), repository);
        validExist(dto, repository);
    }

    private static void validExist(ProductDto dto, WsRepository repository) {
        ProductEntity product = repository.productRepository.findByName(dto.getName().trim());

        CategoryEntity category = repository.categoryRepository.findByIdAndActive(dto.getCategoryId(), true);
        if (category == null) {
            throw new WsException(WsCode.CATEGORY_NOT_FOUND);
        } else if (product != null && category.getId().equals(product.getCategoryId())) {
            throw new WsException(WsCode.PRODUCT_NAME_EXISTS);
        }

        if (!repository.materialRepository.existsByIdAndActive(dto.getMaterialId(), true)) {
            throw new WsException(WsCode.MATERIAL_NOT_FOUND);
        }
    }

    private static void validOptionDto(List<OptionDto> options, WsRepository repository) {
        if (options.isEmpty()) {
            throw new WsException(WsCode.NOT_EMPTY_OPTIONS);
        }
        for (OptionDto option : options) {
            ValidatorUtils.validNullOrEmpty(PRICE, option.getPrice());
            ValidatorUtils.validOnlyNumber(PRICE, option.getPrice());
            ValidatorUtils.validPrice(PRICE, option.getPrice());
            ValidatorUtils.validLongValueMustBeMore(PRICE, option.getPrice(), 0L);
            ValidatorUtils.validNullOrEmpty(QTY, option.getQty());
            ValidatorUtils.validOnlyNumber(QTY, option.getQty());
            ValidatorUtils.validLongValueMustBeMore(QTY, option.getQty(), 0L);
            ValidatorUtils.validNullOrEmpty(IMAGE, option.getImage());
            ValidatorUtils.validNullOrEmpty(COLOR, option.getColorId());
            ValidatorUtils.validNullOrEmpty(SIZE, option.getSizeId());
            validExist(option, repository);
        }
    }

    private static void validExist(OptionDto option, WsRepository repository) {
        if (!repository.sizeRepository.existsById(option.getSizeId())) {
            throw new WsException(WsCode.INTERNAL_SERVER, "Size không hợp lệ");
        }
        if (!repository.colorRepository.existsByIdAndActive(option.getColorId(), true)) {
            throw new WsException(WsCode.INTERNAL_SERVER, "Màu không hợp lệ");
        }
    }

    public static void validUpdate(ProductDto payload, WsRepository repository) {
        ValidatorUtils.validNullOrEmpty(NAME, payload.getName());
        ValidatorUtils.validOnlyCharacterAndNumber(NAME, payload.getName());
        if (repository.productRepository.checkDuplicateName4Update(payload.getName().trim(), payload.getId())) {
            throw new WsException(WsCode.PRODUCT_NAME_EXISTS);
        }
        ValidatorUtils.validLength(NAME, payload.getName(), 4, 100);
        ValidatorUtils.validNullOrEmpty(DES, payload.getDes());
//        ValidatorUtils.validOnlyCharacterAndNumber(DES, payload.getDes());
        ValidatorUtils.validLength(DES, payload.getDes(), 4, false);
        ValidatorUtils.validNullOrEmpty(MATERIAL, payload.getMaterialId());
        if (!repository.materialRepository.existsById(payload.getMaterialId())) {
            throw new WsException(WsCode.MATERIAL_NOT_FOUND);
        }
        if (!StringUtils.isNullOrEmpty(payload.getCategoryId()) && !repository.categoryRepository.existsById(payload.getCategoryId())) {
            throw new WsException(WsCode.CATEGORY_NOT_FOUND);
        }
        validOptionDto(payload.getOptions(), repository);
    }
}
