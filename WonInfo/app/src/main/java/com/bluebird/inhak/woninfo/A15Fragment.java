package com.bluebird.inhak.woninfo;

import android.app.Fragment;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.style.ForegroundColorSpan;
import android.text.style.LineBackgroundSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by InHak on 2017-12-31.
 */

public class A15Fragment extends Fragment {
    ArrayList <String> list, redList;

    public void dateSetting() {
        list = new ArrayList<>();
        list.add("2018.01.30.2018.02.01.2018학년도 1학기 예비수강신청");
        list.add("2018.02.06.2018.02.08.2018학년도 1학기 수강신청");
        list.add("2018.02.15.2018.02.16.설날연휴");
        list.add("2018.02.19.2018.02.23.2018학년도 1학기 개강 준비기간");
        list.add("2018.02.20.학위수여식");
        list.add("2018.02.22.2018학년도 1학기 추가수강신청");
        list.add("2018.03.02.입학식 및 신입생 수강신청");
        list.add("2018.03.05.2018.03.16.부전공, 복수전공(교직복수전공 포함)신청기간");
        list.add("2018.03.08.2018.03.09.수강신청 변경 및 확인");
        list.add("2018.03.19.2018.03.21.1학기 성적포기 신청기간");
        list.add("2018.03.19.2018.03.21.1학기 수강신청 교과목 Drop기간");
        list.add("2018.03.30.일반휴학 및 군복학 마감");
        list.add("2018.03.05.1학기 개강");
        //list.add("2018.04.02.2018.06.15.평생교육실습기간");
        list.add("2018.04.02.2018.04.27.교생실습기간");
        list.add("2018.04.23.2018.04.27.수시시험기간");
        list.add("2018.04.30.2018.05.04.집중수업 및 현장학습기간(정상수업)");
        list.add("2018.05.01.근로자의 날");
        list.add("2018.05.07.대체공휴일(휴무일)");
        list.add("2018.05.15.개교기념일(휴무일)");
        list.add("2018.05.22.부처님오신날(휴무일)");
        list.add("2018.05.28.1학기 조기시험 신청 자격일(군입대)");
        list.add("2018.05.29.2018.05.31.하계 계절수업 예비수강신청");
        list.add("2018.05.31.2018.06.15.1학기 수업평가(학생)");
        //list.add("2018.04.02.2018.06.15.평생교육실습기간");
        list.add("2018.06.01.육일대재(휴무일)");
        list.add("2018.06.06.현충일(휴무일)");
        list.add("2018.06.07.2018.06.08.하계 계절수업 본수강신청");
        list.add("2018.06.13.지방선거일(휴무일)");
        list.add("2018.06.14.2018.06.15.하계 계절수업 등록기간");
        list.add("2018.06.18.2018.06.22.기말시험기간");
        list.add("2018.06.22.1학기 종강");
        list.add("2018.06.25.2018.07.20.하계 계절수업기간(융합학기)");
        list.add("2018.07.06.성적사정");
        list.add("2018.07.13.졸업사정");
        list.add("2018.07.23.2018.07.27.재입학 원서접수");
        list.add("2018.07.24.2018.08.03.2학기 수업계획서 입력(교수)");
        list.add("2018.07.24.2018.08.03.2학기 수업계획서 입력(교수)");
        list.add("2018.08.07.2018.08.09.2학기 예비수강신청");
        list.add("2018.08.13.2018.08.17.2학기 수강신청");
        list.add("2018.08.15.광복절(휴무일)");
        list.add("2018.08.20.2018.08.24.2학기 개강 준비기간");
        list.add("2018.08.20.학위수여식");
        list.add("2018.08.27.2학기 개강");
        list.add("2018.08.27.2018.00.90.부전공, 복수전공(교직복수전공 포함)신청기간");
        list.add("2018.08.30.2018.08.31.수강신청 변경 및 확인");
        list.add("2018.09.10.2018.09.12.2학기 수강신청 교과목 Drop기간");
        list.add("2018.09.10.2018.09.12.2학기 성적포기 신청기간");
        list.add("2018.09.21.일반휴학 및 군복학 마감");
        list.add("2018.09.24.2018.09.26.추석 연휴(휴무일)");
        //list.add("2018.10.01.2018.12.14.평생교육실습기간");
        list.add("2018.10.01.2018.10.26.교생실습기간");
        list.add("2018.10.03.개천절(휴무일)");
        list.add("2018.10.09.한글날(휴무일)");
        list.add("2018.10.15.2018.10.19.수시시험기간");
        list.add("2018.10.22.2018.10.26.집중수업 및 현장학습기간(정상수업)");
        list.add("2018.10.24.2018.10.26.원광플러스페스티벌");
        list.add("2018.11.13.2018.11.15.동계 계절수업 예비수강신청");
        list.add("2018.11.19.2학기 조기시험 신청 자격일(군입대)");
        list.add("2018.11.23.2018.12.07.2학기 수업평가(학생)");
        //list.add("2018.10.01.2018.12.14.평생교육실습기간");
        list.add("2018.11.23.2018.12.07.2학기 수업평가(학생)");
        list.add("2018.12.04.2018.12.06.동계 계절수업 본수강신청");
        list.add("2018.12.10.2018.12.14.기말시험기간");
        list.add("2018.12.11.2018.12.13.동계 계절수업 등록기간");
        list.add("2018.12.14.2학기 종강");
        list.add("2018.12.17.2019.01.11.동계 계절수업기간(융합학기)");
        list.add("2018.12.25.크리스마스(휴무일)");
        list.add("2018.12.28.성적사정");

        redList = new ArrayList<>();
        redList.add("2018.05.07.대체공휴일(휴무일)");
        redList.add("2018.05.15.개교기념일(휴무일)");
        redList.add("2018.05.22.부처님오신날(휴무일)");
        redList.add("2018.06.01.육일대재(휴무일)");
        redList.add("2018.06.06.현충일(휴무일)");
        redList.add("2018.06.13.지방선거일(휴무일)");
        redList.add("2018.08.15.광복절(휴무일)");
        redList.add("2018.09.24.2018.09.26.추석 연휴(휴무일)");
        redList.add("2018.10.03.개천절(휴무일)");
        redList.add("2018.10.09.한글날(휴무일)");
        redList.add("2018.12.25.크리스마스(휴무일)");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.a_15_fragment, container, false);

