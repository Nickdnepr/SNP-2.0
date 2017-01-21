package test.homework.nick.snp20.utils.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import test.homework.nick.snp20.model.music_info_model.Info;
import test.homework.nick.snp20.view.fragments.special_fragments.MFragmentForViewPager;

import java.util.List;

/**
 * Created by Nick on 21.12.16.
 */
public class MViewPagerAdapter extends FragmentPagerAdapter {
    private List<Info> infoList;

    public MViewPagerAdapter(FragmentManager fm, List<Info> infoList) {
        super(fm);
        this.infoList = infoList;
    }

    @Override
    public Fragment getItem(int position) {

        Bundle args = new Bundle();
        args.putString("url", infoList.get(position).getArtwork_url());
        MFragmentForViewPager fragmentForViewPager = new MFragmentForViewPager();
        fragmentForViewPager.setArguments(args);
        return fragmentForViewPager;
    }

    @Override
    public int getCount() {
        return infoList.size();
    }
}
