package mx.com.tecnetia.orthogonal.utils.quioscos;

public enum TipoPicEnum {

    ARDUINO(1),
    PLC(2);

    private int tipoPic;

    TipoPicEnum(int tipoPic){
        this.tipoPic = tipoPic;
    }
    public int getTipoPic() {
        return tipoPic;
    }

    public void setTipoPic(int tipoPic) {
        this.tipoPic = tipoPic;
    }

}
