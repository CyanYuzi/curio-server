package com.cyan.curioserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.nio.file.Path;

@Data
@AllArgsConstructor
public class DownloadFile {
    private String name;
    private Path path;

}
