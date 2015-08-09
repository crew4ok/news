package ru.uruydas.dao;

import ru.uruydas.model.ads.AdsCategory;

import java.util.List;

public interface AdsCategoryDao {

    AdsCategory getById(Long id);

    List<AdsCategory> getAll();

    AdsCategory create(AdsCategory category);
}
