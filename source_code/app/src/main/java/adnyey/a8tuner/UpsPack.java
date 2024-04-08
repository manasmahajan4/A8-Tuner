package adnyey.a8tuner;

/**
 * Created by Mahajan-PC on 2017-11-02.
 */

public class UpsPack {

    int CLS;
    String name;
    double speed_coef;
    double per_accel, per_top, per_hand, per_nitro;

    double accel_stock, accel_MAX, accel_PRO, accel_tk;
    double top_stock, top_MAX, top_PRO, top_tk;
    double hand_stock, hand_MAX, hand_PRO, hand_tk;
    double nitro_stock, nitro_MAX, nitro_PRO, nitro_tk;
    double rank_stock, rank_MAX, rank_PRO, rank_tk;

    UpsPack(){}

    UpsPack(
            int CLS, String name,
            double speed_coef,
            double per_accel,double per_top,double per_hand,double per_nitro,

                    double accel_stock,double accel_MAX,double accel_PRO,double accel_tk,
                    double top_stock,double top_MAX,double top_PRO,double top_tk,
                    double hand_stock,double hand_MAX,double hand_PRO,double hand_tk,
                    double nitro_stock,double nitro_MAX,double nitro_PRO,double nitro_tk,
                    double rank_stock,double rank_MAX,double rank_PRO,double rank_tk


    ) {
        this.CLS = CLS;
        this.name= name;
        this.speed_coef = speed_coef;
        this.per_accel = per_accel; this.per_top = per_top; this.per_hand = per_hand; this.per_nitro = per_nitro;

        this.accel_stock = accel_stock; this.accel_MAX = accel_MAX; this.accel_PRO = accel_PRO; this.accel_tk = accel_tk;
        this.top_stock = top_stock; this.top_MAX = top_MAX; this.top_PRO = top_PRO; this.top_tk = top_tk;
        this.hand_stock = hand_stock; this.hand_MAX = hand_MAX; this.hand_PRO = hand_PRO; this.hand_tk = hand_tk;
        this.nitro_stock = nitro_stock; this.nitro_MAX = nitro_MAX; this.nitro_PRO = nitro_PRO; this.nitro_tk = nitro_tk;
        this.rank_stock = rank_stock; this.rank_MAX = rank_MAX; this.rank_PRO = rank_PRO; this.rank_tk = rank_tk;
    }

    public int getCLS() {
        return CLS;
    }

    public void setCLS(int CLS) {
        this.CLS = CLS;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSpeed_coef() {
        return speed_coef;
    }

    public void setSpeed_coef(double speed_coef) {
        this.speed_coef = speed_coef;
    }

    public double getPer_accel() {
        return per_accel;
    }

    public void setPer_accel(double per_accel) {
        this.per_accel = per_accel;
    }

    public double getPer_top() {
        return per_top;
    }

    public void setPer_top(double per_top) {
        this.per_top = per_top;
    }

    public double getPer_hand() {
        return per_hand;
    }

    public void setPer_hand(double per_hand) {
        this.per_hand = per_hand;
    }

    public double getPer_nitro() {
        return per_nitro;
    }

    public void setPer_nitro(double per_nitro) {
        this.per_nitro = per_nitro;
    }

    public double getAccel_stock() {
        return accel_stock;
    }

    public void setAccel_stock(double accel_stock) {
        this.accel_stock = accel_stock;
    }

    public double getAccel_MAX() {
        return accel_MAX;
    }

    public void setAccel_MAX(double accel_MAX) {
        this.accel_MAX = accel_MAX;
    }

    public double getAccel_PRO() {
        return accel_PRO;
    }

    public void setAccel_PRO(double accel_PRO) {
        this.accel_PRO = accel_PRO;
    }

    public double getAccel_tk() {
        return accel_tk;
    }

    public void setAccel_tk(double accel_tk) {
        this.accel_tk = accel_tk;
    }

    public double getTop_stock() {
        return top_stock;
    }

    public void setTop_stock(double top_stock) {
        this.top_stock = top_stock;
    }

    public double getTop_MAX() {
        return top_MAX;
    }

    public void setTop_MAX(double top_MAX) {
        this.top_MAX = top_MAX;
    }

    public double getTop_PRO() {
        return top_PRO;
    }

    public void setTop_PRO(double top_PRO) {
        this.top_PRO = top_PRO;
    }

    public double getTop_tk() {
        return top_tk;
    }

    public void setTop_tk(double top_tk) {
        this.top_tk = top_tk;
    }

    public double getHand_stock() {
        return hand_stock;
    }

    public void setHand_stock(double hand_stock) {
        this.hand_stock = hand_stock;
    }

    public double getHand_MAX() {
        return hand_MAX;
    }

    public void setHand_MAX(double hand_MAX) {
        this.hand_MAX = hand_MAX;
    }

    public double getHand_PRO() {
        return hand_PRO;
    }

    public void setHand_PRO(double hand_PRO) {
        this.hand_PRO = hand_PRO;
    }

    public double getHand_tk() {
        return hand_tk;
    }

    public void setHand_tk(double hand_tk) {
        this.hand_tk = hand_tk;
    }

    public double getNitro_stock() {
        return nitro_stock;
    }

    public void setNitro_stock(double nitro_stock) {
        this.nitro_stock = nitro_stock;
    }

    public double getNitro_MAX() {
        return nitro_MAX;
    }

    public void setNitro_MAX(double nitro_MAX) {
        this.nitro_MAX = nitro_MAX;
    }

    public double getNitro_PRO() {
        return nitro_PRO;
    }

    public void setNitro_PRO(double nitro_PRO) {
        this.nitro_PRO = nitro_PRO;
    }

    public double getNitro_tk() {
        return nitro_tk;
    }

    public void setNitro_tk(double nitro_tk) {
        this.nitro_tk = nitro_tk;
    }

    public double getRank_stock() {
        return rank_stock;
    }

    public void setRank_stock(double rank_stock) {
        this.rank_stock = rank_stock;
    }

    public double getRank_MAX() {
        return rank_MAX;
    }

    public void setRank_MAX(double rank_MAX) {
        this.rank_MAX = rank_MAX;
    }

    public double getRank_PRO() {
        return rank_PRO;
    }

    public void setRank_PRO(double rank_PRO) {
        this.rank_PRO = rank_PRO;
    }

    public double getRank_tk() {
        return rank_tk;
    }

    public void setRank_tk(double rank_tk) {
        this.rank_tk = rank_tk;
    }
}
