import javax.swing.*;

public class Pral {

    public static void main(String args[])
    {
        ListaCliente listaClientes = new ListaCliente();

        listaClientes.InsertarOrdernadoAsc(new NodoDCliente("Ana",18,'S'));
        listaClientes.InsertarOrdernadoAsc(new NodoDCliente("Pedro",56,'V'));
        listaClientes.InsertarOrdernadoAsc(new NodoDCliente("Maria",60,'D'));
        listaClientes.InsertarOrdernadoAsc(new NodoDCliente("Rebeca",50,'V'));
        listaClientes.InsertarOrdernadoAsc(new NodoDCliente("Cesar",54,'V'));
        listaClientes.InsertarOrdernadoAsc(new NodoDCliente("Laura",28,'C'));

        JOptionPane.showMessageDialog(null,listaClientes.print()
        +"\n\n(Cantidad de clientes) : " + listaClientes.contar());

        ListaCliente main = new ListaCliente();
        main.Ins_Inicio(new NodoDCliente("Ramiro",18,'S'));
        main.Ins_Final(new NodoDCliente("Rock",43,'D'));
        main.Ins_Inicio(new NodoDCliente("Zomer",66,'S'));

        JOptionPane.showMessageDialog(null,main.print()
                +"\n\n(Cantidad de clientes) : " + main.contar());

        main.Ins_Pos(1,new NodoDCliente("Rotzo",45,'S'));

        JOptionPane.showMessageDialog(null,main.print()
                +"\n\n(Cantidad de clientes) : " + main.contar());

    }
}
