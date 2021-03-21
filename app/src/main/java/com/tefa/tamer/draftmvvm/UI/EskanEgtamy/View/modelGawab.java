package com.tefa.tamer.draftmvvm.UI.EskanEgtamy.View;

import com.google.firebase.Timestamp;

import java.net.URI;

public class modelGawab {
    private String title;
    private String date;
    private String pdfUri;
    private String number;
    private String importSide;
    private String exportSide;
    private String userName;
    private String userId;
    private Timestamp timeAdded;






    public modelGawab() {
    }

    public modelGawab(String title, String date, String pdfUri, String number, String importSide, String exportSide, String userName, String userId, Timestamp timeAdded) {
        this.title = title;
        this.date = date;
        this.pdfUri = pdfUri;
        this.number = number;
        this.importSide = importSide;
        this.exportSide = exportSide;
        this.userName = userName;
        this.userId = userId;
        this.timeAdded = timeAdded;
    }

    public Timestamp getTimeAdded() {
        return timeAdded;
    }

    public void setTimeAdded(Timestamp timeAdded) {
        this.timeAdded = timeAdded;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getImportSide() {
        return importSide;
    }

    public void setImportSide(String importSide) {
        this.importSide = importSide;
    }

    public String getExportSide() {
        return exportSide;
    }

    public void setExportSide(String exportSide) {
        this.exportSide = exportSide;
    }

    public String getPdfUri() {
        return pdfUri;
    }

    public void setPdfUri(String pdfUri) {
        this.pdfUri = pdfUri;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
