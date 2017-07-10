package pro.likada.dao;

import pro.likada.model.MeasureUnit;

import java.util.List;

/**
 * Created by bumur on 13.02.2017.
 */
public interface MeasureUnitDAO {

    MeasureUnit findById(long id);

    List<MeasureUnit> getAllMeasureUnits();
}
