package com.hotel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/**
 * Gestió de reserves d'un hotel.
 */
public class App {

    // --------- CONSTANTS I VARIABLES GLOBALS ---------

    // Tipus d'habitació
    public static final String TIPUS_ESTANDARD = "Estàndard";
    public static final String TIPUS_SUITE = "Suite";
    public static final String TIPUS_DELUXE = "Deluxe";

    // Serveis addicionals
    public static final String SERVEI_ESMORZAR = "Esmorzar";
    public static final String SERVEI_GIMNAS = "Gimnàs";
    public static final String SERVEI_SPA = "Spa";
    public static final String SERVEI_PISCINA = "Piscina";

    // Capacitat inicial
    public static final int CAPACITAT_ESTANDARD = 30;
    public static final int CAPACITAT_SUITE = 20;
    public static final int CAPACITAT_DELUXE = 10;

    // IVA
    public static final float IVA = 0.21f;

    // Scanner únic
    public static Scanner sc = new Scanner(System.in);

    // HashMaps de consulta
    public static HashMap<String, Float> preusHabitacions = new HashMap<String, Float>();
    public static HashMap<String, Integer> capacitatInicial = new HashMap<String, Integer>();
    public static HashMap<String, Float> preusServeis = new HashMap<String, Float>();

    // HashMaps dinàmics
    public static HashMap<String, Integer> disponibilitatHabitacions = new HashMap<String, Integer>();
    public static HashMap<Integer, ArrayList<String>> reserves = new HashMap<Integer, ArrayList<String>>();

    // Generador de nombres aleatoris per als codis de reserva
    public static Random random = new Random();

    // --------- MÈTODE MAIN ---------

    /**
     * Mètode principal. Mostra el menú en un bucle i gestiona l'opció triada
     * fins que l'usuari decideix eixir.
     */
    public static void main(String[] args) {
        inicialitzarPreus();

        int opcio = 0;
        do {
            mostrarMenu();
            opcio = llegirEnter("Seleccione una opció: ");
            gestionarOpcio(opcio);
        } while (opcio != 6);

        System.out.println("Eixint del sistema... Gràcies per utilitzar el gestor de reserves!");
    }

    // --------- MÈTODES DEMANATS ---------

    /**
     * Configura els preus de les habitacions, serveis addicionals i
     * les capacitats inicials en els HashMaps corresponents.
     */
    public static void inicialitzarPreus() {
        // Preus habitacions
        preusHabitacions.put(TIPUS_ESTANDARD, 50f);
        preusHabitacions.put(TIPUS_SUITE, 100f);
        preusHabitacions.put(TIPUS_DELUXE, 150f);

        // Capacitats inicials
        capacitatInicial.put(TIPUS_ESTANDARD, CAPACITAT_ESTANDARD);
        capacitatInicial.put(TIPUS_SUITE, CAPACITAT_SUITE);
        capacitatInicial.put(TIPUS_DELUXE, CAPACITAT_DELUXE);

        // Disponibilitat inicial (comença igual que la capacitat)
        disponibilitatHabitacions.put(TIPUS_ESTANDARD, CAPACITAT_ESTANDARD);
        disponibilitatHabitacions.put(TIPUS_SUITE, CAPACITAT_SUITE);
        disponibilitatHabitacions.put(TIPUS_DELUXE, CAPACITAT_DELUXE);

        // Preus serveis
        preusServeis.put(SERVEI_ESMORZAR, 10f);
        preusServeis.put(SERVEI_GIMNAS, 15f);
        preusServeis.put(SERVEI_SPA, 20f);
        preusServeis.put(SERVEI_PISCINA, 25f);
    }

    /**
     * Mostra el menú principal amb les opcions disponibles per a l'usuari.
     */
    public static void mostrarMenu() {
        System.out.println("\n===== MENÚ PRINCIPAL =====");
        System.out.println("1. Reservar una habitació");
        System.out.println("2. Alliberar una habitació");
        System.out.println("3. Consultar disponibilitat");
        System.out.println("4. Llistar reserves per tipus");
        System.out.println("5. Obtindre una reserva");
        System.out.println("6. Ixir");
    }

