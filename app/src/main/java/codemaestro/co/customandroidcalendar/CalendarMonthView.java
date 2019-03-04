package codemaestro.co.customandroidcalendar;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.TextView;



public class CalendarMonthView extends FrameLayout {

    private GridLayout mGridLayout;
    private ViewGroup dayNamesContainer;
    private CalendarDate mSelectedDate;

    public static final int NUMBER_OF_WEEKS_IN_MONTH = 6;
    public static final int NUMBER_OF_DAYS_IN_WEEK = 7;

    public CalendarMonthView(Context context) {
        super(context);
        init();
    }


    public void setSelectedDate(CalendarDate selectedDate) {
        mSelectedDate = selectedDate;
    }

    private void init() {
        inflate(getContext(), R.layout.view_calendar_month, this);
        mGridLayout = (GridLayout) findViewById(R.id.view_calendar_month_grid);
        dayNamesContainer = (ViewGroup) findViewById(R.id.day_names_container);
    }

    public void buildView(CalendarMonth calendarMonth) {
        buildDaysLayout();
        buildGridView(calendarMonth);
    }

    private void buildDaysLayout() {
        String[] days;
        days = getResources().getStringArray(R.array.days_starting_with_sunday);

        for (int i = 0; i < dayNamesContainer.getChildCount(); i++) {
            TextView textView = (TextView) dayNamesContainer.getChildAt(i);
            textView.setText(days[i]);
        }
    }

    private void buildGridView(CalendarMonth calendarMonth) {
        mGridLayout.setRowCount(NUMBER_OF_WEEKS_IN_MONTH);
        mGridLayout.setColumnCount(NUMBER_OF_DAYS_IN_WEEK);

        int screenWidth = Utils.getScreenWidth(getContext());
        int width = screenWidth / NUMBER_OF_DAYS_IN_WEEK;

        for (CalendarDate date : calendarMonth.getDays()) {
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = width;
            params.height = LayoutParams.WRAP_CONTENT;

            CalendarDateView dateView = new CalendarDateView(getContext(), date);
            dateView.setContentDescription(date.toString());
            dateView.setLayoutParams(params);

            mGridLayout.addView(dateView);
        }
    }

}
