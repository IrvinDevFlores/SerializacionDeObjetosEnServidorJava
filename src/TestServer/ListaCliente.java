import sun.security.util.Length;

import javax.swing.*;

public class ListaCliente {

    NodoDCliente primero;
    NodoDCliente ultimo;
    int Length ;

    public ListaCliente()
    {
        Length = 0;
        primero = ultimo = null;
    }

    void Ins_Final(NodoDCliente n) {
        if (EstaVacio()) {
            Ins_Inicio(n);
            Length++;
        } else {
            ultimo.nxt = n;
            ultimo = n;
            Length++;
        }
    }

    boolean EstaVacio() {
        return (primero == null && ultimo == null) ? true : false;
    }

    void Ins_Inicio(NodoDCliente n) {
        if (EstaVacio()) {
            primero = ultimo = n;
            Length++;
        } else {
            n.nxt = primero;
            primero = n;
            Length++;
        }
    }

    int contar() {
        return Length;
    }

    String print() {
        NodoDCliente temp = primero;
        String lista = "";
        int i = 0;
        while (temp != null) {
            lista +=  "Informacion: \n" + (i + 1) + ". Cliente: " + temp.cliente + "\nEdad: " + temp.edad
                    + "\nEstado civil: " + temp.estado_civil +"\n\n";
            temp = temp.nxt;
            i++;
        }
        return lista;
    }

    void Ins_Pos(int pos, NodoDCliente n) {
        NodoDCliente temp = primero;

        if(pos==0)
        {
            JOptionPane.showMessageDialog(null,"Elija la posicion literal no el indice");
        }

        if (EstaVacio()) {
            Ins_Inicio(n);

        } else if (Length == 1) {
            Ins_Final(n);

        }
        else if(pos == 1)
        {
            n.nxt = primero.nxt;
            primero = n;
        }
        else {

            int i = 0;
            NodoDCliente actual = primero;
            NodoDCliente anterior = null;

            while (i < pos) {
                anterior = actual;
                actual = actual.nxt;
                if (actual == null) {
                    break;
                }
                n.nxt = actual;
                anterior.nxt = n;
                i++;
            }
        }
    }


    void InsertarOrdernadoAsc(NodoDCliente n) {
        NodoDCliente temp = primero;
        if (Length == 0) {
            Ins_Inicio(n);
        }
        else if (Length == 1) {
            if(temp.edad < n.edad)
            {
                Ins_Final(n);
            }else
            {
                Ins_Inicio(n);
            }
        }
        else {

            int edadEnNodo = n.edad;
            if (edadEnNodo < primero.edad) {
                Ins_Inicio(n);

            } else if (ultimo.edad <= edadEnNodo) {
                Ins_Final(n);
            }
            else
                {
                NodoDCliente actual = primero;

                while (actual.nxt != null && actual.nxt.edad < edadEnNodo) {
                    actual = actual.nxt;

                }
                n.nxt = actual.nxt;
                actual.nxt = n;
                Length++;
            }
        }
    }
}
