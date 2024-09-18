package com.ecommerce.user.util;

import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public <S, D> List<D> map(List<S> source, Class<D> destination) {
        return source.stream().map(e -> map(e, destination)).toList();
    }
}
