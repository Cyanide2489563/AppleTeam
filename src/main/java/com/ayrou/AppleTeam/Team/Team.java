package com.Ayrou.AppleTeam.Team;

import com.Ayrou.AppleTeam.Main;
import com.Ayrou.AppleTeam.Message.Message;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import net.minecraft.server.v1_13_R2.IChatBaseComponent;
import net.minecraft.server.v1_13_R2.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.*;

public final class Team {

    private Message message = Main.getMessage();
    private TeamManager teamManager = Main.getTeamManager();
    private String name;
    private UUID leader;
    private Visibility visibility;
    private boolean encryption;
    private boolean application;
    private boolean friend;
    private String password;
    private Invite invite;
    private ItemGet itemGet;
    private ArrayList<UUID> members;
    private HashMap<UUID, Long> invitations;
    private HashMap<UUID, Long> reviewList;
    private HashMap<UUID, Long> disconnectionList;

    Team(TeamManager.Builder builder) {
        this.name = builder.name;
        this.leader = builder.leader;
        this.visibility = builder.visibility;
        this.encryption = builder.encryption;
        this.application = builder.application;
        this.friend = builder.friend;
        this.password = builder.password;
        this.invite = builder.invite;
        this.itemGet = builder.itemGet;
        this.members = new ArrayList<>();
        addMember(leader);
        this.invitations = new HashMap<>();
        this.disconnectionList = new HashMap<>();
        if (this.application) this.reviewList = new HashMap<>();
    }

    String getTeamName() {
        return name;
    }

    Visibility getVisibility() {
        return visibility;
    }

    int getMemberSize() {
        return members.size();
    }

    boolean isEncryptionCanJoin() {
        return encryption;
    }

    boolean isApplicationCanJoin() {
        return application;
    }

    boolean isFriendCanJoin() {
        return friend;
    }

    boolean isPasswordCorrect(String password) {
        return this.password.equals(password);
    }

    boolean isLeader(UUID player) {
        return player.equals(leader);
    }

    boolean isTeamFull() {
        return !(members.size() < teamManager.getMaximum());
    }

    boolean isMember(UUID player) {
        return members.contains(player);
    }

    boolean isInvited(UUID player) {
        return invitations.containsKey(player);
    }

    boolean isApplicationed(UUID player) {
        if (!application) return false;
        return reviewList.containsKey(player);
    }

    boolean invite(UUID inviter,UUID player) {
        Player inviter_Player = Objects.requireNonNull(Bukkit.getPlayer(inviter));
        Player player_Player = Objects.requireNonNull(Bukkit.getPlayer(player));

        String inviterName = inviter_Player.getName();
        String playerName = player_Player.getName();

        invitations.put(player, System.currentTimeMillis() + teamManager.getInviteTimeout());
        sendMessages(inviterName + "已邀請玩家" + playerName);

        return invitations.containsKey(player) & sendInvitations(inviter_Player, player_Player);
    }

    boolean accept(UUID player) {
        members.add(player);
        setScoreBoard(player);
        invitations.remove(player);
        return members.contains(player);
    }

    boolean cancel(UUID player) {
        invitations.remove(player);
        return members.contains(player);
    }

