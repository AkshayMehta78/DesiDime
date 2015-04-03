package com.desidime.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.desidime.activity.R;
import com.desidime.adapter.DealAdapter;
import com.desidime.model.Deals;
import com.desidime.utils.Constants;
import com.desidime.utils.JSONParser;
import com.desidime.utils.Utils;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Akshay M on 4/2/2015.
 */
public class PopularDealFragment extends Fragment {

    private View view;
    private ProgressBar pb_loadingBar;
    private ListView lv_deals;
    private ArrayList<Deals> arr_topdeals;
    private static String ERR_STATUS;
    private DealAdapter adapter;
    private TextView tv_pagetitle;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        //Inflate the layout for this fragment
        view=  inflater.inflate(R.layout.deal_layout, container, false);
        getWidgetReference();
        bindWidgetReference();
        initialization();
        return view;
    }

    private void initialization() {
        arr_topdeals=new ArrayList<Deals>();
        if(Utils.isOnline(getActivity().getApplicationContext()))
            new JSONParse().execute();
    }

    private void bindWidgetReference() {
    }

    private void getWidgetReference() {
        pb_loadingBar= (ProgressBar) view.findViewById(R.id.pb_loadingBar);
        lv_deals= (ListView) view.findViewById(R.id.lv_deals);
        tv_pagetitle= (TextView) view.findViewById(R.id.tv_pagetitle);
    }

    private class JSONParse extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected void onPreExecute() {
            pb_loadingBar.setVisibility(View.VISIBLE);
            tv_pagetitle.setVisibility(View.VISIBLE);
            tv_pagetitle.setText("Fetching Popular Deals");
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            try {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                JSONParser parser = new JSONParser();
                JSONObject json = parser.getJSONFromUrl(Constants.FETCH_ALL_DEALS, params);
                return json;
            } catch (Exception e) {
                e.printStackTrace();
                return new JSONObject();
            }
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            try {
                pb_loadingBar.setVisibility(View.GONE);
                tv_pagetitle.setVisibility(View.GONE);
                lv_deals.setVisibility(View.VISIBLE);

                    ERR_STATUS = json.getString(Constants.STR_ERROR_STATUS);
                    if (ERR_STATUS.equalsIgnoreCase(Constants.JSON_SUCCESS)) {
                        JSONObject resultObj=json.getJSONObject("result");
                        JSONArray topArray=resultObj.getJSONArray("popular");
                        if (topArray.length() > 0) {
                        for(int i=0;i<topArray.length();i++)
                        {
                            JSONObject topObject=topArray.getJSONObject(i);
                            String deal_title=topObject.getString("title");
                            String deal_desc=topObject.getString("deal_detail");
                            String deal_image=topObject.getString("pic_thumb");
                            Deals item =new Deals();
                            item.setDeal_title(deal_title);
                            item.setDeal_desc(deal_desc);
                            item.setDeal_image(deal_image);
                            arr_topdeals.add(item);
                        }
                        adapter=new DealAdapter(getActivity().getApplicationContext(),arr_topdeals);
                        lv_deals.setAdapter(adapter);
                    }
                        else
                            Toast.makeText(getActivity(), "No Deals Found", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(getActivity(), "Fail", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Please Try Again", Toast.LENGTH_LONG).show();
            }
        }
    }
}
