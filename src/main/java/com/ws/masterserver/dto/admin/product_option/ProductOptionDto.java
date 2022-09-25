package com.ws.masterserver.dto.admin.product_option;

import com.ws.masterserver.utils.constants.enums.ColorEnum;
import com.ws.masterserver.utils.constants.enums.SizeEnum;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductOptionDto {
    private String id;
    private String productId;
    private SizeEnum size;
    private ColorEnum color;
    private String price;
    private MultipartFile image;
}
