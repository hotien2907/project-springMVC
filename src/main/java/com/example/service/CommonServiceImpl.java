package com.example.service;

import com.example.dto.response.RespProductDto;
import com.example.modle.dao.CommonDao;
import com.example.modle.entity.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class CommonServiceImpl implements CommonService{
 private final static ModelMapper modelMapper = new ModelMapper();

    @Autowired
    CommonDao commonDao;
    @Override
    public List<RespProductDto> productByCategory(Integer categoryId) {
        List <Product> products =commonDao.productByCategory(categoryId);
      return     products.stream()
                .map(pr -> modelMapper.map(pr, RespProductDto.class))
                .collect(Collectors.toList());


    }

    @Override
    public List<RespProductDto> searchProduct(String keyName) {
        List <Product> products =commonDao.searchProduct(keyName);
        return     products.stream()
                .map(pr -> modelMapper.map(pr, RespProductDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<RespProductDto> sortProduct(int value) {

        List <Product> products =commonDao.sortProduct(value);
        return     products.stream()
                .map(pr -> modelMapper.map(pr, RespProductDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<RespProductDto> productRelated(int idCategory, int idProduct) {
        List <Product> products =commonDao.productRelated(idCategory,idProduct);
        return
                products.stream()
                .map(pr -> modelMapper.map(pr, RespProductDto.class))
                .collect(Collectors.toList());

    }
    @Override
    public List<RespProductDto> findAllProductPage(int startNumber, int size) {
        List <Product> products =commonDao.findAllProductPage(startNumber,size);
        return
                products.stream()
                        .map(pr -> modelMapper.map(pr, RespProductDto.class))
                        .collect(Collectors.toList());
    }

}
