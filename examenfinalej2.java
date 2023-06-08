import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

enum Categoria {
    AVENTURAS, CIENCIA_FICCION, ROMANTICA, HISTORIA, ARTE
}

class Libro {
    private String titulo;
    private String autor;
    private String identificador;
    private Categoria categoria;
    private int edadRecomendada;

    public Libro(String titulo, String autor, String identificador, Categoria categoria, int edadRecomendada) {
        this.titulo = titulo;
        this.autor = autor;
        this.identificador = identificador;
        this.categoria = categoria;
        this.edadRecomendada = edadRecomendada;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getIdentificador() {
        return identificador;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public int getEdadRecomendada() {
        return edadRecomendada;
    }
}

class Usuario {
    private String nombre;
    private String apellido1;
    private String apellido2;
    private LocalDate fechaNacimiento;
    private String dni;

    public Usuario(String nombre, String apellido1, String apellido2, LocalDate fechaNacimiento, String dni) {
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.fechaNacimiento = fechaNacimiento;
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getDni() {
        return dni;
    }
}

class Biblioteca {
    private List<Libro> libros;
    private List<Usuario> usuarios;

    public Biblioteca() {
        libros = new ArrayList<>();
        usuarios = new ArrayList<>();
    }

    public void agregarLibro(Libro libro) {
        libros.add(libro);
    }

    public void agregarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public List<Libro> buscarLibrosDisponibles(Usuario usuario) {
        List<Libro> librosDisponibles = new ArrayList<>();
        int edadUsuario = LocalDate.now().getYear() - usuario.getFechaNacimiento().getYear();

        for (Libro libro : libros) {
            if (libro.getEdadRecomendada() <= edadUsuario) {
                librosDisponibles.add(libro);
            }
        }

        return librosDisponibles;
    }

    public void guardarInformacion() {
        try {
            FileWriter writer = new FileWriter("libros.txt");
            for (Libro libro : libros) {
                writer.write(libro.getTitulo() + ";" + libro.getAutor() + ";" + libro.getIdentificador() + ";" +
                        libro.getCategoria().name() + ";" + libro.getEdadRecomendada() + "\n");
            }
            writer.close();

            writer = new FileWriter("usuarios.txt");
            for (Usuario usuario : usuarios) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String fechaNacimiento = usuario.getFechaNacimiento().format(formatter);
                writer.write(usuario.getNombre() + ";" + usuario.getApellido1() + ";" + usuario.getApellido2() + ";" +
                        fechaNacimiento + ";" + usuario.getDni() + "\n");
            }
            writer.close();

            System.out.println("Información guardada correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar la información: " + e.getMessage());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();

        // Agregar algunos libros de ejemplo
        Libro libro1 = new Libro("Viaje al Centro de la Tierra", "Julio Verne", "12345", Categoria.AVENTURAS, 10);
        Libro libro2 = new Libro("1984", "George Orwell", "67890", Categoria.CIENCIA_FICCION, 16);
        Libro libro3 = new Libro("Orgullo y Prejuicio", "Jane Austen", "54321", Categoria.ROMANTICA, 14);
        biblioteca.agregarLibro(libro1);
        biblioteca.agregarLibro(libro2);
        biblioteca.agregarLibro(libro3);

        // Agregar algunos usuarios de ejemplo
        Usuario usuario1 = new Usuario("Juan", "Perez", "Gomez", LocalDate.of(2000, 5, 10), "12345678X");
        Usuario usuario2 = new Usuario("Maria", "Lopez", "Fernandez", LocalDate.of(2005, 8, 25), "98765432Y");
        biblioteca.agregarUsuario(usuario1);
        biblioteca.agregarUsuario(usuario2);

        // Buscar libros disponibles para un usuario
        List<Libro> librosDisponibles = biblioteca.buscarLibrosDisponibles(usuario2);

        // Mostrar los libros disponibles
        System.out.println("Libros disponibles para " + usuario2.getNombre() + ":");
        for (Libro libro : librosDisponibles) {
            System.out.println("Título: " + libro.getTitulo());
            System.out.println("Autor: " + libro.getAutor());
            System.out.println("Categoría: " + libro.getCategoria().name());
            System.out.println("Edad recomendada: " + libro.getEdadRecomendada());
            System.out.println("Identificador: " + libro.getIdentificador());
            System.out.println();
        }

        // Guardar la información en archivos
        biblioteca.guardarInformacion();
    }
}
