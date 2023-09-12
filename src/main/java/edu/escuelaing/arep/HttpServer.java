package edu.escuelaing.arep;

import com.oracle.xmlns.internal.webservices.jaxws_databinding.JavaWsdlMappingType;

import java.lang.reflect.Method;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Luis Benavides (Modify by Ricardo Olarte)
 */
public class HttpServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(24000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 24000.");
            System.exit(1);
        }

        Socket clientSocket = null;
        try {
            System.out.println("Ready to recive ...");
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }
        PrintWriter out = new PrintWriter(
                clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        String inputLine, outputLine;
        while ((inputLine = in.readLine()) != null) {
            System.out.println("Recibí: " + inputLine);
            if (!in.ready()) {
                break;
            }
            String[] strings = inputLine.split(" ");
            String command = strings[1];
            URI uri = null;

            while(in.ready()) {
                if (inputLine.contains("Class")) {
                    classes(command.replace("/consulta?comando=",""));
                } else if (inputLine.contains("invoke")) {

                } else if (inputLine.contains("unaryInvoke")) {

                } else {

                }

                outputLine = "HTTP/1.1 200 OK\r\n"
                        + "Content-Type: text/html\r\n"
                        + "\r\n"
                        + "<!DOCTYPE html>\n"
                        + "<html>\n"
                        + "<head>\n"
                        + "<meta charset=\"UTF-8\">\n"
                        + "<title>Reflective ChatGPT</title>\n"
                        + "</head>\n"
                        + "<body>\n"
                        + "<h1>Parcial Arep Corte1</h1>\n"
                        + "<h1>Form with GET</h1>\n"
                        + "<form action=\"/hello\">\n"
                        + "<label for=\"name\">Name:</label><br>\n"
                        + "<input type=\"text\" id=\"name\" name=\"name\" value=\"John\"><br><br>\n"
                        + "<input type=\"button\" value=\"Submit\" onclick=\"loadGetMsg()\">\n"
                        + "</form>\n"
                        + "<div id=\"getrespmsg\"></div>\n"
                        + "\n"
                        + "<script>\n"
                        + "function loadGetMsg() {\n"
                        + "let nameVar = document.getElementById(\"name\").value;\n"
                        + "const xhttp = new XMLHttpRequest();\n"
                        + "xhttp.onload = function() {\n"
                        + "document.getElementById(\"getrespmsg\").innerHTML =\n"
                        + "this.responseText;\n"
                        + "}\n"
                        + "xhttp.open(\"GET\", \"/consulta?comando=\"+nameVar);\n"
                        + "xhttp.send();\n"
                        + "}\n"
                        + "</script>\n"
                        + "<h1>Form with POST</h1>\n"
                        + "<form action=\"/hellopost\">\n"
                        + "<label for=\"postname\">Name:</label><br>\n"
                        + "<input type=\"text\" id=\"postname\" name=\"name\" value=\"John\"><br><br>\n"
                        + "<input type=\"button\" value=\"Submit\" onclick=\"loadPostMsg(postname)\">\n"
                        + "</form>"
                        + "\n"
                        + "<div id=\"postrespmsg\"></div>\n"
                        + "\n"
                        + "<script>\n"
                        + "function loadPostMsg(name){\n"
                        + "let url = \"/hellopost?name=\" + name.value;\n"
                        + "\n"
                        + "fetch (url, {method: 'POST'})\n"
                        + ".then(x => x.text())\n"
                        + ".then(y => document.getElementById(\"postrespmsg\").innerHTML = y);\n"
                        + "}\n"
                        + "</script>\n"
                        + "</body>\n"
                        + "</html>\n";
                out.println(outputLine);
                out.close();
            }
        }
        in.close();
        clientSocket.close();
        serverSocket.close();
    }

    /**
     * Retorna una lista de campos declarados y metodos declarados
     * @param class_name
     * @return
     */
    private static String classes(String class_name) {

        //LinkedList<String, String> clase = new LinkedList<>();
        //String methods = "";
        //String[] Objetcs;

        try {
            Class<?> clase = Class.forName(class_name);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return "***";
    }

    private String invoke (String class_name, String method_name){
        return "###";
    }

    private String unaryInvoke (String class_name, String method, String param, String value){
        return "---";
    }

    private String binaryInvoke () {
        return "///";
    }

}

/*
"HTTP/1.1 200 OK\r\n"
                    + "Content-Type: text/html\r\n"
                    + "\r\n"
                    + "<!DOCTYPE html>\r\n"
                    + "<html>\n"
                    + "<head>\n"
                    + "<title>Form Example</title>\n"
                    + "<meta charset=\"UTF-8\">\n"
                    + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                    +"</head>\n"
                    + "<body>\n"
                    + "<h1>Form with GET</h1>\n"
                    + "<form action=\"/hello\">\n"
                    + "<label for=\"name\">Name:</label><br>\n"
                    + "<input type=\"text\" id=\"name\" name="name" value="John"><br><br>\n"
                    + "<input type=\"button\" value=\"Submit\" onclick=\"loadGetMsg()\">\n"
                    + "</form>\n"
                    + "<div id="getrespmsg"></div>\n"
                    + "\n"
                    + "<script>\n"
                    + "function loadGetMsg() {\n"
                    +           "let nameVar = document.getElementById("name").value;\n"
                    +           "const xhttp = new XMLHttpRequest();\n"
                    +           "xhttp.onload = function() {\n"
                    +           "document.getElementById("getrespmsg").innerHTML =\n"
                    +           "this.responseText;\n"
                    +           "}\n"
                    +           "xhttp.open("GET", "/hello?name="+nameVar);\n"
                    + "xhttp.send();\n"
                    + "}\n"
                    + "</script>\n"
                    + "\n"
                    + "<h1>Form with POST</h1>\n"
                    + "<form action="/hellopost">\n"
                    + "<label for="postname">Name:</label><br>\n"
                    + "<input type="text" id="postname" name="name" value="John"><br><br>\n"
                    + "<input type="button" value="Submit" onclick="loadPostMsg(postname)">\n"
                    + "</form>"
                    + "\n"
                    + "<div id=" "\"postrespmsg\"></div>\n"
                    + "\n"
                    + "<script>\n"
                    + "function loadPostMsg(name){\n"
                    + "let url = "/hellopost?name=" + name.value;\n"
                    + "\n"
                    + "fetch (url, {method: 'POST'})\n"
                    + ".then(x => x.text())\n"
                    + ".then(y => document.getElementById("postrespmsg").innerHTML = y);\n"
                    + "}\n"
                    + "</script>\n"
                    + "</body>\n"
                    + "</html>\n"
 */