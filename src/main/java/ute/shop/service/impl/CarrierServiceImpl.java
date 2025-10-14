package ute.shop.service.impl;

import java.util.List;

import ute.shop.dao.impl.CarrierDaoImpl;
import ute.shop.dao.ICarrierDao;
import ute.shop.entity.Carrier;
import ute.shop.service.ICarrierService;

public class CarrierServiceImpl implements ICarrierService {

	private final ICarrierDao carrierDao = new CarrierDaoImpl();

	@Override
	public List<Carrier> findAll() {
		return carrierDao.findAll();
	}

	@Override
	public void delete(int id) {
		carrierDao.delete(id);
	}

	@Override
	public Carrier findById(int id) {
		return carrierDao.findById(id);
	}

	@Override
	public void save(Carrier carrier) {
		carrierDao.save(carrier);

	}

	@Override
	public void update(Carrier carrier) {
		carrierDao.update(carrier);

	}

}