    /**
     * Processa l'opció seleccionada per l'usuari i crida el mètode corresponent.
     */
    public static void gestionarOpcio(int opcio) {
       //TODO:Elegim la opcio 1,2,3,4,5 cridant a les funcions corresponents.

       switch (opcio) {
        case 1: reservarHabitacio();
            
            break;
        case 2: alliberarHabitacio();
            
            break;
        case 3: consultarDisponibilitat();
            
            break;
        case 4: obtindreReservaPerTipus();
            
            break;
        case 5: obtindreReserva();
            
            break;                
       
        default:
            break;
       }
    }

    /**
     * Gestiona tot el procés de reserva: selecció del tipus d'habitació,
     * serveis addicionals, càlcul del preu total i generació del codi de reserva.
     */
    public static void reservarHabitacio() {
        //TODO:
        System.out.println("\n===== RESERVAR HABITACIÓ =====");
        //Retorna el string Estandar, Suite o Deluxe
        seleccionarTipusHabitacioDisponible();

        //Guarda el tipus de habitacio en la variable tipus
        String tipus = seleccionarTipusHabitacio(); 

        //Guarda la lista de servicis elegits en el metode seleccionar serveis
        //i els guarda en una llista(array): ["Esmorzar", "Spa", "Gimnas", "Piscina"]
        ArrayList<String> serveis = seleccionarServeis();

        //Guarda el preu total calculat en el metode calcularPreuTotal fent servir
        //la variable tipus(string) i la llista serveis(array)
        float preuTotal = calcularPreuTotal(tipus, serveis);

        //Guarda el codi aleatori generat en la funcio generarCodiReserva en una variable tipus int codi exemple: 111
        int codi = generarCodiReserva();

        //Array donde se guardara la informacion de la reserva
        ArrayList<String> infoReserva = new ArrayList<>();

        //Guarda el tipo de habitacion en el array infoReserva
        infoReserva.add(tipus);

        //Guarda el precio total en el array infoReserva
        infoReserva.add(String.valueOf(preuTotal));

        //Guarda los servicios seleccionados en el array infoReserva
        for (int i=0; i < serveis.size(); i++){
            infoReserva.add(serveis.get(i));
        }

        //Guarda el codigo único y la informacion de la reserva en el hashmap reserves
        reserves.put(codi, infoReserva);

        //Actualiza las habitaciones disponibles
        int disponibles = disponibilitatHabitacions.get(tipus);
        disponibles = disponibles - 1;
        disponibilitatHabitacions.put(tipus, disponibles);

    }

    /**
     * Pregunta a l'usuari un tipus d'habitació en format numèric i
     * retorna el nom del tipus.
     */
    public static String seleccionarTipusHabitacio() {
        //TODO:
        //Pregunta al usuario el tipus de habitacio (opcio) y la funcion devuelve el nombre de la habitacion
        int opcio = 0;
        System.out.println("\nSeleccione tipus d'habitació: ");
        opcio = sc.nextInt();
        switch (opcio) {
            case 1:
                System.out.println("Has seleccionat una habitació tipo: Estàndar");
                return TIPUS_ESTANDARD;
            case 2:
                System.out.println("Has seleccionat una habitació tipo: Suite");
                return TIPUS_SUITE;
            case 3:
                System.out.println("Has seleccionat una habitació tipo: Deluxe");
                return TIPUS_DELUXE;   
            default:
                break;
        }
        sc.close();
        return null;
    }

    /**
     * Mostra la disponibilitat i el preu de cada tipus d'habitació,
     * demana a l'usuari un tipus i només el retorna si encara hi ha
     * habitacions disponibles. En cas contrari, retorna null.
     */
    //Indica la cantidad de habitaciones disponibles y su precio
    public static String seleccionarTipusHabitacioDisponible() {
        System.out.println("\nTipus d'habitació disponibles:");
        //TODO:
        System.out.println("1. "+TIPUS_ESTANDARD+" "+disponibilitatHabitacions.get("Estàndard")+" disponibles - 50 $");
        System.out.println("2. "+TIPUS_SUITE+" "+disponibilitatHabitacions.get("Suite")+" disponibles - 100 $");
        System.out.println("3. "+TIPUS_DELUXE+" "+disponibilitatHabitacions.get("Deluxe")+" disponibles - 150 $");
        return null;
    }

