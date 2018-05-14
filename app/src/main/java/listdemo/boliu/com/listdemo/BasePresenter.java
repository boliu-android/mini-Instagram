package listdemo.boliu.com.listdemo;

/**
 * Created by bloiu on 5/13/2017.
 */

public interface BasePresenter<V extends BaseView> {

    public void attachView(V view);

    public void detachView(V view);
}
