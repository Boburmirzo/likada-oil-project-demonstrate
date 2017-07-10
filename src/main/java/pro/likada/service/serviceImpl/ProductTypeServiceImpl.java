package pro.likada.service.serviceImpl;

import pro.likada.dao.ProductTypeDAO;
import pro.likada.model.ProductType;
import pro.likada.service.ProductTypeService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

/**
 * Created by bumur on 19.02.2017.
 */
@Named("productTypeService")
@Transactional
public class ProductTypeServiceImpl implements ProductTypeService,Serializable{

    @Inject
    private ProductTypeDAO productTypeDAO;

    @Override
    public ProductType findById(long id) {
        return productTypeDAO.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        productTypeDAO.deleteById(id);
    }

    @Override
    public List<ProductType> getAllProductTypes() {
        return productTypeDAO.getAllProductTypes();
    }

    @Override
    public List<ProductType> getProductTypesGroupByNameWork() {
        return productTypeDAO.getProductTypesGroupByNameWork();
    }

    @Override
    public List<ProductType> findAllProductTypesByNameWork(String searchString) {
        return productTypeDAO.findAllProductTypesByNameWork(searchString);
    }

    @Override
    public void save(ProductType productType) {
        productTypeDAO.save(productType);
    }

    @Override
    public List<ProductType> getProductTypesByIdInDescendingOrder() {
        return productTypeDAO.getProductTypesByIdInDescendingOrder();
    }
}