    /**
     * Permet triar serveis addicionals (entre 0 i 4, sense repetir) i
     * els retorna en un ArrayList de String.
     */
    public static ArrayList<String> seleccionarServeis() {
        //TODO:
        //Array que guarda los servicios elegidos en una lista de palabras
        ArrayList<String> serveis = new ArrayList<>();
        int limit=0, tipusServei=0;
        String confirmacio="";

        System.out.println("\nServeis addicionals (0-4):");
        System.out.println("1. Esmorzar (10$)");
        System.out.println("2. Gimnàs (15$)");
        System.out.println("3. Spa (20$)");
        System.out.println("4. Piscina (25$)");

        sc.nextLine();

        //Pide al usuario si quiere un servicio y que eliga cual, no se puede repetir
        do{
            System.out.print("Vol afegir un servei? (s/n): ");
            confirmacio = sc.nextLine();

            if(confirmacio.equalsIgnoreCase("s")){

            System.out.println("Seleccione servei: ");
            tipusServei = sc.nextInt();
            sc.nextLine();

            switch (tipusServei) {
                case 1:
                    if(serveis.contains(SERVEI_ESMORZAR)){
                        System.out.println(SERVEI_ESMORZAR+" ya ha sigut seleccionat");
                        break;
                    }else{
                        serveis.add(SERVEI_ESMORZAR);
                        System.out.println("Servei afegit: "+ SERVEI_ESMORZAR);
                        limit++;
                        break;
                    }
                case 2:
                    if(serveis.contains(SERVEI_GIMNAS)){
                        System.out.println(SERVEI_GIMNAS+" ya ha sigut seleccionat");
                        break;
                    }else{
                        serveis.add(SERVEI_GIMNAS);
                        System.out.println("Servei afegit: "+ SERVEI_GIMNAS);
                        limit++;
                        break;
                    }

                case 3:
                    if(serveis.contains(SERVEI_SPA)){
                        System.out.println(SERVEI_SPA+" ya ha sigut seleccionat");
                        break;
                    }else{
                        serveis.add(SERVEI_SPA);
                        System.out.println("Servei afegit: "+ SERVEI_SPA);
                        limit++;
                        break;
                    }

                case 4:
                    if(serveis.contains(SERVEI_PISCINA)){
                        System.out.println(SERVEI_PISCINA+" ya ha sigut seleccionada");
                        break;
                    }else{
                        serveis.add(SERVEI_PISCINA);
                        System.out.println("Servei afegit: "+ SERVEI_PISCINA);
                        limit++;
                        break;
                    }      

                default:
                    break;
            }
        }
        }while(!confirmacio.equalsIgnoreCase("n") && limit < 4);

        //Devuelve los servicios
        return serveis;
    }

