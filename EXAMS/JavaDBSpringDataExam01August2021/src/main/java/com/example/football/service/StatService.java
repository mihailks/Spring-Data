package com.example.football.service;

import java.io.IOException;

public interface StatService {
    boolean areImported();

    String readStatsFileContent() throws IOException;

    String importStats();

}