    private boolean sendInvitations(Player inviter, Player player) {
        TextComponent up = new TextComponent("§a============================§r\n");
        TextComponent text = new TextComponent("隊伍：§6" + name + "§f已邀請你\n");
        TextComponent text1 = new TextComponent("§2§n[接受邀請]");
        text1.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/team accept " + name));
        text1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("接受邀請").create()));

        TextComponent space = new TextComponent("§r   ");

        TextComponent text2 = new TextComponent("§4§n[拒絕邀請]\n");
        text2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/team cancel " + name));
        text2.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("拒絕邀請").create()));

        TextComponent down = new TextComponent("§a============================§r");
        up.addExtra(text);
        up.addExtra(text1);
        up.addExtra(space);
        up.addExtra(text2);
        up.addExtra(down);

        IChatBaseComponent comp = IChatBaseComponent.ChatSerializer.a(ComponentSerializer.toString(up));

        PacketPlayOutChat packet = new PacketPlayOutChat(comp);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        return true;
    }

    private void sendMessages(String messages) {
        members.forEach(UUID -> Objects.requireNonNull(Bukkit.getPlayer(UUID)).sendMessage(messages));
    }

    void checkInvitations() {
        for (Iterator<Map.Entry<UUID, Long>> it = invitations.entrySet().iterator(); it.hasNext();) {
            Map.Entry<UUID, Long> item = it.next();
            if (item.getValue() < System.currentTimeMillis()) {
                Objects.requireNonNull(Bukkit.getPlayer(item.getKey()))
                        .sendMessage(message.getMessage("Team_Invite_TimeOut"));
                it.remove();
            }
            if (isMember(item.getKey())) {
                //TODO
                Objects.requireNonNull(Bukkit.getPlayer(item.getKey()))
                        .sendMessage(message.getMessage("Team_Invite_TimeOut"));
                it.remove();
            }
        }
    }

    void checkDisconnectionList() {
        disconnectionList.forEach((UUID,Long) -> {
            if (Long > System.currentTimeMillis()) {
                disconnectionList.remove(UUID);
            }
        });
    }

    private void addMember(UUID player) {
        members.add(player);
        setScoreBoard(player);
    }

    private void setScoreBoard(UUID player) {
        Scoreboard board = Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard();
        Objective obj = board.registerNewObjective(name,"dummy",ChatColor.GREEN + "隊伍名稱：" + ChatColor.GOLD + name);
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        ArrayList<org.bukkit.scoreboard.Team> scoreTeam = new ArrayList<>();
        for (int i = 0, num = teamManager.getMaximum(); i < num; i++) {
            String entryName = String.valueOf(ChatColor.GREEN);
            ChatColor color = ChatColor.GRAY;
            String playerName = "沒有隊員";
            String distance = " ";
            UUID member;

            if (members.size() > i) {
                member = members.get(i);

                if (!member.equals(player)) {
                    Player player1 = Objects.requireNonNull(Bukkit.getPlayer(member));
                    Player player2 = Objects.requireNonNull(Bukkit.getPlayer(player));
                    distance += String.valueOf((int)Math.floor(player1.getLocation().distance(player2.getLocation())));
                }
                if (member.equals(leader)) {
                    color = ChatColor.YELLOW;
                    entryName  += "隊長-";
                    playerName = Objects.requireNonNull(Bukkit.getPlayer(member)).getName();
                }
                else {
                    color = ChatColor.BLUE;
                    entryName += "隊員"+ i;
                    playerName = Objects.requireNonNull(Bukkit.getPlayer(member)).getName();
                }
            }
            else entryName += "隊員" + i;
            String data = color + playerName + ChatColor.WHITE + distance;

            scoreTeam.add(i, board.registerNewTeam(String.valueOf(i)));
            scoreTeam.get(i).addEntry(entryName);
            scoreTeam.get(i).setSuffix(data);
            obj.getScore(entryName).setScore(num - i);
        }
        Objects.requireNonNull(Bukkit.getPlayer(player)).setScoreboard(board);
    }

    void updateScoreBoard() {
        for (UUID member : members) {
            Player player = Objects.requireNonNull(Bukkit.getPlayer(member));
            Scoreboard boardTeam = player.getScoreboard();

            for (int i = 0, num = teamManager.getMaximum(); i < num; i++) {
                ChatColor color = ChatColor.GRAY;
                String playerName = "沒有隊員";
                String distance = " ";
                UUID member1;

                if (members.size() > i) {
                    member1 = members.get(i);

                    if (!member1.equals(member)) {
                        Player player1 = Objects.requireNonNull(Bukkit.getPlayer(member1));
                        distance += String.valueOf((int)Math.floor(player1.getLocation().distance(player.getLocation())));
                    }
                    if (member.equals(leader)) {
                        color = ChatColor.YELLOW;
                        playerName = Objects.requireNonNull(Bukkit.getPlayer(member1)).getName();
                    }
                    else {
                        color = ChatColor.BLUE;
                        playerName = Objects.requireNonNull(Bukkit.getPlayer(member1)).getName();
                    }
                }
                String data = color + playerName + ChatColor.WHITE + distance;
                if (boardTeam.getTeam(String.valueOf(i)) != null) boardTeam.getTeam(String.valueOf(i)).setSuffix(data);
            }
        }
    }

    boolean isDisconnectionTimeOut(UUID player) {
        if (disconnectionList.containsKey(player)) {
            return disconnectionList.get(player) > System.currentTimeMillis();
        }
        return true;
    }

    public String reConnection(UUID player) {
        if (isDisconnectionTimeOut(player)) return "你已超過重新連回隊伍時間，已被自動移出隊伍";
        setScoreBoard(player);
        addMember(player);
        disconnectionList.remove(player);
        return "已重新連線回隊伍";
    }

    public void disconnection(UUID player) {
        members.remove(player);
        disconnectionList.put(player, teamManager.getDisconnectionTimeout() + System.currentTimeMillis());
        sendMessages(Objects.requireNonNull(Bukkit.getPlayer(player)).getName() +
                "離線保留空位" + teamManager.getDisconnectionTimeout() + "分鐘");
    }
}