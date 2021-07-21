package org.example.mirai.plugin;

import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.lang.Thread;
import java.text.SimpleDateFormat;

import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.AbstractMessageEvent;
import net.mamoe.mirai.event.events.FriendMessageEvent;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Group;

public class BotThread extends Thread {
    private Date time1=null,time2=null,time3=null;
    private long sleepTime=0;
    SimpleDateFormat format;
    private static Boolean stop = false;
    private HashMap groupMsg = null;
    private Bot bot = null;
    BotThread() {//init
        format = new SimpleDateFormat ("yyyy.MM.dd HH:mm:ss");//init format
        //bot = Bot.getInstance(2683380854L);//get target Bot
        groupMsg = new HashMap<String,ArrayList<String>>();
        time2_init();
        time3_init();
        System.out.println("Thread init finished.");
    };

    void time2_init(){
        try{
            if(time1 == null){
                time1 = new Date();
            }
            String[] t = format.format(new Date(time1.getTime()+24*60*60*1000)).split(" ");
            //String[] t = format.format(new Date(time1.getTime()+60*1000)).split(" ");//test
            time2 = format.parse(t[0]+" 7:00:00");//init send message time
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    void time3_init(){//update
        try{
            if(time1 == null){
                time1 = new Date();
            }
            time3 = new Date(time1.getTime()+3*60*60*1000);//init send message time
            //time3 = new Date(time1.getTime()+60*1000);//test
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    void getSleepTime(AbstractMessageEvent event){
        if(event instanceof FriendMessageEvent){
            ((GroupMessageEvent)event).getSubject().sendMessage("Group only");
            return;
        }
        Group group = ((GroupMessageEvent)event).getSubject();
        try{
            group.sendMessage("sleepTime:"+String.valueOf(sleepTime));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void Stop(){
        stop = true;
        groupMsg.clear();
        System.out.println("Stop thread");
    }

    public void run() {//once per subscribe web
        try{
            while(true){
                if(stop == true){
                    break;
                }
                time1 = new Date();
                //System.out.println("Now time:"+format.format(time1));
                if(time1.compareTo(time2) > 0){//rewrite
                    System.out.println("update time2");
                    BotMysql.subThread();
                    time2_init();//BotActiveEvent
                }
                if(time1.compareTo(time3) > 0){
                    System.out.println("update time3");
                    BotMysql.biliUpdateThread();
                    time3_init();
                }
                sleepTime = time3.getTime() - time1.getTime();

                System.out.println("latest-Thread-msg:sleep time:"+format.format(time1.getTime()+sleepTime));
                if(sleepTime < 0){
                    continue;
                }
                Thread.sleep(sleepTime);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

/*
ArrayList re = BotMysql.subThread();
                    for(int i=0;i<re.size();i++){
                        String[] strArr = String.valueOf(re.get(i)).split("\\*");//updateTime:owner/repo-groupId
                        System.out.println(strArr[0]+" "+strArr[1]);
                        if(!groupMsg.containsKey(strArr[1])){
                            groupMsg.put(strArr[1],new ArrayList<String>());
                        }
                        //Group group = bot.getGroupOrFail(Long.valueOf(strArr[1]));
                        //group.sendMessage(strArr[0]);
                        ((ArrayList)groupMsg.get(strArr[1])).add(strArr[0]);
                        //System.out.println("msg:"+strArr[0]);
                        //Thread.sleep(1000);//sleep for 1s
                    }
                    Iterator it = groupMsg.keySet().iterator();
                    String msg = new String("update:");
                    while(it.hasNext()){
                        String key = String.valueOf(it.next());
                        ArrayList array = ((ArrayList)groupMsg.get(key));
                        for(int i =0;i<re.size();i++){
                            msg += "\n"+array.get(i);
                        }
                        Group group = bot.getGroupOrFail(Long.valueOf(key));
                        group.sendMessage(msg);
                    }

                    //String[] t = format.format(new Date(time1.getTime())).split(" ");
            //time2 = format.parse(t[0]+" 07:00:00");//init send message time
*/