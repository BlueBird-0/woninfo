package com.bluebird.inhak.woninfo;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


// 웹 파싱은 무조건 스레드를 이용해서 동작시켜야 함.
public class WebParser {
    static String userAgent = "Mozilla/5.0 (Linux; Android 7.0; SM-G935S Build/NRD90M; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/64.0.3282.137 Mobile Safari/537.36";
    //String userAgent = "Dalvik/2.1.0 (Linux; U; Android 7.0; SM-G935S Build/NRD90M)";
    static Map<String, String> loginCookie = null;     //로그인 값 저장하는 쿠키

    static public boolean login(String id, String pw)
    {
        //로그인 페이지에서 입력되는 값  ID, PW
        Map<String, String> data = new HashMap<>();
        data.put("nextURL","http://intra.wku.ac.kr/SWupis/V005/loginReturn.jsp");
        data.put("userid", id);
        data.put("passwd", pw);

        try {
            Connection.Response loginResponse = Jsoup.connect("https://auth.wku.ac.kr/Cert/User/Login/login.jsp")
                    .userAgent(userAgent)
                    .timeout(3000)
                    .header("Origin", "http://intra.wku.ac.kr")
                    .header("Referer", "http://intra.wku.ac.kr/SWupis/V005/login.jsp")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Accept-Encoding", "gzip, deflate, br")
                    .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                    .header("Cache-Control", "max-age=0")
                    .header("Connection", "keep-alive")
                    .header("Content-Length", "104")
                    .data(data)
                    .followRedirects(true)
                    .method(Connection.Method.POST)
                    .execute();
            loginCookie = loginResponse.cookies();


            Connection.Response loginReturnResponse = Jsoup.connect("http://intra.wku.ac.kr/SWupis/V005/loginReturn.jsp")
                    .userAgent(userAgent)
                    .timeout(3000)
                    .header("Referer", "http://intra.wku.ac.kr/SWupis/V005/login.jsp")

                    .header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                    .header("Accept-Encoding", "gzip, deflate, br")
                    .header("Accept-Language","ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                    .header("Connection","keep-alive")
                    .header("Host","intra.wku.ac.kr")
                    .cookies(loginCookie)
                    .followRedirects(true)
                    .method(Connection.Method.GET)
                    .execute();
            loginCookie.putAll(loginReturnResponse.cookies());
        }catch(IOException e)
        {
            e.printStackTrace();
            return false;
        }catch(IllegalArgumentException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    static public Document getDomitoryPage()
            {
                try {
                    Document domitoryPage = Jsoup.connect("https://intra.wku.ac.kr/SWupis/V005/CommonServ/dormitory/stud/dormStudent.jsp?sm=3")
                            .userAgent(userAgent)
                            .timeout(3000000)
                            .cookies(loginCookie)
                            .header("Connection", "keep-alive")
                            .header("Cache-Control", "max-age=0")
                            .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                            .header("Content-Type", "text/html;charset=EUC-KR")
                            .header("Accept-Encoding", "gzip, deflate")
                            .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                            .header("HOST", "intra.wku.ac.kr")
                            .get();
                    return domitoryPage;
        }catch(IOException e) { e.printStackTrace();}
        return null;
    }
}
