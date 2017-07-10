package pro.likada.service.serviceImpl;

import pro.likada.dao.MeasureUnitDAO;
import pro.likada.model.MeasureUnit;
import pro.likada.service.MeasureUnitService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

/**
 * Created by bumur on 13.02.2017.
 */
@Named("measureUnitService")
@Transactional
public class MeasureUnitServiceImpl implements MeasureUnitService, Serializable {

    @Inject
    private MeasureUnitDAO measureUnitDAO;

    @Override
    public MeasureUnit findById(long id) {
        return measureUnitDAO.findById(id);
    }

    @Override
    public List<MeasureUnit> getAllMeasureUnits() {
        return measureUnitDAO.getAllMeasureUnits();
    }
}
