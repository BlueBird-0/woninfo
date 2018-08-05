package com.bluebird.inhak.woninfo.A16Fragment;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import com.bluebird.inhak.woninfo.MainActivity;
import com.bluebird.inhak.woninfo.R;

import java.util.Calendar;

public class A16Broadcast extends BroadcastReceiver {
    String INTENT_ACTION = Intent.ACTION_BOOT_COMPLETED;

    @Override
    public void onReceive(Context context, Intent intent) {
        String food[][] = new A16Fragment().getMenu(context);
        String title = "푸쉬 제목";
        String content = "푸쉬 내용";

        //시간 처리 부분
        Calendar now = Calendar.getInstance();
        int dayOfTheWeek = now.get(Calendar.DAY_OF_WEEK)-2; //일=1,월=2,화=3,수=4,목=5,금=6,토=7
        if(dayOfTheWeek == -1) //일요일
            dayOfTheWeek = 6;
        int time = 0;

        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 17, 0, 0); //저녁
        if(now.before(calendar)){
            title = "저녁 식사";
            time = 2;
        }
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 12, 0, 0); //점심
        if(now.before(calendar)){
            title = "점심 식사";
            time = 1;
        }
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 7, 40, 0); //아침
        if(now.before(calendar)){
            title = "아침 식사";
            time = 0;
        }
        content = food[3*time+0][dayOfTheWeek] + "\n" + food[3*time+1][dayOfTheWeek] + "\n" + food[3*time+2][dayOfTheWeek];
        Log.d("test000", content);


        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder builder = new Notification.Builder(context);
        builder.setSmallIcon(R.drawable.ic_push_food).setTicker("HETT").setWhen(System.currentTimeMillis())
                .setNumber(1).setContentTitle(title).setContentText(content)
                .setStyle(new Notification.BigTextStyle().bigText(content))
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE).setContentIntent(pendingIntent).setAutoCancel(true);

        //오레오 버전 이상일 경우 체널 설정
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("channel_id_1", "학식 알림", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("학식 푸쉬알림을 받습니다.");
            channel.enableLights(true);
            channel.setLightColor(Color.GREEN);
            //channel.enableVibration(true);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            notificationManager.createNotificationChannel(channel);
            builder.setChannelId("channel_id_1");
            Log.d("test000", "channel_id_1");
        }

        notificationManager.notify(1, builder.build());
    }
}
