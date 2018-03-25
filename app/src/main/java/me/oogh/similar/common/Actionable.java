package me.oogh.similar.common;

/**
 * @author oogh <oogh216@163.com>
 * @date 2018-03-24
 * @description 规定: 所有使用ActionMode的View都要实现该接口
 */

public interface Actionable {
    /**
     * 销毁ActionMode
     */
    void destroyActionMode();
}
