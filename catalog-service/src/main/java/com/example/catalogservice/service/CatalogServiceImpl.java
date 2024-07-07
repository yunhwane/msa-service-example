package com.example.catalogservice.service;


import com.example.catalogservice.entity.Catalog;
import com.example.catalogservice.repository.CatalogRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CatalogServiceImpl implements CatalogService {

    private final CatalogRepository catalogRepository;

    @Override
    public Iterable<Catalog> getAllCatalogs() {
        return catalogRepository.findAll();
    }
}
