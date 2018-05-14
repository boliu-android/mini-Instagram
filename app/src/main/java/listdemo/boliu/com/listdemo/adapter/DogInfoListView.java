package listdemo.boliu.com.listdemo.adapter;

import java.util.List;

import listdemo.boliu.com.listdemo.BaseView;
import listdemo.boliu.com.listdemo.model.Dog.DogInfo;

/**
 * Created by bloiu on 5/13/2017.
 */

public interface DogInfoListView extends BaseView {
    /**
     * show loading view
     */
    void showLoading();

    /**
     * hide loading view when finish load or exception
     */
    void hideLoading();

    /**
     * show error message
     *
     * @param msg
     */
    void showError(String msg);

    /**
     * show list item
     *
     * @param list
     */
    void showResult(List<DogInfo> list);

    /**
     * update action bar title
     *
     * @param title
     */
    void showTitle(String title);
}
