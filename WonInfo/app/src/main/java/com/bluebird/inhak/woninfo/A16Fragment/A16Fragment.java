package com.bluebird.inhak.woninfo.A16Fragment;

import android.app.AlarmManager;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bluebird.inhak.woninfo.Expandable;
import com.bluebird.inhak.woninfo.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by InHak on 2017-12-31.
 */

public class A16Fragment extends Fragment {
    static String food[][] = new String[9][7];
    static String foodTitle[] = new String[7];
    static String htmlPageUrl = "http://61.245.232.174/wordpress/dormitoryfood/";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a_16_fragment, container, false);

        final SwitchCompat popupSwitch = (SwitchCompat) view.findViewById(R.id.a_16_switch_push);
        final RadioGroup pushTimerGroup = (RadioGroup)view.findViewById(R.id.a_16_radioGroup_pushTimer);
        final SharedPreferences preferences = getActivity().getSharedPreferences(getActivity().getString(R.string.SHARED_PRE_NAME), Context.MODE_PRIVATE);
        final Boolean popupState = preferences.getBoolean(getActivity().getString(R.string.shared_food_push_state), true);
        if(popupState) {
            popupSwitch.setChecked(true);
            for(int i=0; i<pushTimerGroup.getChildCount(); i++)
                pushTimerGroup.getChildAt(i).setEnabled(true);
        }

        //토글 스위치
        popupSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(popupSwitch.isChecked())
                {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean(getString(R.string.shared_food_push_state), true);
                    editor.commit();
                    for(int i=0; i<pushTimerGroup.getChildCount(); i++)
                        pushTimerGroup.getChildAt(i).setEnabled(true);
                    Toast toast = Toast.makeText(getActivity(), "푸쉬알림을 받습니다.", Toast.LENGTH_LONG); toast.show();

                    new A16Fragment().setMenu(getActivity());    //메뉴 불러오기
                    new A16Fragment.PushAlarm(getActivity()).Alarm(); //푸쉬알림설정
                }
                else
                {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean(getString(R.string.shared_food_push_state), false);
                    editor.commit();
                    for(int i=0; i<pushTimerGroup.getChildCount(); i++)
                        pushTimerGroup.getChildAt(i).setEnabled(false);
                    Toast toast = Toast.makeText(getActivity(), "푸쉬알림을 받지 않습니다.", Toast.LENGTH_LONG); toast.show();
                }
            }
        });


        int timer = preferences.getInt(getString(R.string.shared_food_push_timer),30);
        if(timer == 10)
            ((RadioGroup) view.findViewById(R.id.a_16_radioGroup_pushTimer)).check(R.id.a_16_radioBtn_10m);
        else if(timer == 30)
            ((RadioGroup) view.findViewById(R.id.a_16_radioGroup_pushTimer)).check(R.id.a_16_radioBtn_30m);
        else if(timer == 60)
            ((RadioGroup) view.findViewById(R.id.a_16_radioGroup_pushTimer)).check(R.id.a_16_radioBtn_60m);
        //시간 라디오 그룹
        pushTimerGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                SharedPreferences.Editor editor = preferences.edit();
                switch(checkedId){
                    case R.id.a_16_radioBtn_10m:
                        editor.putInt(getString(R.string.shared_food_push_timer), 10);
                        break;
                    case R.id.a_16_radioBtn_30m:
                        editor.putInt(getString(R.string.shared_food_push_timer), 30);
                        break;
                    case R.id.a_16_radioBtn_60m:
                        editor.putInt(getString(R.string.shared_food_push_timer), 60);
                        break;
                }
                editor.commit();
                new A16Fragment().setMenu(getActivity());    //메뉴 불러오기
                new A16Fragment.PushAlarm(getActivity()).Alarm(); //푸쉬알림설정
                Toast toast = Toast.makeText(getActivity(), "설정 변경 완료", Toast.LENGTH_SHORT); toast.show();
            }
        });

        this.getMenu(getActivity());

        //날자 표기
        TextView textViewFoodTitle[] = new TextView[7];
        for(int i=0; i<7; i++)
        {
            String resName = "a_16_foodTitle_"+i;
            String packName = getActivity().getPackageName();
            int viewId = getActivity().getResources().getIdentifier(resName, "id", packName);
            textViewFoodTitle[i] = (TextView) view.findViewById(viewId);
            textViewFoodTitle[i].setText(foodTitle[i]);
        }


        //텍스트뷰 (layout_a_16_food)에 출력
        TextView textViewFood[][] = new TextView[9][7];
        for(int i=0; i<9; i++)
        {
            for(int j=0; j<7; j++)
            {
                String resName = "a_16_food_"+i+"_"+j;
                String packName = getActivity().getPackageName();
                int viewId = getActivity().getResources().getIdentifier(resName, "id", packName);
                textViewFood[i][j] = (TextView) view.findViewById(viewId);
                textViewFood[i][j].setText(food[i][j]);
            }
        }
        //new PushAlarm(getActivity().getApplicationContext()).Alarm();

        //앱 기본 코드
        for(int i=0; i<3;i++)
        {
            Expandable expandable = new Expandable(view, R.id.a_16_exl0+i, R.id.a_16_exlbtn0+i, R.id.a_16_exlimg0+i);
        }

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId())
                {
                    case R.id.a_16_btn_manual0:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://dorm.wku.ac.kr/")));
                        break;
                }
            }
        };
        for(int i=0; i<1; i++)
        {
            TextView textView = (TextView)view.findViewById(R.id.a_16_btn_manual0+i);
            String string = textView.getText().toString();
            textView.setText(Html.fromHtml("<u>"+string+"</u>"));
            textView.setOnClickListener(onClickListener);
        }
        return view;
    }

    public String[][] getMenu(Context context)
    {
        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.SHARED_PRE_NAME), Context.MODE_PRIVATE);
        String callValue = preferences.getString(context.getString(R.string.shared_food_data), null);
        String[] splitStr = callValue.split("\\\\");
        for(int i=0; i<7; i++)
                foodTitle[i] = splitStr[i];
        for(int i=0; i<9; i++)
            for(int j=0; j<7; j++)
                food[i][j] = splitStr[i * 7 + j + 7];

        return food;
    }

    public static void setMenu(Context context)
    {
        // TODO 식단표 입력        학생생활관 주소       http://dorm.wku.ac.kr/, http://61.245.232.174/wordpress/dormitoryfood/
        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.SHARED_PRE_NAME), Context.MODE_PRIVATE);
        Long updateTime = preferences.getLong(context.getString(R.string.shared_food_push_updateTime), 0);

        //현재 업데이트 날자가 지난 상태라면
        Calendar calendar = Calendar.getInstance();


        // TODO 나중에 변경해야함 자동 업데이트 기능 수정바람
