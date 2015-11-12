package com.stealth.lnk.stealthwidget;

import com.stealth.lnk.stealthwidget.AppInfo.AppFilter;
import com.stealth.lnk.stealthwidget.DB.DBHelper;
import com.stealth.lnk.stealthwidget.DB.LayoutDAO;
import com.stealth.lnk.stealthwidget.DB.LayoutDTO;
import com.stealth.lnk.stealthwidget.DB.SettingDAO;
import com.stealth.lnk.stealthwidget.DB.SettingDTO;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AppInfoActivity extends AppCompatActivity {

    private static final String TAG = AppInfoActivity.class.getSimpleName();

    private final int MENU_DOWNLOAD = 0;
    private final int MENU_ALL = 1;
    private int MENU_MODE = MENU_DOWNLOAD;

    private PackageManager pm;

    private View mLoadingContainer;
    private ListView mListView = null;
    private IAAdapter mAdapter = null;

    private DBHelper dbHelper = new DBHelper(this);
    Intent intent;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);
        intent = getIntent();

        mLoadingContainer = findViewById(R.id.loading_container);
        mListView = (ListView) findViewById(R.id.listView1);

        mAdapter = new IAAdapter(this);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //목록을 터치했을 때 벌어지는 일들
            @Override
            public void onItemClick(AdapterView<?> av, View view, int position,
                                    long id) {
                // TODO Auto-generated method stub
                String app_name = ((TextView) view.findViewById(R.id.app_name)).getText().toString();
                String package_name = ((TextView) view.findViewById(R.id.app_package)).getText().toString();

                //DB연결
                LayoutDAO layoutDAO = new LayoutDAO(dbHelper);
                LayoutDTO layoutDTO = new LayoutDTO();
                SettingDAO settingDAO = new SettingDAO(dbHelper);
                SettingDTO settingDTO = new SettingDTO();

                layoutDTO.setName(intent.getStringExtra("layoutName"));
                layoutDTO = layoutDAO.selectLayout(layoutDTO);

                settingDTO.setApp_name(app_name);
                settingDTO.setApp_package(package_name);
                settingDTO.setLayout_seq(layoutDTO.getSeq());

                settingDAO.insertSetting(settingDTO);

                Toast.makeText(AppInfoActivity.this, "설정이 저장되었습니다.", Toast.LENGTH_SHORT).show();

                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // �۾� ����
        startTask();
    }

    /**
     * �۾� ����
     */
    private void startTask() {
        new AppTask().execute();
    }

    /**
     * �ε��� ǥ�� ����
     *
     * @param isView
     *            ǥ�� ����
     */
    private void setLoadingView(boolean isView) {
        if (isView) {
            // ȭ�� �ε��� ǥ��
            mLoadingContainer.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);
        } else {
            // ȭ�� ���� ����Ʈ ǥ��
            mListView.setVisibility(View.VISIBLE);
            mLoadingContainer.setVisibility(View.GONE);
        }
    }

    /**
     * List Fast Holder
     *
     * @author nohhs
     */
    private class ViewHolder {
        // App Icon
        public ImageView mIcon;
        // App Name
        public TextView mName;
        // App Package Name
        public TextView mPacakge;
    }

    /**
     * List Adapter
     *
     * @author nohhs
     */
    private class IAAdapter extends BaseAdapter {
        private Context mContext = null;

        private List<ApplicationInfo> mAppList = null;
        private ArrayList<AppInfo> mListData = new ArrayList<AppInfo>();

        public IAAdapter(Context mContext) {
            super();
            this.mContext = mContext;
        }

        public int getCount() {
            return mListData.size();
        }

        public Object getItem(int arg0) {
            return null;
        }

        public long getItemId(int arg0) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                holder = new ViewHolder();

                LayoutInflater inflater = (LayoutInflater) mContext
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.list_item_layout, null);

                holder.mIcon = (ImageView) convertView
                        .findViewById(R.id.app_icon);
                holder.mName = (TextView) convertView
                        .findViewById(R.id.app_name);
                holder.mPacakge = (TextView) convertView
                        .findViewById(R.id.app_package);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            AppInfo data = mListData.get(position);

            if (data.mIcon != null) {
                holder.mIcon.setImageDrawable(data.mIcon);
            }

            holder.mName.setText(data.mAppNaem);
            holder.mPacakge.setText(data.mAppPackge);

            return convertView;
        }

        /**
         * ���ø����̼� ����Ʈ �ۼ�
         */
        public void rebuild() {
            if (mAppList == null) {

                Log.d(TAG, "Is Empty Application List");
                // ��Ű�� �Ŵ��� ���
                pm = AppInfoActivity.this.getPackageManager();

                // ��ġ�� ���ø����̼� ���
                mAppList = pm
                        .getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES
                                | PackageManager.GET_DISABLED_COMPONENTS);
            }

            AppFilter filter;
            switch (MENU_MODE) {
                case MENU_DOWNLOAD:
                    filter = AppInfo.THIRD_PARTY_FILTER;
                    break;
                default:
                    filter = null;
                    break;
            }

            if (filter != null) {
                filter.init();
            }

            // ���� ������ �ʱ�ȭ
            mListData.clear();

            AppInfo addInfo = null;
            ApplicationInfo info = null;
            for (ApplicationInfo app : mAppList) {
                info = app;

                if (filter == null || filter.filterApp(info)) {
                    // ���͵� ������

                    addInfo = new AppInfo();
                    // App Icon
                    addInfo.mIcon = app.loadIcon(pm);
                    // App Name
                    addInfo.mAppNaem = app.loadLabel(pm).toString();
                    // App Package Name
                    addInfo.mAppPackge = app.packageName;
                    mListData.add(addInfo);
                }
            }

            // ���ĺ� �̸����� ��Ʈ(�ѱ�, ����)
            Collections.sort(mListData, AppInfo.ALPHA_COMPARATOR);
        }
    }

    /**
     * �۾� �½�ũ
     * @author nohhs
     */
    private class AppTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            // �ε��� ����
            setLoadingView(true);
        }

        @Override
        protected Void doInBackground(Void... params) {
            // ���ø���Ʈ �۾�����
            mAdapter.rebuild();

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // ����� ����
            mAdapter.notifyDataSetChanged();

            // �ε��� ����
            setLoadingView(false);
        }

    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, MENU_DOWNLOAD, 1, R.string.menu_download);
        menu.add(0, MENU_ALL, 2, R.string.menu_all);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (MENU_MODE == MENU_DOWNLOAD) {
            menu.findItem(MENU_DOWNLOAD).setVisible(false);
            menu.findItem(MENU_ALL).setVisible(true);
        } else {
            menu.findItem(MENU_DOWNLOAD).setVisible(true);
            menu.findItem(MENU_ALL).setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuId = item.getItemId();

        if (menuId == MENU_DOWNLOAD) {
            MENU_MODE = MENU_DOWNLOAD;
        } else {
            MENU_MODE = MENU_ALL;
        }

        startTask();

        return true;
    }
}
