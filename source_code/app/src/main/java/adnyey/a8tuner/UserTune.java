package adnyey.a8tuner;

import android.support.v7.widget.RecyclerView;

import java.io.Serializable;

/**
 * Created by Mahajan-PC on 2017-11-03.
 */

public class UserTune implements Serializable {

    int id, rank, accel, top, hand, nitro, tires, susp, drive, exha, uniq;
    double speed, tk;

    public UserTune(int id, int rank, int accel, int top, int hand, int nitro, int tires, int susp, int drive, int exha, double speed, double tk, int uniq) {
        this.id = id;
        this.rank = rank;
        this.accel = accel;
        this.top = top;
        this.hand = hand;
        this.nitro = nitro;
        this.tires = tires;
        this.susp = susp;
        this.drive = drive;
        this.exha = exha;
        this.speed = speed;
        this.tk = tk;
        this.uniq = uniq;
    }

    public int getUniq() {
        return uniq;
    }

    public double getTk() {
        return tk;
    }

    public void setTk(double tk) {
        this.tk = tk;
    }

    public void setUniq(int uniq) {
        this.uniq = uniq;
    }

    public UserTune(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getAccel() {
        return accel;
    }

    public void setAccel(int accel) {
        this.accel = accel;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getHand() {
        return hand;
    }

    public void setHand(int hand) {
        this.hand = hand;
    }

    public int getNitro() {
        return nitro;
    }

    public void setNitro(int nitro) {
        this.nitro = nitro;
    }

    public int getTires() {
        return tires;
    }

    public void setTires(int tires) {
        this.tires = tires;
    }

    public int getSusp() {
        return susp;
    }

    public void setSusp(int susp) {
        this.susp = susp;
    }

    public int getDrive() {
        return drive;
    }

    public void setDrive(int drive) {
        this.drive = drive;
    }

    public int getExha() {
        return exha;
    }

    public void setExha(int exha) {
        this.exha = exha;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
