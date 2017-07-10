package pro.likada.service;

import org.primefaces.model.TreeNode;
import pro.likada.model.ProductGroup;

import java.util.List;

/**
 * Created by bumur on 14.02.2017.
 */
public interface ProductGroupService {

    ProductGroup findById(long id);

    void save(ProductGroup productGroup);

    void deleteById(Long id);

    List<ProductGroup> getProductGroupsByParentId(Long id);

    List<ProductGroup>  getAllProductGroups();

    TreeNode fillTreeNodeValues(List<ProductGroup> productGroups, ProductGroup productGroupAll, TreeNode root);

    List<TreeNode> getExpandedProductGroupTreeNodeToList(TreeNode treeNode);

    void expandedTreeNodeToList(TreeNode root, List<TreeNode> nodes, int level);

    Long getMinValueOfProductParentId();

    Long getMinValueOfProductId();

    TreeNode addNewGroupToTreeNode(TreeNode parentNode, ProductGroup newProductGroup);
}
