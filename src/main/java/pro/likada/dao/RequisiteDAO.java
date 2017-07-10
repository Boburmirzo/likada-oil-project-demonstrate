package pro.likada.dao;

import pro.likada.model.Requisite;

import java.util.List;

/**
 * Created by bumur on 27.12.2016.
 */
public interface RequisiteDAO {
    Requisite findById(long id);
    List<Requisite> findByContractorId(long id);
}
