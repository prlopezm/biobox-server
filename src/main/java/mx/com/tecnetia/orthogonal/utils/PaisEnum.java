package mx.com.tecnetia.orthogonal.utils;

public enum PaisEnum {
	
    MEXICO("+52");

    private String codigo;

    PaisEnum(String codigo){
        this.codigo = codigo;
    }
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

}
