package ute.shop.dao;

import ute.shop.entity.StoreSettings;

public interface IStoreSettingsDao {
	StoreSettings getSettings();  
    boolean updateSettings(StoreSettings settings);
}
