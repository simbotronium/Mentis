package com.example.mentis.business.data;

import java.util.ArrayList;
import java.util.List;

public class Member {
    private long id;
    private boolean download = false;
    private List<Examination> examinations = new ArrayList<>();

    public Member(long id) {
        this.id = id;
    }

    public Member(long id, List<Examination> examinations) {
        this.id = id;
        this.examinations = examinations;
    }

    public List<Examination> getExaminations() {
        return examinations;
    }

    public void addExamination(Examination e) {
        examinations.add(e);
    }

    public boolean isDownload() {
        return download;
    }

    public void setDownload(boolean download) {
        this.download = download;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
