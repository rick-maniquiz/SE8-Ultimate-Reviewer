package org.example.exammodels;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public interface Savable {
    public void saveProgress() throws IOException;
}
