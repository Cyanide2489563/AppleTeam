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

    public ArrayList<String> getTeamMemberName() {
        ArrayList<String> memberName = new ArrayList<>();
        for (UUID member : members) {
            if (Bukkit.getOfflinePlayer(member).isOnline())
                memberName.add(Bukkit.getPlayer(member).getName());
            else
                memberName.add(Bukkit.getOfflinePlayer(member).getName());
        }
        return memberName;
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
        Player inviter_Player = Bukkit.getPlayer(inviter);
        Player player_Player = Bukkit.getPlayer(player);

        String inviterName = inviter_Player.getName();
        String playerName = player_Player.getName();

        invitations.put(player, System.currentTimeMillis() + teamManager.getInviteTimeout());
        sendMessages(inviterName + "已邀請玩家" + playerName);
        sendInvitations(player_Player);
        return true;
    }

    boolean accept(UUID player) {
        addMember(player);
        invitations.remove(player);
        return true;
    }

    boolean cancel(UUID player) {
        invitations.remove(player);
        return false;
    }

    private void sendInvitations(Player player) {
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
    }

    public String leave(UUID player) {
        removeMember(player);
        sendMessages(ChatColor.YELLOW + Bukkit.getPlayer(player).getName() + ChatColor.GREEN + "已離開隊伍");
        return ChatColor.GREEN + "已成功離開隊伍" + ChatColor.GOLD + name;
    }

    boolean kick(UUID member) {
        removeMember(member);
        Player player = Bukkit.getPlayer(member);
        if (player != null) {
            player.sendMessage(ChatColor.GREEN + "你已被隊伍" + ChatColor.GOLD + name + ChatColor.GREEN + "踢出");
            sendMessages(ChatColor.GREEN + "已踢除成員" + ChatColor.YELLOW + player.getName());
        }
        else {
            String name = Bukkit.getOfflinePlayer(member).getName();
            sendMessages(ChatColor.GREEN + "已踢除成員" + ChatColor.YELLOW + name);
        }
        return true;
    }

    void disband() {
        invitations.clear();
        if (reviewList != null) reviewList.clear();
        disconnectionList.clear();
        for (UUID member : members) clearScoreBoard(member);
        sendMessages("隊伍已解散");
        members.clear();
    }

    private void sendMessages(String messages) {
        members.forEach(UUID -> Objects.requireNonNull(Bukkit.getPlayer(UUID)).sendMessage(messages));
    }

    void checkInvitations() {
        for (Iterator<Map.Entry<UUID, Long>> it = invitations.entrySet().iterator(); it.hasNext();) {
            Map.Entry<UUID, Long> item = it.next();
            if (item.getValue() < System.currentTimeMillis()) {
                Player player;
                String playerName;

                if (Bukkit.getOfflinePlayer(item.getKey()).isOnline()) {
                    player = Bukkit.getPlayer(item.getKey());
                    player.sendMessage(message.getMessage("Team_Invite_TimeOut"));

                }
                playerName = Bukkit.getOfflinePlayer(item.getKey()).getName();
                sendMessages(ChatColor.YELLOW + playerName + ChatColor.GREEN + "的邀請已過期");
                it.remove();
            }
        }
    }

    private void addMember(UUID player) {
        members.add(player);
        sendMessages(ChatColor.YELLOW + Bukkit.getPlayer(player).getName() + ChatColor.GREEN + "已加入隊伍");
        setScoreBoard(player);
    }

    private void removeMember(UUID player) {
        members.remove(player);
        clearScoreBoard(player);
    }

    private void setScoreBoard(UUID player) {
        Scoreboard board = Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard();
        Objective obj = board.registerNewObjective(name,"dummy",ChatColor.GREEN + "隊伍名稱：" + ChatColor.GOLD + name);
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        ArrayList<org.bukkit.scoreboard.Team> scoreTeam = new ArrayList<>();
        for (int i = 0, num = teamManager.getMaximum(); i < num; i++) {
            String entryName = ChatColor.GREEN.toString();
            ChatColor color = ChatColor.GRAY;
            String playerName = "沒有隊員";
            String distance = " ";
            UUID member;

            if (members.size() > i) {
                member = members.get(i);

                if (Bukkit.getPlayer(member) != null) {
                    if (!member.equals(player)) {
                        Player player1 = Bukkit.getPlayer(member);
                        Player player2 = Bukkit.getPlayer(player);
                        if (player1 != null | player2 != null) {
                            distance += String.valueOf((int)Math.floor(player1.getLocation().distance(player2.getLocation())));
                        }
                    }
                    if (member.equals(leader)) {
                        color = ChatColor.YELLOW;
                        entryName  += "隊長-";
                    }
                    else {
                        color = ChatColor.BLUE;
                        entryName += "隊員"+ i;
                    }
                    playerName = Objects.requireNonNull(Bukkit.getPlayer(member)).getName();
                }
                else {
                    color = ChatColor.RED;
                    playerName = Bukkit.getOfflinePlayer(member).getName();
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
            Player player = Bukkit.getPlayer(member);
            if (player == null) continue;
            Scoreboard boardTeam = player.getScoreboard();

            for (int i = 0, num = teamManager.getMaximum(); i < num; i++) {
                ChatColor color = ChatColor.GRAY;
                String playerName = "沒有隊員";
                StringBuilder distance = new StringBuilder(" ");
                UUID member1;

                if (members.size() > i) {
                    member1 = members.get(i);

                    if (Bukkit.getOfflinePlayer(member1).isOnline()) {
                        if (member.equals(leader))
                            color = ChatColor.YELLOW;
                        else
                            color = ChatColor.BLUE;
                    }
                    else color = ChatColor.RED;

                    if (Bukkit.getPlayer(member1) != null) {
                        if (!member1.equals(member)) {
                            Player player1 = Objects.requireNonNull(Bukkit.getPlayer(member1));
                            distance.append((int) Math.floor(player1.getLocation().distance(player.getLocation())));
                        }
                        playerName = Bukkit.getPlayer(member1).getName();
                    }
                    else playerName = Bukkit.getOfflinePlayer(member1).getName();
                }
                String data = color + playerName + ChatColor.WHITE + distance;
                if (boardTeam.getTeam(String.valueOf(i)) != null) boardTeam.getTeam(String.valueOf(i)).setSuffix(data);
            }
        }
    }

    private void clearScoreBoard(UUID member) {
        Player player = Bukkit.getPlayer(member);
        if (player != null) {
            player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        }
    }

    private boolean isDisconnectionTimeOut(UUID player) {
        if (disconnectionList.containsKey(player)) {
            return disconnectionList.get(player) < System.currentTimeMillis();
        }
        return true;
    }

    boolean isOfflineMember(UUID player) {
        return disconnectionList.containsKey(player);
    }

    public String reConnection(UUID player) {
        if (isDisconnectionTimeOut(player)) {
            disconnectionList.remove(player);
            return "你已超過重新連線時間，已被自動移出隊伍";
        }
        setScoreBoard(player);
        disconnectionList.remove(player);
        return "已重新連線回隊伍";
    }

    public void disconnection(UUID player) {
        disconnectionList.put(player, teamManager.getDisconnectionTimeout() + System.currentTimeMillis());
        sendMessages(Objects.requireNonNull(Bukkit.getPlayer(player)).getName() +
                "離線保留空位" + (teamManager.getDisconnectionTimeout() / 1000) / 60 + "分鐘");
    }
}