package ute.shop.dao;

import java.util.List;

import ute.shop.entity.Carrier;

public interface ICarrierDao {

	List<Carrier> findAll();

	void delete(int id);

	Carrier findById(int id);

	void save(Carrier carrier);

	void update(Carrier carrier);

}
