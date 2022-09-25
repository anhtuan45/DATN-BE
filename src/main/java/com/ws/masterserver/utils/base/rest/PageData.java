package com.ws.masterserver.utils.base.rest;

import com.ws.masterserver.utils.constants.WsCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;
import java.util.*;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("unchecked")
public class PageData<T> {
    private List<T> data;
    private Integer page;
    private Integer pageSize;
    private Integer totalPages;
    private Long totalElements;
    private Integer statusCode;
    private String message;
    private Date timeStamp;

    public PageData(List<T> data, Integer page, Integer pageSize, Long totalElements, Integer statusCode, String message) {
        this.data = data;
        this.page = page;
        this.pageSize = pageSize;
        if (!Objects.equals(0L, totalElements) && Objects.equals(0, totalElements % pageSize)) {
            this.totalPages = Math.toIntExact(totalElements / pageSize);
        } else {
            this.totalPages = 0;
        }
        this.totalElements = totalElements;
        this.statusCode = statusCode;
        this.message = message;
        timeStamp = new Date();
    }

    public PageData(List<T> data, Integer page, Integer pageSize, Long totalElements, WsCode wsCode) {
        this.data = data;
        this.page = page;
        this.pageSize = pageSize;
        if (totalElements % pageSize == 0) {
            this.totalPages = Math.toIntExact(totalElements / this.pageSize);
        } else {
            this.totalPages = Math.toIntExact(totalElements / this.pageSize) + 1;
        }
        this.totalElements = totalElements;
        this.statusCode = Integer.parseInt(wsCode.getCode());
        this.message = wsCode.getMessage();
        timeStamp = new Date();
    }

    public PageData(Boolean isEmpty) {
        if (Boolean.TRUE.equals(isEmpty)) {
            this.data = Collections.emptyList();
            this.page = 0;
            this.pageSize = 0;
            this.totalPages = 0;
            this.totalElements = 0L;
            this.statusCode = Integer.parseInt(WsCode.INTERNAL_SERVER.getCode());
            this.message = HttpStatus.NO_CONTENT.getReasonPhrase();
            timeStamp = new Date();
        }
    }

    public static PageData<Object> setResult(List<Object> data, Integer page, Integer pageSize, Long totalElements, WsCode wsCode) {
        PageData pageData = new PageData<>();
        pageData.setData(data)
                .setPage(page)
                .setPageSize(pageSize)
                .setTotalElements(totalElements)
                .setTotalPages(getTotalPages(totalElements, pageSize))
                .setStatusCode(Integer.parseInt(wsCode.getCode()))
                .setMessage(wsCode.getMessage())
                .setTimeStamp(new Date());
        return pageData;
    }

    private static Integer getTotalPages(Long totalElements, Integer pageSize) {
        if (totalElements % pageSize == 0) {
            return Math.toIntExact(totalElements / pageSize);
        } else {
            return Math.toIntExact(totalElements / pageSize) + 1;
        }
    }

    public static PageData<?> setEmpty(PageReq pageReq) {
        PageData pageData = new PageData<>();
        pageData.setData(new ArrayList());
        pageData.setPage(pageReq.getPage());
        pageData.setPageSize(pageReq.getPageSize());
        pageData.setTotalPages(0);
        pageData.setTotalElements(0L);
        pageData.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        pageData.setMessage(HttpStatus.NO_CONTENT.getReasonPhrase());
        pageData.setTimeStamp(new Date());
        return pageData;
    }

    public static <T> PageData setResult(List<T> data, Integer page, Integer pageSize, Long totalElements) {
        return new PageData(data, page, pageSize, totalElements, WsCode.OK);
    }
}
