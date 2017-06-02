package zsj.com.oyk255.example.ouyiku.view;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * 设置listview高度工具类
 */
public class ViewUtil {
  public static void setListViewHeightBasedOnChildren(ListView listView) {
    ListAdapter listAdapter = listView.getAdapter();
    if (listAdapter == null) {
      return;
    }

    int totalHeight = 0;
    for (int i = 0; i < listAdapter.getCount(); i++) {
      View listItem = listAdapter.getView(i, null, listView);
      listItem.measure(0, 0);
      totalHeight += listItem.getMeasuredHeight();
    }

    ViewGroup.LayoutParams params = listView.getLayoutParams();
    params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
    listView.setLayoutParams(params);
  }

  /**
   * ��ȡListview�ĸ߶ȣ�Ȼ������ViewPager�ĸ߶�
   * @param listView
   * @return
   */
  public static int setListViewHeightBasedOnChildren1(ListView listView) {
    //��ȡListView��Ӧ��Adapter
    ListAdapter listAdapter = listView.getAdapter();
    if (listAdapter == null) {
      // pre-condition
      return 0;
    }

    int totalHeight = 0;
    for (int i = 0, len = listAdapter.getCount(); i < len; i++) { //listAdapter.getCount()�������������Ŀ
      View listItem = listAdapter.getView(i, null, listView);
      listItem.measure(0, 0); //��������View �Ŀ��
      totalHeight += listItem.getMeasuredHeight(); //ͳ������������ܸ߶�
    }

    ViewGroup.LayoutParams params = listView.getLayoutParams();
    params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
    //listView.getDividerHeight()��ȡ�����ָ���ռ�õĸ߶�
    //params.height���õ�����ListView������ʾ��Ҫ�ĸ߶�
    listView.setLayoutParams(params);
    return params.height;
  }
}