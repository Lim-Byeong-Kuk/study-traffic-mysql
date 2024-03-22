package com.example.fastcampusmysql.util;

import org.springframework.data.domain.Sort;

import java.util.List;

public class PageHelper {

    /**
     *  order by 뒤에 들어갈 sql 쿼리를 만들어주는 역할
     */
    public static String orderBy(Sort sort) {
        if (sort.isEmpty()) {
            return "id DESC";
        }

        List<Sort.Order> orders = sort.toList();
        List<String> orderBys = orders.stream()
                .map(order -> order.getProperty() + " " + order.getDirection())
                .toList();

        return String.join(", ", orderBys);
    }
}
