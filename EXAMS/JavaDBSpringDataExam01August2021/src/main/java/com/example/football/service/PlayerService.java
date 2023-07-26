package com.example.football.service;

import java.io.IOException;

public interface PlayerService {
    boolean areImported();

    String readPlayersFileContent() throws IOException;

    String importPlayers();

    String exportBestPlayers();
}