        for(int i=0; i<1;i++)
        {
            Expandable expandable = new Expandable(view, R.id.a_15_exl0+i, R.id.a_15_exlbtn0+i, R.id.a_15_exlimg0+i);
        }

        MaterialCalendarView materialCalendarView = (MaterialCalendarView)view.findViewById(R.id.calendarView);

        dateSetting();//날자 성정

        HashSet<CalendarDay> setDays = getCalendarDaysSet();        //일정있는날
        HashSet<CalendarDay> setOverDays = getCalendarRedDaysSet();    //휴무일

        //////akjdnbsajkbsakjdbsakdjasbjkdsbjkdbaskjdbaskjbksajd
        materialCalendarView.addDecorators(
                new SundayDecorator(),      //일요일
                new SaturdayDecorator(),    //토요일
                new TodayDecorator(),       //오늘
                new EventDecorator(Color.BLUE, setDays),//일반 일정 표시
                new EventRedDecorator(setOverDays));    //공휴일 표시


        OnMonthChangedListener onMonthChangedListener = new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                ListView contentList = (ListView)view.findViewById(R.id.a_15_contentList);
                ArrayList<A15Item> data = new ArrayList<>();


                String query = date.getYear()+"."+((date.getMonth()+1)/10)+((date.getMonth()+1)%10);

                Log.d("test", query);
                for(String str: list)
                {
                    if (str.contains(query) == true)
                    {
                        String dataStrings[] = str.split("\\.");
                        if(dataStrings.length > 6) {
                            String day = dataStrings[1] + "." + dataStrings[2] + " ~ " + dataStrings[4] + "."+ dataStrings[5];
                            String context = dataStrings[6];
                            A15Item test = new A15Item(day, context);
                            data.add(test);
                        } else {
                            String day = dataStrings[1] + "." + dataStrings[2];
                            String context = dataStrings[3];
                            A15Item test = new A15Item(day, context);
                            data.add(test);
                        }
                    }
                }


