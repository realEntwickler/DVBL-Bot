/*
 * Copyright notice
 * Copyright (c) Nils Körting-Eberhardt 2021
 * Created: 10.02.2021 @ 11:39:38
 *
 * All contents of this source code are protected by copyright. The copyright is owned by Nils Körting-Eberhardt, unless explicitly stated otherwise. All rights reserved.
 *
 * LeaveCommand.java is part of the DVBL-Bot which is licensed under the Attribution-NonCommercial-ShareAlike 4.0 International (CC BY-NC-SA 4.0) license.
 */

package real.entwickler.dvblbot.music.commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.managers.AudioManager;
import real.entwickler.dvblbot.Bot;
import real.entwickler.dvblbot.utils.ICommand;

public class LeaveCommand extends ICommand {
    public LeaveCommand(String name, String usage, String description, String... roles) {
        super(name, description, roles);
    }

    @Override
    public void onCommand(Member commandSender, TextChannel textChannel, Message message, String[] args) {
        if (args.length == 1) {
            GuildVoiceState gvs;
            if ((gvs = commandSender.getVoiceState()) != null) {
                VoiceChannel vc;
                if ((vc = gvs.getChannel()) != null) {
                    Guild g = Bot.getInstance().getDVBL();
                    AudioTrack audioTrack = Bot.getInstance().getMusicController().getPlayer(g).getPlayingTrack();
                    AudioManager manager = vc.getGuild().getAudioManager();

                    if(manager.isConnected()) {
                        Bot.getInstance().getMusicController().getManager(g).purgeQueue();
                        Bot.getInstance().getMusicController().getPlayer(g).stopTrack();
                        manager.closeAudioConnection();
                        message.addReaction("U+1F44B").queue();
                    } else {
                        Bot.getInstance().getMessageManager().printBotErrorVoiceChannel(commandSender, textChannel);
                    }
                } else {
                    Bot.getInstance().getMessageManager().printErrorVoiceChannel(commandSender, textChannel);
                }
            } else {
                Bot.getInstance().getMessageManager().printErrorVoiceChannel(commandSender, textChannel);
            }
        } else {
            Bot.getInstance().getMessageManager().printErrorStopCommand(commandSender, textChannel);
        }
    }
}

