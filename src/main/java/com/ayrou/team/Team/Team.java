package com.ayrou.team.Team;

import com.ayrou.team.Main;
import com.ayrou.team.Message.Message;
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

final class Team {

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
        if (this.application) this.reviewList = new HashMap<>();
    }

    String getTeamName() {
        return name;
    }

    UUID getLeader() {
        return leader;
    }

    Visibility getVisibility() {
        return visibility;
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

    public void sendMessages(String messages) {
        members.forEach(UUID -> Objects.requireNonNull(Bukkit.getPlayer(UUID)).sendMessage(messages));
    }

    void checkInvitations() {
        invitations.forEach((UUID,Long) -> {
            if (Long < System.currentTimeMillis()) {
                Objects.requireNonNull(Bukkit.getPlayer(UUID))
                        .sendMessage(message.getMessage("Team_Invite_TimeOut"));
                invitations.remove(UUID);
            }
            if (isMember(UUID)) {
                //TODO
                Objects.requireNonNull(Bukkit.getPlayer(UUID))
                        .sendMessage(message.getMessage("Team_Invite_TimeOut"));
                invitations.remove(UUID);
            }
        });
    }

    void addMember(UUID player) {
        members.add(player);
        setScoreBoard(player);
    }

    void setScoreBoard(UUID player) {
        Scoreboard board = Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard();
        Objective obj = board.registerNewObjective(name,"dummy",ChatColor.GREEN + "隊伍名稱" + ChatColor.YELLOW + name);
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        org.bukkit.scoreboard.Team teamScoreBoard = board.registerNewTeam("teamScoreBoard");
        teamScoreBoard.addEntry("隊長：");
        teamScoreBoard.setSuffix(Bukkit.getPlayer(leader).getName());
        teamScoreBoard.setPrefix("");

        obj.getScore("隊長：").setScore(0);
    }
}