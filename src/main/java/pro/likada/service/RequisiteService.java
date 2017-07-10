package pro.likada.service;

import pro.likada.model.Requisite;

import java.util.List;

/**
 * Created by bumur on 28.12.2016.
 */
public interface RequisiteService {

    Requisite findById(long id);
    List<Requisite> findByContractorId(long id);
}