    /**
     * Calcula i retorna el cost total de la reserva, incloent l'habitació,
     * els serveis seleccionats i l'IVA.
     */
    public static float calcularPreuTotal(String tipusHabitacio, ArrayList<String> serveisSeleccionats) {
    //TODO:

    float subTotal = 0;

    System.out.println();
    System.out.println("Calculem el total...\n");

    // Preu habitació
    float preuHabitacio = preusHabitacions.get(tipusHabitacio);
    subTotal += preuHabitacio;
    System.out.println("Preu habitació: " + preuHabitacio + "$\n");

    // Serveis
    if (serveisSeleccionats.isEmpty()) {
        System.out.println("Serveis: Cap\n");
    } else {
        System.out.print("Serveis: ");

        for (int i = 0; i < serveisSeleccionats.size(); i++) {
            String servei = serveisSeleccionats.get(i);
            float preuServei = preusServeis.get(servei);

            subTotal += preuServei;

            System.out.print(servei + " (" + preuServei + "$)");

            if (i < serveisSeleccionats.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("\n");
    }

    // Subtotal
    System.out.println("Subtotal: " + subTotal + "$\n");

    // IVA
    float iva = subTotal * IVA;
    System.out.println("IVA (21%): " + iva + "$\n");

    // Total
    float total = subTotal + iva;
    System.out.println("TOTAL: " + total + "$");

    return total;
    }

    /**
     * Genera i retorna un codi de reserva únic de tres xifres
     * (entre 100 i 999) que no estiga repetit.
     */
    public static int generarCodiReserva() {
        //TODO:
        int codi=0;
        
        //Genera un nombre (desde 100 a 999) y devuelve el codigo
        do{
            codi=random.nextInt(900)+100;
        }while (reserves.containsKey(codi));

        System.out.println("Reserva creada amb èxit!");
        System.out.println("Codi de reserva: "+codi);
        return codi;
    }

    /**
     * Permet alliberar una habitació utilitzant el codi de reserva
     * i actualitza la disponibilitat.
     */
    public static void alliberarHabitacio() {
        System.out.println("\n===== ALLIBERAR HABITACIÓ =====");
         // TODO: Demanar codi, tornar habitació i eliminar reserva
        System.out.print("Introdueix el codi de reserva: ");
        int codi = sc.nextInt();
        sc.nextLine();


        //Comprueba si existe la reserva, obtiene el tipo de habitacion y elimina la reserva
        if (reserves.containsKey(codi)) {

            System.out.println("\nReserva trobada!");


            ArrayList<String> infoReserva = reserves.get(codi);


            String tipusHabitacio = infoReserva.get(0);


            int lliuresActuals = disponibilitatHabitacions.get(tipusHabitacio);
            disponibilitatHabitacions.put(tipusHabitacio, lliuresActuals + 1);


            reserves.remove(codi);

            System.out.println("\nHabitació alliberada correctament.");
            System.out.println("Disponibilitat actualitzada.");

            } else {
                System.out.println("\nERROR: No s'ha trobat cap reserva amb aquest codi.");
            } 
    }

    /**
     * Mostra la disponibilitat actual de les habitacions (lliures i ocupades).
     */
    public static void consultarDisponibilitat() {

        //Muestra con una tabla las habitaciones libres y las ocupadas    
        System.out.println("\n===== DISPONIBILITAT D'HABITACIONS =====");
        System.out.println("Tipus\t\tLliures\tOcupades");

        int lliuresEstandard = disponibilitatHabitacions.get("Estàndard");
        int ocupadesEstandard = CAPACITAT_ESTANDARD - lliuresEstandard;

        int lliuresSuite = disponibilitatHabitacions.get("Suite");
        int ocupadesSuite = CAPACITAT_SUITE - lliuresSuite;

        int lliuresDeluxe = disponibilitatHabitacions.get("Deluxe");
        int ocupadesDeluxe = CAPACITAT_DELUXE - lliuresDeluxe;

        System.out.println("Estàndard\t" + lliuresEstandard + "\t" + ocupadesEstandard);
        System.out.println("Suite\t\t" + lliuresSuite + "\t" + ocupadesSuite);
        System.out.println("Deluxe\t\t" + lliuresDeluxe + "\t" + ocupadesDeluxe);
    }


    /**
     * Funció recursiva. Mostra les dades de totes les reserves
     * associades a un tipus d'habitació.
     */
    //Llama a la funcion recursiva que hemos hecho
    public static void llistarReservesPerTipus(int[] codis, String tipus) {
        llistarReservesPerTipusRec(codis, tipus, 0);
}

    //Funcion recursiva que recorre un array de codigos y muestra las reservas del tipo inficado
    private static void llistarReservesPerTipusRec(int[] codis, String tipus, int index) {

        if (index == codis.length) {
            return;
        }

        int codi = codis[index];

        if (reserves.containsKey(codi)) {
            ArrayList<String> info = reserves.get(codi);

        if (info.get(0).equals(tipus)) {
            System.out.println("\nCodi reserva: " + codi);
            mostrarDadesReserva(codi);
        }
    }

        llistarReservesPerTipusRec(codis, tipus, index + 1);
    }

    /**
     * Permet consultar els detalls d'una reserva introduint el codi.
     */
    //Lee el codigo y llama a la funcion mostrarDadesReserva
    public static void obtindreReserva() {
        System.out.println("\n===== CONSULTAR RESERVA =====");
        // TODO: Mostrar dades d'una reserva concreta
        System.out.print("Introdueix el codi de reserva: ");
        int codi = sc.nextInt();

        mostrarDadesReserva(codi);
 
    }

    /**
     * Mostra totes les reserves existents per a un tipus d'habitació
     * específic.
     */
    //El usuario elige un tipo de habitacion y llama a la funcion recursiva
    public static void obtindreReservaPerTipus() {
        System.out.println("\n===== CONSULTAR RESERVES PER TIPUS =====");
        // TODO: Llistar reserves per tipus
        System.out.println("Seleccione tipus d'habitació:");
        System.out.println("1. Estàndard");
        System.out.println("2. Suite");
        System.out.println("3. Deluxe");

        int opcio = sc.nextInt();
        sc.nextLine();

        String tipus;

        if (opcio == 1) {
            tipus = TIPUS_ESTANDARD;
        }   else if (opcio == 2) {
            tipus = TIPUS_SUITE;
        } else if (opcio == 3) {
            tipus = TIPUS_DELUXE;
        } else {
            System.out.println("Opció incorrecta");
            return;
        }
        
        int[] codis = new int[reserves.size()];
        int i = 0;

        for (Integer codi : reserves.keySet()) {
            codis[i] = codi;
            i++;
        }

    
        llistarReservesPerTipus(codis, tipus);
    }

    /**
     * Consulta i mostra en detall la informació d'una reserva.
     */
    //Muestra la informacion de la reserva
    public static void mostrarDadesReserva(int codi) {
       // TODO: Imprimir tota la informació d'una reserva

        if (!reserves.containsKey(codi)) {
            System.out.println("No existix ninguna reserva amb este codi.");
            return;
        }
 
        ArrayList<String> info = reserves.get(codi);

        System.out.println("\n===== DADES DE LA RESERVA =====");
        System.out.println("Codi de reserva: " + codi);
        System.out.println("Tipus d'habitació: " + info.get(0));
        System.out.println("Preu total: " + info.get(1) + "$");

        if (info.size() == 2) {
            System.out.println("Serveis: Cap servei addicional");
        } else {
            System.out.print("Serveis: ");
            for (int i = 2; i < info.size(); i++) {
                System.out.print(info.get(i));
                if (i < info.size() - 1) {
                System.out.print(", ");
                }
        }
            System.out.println();
        }
    }

    // --------- MÈTODES AUXILIARS (PER MILLORAR LEGIBILITAT) ---------

    /**
     * Llig un enter per teclat mostrant un missatge i gestiona possibles
     * errors d'entrada.
     */
    static int llegirEnter(String missatge) {
        int valor = 0;
        boolean correcte = false;
        while (!correcte) {
                System.out.print(missatge);
                valor = sc.nextInt();
                correcte = true;
        }
        return valor;
    }

    /**
     * Mostra per pantalla informació d'un tipus d'habitació: preu i
     * habitacions disponibles.
     */
    static void mostrarInfoTipus(String tipus) {
        int disponibles = disponibilitatHabitacions.get(tipus);
        int capacitat = capacitatInicial.get(tipus);
        float preu = preusHabitacions.get(tipus);
        System.out.println("- " + tipus + " (" + disponibles + " disponibles de " + capacitat + ") - " + preu + "€");
    }

    /**
     * Mostra la disponibilitat (lliures i ocupades) d'un tipus d'habitació.
     */
    static void mostrarDisponibilitatTipus(String tipus) {
        int lliures = disponibilitatHabitacions.get(tipus);
        int capacitat = capacitatInicial.get(tipus);
        int ocupades = capacitat - lliures;

        String etiqueta = tipus;
        if (etiqueta.length() < 8) {
            etiqueta = etiqueta + "\t"; // per a quadrar la taula
        }

        System.out.println(etiqueta + "\t" + lliures + "\t" + ocupades);
    }
}
