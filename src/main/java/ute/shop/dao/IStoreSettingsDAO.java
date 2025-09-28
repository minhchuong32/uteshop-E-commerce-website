package ute.shop.dao;

import ute.shop.models.StoreSettings;

public interface IStoreSettingsDAO {
    StoreSettings getSettings();
    boolean updateSettings(StoreSettings settings);
}
