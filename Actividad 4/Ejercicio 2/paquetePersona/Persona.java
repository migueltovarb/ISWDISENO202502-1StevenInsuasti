package paquetePersona;

public class Persona {
    public static final char SEXO_DEF = 'H';
    public static final int BAJO_PESO = -1;
    public static final int PESO_IDEAL = 0;
    public static final int SOBREPESO = 1;
    
    //Atributos (privados)
    private String nombre;
    private int edad;
    private String DNI;
    private char sexo;
    private double peso;
    private double altura; 

    public Persona() {
        this.nombre = "";
        this.edad = 0;
        this.sexo = SEXO_DEF;
        this.peso = 0.0;
        this.altura = 0.0;
        this.DNI = generarDNI();
    }
    
    public Persona(String nombre, int edad, char sexo) {
    	this.nombre = nombre;
    	this.edad = edad;
    	this.sexo = comprobarSexo(sexo);
    	this.peso = 0;
    	this.altura = 0;
    	this.DNI = generarDNI();
    	
    }
    
    public Persona(String nombre, int edad, char sexo, double peso, double altura) {
        this.nombre = nombre;
        this.edad = edad;
        this.sexo = comprobarSexo(sexo);
        this.peso = peso;
        this.altura = altura;
        this.DNI = generarDNI();
    }
    

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getDNI() {
		return DNI;
	}

	public void setDNI(String dNI) {
		DNI = dNI;
	}

	public char getSexo() {
		return sexo;
	}

	public void setSexo(char sexo) {
		this.sexo = sexo;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public double getAltura() {
		return altura;
	}

	public void setAltura(double altura) {
		this.altura = altura;
	}    
    
    public int calcularIMC() {
        double imc = peso / (altura * altura);
        if (imc < 20) {
            return BAJO_PESO;
        } else if (imc >= 20 && imc <= 25) {
            return PESO_IDEAL;
        } else {
            return SOBREPESO;
        }
    }
    public boolean esMayorDeEdad() {
    	return edad>=18;    	
    }
    private char comprobarSexo(char sexo) {
        if (sexo == 'H' || sexo == 'M') {
            return sexo;
        } else {
            return SEXO_DEF;
        }
    }
   
    private String generarDNI() {
        int numero = (int) (Math.random() * 100000000); // 8 cifras
        char letra = calcularLetraDNI(numero);
        return String.valueOf(numero) + letra;
    }

    private char calcularLetraDNI(int numero) {
        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
        return letras.charAt(numero % 23);
    }
    
    @Override
    public String toString() {
        return "Persona de " +
                "nombre '" + nombre + '\'' +
                ", con una edad de " + edad +
                "  su DNI es '" + DNI + '\'' +
                ",  el sexo es " + sexo +
                ", tiene un peso de " + peso +
                " y una altura de " + altura;
    }
	
	

}

