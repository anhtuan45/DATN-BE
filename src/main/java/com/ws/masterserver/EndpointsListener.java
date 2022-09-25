//package com.ws.masterserver;
//
//import com.ws.masterserver.utils.common.JsonUtils;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.stereotype.Component;
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
//import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//@Component
//@Slf4j
//public class EndpointsListener implements ApplicationListener<ContextRefreshedEvent> {
//    @Override
//    public void onApplicationEvent(ContextRefreshedEvent event) {
//
//        ApplicationContext applicationContext = event.getApplicationContext();
//        RequestMappingHandlerMapping requestMappingHandlerMapping = applicationContext
//                .getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class);
//        Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping
//                .getHandlerMethods();
//        List<Model> models = new ArrayList<>();
//        map.forEach((key, value) -> {
//            Model model = new Model(key.getPatternValues(), value.getBean().toString());
//            models.add(model);
//        });
//
//        models.sort(Comparator.comparing(Model::getName));
//        Set<String> names = models.stream().map(Model::getName).collect(Collectors.toSet());
//        names.forEach(System.out::println);
//    }
//
//    @Data
//    @AllArgsConstructor
//    @NoArgsConstructor
//    public class Model {
//        private Set<String> apis;
//        private String name;
//    }
//}
