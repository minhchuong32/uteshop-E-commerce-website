package ute.shop.service;

import ute.shop.models.StoreSettings;

public interface IStoreSettingsService {
    StoreSettings getSettings();
    boolean updateSettings(StoreSettings settings);
}
