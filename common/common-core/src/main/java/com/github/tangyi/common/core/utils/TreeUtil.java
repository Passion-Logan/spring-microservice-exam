package com.github.tangyi.common.core.utils;

import java.util.ArrayList;
import java.util.List;

import com.github.tangyi.common.core.persistence.TreeEntity;

/**
 * 应用模块名称
 * <p>
 * 代码描述
 * <p>
 *
 * @author WQL
 * @since 2020年1月17日 0017 11:38
 */
public class TreeUtil {

    /**
     * 两层循环实现建树
     *
     * @param treeEntities
     *            传入的树实体列表
     * @return List
     */
    @SuppressWarnings(value = "unchecked")
    public static <T> List<T> buildTree(List<? extends TreeEntity<T>> treeEntities, Object root) {
        List<TreeEntity<T>> treeEntityArrayList = new ArrayList<>();
        treeEntities.forEach(treeEntity -> {
            if (treeEntity.getParentId().equals(root)) {
                treeEntityArrayList.add(treeEntity);
            }
            treeEntities.forEach(childTreeEntity -> {
                if (childTreeEntity.getParentId().equals(treeEntity.getId())) {
                    if (treeEntity.getChildren() == null) {
                        treeEntity.setChildren(new ArrayList<>());
                    }
                    treeEntity.add(childTreeEntity);
                }
            });
        });
        return (List<T>)treeEntityArrayList;
    }
}
