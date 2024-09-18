package com.ecommerce.product.util;

import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MapperUtil {

    private ModelMapper modelMapper;

    @PostConstruct
    public void init() {
        this.modelMapper = new ModelMapper();
    }

    public <R> R map(Object source, Class<R> resultClass) {
        return this.modelMapper.map(source, resultClass);
    }
}
