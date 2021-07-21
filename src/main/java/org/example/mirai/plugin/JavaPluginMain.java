package org.example.mirai.plugin;

import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.Listener;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.utils.BotConfiguration;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.events.BotEvent;
import net.mamoe.mirai.event.events.NewFriendRequestEvent;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.event.events.StrangerMessageEvent;
import net.mamoe.mirai.event.events.TempMessageEvent;
import net.mamoe.mirai.event.events.BotOnlineEvent;
import net.mamoe.mirai.event.Listener;

import net.mamoe.mirai.message.data.PlainText;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.Message;

import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.contact.Group;

import com.alibaba.fastjson.JSONArray;

import java.sql.*;


/**
 * 使用 Java 请把
 * {@code /src/main/resources/META-INF.services/net.mamoe.mirai.console.plugin.jvm.JvmPlugin}
 * 文件内容改成 {@code org.example.mirai.plugin.JavaPluginMain} <br/>
 * 也就是当前主类全类名
 *
 * 使用 Java 可以把 kotlin 源集删除且不会对项目有影响
 *
 * 在 {@code settings.gradle.kts} 里改构建的插件名称、依赖库和插件版本
 *
 * 在该示例下的 {@link JvmPluginDescription} 修改插件名称，id 和版本等
 *
 * 可以使用 {@code src/test/kotlin/RunMirai.kt} 在 IDE 里直接调试，
 * 不用复制到 mirai-console-loader 或其他启动器中调试
 */

public final class JavaPluginMain extends JavaPlugin {
    public static final JavaPluginMain INSTANCE = new JavaPluginMain();
    private BotMysql sql;
    private Listener listenerFriend;
    private Listener listenerGroup;
    private Listener listenerStranger;
    private BotThread BT;//bot thread for daily subscribe
    private JavaPluginMain() {
        super(new JvmPluginDescriptionBuilder("org.yinlianlei.plugin.Bot", "0.1.0")
                .build());
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(Exception e){
            e.printStackTrace();
        }
        BT = new BotThread();
        sql = new BotMysql();
        BT.start();
    }

    @Override
    public void onEnable() {
        getLogger().info("日志");
        //System.out.println(Bot.botInstances);
        EventChannel<Event> eventChannel = GlobalEventChannel.INSTANCE.parentScope(this);
        listenerGroup = GlobalEventChannel.INSTANCE.subscribeAlways(GroupMessageEvent.class, event -> {
            String msg = event.getMessage().serializeToMiraiCode();
            //if(msg.contains("mirai:at:")){//Get QQ id
                //System.out.println(msg.split("mirai:at:")[1].split("]")[0]);
            //}
            //System.out.println(msg);
            //System.out.println(event.getMessage().contentToString());
            Group group = event.getSubject();
            if(msg.charAt(0) == '/'){
                String[] in = msg.split(" ");
                if(in[0].contains("task")){
                    sql.Bot_switch(in,event);
                }else if(in[0].contains("bili")){
                    sql.Bot_switch(in,event);
                }else if(in[0].contains("git")){
                    sql.Bot_switch(in,event);
                }else if(in[0].compareTo("/stop") == 0){
                    BT.Stop();
                }
                
            }
        });
        listenerFriend = GlobalEventChannel.INSTANCE.subscribeAlways(FriendMessageEvent.class, event -> {
            String msg = event.getMessage().contentToString();
            //event.getSubject().sendMessage(String.valueOf(event.getTime()));
            //Friend fri = event.getFriend();
            User friend = event.getSubject();
            if(msg.charAt(0) == '/'){
                String[] in = msg.split(" ");
                if(in[0].contains("task")){
                    sql.Bot_switch(in,event);
                }else if(in[0].contains("bili")){
                    sql.Bot_switch(in,event);
                }else if(in[0].contains("git")){
                    sql.Bot_switch(in,event);
                }else if(in[0].compareTo("/stop") == 0){
                    BT.Stop();
                }
            }
        });
        listenerStranger = GlobalEventChannel.INSTANCE.subscribeAlways(NewFriendRequestEvent.class, event -> {
            event.accept();
        });
    }
}
