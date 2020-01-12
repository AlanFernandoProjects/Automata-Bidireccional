package automata;

public class Automata {

    boolean resultado;
    int numEstados;
    int[] Q;
    int tamanioAlfabeto;
    int[][] Delta;
    int estadoInicial;
    int cantEstadosAceptacion;
    int[] EstadoAceptacion;
    int[] intCadenaE;
    int[] Sigma;

    public static void main(String[] args) {

        Automata au = new Automata();
        au.introducirValores("C:\\Users\\ALANZON\\Desktop\\AutomataBidireccional\\src\\automata\\MiConfiguracion.txt", "C:\\Users\\ALANZON\\Desktop\\AutomataBidireccional\\src\\automata\\cadena.txt");
        au.evaluar();

        if (au.resultado) {
            System.out.println("");
            System.out.println("Cadena Aceptada");
        } else {
            System.out.println("");
            System.out.println("Cadena Rechazada");
        }
    }

    String contCadena;

    public void introducirValores(String Archivo, String Cadena) {

        archivo a = new archivo();
        String contArchivo = a.leerArchivo(Archivo);
        int[] arregloArchivo = new int[contArchivo.length()];

        int r = 0;
        for (char valor : contArchivo.toCharArray()) {
            arregloArchivo[r] = Integer.valueOf(valor) - 48;
            r++;
        }

        System.out.print("Elementos de configuracion: ");
        for (int i = 0; i < arregloArchivo.length; i++) {
            System.out.print(arregloArchivo[i] + ",");
        }

        numEstados = arregloArchivo[0];
        Q = new int[numEstados];

        for (int i = 0; i < numEstados; i++) {

            Q[i] = arregloArchivo[i + 1];
        }

        System.out.println("");
        System.out.print("Los estados son: ");
        for (int i = 0; i < numEstados; i++) {

            System.out.print(Q[i] + ",");
        }

        //obtener los valores de la matriz de identidad
        tamanioAlfabeto = arregloArchivo[numEstados + 1];
        Delta = new int[numEstados][tamanioAlfabeto * 2];
        int temp = 0;
        for (int i = 0; i < numEstados; i++) {
            for (int j = 0; j < tamanioAlfabeto * 2; j++) {
                int primerValor = Q.length + 2;
                Delta[i][j] = arregloArchivo[primerValor + temp];
                temp++;
            }
        }
        
        System.out.println("");
        System.out.print("La matriz de transiciones es: ");

        for (int i = 0; i < numEstados; i++) {
            for (int j = 0; j < tamanioAlfabeto * 2; j++) {
                System.out.print(Delta[i][j] + ",");
            }
        }

        estadoInicial = arregloArchivo[Q.length + 2 + temp];
        System.out.println("");
        System.out.println("El estado inicial es:" + estadoInicial);

        cantEstadosAceptacion = arregloArchivo[Q.length + 2 + temp + 1];
        System.out.println("La cant de estados de aceptacion son: " + cantEstadosAceptacion);

        EstadoAceptacion = new int[cantEstadosAceptacion];
        for (int i = 0; i < cantEstadosAceptacion; i++) {
            EstadoAceptacion[i] = arregloArchivo[Q.length + 4 + temp + i];
        }

        System.out.print("");
        System.out.print("Los estados de aceptacion son: ");
        for (int i = 0; i < cantEstadosAceptacion; i++) {
            System.out.print(EstadoAceptacion[i] + ",");
        }

        Sigma = new int[tamanioAlfabeto];

        for (int i = 0; i < tamanioAlfabeto; i++) {
            Sigma[i] = arregloArchivo[Q.length + EstadoAceptacion.length + 4 + temp + i];
        }
        
        System.out.println("");
        System.out.print("Los valores de sigma son: ");
        for (int i = 0; i < tamanioAlfabeto; i++) {
            System.out.print(Sigma[i] + ",");
        }

        contCadena = a.leerArchivo(Cadena);

        intCadenaE = new int[contCadena.length()];
        int i = 0;

        for (char valor : contCadena.toCharArray()) {
            intCadenaE[i] = Integer.valueOf(valor) - 48;
            i++;
        }
        System.out.println("");
        System.out.print("Los valores de entrada son: ");
        for (i = 0; i < intCadenaE.length; i++) {
            System.out.print(intCadenaE[i]);
        }
    }

    int direccion = 0; //20 en en codigo ASCII ES IGUAL A LA LETRA "D"
    int indice = 0;
    
    public boolean evaluar() {
        int estadoActual = estadoInicial;
        int siguienteValor = 0;
        this.direccion= 20;

        while (siguienteValor!=43 && siguienteValor!=45) 
        {         
            if (direccion == 20) 
            {      
                indice++;
                siguienteValor = siguientevalor();
                estadoActual = transicion(estadoActual, siguienteValor,direccion);   
            }
            if(direccion==25)
            {
                indice--;
                siguienteValor = siguientevalor();
                estadoActual = transicion(estadoActual, siguienteValor, direccion);   
            }
        }

        for (int j = 0; j < cantEstadosAceptacion; j++) {
            if (estadoActual == EstadoAceptacion[j]) {
                resultado = true;
                return resultado;
            } else {
                resultado = false;
            }
        }
        return resultado;
    }

    public int siguientevalor() {
        int valorCadenaA = 0;
            valorCadenaA = intCadenaE[indice];
        return valorCadenaA;
    }

    
    public int transicion(int estadoActual, int siguienteValor,int direccion2) {
        
        int estadoActualQ = 0;
        int elementoEntradaA = 0;

        for (int i = 0; i < Q.length; i++) {
            if (estadoActual == Q[i]) {
                estadoActualQ = i;
            }
        }
        
        for (int j = 0; j < Sigma.length; j++) {
            if (siguienteValor == Sigma[j]) {
                elementoEntradaA = j;
            }
        }
       
       //Calcula la posicion en que posicion esta el elemento del estado a transicionar
       if(elementoEntradaA>0){
           if(elementoEntradaA==1){
            elementoEntradaA=2;
            }
           else if(elementoEntradaA==2){
            elementoEntradaA = 4;
            }
        }
       
        estadoActual = Delta[estadoActualQ][elementoEntradaA];
        direccion2 = Delta[estadoActualQ][elementoEntradaA+1];
        this.direccion = direccion2;
        
        System.out.println();
        System.out.print(this.direccion);
        
        return estadoActual;
    }
}
