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
    private Listener listenerFriend;
    private Listener listenerGroup;
    private Listener listenerStranger;
    private JavaPluginMain() {
        super(new JvmPluginDescriptionBuilder("org.yinlianlei.plugin.Bot", "0.1.0")
                .build());
    }

    @Override
    public void onEnable() {
        getLogger().info("日志");
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
                if(msg.contains("task")){
                    //sql.Bot_switch(msg,event);
                }else if(msg.contains("sub")){
                    //sql.Bot_switch(msg,event);
                }else if(msg.contains("菜单")){
                    group.sendMessage(
                    "命令格式: \n"+
                    "/交易 @玩家 物品 数量 [和某位玩家进行交易]\n"+
                    "/技能 @玩家 技能名称 [给某位玩家加Buff]\n"+
                    "/攻击 @玩家 [攻击某位玩家]\n"+
                    "/论道 @玩家 [和某位玩家论道]"
                    );
                }else if(msg.contains("/交易")){
                    group.sendMessage("未开放");
                }else if(msg.contains("/技能")){
                    group.sendMessage("未开放");
                }else if(msg.contains("/攻击")){
                    group.sendMessage("未开放");
                }else if(msg.contains("/论道")){
                    group.sendMessage("未开放");
                }
                
            }
        });
        eventChannel.subscribeAlways(FriendMessageEvent.class, f -> {
            //监听好友消息
            getLogger().info(f.getMessage().contentToString());
        });
    }
}
