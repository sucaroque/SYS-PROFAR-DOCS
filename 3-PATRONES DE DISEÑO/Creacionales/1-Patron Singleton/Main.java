void main(){
    IO.println("====== CLASE SINGLETON =======");
    Singleton sing1 = Singleton.getInstance();
    Singleton sing2 = Singleton.getInstance();
    sing1.mostrarMensaje();
    sing2.mostrarMensaje();
    if (sing1 == sing2) {
        IO.println("¡Ambas variables apuntan a la misma y única instancia!");
        IO.println(sing1);
        IO.println(sing2);
    }
}