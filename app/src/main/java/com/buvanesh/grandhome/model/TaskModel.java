package com.buvanesh.grandhome.model;

import java.util.Date;

/**
 * Created by buvaneshkumar_p on 11/13/2017.
 */

public class TaskModel {

    private int id;
    private String name;
    private String trainNo;
    private String amount;
    private String date;
    private String message;
    private Byte[] attchment;
    private boolean isBanking;
    private boolean isCredit;
    private boolean isTrain;
    private String boardingDate;
    private String boardingTime;
    private String coachNo;
    private String seatNo;
    private String boarding;
    private String pnrNo;
    private boolean status;

    public String getTrainNo() {
        return trainNo;
    }

    public void setTrainNo(String trainNo) {
        this.trainNo = trainNo;
    }

    public String getCoachNo() {
        return coachNo;
    }

    public void setCoachNo(String coachNo) {
        this.coachNo = coachNo;
    }

    public boolean isTrain() {
        return isTrain;
    }

    public void setTrain(boolean train) {
        isTrain = train;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public boolean isBanking() {
        return isBanking;
    }

    public void setBanking(boolean banking) {
        isBanking = banking;
    }

    public boolean isCredit() {
        return isCredit;
    }

    public void setCredit(boolean credit) {
        isCredit = credit;
    }

    public String getBoardingDate() {
        return boardingDate;
    }

    public void setBoardingDate(String boardingDate) {
        this.boardingDate = boardingDate;
    }

    public String getBoardingTime() {
        return boardingTime;
    }

    public void setBoardingTime(String boardingTime) {
        this.boardingTime = boardingTime;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public String getBoarding() {
        return boarding;
    }

    public void setBoarding(String boarding) {
        this.boarding = boarding;
    }

    public String getPnrNo() {
        return pnrNo;
    }

    public void setPnrNo(String pnrNo) {
        this.pnrNo = pnrNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Byte[] getAttchment() {
        return attchment;
    }

    public void setAttchment(Byte[] attchment) {
        this.attchment = attchment;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
