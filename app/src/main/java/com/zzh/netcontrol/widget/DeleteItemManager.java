package com.zzh.netcontrol.widget;

/**
 * Time 2020/2/25
 * Author Zzh
 * Description  DeleteItemLayout的开关管理
 */
public class DeleteItemManager {

    private DeleteItemLayout mDeleteItem;
    private static DeleteItemManager manager;

    public static DeleteItemManager getInstance() {
        if (manager == null) {
            manager = new DeleteItemManager();
        }
        return manager;
    }

    public void setDeleteItem(DeleteItemLayout deleteItem) {
        mDeleteItem = deleteItem;
    }

    public boolean otherItemOpened(DeleteItemLayout deleteItem) {
        return mDeleteItem != null && deleteItem != mDeleteItem;
    }

    public boolean curItemOpened(DeleteItemLayout deleteItem) {
        return mDeleteItem != null && deleteItem == mDeleteItem;
    }

    public void closeItem() {
        if (mDeleteItem != null) {
            mDeleteItem.close();
        }
    }

}
