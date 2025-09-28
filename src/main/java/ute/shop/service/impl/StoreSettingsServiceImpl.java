package ute.shop.service.impl;

import ute.shop.dao.IStoreSettingsDAO;
import ute.shop.dao.impl.StoreSettingsDAOImpl;
import ute.shop.models.StoreSettings;
import ute.shop.service.IStoreSettingsService;

public class StoreSettingsServiceImpl implements IStoreSettingsService {
    private IStoreSettingsDAO dao = new StoreSettingsDAOImpl();

    @Override
    public StoreSettings getSettings() {
        return dao.getSettings();
    }

    @Override
    public boolean updateSettings(StoreSettings settings) {
        return dao.updateSettings(settings);
    }
}
