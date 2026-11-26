
package com.musicplaylist.service.strategy.impl;

import com.musicplaylist.model.Song;
import com.musicplaylist.service.strategy.ShuffleStrategy;

import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component("smartShuffle")
public class SmartShuffleStrategy implements ShuffleStrategy {

    @Override
    public List<Song> shuffle(List<Song> songs) {
        List<Song> remaining = new ArrayList<>(songs);
        List<Song> result = new ArrayList<>();

        Collections.shuffle(remaining);

        while (!remaining.isEmpty()) {
            String lastArtist = result.isEmpty()
                    ? null
                    : result.get(result.size() - 1).getArtist();

            int selectedIndex = -1;

            for (int i = 0; i < remaining.size(); i++) {
                if (lastArtist == null ||
                        !remaining.get(i).getArtist()
                                .equalsIgnoreCase(lastArtist)) {
                    selectedIndex = i;
                    break;
                }
            }

            // Si aucune autre artiste n'est disponible,
            // on prend la première chanson restante.
            if (selectedIndex == -1) {
                selectedIndex = 0;
            }

            result.add(remaining.remove(selectedIndex));
        }

        return result;
    }
}
