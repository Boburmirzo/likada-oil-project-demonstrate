package pro.likada.util;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import java.io.Serializable;

/**
 * Created by bumur on 15.02.2017.
 */
public class TreeNodeUtil extends DefaultTreeNode implements Serializable {

    private int	level;
    private String key;

    public TreeNodeUtil(Object data) {
        super(data);
    }

    public TreeNodeUtil(Object data, TreeNode parent) {
        super(data, parent);
    }

    public TreeNodeUtil(String type, Object data, TreeNode parent) {
        super(type, data, parent);
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
