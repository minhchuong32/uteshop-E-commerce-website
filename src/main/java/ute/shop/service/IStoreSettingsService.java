package ute.shop.service;

import ute.shop.entity.StoreSettings;


public interface IStoreSettingsService {
	StoreSettings getSettings();
    boolean updateSettings(StoreSettings settings);
}
