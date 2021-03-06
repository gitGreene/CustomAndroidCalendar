package codemaestro.co.customandroidcalendar;

import android.widget.FrameLayout;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CustomCalendarView extends FrameLayout implements View.OnClickListener {

    private TextView monthTitleTextView;
    private ImageButton previousMonthLeft;
    private ImageButton nextMonthRight;
    private ViewPager mViewPager;
    private CalendarViewPagerAdapter mViewPagerAdapter;

    public CustomCalendarView(@NonNull Context context) {
        super(context);
        init();
    }

    public CustomCalendarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomCalendarView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_custom_calendar, this);
        mViewPager = (ViewPager) findViewById(R.id.month_view_pager);
        monthTitleTextView = (TextView) findViewById(R.id.custom_calendar_month_label);
        previousMonthLeft = (ImageButton) findViewById(R.id.calendar_previous_month_button);
        nextMonthRight = (ImageButton) findViewById(R.id.calendar_next_month_button);
        previousMonthLeft.setOnClickListener(this);
        nextMonthRight.setOnClickListener(this);
        buildCalendarView();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.calendar_next_month_button:
                int next = mViewPager.getCurrentItem() + 1;
                mViewPager.setCurrentItem(next, true);
                break;
            case R.id.calendar_previous_month_button:
                int prev = mViewPager.getCurrentItem() - 1;
                mViewPager.setCurrentItem(prev, true);
                break;
        }
    }

    private void buildCalendarView() {
        List<CalendarMonth> list = new ArrayList<>();
        CalendarMonth today = new CalendarMonth(Calendar.getInstance());

        list.add(new CalendarMonth(today, -2));
        list.add(new CalendarMonth(today, -1));
        list.add(today);
        list.add(new CalendarMonth(today, 1));
        list.add(new CalendarMonth(today, 2));

        mViewPagerAdapter = new CalendarViewPagerAdapter(list, mViewPager);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.addOnPageChangeListener(mPageChangeListener);
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setCurrentItem(2);
        monthTitleTextView.setText(mViewPagerAdapter.getItemPageHeader(2));
    }

    private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {
            int position = mViewPager.getCurrentItem();
            monthTitleTextView.setText(mViewPagerAdapter.getItemPageHeader(position));

            // current item is the first item in the list
            if (state == ViewPager.SCROLL_STATE_IDLE && position == 1) {
                addPrev();
            }

            // current item is the last item in the list
            if (state == ViewPager.SCROLL_STATE_IDLE && position == mViewPagerAdapter.getCount() - 2) {
                addNext();
            }

        }
    };

    private void addNext() {
        CalendarMonth month = mViewPagerAdapter.getItem(mViewPagerAdapter.getCount() - 1);
        mViewPagerAdapter.addNext(new CalendarMonth(month, 1));
    }

    private void addPrev() {
        CalendarMonth month = mViewPagerAdapter.getItem(0);
        mViewPagerAdapter.addPrev(new CalendarMonth(month, -1));
    }

}
