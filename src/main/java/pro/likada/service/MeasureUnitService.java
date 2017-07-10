package pro.likada.service;

import pro.likada.model.MeasureUnit;

import java.util.List;

/**
 * Created by bumur on 13.02.2017.
 */
public interface MeasureUnitService {

    MeasureUnit findById(long id);

    List<MeasureUnit> getAllMeasureUnits();
}
