package listdemo.boliu.com.listdemo;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import listdemo.boliu.com.listdemo.adapter.DogInfoPresenter;
import listdemo.boliu.com.listdemo.model.Dog.DogInfo;

import static junit.framework.Assert.assertEquals;

/**
 * Created by bloiu on 5/14/2018.
 */

@RunWith(AndroidJUnit4.class)
public class DogInfoPresenterTest {
    DogInfoPresenter dogInfoPresenter;

    @Before
    public void init() {
        dogInfoPresenter = new DogInfoPresenter(null);
        List<DogInfo> list = new ArrayList<>();
        list.add(new DogInfo("kevin", "bella"));
        list.add(new DogInfo("James", "Lola"));
        dogInfoPresenter.setList(list);
    }

    @Test
    public void testGetListAfterFilter() {
        List<DogInfo> list;
        list = dogInfoPresenter.getListAfterFilter(null);
        assertEquals(list.size(),2);

        list = dogInfoPresenter.getListAfterFilter("J");
        assertEquals(list.size(),1);
        assertEquals(list.get(0).ownerName,"James");
    }
}
