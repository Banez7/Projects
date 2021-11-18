package DOM;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


public class GeneratorDOM {
    private final Document document;
    private Node MusicStore;
    private Node Artist;
    private final File file;


    public GeneratorDOM() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factoria.newDocumentBuilder();
        file = new File("MusicStore.xml");
        if (!file.exists()) {
            document = builder.newDocument();
        } else {
            document = builder.parse("MusicStore.xml");
        }
    }

    public void inicializar() {
        if (!file.exists()) {
            MusicStore = document.createElement("MusicStore");
            document.appendChild(MusicStore);
        } else if (file.exists()) {
            NodeList listMusic = document.getElementsByTagName("MusicStore");
            MusicStore = listMusic.item(0);
        }
    }

    public void generarDocumentStyle(String nombreStyle) {
        Element style = document.createElement("style");
        MusicStore.appendChild(style);
        style.setAttribute("name", nombreStyle);
    }

    public Node buscarNodoDeStyle(String style) {
        NodeList child = document.getElementsByTagName("style");
        for (int i = 0; i < child.getLength(); i++) {
            Node childNode = child.item(i);
            NamedNodeMap map = childNode.getAttributes();
            if (map.item(0).getNodeValue().equals(style)) {
                return childNode;
            }
        }
        return null;
    }

    public boolean comprobarExistStyle(String nameStyle) {
        NodeList child = document.getElementsByTagName("style");
        for (int i = 0; i < child.getLength(); i++) {
            Node childNode = child.item(i);
            NamedNodeMap map = childNode.getAttributes();
            if (map.item(0).getNodeValue().equals(nameStyle)) {
                return true;
            }
        }
        return false;
    }


    public void generateArtist (String Style, String nameArtist) {
        Node style_name = buscarNodoDeStyle(Style);
        if (style_name != null) {
                Artist = document.createElement("artist");
                style_name.appendChild(Artist);
                Node nameArtists = document.createElement("name");
                Artist.appendChild(nameArtists);
                nameArtists.appendChild(document.createTextNode(nameArtist));
        } else {
            System.out.println("Error, no existe nigun estilo con ese nombre almacenado ");
        }
    }

    public void generateDisk (String nomStyle, String nomArtist, String nomDisk) {
        Node style_name = buscarNodoDeStyle(nomStyle);
        Node nameArtist = buscarNodeArtist(nomArtist, nomStyle);
        if (style_name != null) {
            if (nameArtist != null) {
                Element nameDisk = document.createElement("album_title");
                nameDisk.setAttribute("title", nomDisk);
                nameArtist.appendChild(nameDisk);
            } else {
             System.out.println("Error, no existe ningun Artista con ese nombre");
            }
        } else{
                System.out.println("Error, no existe nigun estilo con ese nombre almacenado ");
            }
        }

    public void generateSong(String nomStyle, String nomArtist, String nomDisk,String song) {
        Node style_name = buscarNodoDeStyle(nomStyle);
        Node nodeArtist = buscarNodeArtist(nomArtist, nomStyle);
        Node buscarNodeArtist = buscarNodeSong(nomArtist, nomStyle,nomDisk);
        Node checkIfSongExist = buscarIfExistSong(nomArtist,nomStyle,nomDisk);
        int numSong = 0;
        Element nameDisk = null;
        if (style_name != null) {
            if (nodeArtist != null) {
                if (buscarNodeArtist != null) {
                    if (checkIfSongExist == null){
                        nameDisk = document.createElement("songs");
                        checkIfSongExist=buscarNodeArtist.appendChild(nameDisk);
                    }   
                        NodeList cont = checkIfSongExist.getChildNodes();
                        for (int i = 0; i < cont.getLength(); i++) {
                            NodeList cont2 = cont.item(i).getChildNodes();
                            for (int j = 0; j < cont2.getLength(); j++) {
                                numSong++;
                            }
                        }
                        numSong++;
                        Element songs = document.createElement("song" + numSong);
                        songs.appendChild(document.createTextNode(String.valueOf(song)));
                        checkIfSongExist.appendChild(songs);

                } else {
                    System.out.println("Error, no existe ningun disco con ese nombre");
                }
            } else {
                System.out.println("Error no existe ningun Artista con ese nombre");
            }
        }else{
            System.out.println("Error no existe ese estilo");
        }
    }

    public Node buscarNodeSong(String artist, String style, String disk) {
        Node nodeArtist = buscarNodeArtist(artist, style);
        if (nodeArtist != null) {
            NodeList child = nodeArtist.getChildNodes();
            for (int i = 0; i < child.getLength(); i++) {
                Node childNode = child.item(i);
                if (childNode.getNodeName().equals("album_title")) {
                    NamedNodeMap map = childNode.getAttributes();
                    if (map.item(0).getNodeValue().equals(disk)) {
                        return childNode;
                    }
                }
            }
        }
        return null;
    }

    public Node buscarIfExistSong(String artist, String style, String disk) {
        Node nodeSong = buscarNodeSong(artist, style,disk);
        if (nodeSong != null){
            NodeList child = nodeSong.getChildNodes();
            for (int i = 0; i < child.getLength(); i++) {
                Node childNode = child.item(i);
                if (childNode.getNodeName().equals("songs")){
                    return childNode;
                }
            }
        }
        return null;
    }

    public Node buscarNodeArtist(String artist, String style) {
        Node style_name = buscarNodoDeStyle(style);
        if (style_name != null) {
            NodeList child = style_name.getChildNodes();
            if (style_name.getNodeType() == Node.ELEMENT_NODE) {
                for (int i = 0; i < child.getLength(); i++) {
                    Node childNode = child.item(i);
                    if (childNode.getNodeName().equals("artist")) {
                        NodeList artistName = childNode.getChildNodes();
                        for (int j = 0; j < artistName.getLength(); j++) {
                            if (artistName.item(j).getNodeName().equals("name") && artistName.item(j).getTextContent().equals(artist)) {
                                Node childNode2 = artistName.item(j);
                                return childNode2.getParentNode();
                            }
                        }
                    }
                }
            }
        }
        return null;
    }


    public ArrayList<String> listStyle() {
        ArrayList<String> listStyle = new ArrayList<>();
        NodeList child = document.getElementsByTagName("style");
        for (int l = 0; l < child.getLength(); l++) {
            Node currentItem = child.item(l);
            String key = currentItem.getAttributes().getNamedItem("name").getNodeValue();
            listStyle.add(key);
        }
        return listStyle;
    }

    public ArrayList<String> listArtist(String style) {
        ArrayList<String> listArtist = new ArrayList<>();
        Node nodeArtist = buscarNodoDeStyle(style);
        NodeList child = nodeArtist.getChildNodes();
        String key = null;
        for (int l = 0; l < child.getLength(); l++) {
            Node currentItem = child.item(l);
            NodeList child2 = currentItem.getChildNodes();
            for (int a = 0; a < child2.getLength(); a++) {
                Node childNode1 = child2.item(a);
                if (childNode1.getNodeName().equals("name")) {
                    key = childNode1.getTextContent();
                    listArtist.add(key);
                }
            }

        }
        return listArtist;
    }



    public ArrayList<String> listDisk(String style, String nomArtist) {
        ArrayList<String> listDisk = new ArrayList<>();
        Node nameArtist = buscarNodeArtist(nomArtist, style);
        NodeList child = nameArtist.getChildNodes();
        String key = null;
        for (int l = 0; l < child.getLength(); l++) {
            Node currentItem = child.item(l);
            if (currentItem.getNodeName().equals("album_title")){
                NamedNodeMap map = currentItem.getAttributes();
                for (int a = 0; a < map.getLength(); a++) {
                    key =(currentItem.getAttributes().getNamedItem("title").getNodeValue());
                    listDisk.add(key);
                }
            }
        }
        return listDisk;
    }

    public void generarXML() throws TransformerException, IOException {
        TransformerFactory factoria = TransformerFactory.newInstance();
        Transformer transformer = factoria.newTransformer();
        Source source = new DOMSource(document);
        File file = new File("MusicStore.xml");
        FileWriter fw = new FileWriter(file);
        PrintWriter pw = new PrintWriter(fw);
        Result result = new StreamResult(pw);
        transformer.transform(source, result);
        fw.close();
        pw.close();
    }

}
