package ute.shop.service;

import java.util.List;

import ute.shop.entity.Carrier;

public interface ICarrierService {
	List<Carrier> findAll();

	void delete(int id);

	Carrier findById(int id);

	void save(Carrier carrier);

	void update(Carrier carrier);
}
