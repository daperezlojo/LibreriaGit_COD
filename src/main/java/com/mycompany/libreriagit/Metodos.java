
package com.mycompany.libreriagit;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.swing.JOptionPane;
import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.InitCommand;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.RemoteAddCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.kohsuke.github.GHCreateRepositoryBuilder;
import org.kohsuke.github.GitHub;

/**
 * Esta clase contiene todos los métodos necesarios para que funcione el programa
 * @author Damian
 */
public class Metodos {
    
    /**
     * Este metodo estatico nos permite crear un repositorio de GitHub desde Netbeans,
     * añadiendo tus credenciales en un archivo del pc y dándole nombre al repositorio
     * desde el mensaje del JOptionPane del metodo
     * @throws IOException Significa que se ha producido un error en la entrada/salida
     */
     public static void crearRepositorio() throws IOException {
        
        String nombre = JOptionPane.showInputDialog("¿Como deseas nombrar el repositorio?");

        GitHub github = GitHub.connect();
        GHCreateRepositoryBuilder builder;
        builder = github.createRepository(nombre);
        builder.create();
    }
     
     /**
      * Este método estatico nos permite clonar un proyecto desde un repositorio 
      * de GitHub (del cual le pasamos la URL), a una carpeta de nuestro pc(de la
      * cual le pasamos la ruta) mediante un JOptionPane
      * @throws GitAPIException salta la excepcion si se produjo un problema con la Api
      */
    public static void clonar() throws GitAPIException {
     
     String repo = JOptionPane.showInputDialog("Indique el repositorio del proyecto");
     String ruta = JOptionPane.showInputDialog("Indique la ruta de la carpeta"); 
     
        Git.cloneRepository()
                .setURI(repo)
                .setDirectory(new File(ruta))
                .call();
    }
    
    /**
     * Este metodo estatico inicializa el repositorio de un proyecto, del cual le
     * pasamos la ruta
     * @throws GitAPIException salta la excepcion si se produjo un problema con la Api
     */
    public static void inicializarRepositorio() throws GitAPIException {
        
        String ruta = JOptionPane.showInputDialog("Indique la ruta del proyecto");

        InitCommand init = new InitCommand();
        init.setDirectory(new File(ruta))
                .call();

    }
    /**
     * Metodo estatico que nos permite crear un commit siempre y cuando el proyecto
     * ya este inicializado, debemos indicar la ruta del proyecto y el nombre que 
     * deseamos ponerle al commit 
     * @throws IOException Significa que se ha producido un error en la entrada/salida
     * @throws GitAPIException salta la excepcion si se produjo un problema con la Api
     */
    public static void crearCommit() throws IOException, GitAPIException {
        
     String ruta = JOptionPane.showInputDialog("Indique la ruta del proyecto");
     String nombre = JOptionPane.showInputDialog("¿Como deseas nombrar el commit?");
       
        FileRepositoryBuilder repositoryBuilder = new FileRepositoryBuilder();
        Repository repository = repositoryBuilder.setGitDir(new File(ruta+".git"))
                .readEnvironment()
                .findGitDir()
                .setMustExist(true)
                .build();
        Git git = new Git(repository);      
        AddCommand add = git.add();
        add.addFilepattern(ruta+".git").call();
        CommitCommand commit = git.commit();
        commit.setMessage(nombre).call();
    }
    /**
     * Metodo estatico que nos permite subir el proyecto a GitHub siempre y cuando
     * este inicializado y el contenido aparezca guardado en un commit, para que
     * este metodo funcione debemos pasarle la ruta del proyecto, la URI del repositorio
     * de GitHub, y las credenciales de nuestra cuenta.
     * @throws IOException Significa que se ha producido un error en la entrada/salida
     * @throws GitAPIException salta la excepcion si se produjo un problema con la Api
     * @throws URISyntaxException salta la excepcion si se produjo un problema con la url
     */
     public static void hacerPush() throws IOException, GitAPIException, URISyntaxException{
        String ruta = JOptionPane.showInputDialog("Indique la ruta del proyecto");
        String repo = JOptionPane.showInputDialog("Indique la URI del proyecto");
        String usuario = JOptionPane.showInputDialog("Introduce usuario de GitHub");
        String contraseña = JOptionPane.showInputDialog("Introduce contraseña de GitHub");
        
         FileRepositoryBuilder constructorRepositorio = new FileRepositoryBuilder();
        Repository repositorio = constructorRepositorio.setGitDir(new File(ruta+".git"))
                .readEnvironment() 
                .findGitDir() 
                .setMustExist(true)
                .build();
        
        Git git = new Git(repositorio);

        RemoteAddCommand remoteAddCommand = git.remoteAdd();
        remoteAddCommand.setName("origin");
        remoteAddCommand.setUri(new URIish(repo));
        remoteAddCommand.call();

        PushCommand pushCommand = git.push();
        pushCommand.setCredentialsProvider(new UsernamePasswordCredentialsProvider(usuario, contraseña));
        pushCommand.call();
        
    }
    
}
