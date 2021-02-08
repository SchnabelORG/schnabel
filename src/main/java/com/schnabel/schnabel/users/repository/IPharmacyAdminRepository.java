package com.schnabel.schnabel.users.repository;

import com.schnabel.schnabel.users.model.PharmacyAdmin;

import org.springframework.data.repository.CrudRepository;

public interface IPharmacyAdminRepository extends CrudRepository<PharmacyAdmin, Long>
{
    PharmacyAdmin findByEmail(String email);
}
