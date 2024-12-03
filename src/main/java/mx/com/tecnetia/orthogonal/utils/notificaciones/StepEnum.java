package mx.com.tecnetia.orthogonal.utils.notificaciones;

public enum StepEnum {
    RECICLAJE_MENSAJE(1),
    RECICLAJE_TERMINO(2);

    private int step;

    StepEnum(int step){
        this.step = step;
    }
    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }


}
