package pro.likada.service.serviceImpl;

import org.primefaces.model.TreeNode;
import org.primefaces.model.chart.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.dao.ProductDAO;
import pro.likada.model.Product;
import pro.likada.model.ProductGroup;
import pro.likada.model.ProductPrice;
import pro.likada.service.ProductService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by bumur on 14.02.2017.
 */
@Named("productService")
@Transactional
public class ProductServiceImpl implements ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Inject
    private ProductDAO productDAO;
    private List<Long> groupsIds;

    @Override
    public Product findById(long id) {
        return productDAO.findById(id);
    }

    @Override
    public List<Product> getProductsByGroup(TreeNode selectedProductNode) {
        if(((ProductGroup)selectedProductNode.getData()).getId().equals(new Long(-1))) {
               return productDAO.getAllProducts();
           }
            groupsIds = new ArrayList<Long>();
            compareChilds(selectedProductNode, groupsIds);
            return productDAO.getProductsByGroupIds(groupsIds);
    }


    @Override
    public List<Product> getProductsByGroupId(Long groupId) {
        return productDAO.getProductsByGroupId(groupId);
    }

    @Override
    public void deleteById(Long id) {
        productDAO.deleteById(id);
    }

    @Override
    public void compareChilds(TreeNode node, List<Long> list) {
        if (node != null) {
            ProductGroup group = (ProductGroup) node.getData();
            if (group != null) {
                if (group.getId() != null) {
                    list.add(group.getId());
                    LOGGER.info(group.toString());
                    for (int i = 0; i < node.getChildCount(); i++) {
                        TreeNode node_child = node.getChildren().get(i);
                        compareChilds(node_child, list);
                    }
                }
            }
        }
    }

    @Override
    public LineChartModel drawProductLineChart(Product product) {
        LineChartModel model = new LineChartModel();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (product != null) {
            LOGGER.info("GENERATE CHART " + product.getId() + " " + product.getNameShort());
            model.setShowPointLabels(false);

            DateAxis axis = new DateAxis();
            axis.setTickAngle(-50);
            axis.setTickFormat("%d.%m.%y");

            ChartSeries prices = new ChartSeries();
            prices.setLabel(product.getNameShort());

            double min = 0;
            double max = 0;

            List<ProductPrice> prices_list = product.getProductPricesList();

            if (prices_list.size() > 0) {
                axis.setMax(dateFormat.format(prices_list.get(0).getTimeModified()));

                GregorianCalendar gc_max = new GregorianCalendar();
                gc_max.setTime(prices_list.get(0).getTimeModified());

                gc_max.add(GregorianCalendar.MONTH, -1);

                axis.setMin(dateFormat.format(gc_max.getTime()));
            }

            for (int i = 0; i < prices_list.size(); i++) {
                prices.set(dateFormat.format(prices_list.get(i).getTimeModified()), prices_list.get(i).getPrice());

                if (min == 0) {
                    min = prices_list.get(i).getPrice();
                } else {
                    if (prices_list.get(i).getPrice() < min) {
                        min = prices_list.get(i).getPrice();
                    }
                }

                if (max == 0) {
                    max = prices_list.get(i).getPrice();
                } else {
                    if (prices_list.get(i).getPrice() > max) {
                        max = prices_list.get(i).getPrice();
                    }
                }
            }
            model.getAxes().put(AxisType.X, axis);
            Axis yAxis = model.getAxis(AxisType.Y);
            yAxis.setMin(Math.round(min / 5000) * 5000 - 5000);
            yAxis.setMax(Math.round(max / 5000) * 5000 + 5000);

            model.addSeries(prices);
        }
        return model;
    }

    @Override
    public void save(Product product) {
        productDAO.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productDAO.getAllProducts();
    }
}