                A15Adapter adapter = new A15Adapter(view.getContext(), R.layout.a_15_item, data);
                contentList.setAdapter(adapter);
                A15Adapter.setListViewHeightBasedOnChildren(contentList);
            }
        };
        materialCalendarView.setOnMonthChangedListener(onMonthChangedListener);
        onMonthChangedListener.onMonthChanged(materialCalendarView, CalendarDay.today());
        return view;
    }



    private HashSet<CalendarDay> getCalendarDaysSet()
    {
        HashSet<CalendarDay> setDays =new HashSet<>();

        //문자열 자르기
        for(int i=0; i<list.size(); i++) {
            String date[] = (list.get(i)).split("\\.");
            Calendar cal1 = Calendar.getInstance();
            cal1.set(Integer.parseInt(date[0]), Integer.parseInt(date[1]) - 1, Integer.parseInt(date[2]));

            if (date.length >= 7) {
                Calendar cal2 = Calendar.getInstance();
                cal2.set(Integer.parseInt(date[3]), Integer.parseInt(date[4]) - 1, Integer.parseInt(date[5]) + 1);

                while (cal1.getTime().before(cal2.getTime())) {
                    CalendarDay calDay = CalendarDay.from(cal1);
                    setDays.add(calDay);
                    cal1.add(Calendar.DATE, 1);
                }
            } else {
                CalendarDay calDay = CalendarDay.from(cal1);
                setDays.add(calDay);
            }
        }
        return setDays;
    }
    private HashSet<CalendarDay> getCalendarRedDaysSet()
    {
        HashSet<CalendarDay> setDays =new HashSet<>();

        //문자열 자르기
        for(int i=0; i<redList.size(); i++) {
            String date[] = (redList.get(i)).split("\\.");
            Calendar cal1 = Calendar.getInstance();
            cal1.set(Integer.parseInt(date[0]), Integer.parseInt(date[1]) - 1, Integer.parseInt(date[2]));

            if (date.length >= 7) {
                Calendar cal2 = Calendar.getInstance();
                cal2.set(Integer.parseInt(date[3]), Integer.parseInt(date[4]) - 1, Integer.parseInt(date[5]) + 1);

                while (cal1.getTime().before(cal2.getTime())) {
                    CalendarDay calDay = CalendarDay.from(cal1);
                    setDays.add(calDay);
                    cal1.add(Calendar.DATE, 1);
                }
            } else {
                CalendarDay calDay = CalendarDay.from(cal1);
                setDays.add(calDay);
            }
        }
        return setDays;
    }

    public class EventRedDecorator implements  DayViewDecorator {
        private final HashSet<CalendarDay> dates;

        public EventRedDecorator(Collection<CalendarDay> dates) {
            this.dates = new HashSet<>(dates);
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return dates.contains(day);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new LineBackgroundSpan() {
                @Override
                public void drawBackground(Canvas c, Paint p, int left, int right, int top, int baseline, int bottom, CharSequence text, int start, int end, int lnum) {
                    int oldColor = p.getColor();
                    p.setColor(Color.RED);

                  //  c.drawCircle((left + right) / 2 - 18, bottom + 5, 9, p);
                    c.drawCircle((left + right) / 2, bottom + 6, 9, p);
                    p.setColor(oldColor);
                }
            });
        }
    }

    public class EventDecorator implements DayViewDecorator {
        private final int color;
        private final HashSet<CalendarDay> dates;

        public EventDecorator(int color, Collection<CalendarDay> dates) {
            this.color = color;
            this.dates = new HashSet<>(dates);
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return dates.contains(day);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new DotSpan(7, color));
        }
    }

    private class SundayDecorator implements DayViewDecorator {
        private final Calendar calendar = Calendar.getInstance();
        private SundayDecorator() {}

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            day.copyTo(calendar);
            int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
            return weekDay == Calendar.SUNDAY;
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(Color.RED));
        }
    }

    private class SaturdayDecorator implements DayViewDecorator{
        private final Calendar calendar = Calendar.getInstance();
        private SaturdayDecorator(){}

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            day.copyTo(calendar);
            int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
            return weekDay == Calendar.SATURDAY;
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(Color.BLUE));
        }
    }

    private class TodayDecorator implements DayViewDecorator {
        private CalendarDay date;
        private TodayDecorator() {
            date = CalendarDay.today();
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return date != null & day.equals(date);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(Color.WHITE));
            view.addSpan(new LineBackgroundSpan() {
                @Override
                public void drawBackground(Canvas c, Paint p, int left, int right, int top, int baseline, int bottom, CharSequence text, int start, int end, int lnum) {
                    int oldColor = p.getColor();
                    p.setColor(ContextCompat.getColor(getActivity(), R.color.blueMist));

                    c.drawCircle((left + right)/2, (bottom + top)/2 , 70, p);
                    p.setColor(oldColor);
                }
            });
        }
    }

}
