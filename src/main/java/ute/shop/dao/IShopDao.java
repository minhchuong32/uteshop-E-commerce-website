package ute.shop.dao;

import ute.shop.entity.Shop;
import java.util.List;


public interface IShopDao {
    List<Shop> getAll();

    Shop getById(int id);

    void insert(Shop shop);

    void update(Shop shop);

    void delete(int id);
    
    Shop findByUserId(int userId);
    Shop getReferenceById(int id);
}
