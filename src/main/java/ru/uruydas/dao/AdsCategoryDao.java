package ru.uruydas.dao;

import ru.uruydas.model.ads.AdsCategory;

import java.util.List;

public interface AdsCategoryDao {

    AdsCategory getById(Long id);

    List<AdsCategory> getAllCategories();

    List<AdsCategory> getAllSubCategories(AdsCategory category);

    AdsCategory create(AdsCategory category);
}
