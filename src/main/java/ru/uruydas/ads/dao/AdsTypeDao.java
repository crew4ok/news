package ru.uruydas.ads.dao;

import ru.uruydas.ads.model.AdsCategory;
import ru.uruydas.ads.model.AdsType;

import java.util.List;

public interface AdsTypeDao {

    AdsType getById(Long adsTypeId);

    List<AdsType> getByCategory(AdsCategory adsCategory);

    AdsType create(AdsType adsType);

}
