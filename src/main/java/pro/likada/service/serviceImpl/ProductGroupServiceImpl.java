package pro.likada.service.serviceImpl;

import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.likada.dao.ProductGroupDAO;
import pro.likada.model.ProductGroup;
import pro.likada.service.ProductGroupService;
import pro.likada.util.TreeNodeUtil;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bumur on 14.02.2017.
 */
@Named("productGroupService")
@Transactional
public class ProductGroupServiceImpl implements ProductGroupService, Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductGroupServiceImpl.class);

    @Inject
    private ProductGroupDAO productGroupDAO;

    @Override
    public ProductGroup findById(long id) {
        return productGroupDAO.findById(id);
    }

    @Override
    public void save(ProductGroup productGroup) {
        productGroupDAO.save(productGroup);
    }

    @Override
    public void deleteById(Long id) {
       productGroupDAO.deleteById(id);
    }

    @Override
    public List<ProductGroup> getProductGroupsByParentId(Long id) {
        return productGroupDAO.getProductGroupsByParentId(id);
    }

    @Override
    public List<ProductGroup> getAllProductGroups() {
        return productGroupDAO.getAllProductGroups();
    }

    @Override
    public TreeNode fillTreeNodeValues(List<ProductGroup> productGroups, ProductGroup productGroupAll,TreeNode root) {
        List<TreeNode> nodes = new ArrayList<TreeNode>();

        ProductGroup gg_root = new ProductGroup();
        gg_root.setTitle("Все группы");
        gg_root.setId((long) 0);

        root = new TreeNodeUtil(gg_root, null);
        TreeNode node_all = new TreeNodeUtil(productGroupAll, root);

        nodes.add(node_all);
        TreeNode slut_node = null;
        for (int i = 0; i < productGroups.size(); i++) {
            if (productGroups.get(i).getParentId() != null) {
                if (productGroups.get(i).getParentId().equals(new Long(0))) {
                    slut_node = new TreeNodeUtil(productGroups.get(i), root);
                    slut_node.setExpanded(true);
                    nodes.add(slut_node);
                } else {
                    for (int n = 0; n < nodes.size(); n++) {
                        if (nodes.get(n).getData() != null) {
                            if (productGroups.get(i).getParentId().equals(((ProductGroup) nodes.get(n).getData()).getId())) {
                                slut_node = new TreeNodeUtil(productGroups.get(i), nodes.get(n));
                                nodes.add(slut_node);
                            }
                        }
                    }
                }
            }
        }
        slut_node=null;
        return root;
    }

    @Override
    public List<TreeNode> getExpandedProductGroupTreeNodeToList(TreeNode treeNode) {
        if (treeNode != null) {
            int level = 0;
            List<TreeNode> nodes = new ArrayList<TreeNode>();
            expandedTreeNodeToList(treeNode, nodes, level);
            return nodes;
        }

        return null;
    }

    @Override
    public void expandedTreeNodeToList(TreeNode root, List<TreeNode> nodes, int level) {
        if (root != null && nodes != null) {
            int l = level + 1;
            List<TreeNode> childs = root.getChildren();
            for (TreeNode treeNode:childs) {
                TreeNodeUtil treeNodeUtil = (TreeNodeUtil) treeNode;
                treeNodeUtil.setLevel(l);
                nodes.add(treeNodeUtil);
                expandedTreeNodeToList(treeNodeUtil, nodes, l);
            }
        }
    }

    @Override
    public Long getMinValueOfProductParentId() {
        return productGroupDAO.getMinValueOfProductParentId();
    }

    @Override
    public Long getMinValueOfProductId() {
        return productGroupDAO.getMinValueOfProductId();
    }

    @Override
    public TreeNode addNewGroupToTreeNode(TreeNode parentNode, ProductGroup newProductGroup) {
        TreeNode newTreeNode=new TreeNodeUtil(newProductGroup,parentNode);
        return newTreeNode;
    }


}
