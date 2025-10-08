package ute.shop.service.impl;

import ute.shop.dao.IStoreSettingsDao;
import ute.shop.dao.impl.StoreSettingsDAOImpl;
import ute.shop.entity.StoreSettings;
import ute.shop.service.IStoreSettingsService;


public class StoreSettingsServiceImpl implements IStoreSettingsService {
    private final IStoreSettingsDao dao = new StoreSettingsDAOImpl();

    @Override
    public StoreSettings getSettings() {
        return dao.getSettings();
    }

    @Override
    public boolean updateSettings(StoreSettings settings) {
        return dao.updateSettings(settings);
    }
}
