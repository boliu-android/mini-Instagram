package listdemo.boliu.com.listdemo;

/**
 * Created by bloiu on 12/4/2017.
 */

public interface BasePresenter<V extends BaseView> {

    public void attachView(V view);

    public void detachView(V view);
}