//        if(calendar.getTimeInMillis() > updateTime){
            //다음 업데이트 날자 설정
            {
                calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 18, 40, 0); //저녁식사 끝나는 시간
                calendar.add(Calendar.DATE, 8 - calendar.get(Calendar.DAY_OF_WEEK));

                Log.d("food", "다음 업데이트 날자 : " + new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime())+ " ( long 형 변수로는 :" +String.valueOf(calendar.getTimeInMillis()) +" )");

                SharedPreferences.Editor editor = preferences.edit();
                editor.putLong(context.getString(R.string.shared_food_push_updateTime), calendar.getTimeInMillis());
                editor.commit();
            }


            JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask(context);
            jsoupAsyncTask.execute();
            Log.d("food", "식단표 파싱 완료");
//        }
    }



    public static class PushAlarm {

        private Context context;
        public PushAlarm(Context context)
        {
            this.context = context;
        }
        public void Alarm()
        {
            AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

            //Broadcast를 만들어서 푸쉬 알림 사용
            Intent intent = new Intent(context, A16Broadcast.class);
            PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);

            //급식시간 기준 7:40~8:40,  12:00~13:40,  17:00~18:40
            Calendar calendar = Calendar.getInstance();
            Calendar now = Calendar.getInstance();


            SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.SHARED_PRE_NAME), Context.MODE_PRIVATE);
            int minute_dif = preferences.getInt(context.getString(R.string.shared_food_push_timer), 30);

            // 알람시간 calendar에 set하기
            int requestCode = 0;
            Log.d("food", "현재 시간 : " + new SimpleDateFormat("yyyy-MM-dd  HH:mm").format(calendar.getTime())+ " ( long 형 변수로는 :" +String.valueOf(calendar.getTimeInMillis()) +" )");
            while(true)
            {
                //calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 7, 40, 0);
                calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 7, 40, 0);
                calendar.add(Calendar.MINUTE, -minute_dif);
                if(now.before(calendar)){
                    sender = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                    alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), sender);
                }


                calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 12, 0, 0);
                calendar.add(Calendar.MINUTE, -minute_dif);
                if(now.before(calendar)){
                    sender = PendingIntent.getBroadcast(context, requestCode+1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                    alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), sender);
                }


                calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 17, 0, 0);
                calendar.add(Calendar.MINUTE, -minute_dif);
                if(now.before(calendar)){
                    sender = PendingIntent.getBroadcast(context, requestCode+2, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                    alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), sender);
                }

                if(calendar.get(Calendar.DAY_OF_WEEK) == 1)
                    break;
                else {
                    calendar.add(Calendar.DATE, 1);
                    requestCode += 3;
                }
            }
        }
    }


    //홈페이지 (급식표) 파싱 class
    private static class JsoupAsyncTask extends AsyncTask<Void, Void, Void>
    {
        private Context context;
        JsoupAsyncTask(Context context)
        {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                org.jsoup.nodes.Document doc = Jsoup.connect(htmlPageUrl).get();
                Elements links = doc.select("tbody.row-hover tr");

                Element link = links.get(0);

                //날자 설정
                foodTitle = link.text().trim().split(" ");
                link.attr("td");

                //식단 데이터 처리부분
                for(int j=0; j<3; j++)
                {
                    link = links.get(j+1);
                    String data[] = link.text().trim().split(" ");
                    for(int i=0, index=1; i<7; i++)
                    {
                        food[j*3 + 0][i] = data[index++];
                        food[j*3 + 1][i] = data[index++];
                        food[j*3 + 2][i] = data[index++].replace("/", "\n");
                    }
                    link.attr("td");
                }

            } catch(IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //Preferences 저장소에 식단 저장
            SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.SHARED_PRE_NAME), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            String foodString = "";

            //TODO 식단 수동입력   비사용시 주석처리
            //식단 수동 입력시 사용
            foodTitle = new String[]{"8/6 월", "8/7 화", "8/8 수", "8/9 목", "8/10 금", "8/11 토", "8/12 일"};
            food[0] = new String[]{"쌀밥", "쌀밥", "쌀밥", "쌀밥", "쌀밥", "쌀밥", "쌀밥"};
            food[1] = new String[]{"유부부추국", "북어국", "콩나물김치국", "건새우아욱국", "동태오징어국", "두부김치국", "소고기미역국"};
            food[2] = new String[]{"치즈비엔나\n두부조림\n양상추샐럿\n느타리볶음\n포기김치",
                    "돈육피망볶음\n달걀찜\n깻잎김치\n얼갈이나물\n포기김치",
                    "감자채볶음\n야채참치\n브로컬리양송이볶음\n청경채겉절이\n포기김치",
                    "코다리무조림\n새알심피망조림\n시금치들깨나물\n포기김치",
                    "함박&볶은김치\n단호박샐럿\n도라지엿장조림\n깻순겉절이\n포기김치",
                    "간장불고기\n톡톡크래미너겟\n호박표고볶음\n포기김치",
                    "오징어간장조림\n매콤두부지짐\n건파래볶음\n숙주나물\n포기김치"};
            food[3] = new String[]{"잡곡밥", "쌀밥", "잡곡밥", "잡곡밥", "해물볶음밥", "잡곡밥", "잡곡밥"};
            food[4] = new String[]{"계란국/스프", "메밀소바", "육개장", "맑은미역국", "미소된장국", "오징어국", "맑은콩나물국"};
            food[5] = new String[]{"돈까스&카레D\n옥수수마요콘치즈\n감자조림\n멸치호두볶음\n포기김치",
                    "야채튀김\n단무지\n핫케익&딸기쨈\n요구르트\n포기김치",
                    "생선살강정\n두부스크램블\n상추겉절이\n진미채조림\n포기김치",
                    "등뼈찜\n짜장떡볶이\n가지볶음\n오이냉국\n포기김치",
                    "달걀범벅\n치킨꼬치\n오이겉절이\n포기김치",
                    "닭시래기찜\n어묵버섯볶음\n시금치나물\n포기김치",
                    "고추장불고기\n양배추찜/오이고추\n두부조림\n포기김치"};
            food[6] = new String[]{"쌀밥", "쌀밥", "쌀밥", "쌀밥", "쌀밥", "쌀밥", "쌀밥"};
            food[7] = new String[]{"두부된장찌개", "감자고추장찌개", "얼큰북어국", "어묵쑥갓국", "순두부계란국", "만두국", "돈육김치찌개"};
            food[8] = new String[]{"매콤당면볶음\n해물경단구이\n숙주맛살무침\n우엉조림\n포기김치",
                    "돈육스튜조림\n새우부추만두\n가지나물\n포기김치",
                    "돈육폭찹\n마카로니샐럿\n애호박새우젓볶음\n구운김\n포기김치",
                    "오낙볶음\n새우너겟\n콩나물무침\n포기김치",
                    "간장갈비찜\n생선까스\n청경채고추장무침\n포기김치",
                    "북어찜\n감자메란조림\n상추부추무침\n포기김치",
                    "고등어무조림\n치킨너겟\n깻잎찜\n포기김치"};


            for(int i=0; i<7; i++)
            {
                foodString += foodTitle[i];
                foodString += "\\";
            }
            for(int i=0; i<9; i++)
            {
                for(int j=0; j<7; j++)
                {
                    foodString += food[i][j];
                    foodString += "\\";
                }
            }
            editor.putString(context.getString(R.string.shared_food_data), foodString);
            editor.commit();
            super.onPostExecute(aVoid);
        }
    }
}