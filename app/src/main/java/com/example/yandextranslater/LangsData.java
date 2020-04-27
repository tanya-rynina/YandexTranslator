package com.example.yandextranslater;

import java.lang.reflect.Executable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class LangsData {

    private Exception exception;
    private List<String> dirs;
    private Map<String, String> langsMap;
    private Map<String,String> reverseMap;

    public Map<String, String> getReverseMap() {
        return reverseMap;
    }

    public void setReverseMap(Map<String, String> reverseMap) {
        this.reverseMap = reverseMap;
    }

    public Map<String, String> getLangsMap() {
        return langsMap;
    }

    public void setLangsMap(Map<String, String> langsMap) {
        this.langsMap = langsMap;
    }

    public List<String> getDirs() {
        return dirs;
    }

    public void setDirs(List<String> dirs) {
        this.dirs = dirs;
    }

    public boolean hasError() {
        return exception != null;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

//    public List<String> getFilteredLangs() {
//        Set<String> dirsTree = new TreeSet<>();
//
//        for (String dir : dirs) {
//            String srcLang = dir.split("-")[0];
//            String langName = langsMap.get(srcLang);
//            dirsTree.add(langName);
//
//        }
//        return new ArrayList<>(dirsTree);
//    }
}
