package com.pear.threekingdom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;

import android.widget.TextView;

import com.pear.threekingdom.db.DbManager;
import com.pear.threekingdom.entity.Hero;

import java.util.List;

/**
 * Created by bowenwu on 2017/11/24.
 */

public class HeroListAdapter extends BaseAdapter{
    private List<Hero> data;
    private boolean[] whetherDelete;
    private DbManager dbManager;
    private Context context;
    private boolean whetherRadioButtonAppear = false;
    private String filter;
    public HeroListAdapter(DbManager d, Context c, String filter) {
        super();
        dbManager = d;
        context = c;
        if (filter == null) {
            data = dbManager.queryAllHeroes();
        } else {
            data = dbManager.queryHeroesByName(filter);
        }
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    /**
     * may return this value on longClickListener
     * @param i
     * @return i
     */
    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.listview_hero, null);
        }
        ImageView avatarView = (ImageView)view.findViewById(R.id.avatar);
        TextView name = (TextView)view.findViewById(R.id.hero_name);
        TextView pinyin = (TextView)view.findViewById(R.id.hero_pinyin);
        TextView gender = (TextView)view.findViewById(R.id.hero_gender);
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.radio_button);

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickItem(i);
            }
        });
        avatarView.setImageBitmap(data.get(i).avatar);
        name.setText(data.get(i).name);
        pinyin.setText(data.get(i).pinyin);
        gender.setText(data.get(i).gender);

        if (whetherRadioButtonAppear == true) {
            checkBox.setChecked(false);
            checkBox.setVisibility(View.VISIBLE);
        } else {
            checkBox.setVisibility(View.INVISIBLE);
        }
        return view;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();

    }

    public void setRadioButtonAppear(boolean t) {
        whetherRadioButtonAppear = t;
        whetherDelete = new boolean[data.size()];
        for (int i = 0; i < data.size(); ++i) {
            whetherDelete[i] = false;
        }
        this.notifyDataSetChanged();
    }

    public void confirmDeleteHeros() {
        if (whetherRadioButtonAppear == true) {
            // delete them in database
            for (int i = 0; i < data.size(); ++i) {
                if (whetherDelete[i] == true) {
                    dbManager.deleteOneHero(data.get(i).hero_id);
                }
            }
            this.setRadioButtonAppear(false);
            this.searchHerosByName(filter);
        }
    }
    public void searchHerosByName(String name) {
        filter = name;
        if (name == null) {
            data = dbManager.queryAllHeroes();
        } else {
            if (name.isEmpty()) {
                data = dbManager.queryAllHeroes();
            } else {
                data = dbManager.queryHeroesByName(name);
            }
        }
        this.notifyDataSetChanged();
    }

    public boolean whetherInMutipleSelected() {
        return whetherRadioButtonAppear;
    }

    public void clickItem(int position) {
        if (whetherRadioButtonAppear == true) {
            whetherDelete[position] = !whetherDelete[position];
        }
    }
}
