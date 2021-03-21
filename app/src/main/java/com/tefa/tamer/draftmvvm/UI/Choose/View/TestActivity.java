package com.tefa.tamer.draftmvvm.UI.Choose.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.animation.LayoutTransition;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.tefa.tamer.R;
import com.tefa.tamer.databinding.ActivityTestBinding;
import com.tefa.tamer.draftmvvm.UI.Base.BaseActivity;
import com.tefa.tamer.draftmvvm.UI.Choose.ViewModel.ChooseViewModel;
import com.tefa.tamer.draftmvvm.UI.Daarmassr.View.DarMasrFragment;
import com.tefa.tamer.draftmvvm.UI.EskanEgtamy.View.EgtmayFragment;
import com.tefa.tamer.draftmvvm.UI.EskanEgtamy.View.EskanEgtmayActivity;
import com.tefa.tamer.draftmvvm.UI.EskanGana.View.GanaFragment;
import com.tefa.tamer.draftmvvm.UI.Main.View.MainActivity;
import com.tefa.tamer.draftmvvm.UI.Main.View.User;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends BaseActivity {

    private ActivityTestBinding binding;
    private ViewPagerAdapter adapter;
    private int indicatorWidth;
    private ChooseViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        defineToolBar();
        setupView();

    }

    private void defineToolBar() {
        //    final Drawable upArrow = getResources().getDrawable(R.drawable.ic_toolbar_back);
        // upArrow.setColorFilter(getResources().getColor(R.color.grey), PorterDuff.Mode.SRC_ATOP);

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        // getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setTitle(context.getString(R.string.app_name));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_add:
                Intent intent = new Intent(TestActivity.this, EskanEgtmayActivity.class);
                startActivity(intent);
                break;
            case  R.id.action_signout:
                viewModel.signOut();
                break;
        }

        return super.onOptionsItemSelected(item);
    }




    private void setupView() {
        setupViewPager();
        new TabLayoutMediator(binding.tabLayout, binding.viewPager, (tab, position) -> tab.setText(adapter.getFragmentTitleList().get(position))).attach();
        binding.tabLayout.setLayoutTransition(new LayoutTransition());
        binding.tabLayout.setSelectedTabIndicatorColor(getResources().getColor(android.R.color.transparent));
        setupTabView();
        //getIntentData();
    }


    private void setupTabView() {

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //Determine indicator width at runtime
        binding.tabLayout.post(() -> {
            indicatorWidth = binding.tabLayout.getWidth() / binding.tabLayout.getTabCount();

            //Assign new width
            FrameLayout.LayoutParams indicatorParams = (FrameLayout.LayoutParams) binding.indicator.getLayoutParams();
            indicatorParams.width = indicatorWidth;
            binding.indicator.setLayoutParams(indicatorParams);
        });

    }

    private void setupViewPager() {


        adapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle());
        adapter.addFragment(new EgtmayFragment(), getString(R.string.eskanEgtmay));
        adapter.addFragment(new DarMasrFragment(), getString(R.string.daarMasr));
        adapter.addFragment(new GanaFragment(), getString(R.string.eskanGana));
        binding.viewPager.setAdapter(adapter);

        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) binding.indicator.getLayoutParams();

                //Multiply positionOffset with indicatorWidth to get translation
                float translationOffset = (positionOffset + position) * indicatorWidth;
                params.setMarginStart((int) translationOffset);
                binding.indicator.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }

    @Override
    public void initViewModel() {
        viewModel = new ViewModelProvider(this).get(ChooseViewModel.class);
        setViewModel(viewModel);
    }

    @Override
    public void initObservers() {

        viewModel.getIsSuccessMLD().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                startSplashActivity();
            }
        });

        viewModel.getUserAlreadyExistMLD().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {

            }
        });

    }

    @Override
    public void initErrorObservers() {

    }

    @Override
    public void initLoadingObservers() {

    }

    private void startSplashActivity() {

        Intent intent = new Intent(TestActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    static class ViewPagerAdapter extends FragmentStateAdapter{

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        public List<String> getFragmentTitleList() {
            return mFragmentTitleList;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            // return your fragment that corresponds to this 'position'
            return mFragmentList.get(position);
        }

        @Override
        public int getItemCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title){

            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);

        }
    }
}