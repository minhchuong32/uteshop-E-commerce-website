package ute.shop.service;

import ute.shop.entity.Shop;
import java.util.List;


public interface IShopService {
    List<Shop> getAll();

    Shop getById(int id);

    void insert(Shop shop);

    void update(Shop shop);

    void delete(int id);
}
