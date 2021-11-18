package EVALUATOR;

import DOM.GeneratorDOM;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Evaluador {
    private static final Scanner teclado = new Scanner(System.in);
    private static GeneratorDOM generadorDOM;
    private static Evaluador manager;
    private static String nomStyle;

    boolean comprobadorSave=false;

    public static void getInstance() throws IOException, TransformerException, ParserConfigurationException, SAXException {
        if (manager == null) {
            manager = new Evaluador();
        }
    }

    private Evaluador() throws IOException, TransformerException, ParserConfigurationException, SAXException {
        generadorDOM = new GeneratorDOM();
        generadorDOM.inicializar();
        menuPrincipal();
    }

    private void menuPrincipal() throws TransformerException, IOException, ParserConfigurationException, SAXException {
        boolean menu = true;
        int eleccionMenu;

        while (menu) {
            try {
                menu();
                eleccionMenu = teclado.nextInt();
                switch (eleccionMenu) {
                    case 1:
                        añadirStyle();
                        break;
                    case 2:
                        añadirArtista();
                        break;
                    case 3:
                        añadirDiscoAlbum();
                        break;
                    case 4:
                        añadirSongs();
                        break;
                    case 5:
                        break;

                    case 6:
                        generadorDOM.generarXML();
                        comprobadorSave=true;

                        break;
                    case 0:
                        System.out.println("Adios! ");
                        menu = false;
                        break;
                    default:
                        System.out.println("Opcion incorrecta, pruebe de nuevo.");
                        break;
                }
            } catch (InputMismatchException ex) {
                System.out.println("Opcion incorrecta, pruebe de nuevo");
                teclado.next();
            }
        }
    }

    private void añadirStyle() {
        nomStyle=null;
        teclado.useDelimiter("\n");
        do {
            if (nomStyle != null && generadorDOM.comprobarExistStyle(nomStyle)) {
                System.out.println("Estilo ya existente, pruebe con otro.");
            }
            System.out.println("Introduzca el estilo de musica a aÃ±adir: ");
            nomStyle = teclado.next();
        } while (generadorDOM.comprobarExistStyle(nomStyle));
        generadorDOM.generarDocumentStyle(nomStyle);
    }

    private void añadirDiscoAlbum() {
        System.out.println("Elija el estilo de musica: ");
        listStyle();
        int eleccion = teclado.nextInt();
        String nomStyle= listStyleEleccion(eleccion);
        System.out.println("Elija el nombre del artista: ");
        listArtist(nomStyle);
        int eleccionArtist = teclado.nextInt();
        String nomArtist = listArtistEleccion(eleccionArtist,nomStyle);
        System.out.println("Introduzca el nombre del disco a aÃ±adir: ");
        String nomDisk = teclado.next();
        generadorDOM.generateDisk(nomStyle,nomArtist, nomDisk);
    }

    private void listStyle(){
        ArrayList<String> style = generadorDOM.listStyle();
        for (int i=0; i<style.size(); i++){
            System.out.println(i+": "+style.get(i));
        }
    }
    private String listStyleEleccion(int eleccion){
        ArrayList<String> style = generadorDOM.listStyle();
        for (int i=0; i<style.size(); i++){
            if (i==eleccion){
                return style.get(i);
            }
        }
        return null;
    }
    private void listArtist(String style){
        ArrayList<String> artist = generadorDOM.listArtist(style);
        for (int i=0; i<artist.size(); i++){
            System.out.println(i+": "+artist.get(i));
        }
    }
    private String listArtistEleccion(int eleccion,String style){
        ArrayList<String> listArtist = generadorDOM.listArtist(style);
        for (int i=0; i<listArtist.size(); i++){
            if (i==eleccion){
                return listArtist.get(i);
            }
        }
        return null;
    }

    private void listDisk(String style,String artist){
        ArrayList<String> disk = generadorDOM.listDisk(style,artist);
        for (int i=0; i<disk.size(); i++){
            System.out.println(i+": "+disk.get(i));
        }
    }
    private String listDiskEleccion(int eleccion,String style,String artist){
        ArrayList<String> listDisk = generadorDOM.listDisk(style,artist);
        for (int i=0; i<listDisk.size(); i++){
            if (i==eleccion){
                return listDisk.get(i);
            }
        }
        return null;
    }

    private void añadirSongs() {
        System.out.println("Elija el estilo de musica: ");
        listStyle();
        int eleccion = teclado.nextInt();
        String nomStyle= listStyleEleccion(eleccion);
        System.out.println("Elija el nombre del artista: ");
        listArtist(nomStyle);
        int eleccionArtist = teclado.nextInt();
        String nomArtist = listArtistEleccion(eleccionArtist,nomStyle);
        System.out.println("Introduzca el nombre del disco: ");
        listDisk(nomStyle,nomArtist);
        int eleccionDisk = teclado.nextInt();
        String nomDisk = listDiskEleccion(eleccionDisk,nomStyle,nomArtist);
        System.out.println("Introduzca el nombre de la cancion: ");
        String song = teclado.next();
        generadorDOM.generateSong(nomStyle,nomArtist, nomDisk, song);
    }


    private void añadirArtista() {
        teclado.useDelimiter("\n");
        System.out.println("Elija el estilo de musica: ");
        listStyle();
        int eleccion = teclado.nextInt();
        String nomStyle= listStyleEleccion(eleccion);
        System.out.println("Introduzca el nombre del artista a aÃ±adir: ");
        String nomArtista = teclado.next();
        generadorDOM.generateArtist(nomStyle, nomArtista);
    }





    private void menu() {
        System.out.println(" ");
        System.out.println("1. Add Style");
        System.out.println("2. Add Artist");
        System.out.println("3. Add Disc/Album");
        System.out.println("4. Add Song");
        System.out.println("5. Extract the artists by style in txt file");
        System.out.println("6. Generate DOM XML");
        System.out.println("0. Exit");
        System.out.println(" ");
    }

}