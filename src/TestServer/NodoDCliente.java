public class NodoDCliente {

    String cliente;
    int edad;
    char estado_civil;
    NodoDCliente nxt;

    public NodoDCliente(String c, int e, char es)
    {
        cliente  = c;
        edad = e;
        nxt = null;
        estado_civil = es;
    }
}
