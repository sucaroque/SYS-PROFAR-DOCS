public class Singleton {
    /*
    3 partes la clase singleton
    1. tiene un constructor privado
    2. Una variable estática
    3. Un metodo publico que sea estatico para acceder al constructor
     */
    private static Singleton instancia;

    private Singleton(){
        IO.println("Instancia global del objeto singleton");
    }

    public static Singleton getInstance(){
        if (instancia == null){
            instancia = new Singleton();
        }
        return instancia;
    }

    public void mostrarMensaje() {
        IO.println("Clase Singleton activo");
    }
}