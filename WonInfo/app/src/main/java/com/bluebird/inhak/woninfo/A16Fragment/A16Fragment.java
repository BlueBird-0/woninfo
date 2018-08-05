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
            foodTitle = new String[]{"7/23 월", "7/24 화", "7/25 수", "7/26 목", "7/27 금", "7/28 토", "7/29 일"};
            food[0] = new String[]{"쌀밥", "쌀밥", "쌀밥", "쌀밥", "쌀밥", "쌀밥", "쌀밥"};
            food[1] = new String[]{"아우국", "북어해장국", "오징어국", "돈육김치찌개", "콩나물무채국", "어묵국", "두부된장국"};
            food[2] = new String[]{"고추장불고기\n계란장조림\n청경채나물\n포기김치",
                    "함박콘치즈\n만두간장조림\n시금치나물\n브로컬리\n포기김치",
                    "돈육피망볶음\n프랑크소시지\n깻잎김치\n포기김치",
                    "참치볶음\n후라이\n가지나물\n건파래무침\n포기김치",
                    "미트볼케찹조림\n두부스크램블\n미역줄기팽이볶음\n포기김치",
                    "돈육스튜조림\n양상추샐럿\n새송이피망볶음\n도시락김\n포기김치",
                    "햄칠리부추볶음\n코다리조림\n고구마순볶음\n포기김치"};
            food[3] = new String[]{"김치주먹밥", "잡곡밥", "잡곡밥", "야채비빔밥", "잡곡밥", "잡곡밥", "잡곡밥"};
            food[4] = new String[]{"계란국/크림스프", "냉콩나물국", "닭무국", "건새우미역국", "시래기국", "부대찌개", "유부국"};
            food[5] = new String[]{"로제스파게티\n야채과일샐럿\n초코머핀\n포기김치",
                    "탕수육\n두부조림\n상추초무침\n멸치볶음\n포기김치",
                    "골뱅이소면무침\n해물경단전\n고구마순쌈잠무침\n콩조림\n포기김치",
                    "마카로니샐럿\n단무지\n찐옥수수\n요구르트",
                    "오리훈제&수육\n야채쌈\n어묵매콤볶음\n부추겉절이\n식혜\n포기김치",
                    "삼치엿장조림\n잡채고로케\n청경채겉절이\n포기김치",
                    "찜닭\n감자매콤조림\n오이무침\n포기김치"};
            food[6] = new String[]{"쌀밥", "쌀밥", "쌀밥", "쌀밥", "쌀밥", "쌀밥", "쌀밥"};
            food[7] = new String[]{"맑은미역국", "육개장", "수제비국", "순두부버섯국", "계란국", "콩나물국", "북어국"};
            food[8] = new String[]{"오불볶음\n라면땅\n느타리들깨볶음\n포기김치",
                    "옥수수전\n달걀찜\n오이부추무침\n쥐채조림\n포기김치",
                    "매콤돈육단호박조림\n손만두\n얼갈이쌈장무침\n포기김치",
                    "매콤메란조림\n크림떡볶이\n콩나물무침\n호박맛살볶음\n포기김치",
                    "돈육콩나물볶음\n감자단호박범벅\n상추치커리무침\n포기김치",
                    "오로시가스\n마파두부\n가지구이무침\n포기김치",
                    "낙지야채볶음\n달걀범벅\n숙주김가루무침\n포기김치"};


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