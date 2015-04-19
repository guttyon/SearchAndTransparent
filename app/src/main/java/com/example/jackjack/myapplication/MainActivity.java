package com.example.jackjack.myapplication;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
//import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SearchView;


public class MainActivity
        //extends ActionBarActivity
        extends Activity
{
    MyFragment current = null;
    String FRAGMENT_TAG = "I_AM_FRAGMENT_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setContentView(R.layout.main);
        ActionBar ab = getActionBar();
        //if(getFragmentManager().findFragmentById(R.id.list) == null)
        if(false)
        {
            // ListViewを表示するFragment
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            current = new MyFragment();
            ft.replace(R.id.list, current, FRAGMENT_TAG);
            //ft.add(R.id.list, current, FRAGMENT_TAG);
            //ft.addToBackStack(null);//add the transaction to the back stack so the user can navigate back
// Commit the transaction
            ft.remove(current);
            ft.commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        // action barの設定
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_menu, menu);

        /*
        // メニューの要素を追加して取得
        MenuItem actionItem = menu.add("Action Button Help Icon");
        // アイコンを設定
        actionItem.setIcon(android.R.drawable.ic_menu_help);

        // SHOW_AS_ACTION_ALWAYS:常に表示
        actionItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        */

        // SearchViewの設定
        MenuItem mi =  menu.findItem(R.id.search_view);
        SearchView searchView = (SearchView) mi.getActionView();
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                // onClickで表示したlistFragmentを消したい。
                // ListViewを表示するFragment
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.remove(current);
                ft.commit();
                return false;
            }
        });
        searchView.setOnSearchClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(getFragmentManager().findFragmentById(R.id.list) == null)
                {
                    // ListViewを表示するFragment
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    current = new MyFragment();
                    ft.replace(R.id.list, current, FRAGMENT_TAG);
                    //ft.add(R.id.list, current, FRAGMENT_TAG);
                    //ft.addToBackStack(null);//add the transaction to the back stack so the user can navigate back
// Commit the transaction
                    ft.commit();
                }
                return;
            }

        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
/**
 * SearchViewに入力するたびに入力される
 */
            @Override
            public boolean onQueryTextChange(String newText) {
                ActionBar ab = getActionBar();

                // FilterModeの時のみListViewにFileterをかける
               // if (modeArray[MODE].equals("FilterMode"))
                {
                    /*
                    if (TextUtils.isEmpty(newText)) {
                        current.clearFilter();
                    } else
                    */
                    {
                        current.setFilter(newText.toString());
                    }
                }
                return true;
            }

/**
 * SearchViewのSubmitを押下したときに呼び出される
 */
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }
        }
        );

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    static public class MyFragment extends ListFragment {
        private final String[] rows = { "abc", "aab", "aac", "aaa", "abb",
                "acc", "cab", "ccc", "bbb", "saru", "desu", "yo" };

        public void MyFragment() {
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            // ListViewにFilterをかけれるようにする
            getListView().setTextFilterEnabled(true);

            // ListViewに表示するItemの設定
            setListAdapter(new ArrayAdapter(getActivity(),
                    android.R.layout.simple_list_item_1, rows));
        }

        /**
         * ListViewにFilterをかける
         * @param s
         */
        public void setFilter(String s){
            if(s.length() == 0) {
                //setListAdapter(new ArrayAdapter(getActivity(),
                //        android.R.layout.simple_list_item_1, rows));
                getListView().clearTextFilter();
            }else {
                getListView().setFilterText(s);
                //getListView().setTextFilterEnabled(true);
            }
        }

        /**
         * ListViewのFilterをClearする
         */
        public void clearFilter(){
            getListView().clearTextFilter();
        }
    }

    static public class HelloFragment extends Fragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_main, null);
        }

    }
    static public class Fragment1 extends Fragment {
       public void Fragment1(){}
        @Override public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment1, container, false);
        }
    }
   static public class Fragment2 extends Fragment {
       public void Fragment1(){}
       @Override public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
           return inflater.inflate( R.layout.fragment2, container, false);
       }
   }

}
