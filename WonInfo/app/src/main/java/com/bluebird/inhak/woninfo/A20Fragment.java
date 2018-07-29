package com.bluebird.inhak.woninfo;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by InHak on 2017-12-31.
 */

public class A20Fragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a_20_fragment, container, false);

        for(int i=0; i<2;i++)
        {
            Expandable expandable = new Expandable(view, R.id.a_20_exl0+i, R.id.a_20_exlbtn0+i, R.id.a_20_exlimg0+i);
        }

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId())
                {
                    case R.id.a_20_btnLink_0:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://cyber.wku.ac.kr/Cyber/CoBoard_V005/Content/view.jsp?gid=1115985866160&bid=1206492447666&cid=1206494376268")));
                        break;
                    case R.id.a_20_btnLink_1:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://cyber.wku.ac.kr/Cyber/CoBoard_V005/Content/view.jsp?gid=1115985866160&bid=1206492447666&cid=1206519503536")));
                        break;
                    case R.id.a_20_btnLink_2:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://cyber.wku.ac.kr/Cyber/CoBoard_V005/Content/view.jsp?gid=1115985866160&bid=1206492447666&cid=1206526124348")));
                        break;
                    case R.id.a_20_btnLink_3:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://cyber.wku.ac.kr/Cyber/CoBoard_V005/Content/view.jsp?gid=1115985866160&bid=1206492447666&cid=1206526294621")));
                        break;
                    case R.id.a_20_btnLink_4:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://cyber.wku.ac.kr/Cyber/CoBoard_V005/Content/view.jsp?gid=1115985866160&bid=1206492447666&cid=1206527200722")));
                        break;
                    case R.id.a_20_btnLink_5:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://cyber.wku.ac.kr/Cyber/CoBoard_V005/Content/view.jsp?gid=1115985866160&bid=1206492447666&cid=1206518708364")));
                        break;
                    case R.id.a_20_btnLink_6:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://cyber.wku.ac.kr/Cyber/CoBoard_V005/Content/view.jsp?gid=1115985866160&bid=1206492447666&cid=1206520520981")));
                        break;
                    case R.id.a_20_btnLink_7:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://cyber.wku.ac.kr/Cyber/CoBoard_V005/Content/view.jsp?gid=1115985866160&bid=1206492447666&cid=1206526190655")));
                        break;
                    case R.id.a_20_btnLink_8:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://cyber.wku.ac.kr/Cyber/CoBoard_V005/Content/view.jsp?gid=1115985866160&bid=1206492447666&cid=1206527154425")));
                        break;


                    case R.id.a_20_btnLink_9:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://cyber.wku.ac.kr/Cyber/CoBoard_V005/Content/view.jsp?gid=1115985866160&bid=1206492447666&cid=1206519930796")));
                        break;
                    case R.id.a_20_btnLink_10:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://cyber.wku.ac.kr/Cyber/CoBoard_V005/Content/view.jsp?gid=1115985866160&bid=1206492447666&cid=1206526889718")));
                        break;
                    case R.id.a_20_btnLink_11:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://cyber.wku.ac.kr/Cyber/CoBoard_V005/Content/view.jsp?gid=1115985866160&bid=1206492447666&cid=1206525909062")));
                        break;
                    case R.id.a_20_btnLink_12:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://cyber.wku.ac.kr/Cyber/CoBoard_V005/Content/view.jsp?gid=1115985866160&bid=1206492447666&cid=1370915536625")));
                        break;
                    case R.id.a_20_btnLink_13:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://cyber.wku.ac.kr/Cyber/CoBoard_V005/Content/view.jsp?gid=1115985866160&bid=1206492447666&cid=1206526668289")));
                        break;
                    case R.id.a_20_btnLink_14:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://cyber.wku.ac.kr/Cyber/CoBoard_V005/Content/view.jsp?gid=1115985866160&bid=1206492447666&cid=1206526776965")));
                        break;
                    case R.id.a_20_btnLink_15:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://cyber.wku.ac.kr/Cyber/CoBoard_V005/Content/view.jsp?gid=1115985866160&bid=1206492447666&cid=1206520303095")));
                        break;
                    case R.id.a_20_btnLink_16:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://cyber.wku.ac.kr/Cyber/CoBoard_V005/Content/view.jsp?gid=1115985866160&bid=1206492447666&cid=1206525854750")));
                        break;
                    case R.id.a_20_btnLink_17:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://cyber.wku.ac.kr/Cyber/CoBoard_V005/Content/view.jsp?gid=1115985866160&bid=1206492447666&cid=1208150085509")));
                        break;
                    case R.id.a_20_btnLink_18:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://cyber.wku.ac.kr/Cyber/CoBoard_V005/Content/view.jsp?gid=1115985866160&bid=1206492447666&cid=1404276568275")));
                        break;
                    case R.id.a_20_btnLink_19:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://cyber.wku.ac.kr/Cyber/CoBoard_V005/Content/view.jsp?gid=1115985866160&bid=1206492447666&cid=1206526719308")));
                        break;
                    case R.id.a_20_btnLink_20:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://cyber.wku.ac.kr/Cyber/CoBoard_V005/Content/view.jsp?gid=1115985866160&bid=1206492447666&cid=1275284920050")));
                        break;


                    case R.id.a_20_btnLink_21:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://cyber.wku.ac.kr/Cyber/CoBoard_V005/Content/view.jsp?gid=1115985866160&bid=1206492447666&cid=1206526838014")));
                        break;
                    case R.id.a_20_btnLink_22:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://cyber.wku.ac.kr/Cyber/CoBoard_V005/Content/view.jsp?gid=1115985866160&bid=1206492447666&cid=1206526979670")));
                        break;
                    case R.id.a_20_btnLink_23:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://cyber.wku.ac.kr/Cyber/CoBoard_V005/Content/view.jsp?gid=1115985866160&bid=1206492447666&cid=1206527067089")));
                        break;
                    case R.id.a_20_btnLink_24:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://cyber.wku.ac.kr/Cyber/CoBoard_V005/Content/view.jsp?gid=1115985866160&bid=1206492447666&cid=1206526938810")));
                        break;
                    case R.id.a_20_btnLink_25:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://cyber.wku.ac.kr/Cyber/CoBoard_V005/Content/view.jsp?gid=1115985866160&bid=1206492447666&cid=1206527024952")));
                        break;
                    case R.id.a_20_btnLink_26:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://cyber.wku.ac.kr/Cyber/CoBoard_V005/Content/view.jsp?gid=1115985866160&bid=1206492447666&cid=1206527112495")));
                        break;

                    //자료실(학생용)
                    case R.id.a_20_btnLink_27:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://cyber.wku.ac.kr/Cyber/CoBoard_V005/Content/list.jsp?gid=1115985866160&bid=1115985978760")));
                        break;
                }
            }
        };


        for(int i=0; i<28; i++)
        {
            TextView textView = (TextView)view.findViewById(R.id.a_20_btnLink_0 + i);
            String string = textView.getText().toString();
            textView.setText(Html.fromHtml("<u>"+string+"</u>"));
            textView.setOnClickListener(onClickListener);
        }

        return view;
    }
}
